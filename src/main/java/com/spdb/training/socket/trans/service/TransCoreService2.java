package com.spdb.training.socket.trans.service;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import com.spdb.training.beans.trans.TransReqContext;
import com.spdb.training.beans.trans.TransRspContext;
import com.spdb.training.exception.BusinessException;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.services.ITransServices;
import com.spdb.training.services.ResultCodeEnum;
import com.spdb.training.services.TransServicesFactory;
import com.spdb.training.socket.parsemsg.ParseXmlMessage;
import com.spdb.training.socket.trans.constant.ResponseCode;
import com.spdb.training.socket.trans.entity.RspServiceHeader;
import com.spdb.training.socket.trans.or01.ReqOR01Service;
import com.spdb.training.socket.trans.or01.RspOR01Service;
import com.spdb.training.socket.trans.or01.service.TransOR01Service;
import com.spdb.training.socket.utils.DateUtils;
import com.spdb.training.socket.xml.BeanCoperUtils;
import com.spdb.training.socket.xml.ReflectCache;
import com.spdb.training.socket.xml.ReflectParseUtils;

/**
 * @author 作者: 王腾蛟
 * @version time：2019年6月20日 下午5:07:34 类说明: 交易请求，分发，调用，返回封装（设计为单例模式）
 */
public class TransCoreService2 implements ITransService {

	private final static ILog LOG = LoggerFactory.getLogger(TransCoreService2.class);
	private final static String REQ_SERVICE = "reqService";
	private final static String REQ_HEADER_NODE = "reqHeader";
	private final static String REQ_BODY_NODE = "body";

	/**
	 * 单例模式(静态内部类的实现方式):调用时才会实例化此类(延迟加载); 静态内部内只加载一次保证线程安全
	 * 
	 * @return
	 */
	public static class SingletonHolder {
		private static final TransCoreService2 INSTANCE = new TransCoreService2();
	}
	public static final TransCoreService2 getSingleInstance() {
		return SingletonHolder.INSTANCE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object handlerBussiness(String transCode, Object reqServiceBody, Object rspServiceBody) throws Throwable {

		// 1,根据交易码获取对应的业务类
		ITransServices<Object, Object> service = TransServicesFactory.getTransServices(transCode);

		// 2,获取业务类中的请求参数，返回参数
		ArrayList<Class> genericType = ReflectCache.getGenericInterfaceParams(service.getClass()).get(0);
		Class reqServiceParam = genericType.get(0), rspServiceParam = genericType.get(1);
		Object reqServiceInstance = reqServiceParam.newInstance();
		Object rspServiceInstance = rspServiceParam.newInstance();
		// 2.1,通过同名复制，将传入的reqBody赋值到业务类的请求参数上
		if(reqServiceInstance!=null&&reqServiceBody!=null)
		BeanCoperUtils.copyProperties(reqServiceBody, reqServiceInstance);

		// 3,提前封装请求体对象和返回体对象
		TransReqContext<Object> transReqContext = new TransReqContext<>(transCode, reqServiceInstance);
		TransRspContext<Object> transRspContext = new TransRspContext<>(rspServiceInstance);
		
		// 4，将请求参数、返回参数交给线程池处理
		TransExcetorWrapper2.execute(transReqContext, transRspContext);
		
		// 5,将业务的返回参数 同名赋值到 返回对象上
		BeanCoperUtils.copyProperties(transRspContext.getContext(), rspServiceBody);
		
		return rspServiceBody;
	}
	
	

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public Object handlerBussiness(String transCode, Map<String, Object> map) throws Exception {

		// 1,根据交易码获取对应的业务处理类对象
		ITransServices<Object, Object> service = TransServicesFactory.getTransServices(transCode);

		// 2,根据业务处理类对象 得到 请求对象类型和应对对象类型， map --> javabean
		Map<Integer, ArrayList> genericInterfaceType = ReflectParseUtils.getGenericInterfaceType(service.getClass());
		ArrayList<Class> genericType = genericInterfaceType.get(0);
		Class reqServiceParam = genericType.get(0), rspServiceBody = genericType.get(1);
		// 2.1，从map中获取请求头，请求体
		Map<String, Object> reqServcieMap = (Map<String, Object>) map.get(REQ_SERVICE);
		Map<String, Object> reqHeaderMap = (Map<String, Object>) reqServcieMap.get(REQ_HEADER_NODE);
		Map<String, Object> reqBodyMap = (Map<String, Object>) reqServcieMap.get(REQ_BODY_NODE);
		// 2.2，将请求体map --> 请求体对象javabean
		Object reqServiceBody = ReflectParseUtils.mapToObject(reqBodyMap, reqServiceParam);

		// 3, 封装请求体对象和返回体对象，并将其放入相应的业务逻辑中进行执行；（业务逻辑采用线程池处理）
		TransReqContext<Object> transReqContext = new TransReqContext<>(transCode, reqServiceBody);
		TransRspContext<Object> transRspContext = new TransRspContext<>(rspServiceBody.newInstance());
		// 3.1，返回报文的返回头封装
		RspServiceHeader rspServiceHeader = new RspServiceHeader();
		rspServiceHeader.setTranCode(transCode);
		rspServiceHeader.setTransDate(DateUtils.getDate());
		rspServiceHeader.setBackSeq(String.valueOf(System.currentTimeMillis()));

		try {
			// 3.2，将请求对象和返回对象交给线程池处理
			TransExcetorWrapper2.execute(transReqContext, transRspContext);

			rspServiceHeader.setRetCode(ResultCodeEnum.TRAINPB0000.getCode());
			rspServiceHeader.setRetMsg(ResultCodeEnum.TRAINPB0000.getMsg());
		} catch (BusinessException e) {
			LOG.error("交易执行过程中，捕获业务异常", e);
			rspServiceHeader.setRetCode(e.getErrorCode());
			rspServiceHeader.setRetMsg(e.getErrorMsg());
		} catch (Throwable e) {
			LOG.error("交易执行过程中，捕获系统异常", e);
			rspServiceHeader.setRetCode(ResultCodeEnum.TRAINPBSYSERROR.getCode());
			rspServiceHeader.setRetMsg(ResultCodeEnum.TRAINPBSYSERROR.getMsg());
		}

		// 4,将封装好的请求头对象，放入返回对象里面去
		Method[] methods = rspServiceBody.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals("setHeader")) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				method.invoke(transRspContext.getContext(), parameterTypes[0].cast(rspServiceHeader));
			}
		}
		// 5,返回该交易的返回对象
		return transRspContext.getContext();
	}
	
	

	@SuppressWarnings({ "unused", "rawtypes" })
	public static void main(String[] args) {

		try {
			AbsAtomicTransService<ReqOR01Service, RspOR01Service> transTS01Service = new TransOR01Service();
			ArrayList<Class> genericType = ReflectParseUtils.getGenericExtendsType(transTS01Service.getClass());
			Class reqServiceParam = genericType.get(0), rspServiceParam = genericType.get(1);
			// xmlSocketTransService.map2Object(map, class1)

			String code = ResponseCode.success.getCode();
			System.out.println("code:" + code);

			// 1，报文准备
			String fileToString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><reqService><reqHeader><tranCode>UR</tranCode><transDate>20190619</transDate><transTime>131452</transTime><sysId>0515</sysId><addr>com.spdb.training.service.servicedemo1</addr><msgId>2345</msgId></reqHeader><body><globalSeq>05152345632346</globalSeq><transSeq>456732343</transSeq><transDate>20190723</transDate><id>0001</id><name>zhangSan</name><age>19</age><telNo>18800009999</telNo><creditId>366041992000099</creditId></body></reqService>";

			int length = fileToString.getBytes("utf8").length; // 字节长度
			String lengthStr = String.format("%6d", length).replace(" ", "0");
			String newfileToString = lengthStr + fileToString;
			System.out.println("字节长度：" + length + ",前面补0：" + lengthStr + ",报文：" + newfileToString);

			// xmlToString("D:\\test\\Request.xml");
			// 2，报文解析
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(newfileToString.getBytes("utf8"));
			ParseXmlMessage parseXmlMessage = new ParseXmlMessage();
			Map<String, Object> readAssignMsg2Map = parseXmlMessage.streamToMap(byteArrayInputStream);
			System.out.println("报文已经解析为map对象");
			String transCode = parseXmlMessage.getTransCode(readAssignMsg2Map);
			Object handBussiness = new TransCoreService2().handlerBussiness(transCode, readAssignMsg2Map);

			String objectToXml = ReflectParseUtils.objectToXml(handBussiness);
			byte[] doOutputProcess = new ParseXmlMessage().doOutputProcess(objectToXml);
			System.out.println("交易处理结束:" + objectToXml);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

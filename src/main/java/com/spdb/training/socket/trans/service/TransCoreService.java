package com.spdb.training.socket.trans.service;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import com.spdb.training.exception.BusinessException;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.socket.parsemsg.ParseXmlMessage;
import com.spdb.training.socket.trans.constant.ResponseCode;
import com.spdb.training.socket.trans.entity.RspServiceHeader;
import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;
import com.spdb.training.socket.trans.or01.ReqOR01Service;
import com.spdb.training.socket.trans.or01.RspOR01Service;
import com.spdb.training.socket.trans.or01.service.TransOR01Service;
import com.spdb.training.socket.trans.service.config.InitTransService;
import com.spdb.training.socket.utils.DateUtils;
import com.spdb.training.socket.xml.ReflectParseUtils;

/**
 * @author 作者: 王腾蛟
 * @version time：2019年6月20日 下午5:07:34 类说明: 交易请求，分发，调用，返回封装
 */
public class TransCoreService implements ITransService {

	@SuppressWarnings("unused")
	private final static ILog ILOG = LoggerFactory.getLogger(TransCoreService.class);
	private final static String REQ_SERVICE = "reqService";
	
	/**
	 * 单例模式(静态内部类的实现方式):调用时才会实例化此类(延迟加载); 静态内部内只加载一次保证线程安全
	 * @return
	 */
	private static class SingletonHolder {
		private static final TransCoreService INSTANCE = new TransCoreService();
	}
	public static final TransCoreService getSingleInstance(){
		return SingletonHolder.INSTANCE;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object handlerBussiness(String transCode, Map<String, Object> map) throws Exception {

		
		// 1, 交易分发
		AbsAtomicTransService<Object, Object> service = new InitTransService().getService(transCode);
//		ITransServices<Object, Object> service = TransServicesFactory.getTransServices(transCode);

		// 2,请求报文转换 map --> object
		ArrayList<Class> genericType = ReflectParseUtils.getGenericExtendsType(service.getClass());
		Class reqServiceParam = genericType.get(0), rspServiceParam = genericType.get(1);
		Object map2Object = ReflectParseUtils.mapToObject((Map<String, Object>) map.get(REQ_SERVICE), reqServiceParam);

		// 3, 请求交易调用service.execute
		TransReqContext<Object> transReqContext = new TransReqContext<>();
		transReqContext.setContext(map2Object);
		TransRspContext<Object> transRspContext = new TransRspContext<>();
		transRspContext.setContext(rspServiceParam.newInstance());
		RspServiceHeader rspServiceHeader = new RspServiceHeader();
		rspServiceHeader.setTranCode(transCode);
		rspServiceHeader.setTransDate(DateUtils.getDate());
		rspServiceHeader.setBackSeq("backSeqNo");
		
		try {
//			service.execute(transReqContext, transRspContext);
			TransExcetorWrapper.execute(service, transReqContext, transRspContext);
			rspServiceHeader.setRetCode(ResponseCode.success.getCode());
			rspServiceHeader.setRetMsg(ResponseCode.success.getMsg());
		} catch (BusinessException e) {
			rspServiceHeader.setRetCode(e.getErrorCode());
			rspServiceHeader.setRetMsg(e.getErrorMsg());
		} catch (Exception e) {
			rspServiceHeader.setRetCode(ResponseCode.sys_error.getCode());
			rspServiceHeader.setRetMsg(ResponseCode.sys_error.getMsg());
		}

		// 4,报文返回
		Method[] methods = rspServiceParam.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals("setHeader")) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				method.invoke(transRspContext.getContext(), parameterTypes[0].cast(rspServiceHeader));
			}
		}

		return transRspContext.getContext();
	}


	@SuppressWarnings({ "unused", "rawtypes" })
	public static void main(String[] args) {

		try {
			AbsAtomicTransService<ReqOR01Service, RspOR01Service> transTS01Service = new TransOR01Service();
			ArrayList<Class> genericType = ReflectParseUtils.getGenericExtendsType(transTS01Service.getClass());
			Class reqServiceParam = genericType.get(0), rspServiceParam = genericType.get(1);
//			xmlSocketTransService.map2Object(map, class1)

			String code = ResponseCode.success.getCode();
			System.out.println("code:" + code);

			// 1，报文准备
			String fileToString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><reqService><reqHeader><tranCode>TS03</tranCode><transDate>20190619</transDate><transTime>131452</transTime><sysId>0515</sysId><addr>com.spdb.training.service.servicedemo1</addr><msgId>2345</msgId></reqHeader><body><globalSeq>05152345632346</globalSeq><transSeq>456732343</transSeq><transDate>20190723</transDate><id>0001</id><name>zhangSan</name><age>19</age><telNo>18800009999</telNo><creditId>366041992000099</creditId></body></reqService>";
			
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
			Object handBussiness = new TransCoreService().handlerBussiness(transCode, readAssignMsg2Map);
			
			String objectToXml = ReflectParseUtils.objectToXml(handBussiness);
			byte[] doOutputProcess = new ParseXmlMessage().doOutputProcess(objectToXml);
			System.out.println("交易处理结束:"+objectToXml);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Object handlerBussiness(String transCode, Object reqServiceBody, Object rspServiceBody) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}

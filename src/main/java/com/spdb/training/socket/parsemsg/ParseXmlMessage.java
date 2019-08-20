package com.spdb.training.socket.parsemsg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import com.spdb.training.exception.BusinessException;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.services.ResultCodeEnum;
import com.spdb.training.socket.trans.entity.RspServiceHeader;
import com.spdb.training.socket.trans.or01.ReqOR01Service;
import com.spdb.training.socket.trans.or01.RspOR01Service;
import com.spdb.training.socket.trans.or01.service.TransOR01Service;
import com.spdb.training.socket.trans.service.AbsAtomicTransService;
import com.spdb.training.socket.trans.service.TransCoreService;
import com.spdb.training.socket.trans.service.TransCoreService2;
import com.spdb.training.socket.utils.DateUtils;
import com.spdb.training.socket.xml.ReflectCache;
import com.spdb.training.socket.xml.ReflectParseUtils;

/**
 * @author 作者: 王腾蛟
 * @version time：2019年6月20日 上午10:52:05 
 * 类说明: xml类型的报文解析逻辑
 */

public class ParseXmlMessage extends AbsParseMessage {

	private final static ILog LOG = LoggerFactory.getLogger(ParseXmlMessage.class);
	
	/** 约定报文的前6为是说明整个报文的长度 */
	private static final int msgLengthOccu = 6;

	private static final String REQSERVER_BODY_FIELD = "body"; // 请求报文体的字段名
	private static final String GET_REQSERVER_BODY = "getBody"; // 请求报文体 get方法
	private static final String SET_RSPSERVER_HEADER = "setHeader"; // 返回报文头 set方法
	private static final String SET_RSPSERVER_BODY = "setBody"; // 返回报文体 set方法
	
	/**
	 * 将输入流装换为map对象
	 */
	@Override
	public Map<String, Object> streamToMap(InputStream inputStream) throws Exception {
		
		// 1，根据报文前6位的内容表示报文长度，根据此长度获取报文的字节数组
		byte[] body = readMsgByLength(inputStream, msgLengthOccu);
		// 1.1，将字节数组转换成String类型
		String msgString = new String(body, UTF8);
		LOG.debug("完整的请求报文:{}", msgString);

		// 2，请求报文解析为map对象： string --> map
		return ReflectParseUtils.convertXmlToMap(msgString);
	}
	
	/**
	 * 根据交易码找到对应的业务逻辑
	 */
	@Override
	public Object executeTrans(String transCode, Map<String, Object> map) throws Exception {
		
		// 1,根据交易码，将map转换成相应的接口对象
		Object[] mapToObjectByTransCode = mapToObjectByTransCode(transCode, map);
		Object reqServiceObject = mapToObjectByTransCode[0], rspServiceObject = mapToObjectByTransCode[1];		
		// 1.1,获取请求报文中的请求体(已经有请求值了)
		Method getReqServiceBody = ReflectCache.getReflectMethod(reqServiceObject.getClass(), GET_REQSERVER_BODY, null);
		Object reqServcieBody = getReqServiceBody.invoke(reqServiceObject);
		
		// 1.2 提前准备:初始化返回报文头
		RspServiceHeader rspServiceHeader = new RspServiceHeader();
		rspServiceHeader.setTranCode(transCode);
		rspServiceHeader.setTransDate(DateUtils.getDate());
		rspServiceHeader.setTransTime(String.valueOf(System.currentTimeMillis()));
		// 1.3 提前准备：初始化返回报文体
		Field field = ReflectCache.getField(rspServiceObject.getClass(), REQSERVER_BODY_FIELD);
		Object rspServiceBody = field.getType().newInstance();		
		
		try {
			// 2,根据解析后的交易码、请求接口对象、返回接口对象，找到对应的业务处理逻辑
			TransCoreService2.getSingleInstance().handlerBussiness(transCode, reqServcieBody, rspServiceBody);

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
		
		// 3,封装返回对象（包括返回头和返回体）
		// 3.1 封装返回报文头
		Method setHeaderMethod = ReflectCache.getReflectMethod(rspServiceObject.getClass(), SET_RSPSERVER_HEADER, RspServiceHeader.class);
		setHeaderMethod.invoke(rspServiceObject, rspServiceHeader);
		// 3.2 封装返回报文体
		Method setBodyMethod = ReflectCache.getReflectMethod(rspServiceObject.getClass(), SET_RSPSERVER_BODY, rspServiceBody.getClass());
		setBodyMethod.invoke(rspServiceObject, rspServiceBody);
		
		return rspServiceObject;
	}

	/**
	 * 将交易的返回对象Object --> String类型的xml
	 * @throws Exception 
	 */
	@Override
	public String objectToString(Object object) throws Exception {
		return ReflectParseUtils.objectToXml(object);
	}	

	/**
	 * 将返回报文处理为： 6位长度+实际报文；返回最终的应答报文数组
	 * @throws Exception 
	 */
	@Override
	public byte[] doOutputProcess(String rspString) throws Exception {
		// 1,得到原始返回报文的长度
		int srcLength = rspString.getBytes(UTF8).length;
		// 2，将前6为表示为报文长度信息，后拼接实际报文返回
		String lengthStr = String.format("%6d", srcLength).replace(" ", "0");
		return (lengthStr + rspString).getBytes(UTF8);
	}
	
	/**
	 * 获取map对象中的交易码
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getTransCode(Map<String, Object> xml2Map) {

		Map<String, Object> reqServiceMap = (Map<String, Object>) xml2Map.get(XML_ROOT_NODE);
		Map<String, Object> reqHeaderMap = (Map<String, Object>) reqServiceMap.get(XML_HEADER_NODE);
		return (String) reqHeaderMap.get(XML_HEADER_TRANCODE);
	}

}

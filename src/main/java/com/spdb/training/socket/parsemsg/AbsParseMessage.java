package com.spdb.training.socket.parsemsg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.socket.trans.service.AbsAtomicTransService;
import com.spdb.training.socket.trans.service.config.InitTransService;
import com.spdb.training.socket.xml.ReflectCache;
import com.spdb.training.socket.xml.ReflectParseUtils;

/**
 * @author 作者: 王腾蛟
 * @version time：2019年6月20日 上午10:44:39 
 * 类说明: 报文解析基本流程
 */
public abstract class AbsParseMessage implements IParseMessage {

	private final static ILog LOG = LoggerFactory.getLogger(AbsParseMessage.class);
	
	@Override
	public void handMessage(InputStream inputStream, OutputStream outputStream) throws Exception {

		// 1,将stream输入流解析为map对象： stream --> string --> map
		Map<String, Object> map = streamToMap(inputStream);
		String tranCode = getTransCode(map);
		LOG.debug("本次报文交易码：{}", tranCode);
		
		// 2,从map对象中获取交易码，根据tranCode路由交易，执行相应的业务逻辑，并返回报文体对象
		Object object = executeTrans(tranCode, map);

		// 3,将返回报文对象封装为：string类型的xml(6位定长+报文体)
		String rspXmlString = objectToString(object);
		LOG.debug("交易处理后的返回报文:{}", rspXmlString);
		byte[] doOutputProcess = doOutputProcess(rspXmlString);

		// 4,将返回报文体字节数组写入输出流
		outputStream.write(doOutputProcess);
		outputStream.flush();
	}

	/**
	 * 截取指定长度的报文
	 * 
	 * @param inputStream 输入流
	 * @param msgLengthOccu 表示报文长度占前几位
	 * @return
	 * @throws Exception
	 */
	public byte[] readMsgByLength(InputStream inputStream, int msgLengthOccu) throws Exception {

		// 1,读取前6位的报文信息，解析表示具体报文长度的字节信息
		byte[] lengthBytes = readFixedBytes(inputStream, msgLengthOccu);

		// 2,解析长度为int类型
		int msgLenth = Integer.parseInt(new String(lengthBytes, UTF8));

		// 3,根据报文长度，读取具体长度的请求报文
		byte[] requestBytes = readFixedBytes(inputStream, msgLenth);

		return requestBytes;

	}

	/***
	 * 读取指定长度的字节
	 * 
	 * @param ins 输入流
	 * @param sumLeng : 要读取的字节长度
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFixedBytes(InputStream ins, int sumLength) throws IOException {
		// 1,初始化要读取的固定长度的数组
		byte[] bytesBuffer = new byte[sumLength];
		int readLength = 0;
		int hasReadLength = 0;// 已经读取的字节数
		// 2，循环读取，直到读到的字节长度为sumLenght
		while ((readLength = ins.read(bytesBuffer, hasReadLength, sumLength - hasReadLength)) > 0) {
			// 每次循环获取一次，则计算实际已读的字节长度
			hasReadLength = hasReadLength + readLength;
		}
		return bytesBuffer;
	}

	/**
	 * 
	 * @param transCode
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object[] mapToObjectByTransCode(String transCode, Map map) throws Exception{
		// 1,根据交易码找到对应的请求对象和返回对象
		AbsAtomicTransService<Object, Object> service = new InitTransService().initService(transCode);
		
		// 2,采用反射机制获取泛型参数（请求和返回）
		List<Class> genericType = ReflectCache.getGenericExtendsParams(service.getClass());
		Class reqServiceParam = genericType.get(0), rspServiceParam = genericType.get(1);
		Object reqServiceObject = ReflectParseUtils.mapToObject((Map<String, Object>) map.get(XML_ROOT_NODE), reqServiceParam);
		
		// 3,实例化请求对象、实例化返回对象，并返回
		Object[] objects = new Object[]{reqServiceObject, rspServiceParam.newInstance()};
		return objects;
	}

	/** 封装交易后的返回对象 object--> string */
	public abstract String objectToString(Object object) throws Exception;
	/** 根据交易码，执行相应的业务 */
	public abstract Object executeTrans(String transCode, Map<String, Object> map) throws Exception;
	/** 报文解析stream --> map */
	public abstract Map<String, Object> streamToMap(InputStream inputStream) throws Exception;
	/** 封装返回流string --> byte */
	public abstract byte[] doOutputProcess(String rspString) throws Exception;

}

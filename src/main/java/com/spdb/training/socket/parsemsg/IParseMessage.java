package com.spdb.training.socket.parsemsg;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 上午10:36:36 
*   类说明:  报文解析接口
*/
public interface IParseMessage {
	

	/** 约定报文编码为UTF8*/
	static final String UTF8 = "utf8";
	static final String XML_ROOT_NODE = "reqService"; // xml中的root根节点
	static final String XML_HEADER_NODE = "reqHeader"; // xml中的请求头节点
	static final String XML_HEADER_TRANCODE = "tranCode"; // xml中的请求头中的交易码标签
	
	/**
	 * 请求报文解析 -- 交易处理 -- 报文返回
	 * @param inputStream
	 * @param outputStream
	 * @throws Exception
	 */
	void handMessage(InputStream inputStream, OutputStream outputStream) throws Exception;
	
	/**
	 * 获取map对象中的交易码
	 * @param map
	 * @return 
	 */
	String getTransCode(Map<String, Object> map);
}

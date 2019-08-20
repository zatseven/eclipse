package com.spdb.training.socket.trans.service;

import java.util.Map;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 下午5:02:24 
*   类说明:
*/
public interface ITransService {
	
	
	/**
	 * 第一种获取交易方式：理原始map报文；根据交易码以及报文Map，转换成对应的交易对象，并执行交易
	 * @param transCode
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Object handlerBussiness(String transCode, Map<String, Object> map) throws Exception;

	/**
	 * 第二种方式：
	 * @param transCode 业务交易码
	 * @param reqServiceBody 原始请求对象中的请求体
	 * @param rspServiceBody 原始请求对象中的返回体
	 * @return
	 * @throws Throwable
	 */
	Object handlerBussiness(String transCode, Object reqServiceBody, Object rspServiceBody) throws Throwable;

//	/**
//	 * 第一种方式：处理请求体对象；根据交易码分发交易
//	 * @param transCode
//	 * @param reqBody 请求报文中的请求体对象
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable 
//	 */
//	Object handlerBussiness(String transCode, Object reqBody) throws Exception, Throwable;
//	
//	/**
//	 * 第二种方式：处理原始map报文；根据交易码以及报文Map，转换成对应的交易对象，并执行交易
//	 * @param transCode
//	 * @param map
//	 * @return
//	 * @throws Exception
//	 */
//	Object handlerBussiness(String transCode, Map<String, Object> map) throws Exception;
	
}

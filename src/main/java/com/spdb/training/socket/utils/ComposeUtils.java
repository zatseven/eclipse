package com.spdb.training.socket.utils;

import java.io.BufferedInputStream;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月19日 下午5:14:18 
*   类说明: 报文约定好：xml格式/UTF8
*/
public class ComposeUtils {
	
//	private static final byte BLOCK_BGN_SIGN = 0x68;
//    private static final byte BLOCK_END_SIGN = 0x16;
	private static final String UTF8 = "utf8";
	
	/**
	 * 报文解析：1，保报文前6为报文长度；2，根据报文长度获取报文信息
	 * @throws Exception 
	 * @throws  
	 */
	public static String messageAnalyse(BufferedInputStream bufferedInputStream) throws Exception {
		
		
		// 1，约定报文前6位为报文长度
		byte[] bytes1 = new byte[6];
		if (6 != bufferedInputStream.read(bytes1))
			throw new Exception("前6位的报文长度读取异常 ");
		int len = Integer.parseInt(new String(bytes1));
		System.out.println("读取报文长度：" + len);
		
		// 2根据报文长度获取报文
		byte[] body = new byte[len];
		if (len != bufferedInputStream.read(body))
			throw new Exception("读取报文长度不足");
		
		return new String(body, UTF8);
	}

}

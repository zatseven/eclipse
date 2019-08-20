package com.spdb.training.socket.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.socket.parsemsg.IParseMessage;
import com.spdb.training.socket.parsemsg.ParseXmlMessage;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月18日 下午1:41:19 
*   类说明:
*/
public class HandlerSocketService implements Runnable {
	
	private final static ILog LOG = LoggerFactory.getLogger(HandlerSocketService.class);
	private Socket client;

	public HandlerSocketService(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		
		InputStream inputStream = null;
		OutputStream outputStream  = null;
		
		try {
			// 1,获取客户端的输入输出流
			inputStream = client.getInputStream(); // 获取客户端发送过来的输入流
			outputStream = client.getOutputStream();
			
			// 2,报文解析/交易执行/交易返回
			IParseMessage parseMessage = new ParseXmlMessage();
			parseMessage.handMessage(inputStream, outputStream);		
			
		} catch (Throwable e) {
			LOG.error("客户端信息处理异常",e);
		} finally {
			try {
				LOG.debug("交易结束，关闭客户端连接");
				if(outputStream!=null) outputStream.close();
				if(inputStream!=null) inputStream.close();
				if (client!=null) client.close();
			} catch (Throwable e2) {
				LOG.error("输入输出流关闭处理异常",e2);
			}
		}
		
	}

}

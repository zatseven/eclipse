package com.spdb.training.socket.server;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.socket.parsemsg.ParseXmlMessage;
import com.spdb.training.socket.xml.ReflectParseUtils;

public class SocketClient {

	private final static ILog LOG = LoggerFactory.getLogger(SocketClient.class);
	private final static String CHARSET_UTF8 = "utf8";
	private final static String SERVER_IP = "127.0.0.1";
	private final static int SERVER_PORT = 8084;
	
	public String start(String request, String host, int port) {

		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;

		try {
			socket = new Socket(host, port); // 指定连接的服务端地址
			LOG.debug("Socket客户端已经启动，端口:{}",socket.getLocalPort());
			
			// 1,发送请求数据
			outputStream = socket.getOutputStream();
			outputStream.write(request.getBytes(CHARSET_UTF8));
			outputStream.flush();
//			Thread.sleep(60000);
			// 2,获取服务端的返回信息
			inputStream = socket.getInputStream();
			// 3,根据规则，拿到只有报文的返回报文
			byte[] body = new ParseXmlMessage().readMsgByLength(inputStream, 6);
			String rspXML = new String(body, CHARSET_UTF8);
			LOG.debug("客户端收到的应答报文:{}", rspXML);
			return rspXML;
		} catch (Exception e) {
			LOG.error("客户端与服务端交互时抛出异常",e);
			return null;
		} finally {
			try {
				if (inputStream != null) outputStream.close();
				if (outputStream != null) outputStream.close();
				if (socket != null) socket.close();
			} catch (Exception e2) {
				LOG.error("客户端流关闭时抛出异常",e2);
			}
		}
	}
	public static void main(String[] args) {
		
		FileWriter fileWriter = null;
		
		try {
			// 1,从本地的请求xml文件中获取请求信息
//			String xmlPathname = "D:\\test\\UserRegisterReq.xml";
			String xmlPathname = "src/main/resources/OR01Request.xml";
			String xmlToString = ReflectParseUtils.xmlToString(xmlPathname);
			LOG.debug("xml:{}",xmlToString);
			// 2,按照6位长度+报文 规范生成新的请求报文
			int srcLength = xmlToString.getBytes("utf8").length;
			String newxml = String.format("%6d", srcLength).replace(" ", "0") + xmlToString;
			LOG.debug("newxml:{}",newxml);
			// 3,拿到返回的应答报文
			String rspXmlString = new SocketClient().start(newxml,SERVER_IP,SERVER_PORT);
			// 4,将应答报文写入本地盘中的UserRegisterRsp.xml
			String rspXmlPath = "src/main/resources/OR01Response.xml";
			File file = new File(rspXmlPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			fileWriter = new FileWriter(file);
			fileWriter.write(rspXmlString);
			fileWriter.flush();
			
			
		} catch (Exception e) {
			LOG.error("客户端测试主线程抛出异常",e);
		} finally {
			try {
				if (fileWriter!=null) fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

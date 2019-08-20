package com.spdb.training.socket.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月18日 上午9:54:58 
*   类说明:
*/
public class CloseStream {

	private final static ILog LOG = LoggerFactory.getLogger(CloseStream.class);
	
	public static void dataOutputStreamClose(DataOutputStream dataOutputStream){
		
		if (dataOutputStream != null) {
			try {
				dataOutputStream.close();
			} catch (IOException e) {
				LOG.error("输出流DataOutputStream关闭异常",e);
			}
		}
	}
	public static void dataInputStreamClose(DataInputStream dataInputStream){
		
		if (dataInputStream != null) {
			try {
				dataInputStream.close();
			} catch (IOException e) {
				LOG.error("输入流dataInputStream关闭异常",e);
			}
		}
	}
	
	public static void outputStreamClose(OutputStream outputStream){
		
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				LOG.error("输出流OutputStream关闭异常",e);
			}
		}
	}
	public static void inputStreamClose(InputStream inputStream){
		
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				LOG.error("输入流InputStream关闭异常",e);
			}
		}
	}
	
	public static void socketClose(Socket socket) {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				LOG.error("Socket关闭异常",e);
			}
		}
	}
}

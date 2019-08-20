package com.spdb.training.socket.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.threadpool.ThreadPoolFactory;

/**
 * @author 作者: 王腾蛟
 * @version time：2019年6月18日 上午9:44:24 
 * 类说明: 服务端应用程序是通过java语言提供的socket类来实现的
 */
public class SocketServer {

	private final static ILog LOG = LoggerFactory.getLogger(SocketServer.class);
	/** 创建socketserver服务处理线程池*/
	private static ExecutorService threadPool = ThreadPoolFactory.getThreadPool(10,50,50,"handlerSocketPool");
	
	/**
	 * socket服务端口，类
	 * @param port
	 * @param clazz
	 */
	private void start(int port) {

		ServerSocket serverSocket = null;
		Socket client = null;

		try {
			// 1,创建端口建立连接
			serverSocket = new ServerSocket(port); 
			LOG.info("Socket服务端已经启动，绑定地址：{},监听端口:{}", serverSocket.getInetAddress(), serverSocket.getLocalPort());
			// 2,循环监听程序，以监视连接请求
			while (true) { 
				try {
					client = serverSocket.accept(); // 监听端口请求，等待连接；获取与socket连接的数据流对象(阻塞特性)
					LOG.debug("Socket客户端已经请求连接过来，地址:{},端口:{}", client.getInetAddress(), client.getLocalPort());
					HandlerSocketService socketServerService = new HandlerSocketService(client);
					threadPool.execute(socketServerService);
				} catch (Throwable e) {
					LOG.error("客户端请求处理异常",e);
					// TODO 实际生产中可以调用通知接口，将异常信息通知出去，通知人员排查
				} 
			}
		} catch (Throwable e) {
			LOG.error("服务端启动异常",e);
		} finally {
			try {
				if (serverSocket!=null) serverSocket.close();
			} catch (Throwable e) {
				LOG.error("服务端关闭异常",e);
			}
		}
	}

	public static void main(String[] args) {
		try {
			new SocketServer().start(8084);
		} catch (Exception e) {
			LOG.error("服务端启动异常",e);
		}
	}
}

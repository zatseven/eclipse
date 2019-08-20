package com.spdb.training.socket.server;

import java.util.concurrent.ExecutorService;

import com.spdb.training.threadpool.ThreadPoolFactory;

public class Laucher {
	private static ExecutorService pool = ThreadPoolFactory.getThreadPool(10, 50, 50, "handlerSocketPool");

	public static void main(String[] args) {
		for (int i = 0; i < 20000; i++) {
			Thread th = new Thread(new SocketClient2(), "王" + i);
			pool.execute(th);
		}
	}
}

package com.spdb.training.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 默认线程队列
 * 该队列为有界队列，默认深度5000
 * @author wanglw2
 *
 */
public class DefaultTransQueue extends ArrayBlockingQueue<Runnable> {
	private int qInitSize=5000;
	private static final long serialVersionUID = 1459203955387924835L;
	private DefautThreadPoolExceutor executor = null;

	public DefaultTransQueue(int capacity) {
		super(capacity);
		this.setqInitSize(capacity);
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(DefautThreadPoolExceutor executor) {
		this.executor = executor;
	}

	public int getqInitSize() {
		return qInitSize;
	}

	public void setqInitSize(int qInitSize) {
		this.qInitSize = qInitSize;
	}

//	public boolean offer(Runnable e) {
//		// 当前线程数量
//		int currentThreadSize = executor.getPoolSize();
//		// //如果已经达到�?大线程数，则进队�?
//		if (currentThreadSize == executor.getMaximumPoolSize()) {
//			return super.offer(e);
//		}
//
//		int submited = executor.getSubmitedThread();
//		// 如果当前运行的线程小于core数量，直接进入队�?
//		if (submited < currentThreadSize) {
//			return super.offer(e);
//		}
//
//		// 如果没有达到�?大，则让线程池启用最�?
//		if (currentThreadSize < executor.getMaximumPoolSize()) {
//			return false;
//		}
//
//		return super.offer(e);
//	}

}

package com.spdb.training.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 线程池工厂类
 * @author wlw
 *
 */
public class ThreadPoolFactory {
	private static Map<String, ExecutorService> threadPoolMap = new HashMap<>();
	public synchronized static ExecutorService getThreadPool(int corePoolSize, int maxPoolSize, int queueSize,String threadPoolName) {
		ExecutorService executor = null;
		if (threadPoolMap.containsKey(threadPoolName)) {
			executor = (ExecutorService) threadPoolMap.get(threadPoolName);
		} else {
			executor = new DefautThreadPoolExceutor(corePoolSize, maxPoolSize, queueSize, threadPoolName);
			threadPoolMap.put(threadPoolName,executor);
		}
		return executor;
	}

}

package com.spdb.training.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
/**
 * 默认线程池满拒绝执行策略器
 * 如果满，则使用当前线程执行
 * @author wanglw2
 *
 */
public class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {
	private ILog logger = LoggerFactory.getLogger(DefaultRejectedExecutionHandler.class);

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		DefautThreadPoolExceutor e = (DefautThreadPoolExceutor) executor;
		logger.warn("ThreadPool:{} is busy,Task will exe by caller. {}", e.getPoolName(), e.toString());
		r.run();
	}

}

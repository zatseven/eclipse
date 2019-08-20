package com.spdb.training.threadpool;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/**
 * 默认线程池
 * @author wlw
 *
 */
public class DefautThreadPoolExceutor extends ThreadPoolExecutor {
	private ILog logger = LoggerFactory.getLogger(DefautThreadPoolExceutor.class);
	private AtomicInteger submittedThreads = new AtomicInteger(0);
	private String poolName = "";

	public DefautThreadPoolExceutor(int corePoolSize, int maxPoolSize, int queueSize, String poolName) {
		super(corePoolSize, maxPoolSize, 300, TimeUnit.SECONDS, new DefaultTransQueue(queueSize),
				new TransThreadFactory(poolName), new DefaultRejectedExecutionHandler());
		((DefaultTransQueue) this.getQueue()).setExecutor(this);
		this.poolName = poolName;
	}

	public void execute(Runnable command) {
		// 当前运行
		int runTask = submittedThreads.incrementAndGet();
		DefaultTransQueue q = (DefaultTransQueue) this.getQueue();
		int qS = q.remainingCapacity();
		if ((qS * 1.0d / q.getqInitSize()) <= 0.2) {
			logger.warn("{} current pool size:{},qRemainingCapacity:{},queue size:{},submittedThreads:{}",
					new Object[] { poolName, this.getPoolSize(), qS, q.getqInitSize(), runTask });
		}
		super.execute(command);
	}

	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		submittedThreads.decrementAndGet();
	}

	/**
	 * 获取当前提交未结束的的线程
	 * 
	 * @return
	 */
	public int getSubmitedThread() {
		return submittedThreads.get();
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
}

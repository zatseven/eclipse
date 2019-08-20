package com.spdb.training.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 交易线程创建工厂
 * @author wanglw2
 *
 */
public class TransThreadFactory implements ThreadFactory {
	/**
	 * 线程流水号
	 */
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	/**
	 * 线程池名称
	 */
	private String poolName="";
	public TransThreadFactory(String poolName){
		this.poolName = poolName;
	}
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setName(poolName+"-TransThread-"+threadNumber.getAndIncrement());
		return t;
	}

}


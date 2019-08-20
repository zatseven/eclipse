package com.spdb.training.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * �����̴߳�������
 * @author wanglw2
 *
 */
public class TransThreadFactory implements ThreadFactory {
	/**
	 * �߳���ˮ��
	 */
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	/**
	 * �̳߳�����
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


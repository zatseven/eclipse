package com.spdb.training.socket.trans.threadpool;

import java.util.concurrent.Callable;

import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;
import com.spdb.training.socket.trans.service.IAtomicTransService;

/** 
* @author 作者: 王腾蛟
* @version time：2019年7月24日 上午9:47:48 
*   类说明: Callable的call方法，不是异步执行的，是由Future的run方法调用的
 * @param <T>
*/
public class TransRunThread implements Callable<ThreadRunResult>{

	@SuppressWarnings("rawtypes")
	private IAtomicTransService atomicTransService;
	@SuppressWarnings("rawtypes")
	private TransReqContext transReqContext;
	@SuppressWarnings("rawtypes")
	private TransRspContext transRspContext;
	
	@SuppressWarnings("rawtypes")
	public TransRunThread(IAtomicTransService atomicTransService, TransReqContext transReqContext, TransRspContext transRspContext) {
		this.atomicTransService = atomicTransService;
		this.transReqContext = transReqContext;
		this.transRspContext = transRspContext;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ThreadRunResult call() throws Exception {
		
		atomicTransService.execute(transReqContext, transRspContext);
		ThreadRunResult threadRunResult = new ThreadRunResult(atomicTransService, transReqContext, transRspContext);
		return threadRunResult;
	}

}

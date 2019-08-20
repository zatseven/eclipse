package com.spdb.training.socket.trans.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;
import com.spdb.training.socket.trans.threadpool.ThreadRunResult;
import com.spdb.training.socket.trans.threadpool.TransRunThread;

/** 
* @author 作者: 王腾蛟
* @version time：2019年7月24日 上午10:06:48 
*   类说明:
*/
public class TransExcetorWrapper {

	static ExecutorService executorService = Executors.newFixedThreadPool(10);
	private final static ILog LOG = LoggerFactory.getLogger(TransExcetorWrapper.class);
	
	@SuppressWarnings("rawtypes")
	public static void execute(IAtomicTransService atomicTransService, TransReqContext transReqContext, TransRspContext transRspContext) throws Exception {
		long t = System.currentTimeMillis();
		runTrans(atomicTransService, transReqContext, transRspContext);
		LOG.info("交易耗时:{}", (System.currentTimeMillis() - t));
	}

	@SuppressWarnings("rawtypes")
	private static void runTrans(IAtomicTransService atomicTransService, TransReqContext transReqContext,
			TransRspContext transRspContext) throws Exception {
		
		TransRunThread transRunThread = new TransRunThread(atomicTransService, transReqContext, transRspContext);
		Future<ThreadRunResult> submit = executorService.submit(transRunThread);
		submit.get();
	}
	
}

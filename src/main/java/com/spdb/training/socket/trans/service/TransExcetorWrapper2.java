package com.spdb.training.socket.trans.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.spdb.training.beans.trans.TransReqContext;
import com.spdb.training.beans.trans.TransRspContext;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.threadpool.ThreadPoolFactory;
import com.spdb.training.threadpool.ThreadRunResult;
import com.spdb.training.threadpool.TransRunThread;

/** 
* @author 作者: 王腾蛟
* @version time：2019年7月24日 上午10:06:48 
*   类说明:交给线程池处理业务逻辑
*/
public class TransExcetorWrapper2 {
	
	private final static ILog LOG = LoggerFactory.getLogger(TransExcetorWrapper2.class);
	/**创建交易执行线程池*/
	private static ExecutorService threadPool = ThreadPoolFactory.getThreadPool(10,50,50,"handlerTransPool");

	/**
	 * 交易处理器
	 * @param transReqContext
	 * @param transRspContext
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void execute(TransReqContext transReqContext, TransRspContext transRspContext) throws Throwable {
		long t = System.currentTimeMillis();
		runTrans(transReqContext, transRspContext);
		LOG.info("交易码【{}】耗时:{}ms", transReqContext.getTransCode(),(System.currentTimeMillis() - t));
	}
	
	/**
	 * 建议执行放入线程池
	 * @param transReqContext
	 * @param transRspContext
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	private static void runTrans(TransReqContext transReqContext, TransRspContext transRspContext) throws Throwable {
		TransRunThread transRunThread = new TransRunThread(transReqContext, transRspContext);
		Future<ThreadRunResult> submit = threadPool.submit(transRunThread);
		submit.get();
		// 判断业务逻辑中是否抛出过异常；如果有异常，从请求头里面获取异常信息
		if (transReqContext.isException()) {
			if (transReqContext.getException() instanceof RuntimeException) {
				throw (RuntimeException)transReqContext.getException();
			} else {
				throw (Throwable)transReqContext.getException();
			}
		}
	}

}

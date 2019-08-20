package com.spdb.training.threadpool;

import java.util.concurrent.Callable;

import com.spdb.training.beans.trans.TransReqContext;
import com.spdb.training.beans.trans.TransRspContext;
import com.spdb.training.exception.BusinessException;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.services.ITransServices;
import com.spdb.training.services.TransServicesFactory;



public class TransRunThread implements Callable<ThreadRunResult> {
	
	private final static ILog LOG = LoggerFactory.getLogger(TransRunThread.class);
	
	private TransReqContext<?> reqContext;
	private TransRspContext<?> rspContext;

	@SuppressWarnings("rawtypes")
	public TransRunThread(TransReqContext reqContext,
			TransRspContext rspContext) {
		this.reqContext = reqContext;
		this.rspContext = rspContext;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ThreadRunResult call() throws Exception {
		ThreadRunResult rst = new ThreadRunResult(reqContext, rspContext);
		try {
			ITransServices services = TransServicesFactory.getTransServices(reqContext.getTransCode());
			services.execute(reqContext, rspContext);
		} catch (Throwable e) {
			reqContext.setException(e);
		}
		return rst;
	}
	

}

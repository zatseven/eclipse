package com.spdb.training.threadpool;

import com.spdb.training.beans.trans.TransReqContext;
import com.spdb.training.beans.trans.TransRspContext;

/**
 * 线程运行结果
 * @author wlw
 *
 */
public class ThreadRunResult {
	private TransReqContext<?> reqContext;
	private TransRspContext<?> rspContext;
	@SuppressWarnings("rawtypes")
	public ThreadRunResult(TransReqContext reqContext,
			TransRspContext rspContext) {
		this.reqContext = reqContext;
		this.rspContext = rspContext;
	}

	@SuppressWarnings("rawtypes")
	public TransReqContext getReqContext() {
		return reqContext;
	}
	@SuppressWarnings("rawtypes")
	public void setReqContext(TransReqContext reqContext) {
		this.reqContext = reqContext;
	}
	@SuppressWarnings("rawtypes")
	public TransRspContext getRspContext() {
		return rspContext;
	}
	@SuppressWarnings("rawtypes")
	public void setRspContext(TransRspContext rspContext) {
		this.rspContext = rspContext;
	}
}

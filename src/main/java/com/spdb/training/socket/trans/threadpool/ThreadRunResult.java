package com.spdb.training.socket.trans.threadpool;

import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;
import com.spdb.training.socket.trans.service.IAtomicTransService;

/** 
* @author 作者: 王腾蛟
* @version time：2019年7月24日 上午9:57:40 
*   类说明:
*/
public class ThreadRunResult {

	@SuppressWarnings("rawtypes")
	private IAtomicTransService atomicTransService;
	@SuppressWarnings("rawtypes")
	private TransReqContext transReqContext;
	@SuppressWarnings("rawtypes")
	private TransRspContext transRspContext;
	
	@SuppressWarnings("rawtypes")
	public ThreadRunResult(IAtomicTransService atomicTransService, TransReqContext transReqContext, TransRspContext transRspContext) {
		this.atomicTransService = atomicTransService;
		this.transReqContext = transReqContext;
		this.transRspContext = transRspContext;
	}
	
	@SuppressWarnings("rawtypes")
	public IAtomicTransService getAtomicTransService() {
		return atomicTransService;
	}
	@SuppressWarnings("rawtypes")
	public void setAtomicTransService(IAtomicTransService atomicTransService) {
		this.atomicTransService = atomicTransService;
	}
	@SuppressWarnings("rawtypes")
	public TransReqContext getTransReqContext() {
		return transReqContext;
	}
	@SuppressWarnings("rawtypes")
	public void setTransReqContext(TransReqContext transReqContext) {
		this.transReqContext = transReqContext;
	}
	@SuppressWarnings("rawtypes")
	public TransRspContext getTransRspContext() {
		return transRspContext;
	}
	@SuppressWarnings("rawtypes")
	public void setTransRspContext(TransRspContext transRspContext) {
		this.transRspContext = transRspContext;
	}
	
	
}

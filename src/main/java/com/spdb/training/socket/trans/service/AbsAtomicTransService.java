package com.spdb.training.socket.trans.service;

import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月19日 下午2:01:28 
*   类说明: 交易执行流程
*/
public abstract class AbsAtomicTransService<Req, Rsp> implements IAtomicTransService<Req, Rsp> {

	@Override
	public void execute(TransReqContext<Req> reqContext, TransRspContext<Rsp> rspContext) {
		valid(reqContext);
		process(reqContext, rspContext);
	}

	public abstract void process(TransReqContext<Req> reqContext, TransRspContext<Rsp> rspContext);

	public abstract void valid(TransReqContext<Req> reqContext) ;
	
	
}

package com.spdb.training.services;

import com.spdb.training.beans.trans.TransReqContext;
import com.spdb.training.beans.trans.TransRspContext;

/**
 * 联机交易服务接口
 * @author wlw
 *
 */
public interface ITransServices<Req,Rsp> {
	/**
	 * 交易入口
	 * @param reqContext
	 * @param rspContext
	 */
	public void execute(TransReqContext<Req> reqContext ,TransRspContext<Rsp> rspContext);
}

package com.spdb.training.socket.trans.service;

import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月19日 下午2:03:41 
*   类说明:
*/
public interface IAtomicTransService<Req, Rsp> {

	void execute(TransReqContext<Req> reqContext, TransRspContext<Rsp> rspContext);

	
}

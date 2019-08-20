package com.spdb.training.socket.trans.or02.service;

import com.spdb.training.socket.trans.anno.TransCodeAnno;
import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;
import com.spdb.training.socket.trans.or02.ReqOR02Service;
import com.spdb.training.socket.trans.or02.RspOR02Service;
import com.spdb.training.socket.trans.service.AbsAtomicTransService;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 下午4:32:10 
*   类说明:
*/
@TransCodeAnno("OR02")
public class TransOR02Service extends AbsAtomicTransService<ReqOR02Service, RspOR02Service>  {

	@Override
	public void process(TransReqContext<ReqOR02Service> reqContext, TransRspContext<RspOR02Service> rspContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valid(TransReqContext<ReqOR02Service> reqContext) {
		// TODO Auto-generated method stub
		
	}

}

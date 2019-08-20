package com.spdb.training.socket.trans.or01.service;

import com.spdb.training.socket.trans.anno.TransCodeAnno;
import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;
import com.spdb.training.socket.trans.or01.ReqOR01Service;
import com.spdb.training.socket.trans.or01.RspOR01Service;
import com.spdb.training.socket.trans.or01.RspOR01ServiceBody;
import com.spdb.training.socket.trans.service.AbsAtomicTransService;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月18日 下午4:20:13 
*   类说明: 测试用的交易TS01
*/
@TransCodeAnno("OR01")
public class TransOR01Service extends AbsAtomicTransService<ReqOR01Service, RspOR01Service> {

	@Override
	public void process(TransReqContext<ReqOR01Service> reqContext, TransRspContext<RspOR01Service> rspContext) {
		// TODO Auto-generated method stub
//		RspOR01Service context = rspContext.getContext();
//
//		
//		rspContext.getContext().setBody(body);
	}

	@Override
	public void valid(TransReqContext<ReqOR01Service> reqContext) {
		// TODO Auto-generated method stub
		System.out.println("valid:请完成你的业务逻辑校验");
	}

}

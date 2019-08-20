package com.spdb.training.socket.trans.ts03.service;

import com.spdb.training.socket.trans.anno.TransCodeAnno;
import com.spdb.training.socket.trans.entity.TransReqContext;
import com.spdb.training.socket.trans.entity.TransRspContext;
import com.spdb.training.socket.trans.service.AbsAtomicTransService;
import com.spdb.training.socket.trans.ts03.ReqTS03Service;
import com.spdb.training.socket.trans.ts03.RspTS03Service;
import com.spdb.training.socket.trans.ts03.RspTS03ServiceBody;

/** 
* @author 作者: 王腾蛟
* @version time：2019年7月24日 下午2:09:12 
*   类说明:
*/
@TransCodeAnno("UR")
public class TransTS03Service extends AbsAtomicTransService<ReqTS03Service, RspTS03Service> {

	@Override
	public void process(TransReqContext<ReqTS03Service> reqContext, TransRspContext<RspTS03Service> rspContext) {
		
		ReqTS03Service context = reqContext.getContext();
		System.out.println("请求参数中的报文中的:Body().getTelNo():"+context.getBody().getTelNo());
		System.out.println("process:请完成你的业务逻辑处理");
		

		RspTS03ServiceBody body = new RspTS03ServiceBody();
		body.setName(context.getBody().getName());
		body.setUserNo("name000001");
		System.out.println("用户注册通过，后台分配no:"+body.getUserNo());
		rspContext.getContext().setBody(body);
		
	}

	@Override
	public void valid(TransReqContext<ReqTS03Service> reqContext) {
		// TODO Auto-generated method stub
		
	}

}

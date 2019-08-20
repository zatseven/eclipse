package com.spdb.training.services;

import com.spdb.training.beans.trans.TransReqContext;
import com.spdb.training.beans.trans.TransRspContext;
import com.spdb.training.beans.user.User;
import com.spdb.training.beans.user.UserRsp;
import com.spdb.training.exception.BusinessException;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.services.user.UserRegisterServices;

public class UserRegisterTransServices implements ITransServices<User, UserRsp> {
	ILog log = LoggerFactory.getLogger(UserRegisterTransServices.class);
	private UserRegisterServices userRegisterServices;
	
	@Override
	public void execute(TransReqContext<User> reqContext, TransRspContext<UserRsp> rspContext) {
		// 实锟斤拷锟斤拷锟斤拷锟斤拷锟酵拷锟絊pring注锟斤拷
		if (userRegisterServices == null) {
			userRegisterServices = new UserRegisterServices();
		}
		User user = reqContext.getContext();
		log.info("锟斤拷始锟矫伙拷注锟结：[{}]",user);
		if (user == null) {

		}
		String userCode = "";
		// 锟斤拷锟斤拷没锟斤拷欠锟斤拷丫锟斤拷锟斤拷锟�
		log.info("锟斤拷锟斤拷没锟斤拷锟较拷欠锟斤拷锟斤拷");
		User u = userRegisterServices.queryUser(userCode);
		if (u != null) {
			throw new BusinessException(ResultCodeEnum.TRAINPB0001.getCode(), ResultCodeEnum.TRAINPB0001.getMsg(),
					userCode);
		}
		// 锟斤拷锟斤拷锟矫伙拷
//		userRegisterServices.newUser(user);
		log.info("锟矫伙拷锟斤拷息注锟斤拷锟斤拷锟�");

		UserRsp context = rspContext.getContext();
		context.setName(reqContext.getContext().getName());
		context.setUserNo("34567");
	}

}

package com.spdb.training.services.user;

import java.sql.SQLException;

import com.spdb.training.beans.user.User;
import com.spdb.training.dao.UserinfoDao;
import com.spdb.training.exception.ExceptionHandle;
import com.spdb.training.socket.trans.ts03.ReqTS03ServiceBody;
/**
 * ÓÃ»§×¢²á
 * @author wanglw2
 *
 */
public class UserRegisterServices implements IUserRegister {

	@Override
	public User queryUser(String usercode) {
//		ReqTS03ServiceBody reqBody =null;
		User  user = new User();
		try {
			user =UserinfoDao.queryById(usercode);
//			user.setBody(reqBody);
			
			
		} catch (SQLException e) {
			ExceptionHandle.handle(e);
		}
		return  user;
		
	}

	@Override
	public void newUser(User user) {
		try {
			UserinfoDao.insert(user);
		} catch (SQLException e) {
			ExceptionHandle.handle(e);
		}

	}

}

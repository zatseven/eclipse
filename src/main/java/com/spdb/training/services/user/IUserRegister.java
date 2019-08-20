package com.spdb.training.services.user;

import com.spdb.training.beans.user.User;

/**
 * 用户注册
 * @author wanglw2
 *
 */
public interface IUserRegister {
	/**
	 * 根据用户代码查询用户信息
	 * @param usercode
	 * @return
	 */
	public User queryUser(String usercode);
	/**
	 * 用户注册
	 * @param user
	 */
	public void newUser(User user);
}

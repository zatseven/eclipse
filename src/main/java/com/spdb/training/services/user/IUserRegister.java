package com.spdb.training.services.user;

import com.spdb.training.beans.user.User;

/**
 * �û�ע��
 * @author wanglw2
 *
 */
public interface IUserRegister {
	/**
	 * �����û������ѯ�û���Ϣ
	 * @param usercode
	 * @return
	 */
	public User queryUser(String usercode);
	/**
	 * �û�ע��
	 * @param user
	 */
	public void newUser(User user);
}

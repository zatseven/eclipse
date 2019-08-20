package com.spdb.training.beans.user;

import java.io.Serializable;

public class UserRsp implements Serializable{

	private static final long serialVersionUID = -1017085371735723853L;
	
	private String name;
	private String userNo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	
}

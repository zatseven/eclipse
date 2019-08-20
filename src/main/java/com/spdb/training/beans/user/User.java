package com.spdb.training.beans.user;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -1017085371735723853L;
	
	private String transSeq;
	private String transDate;
	private String id;
	private String name;
	private String sex;
	private int age;
	private String telNo;
	private String creditId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getCreditId() {
		return creditId;
	}
	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
	public String getTransSeq() {
		return transSeq;
	}
	public void setTransSeq(String transSeq) {
		this.transSeq = transSeq;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	
	
}

package com.spdb.training.socket.trans.ts03;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 下午2:35:20 
*   类说明:
*/
@XmlType(propOrder= {"transSeq","transDate","id","name","sex","age","telNo","creditId"})
public class ReqTS03ServiceBody {
	
	@XmlElement(name="transSeq")
	private String transSeq;
	@XmlElement(name="transDate")
	private String transDate;
	@XmlElement(name="id")
	private String id;
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="sex")
	private String sex;
	@XmlElement(name="age")
	private int age;
	@XmlElement(name="telNo")
	private String telNo;
	@XmlElement(name="creditId")
	private String creditId;
	
	@XmlTransient
	public String getTransSeq() {
		return transSeq;
	}
	public void setTransSeq(String transSeq) {
		this.transSeq = transSeq;
	}
	@XmlTransient
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	@XmlTransient
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@XmlTransient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlTransient
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@XmlTransient
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@XmlTransient
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	@XmlTransient
	public String getCreditId() {
		return creditId;
	}
	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
	@Override
	public String toString() {
		return "ReqTS03ServiceBody [transSeq=" + transSeq + ", transDate=" + transDate + ", id=" + id + ", name=" + name
				+ ", sex=" + sex + ", age=" + age + ", telNo=" + telNo + ", creditId=" + creditId + "]";
	}
	
	

}

package com.spdb.training.socket.trans.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月19日 上午10:20:56 
*   类说明:
*/
@XmlType(propOrder= {"tranCode","transDate","transTime"})
public class ReqServiceHeader {

	@XmlElement(name="tranCode")
	private String tranCode;
	@XmlElement(name="transDate")
	private String transDate;
	@XmlElement(name="transTime")
	private String transTime;
	
	@XmlTransient  
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	@XmlTransient  
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	@XmlTransient
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}	

}

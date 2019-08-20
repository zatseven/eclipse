package com.spdb.training.socket.trans.ts03;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 下午2:37:50 
*   类说明:
*/
@XmlType(propOrder= {"name","userNo"})
public class RspTS03ServiceBody {

	@XmlElement(name="name")
	private String name;
	@XmlElement(name="userNo")
	private String userNo;
	@XmlTransient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlTransient
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
}

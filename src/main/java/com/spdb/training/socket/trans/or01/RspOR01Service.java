package com.spdb.training.socket.trans.or01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.spdb.training.socket.trans.entity.RspServiceHeader;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月18日 下午6:02:30 
*   类说明:
*/
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlRootElement(name="rspService")
@XmlType(propOrder= {"rspHeader", "body"})
public class RspOR01Service {

	@XmlElement(name = "rspHeader")
	private RspServiceHeader rspHeader;
	@XmlElement(name = "body")
	private RspOR01ServiceBody body;
	@XmlTransient
	public RspServiceHeader getHeader() {
		return rspHeader;
	}
	public void setHeader(RspServiceHeader rspHeader) {
		this.rspHeader = rspHeader;
	}
	@XmlTransient
	public RspOR01ServiceBody getBody() {
		return body;
	}
	public void setBody(RspOR01ServiceBody body) {
		this.body = body;
	}
	
}

package com.spdb.training.socket.trans.or01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.spdb.training.socket.trans.entity.ReqServiceHeader;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月18日 下午6:02:30 
*   类说明:
*/
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlRootElement(name="reqService")
@XmlType(propOrder= {"reqHeader", "body"})
public class ReqOR01Service {

	@XmlElement(name = "reqHeader")
	private ReqServiceHeader reqHeader;
	@XmlElement(name = "body")
	private ReqOR01ServiceBody body;
	
	public ReqServiceHeader getHeader() {
		return reqHeader;
	}
	public void setHeader(ReqServiceHeader reqHeader) {
		this.reqHeader = reqHeader;
	}
	public ReqOR01ServiceBody getBody() {
		return body;
	}
	public void setBody(ReqOR01ServiceBody body) {
		this.body = body;
	}
	
}

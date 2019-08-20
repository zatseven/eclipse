package com.spdb.training.socket.trans.or02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.spdb.training.socket.trans.entity.ReqServiceHeader;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 下午2:34:08 
*   类说明:
*/
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlRootElement(name="reqService")
@XmlType(propOrder= {"reqHeader", "body"})
public class ReqOR02Service {
	
	@XmlElement(name = "reqHeader")
	private ReqServiceHeader reqHeader;
	@XmlElement(name = "body")
	private ReqOR02ServiceBody body;
	
	public ReqServiceHeader getHeader() {
		return reqHeader;
	}
	public void setHeader(ReqServiceHeader reqHeader) {
		this.reqHeader = reqHeader;
	}
	public ReqOR02ServiceBody getBody() {
		return body;
	}
	public void setBody(ReqOR02ServiceBody body) {
		this.body = body;
	}
}

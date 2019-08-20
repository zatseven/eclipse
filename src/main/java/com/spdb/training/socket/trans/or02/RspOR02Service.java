package com.spdb.training.socket.trans.or02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
public class RspOR02Service {

	@XmlElement(name = "rspHeader")
	private RspServiceHeader rspHeader;
	@XmlElement(name = "body")
	private RspOR02ServiceBody body;
	
	public RspServiceHeader getHeader() {
		return rspHeader;
	}
	public void setHeader(RspServiceHeader rspHeader) {
		this.rspHeader = rspHeader;
	}
	public RspOR02ServiceBody getBody() {
		return body;
	}
	public void setBody(RspOR02ServiceBody body) {
		this.body = body;
	}
	
}

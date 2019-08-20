package com.spdb.training.socket.trans.entity;

import java.io.Serializable;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 下午3:25:33 
*   类说明:
*/
@SuppressWarnings("serial")
public class TransReqContext<T> implements Serializable {
	
	private String globalSeq;
	private String transCode;
	private T context;
	
	public void TransRspContext(T context) {
		this.context = context;
	}
	
	public String getGlobalSeq() {
		return globalSeq;
	}
	public void setGlobalSeq(String globalSeq) {
		this.globalSeq = globalSeq;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public T getContext() {
		return context;
	}
	public void setContext(T context) {
		this.context = context;
	}
	

}

package com.spdb.training.beans.trans;

import java.io.Serializable;

/**
 * 联机交易上下文
 * @author wlw
 *
 */
@SuppressWarnings("serial")
public class TransRspContext<T> implements Serializable{
	
	/**
	 * 交易报文
	 */
	private T context;
	
	public TransRspContext(T context){
		this.context = context;
	}
	public T getContext() {
		return context;
	}

	public void setContext(T context) {
		this.context = context;
	}
}


package com.spdb.training.beans.trans;

import java.io.Serializable;

/**
 * ��������������
 * @author wlw
 *
 */
@SuppressWarnings("serial")
public class TransRspContext<T> implements Serializable{
	
	/**
	 * ���ױ���
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


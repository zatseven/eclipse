package com.spdb.training.socket.trans.entity;

import java.io.Serializable;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 下午3:35:43 
*   类说明:
 * @param <T>
*/
@SuppressWarnings("serial")
public class TransRspContext<T> implements Serializable {

	private T context;

	public T getContext() {
		return context;
	}

	public void setContext(T context) {
		this.context = context;
	}
	
	
}

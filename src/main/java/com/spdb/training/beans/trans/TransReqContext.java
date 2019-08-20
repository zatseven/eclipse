package com.spdb.training.beans.trans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 联机交易请求上下文
 * @author wlw
 *
 */
@SuppressWarnings("serial")
public class TransReqContext<T> implements Serializable{
	/*
	 * 业务请求时间
	 */
	private long reqTS = System.currentTimeMillis();
	
	private final static String EXCEPTION_KEY = "throwable";
	/**
	 * 交易码
	 */
	private String transCode;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public TransReqContext(String transCode,T context){
		this.transCode = transCode;
		this.context = context;
	}
	
	/**
	 * 交易报文
	 */
	private T context;

	public T getContext() {
		return context;
	}

	public void setContext(T context) {
		this.context = context;
	}
	public long getReqTS() {
		return reqTS;
	}

	public void setReqTS(long reqTS) {
		this.reqTS = reqTS;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public void setException(Throwable e){
		map.put(EXCEPTION_KEY,e);
	}
	@SuppressWarnings("unchecked")
	public T getException(){
		return (T) map.get(EXCEPTION_KEY);
	}
	public boolean isException(){
		return (getException()!=null);
	}
}


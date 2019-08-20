package com.spdb.training.exception;
/**
 * 系统异常
 * @author wanglw2
 *
 */
public class SystemException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1145212757054136691L;
	public SystemException(String message){
		super(message);
	}
	public SystemException(String message, Throwable cause){
		super(message,cause);
	}
	public SystemException(Throwable cause){
		super(cause);
	}
}

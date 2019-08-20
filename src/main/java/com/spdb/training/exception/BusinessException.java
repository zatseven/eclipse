package com.spdb.training.exception;


/**
 * 业务异常
 * @author wlw
 *
 */
public class BusinessException extends RuntimeException {
	private String errorCode;
	private String errorMsg;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7298211154265190797L;
	
	public BusinessException(String errorCode,String errorMsg){
		super(errorMsg+"["+errorCode+"]");
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}
	public BusinessException(String errorCode,String errorMsg,Throwable cause){
		super(errorMsg+"["+errorCode+"]",cause);
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}

	
	
	public BusinessException(String errorCode,String errorMsg, String... params) {
		super(String.format(errorMsg, params)+"["+errorCode+"]");
		this.setErrorCode(errorCode);
		this.setErrorMsg(String.format(errorMsg, params));
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	

}

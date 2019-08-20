package com.spdb.training.transaction;
/**
 * 定义一个事务处理专用异常
 * @author wangxg3
 *
 */
public class TransactionException extends RuntimeException {

	
	private static final long serialVersionUID = 8049959242619670253L;

	public TransactionException() {
		super();
	}

	public TransactionException(String msg) {
		super(msg);
	}

	public TransactionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public TransactionException(Throwable cause) {
		super(cause);
	}

	protected TransactionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	

}

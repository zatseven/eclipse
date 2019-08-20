package com.spdb.training.exception;

/**
 * 系统异常处理类
 * @author wanglw2
 *
 */
public class ExceptionHandle {
	public static void handle(Throwable e) {
		if(e instanceof BusinessException){
			throw (BusinessException)e;
		}else if (e instanceof SystemException) {
			throw (SystemException)e;
	    }else{
			throw new SystemException(e);
		}
	}
	
	
}
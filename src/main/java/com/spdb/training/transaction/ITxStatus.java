package com.spdb.training.transaction;


/**
 * 事务状态
 * @author wangxg3
 *
 */
public interface ITxStatus {
	
	
	/**
	 * 判断事务状态
	 * @return
	 */
	boolean isCompleted();
	
	void setCompleted(boolean completed);
	
	
    /**
     * 获取事务对象
     * @return
     */
	ITransaction  getTransaction();
	
   
	
	


}

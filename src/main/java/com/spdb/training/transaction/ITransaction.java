package com.spdb.training.transaction;

import java.sql.Connection;

public interface ITransaction {
   /**
    * 	判断事务活跃状态
    * @return
    */
	boolean isTransactionActive();
	
	/**
	    *    修改事务活跃状态
	 * @param active
	 */
	void setTransactionActive(boolean active);
	
	
	void setPreviousIsolation(int isolation);
	
	/**
	 * 	连接最初的事务隔离级别
	 * @return
	 */
	int getPreviousIsolation();
	
    
	Connection getConnection();

}

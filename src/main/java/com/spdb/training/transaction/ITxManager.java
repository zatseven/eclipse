package com.spdb.training.transaction;

/**
 * 事务管理器接口
 * 
 * @author wangxg3
 *
 */
public interface ITxManager {

	/**
	 * 开启一个新事务
	 * 
	 * @param defination
	 * @return
	 */
	ITxStatus startTransaction(ITxDefination defination) throws TransactionException;

	/**
	 * 提交进行中的事务
	 * @param status
	 * @throws TransactionException
	 */
	void commit(ITxStatus status) throws TransactionException;
    
	
	/**
	 * 回滚一个事务
	 * @param status
	 * @throws TransactionException
	 */
	void rollback(ITxStatus status) throws TransactionException;

	

}

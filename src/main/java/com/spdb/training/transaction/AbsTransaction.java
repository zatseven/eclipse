package com.spdb.training.transaction;

import java.sql.Connection;


/**
 * 
 * @author wangxg3
 *
 */
public abstract class AbsTransaction implements ITransaction {

	/**
	 * 当前连接对象
	 */
	protected final Connection currentConnection;
	


	/**
	 * 标记事务是否处于开启状态
	 */
	protected boolean transactionActive = false;

	public AbsTransaction(Connection connection) {
		this.currentConnection = connection;
	}

	@Override
	public boolean isTransactionActive() {
		return transactionActive;
	}
	
	
	

	public void setTransactionActive(boolean transactionActive) {
		this.transactionActive = transactionActive;
	}

	@Override
	public Connection getConnection() {
		return this.currentConnection;
	}

}

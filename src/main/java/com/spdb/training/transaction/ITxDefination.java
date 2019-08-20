package com.spdb.training.transaction;

import java.sql.Connection;

/**
 * 事务定义
 * @author wangxg3
 *
 */
public interface ITxDefination {
	/**
	 * 定义事务隔离级别
	 */
	int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;
	int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;
	int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;
	int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;
	int ISOLATION_DEFAULT = -1;
	
	/**
	 * 获取事务隔离级别
	 * @return
	 */
	int getIsolationLevel();

}

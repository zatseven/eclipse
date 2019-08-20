package com.spdb.training.transaction;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
/**
 * 事务同步管理器，负责当前线程同步事务资源
 * @author wangxg3
 *
 */
public abstract class AbsTransactionSyncManager {

	private static ILog logger = LoggerFactory.getLogger(AbsTransactionSyncManager.class);

	private static final ThreadLocal<Map<DataSource, ITransaction>> resources = new ThreadLocal<>();

	/**
	 * 绑定资源到线程
	 * 
	 * @param dataSource
	 * @param transaction
	 */
	public static void bindResource(DataSource dataSource, ITransaction transaction) {

		Map<DataSource, ITransaction> map = resources.get();

		if (map == null) {
			map = new HashMap<>();
			resources.set(map);
		}

		if (map.get(dataSource) == null) {
			map.put(dataSource, transaction);

		} else {// 如果已经绑定则抛出错误
			throw new TransactionException("transaction already bind to currentThread!Can not bind again!");

		}

	}

	/**
	 * 从线程解绑资源
	 * 
	 * @param key
	 * @return
	 * @throws IllegalStateException
	 */
	public static ITransaction unbindResource(DataSource key) {

		Map<DataSource, ITransaction> map = resources.get();
		if (map == null) {
			return null;
		}
		ITransaction value = map.remove(key);
		// Remove entire ThreadLocal if empty...
		if (map.isEmpty()) {
			resources.remove();
		}

		if (value == null) {
			throw new TransactionException(
					"No value for key [" + key + "] bound to thread [" + Thread.currentThread().getName() + "]");
		}
		return value;
	}

	/**
	 * 检查事务绑定状态
	 * 
	 * @param key
	 * @return
	 */
	public static boolean checkIfExist(DataSource key) {

		Map<DataSource, ITransaction> map = resources.get();

		if (map != null && map.get(key) != null) {
			return true;
		}

		return false;

	}

	/**
	 * 从线程中获取一个开启事务的连接
	 * @param key
	 * @return
	 */
	public static Connection getResource(DataSource key) {
		Map<DataSource, ITransaction> map = resources.get();
		Connection conn = null;
		if (map != null && map.get(key) != null) {

			conn = map.get(key).getConnection();
		}
				
		return conn;

	}

}

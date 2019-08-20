package com.spdb.training.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.spdb.training.jdbc.core.DbUtils;

/**
 * 
 * @author wangxg3
 *
 */
public class DataSourceTransactionManager extends AbsTransactionManager {

	private DataSource dataSource;

	public DataSourceTransactionManager(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 执行开启一个新事务的操作, 包括把事务资源绑定到线程
	 * 
	 * @param defination
	 * @param transaction
	 */
	@Override
	protected void doBegin(ITxDefination defination, AbsTransaction transaction) {
		// 开启一个事务
		Connection conn = transaction.getConnection();

		try {
			if (conn.getAutoCommit()) {
				// 开启事务
				conn.setAutoCommit(false);
				// 这个属性主要是为了后续扩展嵌套事务功能时来做相关的判断
				transaction.transactionActive = true;
			}
		} catch (SQLException e) {
			DbUtils.closeConn(conn);
			throw new TransactionException("Can not begin transaction!", e);
		}

		// 绑定到线程

		AbsTransactionSyncManager.bindResource(dataSource, transaction);

	}

	/**
	 * 生成一个事务对象,并按照事务定义完成初始化
	 * 
	 * @param defination
	 * @return
	 */

	@Override
	protected AbsTransaction doGetTransaction(ITxDefination defination) {

		Connection conn = null;
		int previousIsolation;
		try {
			conn = dataSource.getConnection();
			previousIsolation = conn.getTransactionIsolation();
			// 选择default则使用数据库默认的事务隔离级别
			if (ITxDefination.ISOLATION_DEFAULT != defination.getIsolationLevel())
				conn.setTransactionIsolation(defination.getIsolationLevel());

		} catch (SQLException e) {
			throw new TransactionException("count not get new transaction!", e);
		}

		AbsTransaction transaction = new DefTransaction(conn);
		// 记录连接原生的事务隔离级别，后续连接释放后需要恢复连接的隔离级别
		transaction.setPreviousIsolation(previousIsolation);

		return transaction;
	}

	/**
	 * 开启新事务前的相关校验操作 检验当前线程是否已经存在事务
	 */
	protected void beforeStartTx() throws TransactionException {

		if (AbsTransactionSyncManager.checkIfExist(dataSource)) {
			throw new TransactionException("Error!Transaction is already bind to current thread!");
		}

	}

	@Override
	protected void doCommit(ITxStatus status) throws TransactionException {
		// 单纯执行事务提交操作
		ITransaction transaction = status.getTransaction();
		Connection conn = transaction.getConnection();
		try {
			conn.commit();
			logger.debug("transaction commit succeed!");
		} catch (SQLException e) {
			throw new TransactionException("doCommit failed!", e);

		}

	}

	@Override
	protected void doRollback(ITxStatus status) throws TransactionException {
		// 单纯执行回滚操作
		ITransaction transaction = status.getTransaction();
		Connection con = transaction.getConnection();

		try {
			con.rollback();
			logger.debug("transaction rollback success!");
		} catch (SQLException ex) {
			throw new TransactionException("Could not roll back JDBC transaction", ex);
		}

	}

	@Override
	protected void beforeCommit(ITxStatus status) throws TransactionException {

		// 校验状态参数是否合法

		if (status == null || status.getTransaction() == null) {
			throw new TransactionException("invalid status!Because  its transaction object is null!");

		}
		// 校验事务完成状态
		if (status.isCompleted()) {
			throw new TransactionException(
					"Transaction is already completed - do not call commit or rollback more than once per transaction");
		}

	}

	@Override
	protected void beforeRollback(ITxStatus status) {

		// 校验状态参数是否合法

		if (status == null || status.getTransaction() == null) {
			throw new TransactionException("invalid status!Because  its transaction object is null!");

		}
		// 校验事务完成状态
		if (status.isCompleted()) {
			throw new TransactionException(
					"Transaction is already completed - do not call commit or rollback more than once per transaction");
		}

	}

	@Override
	protected void afterCommit(ITxStatus status) {
		// 解绑资源
		// unbindResource(dataSource);
		// 更新状态
		// status.setCompleted(true);

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	protected void doCleanUpAfterCompletion(ITransaction transaction) {

		try {
			// 从线程中解绑资源
			AbsTransactionSyncManager.unbindResource(dataSource);
		} catch (TransactionException e) {
			// 没有可解绑的资源时会抛错误
			logger.warn(e.getMessage());

		}
		Connection conn = transaction.getConnection();

		// 重置连接
		try {

			conn.setAutoCommit(true);
			conn.setTransactionIsolation(transaction.getPreviousIsolation());
		} catch (SQLException e) {
			logger.error("Could not reset JDBC Connection after transaction", e);

		}

		// 连接归还给资源池或者关闭连接
		DbUtils.closeConn(conn);

	}

	@Override
	protected ITxStatus prepareStatus(ITxDefination defination, AbsTransaction transaction)
			throws TransactionException {
		ITxStatus status = null;
		if (transaction != null)
			status = new DefTxStatus(transaction);
		else {
			throw new TransactionException(
					"could not create a transaction status object  from a  nullable transaction!  ");

		}
		return status;
	}

}

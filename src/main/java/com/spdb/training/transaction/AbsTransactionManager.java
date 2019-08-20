package com.spdb.training.transaction;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/**
 * 
 * @author wangxg3
 *
 */
public abstract class AbsTransactionManager implements ITxManager {

	protected ILog logger = LoggerFactory.getLogger(getClass());

	private boolean rollbackOnCommitFailure = true;

	/**
	 * 定义开启一个事务的工作流
	 */
	@Override
	public ITxStatus startTransaction(ITxDefination defination) throws TransactionException {
		// 校验defination

		// 校验当前线程中是否已经存在事务型连接
		beforeStartTx();
		// 获取一个新的事务
		AbsTransaction transaction = doGetTransaction(defination);
        //生成一个事务状态对象
		ITxStatus status = prepareStatus(defination, transaction);
		// 开启事务
		doBegin(defination, transaction);
		return status;
	}

	/**
	 * 创建一个事务状态对象
	 * 
	 * @param defination
	 * @param transaction
	 */
	protected abstract ITxStatus prepareStatus(ITxDefination defination, AbsTransaction transaction)
			throws TransactionException;

	/**
	 * 提交事务的工作流
	 */
	@Override
	public void commit(ITxStatus status) throws TransactionException {
		//事务提交前的校验工作
		beforeCommit(status);

		try {
			// 提交事务
			doCommit(status);
			afterCommit(status);
		} catch (TransactionException e) {
			logger.error("commit failed!", e);
			// 判断提交失败是否执行回滚
			if (rollbackOnCommitFailure) {
				logger.warn("execute rollback on commit failure!");
				// 提交失败后回滚
				rollback(status);

			}

		} finally {
			// 成功与否，必须释放资源
			cleanUpAfterCompletion(status);
		}

	}

	/**
	 * 回滚事务的工作流
	 */
	@Override
	public void rollback(ITxStatus status) throws TransactionException {
		//回滚前校验
		beforeRollback(status);
		try {
			// 回滚事务
			
			doRollback(status);
		} catch (TransactionException e) {
			logger.error("transaction rollback failed!", e);
			throw e;
			

		} finally {
			// 释放资源
			cleanUpAfterCompletion(status);
		}

	}

	/**
	 * 执行开启一个新事务的操作, 包括把事务资源绑定到线程
	 * 
	 * @param defination
	 * @param transaction
	 */
	protected abstract void doBegin(ITxDefination defination, AbsTransaction transaction) throws TransactionException;

	/**
	 * 生成一个事务对象
	 * 
	 * @param defination
	 * @return
	 */
	protected abstract AbsTransaction doGetTransaction(ITxDefination defination) throws TransactionException;

	/**
	 * 执行提交一个事务的操作
	 * 
	 * @param status
	 */
	protected abstract void doCommit(ITxStatus status) throws TransactionException;

	/**
	 * 执行回滚一个事务的操作
	 * 
	 * @param status
	 */
	protected abstract void doRollback(ITxStatus status) throws TransactionException;

	/**
	 * 开启新事务前的相关校验和准备操作
	 */
	protected abstract void beforeStartTx() throws TransactionException;

	protected abstract void beforeCommit(ITxStatus status) throws TransactionException;

	/**
	 * 事务提交之后需要释放资源
	 * 
	 * @throws TransactionException
	 */
	protected abstract void afterCommit(ITxStatus status) throws TransactionException;

	protected abstract void beforeRollback(ITxStatus status) throws TransactionException;

	public boolean isRollbackOnCommitFailure() {
		return rollbackOnCommitFailure;
	}

	public void setRollbackOnCommitFailure(boolean rollbackOnCommitFailure) {
		this.rollbackOnCommitFailure = rollbackOnCommitFailure;
	}

	/**
	 * 一个事务完成（无论提交，回滚，或者失败）一定会执行的一个方法，不抛出错误
	 * 
	 * @param status
	 */
	private void cleanUpAfterCompletion(ITxStatus status) {
		// 更改状态
		if (status != null)
			status.setCompleted(true);
		// 其他释放资源的基础操作，恢复连接特性的基础操作
		if (status.getTransaction() != null) {
			doCleanUpAfterCompletion(status.getTransaction());
		}

	}

	/**
	 * 释放资源和恢复连接特性的基础操作
	 * 
	 * @param transaction
	 */
	protected abstract void doCleanUpAfterCompletion(ITransaction transaction);;

}

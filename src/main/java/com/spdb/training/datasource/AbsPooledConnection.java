package com.spdb.training.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/**
 * 
 * @author wangxg3
 *
 */
public abstract class AbsPooledConnection implements Connection {
	protected ILog logger = LoggerFactory.getLogger(getClass());

	protected boolean inUse = false;

	protected volatile boolean isRecycled = true;
	protected volatile boolean isReset = true;
	protected ReentrantLock lock = new ReentrantLock();
	protected final ConnHolder connHolder;

	public AbsPooledConnection(ConnHolder connHolder) {
		this.connHolder = connHolder;

	}

	public ConnHolder getConnHolder() {
		return connHolder;
	}

	/**
	 * 定义关闭连接的模板,交给连接池来处理
	 */
	@Override
	public void close() throws SQLException {
		if (isRecycled)
			return;
		lock.lock();
		try {
			if (!isRecycled) {
				connHolder.getDataSource().recycle(this);
				isRecycled = true;
			}
		} finally {
			lock.unlock();

		}

	}

	/**
	 * 如果连接被回收，则不允许被继续使用
	 * 
	 * @throws SQLException
	 */
	protected void checkStateBeforeExecute() throws SQLException {
		if (isRecycled) {
			throw new SQLException("conn is recycled!do not execute!");
		}
		lock.lock();
		try {
			if (isRecycled) {
				throw new SQLException("conn is recycled!do not execute !");
			}

		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}

	}

	@Override
	public boolean isClosed() throws SQLException {
		return isRecycled;

	}

}

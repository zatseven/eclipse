package com.spdb.training.transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import com.spdb.training.datasource.DataSourceFactory;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
/**
 * 	事务管理器工厂类
 * @author wangxg3
 *
 */
public class TxManagerFactory {
	private static ITxManager txManager = null;

	private static final ReentrantLock lock = new ReentrantLock();
	private static final ILog logger = LoggerFactory.getLogger(TxManagerFactory.class);

	/**
	 * 获取事务管理器的工厂方法
	 * 
	 * @return
	 * @throws Throwable
	 */
	public static ITxManager getTxManager() throws Throwable {
		if (txManager != null) {
			return txManager;
		}
		lock.lock();

		try {

			if (txManager == null) {
				txManager = createTxManager();
			}
			return txManager;
		} catch (IOException | SQLException e) {
			logger.error("counld not get a DataSourceTransactionManager", e);
			throw e;
		} finally {
			lock.unlock();
		}

	}

	private static ITxManager createTxManager() throws IOException, SQLException {

		return new DataSourceTransactionManager(DataSourceFactory.getDataSouce());

	}

}

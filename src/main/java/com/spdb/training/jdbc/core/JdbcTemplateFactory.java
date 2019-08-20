package com.spdb.training.jdbc.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import com.spdb.training.datasource.DataSourceFactory;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
/**
 * jdbctemplate工厂类
 * @author wangxg3
 *
 */
public class JdbcTemplateFactory {

	private static JdbcTemplate jdbcTemplate = null;
	private static final ReentrantLock lock = new ReentrantLock();
	private static final ILog logger = LoggerFactory.getLogger(JdbcTemplateFactory.class);

	/**
	 * 工厂方法获取一个jdbcTemplate实例
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static JdbcTemplate getJdbcTemplate() throws IOException, SQLException {
		if (jdbcTemplate != null) {
			return jdbcTemplate;
		}

		lock.lock();
		try {
			if (jdbcTemplate == null) {
				jdbcTemplate = createJdbcTemplate();
			}
			return jdbcTemplate;
		} catch (IOException | SQLException e) {
			logger.error("could not get a jdbcTemplate object!", e);
			throw e;

		} finally {
			lock.unlock();
		}

	}

	/**
	 * 生成一个新的jdbcTemplate对象
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	private static JdbcTemplate createJdbcTemplate() throws IOException, SQLException {
		DataSource dataSource;

		dataSource = DataSourceFactory.getDataSouce();

		JdbcTemplate jdbcTemplateTemp = new JdbcTemplate(dataSource);

		return  jdbcTemplateTemp;

	}

}

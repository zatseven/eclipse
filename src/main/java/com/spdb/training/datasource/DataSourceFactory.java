package com.spdb.training.datasource;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/**
 * 连接池工厂类
 * 
 * @author wangxg3
 *
 */
public class DataSourceFactory {

	private static ILog logger = LoggerFactory.getLogger(DataSourceFactory.class);
	private static DataSource dataSource = null;
	private static final ReentrantLock lock = new ReentrantLock();

	/**
	 * 工厂方法获取 datasource实例
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static DataSource getDataSouce() throws IOException, SQLException {
		if (dataSource != null)
			return dataSource;

		// 实例为空则新生成一个实例
		lock.lock();
		try {
			if (dataSource == null) {
				dataSource = createDataSource();

			}

			return dataSource;

		} finally {
			lock.unlock();
		}

	}

	/**
	 * 生成新的datasource对象
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	private static DataSource createDataSource() throws IOException, SQLException {

		FileInputStream fileInputStream = null;
		DefDataSource dataSourceTemp = null;

		Properties properties = new Properties();
		try {
			// 从配置文件读取连接池属性配置
			URL url = ClassLoader.getSystemResource("DataSource.properties");

			if (url != null) {
				fileInputStream = new FileInputStream(url.getPath());

			} else {
				throw new IOException("file 'DataSource.properties' does't exist!");
			}

			properties.load(fileInputStream);

			dataSourceTemp = new DefDataSource(properties);
			dataSourceTemp.init();

		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					logger.error("failed to close fileinputstream", e);
				}
			}

		}
		return dataSourceTemp;
	}

}

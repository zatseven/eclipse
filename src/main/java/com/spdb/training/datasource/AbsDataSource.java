package com.spdb.training.datasource;


import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/**
 * 
 * @author wangxg3
 *
 */
public abstract class AbsDataSource implements DataSource {
	
	protected  ILog  logger  = LoggerFactory.getLogger(getClass());

	protected PrintWriter logWriter;

	// 连接池支持的可配置属性
	protected String url;
	protected String user;
	protected String password;
	protected String driverClassName;

	protected int maxConn=DEFAULT_MAX_CONN;
	protected int maxActive=DEFAULT_MAX_ACTIVE;
	protected int initCount=DEFAULT_INIT_COUNT;
	protected int increment=DEFAULT_INCREMENT;
	
	
	//连接池初始化标志
	protected volatile boolean inited = false;

	/**
	 * 连接池生产的连接的默认属性
	 */
	protected volatile boolean defaultAutoCommit = true;
	protected volatile boolean defaultReadOnly=false;
	protected volatile Integer defaultTransactionIsolation=null;
	protected volatile String defaultCatalog = null;
	

	// 池中默认最大连接数
	protected static final int DEFAULT_MAX_CONN = 10;
	// 默认最大活跃连接数
	protected static final int DEFAULT_MAX_ACTIVE = 5;
	protected static final int DEFAULT_INIT_COUNT = 1;
	protected static final int DEFAULT_INCREMENT = 1;
	
	/**
	 * 此构造器将对参数进行默认赋值
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @param driverClassName
	 * @throws Exception
	 */
	public AbsDataSource(String url, String user, String password, String driverClassName)  {
		this.url = url;
		this.user = user;
		this.password = password;
		this.driverClassName = driverClassName;
		this.maxConn = DEFAULT_MAX_CONN;
		this.maxActive = DEFAULT_MAX_ACTIVE;
		this.initCount = DEFAULT_INIT_COUNT;
		this.increment = DEFAULT_INCREMENT;

	}
   /**
    * 无参构造器
    */
	public AbsDataSource() {

	}

	String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public int getInitCount() {
		return initCount;
	}

	public int getIncrement() {
		return increment;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		if (!inited)
			this.driverClassName = driverClassName;
	}

	public boolean getInited() {
		return inited;
	}

	public void setUrl(String url) {
		if (!inited)
			this.url = url;
	}

	public void setUser(String user) {
		if (!inited)
			this.user = user;
	}

	public void setPassword(String password) {
		if (!inited)
			this.password = password;
	}

	public void setMaxConn(int maxConn) {
		if (!inited)
			this.maxConn = maxConn;
	}

	public void setMaxActive(int maxActive) {
		if (!inited)
			this.maxActive = maxActive;
	}

	public void setInitCount(int initCount) {
		this.initCount = initCount;
	}

	public void setIncrement(int increment) {
		if (!inited)
			this.increment = increment;
	}

	public boolean isDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	public Boolean getDefaultReadOnly() {
		return defaultReadOnly;
	}

	public Integer getDefaultTransactionIsolation() {
		return defaultTransactionIsolation;
	}

	public String getDefaultCatalog() {
		return defaultCatalog;
	}

	/**
	 * 回收一个池连接
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	protected abstract void recycle(AbsPooledConnection connection) throws SQLException;

	@Override
	public PrintWriter getLogWriter() {
		return logWriter;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.logWriter = out;
	}

	@Override
	public void setLoginTimeout(int seconds) {
		DriverManager.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() {
		return DriverManager.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}

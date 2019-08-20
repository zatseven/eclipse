package com.spdb.training.datasource;

import java.sql.Connection;
import java.sql.DriverManager;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/**
 * 定义连接信息和所属的数据源
 * 
 *@author wangxg3
 *
 */
public class ConnHolder {
	protected  ILog  logger  = LoggerFactory.getLogger(getClass());

	protected final Connection conn;
	protected final AbsDataSource dataSource;
	protected int connId;

	public ConnHolder(Connection conn, Integer connId, AbsDataSource dataSource) {
		this.conn = conn;
		this.dataSource = dataSource;
		this.connId = connId;

	}

	public Connection getConn() {
		return conn;
	}

	public AbsDataSource getDataSource() {
		return dataSource;
	}

	public  int getConnId() {
		return connId;
	}
	
	
	
	

}

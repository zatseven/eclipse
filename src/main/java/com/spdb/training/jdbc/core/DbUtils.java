package com.spdb.training.jdbc.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 
 * @author wangxg3
 *
 */

import javax.sql.DataSource;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.transaction.AbsTransactionSyncManager;

public class DbUtils {

	public static ILog logger = LoggerFactory.getLogger(DbUtils.class);
	public static String url = "jdbc:mysql://localhost:3306/training";
	public static String user = "root";
	public static String passwd = "";
	static {
		// 驱动注册
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws Exception {
		Connection conn = null;
		try {

			conn = DriverManager.getConnection(url, user, passwd);
			// 判断连接是否成功
			if (conn != null) {
				System.out.println("数据库连接成功！");
				System.out.println("当前连接的数据库基本信息为：" + conn.getMetaData().getDatabaseProductVersion());
				return conn;
				// 关闭数据库连接
				// conn.close();
				// System.out.println("数据库连接已关闭");
			} else {

				throw new Exception("获取数据库连接失败！");
			}

		} catch (ClassNotFoundException e) {
			throw e;
		} catch (SQLException e) {

			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 * @return
	 */
	public static boolean closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("连接关闭失败！");
				e.printStackTrace();
				return false;

			}
		}
		return true;
	}

	/**
	 * 关闭statement
	 * 
	 * @param st
	 * @return
	 */
	public static boolean closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("statement关闭失败！");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 关闭resultset
	 * 
	 * @param rs
	 * @return
	 */
	public static boolean closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("resultset关闭失败！");
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * 从threadlocal或者datasource中获取连接
	 * 
	 * @param dataSource
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection(DataSource dataSource) throws SQLException {
		Connection conn = null;

		// 优先从线程中获取事务型连接，这样可以在同一个连接的事务内继续执行jdbc操作
		if (AbsTransactionSyncManager.checkIfExist(dataSource)) {
			logger.debug("将从线程中获取连接");
			conn = AbsTransactionSyncManager.getResource(dataSource);
			

		}
		if(conn!=null) return conn;
		
		//连接为空，则从datasource中获取一个连接
		logger.debug("将从连接池中获取连接");
		conn  = dataSource.getConnection();
		return conn;

	}
	/**
	 * 释放连接，两种释放方式，一种是归还给连接池，一种是归还给线程绑定的事务
	 * @param dataSource
	 * @param conn
	 */
	public static  void  releaseConnection(DataSource dataSource ,Connection conn) {
		
		Connection connectionInBind =AbsTransactionSyncManager.getResource(dataSource);
		
		if(conn !=null && conn==connectionInBind) {
			logger.info("连接归还给线程");
			
		}else {
			//关闭连接或者归还连接给连接池
			DbUtils.closeConn(conn);
		}
	}
}

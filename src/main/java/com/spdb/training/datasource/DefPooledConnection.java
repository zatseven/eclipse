package com.spdb.training.datasource;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;


/**
 * 
 * @author wangxg3
 *
 */
public class DefPooledConnection extends AbsPooledConnection {
	
	private final  Connection conn;
	
	

	public DefPooledConnection(ConnHolder connHolder) {
		super(connHolder);
		conn = connHolder.getConn();
	}

	@Override
	public Statement createStatement() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.prepareCall(sql);
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.nativeSQL(sql);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.setAutoCommit(autoCommit);

	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getAutoCommit();
	}

	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.commit();

	}

	@Override
	public void rollback() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.rollback();
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.isClosed();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getMetaData();
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.setReadOnly(readOnly);

	}

	@Override
	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.isReadOnly();
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.setCatalog(catalog);

	}

	@Override
	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getCatalog();
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.setTransactionIsolation(level);

	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getTransactionIsolation();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.clearWarnings();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.prepareCall(sql);
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getTypeMap();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.setTypeMap(map);

	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.setHoldability(holdability);
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getHoldability();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.setSavepoint(name);
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();

		conn.rollback(savepoint);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.releaseSavepoint(savepoint);

	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.prepareStatement(sql, autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.prepareStatement(sql, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return prepareStatement(sql, columnNames);
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createClob();
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createBlob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createSQLXML();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.isValid(timeout);
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
		conn.setClientInfo(name, value);
		;

	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
		conn.setClientInfo(properties);

	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getClientInfo(name);
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getClientInfo();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createArrayOf(typeName, elements);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.createStruct(typeName, attributes);
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.setSchema(schema);

	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.abort(executor);

	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		conn.setNetworkTimeout(executor, milliseconds);

	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.getNetworkTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		checkStateBeforeExecute();
		return conn.isWrapperFor(iface);
	}

	/**
	 * close()方法需要重写为归还给线程池
	 */
	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		super.close();

	}
	
	

}

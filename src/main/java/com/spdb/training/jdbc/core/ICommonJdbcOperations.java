package com.spdb.training.jdbc.core;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangxg3
 *
 */
public interface ICommonJdbcOperations {
	/**
	 * 执行任意类型的sql语句，使用statement
	 * 
	 * @param sql
	 */
	void execute(String sql) throws SQLException;

	/**
	 * 使用statement执行查询
	 * 
	 * @param sql
	 * @param rowMapper
	 * @return
	 */
	<T> List<T> query(String sql, IRowMapper<T> rowMapper) throws SQLException;

	/**
	 * 使用statement执行更新
	 * 
	 * @param sql
	 * @return
	 */
	int update(String sql);

	/**
	 * 使用statement批量执行sql语句
	 * 
	 * @param sql
	 * @return
	 */
	int[] batchUpdate(String[] sql) throws SQLException;

	/**
	 * 使用preparestatement进行查询,返回list
	 * 
	 * @param sql       ps的初始sql
	 * @param rowMapper 数据行映射器
	 * @param args      sql中占位符的替换对象
	 * @return
	 * @throws SQLException
	 */
	<T> List<T> query(String sql, IRowMapper<T> rowMapper, Object... args) throws SQLException;

	/**
	 * 使用preparestatement查询返回list，元素为map对象
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> query(String sql, Object... args) throws SQLException;

	/**
	 * 返回一条记录
	 * 
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return
	 * @throws SQLException
	 */

	<T> T queryForObj(String sql, IRowMapper<T> rowMapper, Object... args) throws SQLException;

	/**
	 * 返回一条记录，装载入map
	 * 
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> queryForMap(String sql, Object... args) throws SQLException;

	/**
	 * 使用ps进行更新
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	int update(String sql, Object... args) throws SQLException;

	/**
	 * 使用ps进行批量更新
	 * 
	 * @param sql
	 * @param batchArgs
	 * @return
	 */
	int[] batchUpdate(String sql, List<Object[]> batchArgs) throws SQLException;

	

}

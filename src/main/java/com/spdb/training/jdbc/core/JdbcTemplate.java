package com.spdb.training.jdbc.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/**
 * 
 * @author wangxg3
 *
 */
public class JdbcTemplate extends AbsJdbcTemplate {
	private static ILog logger = LoggerFactory.getLogger(JdbcTemplate.class);

	public JdbcTemplate(DataSource dataSource) {
		super(dataSource);

	}

	@Override
	public void execute(String sql) {
		// TODO Auto-genTerated method stub

	}

	@Override
	public <T> List<T> query(String sql, IRowMapper<T> rowMapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(String sql) {
		return 0;

	}

	@Override
	public int[] batchUpdate(String[] sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> query(String sql, IRowMapper<T> rowMapper, Object... args) throws SQLException {
		// 变量初始化
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> results = null;
		try {

			// 获取连接
			conn = DbUtils.getConnection(dataSource);
			// 准备ps
			ps = createPrepareStatement(conn, sql);

			// ps赋值
			setPrepareStatement(ps, args);

			// 执行ps查询
			rs = ps.executeQuery();
			// 提取数据
			results = extractData(rs, rowMapper);
		} finally {// 务必要释放数据库相关资源

			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(ps);
			// 连接池的交还给连接池处置
			DbUtils.releaseConnection(dataSource, conn);

		}

		// 返回查询结果
		return results;
	}

	@Override
	public List<Map<String, Object>> query(String sql, Object... args) throws SQLException {
		// 变量初始化
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> results = null;
		try {
			// 获取连接
			conn = DbUtils.getConnection(dataSource);
			// 准备ps
			ps = createPrepareStatement(conn, sql);

			// ps赋值
			setPrepareStatement(ps, args);

			// 执行ps查询
			rs = ps.executeQuery();
			// 提取数据
			results = extractData(rs, new MapRowMapper());
		} finally {// 务必要释放数据库相关资源

			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(ps);
			// 连接池的交还给连接池处置
			DbUtils.releaseConnection(dataSource, conn);

		}

		// 返回查询结果
		return results;
	}

	@Override
	public int update(String sql, Object... args) throws SQLException {
		// 变量初始化
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsAffected = 0;
		try {

			// 获取连接
			conn = DbUtils.getConnection(dataSource);
			// 准备ps
			ps = createPrepareStatement(conn, sql);
			// ps赋值
			setPrepareStatement(ps, args);
			// 执行ps
			rowsAffected = ps.executeUpdate();
		} finally {
			// 务必关闭资源
			DbUtils.closeStatement(ps);
			// 连接池的交还给连接池处置
			DbUtils.releaseConnection(dataSource, conn);
		}
		// 返回执行结果
		return rowsAffected;
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) throws SQLException {
		// 变量初始化
		Connection conn = null;
		PreparedStatement ps = null;

		int[] batchRowsAffected = null;
		try {

			// 获取连接
			conn = DbUtils.getConnection(dataSource);
			// 准备ps
			ps = createPrepareStatement(conn, sql);
			// ps赋值
			for (Object[] args : batchArgs) {
				setPrepareStatement(ps, args);
				ps.addBatch();

			}
			// 执行ps
			batchRowsAffected = ps.executeBatch();
		} catch (Exception e) {
			throw new SQLException("batch update failed!", e);
		} finally {
			// 务必关闭资源
			DbUtils.closeStatement(ps);
			// 连接池的交还给连接池处置
			DbUtils.releaseConnection(dataSource, conn);
		}

		return batchRowsAffected;
	}

	/**
	 * 根据给定sql 创建preparestatement
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement createPrepareStatement(Connection conn, String sql) throws SQLException {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			logger.error("can not create preparestatement!", e);
			DbUtils.closeStatement(ps);
			throw e;
		}

		return ps;

	}

	/**
	 * 给prepareStatement赋值
	 * 
	 * @param ps
	 * @param args
	 * @throws SQLException
	 */
	private void setPrepareStatement(PreparedStatement ps, Object... args) throws SQLException {

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				ps.setObject(i + 1, arg);

			}
		}

	}

	/**
	 * 从RS中提取数据
	 * 
	 * @param rs
	 * @param rowMapper
	 * @return
	 * @throws SQLException
	 */
	private <T> List<T> extractData(ResultSet rs, IRowMapper<T> rowMapper) throws SQLException {

		List<T> results = new ArrayList<T>();

		// 遍历RS，提取数据
		int rowNum = 0;
		while (rs.next()) {
			results.add(rowMapper.mapRow(rs, rowNum++));
		}
		return results;
	}

	@Override
	public <T> T queryForObj(String sql, IRowMapper<T> rowMapper, Object... args) throws SQLException {
		List<T> resultList = query(sql, rowMapper, args);

		return resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
	}
	
	

	@Override
	public Map<String, Object> queryForMap(String sql, Object... args) throws SQLException {
		List<Map<String, Object>> resultList = query(sql, args);

		return resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
	}

}

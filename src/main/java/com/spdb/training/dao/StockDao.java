package com.spdb.training.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.spdb.training.beans.stock.Stock;
import com.spdb.training.jdbc.core.JdbcTemplate;
import com.spdb.training.jdbc.core.JdbcTemplateFactory;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

public class StockDao {
	private static ILog logger = LoggerFactory.getLogger(StockDao.class);

	private static JdbcTemplate jdbcTemplate = null;
	static {
		try {
			jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		} catch (IOException | SQLException e) {
			logger.error("failed to get jdbctemplate obj", e);

		}

	}

	public static int Update(Stock stock) throws SQLException {
		String sql = "update Stock set qty = qty - ? where item_code = ?";
		Object[] args = { stock.getQty() , stock.getItem_code() };
		return jdbcTemplate.update(sql, args);
	}

	public static List<Map<String, Object>> queryQtyByItem_code(String code) throws SQLException {
		String sql = "select qty from  training.stock where item_code = ?";
		return jdbcTemplate.query(sql,  code);
	}
	
	public static List<Map<String, Object>> queryQtyNotZero() throws SQLException {
		String sql = "select * from  training.stock where qty > 0";
		return jdbcTemplate.query(sql);
	}
}

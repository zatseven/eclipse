package com.spdb.training.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.spdb.training.beans.order.Order;
import com.spdb.training.beans.stock.Stock;
import com.spdb.training.jdbc.core.JdbcTemplate;
import com.spdb.training.jdbc.core.JdbcTemplateFactory;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

public class OrderDao {
	private static ILog logger = LoggerFactory.getLogger(StockDao.class);

	private static JdbcTemplate jdbcTemplate = null;
	static {
		try {
			jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		} catch (IOException | SQLException e) {
			logger.error("failed to get jdbctemplate obj", e);

		}

	}

	public static int Insert(Order order) throws SQLException {
		String sql = "insert into training.order values (?,?,?,?,?,?)";
		Object[] args = { order.getOrder_id(), order.getItem_code(), order.getOrder_qty(), order.getOrder_date(),
				order.getOrder_time(), order.getOrder_user() };
		return jdbcTemplate.update(sql, args);
	}

	public static int Update(Order order) throws SQLException {
		String sql;

		sql = "update training.stock set qty = qty - ? where item_code = ?";
		Object[] args = { Integer.parseInt(order.getOrder_qty()), order.getItem_code() };
		return jdbcTemplate.update(sql, args);
	}
}

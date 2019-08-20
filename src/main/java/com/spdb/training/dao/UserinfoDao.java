package com.spdb.training.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.spdb.training.beans.user.User;
import com.spdb.training.jdbc.core.JdbcTemplate;
import com.spdb.training.jdbc.core.JdbcTemplateFactory;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.rowMapper.UserInfoRowMapper;
import com.spdb.training.socket.trans.ts03.ReqTS03ServiceBody;

public class UserinfoDao {
	private static ILog logger = LoggerFactory.getLogger(UserinfoDao.class);

	private static JdbcTemplate jdbcTemplate = null;
	static {
		try {
			jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		} catch (IOException | SQLException e) {
			logger.error("failed to get jdbctemplate obj", e);

		}

	}

	public static int insert(User userInfo) throws SQLException {
		String sql = "insert into  training.userinfo(id ,trans_seq,trans_date,name,sex,age,tel_no,credit_id)"
				+ "values(?,?,?,?,?,?,?,?)";
		Object[] args = { userInfo.getId(), userInfo.getTransSeq(), userInfo.getTransDate(), userInfo.getName(),
				userInfo.getSex(), userInfo.getAge(), userInfo.getTelNo(), userInfo.getCreditId() };
		return jdbcTemplate.update(sql, args);

	}

	public static User queryById(String id) throws SQLException {
		String sql = "select id ,trans_seq,trans_date,name,sex,age,tel_no,credit_id from  training.userinfo where id = ?";
		return jdbcTemplate.queryForObj(sql, new UserInfoRowMapper(), id);
	}
}

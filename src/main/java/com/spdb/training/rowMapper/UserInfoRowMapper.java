package com.spdb.training.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.spdb.training.beans.user.User;
import com.spdb.training.jdbc.core.IRowMapper;
import com.spdb.training.jdbc.core.MapRowMapper;
import com.spdb.training.socket.trans.ts03.ReqTS03ServiceBody;

public class UserInfoRowMapper implements IRowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// 先使用maprowmapper来完成对象化处理
		Map<String, Object> resultMap = new MapRowMapper().mapRow(rs, rowNum);

		User userinfo = new User();
		if (resultMap.containsKey("id"))
			userinfo.setId(rs.getString("id"));
		if (resultMap.containsKey("trans_seq"))
			userinfo.setTransSeq(rs.getString("trans_seq"));
		if (resultMap.containsKey("trans_date"))
			userinfo.setTransDate(rs.getString("trans_date"));
		if (resultMap.containsKey("name"))
			userinfo.setName(rs.getString("name"));
		if (resultMap.containsKey("sex"))
			userinfo.setSex(rs.getString("sex"));
		if (resultMap.containsKey("age"))
			userinfo.setAge(rs.getInt("age"));
		if (resultMap.containsKey("tel_no"))
			userinfo.setTelNo(rs.getString("tel_no"));
		if (resultMap.containsKey("credit_id"))
			userinfo.setCreditId(rs.getString("credit_id"));

		return userinfo;
	}

}

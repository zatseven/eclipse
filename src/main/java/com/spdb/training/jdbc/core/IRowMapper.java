package com.spdb.training.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 把结果集中的一行映射成一个实体对象
 * @author wangxg3
 *
 * @param <T>
 */
public interface IRowMapper<T> {

	T mapRow(ResultSet rs, int rowNum) throws SQLException;

}

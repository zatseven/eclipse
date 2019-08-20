package com.spdb.training.jdbc.core;

import javax.sql.DataSource;


/**
 * 
 * @author wangxg3
 *
 */
public abstract class AbsJdbcTemplate implements ICommonJdbcOperations {
	public DataSource dataSource;

	public AbsJdbcTemplate(DataSource dataSource) {
		this.dataSource = dataSource;

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	

}

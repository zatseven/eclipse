package com.spdb.training.transaction;

import java.sql.Connection;


/**
 * 
 * @author wangxg3
 *
 */
public class DefTransaction extends AbsTransaction {
	protected int previousIsolation;

	public DefTransaction(Connection connection) {
		super(connection);

	}

	@Override
	public void setPreviousIsolation(int isolation) {

		this.previousIsolation = isolation;

	}

	@Override
	public int getPreviousIsolation() {
		return this.previousIsolation;

	}

}

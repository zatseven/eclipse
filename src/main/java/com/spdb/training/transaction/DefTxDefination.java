package com.spdb.training.transaction;


/**
 * 
 * @author wangxg3
 *
 */
public class DefTxDefination implements ITxDefination {

	private int isolationLevel = ISOLATION_DEFAULT;

	public DefTxDefination() {
	}

	@Override
	public int getIsolationLevel() {
		// TODO Auto-generated method stub
		return this.isolationLevel;
	}

	public void setIsolationLevel(int isolationLevel) {
		this.isolationLevel = isolationLevel;
	}
}

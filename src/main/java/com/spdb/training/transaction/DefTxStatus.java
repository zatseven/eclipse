package com.spdb.training.transaction;

/**
 * 
 * @author wangxg3
 *
 */
public class DefTxStatus extends AbsTxStatus {

	/**
	 * 事务对象
	 */
	private final ITransaction transaction;

	/**
	 * 	构造器
	 * 
	 * @param transaction
	 */
	public DefTxStatus(ITransaction transaction) {
		super();
		this.transaction = transaction;
	}

	@Override
	public ITransaction getTransaction() {
		return this.transaction;
	}

}

package com.spdb.training.transaction;

public   abstract class  AbsTxStatus implements ITxStatus {

	protected boolean completed = false;

	public AbsTxStatus() {
	};

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return completed;
	}

	public  void setCompleted(boolean completed) {
		this.completed = completed;
	}

	

}

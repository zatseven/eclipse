package com.spdb.training.beans.stock;

import java.io.Serializable;

public class Stock implements Serializable{
	private static final long serialVersionUID = 1L;
	private String item_code;
	private int qty;
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
}

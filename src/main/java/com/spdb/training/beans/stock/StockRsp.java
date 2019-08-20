package com.spdb.training.beans.stock;

import java.io.Serializable;
import java.util.List;

public class StockRsp implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nums;
	private List<Stock> row;
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	public List<Stock> getRow() {
		return row;
	}
	public void setRow(List<Stock> row) {
		this.row = row;
	}
	
}

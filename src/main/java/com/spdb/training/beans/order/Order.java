package com.spdb.training.beans.order;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private int order_id;
	private String item_code;
	private String order_qty;
	private String order_date;
	private String order_time;
	private String order_user;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getOrder_qty() {
		return order_qty;
	}

	public void setOrder_qty(String order_qty) {
		this.order_qty = order_qty;
	}

	public String getOrder_date() {
		return dateFormatter.format(new Date());
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getOrder_time() {
		return timeFormatter.format(new Date());
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getOrder_user() {
		return order_user;
	}

	public void setOrder_user(String order_user) {
		this.order_user = order_user;
	}

}

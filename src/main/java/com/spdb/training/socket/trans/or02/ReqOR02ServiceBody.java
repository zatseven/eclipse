package com.spdb.training.socket.trans.or02;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月20日 下午2:35:20 
*   类说明:
*/
@XmlType(propOrder= {"item_code","order_qty","order_user"})
public class ReqOR02ServiceBody {
	
	@XmlElement(name="item_code")
	private String item_code;
	@XmlElement(name="order_qty")
	private String order_qty;
	@XmlElement(name="order_user")
	private String order_user;
	
	@XmlTransient  
	public String getitem_code() {
		return item_code;
	}
	public void setitem_code(String item_code) {
		this.item_code = item_code;
	} 

	@XmlTransient  
	public String getOrder_user() {
		return order_user;
	}

	public void setOrder_user(String order_user) {
		this.order_user = order_user;
	}
	
	@XmlTransient 
	public String getOrder_qty() {
		return order_qty;
	}
	public void setOrder_qty(String order_qty) {
		this.order_qty = order_qty;
	}
		
}

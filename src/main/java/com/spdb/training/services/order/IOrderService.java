package com.spdb.training.services.order;

import java.util.List;
import java.util.Map;

import com.spdb.training.beans.order.Order;
import com.spdb.training.beans.stock.Stock;

/**
 * 
 * @author 郭天舒
 * @version time : 2019.8.16 14:13
 * 
 */
public interface IOrderService {

	/**
	 * 类说明：秒杀商品
	 * @param  order订单参数 
	 * @return 返回结果
	 * 
	 */
	public void purchase(Order order);
}

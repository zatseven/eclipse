package com.spdb.training.services;

import com.spdb.training.beans.order.Order;
import com.spdb.training.beans.trans.TransReqContext;
import com.spdb.training.beans.trans.TransRspContext;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.services.order.OrderService;

public class OrderTransServices implements ITransServices<Order, Object>{
	ILog log = LoggerFactory.getLogger(StockTransServices.class);
	private OrderService orderService;

	@Override
	public void execute(TransReqContext<Order> reqContext, TransRspContext<Object> rspContext) {
		
		Order order = reqContext.getContext();
		
		// 获得库存操作服务类
		if (orderService == null) {
			orderService = new OrderService();
		}
		
		orderService.purchase(order);

	}
}

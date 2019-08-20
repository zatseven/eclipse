package com.spdb.training.services.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spdb.training.beans.order.Order;
import com.spdb.training.beans.stock.Stock;
import com.spdb.training.beans.user.User;
import com.spdb.training.dao.OrderDao;
import com.spdb.training.dao.StockDao;
import com.spdb.training.dao.UserinfoDao;
import com.spdb.training.exception.ExceptionHandle;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.services.stock.StockService;

public class OrderService implements IOrderService {

	StockService stockService = new StockService();
	ILog log = LoggerFactory.getLogger(OrderService.class); 
	@Override
	public void purchase(Order order) {
		try {
			int qty=stockService.queryQtyByItem_code(order.getItem_code())-Integer.parseInt(order.getOrder_qty());			
			if(qty>0)
			{
				order.setOrder_id(0);
				OrderDao.Insert(order);
				OrderDao.Update(order);
				log.info("购买成功");
			}
			else {
				log.info("商品库存不足");
			}
		} catch (SQLException e) {
			ExceptionHandle.handle(e);
		}
	}
}

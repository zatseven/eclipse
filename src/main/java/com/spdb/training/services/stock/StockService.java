package com.spdb.training.services.stock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spdb.training.beans.stock.Stock;
import com.spdb.training.beans.user.User;
import com.spdb.training.dao.StockDao;
import com.spdb.training.dao.UserinfoDao;
import com.spdb.training.exception.ExceptionHandle;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

public class StockService implements IStockService {
	ILog log=LoggerFactory.getLogger(StockService.class);
	@Override
	public int queryQtyByItem_code(String code) {
		int qty = -1;
		try {
			List<Map<String, Object>> results = StockDao.queryQtyByItem_code(code);
			for (Map<String, Object> result : results) {

				for (Map.Entry<String, Object> entry : result.entrySet()) {
					return Integer.parseInt(entry.getValue().toString());
				}
			}

		} catch (SQLException e) {
			ExceptionHandle.handle(e);
		}
		return qty;

	}

	@Override
	public int Update(Stock stock) {
		try {
			return StockDao.Update(stock);
		} catch (SQLException e) {
			ExceptionHandle.handle(e);
		}
		return 0;
	}

	@Override
	public List<Stock> queryQtyNotZero() {
		List<Stock> stocks = new ArrayList<>();
		
		int i = 0;
		
		try {
			List<Map<String, Object>> results = StockDao.queryQtyNotZero();
			for (Map<String, Object> result : results) {
				i = 1;
				Stock stock = new Stock();
				for (Map.Entry<String, Object> entry : result.entrySet()) {
					
					if (i == 1)
					{
						stock.setItem_code(entry.getValue().toString());
						i=0;
					}
						
					else
					{
						stock.setQty(Integer.parseInt(entry.getValue().toString()));
						i=1;
					}
						
				}
				stocks.add(stock);
			}
		} catch (SQLException e) {
			ExceptionHandle.handle(e);
		}
		return stocks;
	}
}

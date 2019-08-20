package com.spdb.training.services;

import java.util.List;

import com.spdb.training.beans.stock.Stock;
import com.spdb.training.beans.stock.StockRsp;
import com.spdb.training.beans.trans.TransReqContext;
import com.spdb.training.beans.trans.TransRspContext;
import com.spdb.training.beans.user.User;
import com.spdb.training.beans.user.UserRsp;
import com.spdb.training.exception.BusinessException;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.services.stock.StockService;
import com.spdb.training.services.user.UserRegisterServices;

public class StockTransServices implements ITransServices<Object, StockRsp> {
	ILog log = LoggerFactory.getLogger(StockTransServices.class);
	private StockService stockService;

	@Override
	public void execute(TransReqContext<Object> reqContext, TransRspContext<StockRsp> rspContext) {
		// 获得库存操作服务类
		if (stockService == null) {
			stockService = new StockService();
		}
        //获得库存不为零的商品信息123
		List<Stock> stocks = stockService.queryQtyNotZero();
        
		StockRsp context = rspContext.getContext();
		context.setNums(stocks.size()+"");
		context.setRow(stocks);

	}

}

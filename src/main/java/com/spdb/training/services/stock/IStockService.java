package com.spdb.training.services.stock;

import java.util.List;
import java.util.Map;

import com.spdb.training.beans.stock.Stock;

/**
 * 
 * @author 郭天舒
 * @version time : 2019.8.16 14:13
 * 
 */
public interface IStockService {

	/**
	 * 类说明：根据商品编号查询商品
	 * @param icode 商品代码
	 * @return 库存数量
	 * 
	 */
	public int queryQtyByItem_code(String code);
	
	/**
	 * 类说明：更新库存数量
	 * @param stock 商品库存对象
	 * @return 更新记录数目
	 * 
	 */
	public int Update(Stock stock);
	
	/**
	 * 类说明：查询库存数不为零的商品
	 * @return
	 */
	public List<Stock> queryQtyNotZero();
}

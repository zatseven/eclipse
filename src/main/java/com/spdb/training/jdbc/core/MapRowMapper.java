package com.spdb.training.jdbc.core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

public class MapRowMapper  implements  IRowMapper<Map<String, Object>>{
	
	private  static ILog  logger  =LoggerFactory.getLogger(MapRowMapper.class);
    /**
     * 把rs中的单行记录转换为map对象
     */
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		//获取rs元数据
		ResultSetMetaData rsmd = rs.getMetaData();
		//获取列数
		int columnCount = rsmd.getColumnCount();
		logger.debug("共有{}列",columnCount);
		//初始化一个map
		Map<String, Object> mapOfColumnValues = createMap(columnCount);
		for (int i = 1; i <= columnCount; i++) {
			//获取当前字列段名称
			String column = rsmd.getColumnLabel(i).toLowerCase();
			//以<字段名，字段值>的键值形式添加到map中
			mapOfColumnValues.putIfAbsent(column, rs.getObject(i));
			//logger.debug("当前列的类型："+column+","+rs.getObject(i).getClass().getName());
			
		}
		return mapOfColumnValues;
		
	}
	
	
	
	/**
	 * 初始化一个map
	 * @param columnCount
	 * @return
	 */
	private Map<String, Object>  createMap(int columnCount){
		
		return new LinkedHashMap<String, Object>(columnCount);
		
	}

}

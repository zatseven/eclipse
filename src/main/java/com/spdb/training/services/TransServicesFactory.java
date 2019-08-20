package com.spdb.training.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

public class TransServicesFactory {
	private static ILog log = LoggerFactory.getLogger(TransServicesFactory.class);
	@SuppressWarnings("rawtypes")
	private static Map<String, ITransServices> servicesMap = new ConcurrentHashMap<String, ITransServices>();

	@SuppressWarnings("rawtypes")
	/**
	 * 获取服务类工厂方法
	 * 
	 * @param transCode
	 * @return
	 */
	public static ITransServices getTransServices(String transCode) {
		if (servicesMap.containsKey(transCode)) {
			return servicesMap.get(transCode);
		} else {
			synchronized (servicesMap) {
				if (servicesMap.containsKey(transCode)) {
					return servicesMap.get(transCode);
				}
				log.debug("初始化服务类：[{}]", transCode);
				ITransServices services = newServices(transCode);
				servicesMap.put(transCode, services);
				return services;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	/**
	 * 实例化服务 如果使用Spring,则实例有spring初始化
	 * 
	 * @param transCode
	 * @return
	 */
	private static ITransServices newServices(String transCode) {
		// TODO
		if (TransCodeEnum.InquireStock.getTransCode().equals(transCode)) {
			return new StockTransServices();
		} else if (TransCodeEnum.PruchaseObjects.getTransCode().equals(transCode)) {
			return new OrderTransServices();
		}
		return null;
	}
}

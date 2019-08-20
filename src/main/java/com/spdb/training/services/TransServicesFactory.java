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
	 * ��ȡ�����๤������
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
				log.debug("��ʼ�������ࣺ[{}]", transCode);
				ITransServices services = newServices(transCode);
				servicesMap.put(transCode, services);
				return services;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	/**
	 * ʵ�������� ���ʹ��Spring,��ʵ����spring��ʼ��
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

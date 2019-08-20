package com.spdb.training.socket.trans.service.config;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;
import com.spdb.training.socket.trans.anno.TransCodeAnno;
import com.spdb.training.socket.trans.or01.service.TransOR01Service;
import com.spdb.training.socket.trans.or02.service.TransOR02Service;
import com.spdb.training.socket.trans.service.AbsAtomicTransService;
import com.spdb.training.socket.trans.ts03.service.TransTS03Service;

/**
 * @author 作者: 王腾蛟
 * @version time：2019年6月20日 下午3:58:59 类说明:
 * @param <T>
 */

public class InitTransService {
	/** 业务类统一放置路径 */
	private final static String SERVICES_PACKAGE_PATH = "com.spdb.training.socket.trans";

	private final static ILog LOG = LoggerFactory.getLogger(InitTransService.class);

	static ConcurrentHashMap<String, Object> services = new ConcurrentHashMap<String, Object>();

	static {
		services = addService(SERVICES_PACKAGE_PATH);
	}

	@SuppressWarnings("unchecked")
	public <T> AbsAtomicTransService<T, T> initService(String transCode) {
		System.out.println("trancode: " + transCode);
		if ("OR01".equals(transCode)) {

			return (AbsAtomicTransService<T, T>) new TransOR01Service();
		}
		if ("OR02".equals(transCode)) {
			return (AbsAtomicTransService<T, T>) new TransOR02Service();
		}
		return null;
	}

	/**
	 * 根据transCode获取service包装类
	 * 
	 * @param <T>
	 *
	 * @param servCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> AbsAtomicTransService<T, T> getService(String transCode) {
		if (services.containsKey(transCode)) {
			return (AbsAtomicTransService<T, T>) services.get(transCode);
		} else {
			synchronized (services) {
				if (services.containsKey(transCode)) {
					return (AbsAtomicTransService<T, T>) services.get(transCode);
				}
				services = addService(SERVICES_PACKAGE_PATH);
				return (AbsAtomicTransService<T, T>) services.get(transCode);
			}
		}
	}

	private static ConcurrentHashMap<String, Object> addService(String classPackagePaths) {
		try {
			ConcurrentHashMap<String, Object> services = new ConcurrentHashMap<String, Object>();
			String[] classPackagePath = classPackagePaths.split(",");
			ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
			// 1,遍历包名，获取带有注解类
			for (String packagePath : classPackagePath) {
				List<Class<?>> class1 = getClass(packagePath);
				classes.addAll(class1);
			}
			// 2,遍历所有的注解类，存放map(key, value)
			for (Class<?> class1 : classes) {
				Object newInstance = class1.newInstance();
				services.put(class1.getAnnotation(TransCodeAnno.class).value(), newInstance);
			}
			return services;

		} catch (Exception e) {
			LOG.error("加载交易类异常", e);
			throw new RuntimeException();
		}
	}

	private static List<Class<?>> getClass(String packagePath) throws ClassNotFoundException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		File directory = null;

		// 1,通过当前类加载器 获取路径目录：com/spdb/training...
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		if (contextClassLoader == null)
			throw new ClassNotFoundException("can't get class loader.");

		String path = packagePath.replace('.', '/');
		URL resource = contextClassLoader.getResource(path);
		if (resource == null)
			throw new ClassNotFoundException("No resource for " + path);

//		System.out.println(".."+resource.getPath());
		directory = new File(resource.getFile());

		// 2,判断路径是目录还是文件，将.class文件放入集合；目录则继续递归查找
		if (directory.exists()) {

			String[] files = directory.list();
			File[] listFiles = directory.listFiles();
			for (int i = 0; listFiles != null && i < listFiles.length; i++) {
				File file = listFiles[i];
				if (file.isFile() && file.getName().endsWith(".class")) {
					Class<?> clazz = Class.forName(packagePath + "." + files[i].substring(0, files[i].length() - 6));
					if (clazz.getAnnotation(TransCodeAnno.class) != null) {
						classes.add(clazz);
					}
				} else if (file.isDirectory()) {
					List<Class<?>> class1 = getClass(packagePath + "." + file.getName());
					if (class1 != null && class1.size() != 0) {
						classes.addAll(class1);
					}
				}
			}
		}
		return classes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {

		InitTransService initTransService = new InitTransService();
		AbsAtomicTransService service = initTransService.getService("TS02");
		service.execute(null, null);
	}
}

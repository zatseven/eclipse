package com.spdb.training.socket.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectCache {

	private final static ConcurrentHashMap<String, Method> methodCache = new ConcurrentHashMap<>();
	private final static ConcurrentHashMap<String, List<Field>> fieldListCache = new ConcurrentHashMap<>();
	private final static ConcurrentHashMap<String, Field> fieldCache = new ConcurrentHashMap<>();
	@SuppressWarnings("rawtypes")
	private final static ConcurrentHashMap<String, Map<Integer, ArrayList>> genericInterfaceParamsCache = new ConcurrentHashMap<>();
	@SuppressWarnings("rawtypes")
	private final static ConcurrentHashMap<String, List<Class>> genericExtendsParamsCache = new ConcurrentHashMap<>();

	/**
	 * 从缓存中获取某个类中的全部字段；如果不存在，则通过反射获取
	 * @param clazz
	 * @return
	 */
	public static List<Field> getFieldsList(Class<?> clazz){
		
		List<Field> fieldsMap = fieldListCache.get(clazz.getName());
		
		if (fieldsMap == null) {
			synchronized (fieldListCache) {
				fieldsMap = fieldListCache.get(clazz.getName());
				if (fieldsMap != null) {
					return fieldsMap;
				}
				Field[] declaredFields = clazz.getDeclaredFields();
				List<Field> fieldsArray = new ArrayList<>();
				for (Field field : declaredFields) {
					fieldsArray.add(field);
				}
				fieldListCache.put(clazz.getName(), fieldsArray);
			}
			return fieldListCache.get(clazz.getName());
		}
		return fieldsMap;
	}
	
	/**
	 * 从缓存中获取某个类中的某个字段；如果不存在，则通过反射获取
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static Field getField(Class<?> clazz, String fieldName) throws Exception{
		
		String fieldFullName = new StringBuffer(clazz.getName()).append("#").append(fieldName).toString();
		Field field = fieldCache.get(fieldFullName);
		
		if (field == null) {
			field = clazz.getDeclaredField(fieldName);
			if (field != null) {
				fieldCache.put(fieldFullName, field);
			}
		}
		return field;
	}
	
	/**
	 * 从缓存中获取类中的某个方法；如果没有此方法，再通过反射获取
	 * @param clazz
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getReflectMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes){
		
		String methodFullName = buildMethodKey(clazz.getName(), methodName, parameterTypes);
		
		Method method = methodCache.get(methodFullName);
		if (method == null) {
			synchronized (methodCache) {
				method = methodCache.get(methodFullName);
				if (method != null) {
					return method;
				}
				Method[] declaredMethods = clazz.getDeclaredMethods();
				for (Method method2 : declaredMethods) {
					String methodFullNameKey = buildMethodKey(clazz.getName(), method2.getName(), method2.getParameterTypes());
					methodCache.put(methodFullNameKey, method2);
				}
			}
			return methodCache.get(methodFullName);
		}
		return method;
	}
	
	/**
	 * 从缓存中获取该类实现接口中的泛型参数；如果不存在，则通过反射获取实现接口中的泛型类型
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<Integer, ArrayList> getGenericInterfaceParams(Class<?> clazz){
		
		Map<Integer, ArrayList> genericParamsMap = genericInterfaceParamsCache.get(clazz.getName());
		
		if (genericParamsMap == null) {
			synchronized (genericInterfaceParamsCache) {
				genericParamsMap = genericInterfaceParamsCache.get(clazz.getName());
				if (genericParamsMap != null) {
					return genericParamsMap;
				}
				Map<Integer, ArrayList> genericInterfaceType = ReflectParseUtils.getGenericInterfaceType(clazz);
				genericInterfaceParamsCache.put(clazz.getName(), genericInterfaceType);
			}
			return genericInterfaceParamsCache.get(clazz.getName());
		}
		return genericParamsMap;
	}
	
	/**
	 * 从缓存中获取该类继承父类中的泛型参数；如果不存在，则通过反射获取继承父类中的泛型类型
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> getGenericExtendsParams(Class<?> clazz){
		
		List<Class> genericParamsMap = genericExtendsParamsCache.get(clazz.getName());
		
		if (genericParamsMap == null) {
			synchronized (genericExtendsParamsCache) {
				genericParamsMap = genericExtendsParamsCache.get(clazz.getName());
				if (genericParamsMap != null) {
					return genericParamsMap;
				}
				ArrayList<Class> genericInterfaceType = ReflectParseUtils.getGenericExtendsType(clazz);
				genericExtendsParamsCache.put(clazz.getName(), genericInterfaceType);
			}
			return genericExtendsParamsCache.get(clazz.getName());
		}
		return genericParamsMap;
	}
	

	/**
	 * 针对某个一个方法，构建唯一的key值
	 * @param className
	 * @param methodName
	 * @param parameters
	 * @return
	 */
	private static String buildMethodKey(String className, String methodName, Class<?>[] parameters) {
		String paramName = "";
		if (parameters != null) {
			for (Class<?> parameter : parameters) {
				paramName += parameter.getName() + "#";
			}
		}
		return className +"#"+methodName+"#"+paramName;
	}
	
}

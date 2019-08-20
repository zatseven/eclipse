package com.spdb.training.socket.xml;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 
 * @author wangtj3
 * @version 2019年8月1日:下午2:29:19
 * @DESC:
 */
public class BeanCoperUtils {

	/**
	 * 将source中和target中相同名的属性，进行同名赋值
	 * 
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void copyProperties(Object source, Object target) throws Exception { // 后续推荐开源ReflectASM
        
		List<Field> srcFieldsList = ReflectCache.getFieldsList(source.getClass());
		List<Field> desFieldsList = ReflectCache.getFieldsList(target.getClass());
		for (Field field : srcFieldsList) {
			field.setAccessible(true);
			Object value = field.get(source);
			for (Field field2 : desFieldsList) {
				// 字段同名才赋值
				if (field.getName().equals(field2.getName())) {
					// 设置访问权限
					field2.setAccessible(true);
					if (value != null && !"".equals(value)) {
						field2.set(target, value);
						field2.setAccessible(false);
					}
				}
			}
			field.setAccessible(false);

		}

	}
}
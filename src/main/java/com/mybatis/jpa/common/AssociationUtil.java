package com.mybatis.jpa.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToOne;

/***
 * 关联性字段工具类
 * 
 * @author svili
 *
 */
public class AssociationUtil {

	public static Class<?> getTargetType(Field field) {
		if (field.isAnnotationPresent(OneToOne.class)) {
			// 获取注解对象
			OneToOne one = field.getAnnotation(OneToOne.class);
			// 设置了targetEntity属性
			if (!one.targetEntity().equals(void.class)) {
				return one.targetEntity();
			}
		}
		return void.class;
	}

	public static String getMappedName(Field field) {
		if (field.isAnnotationPresent(OneToOne.class)) {
			// 获取注解对象
			OneToOne one = field.getAnnotation(OneToOne.class);
			// 设置了mappedBy()属性
			if (!one.mappedBy().trim().equals("")) {
				return one.mappedBy();
			}
		}
		return null;
	}

	/**
	 * 获取关联性字段</br>
	 * 
	 * @param clazz
	 *            class对象
	 * @return
	 */
	public static List<Field> getAssociationFields(Class<?> clazz) {
		List<Field> list = new ArrayList<>();
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if (isAssociationField(field)) {
					list.add(field);
				}
			}
			searchType = searchType.getSuperclass();
		}
		return list;
	}

	/**
	 * 是否为关联性字段</br>
	 * javax.persistence.OneToOne注解</br>
	 * 
	 * @param field
	 *            Field对象
	 */
	public static boolean isAssociationField(Field field) {
		return field.isAnnotationPresent(OneToOne.class);
	}

}

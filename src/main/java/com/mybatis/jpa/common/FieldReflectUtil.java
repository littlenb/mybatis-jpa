package com.mybatis.jpa.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ReflectionUtils;

/**
 * Field反射工具类</br>
 * 
 * @author svili
 *
 */
public class FieldReflectUtil {

	/**
	 * 设定目标对象指定的字段值</br>
	 * oracle Number类型返回的是BigDecimal</br>
	 * mysql tinyint(1)返回的是Boolean类型
	 * 
	 * @param target
	 *            对象
	 * @param field
	 *            字段
	 * @throws Exception
	 *             IllegalArgumentException, IllegalAccessException
	 */
	public static <T> void setFieldValue(T target, Field field, Object value) throws Exception {
		ReflectionUtils.makeAccessible(field);

		// 基本数据类型
		if (field.getType().isPrimitive()) {
			// PrimitiveReflectUtil.setFieldPrimitiveValue(target, field,
			// value);
		}

		// 空值处理
		if (value == null) {
			field.set(target, null);
			return;
		}

		field.set(target, value);
	}

	/**
	 * 获取目标对象指定的字段值
	 * <p>
	 * 空值返回null
	 * </p>
	 * 
	 * @param target
	 *            对象
	 * @param field
	 *            字段
	 * @return value
	 * @throws Exception
	 *             IllegalArgumentException, IllegalAccessException
	 */
	public static <T> Object getFieldValue(T target, Field field) throws Exception {
		ReflectionUtils.makeAccessible(field);
		if (field.get(target) == null) {
			return null;
		}
		// Enum类型字段处理
		if (field.getType().isEnum()) {
			// return EnumFieldReflectUtil.getFieldEnumOrdinal(target, field);
		}
		return field.get(target);
	}

	/**
	 * 获取class类中指定注解类型的field对象
	 * 
	 * @param clazz
	 *            pojo类-class对象
	 * @param annotationType
	 *            注解类-class对象
	 * @return Field or null
	 */
	public static Field findField(Class<?> clazz, Class<? extends Annotation> annotationType) {
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(annotationType)) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * 获取所有字段</br>
	 * 暂时无用
	 * 
	 * @param clazz
	 *            class对象
	 * @return
	 */
	public static List<Field> getAllField(Class<?> clazz) {
		List<Field> list = new ArrayList<>();
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				list.add(field);
			}
			searchType = searchType.getSuperclass();
		}
		return list;
	}

}

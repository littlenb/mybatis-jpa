package com.mybatis.jpa.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.reflection.ReflectionException;

/**
 * 持久化工具类
 * 
 * @author svili
 *
 */
public class PersistentUtil {

	/**
	 * 驼峰_下划线转换,默认开启</br>
	 * 对{@Table}注解无效,支持ClassName_TableName</br>
	 * 对{@Column}注解无效,支持fieldName_columnName</br>
	 */
	private static final boolean CAMEL_TO_UNDERLINE = true;

	/**
	 * 获取Java对象对应的表名</br>
	 * 默认下划线风格
	 * 
	 * @param clazz
	 *            pojo类class对象
	 * @return tableName
	 */
	public static String getTableName(Class<?> clazz) {
		// 判断是否有Table注解
		if (clazz.isAnnotationPresent(Table.class)) {
			// 获取注解对象
			Table table = clazz.getAnnotation(Table.class);
			// 设置了name属性
			if (!table.name().trim().equals("")) {
				return table.name();
			}
		}
		// 类名
		String className = clazz.getSimpleName();

		if (!CAMEL_TO_UNDERLINE) {
			return className;
		} else {
			// 驼峰转下划线
			return ColumnNameUtil.camelToUnderline(className);
		}
	}
	
	public static String getEntityName(Class<?> type) {
//		if (!type.isAnnotationPresent(Entity.class)) {
//			throw new RuntimeException("Not found annotation with Entity in Type." + type.getName());
//		}
		if (type.isAnnotationPresent(Entity.class)) {
			// 获取注解对象
			Entity entity = type.getAnnotation(Entity.class);
			// 设置了mappedBy()属性
			if (!entity.name().trim().equals("")) {
				return entity.name();
			}
		}
		return type.getSimpleName();
	}

	/**
	 * 获取列名</br>
	 * 注解优先，javax.persistence.Column name属性值。</br>
	 * 无注解,将字段名转为字符串,默认下划线风格.</br>
	 * 
	 * @param field
	 *            pojo字段对象
	 * @return
	 */
	public static String getColumnName(Field field) {
		if (field.isAnnotationPresent(javax.persistence.Column.class)) {
			// 获取注解对象
			Column column = field.getAnnotation(Column.class);
			// 设置了name属性
			if (!column.name().trim().equals("")) {
				return column.name().toUpperCase();
			}
		}
		if (!CAMEL_TO_UNDERLINE) {
			return field.getName();
		} else {
			return ColumnNameUtil.camelToUnderline(field.getName());
		}
	}

	/**
	 * 获取pojo主键字段</br>
	 * javax.persistence.Id注解的字段不存在返回null
	 * 
	 * @param clazz
	 *            pojo类-class对象
	 * @return Field
	 */
	public static Field getPrimaryFieldNotCareNull(Class<?> clazz) {
		Field field = FieldReflectUtil.findField(clazz, Id.class);
		if (field != null) {
			return field;
		} else {
			return null;
		}
	}

	/**
	 * 获取pojo主键字段</br>
	 * 
	 * 主键必须存在，不存在抛异常
	 * 
	 * @param clazz
	 *            pojo类-class对象
	 * @return Field
	 * @throws ReflectionExceptio
	 * @see org.apache.ibatis.reflection.ReflectionException
	 */
	public static Field getPrimaryField(Class<?> clazz) {
		Field field = getPrimaryFieldNotCareNull(clazz);
		if (field != null) {
			return field;
		} else {
			throw new ReflectionException(
					"no search result for javax.persistence.Id annotation from " + clazz.getName());
		}
	}

	/**
	 * 获取pojo主键列名</br>
	 * 
	 * @param clazz
	 *            pojo类-class对象
	 * @return underline-columnName
	 * @throws ReflectionExceptio
	 * @see org.apache.ibatis.reflection.ReflectionException
	 */
	public static String getPrimaryKey(Class<?> clazz) {
		Field primaryField = getPrimaryField(clazz);
		return getColumnName(primaryField);
	}

	/**
	 * 根据列名获取字段</br>
	 * 无匹配的字段抛异常
	 * 
	 * @param clazz
	 *            pojo类class对象
	 * @param columnName
	 *            列名
	 * @return
	 */
	public static Field getFieldByColumnName(Class<?> clazz, String columnName) {
		Map<String, Field> mapping = getColumnFieldMapping(clazz);
		Field field = mapping.get(clazz.getName() + "." + columnName);
		if (field == null) {
			throw new ReflectionException(
					"no search matched field to columnName :" + columnName + " from " + clazz.getName());
		}
		return field;
	}

	/**
	 * 列名-字段Mapping</br>
	 * key = clazz.getName() + "." + columnName</br>
	 * value = Field</br>
	 * 可以将此方法的返回结果存储到容器中</br>
	 * 
	 * @param clazz
	 *            pojo类class对象
	 * @return
	 */
	public static Map<String, Field> getColumnFieldMapping(Class<?> clazz) {
		List<Field> fieldList = getPersistentFields(clazz);
		if (fieldList == null || fieldList.isEmpty()) {
			return null;
		}
		Map<String, Field> mapping = new HashMap<>();
		String className = clazz.getName();
		for (Field field : fieldList) {
			mapping.put(className + "." + getColumnName(field), field);
		}
		return mapping;
	}

	/**
	 * 获取持久化字段</br>
	 * 可以将此方法的返回结果存储到容器中</br>
	 * 
	 * @param clazz
	 *            class对象
	 * @return
	 */
	public static List<Field> getPersistentFields(Class<?> clazz) {
		List<Field> list = new ArrayList<>();
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if (isPersistentField(field)) {
					list.add(field);
				}
			}
			searchType = searchType.getSuperclass();
		}
		return list;
	}

	/**
	 * 是否为持久化字段</br>
	 * javax.persistence.Transient注解为非持久化字段</br>
	 * 
	 * @param field
	 *            Field对象
	 * @return
	 */
	public static boolean isPersistentField(Field field) {
		return !field.isAnnotationPresent(Transient.class);
	}
}

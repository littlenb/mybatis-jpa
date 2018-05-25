package com.mybatis.jpa.meta;

import java.lang.reflect.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.type.BooleanTypeHandler;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.mybatis.jpa.common.PersistentUtil;

/**
 * mybatis column元数据类型
 * 
 * @author svili
 *
 */
public class MybatisColumnMeta {

	/** 是否为主键 */
	private boolean primaryKey;

	/** Java fieldName */
	private String property;

	private String columnName;

	/** fieldType */
	private Class<?> type;

	/** mybatis jdbcTypeAlias */
	private String jdbcTypeAlias;

	/** mybatis jdbcType */
	private JdbcType jdbcType;

	/** mybatis typeHandler */
	private Class<? extends TypeHandler<?>> typeHandlerClass;

	/** 持久化字段 */
	private Field field;

	public MybatisColumnMeta(Field field) {
		this.field = field;
		// 初始化
		this.property = field.getName();
		this.columnName = PersistentUtil.getColumnName(field);
		this.type = field.getType();
		this.jdbcTypeAlias = ColumnMetaResolver.resolveJdbcAlias(field);
		this.jdbcType = ColumnMetaResolver.resolveJdbcType(this.jdbcTypeAlias);
		this.typeHandlerClass = ColumnMetaResolver.resolveTypeHandler(field);
	}

	/** meta resolver */
	private static class ColumnMetaResolver {

		public static String resolveJdbcAlias(Field field) {

			Class<?> fieldType = field.getType();
			if (field.getType().isEnum()) {
				if (field.isAnnotationPresent(Enumerated.class)) {
					// 获取注解对象
					Enumerated enumerated = field.getAnnotation(Enumerated.class);
					// 设置了value属性
					if (enumerated.value() == EnumType.ORDINAL) {
						return "INTEGER";
					}
				}
				return "VARCHAR";
			}
			if (field.isAnnotationPresent(Lob.class)) {
				if (String.class.equals(fieldType)) {
					return "CLOB";
				}
			}
			if (Integer.class.equals(fieldType)) {
				return "INTEGER";
			}
			if (Double.class.equals(fieldType)) {
				return "DOUBLE";
			}
			if (Float.class.equals(fieldType)) {
				return "FLOAT";
			}
			if (String.class.equals(fieldType)) {
				return "VARCHAR";
			}
			// date类型需声明
			if (java.util.Date.class.isAssignableFrom(fieldType)) {
				return "TIMESTAMP";
			}
			return null;
		}

		public static JdbcType resolveJdbcType(String alias) {
			if (alias == null) {
				return null;
			}
			try {
				return JdbcType.valueOf(alias);
			} catch (IllegalArgumentException e) {
				throw new BuilderException("Error resolving JdbcType. Cause: " + e, e);
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Class<? extends TypeHandler<?>> resolveTypeHandler(Field field) {

			if (field.getType().isEnum()) {
				if (field.isAnnotationPresent(Enumerated.class)) {
					// 获取注解对象
					Enumerated enumerated = field.getAnnotation(Enumerated.class);
					// 设置了value属性
					if (enumerated.value() == EnumType.ORDINAL) {
						EnumOrdinalTypeHandler<? extends Enum<?>> typeHandler = new EnumOrdinalTypeHandler(
								field.getClass());
						return (Class<? extends TypeHandler<?>>) typeHandler.getClass();
						// return (Class<? extends TypeHandler<?>>)
						// EnumOrdinalTypeHandler.class;
					}
				}
				EnumTypeHandler<? extends Enum<?>> typeHandler = new EnumTypeHandler(field.getClass());
				return (Class<? extends TypeHandler<?>>) typeHandler.getClass();
				// return (Class<? extends TypeHandler<?>>)
				// EnumTypeHandler.class;
			}

			Class<? extends TypeHandler<?>> typeHandlerClass = null;
			if (field.getType().equals(Boolean.class)) {
				typeHandlerClass = (Class<? extends TypeHandler<?>>) BooleanTypeHandler.class;
			}
			return typeHandlerClass;
		}
	}

	// getter
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public String getProperty() {
		return property;
	}

	public String getColumnName() {
		return columnName;
	}

	public Class<?> getType() {
		return type;
	}

	public String getJdbcTypeAlias() {
		return jdbcTypeAlias;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public Class<? extends TypeHandler<?>> getTypeHandlerClass() {
		return typeHandlerClass;
	}

	public Field getField() {
		return field;
	}

}

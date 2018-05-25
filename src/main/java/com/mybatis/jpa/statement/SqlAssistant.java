package com.mybatis.jpa.statement;

import java.lang.reflect.Method;

import org.springframework.util.StringUtils;

import com.mybatis.jpa.meta.MybatisColumnMeta;
import com.mybatis.jpa.meta.PersistentMeta;
import com.mybatis.jpa.type.MethodConstants;
import com.mybatis.jpa.type.OperateEnum;

/**
 * SQL解析工具类</br>
 * 
 * @author svili
 *
 */
public class SqlAssistant {

	private SqlAssistant() {
	}

	/** 解析method的where条件,仅支持单一字段条件,如果没有where语句,返回空字符串"" **/
	public static String buildSingleCondition(Method method, PersistentMeta persistentMeta) {
		String methodType = resolveMethodType(method.getName());
		if (method.getName().equals(methodType)) {
			return "";
		}
		// 清除methodName中的methodType 和 By :expression包含
		// 条件字段(FieldName)、操作符类型(Like)
		String expression = method.getName().substring(methodType.length() + 2);
		OperateEnum operate = resolveOperate(expression);
		// 首字母小写
		String fieldName = StringUtils.uncapitalize(expression.replace(operate.getAlias(), ""));
		MybatisColumnMeta columnMeta;
		if (fieldName.equals("id")) {
			columnMeta = persistentMeta.getPrimaryColumnMeta();
		} else {
			columnMeta = persistentMeta.getColumnMetaMap().get(fieldName);
		}
		if (operate.equals(OperateEnum.ISNULL) || operate.equals(OperateEnum.NOTNULL)) {
			return " WHERE " + columnMeta.getColumnName() + operate.getOperate();
		} else {
			return " WHERE " + columnMeta.getColumnName() + operate.getOperate() + resolveSqlParameter(columnMeta);
		}
	}

	/** 识别{@link MethodConstants}中定义的methodType */
	public static String resolveMethodType(String methodName) {
		// 注意顺序 insert insertSelective,insert应放在后面判断
		if (methodName.startsWith(MethodConstants.INSERT_SELECTIVE)) {
			return MethodConstants.INSERT_SELECTIVE;
		}

		if (methodName.startsWith(MethodConstants.INSERT)) {
			return MethodConstants.INSERT;
		}

		if (methodName.startsWith(MethodConstants.BATCH_INSERT)) {
			return MethodConstants.BATCH_INSERT;
		}

		if (methodName.startsWith(MethodConstants.DELETE)) {
			return MethodConstants.DELETE;
		}

		if (methodName.startsWith(MethodConstants.UPDATE_SELECTIVE)) {
			return MethodConstants.UPDATE_SELECTIVE;
		}

		if (methodName.startsWith(MethodConstants.UPDATE)) {
			return MethodConstants.UPDATE;
		}

		if (methodName.startsWith(MethodConstants.BATCH_UPDATE)) {
			return MethodConstants.BATCH_UPDATE;
		}

		if (methodName.startsWith(MethodConstants.SELECT_PAGE)) {
			return MethodConstants.SELECT_PAGE;
		}

		if (methodName.startsWith(MethodConstants.SELECT)) {
			return MethodConstants.SELECT;
		}

		// throw e
		return null;
	}

	/** 识别@{link OperateEnum}定义的条件操作符 */
	private static OperateEnum resolveOperate(String expression) {
		OperateEnum[] enums = OperateEnum.values();
		for (OperateEnum operate : enums) {
			if (expression.endsWith(operate.getAlias())) {
				return operate;
			}
		}
		return OperateEnum.EQUAL;
	}

	/** 装配sql中动态参数的占位符 #{paramterName,jdbcType=,typeHandler=} */
	public final static String resolveSqlParameter(MybatisColumnMeta columnMeta) {

		return resolveSqlParameter(columnMeta, "");
	}

	/** 装配sql中动态参数的占位符 #{alias.paramterName,jdbcType=,typeHandler=} */
	public final static String resolveSqlParameter(MybatisColumnMeta columnMeta, String alias) {
		String sqlParameter = "#{";
		if (alias != null && !"".equals(alias)) {
			sqlParameter += alias + ".";
		}
		sqlParameter += columnMeta.getProperty();

		// jdbcType
		if (columnMeta.getJdbcTypeAlias() != null) {
			sqlParameter += ", jdbcType=" + columnMeta.getJdbcTypeAlias();
		}
		// typeHandler
		if (columnMeta.getTypeHandlerClass() != null) {
			sqlParameter += ", typeHandler=" + columnMeta.getTypeHandlerClass().getName();
		}
		sqlParameter += "} ";

		return sqlParameter;
	}

}

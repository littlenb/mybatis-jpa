package com.mybatis.jpa.statement;

import java.util.HashMap;
import java.util.Map;

import com.mybatis.jpa.statement.builder.BatchInsertBuilder;
import com.mybatis.jpa.statement.builder.BatchUpdateBuilder;
import com.mybatis.jpa.statement.builder.DeleteBuilder;
import com.mybatis.jpa.statement.builder.InsertBuilder;
import com.mybatis.jpa.statement.builder.InsertSelectiveBuilder;
import com.mybatis.jpa.statement.builder.SelectBuilder;
import com.mybatis.jpa.statement.builder.StatementBuildable;
import com.mybatis.jpa.statement.builder.UpdateBuilder;
import com.mybatis.jpa.statement.builder.UpdateSelectiveBuilder;
import com.mybatis.jpa.type.MethodConstants;

/**
 * statementBuider holder and adapter</br>
 * holder : ensure every {@link StatementBuildable} is singleton</br>
 * adapter: {@link #adapted(String)}
 * 
 * @author svili
 *
 */
public class StatementBuilderHolder {

	/** key is methodType @{link MethodConstants} */
	private static Map<String, StatementBuildable> builderMap;

	/** 私有化构造方法,不允许创建实例对象 */
	private StatementBuilderHolder() {
	}

	static {
		// ensure every {@link StatementBuildable} is singleton
		builderMap = new HashMap<String, StatementBuildable>();
		builderMap.put(MethodConstants.INSERT_SELECTIVE, new InsertSelectiveBuilder());
		builderMap.put(MethodConstants.INSERT, new InsertBuilder());
		builderMap.put(MethodConstants.BATCH_INSERT, new BatchInsertBuilder());
		builderMap.put(MethodConstants.DELETE, new DeleteBuilder());
		builderMap.put(MethodConstants.UPDATE_SELECTIVE, new UpdateSelectiveBuilder());
		builderMap.put(MethodConstants.UPDATE, new UpdateBuilder());
		builderMap.put(MethodConstants.BATCH_UPDATE, new BatchUpdateBuilder());
		builderMap.put(MethodConstants.SELECT, new SelectBuilder());
	}

	/**
	 * adapted StatementBuilder whith methodType @{link MethodConstants}
	 */
	public static StatementBuildable adapted(String methodType) {
		// 可使用反射获取常量字段集遍历
		if (MethodConstants.INSERT_SELECTIVE.equals(methodType)) {
			return builderMap.get(MethodConstants.INSERT_SELECTIVE);
		}
		if (MethodConstants.INSERT.equals(methodType)) {
			return builderMap.get(MethodConstants.INSERT);
		}
		if (MethodConstants.BATCH_INSERT.equals(methodType)) {
			return builderMap.get(MethodConstants.BATCH_INSERT);
		}
		if (MethodConstants.DELETE.equals(methodType)) {
			return builderMap.get(MethodConstants.DELETE);
		}
		if (MethodConstants.UPDATE_SELECTIVE.equals(methodType)) {
			return builderMap.get(MethodConstants.UPDATE_SELECTIVE);
		}
		if (MethodConstants.UPDATE.equals(methodType)) {
			return builderMap.get(MethodConstants.UPDATE);
		}
		if (MethodConstants.BATCH_UPDATE.equals(methodType)) {
			return builderMap.get(MethodConstants.BATCH_UPDATE);
		}
		if (MethodConstants.SELECT_PAGE.equals(methodType)) {
			return builderMap.get(MethodConstants.SELECT);
		}
		if (MethodConstants.SELECT.equals(methodType)) {
			return builderMap.get(MethodConstants.SELECT);
		}
		// throw exception
		return null;
	}
}

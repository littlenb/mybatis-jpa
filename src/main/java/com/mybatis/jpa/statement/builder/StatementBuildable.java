package com.mybatis.jpa.statement.builder;

import java.lang.reflect.Method;

import com.mybatis.jpa.meta.PersistentMeta;
import com.mybatis.jpa.statement.MybatisStatementAdapter;

/**
 * mybatis statement buider 接口
 * 
 * @author svili
 *
 */
public interface StatementBuildable {

	/** sql */
	String buildSQL(PersistentMeta persistentMeta, Method method);

	/**
	 * 创建并注册Mybatis Statement
	 * 
	 * @param adapter
	 *            Adapter for {@see MappedStatement}
	 * @param persistentMeta
	 *            Entity持久化元数据
	 * @param method
	 *            mapper.method
	 */
	void parseStatement(MybatisStatementAdapter adapter, PersistentMeta persistentMeta, Method method);

}

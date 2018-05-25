package com.mybatis.jpa.statement.builder;

import java.lang.reflect.Method;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;

import com.mybatis.jpa.meta.MybatisColumnMeta;
import com.mybatis.jpa.meta.PersistentMeta;
import com.mybatis.jpa.statement.MybatisStatementAdapter;
import com.mybatis.jpa.statement.SqlAssistant;

public class UpdateSelectiveBuilder implements StatementBuildable {

	@Override
	public String buildSQL(PersistentMeta persistentMeta, Method method) {
		// columns
		StringBuilder sets = new StringBuilder();
		sets.append("<trim prefix='' suffix='' suffixOverrides=',' > ");
		for (MybatisColumnMeta columnMeta : persistentMeta.getColumnMetaMap().values()) {

			sets.append("<if test='" + columnMeta.getProperty() + "!= null'> ");
			// columnName = #{ }
			sets.append(columnMeta.getColumnName()).append(" = ").append(SqlAssistant.resolveSqlParameter(columnMeta))
					.append(" , ");
			sets.append("</if> ");
		}

		sets.append("</trim> ");

		return "<script>" + "UPDATE " + persistentMeta.getTableName() + " set " + sets.toString()
				+ SqlAssistant.buildSingleCondition(method, persistentMeta) + "</script>";
	}

	@Override
	public void parseStatement(MybatisStatementAdapter adapter, PersistentMeta persistentMeta, Method method) {
		// 方法名
		adapter.setMethodName(method.getName());
		// 参数类型
		adapter.setParameterTypeClass(persistentMeta.getType());
		// sqlScript
		adapter.setSqlScript(buildSQL(persistentMeta, method));
		// 返回值类型
		adapter.setResultType(int.class);
		adapter.setResultMapId(null);

		adapter.setSqlCommandType(SqlCommandType.UPDATE);

		// 主键策略
		adapter.setKeyGenerator(new NoKeyGenerator());
		adapter.setKeyProperty(null);
		adapter.setKeyColumn(null);

		adapter.parseStatement();

	}

}

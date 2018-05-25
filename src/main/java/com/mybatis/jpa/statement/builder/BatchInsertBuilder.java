package com.mybatis.jpa.statement.builder;

import java.lang.reflect.Method;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;

import com.mybatis.jpa.meta.MybatisColumnMeta;
import com.mybatis.jpa.meta.PersistentMeta;
import com.mybatis.jpa.statement.MybatisStatementAdapter;
import com.mybatis.jpa.statement.SqlAssistant;

public class BatchInsertBuilder implements StatementBuildable {

	@Override
	public String buildSQL(PersistentMeta persistentMeta, Method method) {
		StringBuilder values = new StringBuilder();

		for (MybatisColumnMeta columnMeta : persistentMeta.getColumnMetaMap().values()) {
			if (values.length() > 0) {
				values.append(",");
			}
			values.append(SqlAssistant.resolveSqlParameter(columnMeta, "rowData"));
		}

		return "<script>" + " INSERT INTO " + persistentMeta.getTableName() + " (" + persistentMeta.getColumnNames()
				+ " ) " + " VALUES " + "<foreach item='rowData' index='rowIndex' collection='list' separator=','>"
				+ "( " + values.toString() + " )" + "</foreach>" + "</script>";
	}

	@Override
	public void parseStatement(MybatisStatementAdapter adapter, PersistentMeta persistentMeta, Method method) {
		// 方法名
		adapter.setMethodName(method.getName());
		// 参数类型
		adapter.setParameterTypeClass(persistentMeta.getClass());
		// sqlScript
		adapter.setSqlScript(buildSQL(persistentMeta, method));
		// 返回值类型
		adapter.setResultType(int.class);
		adapter.setResultMapId(null);

		adapter.setSqlCommandType(SqlCommandType.INSERT);

		// 主键策略
		adapter.setKeyGenerator(new NoKeyGenerator());
		adapter.setKeyProperty(null);
		adapter.setKeyColumn(null);

		adapter.parseStatement();

	}

}

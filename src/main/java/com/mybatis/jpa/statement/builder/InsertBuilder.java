package com.mybatis.jpa.statement.builder;

import java.lang.reflect.Method;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.SqlCommandType;

import com.mybatis.jpa.meta.MybatisColumnMeta;
import com.mybatis.jpa.meta.PersistentMeta;
import com.mybatis.jpa.statement.MybatisStatementAdapter;
import com.mybatis.jpa.statement.SqlAssistant;

public class InsertBuilder implements StatementBuildable {

	@Override
	public String buildSQL(final PersistentMeta persistentMeta, Method method) {
		return new SQL() {
			{
				INSERT_INTO(persistentMeta.getTableName());
				for (MybatisColumnMeta columnMeta : persistentMeta.getColumnMetaMap().values()) {
					VALUES(columnMeta.getColumnName(), SqlAssistant.resolveSqlParameter(columnMeta));
				}
			}
		}.toString();
	}

	@Override
	public void parseStatement(MybatisStatementAdapter adapter, PersistentMeta persistentMeta, Method method) {
		// 方法名
		adapter.setMethodName("insert");
		// 参数类型
		adapter.setParameterTypeClass(persistentMeta.getType());
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

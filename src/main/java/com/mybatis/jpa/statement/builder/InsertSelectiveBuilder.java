package com.mybatis.jpa.statement.builder;

import java.lang.reflect.Method;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;

import com.mybatis.jpa.meta.MybatisColumnMeta;
import com.mybatis.jpa.meta.PersistentMeta;
import com.mybatis.jpa.statement.MybatisStatementAdapter;
import com.mybatis.jpa.statement.SqlAssistant;

public class InsertSelectiveBuilder implements StatementBuildable {

	@Override
	public String buildSQL(PersistentMeta persistentMeta, Method method) {
		// columns
		StringBuilder columns = new StringBuilder();
		columns.append("<trim prefix='(' suffix=')' suffixOverrides=',' > ");
		// values
		StringBuilder values = new StringBuilder();
		values.append("<trim prefix='(' suffix=')' suffixOverrides=',' > ");
		for (MybatisColumnMeta columnMeta : persistentMeta.getColumnMetaMap().values()) {
			// columns
			columns.append("<if test='" + columnMeta.getProperty() + "!= null'> ");
			columns.append(columnMeta.getColumnName() + ", ");
			columns.append("</if> ");
			// values
			values.append("<if test='" + columnMeta.getProperty() + "!= null'> ");
			values.append(SqlAssistant.resolveSqlParameter(columnMeta) + ", ");
			values.append("</if> ");
		}

		columns.append("</trim> ");
		values.append("</trim> ");

		return "<script>" + "INSERT INTO " + persistentMeta.getTableName() + columns.toString() + " VALUES "
				+ values.toString() + "</script>";
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

		adapter.setSqlCommandType(SqlCommandType.INSERT);

		// 主键策略
		adapter.setKeyGenerator(new NoKeyGenerator());
		adapter.setKeyProperty(null);
		adapter.setKeyColumn(null);

		adapter.parseStatement();

	}

}

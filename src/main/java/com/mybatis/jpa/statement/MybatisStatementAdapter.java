package com.mybatis.jpa.statement;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;

/**
 * mybatis statement adapter</br>
 * 调用parseStatement()方法,创建/注册statement对象</br>
 * 
 * @author svili
 *
 */
public class MybatisStatementAdapter {

	/** mybatis assistant */
	private MapperBuilderAssistant assistant;

	private LanguageDriver languageDriver;

	/** 方法名 */
	private String methodName;

	/** 参数类型 mybatis dao 只能有一个参数,多个参数请使用map封装 */
	private Class<?> parameterTypeClass;

	/** sql表达式,动态sql需使用<script>标签装饰:<script>dynamicSql</script> */
	private String sqlScript;

	/** default null */
	private Integer fetchSize;
	/** default null */
	private Integer timeout;

	/** default StatementType.PREPARED */
	private StatementType statementType;
	/** default ResultSetType.FORWARD_ONLY */
	private ResultSetType resultSetType;

	/** insert / update / delete /select */
	private SqlCommandType sqlCommandType;

	/** 主键策略 */
	private KeyGenerator keyGenerator;
	private String keyProperty;
	private String keyColumn;

	/** resultMap default : currentNamespace + "." + methodName */
	String resultMapId;
	/** 方法返回值类型 */
	private Class<?> resultType;

	protected MybatisStatementAdapter() {
	}

	public MybatisStatementAdapter(MapperBuilderAssistant assistant) {
		this.assistant = assistant;

		/** 初始化默认参数 **/
		this.initParameters();
	}

	/** 初始化默认参数 */
	private void initParameters() {
		this.languageDriver = assistant.getLanguageDriver(null);
		// dynamic & has parameters
		this.statementType = StatementType.PREPARED;
		// unknow
		this.resultSetType = ResultSetType.FORWARD_ONLY;
		// 无主键策略
		// this.keyGenerator = new NoKeyGenerator();
	}

	/** 创建mybatis statement,并向configuration中注册 */
	public final void parseStatement() {
		SqlSource sqlSource = this.buildSqlSource(sqlScript, parameterTypeClass);
		if (sqlSource != null) {

			// Options options = null;
			final String mappedStatementId = this.getMappedStatementId();

			boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
			boolean flushCache = !isSelect;
			boolean useCache = isSelect;

			assistant.addMappedStatement(mappedStatementId, sqlSource, statementType, sqlCommandType, fetchSize,
					timeout,
					// ParameterMapID
					null, parameterTypeClass, resultMapId, resultType, resultSetType, flushCache, useCache,
					// TODO gcode issue #577
					false, keyGenerator, keyProperty, keyColumn,
					// DatabaseID
					null, languageDriver,
					// ResultSets
					null);
		}
	}

	/**
	 * 调用此方法前,需设置assistant.currentNamespace 和 methodName</br>
	 * 若以上两个参数有一个为空(null or empty),则throw RunTimeException
	 * 
	 * @return currentNamespace + "." + methodName
	 */
	private String getMappedStatementId() {
		String currentNamespace = this.assistant.getCurrentNamespace();

		if (currentNamespace == null || currentNamespace.trim().equals("")) {
			// throw
		}

		if (this.methodName == null || this.methodName.trim().equals("")) {
			// throw
		}

		return currentNamespace + "." + this.methodName;
	}

	/** 创建mybatis SqlSource */
	private SqlSource buildSqlSource(String sqlScript, Class<?> parameterTypeClass, LanguageDriver languageDriver) {
		return languageDriver.createSqlSource(this.assistant.getConfiguration(), sqlScript, parameterTypeClass);
	}

	/** 创建mybatis SqlSource */
	private SqlSource buildSqlSource(String sqlScript, Class<?> parameterTypeClass) {
		return buildSqlSource(sqlScript, parameterTypeClass, this.languageDriver);
	}

	public static class Builder {
		private MybatisStatementAdapter adapter = new MybatisStatementAdapter();

		public Builder assistant(MapperBuilderAssistant assistant) {
			this.adapter.assistant = assistant;
			return this;
		}

		public Builder languageDriver(LanguageDriver languageDriver) {
			this.adapter.languageDriver = languageDriver;
			return this;
		}

		public Builder methodName(String methodName) {
			this.adapter.methodName = methodName;
			return this;
		}

		public Builder parameterTypeClass(Class<?> parameterTypeClass) {
			this.adapter.parameterTypeClass = parameterTypeClass;
			return this;
		}

		public Builder sqlScript(String sqlScript) {
			this.adapter.sqlScript = sqlScript;
			return this;
		}

		public Builder fetchSize(Integer fetchSize) {
			this.adapter.fetchSize = fetchSize;
			return this;
		}

		public Builder methodName(Integer timeout) {
			this.adapter.timeout = timeout;
			return this;
		}

		public Builder statementType(StatementType statementType) {
			this.adapter.statementType = statementType;
			return this;
		}

		public Builder sqlCommandType(SqlCommandType sqlCommandType) {
			this.adapter.sqlCommandType = sqlCommandType;
			return this;
		}

		public Builder keyGenerator(KeyGenerator keyGenerator) {
			this.adapter.keyGenerator = keyGenerator;
			return this;
		}

		public Builder keyProperty(String keyProperty) {
			this.adapter.keyProperty = keyProperty;
			return this;
		}

		public Builder keyColumn(String keyColumn) {
			this.adapter.keyColumn = keyColumn;
			return this;
		}

		public Builder resultMapId(String resultMapId) {
			this.adapter.resultMapId = resultMapId;
			return this;
		}

		public Builder resultType(Class<?> resultType) {
			this.adapter.resultType = resultType;
			return this;
		}

		public MybatisStatementAdapter build() {
			return this.adapter;
		}
	}

	// getter and setter
	public MapperBuilderAssistant getAssistant() {
		return assistant;
	}

	public void setAssistant(MapperBuilderAssistant assistant) {
		this.assistant = assistant;
	}

	public LanguageDriver getLanguageDriver() {
		return languageDriver;
	}

	public void setLanguageDriver(LanguageDriver languageDriver) {
		this.languageDriver = languageDriver;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?> getParameterTypeClass() {
		return parameterTypeClass;
	}

	public void setParameterTypeClass(Class<?> parameterTypeClass) {
		this.parameterTypeClass = parameterTypeClass;
	}

	public String getSqlScript() {
		return sqlScript;
	}

	public void setSqlScript(String sqlScript) {
		this.sqlScript = sqlScript;
	}

	public Integer getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public StatementType getStatementType() {
		return statementType;
	}

	public void setStatementType(StatementType statementType) {
		this.statementType = statementType;
	}

	public ResultSetType getResultSetType() {
		return resultSetType;
	}

	public void setResultSetType(ResultSetType resultSetType) {
		this.resultSetType = resultSetType;
	}

	public SqlCommandType getSqlCommandType() {
		return sqlCommandType;
	}

	public void setSqlCommandType(SqlCommandType sqlCommandType) {
		this.sqlCommandType = sqlCommandType;
	}

	public KeyGenerator getKeyGenerator() {
		return keyGenerator;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public String getKeyProperty() {
		return keyProperty;
	}

	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	public String getResultMapId() {
		return resultMapId;
	}

	public void setResultMapId(String resultMapId) {
		this.resultMapId = resultMapId;
	}

	public Class<?> getResultType() {
		return resultType;
	}

	public void setResultType(Class<?> resultType) {
		this.resultType = resultType;
	}

}

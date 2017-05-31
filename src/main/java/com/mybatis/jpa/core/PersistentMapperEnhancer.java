package com.mybatis.jpa.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.ResultMapResolver;
import org.apache.ibatis.mapping.Discriminator;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.mybatis.jpa.annotation.MapperDefinition;
import com.mybatis.jpa.annotation.StatementDefinition;
import com.mybatis.jpa.common.PersistentUtil;
import com.mybatis.jpa.meta.MybatisColumnMeta;
import com.mybatis.jpa.meta.PersistentMeta;
import com.mybatis.jpa.statement.MybatisStatementAdapter;
import com.mybatis.jpa.statement.SqlAssistant;
import com.mybatis.jpa.statement.StatementBuilderHolder;
import com.mybatis.jpa.statement.builder.StatementBuildable;

/**
 * Persistent Mapper Enhancer(增强器)</br>
 * 
 * @attation 由Mybatis负责创建Mapper接口的代理对</br>
 * @attation 该enhancer只负责:</br>
 *           1.解析Entity,创建并注册ResultMap{@see ResultMap};</br>
 *           2.解析Method,创建并注册Mybatis Statement {@see MappedStatement}
 * 
 * @author svili
 * @data 2017年5月8日
 *
 */
public class PersistentMapperEnhancer extends BaseBuilder {

	/* mybatis */
	protected MapperBuilderAssistant assistant;

	/* mybatis mapper接口类型 */
	protected Class<?> mapper;

	/* 持久化Entity类型 */
	protected Class<?> type;

	/* 持久化Entity元数据 */
	protected PersistentMeta persistentMeta;

	/* MybatisStatement 适配器 */
	protected MybatisStatementAdapter adapter;

	/**
	 * 容器中configuration唯一,必须初始化
	 * 
	 * @param configuration
	 *            mybatis configuration{@see Configuration}
	 * @param mapper
	 *            mybatis mapper接口类型
	 */
	public PersistentMapperEnhancer(Configuration configuration, Class<?> mapper) {
		super(configuration);
		String resource = mapper.getName().replace(".", "/") + ".java (best guess)";
		this.assistant = new MapperBuilderAssistant(configuration, resource);
		this.adapter = new MybatisStatementAdapter(assistant);
		this.mapper = mapper;

		if (mapper.isAnnotationPresent(MapperDefinition.class)) {
			// 获取注解对象
			MapperDefinition mapperDefinition = mapper.getAnnotation(MapperDefinition.class);
			// Entity type
			this.type = mapperDefinition.domainClass();
			// Entity元数据
			this.persistentMeta = new PersistentMeta(type);
		}
	}

	/* mapper增强方法入口 */
	public void enhance() {
		String resource = mapper.toString();
		if (!configuration.isResourceLoaded(resource)) {
			configuration.addLoadedResource(resource);
		}
		assistant.setCurrentNamespace(mapper.getName());
		// no cache

		/*
		 * mapper will be definition if it has annotation with {@see
		 * StatementDefinition}
		 */
		if (!mapper.isAnnotationPresent(MapperDefinition.class)) {
			return;
		}
		// build and register ResultMap;
		ResultMapAdapter.parseResultMap(assistant, persistentMeta);

		/*
		 * build and register Mybatis Statement {@see MappedStatement} there use
		 * class.getMethods it means also contains methods from superClass
		 */
		for (Method method : mapper.getMethods()) {
			/*
			 * method will be definition if it has annotation with {@see
			 * StatementDefinition}
			 */
			if (method.isAnnotationPresent(StatementDefinition.class)) {
				String methodType = SqlAssistant.resolveMethodType(method.getName());
				StatementBuildable statementBuilder = StatementBuilderHolder.adapted(methodType);
				if (statementBuilder != null) {
					statementBuilder.parseStatement(adapter, persistentMeta, method);
				}
			}
		}
	}

	/* resultMap adapter */
	private static class ResultMapAdapter {

		static void parseResultMap(MapperBuilderAssistant assistant, PersistentMeta persistentMeta) {
			Class<?> resultType = persistentMeta.getType();

			String id = resultType.getSimpleName() + "Map";
			String extend = null;
			// 是否自动映射
			Boolean autoMapping = false;
			Discriminator discriminator = null;

			List<Field> fields = PersistentUtil.getPersistentFields(resultType);

			List<ResultMapping> resultMappings = new ArrayList<>(fields.size() + 1);

			for (MybatisColumnMeta columnMeta : persistentMeta.getColumnMetaMap().values()) {
				// java 字段名
				String property = columnMeta.getProperty();
				// sql 列名
				String column = columnMeta.getColumnName();
				Class<?> javaTypeClass = columnMeta.getType();

				JdbcType jdbcTypeEnum = columnMeta.getJdbcType();

				String nestedSelect = null;
				String nestedResultMap = null;
				String notNullColumn = null;
				String columnPrefix = null;
				String resultSet = null;
				String foreignColumn = null;
				// if primaryKey flags.add(ResultFlag.ID);
				List<ResultFlag> flags = new ArrayList<ResultFlag>();
				// lazy or eager
				boolean lazy = false;
				// enum
				Class<? extends TypeHandler<?>> typeHandlerClass = columnMeta.getTypeHandlerClass();

				ResultMapping resultMapping = assistant.buildResultMapping(resultType, property, column, javaTypeClass,
						jdbcTypeEnum, nestedSelect, nestedResultMap, notNullColumn, columnPrefix, typeHandlerClass,
						flags, resultSet, foreignColumn, lazy);
				resultMappings.add(resultMapping);
			}

			ResultMapResolver resultMapResolver = new ResultMapResolver(assistant, id, resultType, extend,
					discriminator, resultMappings, autoMapping);
			try {
				// 生成ResultMap并加入到Configuration中
				resultMapResolver.resolve();
			} catch (IncompleteElementException e) {
				assistant.getConfiguration().addIncompleteResultMap(resultMapResolver);
				throw e;
			}
		}
	}

}

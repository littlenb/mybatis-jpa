package com.mybatis.jpa.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

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

import com.mybatis.jpa.common.AssociationUtil;
import com.mybatis.jpa.common.PersistentUtil;
import com.mybatis.jpa.constant.ResultMapConstants;
import com.mybatis.jpa.meta.MybatisColumnMeta;
import com.mybatis.jpa.meta.PersistentMeta;

/**
 * Persistent ResultMap Enhancer(增强器)</br>
 * 
 * @attation 由Mybatis负责创建Mapper接口的代理</br>
 * @attation 该enhancer只负责:</br>
 *           1.解析Entity,创建并注册ResultMap{@see ResultMap};</br>
 * 
 * @author svili
 *
 */
public class PersistentResultMapEnhancer extends BaseBuilder {

	/** mybatis */
	protected MapperBuilderAssistant assistant;

	/** 命名空间 */
	protected String namespace;

	/** 持久化Entity类型 */
	protected Class<?> type;

	/** 持久化Entity元数据 */
	protected PersistentMeta persistentMeta;

	public PersistentResultMapEnhancer(Configuration configuration, Class<?> type) {
		super(configuration);

		String resource = ResultMapConstants.DEFAULT_NAMESPACE.replaceAll(".", "/") + ".java (best guess)";
		this.assistant = new MapperBuilderAssistant(configuration, resource);

		this.type = type;
		this.persistentMeta = new PersistentMeta(type);
	}

	public void enhance() {
		String resource = "interface " + ResultMapConstants.DEFAULT_NAMESPACE;
		if (!configuration.isResourceLoaded(resource)) {
			configuration.addLoadedResource(resource);
		}
		assistant.setCurrentNamespace(ResultMapConstants.DEFAULT_NAMESPACE);

		if (!type.isAnnotationPresent(Entity.class)) {
			return;
		}

		// build and register ResultMap;
		ResultMapAdapter.parseResultMap(assistant, persistentMeta);

	}

	/** resultMap adapter */
	private static class ResultMapAdapter {

		static void parseResultMap(MapperBuilderAssistant assistant, PersistentMeta persistentMeta) {
			Class<?> resultType = persistentMeta.getType();
			String id = persistentMeta.getEntityName();

			if (assistant.getConfiguration().getResultMapNames().contains(id)) {
				return;
			}

			String extend = null;
			// 是否自动映射
			Boolean autoMapping = false;
			Discriminator discriminator = null;

			List<Field> fields = PersistentUtil.getPersistentFields(resultType);

			List<ResultMapping> resultMappings = new ArrayList<>(fields.size() + 1);

			// 持久化字段
			for (MybatisColumnMeta columnMeta : persistentMeta.getColumnMetaMap().values()) {
				// java 字段名
				String property = columnMeta.getProperty();
				// sql 列名
				String column = columnMeta.getColumnName();
				Class<?> javaType = columnMeta.getType();

				JdbcType jdbcType = columnMeta.getJdbcType();

				String nestedSelect = null;
				String nestedResultMap = null;
				String notNullColumn = null;
				String columnPrefix = null;
				String resultSet = null;
				String foreignColumn = null;
				// if primaryKey flags.add(ResultFlag.ID);
				List<ResultFlag> flags = new ArrayList<>();
				// lazy or eager
				boolean lazy = false;
				// enum
				Class<? extends TypeHandler<?>> typeHandlerClass = columnMeta.getTypeHandlerClass();

				ResultMapping resultMapping = assistant.buildResultMapping(resultType, property, column, javaType,
						jdbcType, nestedSelect, nestedResultMap, notNullColumn, columnPrefix, typeHandlerClass, flags,
						resultSet, foreignColumn, lazy);
				resultMappings.add(resultMapping);
			}

			// 关联性字段
			List<Field> associationFields = AssociationUtil.getAssociationFields(resultType);
			if (!associationFields.isEmpty()) {
				for (Field field : associationFields) {
					// java 字段名
					String property = field.getName();
					// 关联字段 mappedBy
					String column = AssociationUtil.getMappedName(field);
					Class<?> javaType = AssociationUtil.getTargetType(field);

					JdbcType jdbcType = null;

					String nestedSelect = null;
					String nestedResultMap = assistant.getCurrentNamespace() + "."
							+ PersistentUtil.getEntityName(javaType);
					String notNullColumn = null;
					String columnPrefix = null;
					String resultSet = null;
					String foreignColumn = null;
					// if primaryKey flags.add(ResultFlag.ID);
					List<ResultFlag> flags = new ArrayList<>();
					// lazy or eager
					boolean lazy = false;
					// enum
					Class<? extends TypeHandler<?>> typeHandlerClass = null;

					ResultMapping resultMapping = assistant.buildResultMapping(resultType, property, column, javaType,
							jdbcType, nestedSelect, nestedResultMap, notNullColumn, columnPrefix, typeHandlerClass,
							flags, resultSet, foreignColumn, lazy);
					resultMappings.add(resultMapping);
				}
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

package com.mybatis.jpa.meta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mybatis.jpa.common.PersistentUtil;

/**
 * 持久化元数据类型
 * 
 * @author svili
 *
 */
public class PersistentMeta {

	/** 持久化对象 类型 */
	private Class<?> type;

	/** 表名 */
	private String tableName;

	/** {@link javax.persistence.Entity} */
	private String entityName;

	// private String primaryKey;

	/** 主键column元数据 */
	private MybatisColumnMeta primaryColumnMeta;

	/** 列名集 */
	private List<String> columnNameCollection;

	private String columnNames;

	/** column元数据集 {key-fieldName} */
	private Map<String, MybatisColumnMeta> columnMetaMap;

	public PersistentMeta(Class<?> type) {
		this.type = type;
		persistence();
	}

	private void persistence() {
		tableName = PersistentUtil.getTableName(type);
		entityName = PersistentUtil.getEntityName(type);
		primaryColumnMeta = new MybatisColumnMeta(PersistentUtil.getPrimaryField(type));

		// 持久化字段集
		List<Field> fields = PersistentUtil.getPersistentFields(type);
		// 初始化集合
		columnNameCollection = new ArrayList<String>();
		columnMetaMap = new LinkedHashMap<String, MybatisColumnMeta>();
		StringBuilder columnNamesTemp = new StringBuilder();

		for (Field field : fields) {
			MybatisColumnMeta columnMeta = new MybatisColumnMeta(field);
			columnMetaMap.put(field.getName(), columnMeta);
			columnNameCollection.add(columnMeta.getColumnName());
			columnNamesTemp.append(",").append(columnMeta.getColumnName());
		}
		columnNames = columnNamesTemp.substring(1);
	}

	// getter

	public Class<?> getType() {
		return type;
	}

	public String getTableName() {
		return tableName;
	}

	public String getEntityName() {
		return entityName;
	}

	public MybatisColumnMeta getPrimaryColumnMeta() {
		return primaryColumnMeta;
	}

	public List<String> getColumnNameCollection() {
		return columnNameCollection;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public Map<String, MybatisColumnMeta> getColumnMetaMap() {
		return columnMetaMap;
	}

}

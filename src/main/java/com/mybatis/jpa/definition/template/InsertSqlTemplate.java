package com.mybatis.jpa.definition.template;

import com.mybatis.jpa.util.ColumnMetaResolver;
import com.mybatis.jpa.util.PersistentUtil;
import java.lang.reflect.Field;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author svili
 **/
public class InsertSqlTemplate implements SqlTemplate {

  @Override
  public String parseSQL(final Class<?> type) {
    return new SQL() {
      {
        INSERT_INTO(PersistentUtil.getTableName(type));
        for (Field field : PersistentUtil.getPersistentFields(type)) {
          if (PersistentUtil.insertable(field)) {
            VALUES(PersistentUtil.getColumnName(field),
                ColumnMetaResolver.resolveSqlPlaceholder(field));
          }
        }
      }
    }.toString();
  }

}

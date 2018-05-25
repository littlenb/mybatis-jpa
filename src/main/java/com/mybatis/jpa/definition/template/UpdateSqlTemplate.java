package com.mybatis.jpa.definition.template;

import com.mybatis.jpa.util.ColumnMetaResolver;
import com.mybatis.jpa.util.PersistentUtil;
import java.lang.reflect.Field;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author svili
 **/
public class UpdateSqlTemplate implements SqlTemplate {

  @Override
  public String parseSQL(final Class<?> type) {
    return new SQL() {
      {
        UPDATE(PersistentUtil.getTableName(type));
        for (Field field : PersistentUtil.getPersistentFields(type)) {
          if (PersistentUtil.updatable(field)) {
            SET(PersistentUtil.getColumnName(field) + " = " + ColumnMetaResolver
                .resolveSqlPlaceholder(field));
          }
        }
      }
    }.toString();
  }

}

package com.littlenb.mybatisjpa.support.template;

import com.littlenb.mybatisjpa.util.ColumnMetaResolver;
import com.littlenb.mybatisjpa.util.PersistentUtil;
import java.lang.reflect.Field;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author sway.li
 **/
public class UpdateSqlTemplate implements SqlTemplate {

  public static final SqlTemplate INSTANCE = new UpdateSqlTemplate();

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

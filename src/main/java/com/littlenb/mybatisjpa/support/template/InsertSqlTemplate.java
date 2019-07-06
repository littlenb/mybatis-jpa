package com.littlenb.mybatisjpa.support.template;

import com.littlenb.mybatisjpa.util.ColumnMetaResolver;
import com.littlenb.mybatisjpa.util.PersistentUtil;
import java.lang.reflect.Field;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author sway.li
 **/
public class InsertSqlTemplate implements SqlTemplate {

  public static final SqlTemplate INSTANCE = new InsertSqlTemplate();

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

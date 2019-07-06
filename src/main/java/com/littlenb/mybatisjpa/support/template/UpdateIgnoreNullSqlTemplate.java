package com.littlenb.mybatisjpa.support.template;

import com.littlenb.mybatisjpa.util.ColumnMetaResolver;
import com.littlenb.mybatisjpa.util.PersistentUtil;
import java.lang.reflect.Field;

/**
 * @author sway.li
 **/
public class UpdateIgnoreNullSqlTemplate implements SqlTemplate {

  public static final SqlTemplate INSTANCE = new UpdateIgnoreNullSqlTemplate();

  @Override
  public String parseSQL(Class<?> type) {
    // columns
    StringBuilder sets = new StringBuilder();
    sets.append("<trim prefix='' suffix='' suffixOverrides=',' > ");
    for (Field field : PersistentUtil.getPersistentFields(type)) {

      sets.append("<if test='" + field.getName() + "!= null'> ");
      // columnName = #{ }

      sets.append(PersistentUtil.getColumnName(field)).append(" = ")
          .append(ColumnMetaResolver.resolveSqlPlaceholder(field)).append(" , ");
      sets.append("</if> ");
    }

    sets.append("</trim> ");

    return "UPDATE " + PersistentUtil.getTableName(type) + " set " + sets.toString();
  }

}

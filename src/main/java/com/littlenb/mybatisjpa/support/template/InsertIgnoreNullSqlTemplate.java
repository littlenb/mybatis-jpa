package com.littlenb.mybatisjpa.support.template;

import com.littlenb.mybatisjpa.util.ColumnMetaResolver;
import com.littlenb.mybatisjpa.util.PersistentUtil;
import java.lang.reflect.Field;

/**
 * @author sway.li
 **/
public class InsertIgnoreNullSqlTemplate implements SqlTemplate {

  public static final SqlTemplate INSTANCE = new InsertIgnoreNullSqlTemplate();

  @Override
  public String parseSQL(Class<?> type) {
    // columns
    StringBuilder columns = new StringBuilder();
    columns.append(" <trim prefix='(' suffix=')' suffixOverrides=',' > ");
    // values
    StringBuilder values = new StringBuilder();
    values.append(" <trim prefix='(' suffix=')' suffixOverrides=',' > ");

    for (Field field : PersistentUtil.getPersistentFields(type)) {
      // columns
      columns.append("<if test='" + field.getName() + "!= null'> ");
      columns.append(PersistentUtil.getColumnName(field) + ", ");
      columns.append("</if> ");
      // values
      values.append("<if test='" + field.getName() + "!= null'> ");
      values.append(ColumnMetaResolver.resolveSqlPlaceholder(field) + ", ");
      values.append("</if> ");
    }

    columns.append("</trim> ");
    values.append("</trim> ");

    return "INSERT INTO " + PersistentUtil.getTableName(type) + columns.toString() + " VALUES "
        + values.toString();
  }

}

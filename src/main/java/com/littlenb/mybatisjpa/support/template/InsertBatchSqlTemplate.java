package com.littlenb.mybatisjpa.support.template;

import com.littlenb.mybatisjpa.util.ColumnMetaResolver;
import com.littlenb.mybatisjpa.util.PersistentUtil;
import java.lang.reflect.Field;

/**
 * @author sway.li
 **/
public class InsertBatchSqlTemplate implements SqlTemplate {

  public static final SqlTemplate INSTANCE = new InsertBatchSqlTemplate();

  @Override
  public String parseSQL(final Class<?> type) {
    // columns
    StringBuilder columns = new StringBuilder();
    columns.append(" <trim prefix='(' suffix=')' suffixOverrides=',' > ");
    // values
    StringBuilder values = new StringBuilder();
    values.append(" <trim prefix='(' suffix=')' suffixOverrides=',' > ");

    for (Field field : PersistentUtil.getPersistentFields(type)) {
      if (!PersistentUtil.insertable(field)) {
        continue;
      }
      // columns
      columns.append(PersistentUtil.getColumnName(field)).append(", ");
      // values
      values.append(ColumnMetaResolver.resolveSqlPlaceholder(field, "data")).append(", ");
    }

    columns.append("</trim> ");
    values.append("</trim> ");

    return "INSERT INTO " + PersistentUtil.getTableName(type) + columns.toString() + " VALUES "
        + "<foreach item='data' index='i' collection='list' separator=','>" + values.toString()
        + "</foreach>";
  }
}
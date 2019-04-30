package com.mybatis.jpa.definition.template;

import com.mybatis.jpa.util.ColumnMetaResolver;
import com.mybatis.jpa.util.PersistentUtil;
import java.lang.reflect.Field;

/**
 * @author sway.li
 **/
public class InsertSelectiveSqlTemplate implements SqlTemplate {

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

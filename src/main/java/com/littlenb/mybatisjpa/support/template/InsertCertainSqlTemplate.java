package com.littlenb.mybatisjpa.support.template;

import com.littlenb.mybatisjpa.support.Constant;
import com.littlenb.mybatisjpa.util.ColumnMetaResolver;
import com.littlenb.mybatisjpa.util.PersistentUtil;
import java.lang.reflect.Field;

/**
 * @author sway.li
 **/
public class InsertCertainSqlTemplate implements SqlTemplate {

  public static final SqlTemplate INSTANCE = new InsertCertainSqlTemplate();

  @Override
  public String parseSQL(Class<?> type) {
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
      columns.append(String.format(
          "<if test='includes.contains(\"%1$s\") and !excludes.contains(\"%1$s\")'> ",
          field.getName()
      ));
      columns.append(PersistentUtil.getColumnName(field)).append(", ");
      columns.append("</if> ");
      // values
      values.append(String.format(
          "<if test='includes.contains(\"%1$s\") and !excludes.contains(\"%1$s\")'> ",
          field.getName()
      ));
      values.append(
          ColumnMetaResolver.resolveSqlPlaceholder(field, Constant.CERTAINTY_ENTITY_KEY))
          .append(", ");
      values.append("</if> ");
    }

    columns.append("</trim> ");
    values.append("</trim> ");

    return "INSERT INTO " + PersistentUtil.getTableName(type) + columns.toString() + " VALUES "
        + values.toString();
  }

}

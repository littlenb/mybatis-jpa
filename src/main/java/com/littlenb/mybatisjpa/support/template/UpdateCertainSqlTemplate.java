package com.littlenb.mybatisjpa.support.template;

import com.littlenb.mybatisjpa.support.Constant;
import com.littlenb.mybatisjpa.util.ColumnMetaResolver;
import com.littlenb.mybatisjpa.util.PersistentUtil;
import java.lang.reflect.Field;

/**
 * @author sway.li
 **/
public class UpdateCertainSqlTemplate implements SqlTemplate {

  public static final SqlTemplate INSTANCE = new UpdateCertainSqlTemplate();

  @Override
  public String parseSQL(Class<?> type) {
    // columns
    StringBuilder sets = new StringBuilder();
    sets.append("<trim prefix='' suffix='' suffixOverrides=',' > ");
    for (Field field : PersistentUtil.getPersistentFields(type)) {
      sets.append(String.format(
          "<if test='includes.contains(\"%1$s\") and !excludes.contains(\"%1$s\")'> ",
          field.getName()
      ));
      // columnName = #{ }

      sets.append(PersistentUtil.getColumnName(field)).append(" = ")
          .append(ColumnMetaResolver.resolveSqlPlaceholder(field, Constant.CERTAINTY_ENTITY_KEY))
          .append(", ");
      sets.append("</if> ");
    }

    sets.append("</trim> ");

    return "UPDATE " + PersistentUtil.getTableName(type) + " SET " + sets.toString();
  }

}

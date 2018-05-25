package com.mybatis.jpa.definition.template;

import com.mybatis.jpa.util.ColumnMetaResolver;
import com.mybatis.jpa.util.PersistentUtil;
import java.lang.reflect.Field;

/**
 * @author svili
 **/
public class UpdateSelectiveSqlTemplate implements SqlTemplate {

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

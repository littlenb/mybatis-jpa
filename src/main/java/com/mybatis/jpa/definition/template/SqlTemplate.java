package com.mybatis.jpa.definition.template;

/**
 * @author svili
 **/
public interface SqlTemplate {

  String parseSQL(final Class<?> type);
}

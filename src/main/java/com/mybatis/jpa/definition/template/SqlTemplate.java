package com.mybatis.jpa.definition.template;

/**
 * @author sway.li
 **/
public interface SqlTemplate {

  String parseSQL(final Class<?> type);
}

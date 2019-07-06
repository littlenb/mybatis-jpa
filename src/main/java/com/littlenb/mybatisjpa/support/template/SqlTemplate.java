package com.littlenb.mybatisjpa.support.template;

/**
 * @author sway.li
 */
public interface SqlTemplate {

  String parseSQL(final Class<?> type);

}

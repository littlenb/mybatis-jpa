package com.mybatis.jpa.definition.template;

/**
 * @author sway.li
 **/
public interface SqlTemplateHolder {

  SqlTemplate insertSqlTemplate = new InsertSqlTemplate();

  SqlTemplate insertSelectiveSqlTemplate = new InsertSelectiveSqlTemplate();

  SqlTemplate updateSqlTemplate = new UpdateSqlTemplate();

  SqlTemplate updateSelectiveSqlTemplate = new UpdateSelectiveSqlTemplate();

}

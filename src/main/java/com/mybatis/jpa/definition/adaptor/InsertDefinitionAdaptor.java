package com.mybatis.jpa.definition.adaptor;

import com.mybatis.jpa.annotation.InsertDefinition;
import com.mybatis.jpa.definition.property.AnnotationProperty;
import com.mybatis.jpa.definition.property.InsertDefinitionProperty;
import com.mybatis.jpa.definition.template.SqlTemplate;
import com.mybatis.jpa.definition.template.SqlTemplateHolder;
import java.lang.annotation.Annotation;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * @author sway.li
 **/
public class InsertDefinitionAdaptor implements AnnotationAdaptable {

  @Override
  public AnnotationProperty context(Annotation annotation) {
    if (annotation instanceof InsertDefinition) {
      InsertDefinition insert = (InsertDefinition) annotation;
      return new InsertDefinitionProperty(insert);
    }
    return null;
  }

  @Override
  public SqlTemplate sqlTemplate(Annotation annotation) {
    if (annotation instanceof InsertDefinition) {
      InsertDefinition insert = (InsertDefinition) annotation;
      if (insert.selective()) {
        return SqlTemplateHolder.insertSelectiveSqlTemplate;
      } else {
        return SqlTemplateHolder.insertSqlTemplate;
      }
    }
    return null;
  }

  @Override
  public SqlCommandType sqlCommandType() {
    return SqlCommandType.INSERT;
  }
}

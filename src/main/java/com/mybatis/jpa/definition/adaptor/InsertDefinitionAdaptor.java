package com.mybatis.jpa.definition.adaptor;

import com.mybatis.jpa.annotation.InsertDefinition;
import com.mybatis.jpa.definition.property.AnnotationProperty;
import com.mybatis.jpa.definition.property.InsertDefinitionProperty;
import com.mybatis.jpa.definition.template.InsertSelectiveSqlTemplate;
import com.mybatis.jpa.definition.template.InsertSqlTemplate;
import com.mybatis.jpa.definition.template.SqlTemplate;
import java.lang.annotation.Annotation;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * @author svili
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
        return new InsertSelectiveSqlTemplate();
      } else {
        return new InsertSqlTemplate();
      }
    }
    return null;
  }

  @Override
  public SqlCommandType sqlCommandType() {
    return SqlCommandType.INSERT;
  }
}

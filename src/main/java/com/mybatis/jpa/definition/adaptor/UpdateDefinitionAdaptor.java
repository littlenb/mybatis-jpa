package com.mybatis.jpa.definition.adaptor;

import com.mybatis.jpa.annotation.UpdateDefinition;
import com.mybatis.jpa.definition.property.AnnotationProperty;
import com.mybatis.jpa.definition.property.UpdateDefinitionProperty;
import com.mybatis.jpa.definition.template.SqlTemplate;
import com.mybatis.jpa.definition.template.UpdateSelectiveSqlTemplate;
import com.mybatis.jpa.definition.template.UpdateSqlTemplate;
import java.lang.annotation.Annotation;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * @author svili
 **/
public class UpdateDefinitionAdaptor implements AnnotationAdaptable {

  @Override
  public AnnotationProperty context(Annotation annotation) {
    if (annotation instanceof UpdateDefinition) {
      UpdateDefinition update = (UpdateDefinition) annotation;
      return new UpdateDefinitionProperty(update);
    }
    return null;
  }

  @Override
  public SqlTemplate sqlTemplate(Annotation annotation) {
    if (annotation instanceof UpdateDefinition) {
      UpdateDefinition update = (UpdateDefinition) annotation;
      if (update.selective()) {
        return new UpdateSelectiveSqlTemplate();
      } else {
        return new UpdateSqlTemplate();
      }
    }
    return null;
  }

  @Override
  public SqlCommandType sqlCommandType() {
    return SqlCommandType.UPDATE;
  }

}

package com.mybatis.jpa.definition.property;

import com.mybatis.jpa.annotation.InsertDefinition;

/**
 * @author sway.li
 **/
public class InsertDefinitionProperty implements AnnotationProperty {

  private InsertDefinition annotation;

  public InsertDefinitionProperty(InsertDefinition annotation) {
    this.annotation = annotation;
  }

  @Override
  public boolean selective() {
    return annotation.selective();
  }

  @Override
  public String where() {
    return null;
  }

}

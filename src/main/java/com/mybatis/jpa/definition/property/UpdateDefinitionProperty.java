package com.mybatis.jpa.definition.property;

import com.mybatis.jpa.annotation.UpdateDefinition;

/**
 * @author sway.li
 **/
public class UpdateDefinitionProperty implements AnnotationProperty {

  private UpdateDefinition annotation;

  public UpdateDefinitionProperty(UpdateDefinition annotation) {
    this.annotation = annotation;
  }

  @Override
  public boolean selective() {
    return annotation.selective();
  }

  @Override
  public String where() {
    return annotation.where();
  }

}

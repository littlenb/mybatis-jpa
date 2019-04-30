package com.mybatis.jpa.definition;

import com.mybatis.jpa.annotation.InsertDefinition;
import com.mybatis.jpa.annotation.UpdateDefinition;
import com.mybatis.jpa.definition.adaptor.AnnotationAdaptable;
import com.mybatis.jpa.definition.adaptor.InsertDefinitionAdaptor;
import com.mybatis.jpa.definition.adaptor.UpdateDefinitionAdaptor;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sway.li
 **/
public class AnnotationDefinitionRegistry {

  private final Map<Class<? extends Annotation>, AnnotationAdaptable> ANNOTATION_ADAPTORS = new HashMap<>();

  public AnnotationDefinitionRegistry() {
    registerAdaptor(InsertDefinition.class, new InsertDefinitionAdaptor());
    registerAdaptor(UpdateDefinition.class, new UpdateDefinitionAdaptor());
  }

  public AnnotationAdaptable resolveAdaptor(Class<? extends Annotation> annotationType) {
    if (annotationType == null) {
      return null;
    }
    return ANNOTATION_ADAPTORS.get(annotationType);
  }

  public void registerAdaptor(Class<? extends Annotation> annotationType,
      AnnotationAdaptable adaptor) {
    if (annotationType == null) {
      throw new RuntimeException("The parameter annotationType cannot be null");
    }
    if (adaptor == null) {
      throw new RuntimeException("The parameter register cannot be null");
    }
    ANNOTATION_ADAPTORS.put(annotationType, adaptor);
  }

  public Map<Class<? extends Annotation>, AnnotationAdaptable> getAnnotationAdaptors() {
    return Collections.unmodifiableMap(ANNOTATION_ADAPTORS);
  }
}

package com.littlenb.mybatisjpa.statement;

import com.littlenb.mybatisjpa.annotation.InsertDefinition;
import com.littlenb.mybatisjpa.annotation.UpdateDefinition;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sway.li
 */
public class AnnotationStatementRegistry {

  private Map<Class<? extends Annotation>, StatementFactory> REGISTERS = new HashMap<>();

  public static AnnotationStatementRegistry getDefaultRegistry() {
    AnnotationStatementRegistry registry = new AnnotationStatementRegistry();
    registry.register(InsertDefinition.class, InsertStatementFactory.INSTANCE);
    registry.register(UpdateDefinition.class, UpdateStatementFactory.INSTANCE);
    return registry;
  }

  public void register(Class<? extends Annotation> annotationType, StatementFactory factory) {
    if (annotationType == null) {
      throw new IllegalArgumentException("The parameter 'annotationType' cannot be null.");
    }
    if (factory == null) {
      throw new IllegalArgumentException("The parameter 'factory' cannot be null.");
    }
    REGISTERS.put(annotationType, factory);
  }

  public Map<Class<? extends Annotation>, StatementFactory> getRegisters() {
    return Collections.unmodifiableMap(REGISTERS);
  }

  public StatementFactory findFactory(Class<? extends Annotation> annotationType) {
    if (annotationType == null) {
      return null;
    }

    return REGISTERS.get(annotationType);
  }

}

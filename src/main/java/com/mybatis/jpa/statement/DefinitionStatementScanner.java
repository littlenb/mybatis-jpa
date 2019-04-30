package com.mybatis.jpa.statement;

import com.mybatis.jpa.annotation.InsertDefinition;
import com.mybatis.jpa.annotation.UpdateDefinition;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.annotation.MethodResolver;
import org.apache.ibatis.session.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

/**
 * @author sway.li
 **/
public class DefinitionStatementScanner {

  private Configuration configuration;

  private String[] basePackages;

  private StatementFactory statementFactory;

  private Set<Class<? extends Annotation>> registryAnnotationSet = new HashSet<>();

  public void scan() {
    for (String basePackage : basePackages) {
      Reflections reflections = new Reflections(basePackage, new TypeAnnotationsScanner(),
          new SubTypesScanner(), new MethodAnnotationsScanner());
      Set<Class<? extends Annotation>> registryAnnotation = registryAnnotationSet;
      Set<Class<?>> mappers = reflections.getTypesAnnotatedWith(Mapper.class);
      for (Class<?> mapperClass : mappers) {
        Method[] methods = mapperClass.getMethods();
        for (Method method : methods) {
          Annotation[] annotations = method.getDeclaredAnnotations();
          for (Annotation annotation : annotations) {
            if (registryAnnotation.contains(annotation.annotationType())) {
              statementFactory.parseStatement(method, mapperClass);
            }
          }

        }
      }
    }
    parsePendingMethods();
  }

  private void parsePendingMethods() {
    Collection<MethodResolver> incompleteMethods = configuration.getIncompleteMethods();
    synchronized (incompleteMethods) {
      Iterator<MethodResolver> iter = incompleteMethods.iterator();
      while (iter.hasNext()) {
        try {
          iter.next().resolve();
          iter.remove();
        } catch (IncompleteElementException e) {
          // This method is still missing a resource
        }
      }
    }
  }

  public static class Builder {

    private DefinitionStatementScanner instance = new DefinitionStatementScanner();

    public Builder() {
      instance.registryAnnotationSet.add(InsertDefinition.class);
      instance.registryAnnotationSet.add(UpdateDefinition.class);
    }

    public Builder configuration(Configuration configuration) {
      this.instance.configuration = configuration;
      return this;
    }

    public Builder basePackages(String[] basePackages) {
      this.instance.basePackages = basePackages;
      return this;
    }

    public Builder statementFactory(StatementFactory statementFactory) {
      this.instance.statementFactory = statementFactory;
      return this;
    }

    public Builder registryAnnotationSet(Set<Class<? extends Annotation>> registryAnnotationSet) {
      this.instance.registryAnnotationSet = registryAnnotationSet;
      return this;
    }

    public DefinitionStatementScanner build() {
      return this.instance;
    }
  }

}

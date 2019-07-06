package com.littlenb.mybatisjpa.support;

import com.littlenb.mybatisjpa.statement.AnnotationStatementRegistry;
import com.littlenb.mybatisjpa.statement.StatementFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.annotation.MethodResolver;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

/**
 * @author sway.li
 **/
public class AnnotationStatementScanner {

  private Configuration configuration;

  private String[] basePackages;

  private AnnotationStatementRegistry annotationStatementRegistry;

  public void scan() {
    for (String basePackage : basePackages) {
      Reflections reflections = new Reflections(basePackage, new TypeAnnotationsScanner(),
          new SubTypesScanner(), new MethodAnnotationsScanner());
      Set<Class<?>> mappers = reflections.getTypesAnnotatedWith(Mapper.class);
      for (Class<?> mapperClass : mappers) {
        Method[] methods = mapperClass.getMethods();
        for (Method method : methods) {
          Annotation[] annotations = method.getDeclaredAnnotations();
          for (Annotation annotation : annotations) {
            StatementFactory statementFactory = annotationStatementRegistry
                .findFactory(annotation.annotationType());
            if (statementFactory != null) {
              MappedStatement statement = statementFactory.parseStatement(configuration, method, mapperClass);
              configuration.addMappedStatement(statement);
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

    private AnnotationStatementScanner instance = new AnnotationStatementScanner();

    public Builder() {
    }

    public Builder configuration(Configuration configuration) {
      this.instance.configuration = configuration;
      return this;
    }

    public Builder basePackages(String[] basePackages) {
      this.instance.basePackages = basePackages;
      return this;
    }

    public Builder annotationStatementRegistry(AnnotationStatementRegistry annotationStatementRegistry) {
      this.instance.annotationStatementRegistry = annotationStatementRegistry;
      return this;
    }

    public AnnotationStatementScanner build() {
      return this.instance;
    }
  }

}

package com.mybatis.jpa.statement;

import com.mybatis.jpa.definition.AnnotationDefinitionRegistry;
import com.mybatis.jpa.definition.adaptor.AnnotationAdaptable;
import com.mybatis.jpa.definition.property.AnnotationProperty;
import com.mybatis.jpa.definition.template.SqlTemplate;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * @author svili
 **/
public class DefinitionStatementBuilder implements StatementBuildable {

  protected Configuration configuration;

  protected AnnotationDefinitionRegistry definitionRegistry;

  public DefinitionStatementBuilder(Configuration configuration) {
    this(configuration, new AnnotationDefinitionRegistry());
  }

  public DefinitionStatementBuilder(Configuration configuration,
      AnnotationDefinitionRegistry definitionRegistry) {
    this.configuration = configuration;
    this.definitionRegistry = definitionRegistry;
  }

  @Override
  public void parseStatement(Method method) {

    if (!configuration.isResourceLoaded(method.getDeclaringClass().toString())) {
      configuration.addLoadedResource(method.getDeclaringClass().toString());
    }

    LanguageDriver languageDriver = configuration.getDefaultScriptingLanuageInstance();
    SqlSource sqlSource = languageDriver
        .createSqlSource(configuration, parseSQL(method), Object.class);
    String statementId = method.getDeclaringClass().getName() + "." + method.getName();
    MappedStatement.Builder builder = new MappedStatement.Builder(configuration, statementId,
        sqlSource, recognizeSqlCommandType(method));

    String resource = recognizeResource(method);
    builder.resource(resource).lang(languageDriver).statementType(StatementType.PREPARED);
    MappedStatement statement = builder.build();
    configuration.addMappedStatement(statement);
  }

  protected String parseSQL(Method method) {
    Annotation annotation = recognizeDefinitionAnnotation(method);
    AnnotationAdaptable adaptor = recognizeAdaptor(method);

    AnnotationProperty annotationProperty = adaptor.context(annotation);
    SqlTemplate sqlTemplate = adaptor.sqlTemplate(annotation);

    Class<?> type = recognizeEntityType(method);

    String sql = sqlTemplate.parseSQL(type);

    if (annotationProperty.where() != null) {
      sql = sql + " where " + annotationProperty.where();
    }

    return "<script> " + sql + "</script>";
  }

  protected String recognizeResource(Method method) {
    Class<?> mapper = method.getDeclaringClass();
    return mapper.getName().replace(".", "/") + ".java (best guess)";
  }

  protected SqlCommandType recognizeSqlCommandType(Method method) {
    return recognizeAdaptor(method).sqlCommandType();
  }

  protected AnnotationAdaptable recognizeAdaptor(Method method) {
    Annotation annotation = recognizeDefinitionAnnotation(method);
    return definitionRegistry.resolveAdaptor(annotation.annotationType());
  }

  protected Annotation recognizeDefinitionAnnotation(Method method) {
    for (Class<? extends Annotation> annotationType : definitionRegistry.getAnnotationAdaptors()
        .keySet()) {
      if (method.isAnnotationPresent(annotationType)) {
        return method.getAnnotation(annotationType);
      }
    }
    return null;
  }

  protected Class<?> recognizeEntityType(Method method) {
    Class<?> actualType;

    Type[] genericTypes = method.getGenericParameterTypes();
    Type genericType = genericTypes[0];
    if (genericType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) genericType;
      actualType = (Class<?>) pt.getActualTypeArguments()[0];
    } else {
      actualType = (Class<?>) genericType;
    }
    return actualType;
  }

}

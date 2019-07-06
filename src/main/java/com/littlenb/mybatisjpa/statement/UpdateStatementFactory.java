package com.littlenb.mybatisjpa.statement;

import com.littlenb.mybatisjpa.annotation.UpdateDefinition;
import com.littlenb.mybatisjpa.support.Constant;
import com.littlenb.mybatisjpa.support.template.UpdateCertainSqlTemplate;
import com.littlenb.mybatisjpa.support.template.UpdateIgnoreNullSqlTemplate;
import com.littlenb.mybatisjpa.support.template.UpdateSqlTemplate;
import com.littlenb.mybatisjpa.type.SelectorStrategy;
import java.lang.reflect.Method;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * @author sway.li
 */
public class UpdateStatementFactory extends AbstractStatementFactory {

  public static final StatementFactory INSTANCE = new UpdateStatementFactory();

  @Override
  public MappedStatement parseStatement(Configuration configuration, Method method,
      Class<?> targetClass) {
    String resourceName = targetClass.toString();

    if (!configuration.isResourceLoaded(resourceName)) {
      configuration.addLoadedResource(resourceName);
    }
    String targetClassName = targetClass.getName();
    Class<?> type = super.recognizeEntityType(method, targetClass);
    LanguageDriver languageDriver = Constant.XML_LANGUAGE_DRIVER;
    SqlSource sqlSource = languageDriver
        .createSqlSource(configuration, "<script> " + parseSQL(method, type) + "</script>",
            Object.class);
    String statementId = targetClassName + "." + method.getName();
    MappedStatement.Builder builder = new MappedStatement.Builder(configuration, statementId,
        sqlSource, SqlCommandType.UPDATE);
    builder.resource(super.recognizeResource(targetClassName)).lang(languageDriver)
        .statementType(StatementType.PREPARED);

    return builder.build();
  }

  private String parseSQL(Method method, Class<?> type) {
    UpdateDefinition updateDefinition = method.getAnnotation(UpdateDefinition.class);
    if (updateDefinition == null) {
      throw new RuntimeException(
          "'" + method.getName() + "' method is not annotation with 'UpdateDefinition'.");
    }
    SelectorStrategy strategy = updateDefinition.strategy();

    String sql = "";

    if (SelectorStrategy.NONE.equals(strategy)) {
      sql = UpdateSqlTemplate.INSTANCE.parseSQL(type);
    }

    if (SelectorStrategy.IGNORE_NULL.equals(strategy)) {
      sql = UpdateIgnoreNullSqlTemplate.INSTANCE.parseSQL(type);
    }

    if (SelectorStrategy.CERTAIN.equals(strategy)) {
      sql = UpdateCertainSqlTemplate.INSTANCE.parseSQL(type);
    }

    if (!"".equals(updateDefinition.where())) {
      sql = sql + " WHERE " + updateDefinition.where();
    }

    return sql;
  }

}

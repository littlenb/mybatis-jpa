package com.littlenb.mybatisjpa.statement;

import com.littlenb.mybatisjpa.annotation.InsertDefinition;
import com.littlenb.mybatisjpa.support.Constant;
import com.littlenb.mybatisjpa.support.template.InsertIgnoreNullSqlTemplate;
import com.littlenb.mybatisjpa.support.template.InsertCertainSqlTemplate;
import com.littlenb.mybatisjpa.support.template.InsertSqlTemplate;
import com.littlenb.mybatisjpa.type.SelectorStrategy;
import com.littlenb.mybatisjpa.util.PersistentUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * @author sway.li
 */
public class InsertStatementFactory extends AbstractStatementFactory {

  public static final StatementFactory INSTANCE = new InsertStatementFactory();

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
        sqlSource, SqlCommandType.INSERT);
    builder.resource(super.recognizeResource(targetClassName)).lang(languageDriver)
        .statementType(StatementType.PREPARED);

    // keyGenerator
    Field id = PersistentUtil.getIdField(type);
    String keyProperty = id.getName();
    if (SelectorStrategy.CERTAIN.equals(recognizeStrategy(method))) {
      keyProperty = Constant.CERTAINTY_ENTITY_KEY + "." + keyProperty;
    }
    if (id.isAnnotationPresent(GeneratedValue.class)) {
      GeneratedValue generatedValue = id.getAnnotation(GeneratedValue.class);
      if (GenerationType.AUTO.equals(generatedValue.strategy())) {
        builder.keyGenerator(new Jdbc3KeyGenerator()).keyProperty(keyProperty)
            .keyColumn(PersistentUtil.getColumnName(id));
      } else if (GenerationType.IDENTITY.equals(generatedValue.strategy())) {
        String generator = "".equals(generatedValue.generator()) ? Constant.DEFAULT_KEY_GENERATOR
            : generatedValue.generator();
        KeyGenerator keyGenerator = configuration.getKeyGenerator(generator);
        if (keyGenerator == null) {
          throw new IllegalArgumentException(
              "mybatis mybatisjpa init failure , can not find '" + generator
                  + "' in configuration.");
        }
        builder.keyGenerator(keyGenerator).keyProperty(keyProperty)
            .keyColumn(PersistentUtil.getColumnName(id));
      }
    }

    return builder.build();
  }

  private String parseSQL(Method method, Class<?> type) {
    SelectorStrategy strategy = recognizeStrategy(method);
    if (SelectorStrategy.IGNORE_NULL.equals(strategy)) {
      return InsertIgnoreNullSqlTemplate.INSTANCE.parseSQL(type);
    }

    if (SelectorStrategy.CERTAIN.equals(strategy)) {
      return InsertCertainSqlTemplate.INSTANCE.parseSQL(type);
    }

    return InsertSqlTemplate.INSTANCE.parseSQL(type);
  }

  private SelectorStrategy recognizeStrategy(Method method) {
    InsertDefinition insertDefinition = method.getAnnotation(InsertDefinition.class);
    if (insertDefinition == null) {
      throw new RuntimeException(
          "'" + method.getName() + "' method is not annotation with 'InsertDefinition'.");
    }
    return insertDefinition.strategy();
  }

}

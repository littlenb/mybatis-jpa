package com.littlenb.mybatisjpa.rs;

import com.littlenb.mybatisjpa.util.FieldReflectUtil;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;

/**
 * @author sway.li
 **/
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {
    Statement.class})})
public class ResultTypePlugin implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    if (invocation.getTarget() instanceof DefaultResultSetHandler) {
      DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) invocation.getTarget();

      Object[] args = invocation.getArgs();
      Statement stmt = (Statement) args[0];

      MappedStatement mappedStatement = (MappedStatement) FieldReflectUtil
          .getFieldValue(resultSetHandler, "mappedStatement");

      List<ResultMap> resultMaps = mappedStatement.getResultMaps();

      if (resultMaps != null && !resultMaps.isEmpty()) {
        ResultMap resultMap = resultMaps.get(0);

        if (resultMap.getResultMappings() == null || resultMap.getResultMappings().isEmpty()) {
          if (resultMap.getType().isAnnotationPresent(Entity.class) || resultMap.getType()
              .isAnnotationPresent(Table.class)) {

            ResultMapParser parser = ResultMapParserHolder
                .getInstance(mappedStatement.getConfiguration());
            ResultMap newResultMap = parser
                .reloadResultMap(mappedStatement.getResource(), resultMap.getId(),
                    resultMap.getType());

            List<ResultMap> newResultMaps = new ArrayList<>();
            newResultMaps.add(newResultMap);

            FieldReflectUtil.setFieldValue(mappedStatement, "resultMaps", newResultMaps);

            // modify the resultMaps
            FieldReflectUtil.setFieldValue(resultSetHandler, "mappedStatement", mappedStatement);
          }
        }
      }

      // return resultSetHandler.handleResultSets(stmt);
    }
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {

  }

  /**
   * Use static inner classes ensure thread safety
   */
  private static class ResultMapParserHolder {

    private static Map<String, ResultMapParser> parsers = new HashMap<>();

    static ResultMapParser getInstance(Configuration configuration) {
      String id = configuration.getEnvironment().getId();
      if (!parsers.containsKey(id)) {
        parsers.put(id, new ResultMapParser(configuration));
      }
      return parsers.get(id);
    }
  }

}

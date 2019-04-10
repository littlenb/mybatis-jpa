package com.mybatis.jpa.statement;

import java.lang.reflect.Method;

/**
 * @author svili
 **/
public interface StatementBuildable {

  void parseStatement(Method method, Class<?> targetClass);
}

package com.mybatis.jpa.statement;

import java.lang.reflect.Method;

/**
 * @author sway.li
 **/
public interface StatementFactory {

  void parseStatement(Method method, Class<?> targetClass);
}

package com.littlenb.mybatisjpa.statement;

import java.lang.reflect.Method;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

/**
 * @author sway.li
 **/
public interface StatementFactory {

  MappedStatement parseStatement(Configuration configuration, Method method, Class<?> targetClass);

}

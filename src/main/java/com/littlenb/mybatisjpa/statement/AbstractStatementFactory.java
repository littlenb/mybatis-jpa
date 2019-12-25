package com.littlenb.mybatisjpa.statement;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

/**
 * @author sway.li
 */
public abstract class AbstractStatementFactory implements StatementFactory {

  protected String recognizeResource(String targetClassName) {
    return targetClassName.replace(".", "/") + ".java (best guess)";
  }

  protected Class<?> recognizeEntityType(Method method, Class<?> targetClass) {
    Type[] genericTypes = method.getGenericParameterTypes();
    Type genericType = genericTypes[0];

    if (genericType instanceof TypeVariableImpl) {
      // interface XXXMapper extends IBaseMapper<T>
      Type[] interfaces = targetClass.getGenericInterfaces();
      for (Type type : interfaces) {
        if (type instanceof ParameterizedType) {
          ParameterizedType pt = (ParameterizedType) type;
          return (Class<?>) pt.getActualTypeArguments()[0];
        }
      }
    }

    if (genericType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) genericType;
      if (pt.getActualTypeArguments()[0] instanceof TypeVariableImpl) {
        // interface XXXMapper extends IBaseMapper<T>
        // insert(List<T> list)
        Type[] interfaces = targetClass.getGenericInterfaces();
        for (Type type : interfaces) {
          if (type instanceof ParameterizedType) {
            ParameterizedType pt2 = (ParameterizedType) type;
            return (Class<?>) pt2.getActualTypeArguments()[0];
          }
        }
      }
    }

    Class<?> actualType;
    if (genericType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) genericType;
      actualType = (Class<?>) pt.getActualTypeArguments()[0];
    } else {
      actualType = (Class<?>) genericType;
    }
    return actualType;
  }

  protected boolean isCollectionParameter(Method method) {
    Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes == null || parameterTypes.length == 0) {
      return false;
    }
    Class<?> clazz = parameterTypes[0];
    return Collection.class.isAssignableFrom(clazz);
  }

}

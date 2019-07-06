package com.littlenb.mybatisjpa.keygen;

import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

/**
 * @author sway.li
 */
public class IdentityKeyGenerator implements KeyGenerator {

  private IdGenerator idGenerator;

  public IdentityKeyGenerator(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  @Override
  public void processBefore(Executor executor, MappedStatement ms, Statement stmt,
      Object parameter) {
    processGeneratedKeys(ms, stmt, parameter);
  }

  @Override
  public void processAfter(Executor executor, MappedStatement ms, Statement stmt,
      Object parameter) {
    // do nothing
  }

  public void processGeneratedKeys(MappedStatement ms, Statement stmt, Object parameter) {
    final String[] keyProperties = ms.getKeyProperties();
    if (keyProperties == null || keyProperties.length == 0) {
      return;
    }
    if (keyProperties.length > 1) {
      throw new ExecutorException(
          "There are more than one keyProperty in MappedStatement : " + ms.getId() + ".");
    }

    if (parameter instanceof Collection) {
      handleCollection(ms, stmt, (Collection) parameter);
    } else if (parameter.getClass().isArray()) {
      handleCollection(ms, stmt, Arrays.asList(parameter));
    } else {
      handleObject(ms, stmt, parameter, idGenerator.nextId());
    }
  }

  private void handleCollection(MappedStatement ms, Statement stmt, Collection collection) {
    if (collection.isEmpty()) {
      return;
    }
    int size = collection.size();
    Object[] idArray = idGenerator.nextSegment(size);

    Iterator iterator = collection.iterator();
    for (int i = 0; i < size; i++) {
      Object parameter = iterator.next();
      Object id = idArray[i];
      handleObject(ms, stmt, parameter, id);
    }
  }

  private void handleObject(MappedStatement ms, Statement stmt, Object parameter, Object idValue) {
    final String[] keyProperties = ms.getKeyProperties();
    final Configuration configuration = ms.getConfiguration();
    final MetaObject metaParam = configuration.newMetaObject(parameter);
    setValue(metaParam, keyProperties[0], idValue);
  }

  private void setValue(MetaObject metaParam, String property, Object value) {
    if (metaParam.hasSetter(property)) {
      metaParam.setValue(property, value);
    } else {
      throw new ExecutorException(
          "No setter found for the keyProperty '" + property + "' in " + metaParam
              .getOriginalObject().getClass().getName() + ".");
    }
  }

}

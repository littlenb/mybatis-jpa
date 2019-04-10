package com.mybatis.jpa.foo;

import com.mybatis.jpa.annotation.InsertDefinition;
import com.mybatis.jpa.annotation.UpdateDefinition;

/**
 * @author svili
 **/
public interface IBaseMapper<T> {

  @InsertDefinition
  int insert(T t);

  @InsertDefinition(selective = true)
  int insertSelective(T t);

  @UpdateDefinition
  int updateById(T t);

  @UpdateDefinition(selective = true)
  int updateSelectiveById(T t);

}

package com.mybatis.jpa.mapper;

import com.mybatis.jpa.annotation.InsertDefinition;
import com.mybatis.jpa.annotation.UpdateDefinition;
import com.mybatis.jpa.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author svili
 **/
@Mapper
@Repository
public interface UserUpdateMapper {

  @InsertDefinition
  int insert(User user);

  @InsertDefinition(selective = true)
  int insertSelective(User user);

  @UpdateDefinition(where = " user_id = #{userId}")
  int updateById(User user);

  @UpdateDefinition(selective = true, where = " user_id = #{userId}")
  int updateSelectiveById(User user);
}

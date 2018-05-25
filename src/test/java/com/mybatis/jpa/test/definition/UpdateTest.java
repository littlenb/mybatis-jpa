package com.mybatis.jpa.test.definition;

import com.mybatis.jpa.mapper.UserUpdateMapper;
import com.mybatis.jpa.model.User;
import com.mybatis.jpa.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author svili
 **/
public class UpdateTest extends AbstractTest {

  @Autowired
  private UserUpdateMapper userUpdateMapper;

  @Test
  public void insert() {
    User user = new User();
    user.setUserId(118299928123543554L);
    user.setPassword("12345");
    userUpdateMapper.insert(user);
  }

  @Test
  public void insertSelective() {
    User user = new User();
    user.setUserId(118299928123543555L);
    user.setPassword("12345");
    userUpdateMapper.insertSelective(user);
  }

  @Test
  public void update() {
    long id = 12345L;
    User user = new User();
    user.setUserId(id);
    user.setPassword("update password 123456");
    userUpdateMapper.updateById(user);
  }

  @Test
  public void updateSelective() {
    long id = 118299928123543552L;
    User user = new User();
    user.setUserId(id);
    user.setPassword("update password 123456");
    userUpdateMapper.updateSelectiveById(user);
  }

}

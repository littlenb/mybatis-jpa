package com.mybatis.jpa.test.definition;

import com.mybatis.jpa.mapper.InheritUserUpdateMapper;
import com.mybatis.jpa.model.User;
import com.mybatis.jpa.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author svili
 **/
public class InheritUpdateTest extends AbstractTest {
  @Autowired
  private InheritUserUpdateMapper inheritUserUpdateMapper;

  @Test
  public void insert() {
    User user = new User();
    user.setId(118299928123543556L);
    user.setPassword("12345");
    inheritUserUpdateMapper.insert(user);
  }

  @Test
  public void insertSelective() {
    User user = new User();
    user.setId(118299928123543557L);
    user.setPassword("12345");
    inheritUserUpdateMapper.insertSelective(user);
  }

  @Test
  public void update() {
    long id = 118299928123543552L;
    User user = new User();
    user.setId(id);
    user.setPassword("update password 12345678");
    inheritUserUpdateMapper.updateById(user);
  }

  @Test
  public void updateSelective() {
    long id = 118299928123543552L;
    User user = new User();
    user.setId(id);
    user.setPassword("update password 123456");
    inheritUserUpdateMapper.updateSelectiveById(user);
  }
}

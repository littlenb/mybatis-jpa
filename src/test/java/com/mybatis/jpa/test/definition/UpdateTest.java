package com.mybatis.jpa.test.definition;

import com.mybatis.jpa.mapper.UserUpdateMapper;
import com.mybatis.jpa.model.User;
import com.mybatis.jpa.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sway.li
 **/
public class UpdateTest extends AbstractTest {

  @Autowired
  private UserUpdateMapper userUpdateMapper;

  @Test
  public void update() {
    long id = 118299928123543553L;
    User user = new User();
    user.setId(id);
    user.setPassword("update password 123456");
    userUpdateMapper.updateById(user);
  }

  @Test
  public void updateSelective() {
    long id = 118299928123543553L;
    User user = new User();
    user.setId(id);
    user.setPassword("update password 123456");
    userUpdateMapper.updateSelectiveById(user);
  }

}

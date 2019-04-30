package com.mybatis.jpa.test.definition;

import com.mybatis.jpa.mapper.UserUpdateMapper;
import com.mybatis.jpa.model.User;
import com.mybatis.jpa.test.AbstractTest;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sway.li
 **/
public class InsertTest extends AbstractTest {

  @Autowired
  private UserUpdateMapper userUpdateMapper;

  @Test
  public void insert() {
    User user = new User();
    user.setPassword("12345");
    user.setUniCode(UUID.randomUUID().toString());
    userUpdateMapper.insert(user);
    System.out.println(user.getId());
  }

  @Test
  public void insertSelective() {
    User user = new User();
    user.setPassword("12345");
    user.setUniCode(UUID.randomUUID().toString());
    userUpdateMapper.insertSelective(user);
    System.out.println(user.getId());
  }

}

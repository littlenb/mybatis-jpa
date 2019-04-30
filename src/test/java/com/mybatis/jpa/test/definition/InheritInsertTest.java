package com.mybatis.jpa.test.definition;

import com.mybatis.jpa.mapper.InheritUserMapper;
import com.mybatis.jpa.model.User;
import com.mybatis.jpa.test.AbstractTest;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sway.li
 **/
public class InheritInsertTest extends AbstractTest {

  @Autowired
  private InheritUserMapper inheritUserMapper;

  @Test
  public void insert() {
    User user = new User();
    // user.setId(118299928123543556L);
    user.setUniCode(UUID.randomUUID().toString());
    user.setPassword("12345");
    inheritUserMapper.insert(user);
    System.out.println(user.getId());
  }

  @Test
  public void insertSelective() {
    User user = new User();
    // user.setId(118299928123543557L);
    user.setUniCode(UUID.randomUUID().toString());
    user.setPassword("12345");
    inheritUserMapper.insertSelective(user);
    System.out.println(user.getId());
  }

}

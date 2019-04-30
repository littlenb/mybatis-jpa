package com.mybatis.jpa.test.definition;

import com.mybatis.jpa.mapper.InheritUserMapper;
import com.mybatis.jpa.model.User;
import com.mybatis.jpa.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sway.li
 **/
public class InheritUpdateTest extends AbstractTest {

  @Autowired
  private InheritUserMapper inheritUserMapper;

  @Test
  public void update() {
    long id = 118299928123543552L;
    User user = new User();
    user.setId(id);
    user.setPassword("update password 12345678");
    inheritUserMapper.updateById(user);
  }

  @Test
  public void updateSelective() {
    long id = 118299928123543552L;
    User user = new User();
    user.setId(id);
    user.setPassword("update password 123456");
    inheritUserMapper.updateSelectiveById(user);
  }
}

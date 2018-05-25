package com.mybatis.jpa.test.plugin;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.mybatis.jpa.mapper.UserQueryMapper;
import com.mybatis.jpa.model.User;
import com.mybatis.jpa.test.AbstractTest;

public class ResultTypeTest extends AbstractTest {

  @Resource
  private UserQueryMapper userQueryMapper;

  @Test
  public void selectById() {
    long id = 118299928123543552L;

    User user = userQueryMapper.selectById(id);
    System.out.println(JSON.toJSONString(user));

    // select twice,watch the resultMap reload times.
    User user2 = userQueryMapper.selectById(id);
    System.out.println(JSON.toJSONString(user2));
  }

  @Test
  public void selectOneToOne() {
    long id = 118299928123543552L;
    User user = userQueryMapper.selectOneToOne(id);
    System.out.println(JSON.toJSONString(user));
  }

  @Test
  public void selectOneToMany() {
    long id = 118299928123543552L;
    User user = userQueryMapper.selectOneToMany(id);
    System.out.println(JSON.toJSONString(user));
  }

  @Test
  public void selectUnion() {
    long id = 118299928123543552L;
    User user = userQueryMapper.selectUnion(id);
    System.out.println(JSON.toJSONString(user));
  }

}

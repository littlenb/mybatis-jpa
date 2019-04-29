package com.mybatis.jpa.test.keygenerator;

import com.mybatis.jpa.mapper.UserRoleRelationMapper;
import com.mybatis.jpa.model.UserRoleRelation;
import com.mybatis.jpa.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author svili
 **/
public class KeyGeneratorTest extends AbstractTest {

  @Autowired
  private UserRoleRelationMapper userRoleRelationMapper;

  @Test
  public void testSelective(){
    UserRoleRelation entity = new UserRoleRelation();
    entity.setRoleId(1L);
    entity.setUserId(1L);
    userRoleRelationMapper.insertSelective(entity);
    // test mysql auto increment key
    System.out.println(entity.getId());
  }

  @Test
  public void test(){
    UserRoleRelation entity = new UserRoleRelation();
    entity.setRoleId(1L);
    entity.setUserId(1L);
    userRoleRelationMapper.insert(entity);
    // test mysql auto increment key
    System.out.println(entity.getId());
  }

}

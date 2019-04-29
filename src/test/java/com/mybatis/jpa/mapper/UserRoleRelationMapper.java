package com.mybatis.jpa.mapper;

import com.mybatis.jpa.foo.IBaseMapper;
import com.mybatis.jpa.model.UserRoleRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author svili
 **/
@Mapper
@Repository
public interface UserRoleRelationMapper extends IBaseMapper<UserRoleRelation>{

}

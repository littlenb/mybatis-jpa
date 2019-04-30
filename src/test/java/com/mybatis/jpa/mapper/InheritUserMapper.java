package com.mybatis.jpa.mapper;

import com.mybatis.jpa.foo.IBaseMapper;
import com.mybatis.jpa.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author sway.li
 **/
@Mapper
@Repository
public interface InheritUserMapper extends IBaseMapper<User> {

}

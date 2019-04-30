package com.mybatis.jpa.mapper;

import com.mybatis.jpa.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author sway.li
 */
@Repository
public interface UserQueryMapper {

  @Select("SELECT * FROM t_sys_user WHERE id = #{id}")
  User selectById(long userId);

  @Select("SELECT t.*,a.* FROM t_sys_user t,t_sys_user_archive a WHERE t.id = a.id and t.id = #{id}")
  User selectOneToOne(long userId);

  @Select("SELECT t.*,r.* FROM t_sys_user t LEFT JOIN t_relation_user_role r ON t.id = r.user_id WHERE t.id = #{id}")
  User selectOneToMany(long userId);

  User selectUnion(long userId);

}

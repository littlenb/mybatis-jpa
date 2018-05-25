package com.mybatis.jpa.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.mybatis.jpa.model.User;

/**
 * @author svili
 */
@Repository
public interface UserQueryMapper {

	@Select("SELECT * FROM t_sys_user WHERE user_id = #{userId}")
	User selectById(long userId);

	@Select("SELECT t.*,a.* FROM t_sys_user t,t_sys_user_archive a WHERE t.user_id = a.user_id and t.user_id = #{userId}")
	User selectOneToOne(long userId);

	@Select("SELECT t.*,r.* FROM t_sys_user t LEFT JOIN t_relation_user_role r ON t.user_id = r.user_id WHERE t.user_id = #{userId}")
	User selectOneToMany(long userId);
	
	User selectUnion(long userId);

}

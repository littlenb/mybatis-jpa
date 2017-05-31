package com.ybg.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.mybatis.jpa.annotation.MapperDefinition;
import com.mybatis.jpa.annotation.StatementDefinition;
import com.mybatis.jpa.mapper.MybatisBaseMapper;
import com.ybg.model.User;

/**
 * test
 * 
 * @author svili
 * @data 2017年5月8日
 *
 */
@Repository
@MapperDefinition(domainClass = User.class)
public interface UserMapper extends MybatisBaseMapper<User> {

	@StatementDefinition
	int deleteByUserName(String userName);

	@StatementDefinition
	int updateByUserName(User user);

	@StatementDefinition
	int updateSelectiveByUserName(User user);

	@StatementDefinition
	List<User> selectByUserName(String userName);

	/* Like 的通配符需要自行添加 */
	@StatementDefinition
	List<User> selectByUserNameLike(String userName);

	@StatementDefinition
	List<User> selectByUserIdLessThan(Integer userId);

	@StatementDefinition
	List<User> selectByUserIdIsNull();

	/*more condition or complex SQL,need yourself build*/
	
	@Select("select * from ybg_test_user where user_name = #{userName} and dept_id = #{deptId}")
	@ResultMap(value="UserMap")
	List<User> selectComplex(Map<String, Object> args);
	
	List<User> selectComplex2(Map<String, Object> args);

}

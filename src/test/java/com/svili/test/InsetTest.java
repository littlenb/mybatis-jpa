package com.svili.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.svili.mapper.UserMapper;
import com.svili.model.User;
import com.svili.type.DataStateEnum;

public class InsetTest extends AbstractTest {
	
	@Resource
	protected UserMapper userMapper;

	//@Test
	public void insert() {
		User user = new User();
		user.setUserId(2);
		user.setDeptId(1);
		user.setUserName("svili_1");
		user.setLoginName("insert");
		user.setEmail("insert");
		user.setJob("insert");
		user.setPassword("insert");
		user.setState(DataStateEnum.UNEFFECT);
		user.setCreateTime(new java.util.Date());
		userMapper.insert(user);
	}

	@Test
	public void insertSelective() {
		User user = new User();
		user.setUserId(3);
		user.setDeptId(1);
		user.setUserName("svili_1");
		user.setLoginName("insert");
		user.setEmail("insert");
		user.setJob("insert");
		user.setPassword("insert");
		user.setState(DataStateEnum.UNEFFECT);
		user.setCreateTime(new java.util.Date());
		userMapper.insertSelective(user);
	}
}

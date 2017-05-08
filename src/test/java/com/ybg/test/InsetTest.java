package com.ybg.test;

import org.junit.Test;

import com.ybg.model.User;
import com.ybg.type.DataStateEnum;

public class InsetTest extends AbstractMapperTest {

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

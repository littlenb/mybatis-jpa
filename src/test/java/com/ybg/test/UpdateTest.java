package com.ybg.test;

import org.junit.Test;

import com.ybg.model.User;
import com.ybg.type.DataStateEnum;

public class UpdateTest extends AbstractMapperTest {

	// @Test
	public void updateSelectiveByPrimaryKey() {
		User user = new User();
		user.setUserId(1);
		user.setDeptId(1);
		user.setUserName("svili_1");
		user.setLoginName("updateSelectiveByPrimaryKey");
		user.setEmail("updateSelectiveByPrimaryKey");
		user.setJob("updateSelectiveByPrimaryKey");
		user.setPassword("updateSelectiveByPrimaryKey");
		user.setState(DataStateEnum.UNEFFECT);
		user.setCreateTime(new java.util.Date());
		userMapper.updateSelectiveByPrimaryKey(user);
	}

	// @Test
	public void updateByPrimaryKey() {
		User user = new User();
		user.setUserId(1);
		user.setDeptId(11);
		user.setUserName("svili_1");
		userMapper.updateByPrimaryKey(user);
	}

	//@Test
	public void updateByUserName() {
		User user = new User();
		user.setDeptId(1);
		user.setUserName("svili_1");
		user.setEmail("update by user name");
		user.setJob("update by user name");
		user.setLoginName("update by user name");
		user.setPassword("update by user name");
		user.setState(DataStateEnum.UNEFFECT);
		user.setCreateTime(new java.util.Date());
		userMapper.updateByUserName(user);
	}

	@Test
	public void updateSelectiveByUserName() {
		User user = new User();
		user.setDeptId(1);
		user.setUserName("svili_1");
		user.setEmail("updateSelectiveByUserName");
		user.setJob("updateSelectiveByUserName");
		user.setLoginName("updateSelectiveByUserName");
		user.setPassword("updateSelectiveByUserName");
		//user.setState(DataStateEnum.UNEFFECT);
		user.setCreateTime(new java.util.Date());
		userMapper.updateByUserName(user);
	}
}

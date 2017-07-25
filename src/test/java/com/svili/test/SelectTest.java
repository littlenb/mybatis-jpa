package com.svili.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svili.mapper.UserMapper;
import com.svili.model.User;

public class SelectTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SelectTest.class);
	
	@Resource
	protected UserMapper userMapper;

	@Test
	public void selectByPrimaryKey() {
		User user = userMapper.selectByPrimaryKey(1);
		if (user != null)
			LOGGER.info(user.toString());
	}

	// @Test
	public void select() {
		List<User> list = userMapper.select();
		System.out.println(list);
	}

	// @Test
	public void selectByUserName() {
		List<User> list = userMapper.selectByUserName("svili");
		System.out.println(list);
	}

	// @Test
	public void selectByUserNameLike() {
		List<User> list = userMapper.selectByUserNameLike("%svili%");
		System.out.println(list);
	}

	// @Test
	public void selectByUserIdLessThan() {
		List<User> list = userMapper.selectByUserIdLessThan(5);
		System.out.println(list);
	}

	// @Test
	public void selectByUserIdIsNull() {
		List<User> list = userMapper.selectByUserIdIsNull();
		System.out.println(list);
	}

//	@Test
	public void selectComplex() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("userName", "svili first one");
		args.put("deptId", 1);
		List<User> list = userMapper.selectComplex(args);
		System.out.println(list);
	}

	//@Test
	public void selectComplex2() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("userName", "svili first one");
		args.put("deptId", 1);
		List<User> list = userMapper.selectComplex2(args);
		System.out.println(list);
	}
}

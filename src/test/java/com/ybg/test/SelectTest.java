package com.ybg.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ybg.model.User;

public class SelectTest extends AbstractMapperTest {

	// @Test
	public void selectByPrimaryKey() {
		User user = userMapper.selectByPrimaryKey(1);
		System.out.println(user.toString());
	}

	@Test
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

	// @Test
	public void selectComplex() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("userName", "svili first one");
		args.put("deptId", 1);
		List<User> list = userMapper.selectComplex(args);
		System.out.println(list);
	}

	// @Test
	public void selectComplex2() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("userName", "svili first one");
		args.put("deptId", 1);
		List<User> list = userMapper.selectComplex2(args);
		System.out.println(list);
	}
}

package com.svili.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.svili.mapper.UserMapper;

public class DeleteTest extends AbstractTest {
	
	@Resource
	protected UserMapper userMapper;

	//@Test
	public void delete() {
		userMapper.deleteByPrimaryKey(2);
	}
	
	@Test
	public void deleteByUserName(){
		userMapper.deleteByUserName("svili_1");
	}
}

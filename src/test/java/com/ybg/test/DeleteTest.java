package com.ybg.test;

import org.junit.Test;

public class DeleteTest extends AbstractMapperTest {

	//@Test
	public void delete() {
		userMapper.deleteByPrimaryKey(2);
	}
	
	@Test
	public void deleteByUserName(){
		userMapper.deleteByUserName("svili_1");
	}
}

package com.ybg.test;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import com.ybg.mapper.UserMapper;
import com.ybg.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:configs/spring.xml" })
public abstract class AbstractMapperTest {

	static {
		try {
			Log4jConfigurer.initLogging("classpath:configs/log4j.properties");
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot Initialize log4j");
		}
	}

	@Resource
	protected UserMapper userMapper;

}

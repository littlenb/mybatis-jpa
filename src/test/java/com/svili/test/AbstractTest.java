package com.svili.test;

import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import com.svili.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:configs/spring.xml" })
public abstract class AbstractTest {

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

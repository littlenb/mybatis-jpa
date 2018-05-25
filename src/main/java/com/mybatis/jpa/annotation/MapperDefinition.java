package com.mybatis.jpa.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用该注解标记的Mapper将被加入statement解析队列
 * 
 * @author svili
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MapperDefinition {
	/** 持久化Entity类型 */
	Class<?> domainClass();
}

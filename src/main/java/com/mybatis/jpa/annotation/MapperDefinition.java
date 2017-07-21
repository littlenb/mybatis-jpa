package com.mybatis.jpa.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用该注解标记的Mapper将被加入statement解析队列,并在当前Mapper命名空间的 Mybatis Configuration中
 * ,注册一个以domainClass.getSimpleName() + "Map"命名的ResultMap
 * 
 * @author svili
 * @data 2017年5月8日
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MapperDefinition {
	/** 持久化Entity类型 */
	Class<?> domainClass();
}

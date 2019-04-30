package com.mybatis.jpa.annotation;

import com.mybatis.jpa.type.CodeType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The priority is higher than <code>Enumerated</code>
 *
 * @author sway.li
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CodeEnum {

  CodeType value() default CodeType.INT;

}

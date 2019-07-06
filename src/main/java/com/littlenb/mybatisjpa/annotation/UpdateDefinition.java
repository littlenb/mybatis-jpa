package com.littlenb.mybatisjpa.annotation;

import com.littlenb.mybatisjpa.type.SelectorStrategy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sway.li
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UpdateDefinition {

  SelectorStrategy strategy() default SelectorStrategy.NONE;

  /**
   * default id = #{id}
   */
  String where() default "id = #{id}";
}

package com.mybatis.jpa.type;


/**
 * 枚举类型扩展
 *
 * @param <T> Integer or String
 * @author sway.li
 */
public interface ICodeEnum<T> {

  T getCode();

}

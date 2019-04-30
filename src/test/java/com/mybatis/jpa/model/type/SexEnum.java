package com.mybatis.jpa.model.type;

import com.mybatis.jpa.type.ICodeEnum;

/***
 *
 * @author sway.li
 *
 */
public enum SexEnum implements ICodeEnum<Integer> {

  UNKNOW(2), MALE(1), FEMALE(0);

  private int code;

  SexEnum(int code) {
    this.code = code;
  }

  @Override
  public Integer getCode() {
    return code;
  }

}

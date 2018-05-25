package com.mybatis.jpa.model.type;

import com.mybatis.jpa.type.ICodeEnum;

/**
 * 政治面貌
 *
 * @author lishiwei
 */
public enum PoliticalEnum implements ICodeEnum<Integer> {

  /**
   * 群众
   */
  MASSES(1),
  /**
   * 共青团员
   */
  LEAGUE(2),
  /**
   * 中共预备党员
   */
  PROBATIONARY_COMMUNIST(3),
  /**
   * 中共党员
   */
  COMMUNIST(4);

  private int code;

  PoliticalEnum(int code) {
    this.code = code;
  }

  @Override
  public Integer getCode() {
    return code;
  }
}

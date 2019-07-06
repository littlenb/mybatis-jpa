package com.littlenb.mybatisjpa.type;

/**
 * @author sway.li
 */
public enum SelectorStrategy {

  /**
   * none
   */
  NONE,

  /**
   * ignore null
   */
  IGNORE_NULL,

  /**
   * @see com.littlenb.mybatisjpa.support.Certainty
   */
  CERTAIN
}

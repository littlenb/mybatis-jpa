package com.mybatis.jpa.foo;

import com.mybatis.jpa.keygen.IdGenerator;

/**
 * @author sway.li
 */
public class MyIdGenerator implements IdGenerator {

  /**
   * reset the <code>start</code> when start application
   */
  private long start = 118299928123543556L;

  @Override
  public synchronized Object nextId() {
    return start++;
  }

  @Override
  public synchronized Object[] nextSegment(int size) {
    Object[] segment = new Object[size];
    for(int i = 0 ; i< size ;i++){
      segment[i] = start++;
    }
    return segment;
  }
}

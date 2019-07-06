package com.littlenb.mybatisjpa.keygen;

/**
 * @author sway.li
 */
public interface IdGenerator {

  Object nextId();

  Object[] nextSegment(int size);
}

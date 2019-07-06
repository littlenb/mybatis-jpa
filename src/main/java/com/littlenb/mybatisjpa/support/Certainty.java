package com.littlenb.mybatisjpa.support;

import java.util.Collections;
import java.util.Set;

/**
 * @author sway.li
 */
public class Certainty<E> {

  private E entity;

  private Set<String> includes;

  private Set<String> excludes;

  public Certainty() {
  }

  public static <T> Certainty<T> includes(T entity, Set<String> fields) {
    Certainty<T> certainty = new Certainty<>();
    certainty.entity = entity;
    certainty.includes = fields;
    certainty.excludes = Collections.emptySet();
    return certainty;
  }

  public static <T> Certainty<T> excludes(T entity, Set<String> fields) {
    Certainty<T> certainty = new Certainty<>();
    certainty.entity = entity;
    certainty.includes = Collections.emptySet();
    certainty.excludes = fields;
    return certainty;
  }

  // getter

  public E getEntity() {
    return entity;
  }

  public Set<String> getIncludes() {
    return includes;
  }

  public Set<String> getExcludes() {
    return excludes;
  }
}

package com.littlenb.mybatisjpa.support;

import com.littlenb.mybatisjpa.util.NamingStrategy;

/**
 * @author sway.li
 */
public class MybatisJapConfiguration {

  private NamingStrategy tableNamingStrategy;

  private NamingStrategy columnNamingStrategy;

  private MybatisJapConfiguration(){
  }

  public static MybatisJapConfiguration getInstance(){
    return Holder.instance;
  }

  private static class Holder{
    private static MybatisJapConfiguration instance = new MybatisJapConfiguration();
  }

  public NamingStrategy getTableNamingStrategy() {
    return tableNamingStrategy;
  }

  public void setTableNamingStrategy(NamingStrategy tableNamingStrategy) {
    this.tableNamingStrategy = tableNamingStrategy;
  }

  public NamingStrategy getColumnNamingStrategy() {
    return columnNamingStrategy;
  }

  public void setColumnNamingStrategy(NamingStrategy columnNamingStrategy) {
    this.columnNamingStrategy = columnNamingStrategy;
  }
}

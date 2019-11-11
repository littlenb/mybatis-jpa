package com.littlenb.mybatisjpa.support;

/**
 * @author sway.li
 */
public class MybatisJapConfiguration {

  private boolean camelToUnderline;

  /**
   * @see Constant.DEFAULT_CASE_MODE
   */
  private int tableCaseMode;

  private int columnCaseMode;

  private MybatisJapConfiguration(){
  }

  public static MybatisJapConfiguration getInstance(){
    return Holder.instance;
  }

  private static class Holder{
    private static MybatisJapConfiguration instance = new MybatisJapConfiguration();
  }

  public boolean isCamelToUnderline() {
    return camelToUnderline;
  }

  public void setCamelToUnderline(boolean camelToUnderline) {
    this.camelToUnderline = camelToUnderline;
  }

  public int getTableCaseMode() {
    return tableCaseMode;
  }

  public void setTableCaseMode(int tableCaseMode) {
    this.tableCaseMode = tableCaseMode;
  }

  public int getColumnCaseMode() {
    return columnCaseMode;
  }

  public void setColumnCaseMode(int columnCaseMode) {
    this.columnCaseMode = columnCaseMode;
  }
}

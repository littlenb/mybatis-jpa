package com.mybatis.jpa.model;

import com.mybatis.jpa.annotation.CodeEnum;
import com.mybatis.jpa.model.type.PoliticalEnum;
import com.mybatis.jpa.model.type.SexEnum;
import com.mybatis.jpa.type.CodeType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 *
 * @author svili
 *
 */
@Table(name = "t_sys_user_archive")
public class UserArchive {

  @Id
  private Long id;

  /**
   * 姓名
   */
  private String userName;

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 性别
   */
  @Enumerated(EnumType.ORDINAL)
  private SexEnum sex;

  /**
   * 政治面貌
   */
  @CodeEnum(CodeType.INT)
  private PoliticalEnum political;

  /**
   * 民族
   */
  private String race;

  /**
   * 手机号码
   */
  private String mobilePhone;

  /**
   * 固定电话/办公电话
   */
  private String officePhone;

  /**
   * 电子邮箱
   */
  private String email;

  /**
   * 创建时间
   */
  @Column(name = "gmt_create")
  private Date createTime;

  /**
   * 更新时间
   */
  @Column(name = "gmt_modify")
  private Date modifyTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public SexEnum getSex() {
    return sex;
  }

  public void setSex(SexEnum sex) {
    this.sex = sex;
  }

  public PoliticalEnum getPolitical() {
    return political;
  }

  public void setPolitical(PoliticalEnum political) {
    this.political = political;
  }

  public String getRace() {
    return race;
  }

  public void setRace(String race) {
    this.race = race;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getOfficePhone() {
    return officePhone;
  }

  public void setOfficePhone(String officePhone) {
    this.officePhone = officePhone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }
}

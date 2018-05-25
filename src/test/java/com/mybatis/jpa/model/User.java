package com.mybatis.jpa.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/***
 *
 * @author svili
 *
 */
@Entity
@Table(name = "t_sys_user")
public class User {

  @Id
  private Long userId;

  /**
   * 用户识别码,唯一
   */
  private String uniCode;

  /**
   * 密码
   */
  @Column(name = "password_encrypt")
  private String password;

  /**
   * 是否授权
   */
  @Column(name = "is_granted")
  private Boolean granted;

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

  @OneToOne(mappedBy = "user_id")
  private UserArchive archive;

  @OneToMany(mappedBy = "user_id")
  private List<UserRoleRelation> roles;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUniCode() {
    return uniCode;
  }

  public void setUniCode(String uniCode) {
    this.uniCode = uniCode;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getGranted() {
    return granted;
  }

  public void setGranted(Boolean granted) {
    this.granted = granted;
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

  public UserArchive getArchive() {
    return archive;
  }

  public void setArchive(UserArchive archive) {
    this.archive = archive;
  }

  public List<UserRoleRelation> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRoleRelation> roles) {
    this.roles = roles;
  }
}

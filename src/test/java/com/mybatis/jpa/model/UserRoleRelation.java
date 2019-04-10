package com.mybatis.jpa.model;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author svili
 **/
@Table(name = "t_relation_user_role")
public class UserRoleRelation {

  @Id
  private Long id;

  private Long userId;

  private Long roleId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
}

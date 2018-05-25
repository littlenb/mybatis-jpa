package com.mybatis.jpa.model;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author svili
 **/
@Table(name = "t_relation_user_role")
public class UserRoleRelation {

  @Id
  private Long relationId;

  private Long userId;

  private Long roleId;

  public Long getRelationId() {
    return relationId;
  }

  public void setRelationId(Long relationId) {
    this.relationId = relationId;
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

package com.ybg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSON;
import com.ybg.type.DataStateEnum;

/**
 * 用户信息
 * 
 * @Table和Id是必须的
 * 
 * @author svili
 * @date 2016年8月10日
 *
 */
@Entity
/* {@Table}非必须,若无此注解,将类名解析为下划线风格 做为表名 */
@Table(name = "user")
public class User {

	/* 非持久化字段 */
	@Transient
	private static final long serialVersionUID = -7788405797990662048L;

	/* {@Id}必须,主键标识,{@Column}非必须,若无此注解,或其name="",将字段名解析为下划线风格 做为SQL列名 */
	@Id
	@Column(name = "user_Id")
	private Integer userId;

	@Column(name = "dept_Id")
	private Integer deptId;

	@Column(name = "user_Name")
	private String userName;

	@Column(name = "login_Name")
	private String loginName;

	@Column(name = "password_alias")
	private String password;

	@Column(name = "mobile_Phone")
	private String mobilePhone;

	@Column(name = "office_Phone")
	private String officePhone;

	@Column(name = "email")
	private String email;

	@Column(name = "job")
	private String job;

	@Column(name = "order_Id")
	private Integer orderId;

	/* {@Enumerated}非必须,若无此注解,按照Mybatis约定,枚举类型使用{@EnumTypeHandler}解析 */
	@Enumerated
	@Column(name = "state")
	private DataStateEnum state;

	@Column(name = "create_Time")
	private java.util.Date createTime;

	@Column(name = "update_Time")
	private java.util.Date updateTime;

	public User() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public DataStateEnum getState() {
		return state;
	}

	public void setState(DataStateEnum state) {
		this.state = state;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}

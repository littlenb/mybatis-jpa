package com.mybatis.jpa.type;

/**
 * sql where语句中条件操作符</br>
 * 用以区分/识别mapper中mehtodName的条件操作符</br>
 * EQUAL为默认值,即如果没有解析到条件操作符,按照EQUAL处理.</br>
 * example:selectByPrimaryKey means : where primaryKey = :primaryKey
 * 
 * @author svili
 * @data 2017年5月8日
 *
 */
public enum OperateEnum {
	LESSTHAN("LessThan", " < "), 
	GREATERTHAN("GreaterThan", " > "), 
	ISNULL("IsNull", " is null "), 
	NOTNULL("NotNull"," is not null "), 
	NOTLIKE("NotLike", " not like "), 
	LIKE("Like"," like "), 
	EQUAL("Is", " = "), 
	NOT("Not", " != "), 
	NOTIN("NotIn", " not in "), 
	IN("In", " in ");

	private String alias;

	private String operate;

	private OperateEnum(String alias, String operate) {
		this.alias = alias;
		this.operate = operate;
	}

	public String getAlias() {
		return this.alias;
	}

	public String getOperate() {
		return this.operate;
	}
}

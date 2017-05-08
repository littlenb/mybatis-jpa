package com.ybg.type;

/**
 * 数据有效性
 * 
 * @author lishiwei
 * @date 2016年8月26日
 *
 */
public enum DataStateEnum {
	
	/**
	 * 无效
	 */
	UNEFFECT(0, "无效"),
	
	/**
	 * 有效
	 */
	EFFECT(1, "有效");
	

	private int code;

	private String description;

	private DataStateEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public int toCode(){
		return this.code;
	}

}

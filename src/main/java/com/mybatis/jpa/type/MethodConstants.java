package com.mybatis.jpa.type;

/**
 * mapper中method类型定义</br>
 * 需按照以下常量开头定义methodName,如:updateSelectiveByPrimaryKeyNotNull。
 * 
 * @author svili
 *
 */
public class MethodConstants {

	/** insert非空字段 */
	public static final String INSERT_SELECTIVE = "insertSelective";

	public static final String INSERT = "insert";

	public static final String BATCH_INSERT = "batchInsert";

	public static final String DELETE = "delete";

	public static final String UPDATE_SELECTIVE = "updateSelective";

	public static final String UPDATE = "update";

	public static final String BATCH_UPDATE = "batchUpdate";

	public static final String SELECT_PAGE = "selectPage";

	public static final String SELECT = "select";

}

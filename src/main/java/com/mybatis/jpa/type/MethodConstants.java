package com.mybatis.jpa.type;

/**
 * mapper中method类型定义</br>
 * 需按照以下常量开头定义methodName,如:updateSelectiveByPrimaryKeyNotNull。
 * 
 * @author svili
 * @data 2017年5月8日
 *
 */
public class MethodConstants {

	/** insert非空字段 */
	public final static String INSERT_SELECTIVE = "insertSelective";

	public final static String INSERT = "insert";

	public final static String BATCH_INSERT = "batchInsert";

	public final static String DELETE = "delete";

	public final static String UPDATE_SELECTIVE = "updateSelective";

	public final static String UPDATE = "update";

	public final static String BATCH_UPDATE = "batchUpdate";

	public final static String SELECT_PAGE = "selectPage";

	public final static String SELECT = "select";

}

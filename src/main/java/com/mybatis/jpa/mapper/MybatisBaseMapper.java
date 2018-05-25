package com.mybatis.jpa.mapper;

import java.util.List;

import com.mybatis.jpa.annotation.StatementDefinition;

/**
 * 规范通用Mapper方法签名</br>
 * 
 * 子类Mapper通过继承此接口获取方法签名,并使用{@MapperDefinition}注解标记Mapper.</br>
 * 使用{@StatementDefinition}注解标记的方法,将被解析并注入到Mybatis Statement</br>
 * 
 * about deference with insert(){@link #insert(Object)} and insertSelective()
 * {@link #insertSelective(Object)}</br>
 * 方法的区别在于null值的处理,假设column_1在数据库设置了默认值,而参数中的field_1为null值,则insert
 * 在数据库写入null,而insertSelective写入数据库默认值.
 * 
 * @attation 并非要继承此接口才能使用mybatis-jpa,事实上它只是定义了公共的Mapper方法签名,便于代码风格的统一
 * 
 * 
 * @author svili
 *
 * @param <T>持久化Entity类型
 */
public interface MybatisBaseMapper<T> {

	@StatementDefinition
	int insertSelective(T entity);

	@StatementDefinition
	int insert(T entity);

	// @StatementDefinition
	int batchInsert(List<T> list);

	@StatementDefinition
	int deleteById(Object primaryValue);

	@StatementDefinition
	int updateById(T entity);

	@StatementDefinition
	int updateSelectiveById(T entity);

	// @StatementDefinition
	int batchUpdate(T entity);

	@StatementDefinition
	T selectById(Object primaryValue);

	@StatementDefinition
	List<T> select();
}

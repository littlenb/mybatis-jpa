package com.mybatis.jpa.core;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.annotation.MethodResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.type.filter.TypeFilter;

import com.mybatis.jpa.annotation.MapperDefinition;
import com.mybatis.jpa.common.scanner.AnnotationTypeFilterBuilder;
import com.mybatis.jpa.common.scanner.SpringClassScanner;

/**
 * scanner to MapperDefinition and enhance it</br>
 * execute when an {@code ApplicationContext} gets initialized or refreshed</br>
 * 
 * @author svili
 * @data 2017年5月8日
 *
 */
public class MapperEnhancerScaner implements ApplicationListener<ApplicationEvent> {

	/* 初始化参数:mapper package base place */
	private String basePackage;

	/* 初始化参数:sqlSessionFactory */
	private SqlSessionFactory sqlSessionFactory;

	// setter
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (!(event instanceof ContextRefreshedEvent)) {
			return;
		}
		// mybatis configuration
		Configuration configuration = this.sqlSessionFactory.getConfiguration();
		
		

		/** scan **/
		TypeFilter typeFilter = AnnotationTypeFilterBuilder.build(MapperDefinition.class);
		SpringClassScanner scanner = new SpringClassScanner.Builder().scanPackage(this.basePackage)
				.typeFilter(typeFilter).build();
		Set<Class<?>> mapperSet = null;
		try {
			mapperSet = scanner.scan();
		} catch (ClassNotFoundException | IOException e) {
			// log or throw runTimeExp
			e.printStackTrace();
		}
		if (mapperSet != null && !mapperSet.isEmpty()) {
			for (Class<?> mapper : mapperSet) {
				// mapper enhance
				PersistentMapperEnhancer mapperEnhancer = new PersistentMapperEnhancer(configuration, mapper);
				mapperEnhancer.enhance();
			}
			parsePendingMethods(configuration);
		}

	}
	
	private void parsePendingMethods(Configuration configuration) {
		Collection<MethodResolver> incompleteMethods = configuration.getIncompleteMethods();
		synchronized (incompleteMethods) {
			Iterator<MethodResolver> iter = incompleteMethods.iterator();
			while (iter.hasNext()) {
				try {
					iter.next().resolve();
					iter.remove();
				} catch (IncompleteElementException e) {
					// This method is still missing a resource
				}
			}
		}
	}

}

package com.mybatis.jpa.core;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;

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
 *
 */
public class PersistentEnhancerScaner implements ApplicationListener<ApplicationEvent> {

	/** 初始化参数:mapper package base place */
	private String mapperPackage;

	/** 初始化参数:entity package base place */
	private String entityPackage;

	/** 初始化参数:sqlSessionFactory */
	private SqlSessionFactory sqlSessionFactory;

	// setter
	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (!(event instanceof ContextRefreshedEvent)) {
			return;
		}

		// root application context 没有parent.
		if (((ContextRefreshedEvent) event).getApplicationContext().getParent() != null) {
			return;
		}

		// mybatis configuration
		Configuration configuration = this.sqlSessionFactory.getConfiguration();

		/** scan entity **/
		TypeFilter entityFilter = AnnotationTypeFilterBuilder.build(Entity.class);
		SpringClassScanner entityScanner = new SpringClassScanner.Builder().scanPackage(this.entityPackage)
				.typeFilter(entityFilter).build();
		Set<Class<?>> entitySet = null;
		try {
			entitySet = entityScanner.scan();
		} catch (ClassNotFoundException | IOException e) {
			// log or throw runTimeExp
			throw new RuntimeException(e);
		}
		if (entitySet != null && !entitySet.isEmpty()) {
			for (Class<?> entity : entitySet) {
				// resultMap enhance
				PersistentResultMapEnhancer resultMapEnhancer = new PersistentResultMapEnhancer(configuration, entity);
				resultMapEnhancer.enhance();
			}
			// parsePendingMethods(configuration);
		}

		/** scan **/
		TypeFilter typeFilter = AnnotationTypeFilterBuilder.build(MapperDefinition.class);
		SpringClassScanner scanner = new SpringClassScanner.Builder().scanPackage(this.mapperPackage)
				.typeFilter(typeFilter).build();
		Set<Class<?>> mapperSet = null;
		try {
			mapperSet = scanner.scan();
		} catch (ClassNotFoundException | IOException e) {
			// log or throw runTimeExp
			throw new RuntimeException(e);
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

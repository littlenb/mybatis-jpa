package com.mybatis.jpa.definition.adaptor;

import com.mybatis.jpa.definition.property.AnnotationProperty;
import com.mybatis.jpa.definition.template.SqlTemplate;
import java.lang.annotation.Annotation;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * @author svili
 **/
public interface AnnotationAdaptable {

  AnnotationProperty context(Annotation annotation);

  SqlTemplate sqlTemplate(Annotation annotation);

  SqlCommandType sqlCommandType();

}

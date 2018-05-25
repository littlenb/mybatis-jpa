package com.mybatis.jpa.foo;

import com.mybatis.jpa.statement.DefinitionStatementBuilder;
import com.mybatis.jpa.statement.StatementBuildable;
import com.mybatis.jpa.statement.DefinitionStatementScanner;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author svili
 **/
@Service
public class DefinitionStatementInit {

  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  @PostConstruct
  public void init() {
    Configuration configuration = sqlSessionFactory.getConfiguration();
    StatementBuildable statementBuildable = new DefinitionStatementBuilder(configuration);
    DefinitionStatementScanner.Builder builder = new DefinitionStatementScanner.Builder();
    DefinitionStatementScanner definitionStatementScanner = builder.configuration(configuration)
        .basePackages(new String[]{"com.mybatis.jpa.mapper"})
        .statementBuilder(statementBuildable).build();
    definitionStatementScanner.scan();
  }
}

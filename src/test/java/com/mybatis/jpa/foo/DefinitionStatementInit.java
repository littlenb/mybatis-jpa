package com.mybatis.jpa.foo;

import com.mybatis.jpa.keygen.IdGenerator;
import com.mybatis.jpa.keygen.IdentityKeyGenerator;
import com.mybatis.jpa.statement.DefinitionStatementFactory;
import com.mybatis.jpa.statement.DefinitionStatementScanner;
import com.mybatis.jpa.statement.StatementFactory;
import javax.annotation.PostConstruct;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sway.li
 **/
@Service
public class DefinitionStatementInit {

  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  @PostConstruct
  public void init() {
    Configuration configuration = sqlSessionFactory.getConfiguration();
    KeyGenerator keyGenerator = new IdentityKeyGenerator(new MyIdGenerator());
    configuration.addKeyGenerator("defaultKeyGenerator",keyGenerator);
    StatementFactory statementFactory = new DefinitionStatementFactory(configuration);
    DefinitionStatementScanner definitionStatementScanner = new DefinitionStatementScanner.Builder()
        .configuration(configuration)
        .basePackages(new String[]{"com.mybatis.jpa.mapper"})
        .statementFactory(statementFactory).build();
    definitionStatementScanner.scan();
  }
}

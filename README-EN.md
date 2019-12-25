# mybatis-jpa

[![Mybatis](https://img.shields.io/badge/mybatis-3.4.x-brightgreen.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis)
[![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg)]()
[![maven central](https://img.shields.io/badge/version-2.2.0-brightgreen.svg)](http://search.maven.org/#artifactdetails%7Ccom.github.cnsvili%7Cmybatis-jpa%7C2.1.3%7C)
[![APACHE 2 License](https://img.shields.io/badge/license-Apache2-blue.svg?style=flat)](LICENSE)

:book: English Documentation | [:book: 中文文档](README.md)

The plugins for mybatis, in order to provider the ability to handler jpa.

## maven

```xml
        <dependency>
            <groupId>com.littlenb</groupId>
            <artifactId>mybatis-jpa</artifactId>
            <version>2.2.0</version>
        </dependency>
```

## Plugin boom

+ ResultTypePlugin [![Interceptor](https://img.shields.io/badge/Interceptor-brightgreen.svg)](https://github.com/svili365/mybatis-jpa/wiki/ResultTypePlugin)
  
  Introduce the JPA annotation to handle result set mappings(JavaBean/POJO).
  
  It means with ResultTypePlugin,no longer need to be build ResultMap.

+ AnnotationStatementScanner [![statementFactory](https://img.shields.io/badge/StatementFactory-brightgreen.svg)](https://github.com/svili365/mybatis-jpa/wiki/DefinitionStatementScanner)

  register MappedStatement with annotation-based,only support for Insert and Update.


Please view test project where has more examples[mybatis-jpa-test](https://github.com/svili365/mybatis-jpa-test)
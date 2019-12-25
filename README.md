# mybatis-jpa

[![Mybatis](https://img.shields.io/badge/mybatis-3.4.x-brightgreen.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis)
[![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg)]()
[![maven central](https://img.shields.io/badge/version-2.2.0-brightgreen.svg)](http://search.maven.org/#artifactdetails%7Ccom.github.cnsvili%7Cmybatis-jpa%7C2.1.3%7C)
[![APACHE 2 License](https://img.shields.io/badge/license-Apache2-blue.svg?style=flat)](LICENSE)

[:book: English Documentation](README-EN.md) | :book: 中文文档

Mybatis插件，提供Mybatis处理JPA的能力.

## maven

```xml
        <dependency>
            <groupId>com.littlenb</groupId>
            <artifactId>mybatis-jpa</artifactId>
            <version>2.2.0</version>
        </dependency>
```

## 插件清单

+ ResultTypePlugin [![Interceptor](https://img.shields.io/badge/Interceptor-brightgreen.svg)](https://github.com/svili365/mybatis-jpa/wiki/ResultTypePlugin)

  对于常规的结果映射, 不需要再构建ResultMap, ResultTypePlugin按照(JavaBean/POJO)中JPA注解, 解析生成相应的ResultMap.

+ AnnotationStatementScanner [![statementFactory](https://img.shields.io/badge/StatementFactory-brightgreen.svg)](https://github.com/svili365/mybatis-jpa/wiki/DefinitionStatementScanner)

  注册MappedStatement, 基于注解, 仅支持Insert和Update.


更多示例请查看[mybatis-jpa-test](https://github.com/svili365/mybatis-jpa-test)

## 联系方式
QQ交流群:246912326

# mybatis-jpa

[![Mybatis](https://img.shields.io/badge/mybatis-3.4.x-brightgreen.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis)
[![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg)]()
[![maven central](https://img.shields.io/badge/version-2.2.0-brightgreen.svg)](http://search.maven.org/#artifactdetails%7Ccom.github.cnsvili%7Cmybatis-jpa%7C2.1.3%7C)
[![APACHE 2 License](https://img.shields.io/badge/license-Apache2-blue.svg?style=flat)](LICENSE)

[:book: English Documentation](README-EN.md) | :book: 中文文档

Mybatis插件，提供Mybatis处理JPA的能力。

## maven

```xml
        <dependency>
            <groupId>com.littlenb</groupId>
            <artifactId>mybatis-jpa</artifactId>
            <version>2.2.0</version>
        </dependency>
```

## 插件清单

+ ResultTypePlugin [![plugin](https://img.shields.io/badge/plugin-resolved-green.svg)]()

+ AnnotationStatementScanner [![plugin](https://img.shields.io/badge/plugin-resolved-green.svg)]()

### ResultTypePlugin

对于常规的结果映射,不需要再构建ResultMap,ResultTypePlugin增加了Mybatis对结果映射(JavaBean/POJO)中JPA注解的处理。

映射规则：

+ 名称匹配默认与mybatis全局配置一致

  可以在mybatis全局配置文件中，开启驼峰(Java Field)与下划线(SQL Column)的匹配规则.

```xml
<settings>
    <!-- default : false -->
		<setting name="mapUnderscoreToCamelCase" value="true" />
</settings>
```

+ 使用@Column注解中name属性指定SQL Column

+ 使用@Transient注解标记非持久化字段(不需要结果集映射的字段)

类型处理:

+ Boolean-->BooleanTypeHandler

+ Enum默认为EnumTypeHandler

  使用@Enumerated(EnumType.ORDINAL) 指定为 EnumOrdinalTypeHandler

+ Enum实现ICodeEnum接口实现自定义枚举值

  使用@CodeEnum(CodeType.INT) 指定为 IntCodeEnumTypeHandler
  
  或@CodeEnum(CodeType.STRING) 指定为 StringCodeEnumTypeHandler
  
  @CodeEnum 优先级 高于 @Enumerated

结果集嵌套:

+ 支持OneToOne
+ 支持OneToMany

e.g.

mybatis.xml

```xml
<configuration>
    <plugins>
		<plugin interceptor="com.littlenb.mybatisjpa.rs.ResultTypePlugin">
		</plugin>
	</plugins>
</configuration>
```

JavaBean

```JAVA
@Entity
public class UserArchive {// <resultMap id="xxx" type="userArchive">

    @Id
    private Long id;// <id property="id" column="id" />
                           
    /** 默认驼峰与下划线转换 */
    private String userName;// <result property="username" column="user_name"/>

    /** 枚举类型 */
    @Enumerated(EnumType.ORDINAL)
    private SexEnum sex;// <result property="sex" column="sex" typeHandler=EnumOrdinalTypeHandler/>
    
    /** 枚举类型,自定义值 */
    @CodeEnum(CodeType.INT)
    private PoliticalEnum political;// <result property="political" column="political" typeHandler=IntCodeEnumTypeHandler/>
    
    /** 属性名与列名不一致 */
    @Column(name = "gmt_create")
    private Date createTime;// <result property="createTime" column="gmt_create"/>
}// </resultMap>
```

mapper.xml

```xml
<!-- in xml,declare the resultType -->
<select id="selectById" resultType="userArchive">
	SELECT * FROM t_sys_user_archive WHERE id = #{id}
</select>
```

### AnnotationStatementScanner

注册MappedStatement,基于注解,仅支持Insert和Update。

#### InsertDefinition:

+ selective: 默认值false(处理null属性)

#### updateDefinition:

+ selective: 默认值false(处理null属性)

+ where: SQL condition

e.g.

Spring 容器初始化完成后执行

```java
@Component
public class AnnotationStatementInit {

  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  @PostConstruct
  public void init() {
    Configuration configuration = sqlSessionFactory.getConfiguration();
    KeyGenerator keyGenerator = new IdentityKeyGenerator(new MyIdGenerator());
    configuration.addKeyGenerator(Constant.DEFAULT_KEY_GENERATOR, keyGenerator);
    AnnotationStatementScanner annotationStatementScanner = new AnnotationStatementScanner.Builder()
        .configuration(configuration)
        .basePackages(new String[]{"com.littlenb.mybatisjpa.demo.mapper"})
        .annotationStatementRegistry(AnnotationStatementRegistry.getDefaultRegistry()).build();
    annotationStatementScanner.scan();
  }
}
```

Mapper

```Java
@Mapper
@Repository
public interface UserUpdateMapper {

    @InsertDefinition(strategy = SelectorStrategy.IGNORE_NULL)
    int insert(User user);

    @UpdateDefinition(strategy = SelectorStrategy.IGNORE_NULL, where = " id = #{id}")
    int updateById(User user);
}
```

Best Advice

```java
/**
* Definition a Generic Interface as BaseMapper 
*/
public interface IBaseMapper<T> {

    @InsertDefinition
    int insert(T t);
  
    @InsertDefinition(strategy = SelectorStrategy.IGNORE_NULL)
    int insertIgnoreNull(T t);
  
    @InsertDefinition(strategy = SelectorStrategy.CERTAIN)
    int insertCertain(Certainty<T> certainty);
  
    @UpdateDefinition
    int updateById(T t);
  
    @UpdateDefinition(strategy = SelectorStrategy.IGNORE_NULL)
    int updateByIdIgnoreNull(T t);
  
    @UpdateDefinition(strategy = SelectorStrategy.CERTAIN, where = " id = #{entity.id}")
    int updateByIdCertain(Certainty<T> certainty);

}

/**
* extends BaseMapper
*/
@Mapper
@Repository
public interface UserMapper extends IBaseMapper<User> {

}
```

更多示例请查看test

## 联系方式
QQ交流群:246912326

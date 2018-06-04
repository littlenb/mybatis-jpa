# mybatis-jpa

[![Mybatis](https://img.shields.io/badge/mybatis-3.4.x-brightgreen.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis)
[![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg)]()
[![maven central](https://img.shields.io/badge/version-2.1.1-brightgreen.svg)](http://search.maven.org/#artifactdetails%7Ccom.github.cnsvili%7Cmybatis-jpa%7C2.1.1%7C)
[![APACHE 2 License](https://img.shields.io/badge/license-Apache2-blue.svg?style=flat)](LICENSE)

:book: English Documentation | [:book: 中文文档](README.md)

The plugins for mybatis, in order to provider the ability to handler jpa.

## maven

```xml
        <dependency>
            <groupId>com.littlenb</groupId>
            <artifactId>mybatis-jpa</artifactId>
            <version>2.1.1</version>
        </dependency>
```

## Plugin boom

+ ResultTypePlugin [![plugin](https://img.shields.io/badge/plugin-resolved-green.svg)]()

+ UpdatePlugin [![plugin](https://img.shields.io/badge/plugin-resolved-green.svg)]()

### ResultTypePlugin

Introduce the JPA annotation to handle result set mappings(JavaBean/POJO).

It means with ResultTypePlugin,no longer need to be build ResultMap.

Mapping rules：

+ default name mapping rule is the same as mybatis global config.

  you can setting mapping rule in mybatis-config.xml with camel(Java Field) to underline(SQL Column)

```xml
<settings>
    <!-- default : false -->
		<setting name="mapUnderscoreToCamelCase" value="true" />
</settings>
```

+ to specify SQL Column,declare the property "name" in @Column annotation

+ declare the no mapping field with @Transient annotation

TypeHandler:

+ Boolean-->BooleanTypeHandler

+ Enum is default with EnumTypeHandler

  @Enumerated(EnumType.ORDINAL) --> EnumOrdinalTypeHandler

+ implement ICodeEnum to achieve custom Enum value

  @CodeEnum(CodeType.INT) --> IntCodeEnumTypeHandler
  
  @CodeEnum(CodeType.STRING) --> StringCodeEnumTypeHandler
  
  @CodeEnum priority above than @Enumerated

nested result set:

+ @OneToOne

+ @OneToMany

e.g.

mybatis.xml

```xml
<configuration>
    <plugins>
		<plugin interceptor="com.mybatis.jpa.plugin.ResultTypePlugin">
		</plugin>
	</plugins>
</configuration>
```

JavaBean

```JAVA
@Entity
public class UserArchive {// <resultMap id="xxx" type="userArchive">

    @Id
    private Long userId;// <id property="id" column="user_id" />
                           
    /** default mapping rule is camel(Java Field) to underline(SQL Column) */
    private String userName;// <result property="username" column="user_name"/>

    /** enum type */
    @Enumerated(EnumType.ORDINAL)
    private SexEnum sex;// <result property="sex" column="sex" typeHandler=EnumOrdinalTypeHandler/>
    
     /** enum type,custom value */
     @CodeEnum(CodeType.INT)
     private PoliticalEnum political;// <result property="political" column="political" typeHandler=IntCodeEnumTypeHandler/>

    /** java field differ from sql column in name */
    @Column(name = "gmt_create")
    private Date createTime;// <result property="createTime" column="gmt_create"/>
}// </resultMap>
```

mapper.xml

```xml
<!-- in xml,declare the resultType -->
<select id="selectById" resultType="userArchive">
	SELECT * FROM t_sys_user_archive WHERE user_id = #{userId}
</select>
```

### DefinitionStatementScanner

register MappedStatement with annotation-based,only support for Insert and Update.

#### InsertDefinition:

+ selective: default value is false(handler null of java field)

#### updateDefinition:

+ selective: default value is false(handler null of java field)

+ where: SQL condition

### Mapping rules and TypeHandler 

+ if the field is no need resolve in SQL,declare the property "insertable" "updateable" in @Column.

+ the same as above(ResultTypePlugin rules)

e.g.

after Spring init

```java
@Service
public class DefinitionStatementInit {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void init() {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        StatementBuildable statementBuildable = new DefinitionStatementBuilder(configuration);
        DefinitionStatementScanner.Builder builder = new DefinitionStatementScanner.Builder();
        DefinitionStatementScanner definitionStatementScanner = builder.configuration(configuration).basePackages(new String[]{"com.mybatis.jpa.mapper"})
                .statementBuilder(statementBuildable).build();
        definitionStatementScanner.scan();
    }
}
```

Mapper

```Java
@Mapper
@Repository
public interface UserUpdateMapper {

    @InsertDefinition(selective = true)
    int insert(User user);

    @UpdateDefinition(selective = true, where = " user_id = #{userId}")
    int updateById(User user);
}
```

Please view test package where has more examples.
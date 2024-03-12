<img src="./doc/public/logo.png" style="zoom:25%;" />


# dream-orm： 一个优雅、无依赖、高性能的orm框架

<p align="center">
    <a target="_blank" href="https://search.maven.org/search?q=dream-orm">
        <img src="https://img.shields.io/maven-central/v/com.dream-orm/dream-orm?label=Maven%20Central" alt="Maven" />
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8-green.svg" alt="jdk-8" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-21-green.svg" alt="jdk-21" />
	</a>
    <br />
        <img src="https://img.shields.io/badge/SpringBoot-v2.x-blue">
        <img src="https://img.shields.io/badge/SpringBoot-v3.x-blue">
        <a target="_blank" href='https://gitee.com/noear/solon'><img src="https://img.shields.io/badge/Solon-v2.x-blue"></a>
    <br />
    <a target="_blank" href='https://gitee.com/moxiaoai/dream-orm'>
		<img src='https://gitee.com/moxiaoai/dream-orm/badge/star.svg' alt='Gitee star'/>
	</a>
    <a target="_blank" href='https://github.com/dream-orm/dream-orm'>
		<img src="https://img.shields.io/github/stars/dream-orm/dream-orm.svg?logo=github" alt="Github star"/>
	</a>
</p>

# dream-orm介绍

dream-orm是一个自主研发类似mybatis的持久层框架。

轻便，它非常轻量，不依赖第三方jar包 灵巧，拥有极高的性能与灵活性，在满足类Mybatis框架功能上，它还首创SQL方言翻译，
通过SQL方言转换技术（几乎无性能损耗）只要你会MySQL的SQL写法，那么你可以无缝移植到非MySQL数据库。

极速开发，内置的QueryDef不仅帮助开发者极大减少SQL编写的工作同时，减少出错的可能性，而且基本上支持MySQL所有函数，支持常见的SQL语句改写成java链式形式。

dream-orm不仅能够极大的提高开发效率与开发体验，让开发者有更多的时间专注于自己的事，而且还能根据业务进行函数化封装。

## 特性

**跨平台**：mysql语法无缝移植到非MySQL数据库，首创SQL方言翻译

**轻量级**：零依赖且每个模块经过深度调优，性能天花板

**灵活**：接口代替参数实现功能开关，给予更大的开放空间

**函数化**：接口代替参数实现功能开关，给予更大的开放空间

**包罗万象**：持续开发不同形式的接口调用形式，链式\链式强化操作、mapper操作、模板操作等，满足不同人的使用习惯

**更上一层楼**：基于表做缓存隔离，任意查询皆会缓存且任意操作只会清空与操作有关表的缓存

# 快速开始
在开始之前，我们假定您已经：

- 熟悉Java环境配置及其开发
- 熟悉关系型数据库，比如 MySQL
- 熟悉Spring Boot及相关框架
- 熟悉Java构建工具，比如 Maven

##  Hello World

**第一步：创建数据库表**

```sql
CREATE TABLE account
(
    id        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name      VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age       INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email     VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    tenant_id INT(11) NULL COMMENT '租户',
    dept_id   int(11) NULL COMMENT '所在部门',
    del_flag  int(11) NULL COMMENT '删除标志',
    PRIMARY KEY (id)
);
```

**第二步：创建springboot项目，并添加maven依赖**

```xml
<dependency>
    <groupId>com.dream-orm</groupId>
    <artifactId>dream-orm-spring-boot-starter</artifactId>
    <version>${dream-orm.version}</version>
</dependency>
```

**第三步：配置table扫描和mapper扫描**

```java
@FlexAPT
@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

    /**
     * 配置SQL输出
     *
     * @return
     */
    @Bean
    public Listener[] listeners() {
        return new Listener[]{new DebugListener()};
    }

    /**
     * 配置扫描的table和mapper路径
     *
     * @return
     */
    @Bean
    public ConfigurationBean configurationBean() {
        String packageName = this.getClass().getPackage().getName();
        List<String> pathList=Arrays.asList(packageName);
        ConfigurationBean configurationBean = new ConfigurationBean(pathList, pathList);
        return configurationBean;
    }
}
```

**第四步：编写Table实体类**

```java
@Table("account")
public class Account {
    @Id
    @Column("id")
    private Integer id;
    @Column(value = "name")
    private String name;
    @Column("age")
    private Integer age;
    @Column("email")
    private String email;
    @Column("tenant_id")
    private Integer tenantId;
    @Column("dept_id")
    private Integer deptId;
    @Column("del_flag")
    private Integer delFlag;
}
```

**第五步：编写View实体类**

不同于Table实体类，Table实体类等价于数据库表所有字段，而View实体类是指数据库表部分字段，因为一些业务场景，仅仅需要业务部分字段就够啦

```java
@View(Account.class)
public class AccountView {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
}
```

**第六步：添加测试类，进行功能测试**

**模板查询**

TemplateMapper在springboot项目里使用@Autowired，solon使用@Inject注解即可自动装配

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldTemplateTest {
    @Autowired
    private TemplateMapper templateMapper;
    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        AccountView accountView = templateMapper.selectById(AccountView.class, 1);
        System.out.println("查询结果："+accountView);
    }
}
```

**控制台输出**

```tex
方法：com.dream.helloworld.HelloWorldTest.testSelectById
语句：SELECT account.id,account.name,account.age,account.email FROM  account WHERE account.id=?
参数：[1]
用时：30ms
查询结果：AccountView{id=1, name='Jone', age=18, email='test1'}
```

**链式查询**

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexTest {
    @Autowired
    private FlexMapper flexMapper;

    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        Query query = select(account.accountView).from(account).where(account.id.eq(1));
        AccountView accountView = flexMapper.selectOne(query, AccountView.class);
        System.out.println("查询结果：" + accountView);
    }
}
```

```tex
方法：null
语句：SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email` FROM `account`  WHERE `account`.`id`=?
参数：[1]
用时：36ms
查询结果：AccountView{id=1, name='Jone', age=18, email='test1'}
```

**链式强化查询**

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexChainTest {
    @Autowired
    private FlexChainMapper flexChainMapper;

    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        AccountView accountView = flexChainMapper
                .select(account.accountView)
                .from(account)
                .where(account.id.eq(1))
                .one(AccountView.class);
        System.out.println("查询结果：" + accountView);
    }
}
```

```tex
方法：null
语句：SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email` FROM `account`  WHERE `account`.`id`=?
参数：[1]
用时：126ms
查询结果：AccountView{id=1, name='Jone', age=18, email='test1'}
```

以上的 [示列](https://gitee.com/moxiaoai/dream-orm/tree/master/dream-orm-hello-world) 中， `ACCOUNT` 为 dream-orm通过 APT 自动生成，只需通过静态导入即可，无需手动编码。更多查看 APT文档。当然除了以上写法外，也支持mapper接口写法，这里不举例展示，具体详情以及更多写法请点击[示列](https://gitee.com/moxiaoai/dream-orm/tree/master/dream-orm-hello-world) 查看



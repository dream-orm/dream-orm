# 快速开始
在开始之前，我们假定您已经：

- 熟悉Java环境配置及其开发
- 熟悉关系型数据库，比如 MySQL
- 熟悉Spring Boot及相关框架
- 熟悉Java构建工具，比如 Maven

##  Hello World文档

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

# Mapper接口操作

**mapper接口是最原始的开发操作，缺点：手写SQL开发慢，容易出错，优点：更加灵活，可以对SQL做增强操作。主要有两个步骤，涉及四个核心注解和一个接口方法增强类：**

- 编写mapper接口并用Mapper注解声明
- 编写方法并用Sql注解声明，参数用Param注解声明

## Mapper注解

```java
/**
 * 接口可执行标识
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapper {
}
```

## Param注解

```java
public @interface Param {
    /**
     * 指定参数名称
     *
     * @return
     */
    String value();
}
```

| 属性名 | 描述     |
| ------ | -------- |
| value  | 参数名称 |

## Sql注解

```java
public @interface Sql {
    /**
     * 接口方法对应的SQL语句
     *
     * @return
     */
    String value();

    /**
     * 是否应用缓存
     *
     * @return
     */
    boolean cache() default true;

    /**
     * 超时时长，只应用于查询
     *
     * @return
     */
    int timeOut() default 0;
}
```

| 属性名 | 描述                   |
| ------ | ---------------------- |
| value  | 接口方法对应的SQL语句  |
| cache  | 是否应用缓存           |
| time   | 超时时长，只应用于查询 |

## PageQuery注解

```java
/**
 * 分页查询标识
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageQuery {
    /**
     * Page地址
     *
     * @return
     */
    String value() default "page";
}
```

| 属性  | 描述               |
| ----- | ------------------ |
| value | 对象Page的参数地址 |

## Provider注解

此注解可以把sql语句写在其他位置，另外具有sql增强功能

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Provider {
    Class type();

    String method() default "";
}
```

| 属性   | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| type   | 指定sql写在的类                                              |
| method | 对应类中的方法名（返回值限：String和ActionProvider），如果为空，则取注解绑定的方法名， |

```java
/**
 * 映射接口方法与SQL
 */
public interface ActionProvider {

    /**
     * SQL语句
     *
     * @return
     */
    String sql();

    /**
     * SQL执行前行为
     *
     * @return
     */
    default InitAction initAction() {
        return null;
    }

    /**
     * SQL结果遍历行为
     *
     * @return
     */
    default LoopAction loopAction() {
        return null;
    }

    /**
     * SQL执行后行为
     *
     * @return
     */
    default DestroyAction destroyAction() {
        return null;
    }

    /**
     * 返回的类型
     *
     * @return
     */
    default Class<? extends Collection> rowType() {
        return null;
    }

    /**
     * 返回的类型
     *
     * @return
     */
    default Class<?> colType() {
        return null;
    }

    /**
     * 是否应用缓存
     *
     * @return
     */
    default Boolean cache() {
        return null;
    }

    /**
     * 查询超时设置
     *
     * @return
     */
    default Integer timeOut() {
        return null;
    }

    /**
     * SQL操作最终类
     *
     * @return
     */
    default StatementHandler statementHandler() {
        return null;
    }

    /**
     * 映射数据库查询数据与java对象
     *
     * @return
     */
    default ResultSetHandler resultSetHandler() {
        return null;
    }
}
```

| ActionProvider方法 | 描述                               |
| ------------------ | ---------------------------------- |
| sql                | SQL语句                            |
| initAction         | SQL执行前行为                      |
| loopAction         | 若查询结果为集合，则遍历调用此方法 |
| destroyAction      | SQL执行后行为                      |
| rowType            | 接受的集合类型，一般系统判断即可   |
| colType            | 接受的对象类型，一般系统判断即可   |
| cache              | 是否使用缓存                       |
| timeOut            | 超时设置                           |
| statementHandler   | SQL操作最终类                      |
| resultSetHandler   | 映射数据库查询数据与java对象       |

**SQL执行前行为**

```
public interface InitAction {
    void init(MappedStatement mappedStatement, Session session);
}
```

| 参数名          | 描述                     |
| --------------- | ------------------------ |
| mappedStatement | 编译后的接口方法详尽信息 |
| session         | SQL操作会话              |

**SQL遍历行为（查询结果为集合情况）**

```java
public interface LoopAction {
    void loop(Object row, MappedStatement mappedStatement, Session session);
}
```

| 参数名          | 描述                     |
| --------------- | ------------------------ |
| row             | 查询结果集合单个元素     |
| mappedStatement | 编译后的接口方法详尽信息 |
| session         | SQL操作会话              |

**SQL执行后行为**

```java
public interface DestroyAction {
    Object destroy(Object result, MappedStatement mappedStatement, Session session);
}
```

| 参数名          | 描述                     |
| --------------- | ------------------------ |
| result          | 数据库查询结果           |
| mappedStatement | 编译后的接口方法详尽信息 |
| session         | SQL操作会话              |

## 测试一：查询单条

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("select @*() from account where id=@?(id)")
    AccountView selectById(@Param("id") long id);
}
```

**@*()和@?()是内置的@函数。**

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        AccountView accountView = accountMapper.selectById(1);
        System.out.println("查询结果：" + accountView);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.selectById
语句：SELECT account.id,account.name,account.age,account.email FROM account WHERE id=?
参数：[1]
用时：42ms
查询结果：AccountView{id=1, name='Jone', age=18, email='test1'}
```

## 测试二：查询多条

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("select @*() from account where id>@?(id)")
    List<AccountView> selectList(@Param("id") long id);
}
```

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

	/**
     * 测试查询多条
     */
    @Test
    public void testSelectList() {
        List<AccountView> accountViews = accountMapper.selectList(3);
        System.out.println("查询结果：" + accountViews);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.selectList
语句：SELECT account.id,account.name,account.age,account.email FROM account WHERE id>?
参数：[3]
用时：87ms
查询结果：[AccountView{id=4, name='Sandy', age=21, email='test4'}, AccountView{id=5, name='Billie', age=24, email='test5'}]
```

## 测试三：条件非空去除查询

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("select @*() from account where @not(id>@?(account.id) and name like concat('%',@?(account.name),'%'))")
    List<AccountView> selectNotList(@Param("account") AccountView accountView);
}
```

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试非空条件去除查询
     */
    @Test
    public void testSelectNotList() {
        AccountView accountView=new AccountView();
        accountView.setId(3);
        accountView.setName("");
        List<AccountView> accountViews = accountMapper.selectNotList(accountView);
        System.out.println("查询结果：" + accountViews);
    }
```

**@not函数起到条件为空去除，包括空字符串**

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.selectNotList
语句：SELECT account.id,account.name,account.age,account.email FROM account WHERE id>?
参数：[3]
用时：35ms
查询结果：[AccountView{id=4, name='Sandy', age=21, email='test4'}, AccountView{id=5, name='Billie', age=24, email='test5'}]
```



## 测试四：分页查询

**编写Mapper接口**

```java
    @PageQuery
    @Sql("select @*() from account where id>@?(id)")
    List<AccountView> selectPage(@Param("id") long id, @Param("page") Page page);
```

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试分页查询
     */
    @Test
    public void testSelectPage() {
        Page page=new Page(1,2);
        List<AccountView> accountViews = accountMapper.selectPage(1, page);
        System.out.println("总数："+page.getTotal()+"\n查询结果：" + accountViews);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.selectPage
语句：SELECT account.id,account.name,account.age,account.email FROM account WHERE id>? LIMIT ?,?
参数：[1, 0, 2]
方法：com.dream.helloworld.mapper.AccountMapper.selectPage#count
语句：SELECT COUNT(1) FROM account WHERE id>?
参数：[1]
用时：66ms
用时：104ms
总数：4
查询结果：[AccountView{id=2, name='Jack', age=20, email='test2'}, AccountView{id=3, name='Tom', age=28, email='test3'}]
```

## 测试五：全量更新操作

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("update account set name=@?(account.name),age=@?(account.age) where id=@?(account.id)")
    int updateById(@Param("account") AccountView accountView);
}
```

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        AccountView accountView=new AccountView();
        accountView.setId(1);
        accountView.setName("accountName");
        accountMapper.updateById(accountView);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.updateById
语句：UPDATE account SET name=?,age=?  WHERE id=?
参数：[accountName, null, 1]
用时：11ms
```

## 测试六：非空更新操作

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("update account set @non(name=@?(account.name),age=@?(account.age)) where id=@?(account.id)")
    int updateNonById(@Param("account") AccountView accountView);
}
```

**@non函数起到非空去除操作，但空字符串不当做空，主要用于更新。**

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试非空更新
     */
    @Test
    public void testUpdateNon() {
        AccountView accountView=new AccountView();
        accountView.setId(1);
        accountView.setName("accountName");
        accountMapper.updateNonById(accountView);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.updateNonById
语句：UPDATE account SET name=?  WHERE id=?
参数：[accountName, 1]
用时：10ms
```



## 测试七：新增操作

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("insert into account(id,name)values(@?(account.id),@?(account.name))")
    int insert(@Param("account") AccountView accountView);
}
```

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        AccountView accountView=new AccountView();
        accountView.setId(12);
        accountView.setName("accountName");
        accountMapper.insert(accountView);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.insert
语句：INSERT INTO account(id,name)VALUES(?,?)
参数：[12, accountName]
用时：16ms
```

## 测试八：删除操作

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("delete from account where id=@?(id)")
    int deleteById(@Param("id") long id);
}
```

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        accountMapper.deleteById(1);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.deleteById
语句：DELETE FROM account  WHERE id=?
参数：[1]
用时：12ms
```

## 测试九：主键集合批量删除操作

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("delete from account where id in (@foreach(ids))")
    int deleteByIds(@Param("ids") List<Integer>ids);
}
```

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试主键集合批量删除
     */
    @Test
    public void testDeleteByIds() {
        accountMapper.deleteByIds(Arrays.asList(1,2,3));
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.deleteByIds
语句：DELETE FROM account  WHERE id IN (?,?,?)
参数：[1, 2, 3]
用时：27ms
```

## 测试十：对象集合批量删除操作

**编写Mapper接口**

```java
@Mapper
public interface AccountMapper {
    @Sql("delete from account where id in (@foreach(accounts,@?(item.id)))")
    int deleteByViews(@Param("accounts") List<AccountView>accountViews);
}
```

**测试**

```java
	@Autowired
    private AccountMapper accountMapper;

    /**
     * 测试对象集合批量删除
     */
    @Test
    public void testDeleteByViews() {
        List<AccountView>accountViews=new ArrayList<>();
        for(int i=1;i<4;i++){
            AccountView accountView=new AccountView();
            accountView.setId(i);
            accountViews.add(accountView);
        }
        accountMapper.deleteByViews(accountViews);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.AccountMapper.deleteByViews
语句：DELETE FROM account  WHERE id IN (?,?,?)
参数：[1, 2, 3]
用时：12ms
```

## 测试十一：接口增强操作

**为了SQL过长可以很好展示，可以把SQL写在其他位置，或者需要在SQL执行前和执行后增加自己的行为，或者自定义映射器和执行器等可以用接口增强操作。**

**测试SQL仅仅写在其他位置**

**编写ActionProvider类**

```java
public class AccountProvider {
    public String selectProvideById(){
        return "select @*() from account where id=@?(id)";
    }
}
```

**编写Mapper接口**

```java
@Mapper
public interface ProviderAccountMapper {
    @Provider(type = AccountProvider.class)
    AccountView selectProvideById(@Param("id") int id);
}
```

**测试**

```java
    @Autowired
    private ProviderAccountMapper providerAccountMapper;

    /**
     * 测试SQL仅仅写在其他位置
     */
    @Test
    public void testSelectProviderById() {
        AccountView accountView = providerAccountMapper.selectProvideById(1);
        System.out.println("查询结果：" + accountView);
    }
```

**控制台输出**

```tex
方法：com.dream.helloworld.mapper.ProviderAccountMapper.selectProvideById
语句：SELECT account.id,account.name,account.age,account.email FROM account WHERE id=?
参数：[1]
用时：55ms
查询结果：AccountView{id=1, name='Jone', age=18, email='test1'}
```

**测试SQL全面增强**

**编写ActionProvider类**

```java
public class AccountProvider {
    public ActionProvider selectProvideList(){
        return new ActionProvider() {
            @Override
            public String sql() {
                return "select @*() from account where id>@?(account.id)";
            }

            @Override
            public Action[] initActionList() {
                return new Action[]{(session, mappedStatement, arg) -> System.out.println("sql执行前自定义操作")};
            }

            @Override
            public Action[] destroyActionList() {
                return new Action[]{(session, mappedStatement, arg) -> System.out.println("sql执行后自定义操作")};
            }
        };
    }
}
```

**编写Mapper接口**

```java
@Mapper(AccountProvider.class)
public interface ProviderAccountMapper {
   List<AccountView> selectProvideList(@Param("account") AccountView accountView);
}
```

**测试**

```java
    @Autowired
    private ProviderAccountMapper providerAccountMapper;

    /**
     * 测试SQL全面增强
     */
    @Test
    public void selectProvideList() {
        AccountView accountView=new AccountView();
        accountView.setId(3);
        List<AccountView>accountViews = providerAccountMapper.selectProvideList(accountView);
        System.out.println("查询结果：" + accountViews);
    }
```

**控制台输出**

```tex
sql执行前自定义操作
查询结果遍历自定义操作
查询结果遍历自定义操作
sql执行后自定义操作
方法：com.dream.helloworld.h2.mapper.ProviderAccountMapper.selectProvideList
语句：SELECT account.id,account.name,account.age,account.email FROM account WHERE id>?
参数：[3]
用时：34ms
查询结果：[AccountView{id=4, name='Sandy', age=21, email='test4'}, AccountView{id=5, name='Billie', age=24, email='test5'}]
```


# 模板操作

## 基础操作

`dream-orm`内置了一个名为 `TemplateMapper` 的接口，它实现了基本的增删改查功能以及分页查询功能。

| 方法名                                                       | 描述                                   |
| ------------------------------------------------------------ | -------------------------------------- |
| selectById(Class&lt;T&gt; type, Object id)                   | 主键查询                               |
| selectByIds(Class&lt;T&gt; type, Collection&lt;?&gt; idList) | 主键批量查询                           |
| selectOne(Class&lt;T&gt; type, Object conditionObject)       | 根据注解生成条件，查询一条             |
| selectList(Class&lt;T&gt; type, Object conditionObject)      | 根据注解生成条件，查询多条             |
| selectTree(Class&lt;T&gt; type, Object conditionObject)      | 根据注解生成条件，查询，并返回树形结构 |
| selectPage(Class&lt;T&gt; type, Object conditionObject, Page page) | 根据注解生成条件，分页查询多条         |
| updateById(Object view)                                      | 主键更新                               |
| updateNonById(Object view)                                   | 主键非空更新，注意：空字符串也更新     |
| insert(Object view)                                          | 插入                                   |
| insertFetchKey(Object view)                                  | 插入并在view属性记录主键值             |
| deleteById(Class&lt;?&gt; type, Object id)                   | 主键删除                               |
| deleteByIds(Class&lt;?&gt; type, Collection&lt;?&gt; idList) | 主键批量删除                           |
| existById(Class&lt;?&gt; type, Object id)                    | 判断主键是否存在                       |
| exist(Class&lt;?&gt; type, Object conditionObject)           | 根据注解生成条件，判断是否存在         |
| batchInsert(Collection&lt;?&gt; viewList)                    | 批量插入，一千作为一个批次             |
| batchUpdateById(Collection&lt;?&gt; viewList)                | 批量主键更新，一千作为一个批次         |



## **注解条件**

**举例：对于mybatis语法**

```xml
where 1=1 
<if test="name!=null and name !=''">
    and name like('%',#{name},'%')
</if>
<if test="age!=null">
and age in
<foreach item="item" index="index" collection="age"
         open="(" separator="," close=")">
    #{item}
</foreach>
</if>
```

**可改写成**

```java
public class AccountCondition {
    @Conditional(value = ContainsCondition.class)
    private String name;

    @Conditional(value = InCondition.class)
    private List<Integer> age;
}
```

**测试**

```java
/**
 * 测试注解条件
 */
@Test
public void testSelectAnnotationCondition() {
    AccountCondition accountCondition=new AccountCondition();
    accountCondition.setName("a");
    accountCondition.setAge(Arrays.asList(18,20,21,24));
    List<AccountView> accountViews = templateMapper.selectList(AccountView.class, accountCondition);
    System.out.println("查询结果：" + accountViews);
}
```

**控制台输出**

```tex
方法：com.dream.helloworld.HelloWorldTemplateTest.testSelectAnnotationCondition
语句：SELECT account.id,account.name,account.age,account.email FROM  account WHERE account.name LIKE CONCAT('%',?,'%') AND account.age IN(?,?,?,?)
参数：[a, 18, 20, 21, 24]
用时：85ms
查询结果：[AccountView{id=2, name='Jack', age=20, email='test2'}, AccountView{id=4, name='Sandy', age=21, email='test4'}]
```


**Conditional注解**

**用法：指定生成的where条件**

```java
public @interface Conditional {
    String column() default "";

    boolean nullFlag() default true;

    boolean or() default false;

    Class<? extends Condition> value();
}
```

| 属性名   | 描述                         |
| -------- | ---------------------------- |
| column   | 字段名                       |
| nullFlag | 为空是否剔除（空字符串为空） |
| or       | 是否采用or，默认and          |
| value    | 生成条件的实现类             |

```java
public interface Condition {
    String getCondition(String table, String column, String field);
}
```

| 参数名 | 描述         |
| ------ | ------------ |
| table  | 数据库表别名 |
| column | 数据库字段名 |
| field  | 对象属性名称 |

**已实现的Condition类**

| Condition类        | 描述         |
| ------------------ | ------------ |
| ContainsCondition  | like '%?%'   |
| EndWithCondition   | like '?%'    |
| EqCondition        | =?           |
| GeqCondition       | > =?         |
| GtCondition        | > ?          |
| InCondition        | in(?,?)      |
| NotInCondition     | not in (?,?) |
| LeqCondition       | <=?          |
| LtCondition        | <?           |
| NeqCondition       | <>?          |
| NotNullCondition   | is not null  |
| NullCondition      | is null      |
| StartWithCondition | like '%?     |

**当然，也可以基于接口实现自己的条件类，除了生成条件外，也可以对参数值校验、参数值修改、默认值填充以及指定排序字段。**

## 注解校验

**举例**

```java
@View(Account.class)
public class ValidatedAccountView {
    @Unique(msg = "数据库已经存在该字段")
    private Integer id;
    @NotBlank(msg = "名称不能为空")
    @Length(min = 5,max = 20,msg = "名称长度必须在【5,20】之间")
    private String name;
    private Integer age;
    private String email;
 }
```

**这里对id进行数据库唯一性判断（唯一性校验仅仅对数据插入判断，更新不校验），以及对name做长度判断**

**测试**

```java
/**
 * 测试注解校验
 */
@Test
public void testInsertValidated() {
    ValidatedAccountView validatedAccountView=new ValidatedAccountView();
    validatedAccountView.setId(12);
    validatedAccountView.setName("12");
    templateMapper.insert(validatedAccountView);
}
```


**Validated注解**

**用法：对传入的参数进行校验**

```java
@Target(ElementType.ANNOTATION_TYPE)
public @interface Validated {
    /**
     * 校验的注解类
     * @return 校验的注解类
     */
    Class<? extends Validator> value();
}
```

| 属性名 | 描述         |
| ------ | ------------ |
| value  | 校验的注解类 |

```java
/**
 * 参数校验器
 *
 * @param <T>
 */
public interface Validator<T> {
    /**
     * 是否进行参数校验
     *
     * @param session SQL操作会话
     * @param type    对象类型
     * @param field   对象字段属性
     * @param command 执行SQL类型
     * @return
     */
    default boolean isValid(Session session, Class type, Field field, Command command) {
        return true;
    }

    /**
     * 校验参数，不通过则返回非空字符串即可
     *
     * @param value    参数值
     * @param paramMap 自定义参数
     * @return 错误信息
     */
    String validate(T value, Map<String, Object> paramMap);
}
```

| 属性名   | 描述                             |
| -------- | -------------------------------- |
| isValid  | 是否进行参数校验                 |
| validate | 校验参数，不通过则返回非空字符串 |

已实现的Validator

| Validator类          | 描述                                              | 校验语句                                     |
| -------------------- | ------------------------------------------------- | -------------------------------------------- |
| AssertFalseValidator | 校验值若不为空，值必须为false                     | 增删改查                                     |
| AssertTrueValidator  | 校验值若不为空，值必须为true                      | 增删改查                                     |
| LengthValidator      | 校验值若不为空，校验值长度                        | 增删改查                                     |
| MaxValidator         | 校验值若不为空，校验值是否超过最大值              | 增删改查                                     |
| MinValidator         | 校验值若不为空，校验值是否小于最小值              | 增删改查                                     |
| NotBlankValidator    | 校验值不能为空，且不能为空字符串                  | 增删改查                                     |
| NotNullValidator     | 校验值不能为空                                    | 增删改查                                     |
| PatternValidator     | 校验值若不为空，校验满足正则表达式                | 增删改查                                     |
| RangeValidator       | 校验值若不为空，校验值是否在规定范围              | 增删改查                                     |
| SizeValidator        | 校验值若不为空，校验集合或map的大小是否在规定范围 | 增删改查                                     |
| NotExistValidator    | 校验值若不为空，校验数据不存在                    | 增删（新增进行唯一性校验，删除进行依赖校验） |

## **注解修改**

**举例**

```java
@View(Account.class)
public class WrapAccountView {
    private Integer id;
    private String name;
    @Wrap(ZeroWrapper.class)
    private Integer age;
    private String email;
}
```

**这里对age字段做默认值填充0**

**测试**

```java
/**
 * 测试注解修改
 */
@Test
public void testInsertWrap() {
    WrapAccountView wrapAccountView = new WrapAccountView();
    wrapAccountView.setId(1);
    wrapAccountView.setName("哈哈");
    templateMapper.updateById(wrapAccountView);
}
```

**控制台输出**

```tex
方法：com.dream.helloworld.HelloWorldTemplateTest.testInsertWrap
语句：UPDATE account SET name=?,age=?,email=?  WHERE account.id=?
参数：[哈哈, 0, null, 1]
用时：12ms
```


**Wrap注解**

用法：参数值注入与修改，可完成填充默认值，字段加密等操作

```java
public @interface Wrap {
    /**
     * 参数修改类
     * @return 参数修改类
     */
    Class<? extends Wrapper> value();

    /**
     * 参数修改时机：插入、更新、插入或更新，默认插入或更新
     * @return 参数修改时机
     */
    WrapType type() default WrapType.INSERT_UPDATE;
}
```

| 属性名 | 描述                                                 |
| ------ | ---------------------------------------------------- |
| value  | 参数修改类                                           |
| type   | 参数修改时机：插入、更新、插入或更新，默认插入或更新 |

```java
/**
 * sql操作参数修改接口
 */
public interface Wrapper {
    /**
     * 返回修改后的参数
     *
     * @param value 修改前参数
     * @return
     */
    Object wrap(Object value);
}
```

| 参数名 | 描述                           |
| ------ | ------------------------------ |
| value  | 修改前参数，返回为修改后的参数 |

## 注解排序

**举例**

```java
public class OrderAccountCondition {
    @Conditional(ContainsCondition.class)
    @Sort(value = Order.ASC,order = 1)
    private String name;
    @Sort(value = Order.DESC,order = 0)
    private Integer age;
    }
```

**这里先根据age降序，然后name升序**

**测试**

```java
/**
 * 测试注解排序
 */
@Test
public void testSelectOrder() {
    OrderAccountCondition accountCondition=new OrderAccountCondition();
    accountCondition.setName("a");
    List<AccountView> accountViews = templateMapper.selectList(AccountView.class, accountCondition);
    System.out.println("查询结果：" + accountViews);
}
```

**控制台输出**

```tex
方法：com.dream.helloworld.HelloWorldTemplateTest.testSelectOrder
语句：SELECT account.id,account.name,account.age,account.email FROM  account WHERE account.name LIKE CONCAT('%',?,'%') ORDER BY age DESC,name ASC
参数：[a]
用时：31ms
查询结果：[AccountView{id=4, name='Sandy', age=21, email='test4'}, AccountView{id=2, name='Jack', age=20, email='test2'}]
```

**Sort注解**

**用法：排序**

```java
public @interface Sort {
    String column() default "";

    Order value() default Order.ASC;

    int order() default 0;
}
```

| 属性名 | 描述                                           |
| ------ | ---------------------------------------------- |
| column | 字段名称                                       |
| value  | 排序方式                                       |
| order  | 指定多个排序字段时，显示优先级，越小优先级越高 |

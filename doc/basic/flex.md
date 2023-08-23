# 链式操作

在 dream-orm中，内置了 `QueryDef` 、`UpdateDef`、`deleteDef`、insertDef 用于对数据进行查询、修改、删除和插入操作，链式依赖APT在编译时生成对应代码，默认处于关闭状态。

## **开启链式操作**

**FlexAPT注解**

```java
public @interface FlexAPT {
    /**
     * 是否启用APT
     *
     * @return
     */
    boolean enable() default true;

    /**
     * 生成apt类的后缀
     *
     * @return
     */
    String classSuffix() default "TableDef";

    /**
     * 生成类所在目录
     * 支持./、../和/
     *
     * @return
     */
    String dir() default "./table";
}
```

| 属性        | 描述                                                |
| ----------- | --------------------------------------------------- |
| enable      | 是否开启APT，当类声明此注解默认开启                 |
| classSuffix | 生成apt类的后缀                                     |
| dir         | 根据Table注解所在类的目录生成对应代码所在目录的包名 |

**APT编译生成的代码不仅仅依赖Table注解声明的类，还依赖View注解声明的类。**

## 查询SQL


### select

**举例：查询多个字段**

```sql
select(account.id,account.name)
```

**SQL输出**

```sql
SELECT `account`.`id`,`account`.`name`
```

举例：查询数组

```sql
select(account.accountView)
```

**accountView数组是由视图类`AccountView`编译生成的数组，查询的字段等同于视图类声明的属性**

**SQL输出**

```sql
SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email`
```

### 字段别名

```sql
account.name.as("uname")
```

**SQL输出**

```sql
`account`.`name` `uname`
```

### case

**测试**

```sql
case_(account.age).when(11).then(11).when("11").then("字符串11").else_("默认值").end()
```

**SQL输出**

```sql
CASE `account`.`age` WHEN 11 THEN 11 WHEN '11' THEN '字符串11' ELSE '默认值' END
```

**测试条件**

```sql
case_().when(account.age.eq(11)).then(11).when(account.age.eq("11")).then("字符串11").else_("默认值").end();
```

**SQL输出**

```sql
CASE  WHEN `account`.`age`=? THEN 11 WHEN `account`.`age`=? THEN '字符串11' ELSE '默认值' END
```

### 函数

目前，`dream-orm`已支持 80+ 个常见的 SQL 函数，查看已支持的  [所有函数](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-flex/src/main/java/com/dream/flex/def/FunctionDef.java) 。 若还不满足，您可以参考 [FunctionDef](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-flex/src/main/java/com/dream/flex/def/FunctionDef.java) ，然后在自己的项目里进行自定义扩展。

| 支持的函数     | 函数说明                                                     |
| -------------- | ------------------------------------------------------------ |
| ascii          | 返回字符串 s 的第一个字符的 ASSCII 码                        |
| len            | 返回字符串 s 的字符数                                        |
| length         | 返回字符串 s 的长度                                          |
| concat         | 将字符串 s1，s2 等多个字符串合并为一个字符串                 |
| group_concat   | 将同一组的列显示出来，并且用分隔符分隔                       |
| find_in_set    | 返回在字符串 s2 中与 s1 匹配的字符串的位置                   |
| coalesce       | 返回第一个非`NULL`参数                                       |
| concat_ws      | 同 CONCAT(s1, s2, ...)，但是每个字符串之间要加上 x           |
| instr          | 从字符串 s 中获取 s1 的开始位置                              |
| locate         | 函数返回subStr在string中出现的位置                           |
| lcase          | 将字符串 s 的所有字符都变成小写字母                          |
| lower          | 将字符串 s 的所有字符都变成小写字母                          |
| upper          | 将字符串 s 的所有字符都变成大写字母                          |
| left           | 返回字符串 s 的前 n 个字符                                   |
| repeat         | 将字符串 s 重复 n 次                                         |
| right          | 返回字符串 s 的后 n 个字符                                   |
| ltrim          | 去掉字符串 s 开始处的空格                                    |
| rtrim          | 去掉字符串 s 结尾处的空格                                    |
| trim           | 去掉字符串 s 开始处和结尾处的空格                            |
| reverse        | 将字符串 s 的顺序反过来                                      |
| replace        | 用字符串 s2 代替字符串 s 中的字符串 s1                       |
| strcmp         | 比较字符串 s1 和 s2                                          |
| substr         | 获取从字符串 s 中的第 n 个位置开始长度为 len 的字符串        |
| abs            | 返回绝对值                                                   |
| avg            | 返回指定列的平均值                                           |
| sum            | 返回指定字段值的和                                           |
| count          | 查询数据总量                                                 |
| ceil           | 返回大于或等于 x 的最小整数（向上取整）                      |
| ceiling        | 返回大于或等于 x 的最小整数（向上取整）                      |
| floor          | 返回小于或等于 x 的最大整数（向下取整）                      |
| rand           | 返回 0~1 的随机数                                            |
| pi             | 返回圆周率                                                   |
| min            | 返回指定列的最小值                                           |
| max            | 返回指定列的最大值                                           |
| sign           | 返回 x 的符号，x 是负数、0、正数分别返回 -1、0、1            |
| truncate       | 返回数值 x 保留到小数点后 y 位的值                           |
| round          | 返回离 x 最近的整数（四舍五入）                              |
| pow            | 返回 x 的 y 次方                                             |
| power          | 返回 x 的 y 次方                                             |
| sqrt           | 返回 x 的平方根                                              |
| exp            | 返回 e 的 x 次方                                             |
| mod            | 返回 x 除以 y 以后的余数                                     |
| ln             | 返回数字的自然对数                                           |
| log            | 返回自然对数（以 e 为底的对数）                              |
| log2           | 返回以 2 为底的对数                                          |
| log10          | 返回以 10 为底的对数                                         |
| sin            | 求正弦值                                                     |
| asin           | 求反正弦值                                                   |
| cos            | 求余弦值                                                     |
| acos           | 求反余弦值                                                   |
| tan            | 求正切值                                                     |
| atan           | 求反正切值                                                   |
| atan2          | 返回两个值的反正切                                           |
| cot            | 求余切值                                                     |
| date_add       | 计算开始日期 d 加上 n 天的日期                               |
| date_sub       | 计算起始日期 d 减去 n 天的日期                               |
| date_format    | 以不同的格式显示日期/时间数据                                |
| str_to_date    | 将字符串转换为日期时间                                       |
| curdate        | 返回当前日期                                                 |
| datediff       | 计算日期 d1 到 d2 之间相隔的天数                             |
| year           | 返回日期 d 中的年份值                                        |
| month          | 返回日期 d 中的月份值，范围是 1~12                           |
| day            | 返回日期 d 中的天数值                                        |
| quarter        | 返回日期 d 是第几季度，范围 1-4                              |
| hour           | 返回时间 t 中的小时值                                        |
| minute         | 返回时间 t 中的分钟值                                        |
| second         | 返回时间 t 中的秒钟值                                        |
| dayofweek      | 返回日期 d 是星期几，1 表示星期日，2 表示星期二              |
| dayofyear      | 计算日期 d 是本年的第几天                                    |
| weekofyear     | 计算日期 d 是本年的第几个星期，范围是 1-53                   |
| last_day       | 提取给定日期当月的最后一天                                   |
| date           | 提取时间的日期部分                                           |
| now            | 返回当前日期和时间                                           |
| sysdate        | 返回当前日期和时间                                           |
| ifnull         | 如果不是`NULL`，则返回第一个参数。 否则，`IFNULL`函数返回第二个参数 |
| if_            | 如果expr1为TRUE，则IF()返回值为expr2，否则返回值为expr3      |
| lpad           | 字符串 s2 来填充 s1 的开始处，使字符串长度达到 len           |
| rpad           | 字符串 s2 来填充 s1 的结尾处，使字符串长度达到 len           |
| space          | 返回 n 个空格                                                |
| unix_timestamp | 以 UNIX 时间戳的形式返回当前时间                             |
| from_unixtime  | 把 UNIX 时间戳的时间转换为普通格式的时间                     |
| nullif         | 如果两个参数相等，则返回NULL；否则就返回第一个参数           |
| convert        | 将任何类型的值转换为具有指定类型的值                         |
| cast           | 将任何类型的值转换为具有指定类型的值                         |
| to_char        | 指定的格式将时间戳值、间隔值、数字值转为字符串               |
| to_number      | 将指定的字符串转为一个数字                                   |
| to_date        | 转换为 普通的时间格式                                        |
| to_timestamp   | 转换可为 时间戳格式                                          |

### from

**测试**

```sql
select(account.id).from(account)
```

**SQL输出**

```sql
SELECT `account`.`id` FROM `account` 
```

**测试关联查询以及表别名**

```sql
AccountTableDef account2=new AccountTableDef("account2");
select(account.id).from(account).leftJoin(account2).on(account2.id.eq(account.id));
```

**AccountTableDef是APT自动生成的，传入一个字符串表示表的别名**

**SQL输出**

```sql
SELECT `account`.`id` FROM `account`  LEFT JOIN `account` `account2` ON `account2`.`id`=`account`.`id`
```

### where

**测试**

```sql
select(account.id).from(account).where(account.name.like("a"));
```

**SQL输出**

```sql
SELECT `account`.`id` FROM `account`  WHERE `account`.`name` LIKE CONCAT('%',?,'%')
```

**测试动态条件1**

```sql
select(account.id).from(account).where(account.name.like("a", Objects::isNull));
```

**SQL输出**

```
SELECT `account`.`id` FROM `account` 
```

**测试动态条件2**

```sql
select(account.id).from(account).where(account.name.like("a").when(false))
```

**SQL输出**

```sql
SELECT `account`.`id` FROM `account` 
```

### groupBy

**测试**

```sql
select(count(account.id)).from(account).groupBy(account.age);
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  GROUP BY `account`.`age`
```

### having

**测试**

```sql
select(count(account.id)).from(account).groupBy(account.age).having(account.name.likeLeft("a"))
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  GROUP BY `account`.`age` HAVING `account`.`name` LIKE CONCAT('%',?)
```

### orderBy

**测试**

```sql
select(count(account.id)).from(account).orderBy(account.age.desc(),account.name.asc());
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  ORDER BY `account`.`age` DESC,`account`.`name` ASC
```

### limit

**测试**

```sql
select(count(account.id)).from(account).limit(5,10);
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  LIMIT ?,?
```

### offset

**测试**

```sql
select(count(account.id)).from(account).offset(10,5);
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  LIMIT ? OFFSET ?
```

### union

**测试**

```sql
select(count(account.id)).from(account).union(select(count(account.id)).from(account));
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  UNION SELECT COUNT(`account`.`id`) FROM `account` 
```

### unionAll

**测试**

```sql
select(count(account.id)).from(account).unionAll(select(count(account.id)).from(account));
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  UNION ALL SELECT COUNT(`account`.`id`) FROM `account` 
```

### forUpdate

**测试**

```sql
select(count(account.id)).from(account).forUpdate();
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  FOR UPDATE
```

### forUpdateNoWait

**测试**

```sql
select(count(account.id)).from(account).forUpdateNoWait();
```

**SQL输出**

```sql
SELECT COUNT(`account`.`id`) FROM `account`  FOR UPDATE NOWAIT
```

## 更新SQL

**测试**

```sql
update(account).set(account.age, account.age.add(1)).set(account.name, "accountName").where(account.id.eq(1));
```

**SQL输出**

```sql
UPDATE `account`  SET `account`.`age`=`account`.`age`+1,`account`.`name`=?  WHERE `account`.`id`=?
```

## 新增SQL

**测试**

```sql
insertInto(account).columns(account.name, account.age).values("accountName", 12);
```

**SQL输出**

```sql
INSERT INTO `account` (`name`,`age`)VALUES(?,?)
```

## 删除SQL

**测试**

```sql
delete(account).where(account.id.eq(1));
```

**SQL输出**

```sql
DELETE FROM `account`   WHERE `account`.`id`=?
```

## 综合操作

**创建好的链式SQL最终目的是为了进行数据库操作，可以使用FlexMapper接口**

```java
/**
 * 链式操作类
 */
public interface FlexMapper {
    /**
     * 白名单，列如开启插件后，链式并不默认添加，需要在白名单额外增加
     */
    Set<Class<? extends Inject>> WHITE_SET = new HashSet<>();

    /**
     * 查询并返回一条
     *
     * @param query 查询定义器
     * @param type  返回类型
     * @param <T>
     * @return 单条数据
     */
    <T> T selectOne(Query query, Class<T> type);

    /**
     * 查询并返回集合
     *
     * @param query 查询定义器
     * @param type  返回类型
     * @param <T>
     * @return 集合数据
     */
    <T> List<T> selectList(Query query, Class<T> type);

    /**
     * 查询并返回分页
     *
     * @param query 查询定义器
     * @param type  返回类型
     * @param page  分页
     * @param <T>
     * @return 分页数据
     */
    <T> Page<T> selectPage(Query query, Class<T> type, Page page);

    /**
     * 更新操作
     *
     * @param update 更新定义器
     * @return 更新数量
     */
    int update(Update update);

    /**
     * 删除操作
     *
     * @param delete 删除定义器
     * @return 删除数量
     */
    int delete(Delete delete);

    /**
     * 插入操作
     *
     * @param insert 插入定义器
     * @return 插入数量
     */
    int insert(Insert insert);
}
```

| 方法       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| WHITE_SET  | 链式使用插件的白名单，默认不添加，处于性能考虑，需要开发者手动加入插件到白名单 |
| selectOne  | 根据查询链式SQL查询一条数据                                  |
| selectList | 根据查询链式SQL查询多条数据                                  |
| selectPage | 根据查询链式SQL分页查询多条数据（链式SQL不支持手动加入分页） |
| update     | 根据更新链式SQL做修改操作                                    |
| delete     | 根据删除链式SQL做删除操作                                    |
| insert     | 根据插入链式SQL做插入操作                                    |

### 测试一：查询单条

**测试**

```java
/**
 * 测试主键查询
 */
@Test
public void testSelectById() {
    Query query = select(account.accountView).from(account).where(account.id.eq(1));
    AccountView accountView = flexMapper.selectOne(query, AccountView.class);
    System.out.println("查询结果：" + accountView);
}
```

**控制台输出**

```tex
方法：null
语句：SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email` FROM `account`  WHERE `account`.`id`=?
参数：[1]
用时：110ms
查询结果：AccountView{id=1, name='Jone', age=18, email='test1'}
```

### 测试二：查询多条

**测试**

```java
    /**
     * 测试查询多条
     */
    @Test
    public void testSelectList() {
        Query query = select(account.accountView).from(account).where(account.id.gt(3));
        List<AccountView> accountViews = flexMapper.selectList(query, AccountView.class);
        System.out.println("查询结果：" + accountViews);
    }
```

**控制台输出**

```tex
方法：null
语句：SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email` FROM `account`  WHERE `account`.`id`>?
参数：[3]
用时：34ms
查询结果：[AccountView{id=4, name='Sandy', age=21, email='test4'}, AccountView{id=5, name='Billie', age=24, email='test5'}]
```

### 测试三：分页查询

**测试**

```java
    /**
     * 测试分页查询
     */
    @Test
    public void testSelectPage() {
        Query query = select(account.accountView).from(account).where(account.id.gt(1));
        Page page=new Page(1,2);
        page = flexMapper.selectPage(query, AccountView.class,page);
        System.out.println("总数："+page.getTotal()+"\n查询结果：" + page.getRows());
    }
```

**控制台输出**

```tex
方法：null
语句：SELECT COUNT(1) FROM `account`  WHERE `account`.`id`>?
参数：[1]
用时：48ms
方法：null
语句：SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email` FROM `account`  WHERE `account`.`id`>? LIMIT ?,?
参数：[1, 0, 2]
用时：18ms
总数：4
查询结果：[AccountView{id=2, name='Jack', age=20, email='test2'}, AccountView{id=3, name='Tom', age=28, email='test3'}]
```

### 测试四：更新操作

**测试**

```java
    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        Update update = update(account).set(account.age, account.age.add(1))
            .set(account.name, "accountName").where(account.id.eq(1));
        flexMapper.update(update);
    }
```

**控制台输出**

```tex
方法：null
语句：UPDATE `account`  SET `account`.`age`=`account`.`age`+1,`account`.`name`=?  WHERE `account`.`id`=?
参数：[accountName, 1]
用时：13ms
```

### 测试五：新增操作

**测试**

```java
    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        Insert insert = insertInto(account).columns(account.name, account.age).values("accountName", 12);
        flexMapper.insert(insert);
    }
```

**控制台输出**

```tex
方法：null
语句：INSERT INTO `account` (`name`,`age`)VALUES(?,?)
参数：[accountName, 12]
用时：7ms
```

### 测试六：删除操作

**测试**

```java
    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        Delete delete= delete(account).where(account.id.eq(1));
        flexMapper.delete(delete);
    }
```

**控制台输出**

```tex
方法：null
语句：DELETE FROM `account`   WHERE `account`.`id`=?
参数：[1]
用时：9ms
```


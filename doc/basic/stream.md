# Stream操作

此系列写法，为兼容mybatis-plus而设计的API，同时增加新特性。

## 引用系统Mapper

```java
@Autowired
private StreamMapper streamMapper;
```

## 查询表

```java
@Test
public void testSelectAll() {
    DefaultSelectWrapper<Account> wrapper = Wrappers.query(Account.class);
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account`
参数：[]
用时：25ms
```

## 查询自定义字段

```java
@Test
public void testSelectColumn() {
    DefaultFromWrapper<Account> wrapper = Wrappers.query(Account.class).select("id", "name", "age");
    Page<Account> accountPage = streamMapper.selectPage(wrapper, new Page<>(1, 10));
}
```

**日志输出**

```sql
语句：SELECT COUNT(*) FROM (SELECT id,name,age FROM `account`) `t_tmp`
参数：[]
用时：1293ms
语句：SELECT id,name,age FROM `account` LIMIT ? OFFSET ?
参数：[10, 0]
用时：227ms
```

## 查询函数字段

```sql
@Test
public void testSelectFunc() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).select(i -> i.len("name").ascii("name").length("name"));
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT CHAR_LENGTH(name),ASCII(name),LENGTH(name) FROM `account`
参数：[]
用时：39ms
```

## 条件查询(1)

```sql
@Test
public void testWhere() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class)
            .leq("age", 11).and(a -> a.leq("age", 11).or(b -> b.like("name", "11")));
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` WHERE age<=? AND (age<=? OR name LIKE CONCAT('%',?,'%'))
参数：[11, 11, 11]
用时：36ms
```

## 条件查询(2)

```sql
@Test
public void testWhere2() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).where(i -> i.leq("age", 11).and(a -> a.leq("age", 11).or(b -> b.like("name", "11"))));
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` WHERE age<=? AND (age<=? OR name LIKE CONCAT('%',?,'%'))
参数：[11, 11, 11]
用时：36ms
```

## Not条件查询

```sql
@Test
public void testWhereNot() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class)
            .notIn("age", 1, 2, 3)
            .notIn("age", Wrappers.query(Account.class).select("age"))
            .notLike("name", "a")
            .notLikeLeft("name", "b")
            .notLikeRight("name", "c")
            .notBetween("age", 11, 34);
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` WHERE age NOT  IN (?,?,?) AND age NOT  IN (SELECT age FROM `account`) AND name NOT  LIKE CONCAT('%',?,'%') AND name NOT  LIKE CONCAT(?,'%') AND name NOT  LIKE CONCAT('%',?) AND age NOT  BETWEEN ? AND ?
参数：[1, 2, 3, a, b, c, 11, 34]
用时：37ms
```

## groupBy

```sql
@Test
public void testGroup() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).select("avg(age) age", "name").groupBy("name");
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT avg(age) age,name FROM `account` GROUP BY name
参数：[]
用时：43ms
```

## having

```sql
@Test
public void testHaving() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).select("avg(age) age", "name").groupBy("name").leq("age", 11).and(a -> a.leq("age", 11).or(b -> b.like("name", "11")));
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT avg(age) age,name FROM `account` GROUP BY name HAVING age<=? AND (age<=? OR name LIKE CONCAT('%',?,'%'))
参数：[11, 11, 11]
用时：37ms
```

## having(2)

```sql
@Test
public void testHaving2() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).select("avg(age) age", "name").groupBy("name").having(i -> i.leq("age", 11).and(a -> a.leq("age", 11).or(b -> b.like("name", "11"))));
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT avg(age) age,name FROM `account` GROUP BY name HAVING age<=? AND (age<=? OR name LIKE CONCAT('%',?,'%'))
参数：[11, 11, 11]
用时：35ms
```

## orderBy

```sql
@Test
public void testOrder() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).orderBy("name", "age");
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**SQL输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` ORDER BY name,age
参数：[]
用时：40ms
```

## orderBy(2)

```sql
@Test
public void testOrder2() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).orderBy(i -> i.asc("name").desc("age"));
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` ORDER BY name ASC,age DESC
参数：[]
用时：29ms
```

## limit

```sql
@Test
public void testLimit() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).limit(5, 10);
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**SQL输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` LIMIT ?,?
参数：[5, 10]
用时：26ms
```

## offset

```sql
@Test
public void testOffset() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).offset(5, 10);
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` LIMIT ? OFFSET ?
参数：[10, 5]
用时：26ms
```

## union

```sql
@Test
public void testUnion() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).union(Wrappers.query(Account.class));
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` UNION SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account`
参数：[]
用时：32ms
```

## unionAll

```sql
@Test
public void testUnionAll() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).unionAll(Wrappers.query(Account.class));
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` UNION ALL SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account`
参数：[]
用时：30ms
```

## forUpdate

```sql
@Test
public void testForUpdate() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).forUpdate();
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` FOR UPDATE
参数：[]
用时：39ms
```

## forUpdateNoWait

```sql
@Test
public void testForUpdateNoWait() {
    QueryWrapper<Account> wrapper = Wrappers.query(Account.class).forUpdateNoWait();
    List<Account> accounts = streamMapper.selectList(wrapper);
}
```

**日志输出**

```sql
语句：SELECT `id`,`name`,`age`,`email`,`tenant_id`,`dept_id`,`del_flag` FROM `account` FOR UPDATE NOWAIT
参数：[]
用时：31ms
```

# Stream操作

此系列写法，为兼容mybatis-plus而设计的API，同时增加新特性

## 查询自定义字段

```java
    @Test
    public void testSelectColumn() {
        QueryWrapper wrapper = Wrappers.query(Account.class).select("a", "b", "c");
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT a,b,c FROM `account`
```

## 查询函数字段

```sql
    @Test
    public void testSelectFunc() {
        QueryWrapper wrapper = Wrappers.query(Account.class).select(i -> i.len("a").ascii("b").length("c"));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT CHAR_LENGTH(a),ASCII(b),LENGTH(c) FROM `account`
```

## 条件查询(1)

```sql
    @Test
    public void testWhere() {
        QueryWrapper wrapper = Wrappers.query(Account.class)
                .leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11")));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }account.name.as("uname")
```

**日志输出**

```sql
SELECT * FROM `account` WHERE b<=? AND (age<=? OR a LIKE CONCAT('%',?,'%'))
```

## 条件查询(2)

```sql
    @Test
    public void testWhere2() {
        QueryWrapper wrapper = Wrappers.query(Account.class).where(i -> i.leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11"))));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` WHERE b<=? AND (age<=? OR a LIKE CONCAT('%',?,'%'))
```

## groupBy

```sql
    @Test
    public void testGroup() {
        QueryWrapper wrapper = Wrappers.query(Account.class).groupBy("a");
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` GROUP BY a
```

## having

```sql
    @Test
    public void testHaving() {
        QueryWrapper wrapper = Wrappers.query(Account.class).groupBy("a").leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11")));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` GROUP BY a HAVING b<=? AND (age<=? OR a LIKE CONCAT('%',?,'%'))
```

## having(2)

```sql
    @Test
    public void testHaving2() {
        QueryWrapper wrapper = Wrappers.query(Account.class).groupBy("a").having(i -> i.leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11"))));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` GROUP BY a HAVING b<=? AND (age<=? OR a LIKE CONCAT('%',?,'%'))
```

## orderBy

```sql
    @Test
    public void testOrder() {
        QueryWrapper wrapper = Wrappers.query(Account.class).orderBy("a","b");
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**SQL输出**

```sql
SELECT * FROM `account` ORDER BY a,b
```

## orderBy(2)

```sql
    @Test
    public void testOrder2() {
        QueryWrapper wrapper = Wrappers.query(Account.class).orderBy(i->i.asc("a").desc("b"));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` ORDER BY a ASC,b DESC
```

## limit

```sql
    @Test
    public void testLimit() {
        QueryWrapper wrapper = Wrappers.query(Account.class).limit(5,10);
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**SQL输出**

```sql
SELECT * FROM `account` LIMIT ?,?
```

## offset

```sql
    @Test
    public void testOffset() {
        QueryWrapper wrapper = Wrappers.query(Account.class).offset(5,10);
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` LIMIT ? OFFSET ?
```

## union

**测试**

```sql
    @Test
    public void testUnion() {
        QueryWrapper wrapper = Wrappers.query(Account.class).union(Wrappers.query(Account.class));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` UNION SELECT * FROM `account`
```

## unionAll

```sql
    @Test
    public void testUnionAll() {
        QueryWrapper wrapper = Wrappers.query(Account.class).unionAll(Wrappers.query(Account.class));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` UNION ALL SELECT * FROM `account`
```

## forUpdate

```sql
    @Test
    public void testForUpdate() {
        QueryWrapper wrapper = Wrappers.query(Account.class).forUpdate();
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` FOR UPDATE
```

## forUpdateNoWait

```sql
    @Test
    public void testForUpdateNoWait() {
        QueryWrapper wrapper = Wrappers.query(Account.class).forUpdateNoWait();
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
```

**日志输出**

```sql
SELECT * FROM `account` FOR UPDATE NOWAIT
```

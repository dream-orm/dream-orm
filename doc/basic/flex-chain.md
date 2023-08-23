# 链式强化操作

**链式强化操作是基于链式操作扩展而来，继承了原有的功能，这里使用FlexChianMapper接口。**

```java
/**
 * 链式强化接口
 */
public interface FlexChainMapper {
    /**
     * 查询链式操作
     * @param columnDefs 查询字段集
     * @return 查询链式
     */
    ChainSelectDef select(ColumnDef... columnDefs);

    /**
     * 更新链式操作
     * @param tableDef 表
     * @return 更新链式
     */
    ChainUpdateColumnDef update(TableDef tableDef);

    /**
     * 插入链式操作
     * @param tableDef 表
     * @return 插入链式
     */
    ChainInsertIntoTableDef insertInto(TableDef tableDef);

    /**
     * 删除链式操作
     * @param tableDef 表
     * @return 删除链式
     */
    ChainDeleteTableDef delete(TableDef tableDef);
}
```

| 方法名     | 描述         |
| ---------- | ------------ |
| select     | 查询链式操作 |
| update     | 更新链式操作 |
| insertInto | 插入链式操作 |
| delete     | 删除链式操作 |

## 测试一：查询单条

**测试**

```java
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
```

**控制台输出**

```tex
方法：null
语句：SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email` FROM `account`  WHERE `account`.`id`=?
参数：[1]
用时：37ms
查询结果：AccountView{id=1, name='Jone', age=18, email='test1'}
```

## 测试二：查询多条

**测试**

```java
    /**
     * 测试查询多条
     */
    @Test
    public void testSelectList() {
        List<AccountView> accountViews = flexChainMapper.select(account.accountView).from(account)
                .where(account.id.gt(3)).list(AccountView.class);
        System.out.println("查询结果：" + accountViews);
    }
```

**控制台输出**

```tex
方法：null
语句：SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email` FROM `account`  WHERE `account`.`id`>?
参数：[3]
用时：32ms
查询结果：[AccountView{id=4, name='Sandy', age=21, email='test4'}, AccountView{id=5, name='Billie', age=24, email='test5'}]
```

## 测试三：分页查询

**测试**

```java
/**
     * 测试分页查询
     */
    @Test
    public void testSelectPage() {
        Page page=new Page(1,2);
        page=flexChainMapper.select(account.accountView).from(account).where(account.id.gt(1)).page(AccountView.class,page);
        System.out.println("总数："+page.getTotal()+"\n查询结果：" + page.getRows());
    }
```

**控制台输出**

```tex
方法：null
语句：SELECT COUNT(1) FROM `account`  WHERE `account`.`id`>?
参数：[1]
用时：38ms
方法：null
语句：SELECT `account`.`id`,`account`.`name`,`account`.`age`,`account`.`email` FROM `account`  WHERE `account`.`id`>? LIMIT ?,?
参数：[1, 0, 2]
用时：20ms
总数：4
查询结果：[AccountView{id=2, name='Jack', age=20, email='test2'}, AccountView{id=3, name='Tom', age=28, email='test3'}]
```

## 测试四：更新操作

**测试**

```java
    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        flexChainMapper.update(account).set(account.age, account.age.add(1))
                .set(account.name, "accountName")
                .where(account.id.eq(1)).execute();
    }
```

**控制台输出**

```tex
方法：null
语句：UPDATE `account`  SET `account`.`age`=`account`.`age`+1,`account`.`name`=?  WHERE `account`.`id`=?
参数：[accountName, 1]
用时：11ms
```

## 测试五：新增操作

**测试**

```java
    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        flexChainMapper.insertInto(account).columns(account.name, account.age).values("accountName", 12).execute();
    }
```

**控制台输出**

```tex
方法：null
语句：INSERT INTO `account` (`name`,`age`)VALUES(?,?)
参数：[accountName, 12]
用时：13ms
```

## 测试六：删除操作

**测试**

```java
    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        flexChainMapper.delete(account).where(account.id.eq(1)).execute();
    }
```

**控制台输出**

```tex
方法：null
语句：DELETE FROM `account`   WHERE `account`.`id`=?
参数：[1]
用时：10ms
```

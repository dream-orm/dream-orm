# 不翻译SQL操作

**`dream-orm`都是要对SQL进行编译的，因此需要准确无误的识别SQL，对于有些极其特殊的语法，`dream-orm`做不到语法识别，因此提供不翻译SQL，直接执行是十分有必要的。需要用到`JdbcMapper`接口。**

```java
/**
 * 不翻译直接执行SQL接口
 */
public interface JdbcMapper {
    /**
     * SQL操作
     *
     * @param sql  原始SQL
     * @param args 参数
     * @return 操作条数
     */
    default int execute(String sql, Object... args) {
        return execute(sql, new JdbcStatementSetter(args));
    }

    /**
     * SQL操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @return 操作条数
     */
    int execute(String sql, StatementSetter statementSetter);

    /**
     * 批量操作
     *
     * @param sql             原始SQL
     * @param argList         集合数据
     * @param statementSetter 自定义参数设置
     * @param <T>
     * @return 操作条数
     */
    default <T> List<Object> batchExecute(String sql, List<T> argList, StatementSetter statementSetter) {
        return batchExecute(sql, argList, statementSetter, 1000);
    }

    /**
     * 批量操作
     *
     * @param sql             原始SQL
     * @param argList         集合数据
     * @param statementSetter 自定义参数设置
     * @param batchSize       批量大小
     * @param <T>
     * @return
     */
    <T> List<Object> batchExecute(String sql, List<T> argList, StatementSetter statementSetter, int batchSize);

    /**
     * 查询操作
     *
     * @param sql  原始SQL
     * @param type 返回类型
     * @param args 参数
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, Class<T> type, Object... args) {
        return getOne(queryForList(sql, type, args));
    }

    /**
     * 查询操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @param type            返回类型
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, StatementSetter statementSetter, Class<T> type) {
        return getOne(queryForList(sql, statementSetter, type));
    }

    /**
     * 查询操作
     *
     * @param sql        原始SQL
     * @param rowMapping 自定义映射
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, RowMapping<T> rowMapping) {
        return getOne(queryForList(sql, rowMapping));
    }

    /**
     * 查询操作
     *
     * @param sql        原始SQL
     * @param rowMapping 自定义映射
     * @param args       参数
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, RowMapping<T> rowMapping, Object... args) {
        return getOne(queryForList(sql, new JdbcStatementSetter(args), rowMapping));
    }

    /**
     * 查询操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @param rowMapping      自定义映射
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, StatementSetter statementSetter, RowMapping<T> rowMapping) {
        return getOne(queryForList(sql, statementSetter, rowMapping));
    }

    /**
     * 查询操作
     *
     * @param sql  原始SQL
     * @param type 查询类型
     * @param args 参数
     * @param <T>
     * @return 查询的数据
     */
    default <T> List<T> queryForList(String sql, Class<T> type, Object... args) {
        return queryForList(sql, new JdbcStatementSetter(args), type);
    }

    /**
     * 查询操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @param type            查询类型
     * @param <T>
     * @return 查询的数据
     */
    default <T> List<T> queryForList(String sql, StatementSetter statementSetter, Class<T> type) {
        RowMapping rowMapping;
        if (ReflectUtil.isBaseClass(type)) {
            rowMapping = new SingleColumnRowMapping<>(type);
        } else if (Map.class.isAssignableFrom(type)) {
            rowMapping = new ColumnMapRowMapping();
        } else {
            rowMapping = new BeanPropertyRowMapping(type);
        }
        return queryForList(sql, statementSetter, rowMapping);
    }

    /**
     * 查询操作
     *
     * @param sql        原始SQL
     * @param rowMapping 自定义映射
     * @param <T>
     * @return 查询的数据
     */
    default <T> List<T> queryForList(String sql, RowMapping<T> rowMapping) {
        return queryForList(sql, new JdbcStatementSetter(null), rowMapping);
    }

    /**
     * 查询操作
     *
     * @param sql        原始SQL
     * @param rowMapping 自定义映射
     * @param args       参数
     * @param <T>
     * @return 查询的数据
     */
    default <T> List<T> queryForList(String sql, RowMapping<T> rowMapping, Object... args) {
        return queryForList(sql, new JdbcStatementSetter(args), rowMapping);
    }

    /**
     * 查询操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @param rowMapping      自定义映射
     * @param <T>
     * @return 查询的数据
     */
    <T> List<T> queryForList(String sql, StatementSetter statementSetter, RowMapping<T> rowMapping);

    default <T> T getOne(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        throw new DreamRunTimeException("数据存在多条");
    }

    /**
     * 根据主键更新
     *
     * @param view 更新对象
     * @return
     */
    int updateById(Object view);

    /**
     * 根据主键非空更新
     *
     * @param view 更新对象
     * @return
     */
    int updateNonById(Object view);

    /**
     * 插入数据
     *
     * @param view 插入对象
     * @return
     */
    int insert(Object view);

    /**
     * 批量插入
     *
     * @param viewList 数据集合
     * @return
     */
    <T> List<Object> batchInsert(List<T> viewList);

    /**
     * 批量更新
     *
     * @param viewList 数据集合
     * @return
     */
    <T> List<Object> batchUpdateById(List<T> viewList);
}

```
## 测试一：查询单条

**测试**

```java
    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        AccountView accountView = jdbcMapper.queryForObject("select id,name from account where id=?", AccountView.class, 1);
        System.out.println("查询结果：" + accountView);
    }
```

**控制台输出**

```tex
方法：null
语句：select id,name from account where id=?
参数：[]
用时：23ms
查询结果：AccountView{id=1, name='Jone', age=null, email='null'}
```

## 测试二：查询多条

**测试**

```java
    /**
     * 测试查询多条
     */
    @Test
    public void testSelectList() {
        List<AccountView> accountViews = jdbcMapper.queryForList("select id,name from account where id>?",
                                                                 AccountView.class, 3);
        System.out.println("查询结果：" + accountViews);
    }
```

**控制台输出**

```tex
方法：null
语句：select id,name from account where id>?
参数：[]
用时：31ms
查询结果：[AccountView{id=4, name='Sandy', age=null, email='null'}, AccountView{id=5, name='Billie', age=null, email='null'}]
```

## 测试三：查询多条且手动设置参数

**测试**

```java
    /**
     * 测试查询多条且手动设置参数
     */
    @Test
    public void testSelectList2() {
        List<AccountView> accountViews = jdbcMapper.queryForList("select id,name from account where id>?",new StatementSetter(){
            @Override
            public void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException {
                ps.setInt(1,3);
            }
        }, AccountView.class);
        System.out.println("查询结果：" + accountViews);
    }
```

**控制台输出**

```tex
方法：null
语句：select id,name from account where id>?
参数：[]
用时：36ms
查询结果：[AccountView{id=4, name='Sandy', age=null, email='null'}, AccountView{id=5, name='Billie', age=null, email='null'}]
```

## 测试四：查询多条且手动映射

**测试**

```java
    /**
     * 测试查询多条且手动映射
     */
    @Test
    public void testSelectListMapping() {
        List<AccountView> accountViews = jdbcMapper.queryForList("select id,name from account where id>?",
                                                                 new RowMapping<AccountView>() {
            @Override
            public AccountView mapTow(ResultSet resultSet) throws SQLException {
                AccountView accountView=new AccountView();
                accountView.setId(resultSet.getInt(1));
                accountView.setName(resultSet.getString(2));
                return accountView;
            }
        }, 3);
        System.out.println("查询结果：" + accountViews);
    }
```

**控制台输出**

```tex
方法：null
语句：select id,name from account where id>?
参数：[]
用时：32ms
查询结果：[AccountView{id=4, name='Sandy', age=null, email='null'}, AccountView{id=5, name='Billie', age=null, email='null'}]
```

## 测试五：更新操作

**测试**

```java
    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        jdbcMapper.execute("update account set name=? where id=?","accountName",1);
    }
```

**控制台输出**

```tex
方法：null
语句：update account set name=? where id=?
参数：[]
用时：12ms
```

## 测试六：更新操作且手动设置参数

**测试**

```java
    /**
     * 更新操作且手动设置参数
     */
    @Test
    public void testUpdate2() {
        AccountView accountView=new AccountView();
        accountView.setName("accountName");
        accountView.setId(1);
        jdbcMapper.execute("update account set name=? where id=?", new StatementSetter() {
            @Override
            public void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException {
                ps.setString(1,accountView.getName());
                ps.setInt(2,accountView.getId());
            }
        });
    }
```

**控制台输出**

```tex
方法：null
语句：update account set name=? where id=?
参数：[]
用时：11ms
```



## 测试七：新增操作

**测试**

```java
    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        jdbcMapper.execute("insert into account(id,name)values(?,?)",1,"accountName");
    }
```

**控制台输出**

```tex
方法：null
语句：insert into account(id,name)values(?,?)
参数：[]
用时：15ms
```

## 测试八：新增操作且手动设置参数

**测试**

```java
    /**
     * 测试插入且手动设置参数
     */
    @Test
    public void testInsert2() {
        AccountView accountView = new AccountView();
        accountView.setName("accountName");
        accountView.setId(14);
        jdbcMapper.execute("insert into account(id,name)values(?,?)", new StatementSetter() {
            @Override
            public void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException {
                ps.setInt(1, accountView.getId());
                ps.setString(2, accountView.getName());
            }
        });
    }
```

**控制台输出**

```tex
方法：null
语句：insert into account(id,name)values(?,?)
参数：[]
用时：20ms
```

## 测试九：删除操作

**测试**

```java
    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        jdbcMapper.execute("delete from account where id=?",1);
    }
```

**控制台输出**

```tex
方法：null
语句：delete from account where id=?
参数：[]
用时：11ms
```

## 测试十：删除操作且手动设置参数

**测试**

```java
    /**
     * 测试删除且手动设置参数
     */
    @Test
    public void testDelete2() {
        AccountView accountView = new AccountView();
        accountView.setId(1);
        jdbcMapper.execute("delete from account where id=?", new StatementSetter() {
            @Override
            public void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException {
                ps.setInt(1,accountView.getId());
            }
        });
    }
```

**控制台输出**

```tex
方法：null
语句：delete from account where id=?
参数：[]
用时：14ms
```


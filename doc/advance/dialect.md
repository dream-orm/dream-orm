# 方言

**配置方言在不同数据库下做不同的SQL翻译。**

**配置方言方案一**

```java
    /**
     * 默认使用MySQL方言
     *
     * @return
     */
    @Bean
    public ToSQL toSQL() {
        return new ToMYSQL();
    }
```

**配置方言方案二：工厂配置（不建议，否则配置文件配置会失效）**

```java
    /**
     * 配置方言方案二：工厂配置
     *
     * @return
     */
    @Bean
    public DialectFactory dialectFactory() {
        DefaultDialectFactory defaultDialectFactory = new DefaultDialectFactory();
        defaultDialectFactory.setToSQL(new ToMYSQL());
        return defaultDialectFactory;
    }
```

**配置方言方案三：配置文件配置**

```yaml
dream:
  toSQL: com.dream.antlr.sql.ToMySQL
```

若内置方言无法满足，可以继承接口[ToPubSQL](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-antlr/src/main/java/com/dream/antlr/sql/ToPubSQL.java)实现自己的方言。

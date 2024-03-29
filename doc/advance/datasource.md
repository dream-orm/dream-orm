# 动态数据源

**支持同一个service调用不同的数据源，但数据源的类型必须是相同的。**

**开启注解**

```java
public @interface EnableShare {
    Class<? extends DataSource> value();
}
```

| 属性  | 描述                 |
| ----- | -------------------- |
| value | DataSource实现类类型 |

**数据源配置**

```yaml
dream:
  datasource:
    master:
      driverClassName: com.mysql.jdbc.Driver
      jdbcUrl: jdbc:mysql://192.168.0.3/d-open
      username: root
      password: root
      keepaliveTime: 1000
      isReadOnly: false
    slave:
      driverClassName: com.mysql.jdbc.Driver
      jdbcUrl: jdbc:mysql://192.168.0.3/d-open-6c
      username: root
      password: root
```

注：dream.datasource固定，master和slave为数据连接池名称，其他为数据连接池字段属性

**数据源选择**

```java
public @interface Share {
    String value();
}
```

| 属性  | 描述                         |
| ----- | ---------------------------- |
| value | 数据连接池名称，默认是master |

## 读写分离（分片）

默认的数据源选择是根据注解判断的，当然也可以重写数据源选择方法，实现自定义分片。

```java
public interface ShardStrategy {
    /**
     * 数据源分片
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @return 分片的数据源名称
     */
    String strategy(MappedStatement mappedStatement);
}
```

利用监听器注入分片

```java
@Bean
public Listener[] listeners() {
    return new Listener[]{new ShardListener(new DefaultShardStrategy())};
}
```

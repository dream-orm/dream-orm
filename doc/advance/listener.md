# 监听器

**统一监听SQL，监听执行前，执行后和执行异常。依赖`Listener`接口，自定义监听器继承自该接口即可。**

```java
/**
 * SQL执行监听器
 */
public interface Listener {

    /**
     * SQL操作执行前进入此方法
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     */
    void before(MappedStatement mappedStatement);

    /**
     * SQL操作正常返回进入此方法
     *
     * @param result          SQL操作返回的数据
     * @param mappedStatement 编译后的接口方法详尽信息
     */
    void afterReturn(Object result, MappedStatement mappedStatement);

    /**
     * SQL操作异常进入此方法
     *
     * @param e               异常类
     * @param mappedStatement 编译后的接口方法详尽信息
     */
    void exception(SQLException e, MappedStatement mappedStatement);
}
```

**配置监听器方案一**

```java
/**
 * 配置SQL输出
 *
 * @return
 */
@Bean
public Listener[] listeners() {
    return new Listener[]{new DebugListener()};
}
```

**配置监听器方案二：工厂配置（不建议，否则配置文件配置会失效）**

```java
/**
 * 配置监听器方案二：工厂配置
 * @return
 */
@Bean
public ListenerFactory listenerFactory(){
    ListenerFactory listenerFactory=new DefaultListenerFactory();
    listenerFactory.listeners(new DebugListener());
    return listenerFactory;
}
```

**配置监听器方案三：配置文件配置**

```yaml
dream:
  listeners:
    - com.dream.drive.listener.DebugListener
```

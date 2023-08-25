# 事务管理

**对于集成到`springboot`的`dream-orm`，`springboot`已经接管了事务，但其实`dream-orm`具备脱离`sprinboot`单独启动的能力，因此必要要具备事务管理器，提供启动创建类`DriveFactory`。**

```java
public interface DriveFactory {
    /**
     * session会话
     * @return session会话
     */
    Session session();

    /**
     * 模板操作接口
     * @return 模板操作接口
     */
    TemplateMapper templateMapper();

    /**
     * 链式操作接口
     * @return 链式操作接口
     */
    FlexMapper flexMapper();

    /**
     * 链式强化接口
     * @return 链式强化接口
     */
    FlexChainMapper flexChainMapper();

    /**
     * 不翻译直接执行SQL接口
     * @return 不翻译直接执行SQL接口
     */
    JdbcMapper jdbcMapper();
}
```

事务管理

```java
public class TransManager {
    /**
     * 执行事务
     * @param supplier 自定义内容
     * @param <T>
     * @return 数据
     */
    public static <T> T exec(Supplier<T> supplier) {
        return exec(supplier, Propagation.REQUIRED);
    }

    /**
     * 执行事务
     * @param supplier 自定义内容
     * @param propagation 事务传播机制
     * @param <T>
     * @return 数据
     */
    public static <T> T exec(Supplier<T> supplier, Propagation propagation) {
        ....
    }
```

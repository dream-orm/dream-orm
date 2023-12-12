# 字段拦截处理

此功能可完成字段修改，列如：加密、解密、掩码、一对一、一对多、多对多等功能，涉及一个注解类和一个接口类。

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Processor {

    Class<? extends ActionProcessor> value();
}
```

| 属性  | 描述       |
| ----- | ---------- |
| value | 字段处理类 |

```java
/**
 * 此接口继承自LoopAction，核心操作在LoopAction提供的方法里，此类仅仅提供额外信息作为辅助
 */
public interface ActionProcessor extends LoopAction {
    void init(Field field, Map<String, Object> paramMap, Configuration configuration);
}

```

| 参数属性      | 描述              |
| ------------- | ----------------- |
| field         | 注解修饰的字段    |
| paramMap      | 注解的内容        |
| configuration | dream-orm全局配置 |

**一对一，一对多，多对多**

```java
@Processor(FetchActionProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Fetch {
    /**
     * 自定义sql满足复杂开发
     *
     * @return
     */
    String sql();
}
```

| 属性 | 描述                                            |
| ---- | ----------------------------------------------- |
| sql  | 需要执行的sql，执行结果值填充到注解修饰的字段里 |

另外也提供容易的fetch

```java
@Processor(EasyFetchActionProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EasyFetch {
    /**
     * 查询的目标表，为空则根据字段属性类型取表名
     *
     * @return
     */
    String table() default "";

    /**
     * 目标条件字段
     *
     * @return
     */
    String column();

    /**
     * 需要查询的目标字段，为空则根据字段属性类型决定查询字段
     *
     * @return
     */
    String[] columns() default {};

    /**
     * 当前对象属性名，取该值和目标条件字段作为查询条件
     *
     * @return
     */
    String field();
}
```


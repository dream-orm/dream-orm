# 关键字插件

**数据库关键字，不是关键字可以不加特殊符号，关键字必须要加，dream-orm提供方案，SQL语句可以统一不加特殊符号，一样可以正常执行。默认插件不开启，有两种开启方式。**

**注入器：[TransformInject](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/transform/inject/TransformInject.java)**

**处理器：[TransformHandler](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/transform/inject/TransformHandler.java)**

```java
public interface TransformHandler {

    /**
     * 关键字拦截
     *
     * @param column      字段名
     * @param invokerList 当前应用的@函数解析器
     * @return 是否是关键字
     */
    boolean intercept(String column, List<Invoker> invokerList);
}
```

**开启关键字插件方式一**

```java
/**
 * 开启插件方式一
 *
 * @return
 */
@Bean
public Inject[] injects() {
    /**
     * 开启关键字插件
     */
    return new Inject[]{new TransformInject(new InterceptTransformHandler("keyword.txt"))};
}
```


**开启关键字插件方式二**

```java
    /**
     * 开启插件方式二
     *
     * @return
     */
    @Bean
    public InjectFactory injectFactory() {
        InjectFactory injectFactory = new DefaultInjectFactory();
        /**
         * 开启关键字插件
         */
        injectFactory.injects(new TransformInject(new InterceptTransformHandler("keyword.txt")));
        return injectFactory;
    }
```

**在`keyword.txt`配置自定义的关键字，配置好后，运行下测试案例，就会看到执行SQL自动打上关键字标识。**

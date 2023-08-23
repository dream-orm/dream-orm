# 动态表名插件

**动态表名指的是用户在对数据进行操作的时候，动态修改当前操作的表。一旦开启动态表名，缓存将失效。**

**注入器：DynamicInject**

**处理器：DynamicHandler**

```java
public interface DynamicHandler {
    /**
     * 判断是否应用动态表名
     *
     * @param methodInfo mapper方法详尽信息
     * @param table      数据表
     * @return
     */
    boolean isDynamic(MethodInfo methodInfo, String table);

    /**
     * 根据当前表名，返回新的表名
     * @param table 当前表名
     * @return 根据当前表名，返回新的表名
     */
    String process(String table);
}
```

**开启数据权限插件方式一**

```java
/**
 * 开启插件方式一
 *
 * @return
 */
@Bean
public Inject[] injects() {
    /**
         * 开启插件
         */
        return new Inject[]{
                /**
                 * 开启动态表名插件
                */
                new DynamicInject(new DynamicHandler() {
                    @Override
                    public boolean isDynamic(MethodInfo methodInfo, String table) {
                        return true;
                    }

                    @Override
                    public String process(String table) {
                        System.out.println("修改表名：" + table);
                        return table;
                    }
                })
        };
}
```


**开启数据权限插件方式二**

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
         * 开启动态表名插件
         */
        injectFactory.injects(new DynamicInject(new DynamicHandler() {
                    @Override
                    public boolean isDynamic(MethodInfo methodInfo, String table) {
                        return true;
                    }

                    @Override
                    public String process(String table) {
                        System.out.println("修改表名：" + table);
                        return table;
                    }
                }));
        return injectFactory;
    }
```


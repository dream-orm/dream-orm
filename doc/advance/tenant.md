# 多租户插件

**多租户对增删改查自动加入租户条件，并且开启后租户将完全由系统接管，开发者无法对租户做更新和插入操作。**

**注入器：[TenantInject](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/tenant/inject/TenantInject.java)**

**处理器：[TenantHandler](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/tenant/inject/TenantHandler.java)**

```java
public interface TenantHandler {
    /**
     * 是否应用多租户
     *
     * @param methodInfo mapper方法详尽信息
     * @param tableInfo  主表详尽信息
     * @return
     */
    default boolean isTenant(MethodInfo methodInfo, TableInfo tableInfo) {
        return tableInfo.getFieldName(getTenantColumn()) != null;
    }

    /**
     * 返回应用的多租户字段
     *
     * @return
     */
    default String getTenantColumn() {
        return "tenant_id";
    }

    /**
     * 返回应用的多租户值
     *
     * @return
     */
    Object getTenantObject();
}
```

**开启多租户插件方式一**

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
                new TenantInject(new TenantHandler() {
                    public boolean isTenant(MethodInfo methodInfo, TableInfo tableInfo) {
                        return tableInfo.getFieldName(getTenantColumn()) != null;
                    }

                    @Override
                    public String getTenantColumn() {
                        return "tenant_id";
                    }

                    @Override
                    public Object getTenantObject() {
                        return 1;
                    }
                })};
}
```


**开启多租户插件方式二**

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
         * 开启多租户插件
         */
        injectFactory.injects(new TenantInject(new TenantHandler() {
            public boolean isTenant(MethodInfo methodInfo, TableInfo tableInfo) {
                return tableInfo.getFieldName(getTenantColumn()) != null;
            }

            @Override
            public String getTenantColumn() {
                return "tenant_id";
            }

            @Override
            public Object getTenantObject() {
                return 1;
            }
        }));
        return injectFactory;
    }
```

# 数据权限插件

**数据权限是不需要在SQL里加入占位符的，而是根据主表进行判断，动态注入条件。**

**注入器：[PermissionInject](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/permission/inject/PermissionInject.java)**

**处理器：[PermissionInject](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/permission/inject/PermissionInject.java)**

```java
public interface PermissionHandler {

    /**
     * 判断是否应用数据权限
     *
     * @param methodInfo mapper方法详尽信息
     * @param tableInfo  主表详尽信息
     * @return
     */
    boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo);

    /**
     * 获取数据权限SQL
     *
     * @param alias 主表别名
     * @return
     */
    String getPermission(String alias);

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
                new PermissionInject(new PermissionHandler() {
                    @Override
                    public boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo) {
                        return tableInfo.getFieldName("field_id") != null;
                    }

                    @Override
                    public String getPermission(String alias) {
                        return alias + ".dept_id=1";
                    }
                })};
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
         * 开启数据权限插件
         */
        injectFactory.injects(new PermissionInject(new PermissionHandler() {
            @Override
            public boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo) {
                return tableInfo.getFieldName("field_id") != null;
            }

            @Override
            public String getPermission(String alias) {
                return alias + ".dept_id=1";
            }
        }));
        return injectFactory;
    }
```

**权限操作仅仅是对表存在dept_id的表做权限控制，当然实际环境随机应变。**


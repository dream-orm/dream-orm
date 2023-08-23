# 逻辑删除插件

**逻辑删除不仅仅将删除语句改成update语句，而且还会对查询或更新SQL追求逻辑删除条件。**

**注入器：[LogicInject](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/logic/inject/LogicInject.java)**

**处理器：[LogicHandler](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/logic/inject/LogicHandler.java)**

```java
public interface LogicHandler {

    /**
     * 判断是否应用逻辑删除
     *
     * @param methodInfo mapper方法详尽信息
     * @param tableInfo  主表详尽信息
     * @return
     */
    default boolean isLogic(MethodInfo methodInfo, TableInfo tableInfo) {
        return tableInfo.getFieldName(getLogicColumn()) != null;
    }

    /**
     * 未删除的标识
     *
     * @return
     */
    default String getNormalValue() {
        return "0";
    }

    /**
     * 删除后的标识
     *
     * @return
     */
    default String getDeletedValue() {
        return "1";
    }

    /**
     * 逻辑删除字段
     *
     * @return
     */
    String getLogicColumn();


}
```

**开启逻辑插件方式一**

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
                new LogicInject(new LogicHandler() {
                    @Override
                    public boolean isLogic(MethodInfo methodInfo, TableInfo tableInfo) {
                        return tableInfo.getFieldName(getLogicColumn()) != null;
                    }

                    @Override
                    public String getNormalValue() {
                        return "0";
                    }
                    
                    @Override
                    public String getDeletedValue() {
                        return "1";
                    }

                    @Override
                    public String getLogicColumn() {
                        return "del_flag";
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
         * 开启逻辑删除插件
         */
        injectFactory.injects(                
            new LogicInject(new LogicHandler() {
                    @Override
                    public boolean isLogic(MethodInfo methodInfo, TableInfo tableInfo) {
                        return tableInfo.getFieldName(getLogicColumn()) != null;
                    }

                    @Override
                    public String getNormalValue() {
                        return "0";
                    }
                    
                    @Override
                    public String getDeletedValue() {
                        return "1";
                    }

                    @Override
                    public String getLogicColumn() {
                        return "del_flag";
                    }
                }));
        return injectFactory;
    }
```


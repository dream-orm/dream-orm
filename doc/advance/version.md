# 乐观锁插件

**注入器：[VersionInject](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/version/inject/VersionInject.java)**

**处理器：[VersionHandler](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-mate/src/main/java/com/dream/mate/version/inject/VersionHandler.java)**

```java
public interface VersionHandler {

    /**
     * 判断是否应用乐观锁
     *
     * @param methodInfo mapper方法详尽信息
     * @param table      主表
     * @return
     */
    default boolean isVersion(MethodInfo methodInfo, String table) {
        if (methodInfo != null) {
            TableInfo tableInfo = methodInfo.getConfiguration().getTableFactory().getTableInfo(table);
            if (tableInfo != null) {
                ColumnInfo columnInfo = tableInfo.getColumnInfo(getVersionColumn());
                return columnInfo != null;
            }
        }
        return false;
    }

    /**
     * 获取当前版本值
     *
     * @param objectWrapper
     * @return
     */
    String getCurVersion(ObjectWrapper objectWrapper);

    /**
     * 获取下个版本值
     *
     * @return
     */
    String getNextVersion(ObjectWrapper objectWrapper);

    /**
     * 乐观锁字段
     *
     * @return
     */
    String getVersionColumn();
}
```

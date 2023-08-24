# 映射拦截

**`dream-orm`映射并不是仅仅根据字段名称，还根据字段所属表名决定。在值映射的过程中，做一些私有化操作是十分必要的，比如：统一或指定对字段做数据脱密、加密，字典回写，一对一，一对多，多对一，多对多等操作。需要用到两个重要接口[ExtractorFactory](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-system/src/main/java/com/dream/system/core/resultsethandler/extract/ExtractorFactory.java)和[Extractor](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-system/src/main/java/com/dream/system/core/resultsethandler/extract/Extractor.java)。**

```java
public interface ExtractorFactory {

    /**
     * 获取数据处理器
     *
     * @param columnInfo 表字段信息
     * @param field      java属性字段
     * @param property   映射的属性名
     * @return 数据处理器
     */
    Extractor getExtractor(ColumnInfo columnInfo, Field field, String property);
}
```

**该接口用于创建`Extractor`对象。**

| getExtractor参数名 | 描述                           | 不为空条件                 |
| ------------------ | ------------------------------ | -------------------------- |
| columnInfo         | 查询字段的信息                 | 查询的字段来自数据库表字段 |
| field              | 查询字段映射到java类属性的信息 | 查询的字段映射到java类属性 |
| property           | 查询字段映射到对象的名称       | 绝不为空                   |

```java
/**
 * 映射拦截
 */
public interface Extractor {
    /**
     * 自定义处理方法
     *
     * @param property      字段名
     * @param value         SQL查询值
     * @param objectFactory 反射工厂
     */
    void extract(String property, Object value, ObjectFactory objectFactory);
}
```

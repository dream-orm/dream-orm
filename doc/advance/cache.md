# 数据缓存

**`dream-orm`目前只提供了基于内存的缓存。默认处于关闭状态，若开启缓存，一切的查询操作都会进行读取和存放缓存。而非查询并不会清空所有缓存，只会清空本次操作表的缓存，因此特别适合单系统且数据修改全来自`dream-orm`。依赖接口`Cache`。**

```java
/**
 * 缓存接口
 */
public interface Cache {
    /**
     * 缓存数据
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @param value           数据库查询后的值
     */
    void put(MappedStatement mappedStatement, Object value);

    /**
     * 获取数据
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @return
     */
    Object get(MappedStatement mappedStatement);

    /**
     * 删除数据
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     */
    void remove(MappedStatement mappedStatement);

    /**
     * 清空数据
     */
    void clear();
}
```

**配置缓存方案一**

```java
    /**
     * 开启缓存方式一
     *
     * @return
     */
    @Bean
    public Cache cache() {
        return new MemoryCache();
    }
```

**配置缓存方案二：工厂配置（不建议，否则配置文件配置会失效）**

```java
    /**
     * 开启缓存方式二
     *
     * @return
     */
    @Bean
    public CacheFactory cacheFactory() {
        DefaultCacheFactory cacheFactory=new DefaultCacheFactory();
        cacheFactory.setCache(new MemoryCache());
        return cacheFactory;
    }
```

**配置缓存方案三：配置文件配置**

```yaml
dream:
  cache: com.dream.system.cache.MemoryCache
```

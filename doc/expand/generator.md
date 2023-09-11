# 代码生成器

提供模块 `dream-orm-generator` 生成代码，需要额外引入。

```xml
<dependency>
    <groupId>com.dream-orm</groupId>
    <artifactId>dream-orm-generator</artifactId>
    <version>${dream-orm.version}</version>
</dependency>
```

```java
/**
 * 代码生成配置
 */
public interface GeneratorHandler {
    /**
     * 作者名称
     *
     * @return 作者名称
     */
    String author();

    /**
     * 生成目录
     *
     * @return 生成目录
     */
    String sourceDir();

    /**
     * 生成时间
     *
     * @return 生成时间
     */
    default String dateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 是否覆盖
     *
     * @param table 数据表
     * @return 是否覆盖
     */
    default boolean override(String table) {
        return false;
    }

    /**
     * 是否生成数据表代码
     *
     * @param table 数据表
     * @return 是否生成数据表代码
     */
    boolean support(String table);

    /**
     * 控制层全类名
     *
     * @param table 数据表
     * @return 控制层全类名
     */
    String controllerClassName(String table);

    /**
     * 接口服务全类名
     *
     * @param table 数据表
     * @return 接口服务全类名
     */
    String serviceClassName(String table);

    /**
     * 接口服务实现全类名
     *
     * @param table 数据表
     * @return 接口服务实现全类名
     */
    String serviceImplClassName(String table);

    /**
     * 实体映射全类名
     *
     * @param table 数据表
     * @return 实体映射全类名
     */
    String tableClassName(String table);

    /**
     * vo视图全类名（列表页字段）
     *
     * @param table 数据表
     * @return vo视图全类名（列表页字段）
     */
    String voClassName(String table);

    /**
     * bo视图全类名（编辑页字段）
     *
     * @param table 数据表
     * @return bo视图全类名（编辑页字段）
     */
    String boClassName(String table);

    /**
     * 查询视图全类名
     *
     * @param table 数据表
     * @return 查询视图全类名
     */
    String dtoClassName(String table);
}
```

**测试**

```java
public class HelloWordGeneratorTest {
    public static void main(String[] args) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://192.168.0.242/d-open");
        dataSource.setUsername("root");
        dataSource.setPassword("BMW#Halu@1234%");
        new Generator(dataSource, new GeneratorHandlerImpl()).generate();
    }

    public static class GeneratorHandlerImpl extends AbstractGeneratorHandler {

        @Override
        protected String basePackage() {
            return "com.dream.codegen";
        }

        @Override
        public String author() {
            return "moxa";
        }

        @Override
        public String sourceDir() {
            return "D:\\projects\\dream-project\\dream-orm\\dream-orm-hello-world-generator\\src\\main\\java";
        }

        @Override
        public boolean support(String table) {
            return table.contains("user");
        }
    }
}
```

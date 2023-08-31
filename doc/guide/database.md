# 支持的数据库类型

dream-orm支持的数据库类型，如下表格所示，其余未写出的由于没有经过测试，此外我们还可以通过自定义方言的方式支持更多的数据库。

| 数据库        | 描述                | 方言                         |
| ------------- |-------------------| ---------------------------- |
| mysql         | MySQL 数据库         | com.dream.antlr.sql.ToMYSQL  |
| mariadb       | MariaDB 数据库       | com.dream.antlr.sql.ToMYSQL  |
| oracle        | Oracle 数据库        | com.dream.antlr.sql.ToORACLE |
| oracle12c     | Oracle12c 及以上数据库  | com.dream.antlr.sql.ToORACLE |
| postgresql    | PostgreSQL 数据库    | com.dream.antlr.sql.ToPGSQL  |
| sqlserver2005 | SQLServer2005 数据库 | com.dream.antlr.sql.ToMSSQL  |
| sqlserver     | SQLServer 数据库     | com.dream.antlr.sql.ToMSSQL  |
| dm            | 达梦数据库             | com.dream.antlr.sql.ToDM     |

## 数据库方言

**在某些场景下，比如用户要实现自己的 SQL 生成逻辑时，我们可以通过实现自己的方言达到这个目的，实现方言分为两个步骤：**

1. 继承com.dream.antlr.sql.ToPubSQL编写对应数据库抽象树转SQL
2. 注入方言

**springboot项目注入自定义方言两种形式**

1. java类注入

   ```java
   @Bean
   public ToSQL toSQL() {
       return new ToMYSQL();
   }
   ```

2. yml配置

```yaml
dream:
  toSQL: com.dream.antlr.sql.ToMYSQL
```

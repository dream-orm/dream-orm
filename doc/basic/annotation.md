# 核心注解

## Table

**`@Table` 主要是用于给 Entity 实体类添加标识，用于描述 实体类 和 数据库表 的关系，以及对实体类进行的一些 功能辅助。**

```java
public @interface Table {
    /**
     * 数据库表名称
     *
     * @return
     */
    String value();
}

```

| 注解属性 | 描述             |
| -------- | ---------------- |
| value    | 指定绑定的数据表 |

## Id

**在 Entity 类中， 使用 `@Id` 注解来标识主键。**

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {

}
```

## Column

**在 Entity 类中， 使用 `@Column` 注解来标识数据库字段。**

```java
public @interface Column {
    /**
     * 对应的数据库表字段名称
     *
     * @return
     */
    String value();

    /**
     * 对应的数据库表字段类型
     *
     * @return
     */
    int jdbcType() default Types.NULL;
}
```

| 注解属性 | 描述                   |
| -------- | ---------------------- |
| value    | 对应的数据库表字段名称 |
| jdbcType | 对应的数据库表字段类型 |

## Join

**在 Entity 类中， 使用 `@Join` 注解来标识数据表之间联系。**

```java
public @interface Join {
    /**
     * 当前表关联的字段名
     *
     * @return
     */
    String column();

    /**
     * 关联的目标表字段名
     *
     * @return
     */
    String joinColumn();

    /**
     * 关联类型
     *
     * @return
     */
    JoinType joinType() default JoinType.LEFT_JOIN;
}
```

| 注解属性   | 描述           |
| ---------- | -------------- |
| column     | 该表字段名     |
| joinColumn | 关联表的字段名 |
| joinType   | 关联类型       |

## View

**在 Entity 类中， 使用 `@View` 注解来标识引用数据表的部分字段。View修饰的类属性，必须和Table修饰的属性一致，才能做到映射**

```java
public @interface View {
    /**
     * 对应的数据库表映射的java类
     *
     * @return
     */
    Class<?> value();
}
```

| 注解属性 | 描述             |
| -------- | ---------------- |
| value    | 来源数据表映射类 |

## Ignore

**在 Entity 类中， 使用 `@Ignore` 注解忽略字段。**

```java
public @interface Ignore {

}
```

## PageQuery

**在`Mapper`接口方法中，使用 `@PageQuery` 注解开启分页。**

```java
public @interface PageQuery {
    /**
     * Page地址
     *
     * @return
     */
    String value() default "page";
}
```

| 属性名 | 描述         |
| ------ | ------------ |
| value  | Page对象地址 |


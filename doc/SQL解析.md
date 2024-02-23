# SQL解析

dream-orm是可以支持写MySQL语法，然后自动在其他数据库下转换，而且无视SQL复杂度，需要经过**词法分析->语法分析->规约->转换**，最终得到目的SQL。对于以下SQL，先进行词法分析

## 词法分析

```sql
select name,avg(age) from users where dept_id=1001 group by name
```

### 词法分析结果

| 单词    | 词类型 | 描述       |
| ------- | ------ | ---------- |
| select  | SELECT | 关键字     |
| name    | LETTER | 字符       |
| ,       | COMMA  | 逗号       |
| avg     | AVG    | 函数关键字 |
| (       | LBRACE | 左括号     |
| age     | LETTER | 字符       |
| )       | RBRACE | 右括号     |
| from    | FROM   | 关键字     |
| users   | LETTER | 字符       |
| where   | WHERE  | 关键字     |
| dept_id | LETTER | 字符       |
| =       | EQ     | 运算符     |
| 1001    | NUMBER | 数字       |
| group   | GROUP  | 关键字     |
| by      | BY     | 关键字     |
| name    | LETTER | 字符       |

### 源码实现

```java
@Test
public void testQuery() {
        ExprReader exprReader = new ExprReader("select name,avg(age) from users where dept_id=1001 group by name");
        ExprInfo exprInfo;
        while (true){
        exprInfo=exprReader.push();
        if(exprInfo.getExprType()== ExprType.ACC){
        break;
        }
        System.out.println("单词："+exprInfo.getInfo()+"\t\t单词类型："+exprInfo.getExprType());
        }
        }
```

## 语法分析

基于词法分析的基础之上进行语法分析，可以将上诉SQL进行细分成若干语法。

**完整查询语法  select name,avg(age) from users where dept_id=1001 group by name**

| 语法器     | 语法内容             |
| ---------- | -------------------- |
| select语法 | select name,avg(age) |
| from语法   | from users           |
| where语法  | where dept_id=1001   |
| group语法  | group by name        |

**此时select查询等语法可以继续细分，一直细分到语法器能够解析单个词为止。但如何让语法器紧密有条不稳的合作呢？就需要进行规约。**

### select语法解析

```java
@Test
public void testSelect() throws AntlrException {
        ExprReader exprReader = new ExprReader("select name,avg(age)");
        Statement statement = new SelectExpr(exprReader).expr();
        }
```

### from语法解析

```java
@Test
public void testFrom() throws AntlrException {
        ExprReader exprReader = new ExprReader("from users");
        Statement statement = new FromExpr(exprReader).expr();
        }
```

### where语法解析

```java
@Test
public void testWhere() throws AntlrException {
        ExprReader exprReader = new ExprReader("where dept_id=1001");
        Statement statement = new WhereExpr(exprReader).expr();
        }
```

### group语法解析

```java
@Test
public void testGroup() throws AntlrException {
        ExprReader exprReader = new ExprReader("group by name");
        Statement statement = new GroupExpr(exprReader).expr();
        }
```

。。。。更多

### 函数语法解析

```java
@Test
public void testFunc() throws AntlrException {
        ExprReader exprReader = new ExprReader("avg(age)");
        Statement statement = new FunctionExpr(exprReader).expr();
        }
```

### 比较语法解析

```java
@Test
public void testCompare() throws AntlrException {
        ExprReader exprReader = new ExprReader("dept_id=1001");
        Statement statement = new CompareExpr(exprReader).expr();
        }
```

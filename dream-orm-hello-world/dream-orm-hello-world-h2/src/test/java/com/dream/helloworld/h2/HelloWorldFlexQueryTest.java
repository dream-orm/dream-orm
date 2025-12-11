package com.dream.helloworld.h2;

import com.dream.antlr.config.Assist;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToMySQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.flex.def.CaseColumnDef;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.ConditionDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.invoker.TakeMarkInvoker;
import com.dream.helloworld.h2.def.AccountDef;
import com.dream.system.antlr.factory.DefaultInvokerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static com.dream.flex.def.FunctionDef.*;
import static com.dream.helloworld.h2.def.AccountDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexQueryTest {
    private ToSQL toSQL = new ToMySQL();

    /**
     * 测试select多个字段
     */
    @Test
    public void testSelect() {
        QueryDef queryDef = select(account.id, account.name);
        toOut(queryDef.statement());
    }

    /**
     * 测试字段别名
     */
    @Test
    public void testSelectColumnAs() {
        ColumnDef columnDef = account.name.as("uname");
        toOut(columnDef.getStatement());
    }

    /**
     * 测试case
     */
    @Test
    public void testCase() {
        CaseColumnDef columnDef = case_(account.age).when(11).then(11).when("11").then("字符串11").else_("默认值").end();
        toOut(columnDef.getStatement());
    }

    /**
     * 测试case条件
     */
    @Test
    public void testCaseCondition() {
        CaseColumnDef columnDef = case_().when(account.age.eq(11)).then(11).when(account.age.eq("11")).then("字符串11").else_("默认值").end();
        toOut(columnDef.getStatement());
    }

    /**
     * 测试from语句
     */
    @Test
    public void testFrom() {
        QueryDef queryDef = select(account.id, find_in_set(account.age, "12,34,56")).from(account);
        toOut(queryDef.statement());
    }

    /**
     * 测试关联查询
     */
    @Test
    public void testFromJoin() {
        AccountDef account2 = new AccountDef("account2");
        QueryDef queryDef = select(account.id).from(account.leftJoin(account2, account.id, account2.id));
        toOut(queryDef.statement());
    }

    /**
     * 测试关联查询2
     */
    @Test
    public void testFromJoin2() {
        AccountDef account2 = new AccountDef("account2");
        QueryDef queryDef = select(account.id).from(account.leftJoin(account2).on(account.id.eq(account2.id)));
        toOut(queryDef.statement());
    }

    /**
     * 测试Where
     */
    @Test
    public void testWhere() {
        QueryDef queryDef = select(account.id).from(account).where(account.name.like("a"));
        toOut(queryDef.statement());
    }

    /**
     * 测试 and
     */
    @Test
    public void testAnd() {
        ConditionDef conditionDef = account.name.like("a").and(account.age.eq(11));
        toOut(conditionDef.getStatement());
    }

    /**
     * 测试 or
     */
    @Test
    public void testOr() {
        ConditionDef conditionDef = account.name.like("a").or(account.age.eq(11));
        toOut(conditionDef.getStatement());
    }

    /**
     * 测试Where动态条件
     */
    @Test
    public void testWhereDynamic1() {
        QueryDef queryDef = select(account.id).from(account).where(account.name.like("a", Objects::isNull));
        toOut(queryDef.statement());
    }

    /**
     * 测试Where动态条件
     */
    @Test
    public void testWhereDynamic2() {
        QueryDef queryDef = select(account.id).from(account).where(account.name.like("a").when(false));
        toOut(queryDef.statement());
    }

    /**
     * 测试group
     */
    @Test
    public void testGroup() {
        QueryDef queryDef = select(count(account.id)).from(account).groupBy(account.age);
        toOut(queryDef.statement());
    }

    /**
     * 测试having
     */
    @Test
    public void testHaving() {
        QueryDef queryDef = select(count(account.id)).from(account).groupBy(account.age).having(account.name.likeLeft("a").and(account.name.likeRight("b")));
        toOut(queryDef.statement());
    }

    /**
     * 测试orderBy
     */
    @Test
    public void testOrderBy() {
        QueryDef queryDef = select(count(account.id)).from(account).orderBy(account.age.desc(), account.name.asc());
        toOut(queryDef.statement());
    }

    /**
     * 测试Limit
     */
    @Test
    public void testLimit() {
        QueryDef queryDef = select(count(account.id)).from(account).limit(5, 10);
        toOut(queryDef.statement());
    }


    /**
     * 测试Offset
     */
    @Test
    public void testOffset() {
        QueryDef queryDef = select(count(account.id)).from(account).offset(10, 5);
        toOut(queryDef.statement());
    }

    /**
     * 测试Union
     */
    @Test
    public void testUnion() {
        QueryDef queryDef = select(count(account.id)).from(account).union(select(count(account.id)).from(account));
        toOut(queryDef.statement());
    }

    /**
     * 测试UnionAll
     */
    @Test
    public void testUnionAll() {
        QueryDef queryDef = select(count(account.id)).from(account).unionAll(select(count(account.id)).from(account));
        toOut(queryDef.statement());
    }

    /**
     * 测试forUpdate
     */
    @Test
    public void testforUpdate() {
        QueryDef queryDef = select(count(account.id)).from(account).forUpdate();
        toOut(queryDef.statement());
    }

    /**
     * 测试forUpdateNoWait
     */
    @Test
    public void forUpdateNoWait() {
        QueryDef queryDef = select(count(account.id)).from(account).forUpdateNoWait();
        toOut(queryDef.statement());
    }

    @Test
    public void testSelectStr() {
        QueryDef queryDef = select("id", "name").from("user");
        toOut(queryDef.statement());
    }

    private void toOut(Statement statement) {
        InvokerFactory invokerFactory = new DefaultInvokerFactory();
        invokerFactory.addInvoker(TakeMarkInvoker.FUNCTION, TakeMarkInvoker::new);
        try {
            String sql = toSQL.toStr(statement, new Assist(invokerFactory, null), null);
            System.out.println(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

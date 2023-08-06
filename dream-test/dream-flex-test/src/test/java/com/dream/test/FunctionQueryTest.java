package com.dream.test;

import com.dream.antlr.sql.ToMYSQL;
import com.dream.flex.config.DataType;
import com.dream.flex.config.DateType;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.Query;
import com.dream.flex.def.SortDef;
import com.dream.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;

public class FunctionQueryTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    @Test
    public void testStr() {
        Query query = FunctionDef.select(
                        FunctionDef.ascii(UserTableDef.user.id),
                        FunctionDef.len(UserTableDef.user.name),
                        FunctionDef.length(UserTableDef.user.name),
                        FunctionDef.concat(UserTableDef.user.name, UserTableDef.user.email),
                        FunctionDef.group_concat(true, new ColumnDef[]{UserTableDef.user.name, UserTableDef.user.del_flag}, new SortDef[]{UserTableDef.user.name.desc()}, "|"),
                        FunctionDef.concat_ws(",", UserTableDef.user.name, UserTableDef.user.email),
                        FunctionDef.repeat(UserTableDef.user.name, 5),
                        FunctionDef.reverse(UserTableDef.user.name),
                        FunctionDef.replace(UserTableDef.user.name, "a", "b"),
                        FunctionDef.space(5),
                        FunctionDef.substr(UserTableDef.user.name, 5, 2),
                        FunctionDef.upper(UserTableDef.user.name),
                        FunctionDef.ltrim(UserTableDef.user.name),
                        FunctionDef.rtrim(UserTableDef.user.name),
                        FunctionDef.trim(UserTableDef.user.name),
                        FunctionDef.coalesce(UserTableDef.user.name, UserTableDef.user.age)
                )
                .from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void testNumber() {
        Query query = FunctionDef.select(
                        FunctionDef.abs(UserTableDef.user.id),
                        FunctionDef.acos(UserTableDef.user.name),
                        FunctionDef.asin(UserTableDef.user.name),
                        FunctionDef.atan(UserTableDef.user.name),
                        FunctionDef.avg(true, UserTableDef.user.name),
                        FunctionDef.count(true, UserTableDef.user.name),
                        FunctionDef.exp(UserTableDef.user.name),
                        FunctionDef.floor(UserTableDef.user.name),
                        FunctionDef.ln(UserTableDef.user.name),
                        FunctionDef.log(UserTableDef.user.name),
                        FunctionDef.log(FunctionDef.col("2"), UserTableDef.user.name),
                        FunctionDef.log2(UserTableDef.user.name),
                        FunctionDef.log10(UserTableDef.user.name),
                        FunctionDef.max(UserTableDef.user.name),
                        FunctionDef.min(UserTableDef.user.name),
                        FunctionDef.mod(UserTableDef.user.name, FunctionDef.col(4)),
                        FunctionDef.pi(),
                        FunctionDef.pow(FunctionDef.col(2), FunctionDef.col(4)),
                        FunctionDef.power(FunctionDef.col(2), FunctionDef.col(4)),
                        FunctionDef.rand(),
                        FunctionDef.round(FunctionDef.col(23), FunctionDef.col(2)),
                        FunctionDef.sign(UserTableDef.user.dept_id),
                        FunctionDef.sin(UserTableDef.user.dept_id),
                        FunctionDef.sqrt(UserTableDef.user.dept_id),
                        FunctionDef.sum(true, UserTableDef.user.dept_id),
                        FunctionDef.tan(UserTableDef.user.dept_id),
                        FunctionDef.truncate(UserTableDef.user.dept_id, FunctionDef.col(2))
                )
                .from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void testDate() {
        Query query = FunctionDef.select(
                        FunctionDef.curdate(),
                        FunctionDef.datediff(FunctionDef.now(), FunctionDef.now()),
                        FunctionDef.date_sub(FunctionDef.now(), 1, DateType.HOUR),
                        FunctionDef.date_add(FunctionDef.now(), 2, DateType.YEAR),
                        FunctionDef.dayofyear(FunctionDef.now()),
                        FunctionDef.dayofweek(FunctionDef.now()),
                        FunctionDef.year(FunctionDef.now()),
                        FunctionDef.month(FunctionDef.now()),
                        FunctionDef.day(FunctionDef.now()),
                        FunctionDef.hour(FunctionDef.now()),
                        FunctionDef.last_day(FunctionDef.now()),
                        FunctionDef.minute(FunctionDef.now()),
                        FunctionDef.quarter(FunctionDef.now()),
                        FunctionDef.second(FunctionDef.now()),
                        FunctionDef.weekofyear(FunctionDef.now()),
                        FunctionDef.date_format(FunctionDef.now(), "%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j"),
                        FunctionDef.str_to_date(UserTableDef.user.name, "%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j")
                )
                .from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void testOther() {
        Query query = FunctionDef.select(
                        FunctionDef.convert(UserTableDef.user.name, DataType.CHAR),
                        FunctionDef.cast(UserTableDef.user.name, DataType.SIGNED),
                        FunctionDef.isnull(UserTableDef.user.name),
                        FunctionDef.ifnull(UserTableDef.user.name, FunctionDef.col(3)),
                        FunctionDef.if_(UserTableDef.user.name.eq(2), FunctionDef.col(1), FunctionDef.col(2)),
                        FunctionDef.nullif(FunctionDef.col(1), FunctionDef.col(2))
                )
                .from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void testCaseWhen() {
        Query query = FunctionDef.select(
                        FunctionDef.case_().when(UserTableDef.user.name.eq(FunctionDef.col("3"))).then(UserTableDef.user.name).when(UserTableDef.user.name.eq("4")).then(FunctionDef.col("5")).else_(FunctionDef.col(6)).end().as("aa"),
                        FunctionDef.case_(UserTableDef.user.name).when(FunctionDef.col("a")).then(FunctionDef.col("b")).when("a").then(4).else_("7").end().as("vv")
                )
                .from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }
}

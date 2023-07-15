package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.DataType;
import com.moxa.dream.flex.config.DateType;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.def.ColumnDef;
import com.moxa.dream.flex.def.SortDef;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.ColumnDef.column;
import static com.moxa.dream.flex.def.FunctionDef.*;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;

public class FunctionQueryTest {
    private ToSQL toSQL = new ToMYSQL();

    @Test
    public void testStr() {
        SqlInfo muser = select(
                ascii(user.id),
                len(user.name),
                length(user.name),
                concat(user.name, user.email),
                group_concat(true, new ColumnDef[]{user.name, user.del_flag}, new SortDef[]{user.name.desc()}, "|"),
                concat_ws(",", user.name, user.email),
                repeat(user.name, 5),
                reverse(user.name),
                replace(user.name, "a", "b"),
                space(5),
                substr(user.name, 5, 2),
                upper(user.name),
                ltrim(user.name),
                rtrim(user.name),
                trim(user.name),
                coalesce(user.name, user.age)
        )
                .from(user.as("u")).toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void testNumber() {
        SqlInfo muser = select(
                abs(user.id),
                acos(user.name),
                asin(user.name),
                atan(user.name),
                avg(true, user.name),
                count(true, user.name),
                exp(user.name),
                floor(user.name),
                ln(user.name),
                log(user.name),
                log(column("2"), user.name),
                log2(user.name),
                log10(user.name),
                max(user.name),
                min(user.name),
                mod(user.name, column(4)),
                pi(),
                pow(column(2), column(4)),
                power(column(2), column(4)),
                rand(),
                round(column(23), column(2)),
                sign(user.dept_id),
                sin(user.dept_id),
                sqrt(user.dept_id),
                sum(true, user.dept_id),
                tan(user.dept_id),
                truncate(user.dept_id, column(2))
        )
                .from(user.as("u")).toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void testDate() {
        SqlInfo muser = select(
                curdate(),
                datediff(now(), now()),
                date_sub(now(), 1, DateType.HOUR),
                date_add(now(), 2, DateType.YEAR),
                dayofyear(now()),
                dayofweek(now()),
                year(now()),
                month(now()),
                day(now()),
                hour(now()),
                last_day(now()),
                minute(now()),
                quarter(now()),
                second(now()),
                weekofyear(now()),
                date_format(now(), "%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j"),
                str_to_date(user.name, "%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j")
        )
                .from(user.as("u")).toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void testOther() {
        SqlInfo muser = select(
                convert(user.name, DataType.CHAR),
                cast(user.name, DataType.SIGNED),
                isnull(user.name),
                ifnull(user.name, column(3)),
                if_(user.name.eq(2), column(1), column(2)),
                nullif(column(1), column(2))
        )
                .from(user.as("u")).toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void testCaseWhen() {
        SqlInfo muser = select(
                case_().when(user.name.eq(column("3"))).then(user.name).when(user.name.eq("4")).then(column("5")).else_(column(6)).end().as("aa"),
                case_(user.name).when(column("a")).then(column("b")).when("a").then(4).else_("7").end().as("vv")
        )
                .from(user.as("u")).toSQL(toSQL);
        System.out.println(muser);
    }
}

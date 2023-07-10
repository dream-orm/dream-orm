package com.moxa.dream.flex.test;

import com.moxa.dream.flex.def.ColumnDef;
import com.moxa.dream.flex.def.ForUpdateDef;
import com.moxa.dream.flex.def.TableDef;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.FunctionDef.abs;
import static com.moxa.dream.flex.def.FunctionDef.select;

public class MyTest {
    @Test
    public void test() {
        ForUpdateDef forUpdateDef = select(abs(new ColumnDef("a")), new ColumnDef("b").as("b0"))
                .from(new TableDef("dual")).where((new ColumnDef("c").eq(1)).and(new ColumnDef("c").eq(1)).or(new ColumnDef("c").eq(1)))
                .groupBy(new ColumnDef("c"), new ColumnDef("d")).having(new ColumnDef("s").like("2"))
                .orderBy(new ColumnDef("a").desc())
                .limit(10, 11)
                .forUpdate();
        System.out.println(forUpdateDef);
    }

}

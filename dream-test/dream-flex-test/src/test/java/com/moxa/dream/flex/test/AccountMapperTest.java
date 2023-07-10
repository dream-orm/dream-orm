package com.moxa.dream.flex.test;

import com.moxa.dream.flex.def.ColumnDef;
import com.moxa.dream.flex.def.ForUpdateDef;
import com.moxa.dream.flex.def.TableDef;
import com.moxa.dream.flex.mapper.FlexMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.moxa.dream.flex.def.FunctionDef.abs;
import static com.moxa.dream.flex.def.FunctionDef.select;


@SpringBootTest
class AccountMapperTest {
    @Autowired
    private FlexMapper flexMapper;

    @Test
    void testCount() {
        ForUpdateDef forUpdateDef = select(abs(new ColumnDef("a")), new ColumnDef("b").as("b0"))
                .from(new TableDef("dual")).where((new ColumnDef("c").eq(1)).and(new ColumnDef("c").eq(1)).or(new ColumnDef("c").eq(1)))
                .groupBy(new ColumnDef("c"), new ColumnDef("d")).having(new ColumnDef("s").like("2"))
                .orderBy(new ColumnDef("a").desc());
    }

}

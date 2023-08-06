package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.def.Query;
import com.moxa.dream.flex.def.Update;
import com.moxa.dream.flex.invoker.FlexMarkInvoker;
import com.moxa.dream.flex.invoker.FlexTableInvoker;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.List;
import java.util.Set;

public class PrintSqlTest {
    private ToSQL toSQL;

    public PrintSqlTest() {
        this(new ToMYSQL());
    }

    public PrintSqlTest(ToSQL toSQL) {
        this.toSQL = toSQL;
    }

    public SqlInfo toSQL(Query query) {
        return toSQL(query.statement());
    }

    public SqlInfo toSQL(Update update) {
        return toSQL(update.getStatement());
    }

    protected SqlInfo toSQL(Statement statement) {
        Assist assist = getAssist();
        String sql;
        try {
            sql = toSQL.toStr(statement, assist, null);
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        FlexMarkInvoker flexMarkInvoker = (FlexMarkInvoker) assist.getInvoker(FlexMarkInvoker.FUNCTION, FlexMarkInvoker.DEFAULT_NAMESPACE);
        FlexTableInvoker flexTableInvoker = (FlexTableInvoker) assist.getInvoker(FlexTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        List<Object> paramList = flexMarkInvoker.getParamList();
        Set<String> tableSet = flexTableInvoker.getTableSet();
        return new SqlInfo(sql, paramList, tableSet);
    }

    private Assist getAssist() {
        InvokerFactory invokerFactory = new AntlrInvokerFactory();
        invokerFactory.addInvokers(defaultInvokers());
        return new Assist(invokerFactory, null);
    }

    private Invoker[] defaultInvokers() {
        return new Invoker[]{new FlexMarkInvoker(), new FlexTableInvoker()};
    }
}

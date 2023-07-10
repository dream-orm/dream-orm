package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.invoker.FlexValueInvoker;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.List;

public class ForUpdateDef {
    protected QueryStatement statement;

    public ForUpdateDef(QueryStatement statement) {
        this.statement = statement;
    }

    public SqlDef toSQL(ToSQL toSQL) {
        Assist assist = getAssist();
        String sql;
        try {
            sql = toSQL.toStr(statement, assist, null);
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        FlexValueInvoker flexValueInvoker = (FlexValueInvoker) assist.getInvoker(FlexValueInvoker.FUNCTION, FlexValueInvoker.DEFAULT_NAMESPACE);
        List<Object> paramList = flexValueInvoker.getParamList();
        return new SqlDef(sql, paramList);
    }

    private Assist getAssist() {
        InvokerFactory invokerFactory = new AntlrInvokerFactory();
        invokerFactory.addInvokers(new FlexValueInvoker());
        return new Assist(invokerFactory, null);
    }

    public QueryStatement getStatement() {
        return statement;
    }
}

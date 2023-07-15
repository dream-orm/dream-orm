package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.invoker.FlexMarkInvoker;
import com.moxa.dream.flex.invoker.FlexTableInvoker;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.List;
import java.util.Set;

public abstract class AbstractQuery implements Query {
    @Override
    public SqlInfo toSQL(ToSQL toSQL) {
        Assist assist = getAssist();
        String sql;
        try {
            sql = toSQL.toStr(getStatement(), assist, null);
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

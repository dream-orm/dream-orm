package com.dream.flex.dialect;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.AntlrInvokerFactory;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.invoker.FlexMarkInvoker;
import com.dream.flex.invoker.FlexTableInvoker;
import com.dream.util.exception.DreamRunTimeException;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class AbstractFlexDialect implements FlexDialect {
    private ToSQL toSQL;

    public AbstractFlexDialect(ToSQL toSQL) {
        this.toSQL = toSQL;
    }

    @Override
    public SqlInfo toSQL(Statement statement) {
        Assist assist = getAssist();
        String sql;
        try {
            sql = toSQL.toStr(statement, assist, invokerList());
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        FlexMarkInvoker flexMarkInvoker = (FlexMarkInvoker) assist.getInvoker(FlexMarkInvoker.FUNCTION, FlexMarkInvoker.DEFAULT_NAMESPACE);
        FlexTableInvoker flexTableInvoker = (FlexTableInvoker) assist.getInvoker(FlexTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        List<Object> paramList = flexMarkInvoker.getParamList();
        Set<String> tableSet = flexTableInvoker.getTableSet();
        return new SqlInfo(sql, paramList, tableSet);
    }

    protected Assist getAssist() {
        InvokerFactory invokerFactory = new AntlrInvokerFactory();
        invokerFactory.addInvokers(defaultInvokers());
        return new Assist(invokerFactory, new HashMap<>());
    }

    protected Invoker[] defaultInvokers() {
        return new Invoker[]{new FlexMarkInvoker(), new FlexTableInvoker()};
    }

    protected abstract List<Invoker> invokerList();
}

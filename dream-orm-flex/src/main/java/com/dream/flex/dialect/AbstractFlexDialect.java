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
import com.dream.system.config.MethodInfo;
import com.dream.util.exception.DreamRunTimeException;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class AbstractFlexDialect implements FlexDialect {
    protected ToSQL toSQL;

    public AbstractFlexDialect(ToSQL toSQL) {
        this.toSQL = toSQL;
    }

    @Override
    public SqlInfo toSQL(Statement statement, MethodInfo methodInfo) {
        Assist assist = getAssist(methodInfo);
        String sql;
        try {
            sql = toSQL.toStr(statement, assist, invokerList());
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        FlexMarkInvoker flexMarkInvoker = (FlexMarkInvoker) assist.getInvoker(FlexMarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        FlexTableInvoker flexTableInvoker = (FlexTableInvoker) assist.getInvoker(FlexTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        List<Object> paramList = flexMarkInvoker.getParamList();
        Set<String> tableSet = flexTableInvoker.getTableSet();
        return new SqlInfo(sql, paramList, tableSet);
    }

    protected Assist getAssist(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = new AntlrInvokerFactory();
        invokerFactory.addInvokers(defaultInvokers());
        Assist assist = new Assist(invokerFactory, new HashMap<>());
        if (methodInfo != null) {
            assist.setCustom(MethodInfo.class, methodInfo);
        }
        return assist;
    }

    protected Invoker[] defaultInvokers() {
        return new Invoker[]{new FlexMarkInvoker(), new FlexTableInvoker()};
    }

    protected abstract List<Invoker> invokerList();
}

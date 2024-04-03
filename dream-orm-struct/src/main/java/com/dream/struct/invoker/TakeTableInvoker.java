package com.dream.struct.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.util.common.LowHashSet;

import java.util.List;
import java.util.Set;

public class TakeTableInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_instruct_take_table";
    private final Set<String> tableSet = new LowHashSet();

    @Override
    public Invoker newInstance() {
        return new TakeTableInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        TakeTableInvokerStatement takeTableInvokerStatement = (TakeTableInvokerStatement) invokerStatement;
        String table = takeTableInvokerStatement.getTable();
        tableSet.add(table);
        return toSQL.toStr(new SymbolStatement.SingleMarkStatement(table), assist, invokerList);
    }

    @Override
    public Handler[] handler() {
        return new Handler[0];
    }

    public Set<String> getTableSet() {
        return tableSet;
    }
}

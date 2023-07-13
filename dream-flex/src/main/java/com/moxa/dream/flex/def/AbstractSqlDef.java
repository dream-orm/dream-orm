package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.ResultInfo;
import com.moxa.dream.flex.invoker.FlexMarkInvoker;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.List;

public abstract class AbstractSqlDef implements SqlDef {
    @Override
    public ResultInfo toSQL(ToSQL toSQL) {
        Assist assist = getAssist();
        String sql;
        try {
            sql = toSQL.toStr(getStatement(), assist, null);
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        FlexMarkInvoker flexMarkInvoker = (FlexMarkInvoker) assist.getInvoker(FlexMarkInvoker.FUNCTION, FlexMarkInvoker.DEFAULT_NAMESPACE);
        List<Object> paramList = flexMarkInvoker.getParamList();
        return new ResultInfo(sql, paramList);
    }

    private Assist getAssist() {
        InvokerFactory invokerFactory = new AntlrInvokerFactory();
        invokerFactory.addInvokers(new FlexMarkInvoker());
        return new Assist(invokerFactory, null);
    }
}

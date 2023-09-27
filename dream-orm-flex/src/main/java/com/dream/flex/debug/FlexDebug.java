package com.dream.flex.debug;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.AntlrInvokerFactory;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToMYSQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.invoker.FlexMarkInvoker;
import com.dream.flex.invoker.FlexTableInvoker;
import com.dream.util.exception.DreamRunTimeException;

import java.util.List;
import java.util.Set;

public class FlexDebug {
    private ToSQL toSQL;

    public FlexDebug() {
        this(new ToMYSQL());
    }

    public FlexDebug(ToSQL toSQL) {
        this.toSQL = toSQL;
    }

    public SqlInfo toSQL(QueryDef queryDef) {
        return toSQL(queryDef.statement());
    }

    public SqlInfo toSQL(InsertDef insertDef) {
        return toSQL(insertDef.statement());
    }

    public SqlInfo toSQL(DeleteDef deleteDef) {
        return toSQL(deleteDef.statement());
    }

    public SqlInfo toSQL(UpdateDef updateDef) {
        return toSQL(updateDef.statement());
    }

    public SqlInfo toSQL(Statement statement) {
        Assist assist = getAssist();
        String sql;
        try {
            sql = toSQL.toStr(statement.clone(), assist, null);
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
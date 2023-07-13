package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.ResultInfo;
import com.moxa.dream.flex.invoker.FlexMarkInvoker;
import com.moxa.dream.flex.invoker.FlexTableInvoker;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSqlDef implements SqlDef {
    @Override
    public ResultInfo toSQL(ToSQL toSQL, Map<Class, Object> customMap, Invoker... invokers) {
        Assist assist = getAssist(customMap, invokers);
        String sql;
        try {
            sql = toSQL.toStr(getStatement(invokers), assist, null);
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        FlexMarkInvoker flexMarkInvoker = (FlexMarkInvoker) assist.getInvoker(FlexMarkInvoker.FUNCTION, FlexMarkInvoker.DEFAULT_NAMESPACE);
        FlexTableInvoker flexTableInvoker = (FlexTableInvoker) assist.getInvoker(FlexTableInvoker.FUNCTION, FlexTableInvoker.DEFAULT_NAMESPACE);
        List<Object> paramList = flexMarkInvoker.getParamList();
        Set<String> tableSet = flexTableInvoker.getTableSet();
        return new ResultInfo(sql, paramList, tableSet, assist);
    }

    private Assist getAssist(Map<Class, Object> customMap, Invoker[] invokers) {
        InvokerFactory invokerFactory = new AntlrInvokerFactory();
        invokerFactory.addInvokers(defaultInvokers());
        if (invokers != null && invokers.length > 0) {
            invokerFactory.addInvokers(invokers);
        }
        return new Assist(invokerFactory, customMap);
    }

    private Invoker[] defaultInvokers() {
        return new Invoker[]{new FlexMarkInvoker(), new FlexTableInvoker()};
    }

    private Statement getStatement(Invoker[] invokers) {
        Statement statement = getStatement();
        if (invokers != null && invokers.length > 0) {
            for (int i = 0; i < invokers.length; i++) {
                Invoker invoker = invokers[i];
                InvokerStatement invokerStatement = new InvokerStatement();
                invokerStatement.setFunction(invoker.namespace());
                invokerStatement.setFunction(invoker.function());
                ListColumnStatement listColumnStatement = new ListColumnStatement(",");
                listColumnStatement.add(statement);
                invokerStatement.setParamStatement(listColumnStatement);
                statement = invokerStatement;
            }
            PackageStatement packageStatement = new PackageStatement();
            packageStatement.setStatement(statement);
            statement = packageStatement;
        }
        return statement;
    }
}

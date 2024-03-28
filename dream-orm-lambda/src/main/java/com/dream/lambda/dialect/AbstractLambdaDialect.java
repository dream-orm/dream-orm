package com.dream.lambda.dialect;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.AntlrInvokerFactory;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.lambda.config.SqlInfo;
import com.dream.lambda.invoker.LambdaMarkInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.util.exception.DreamRunTimeException;

import java.util.HashMap;
import java.util.List;

public abstract class AbstractLambdaDialect implements LambdaDialect {
    protected ToSQL toSQL;

    public AbstractLambdaDialect(ToSQL toSQL) {
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
        LambdaMarkInvoker lambdaMarkInvoker = (LambdaMarkInvoker) assist.getInvoker(LambdaMarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
//        FlexTableInvoker flexTableInvoker = (FlexTableInvoker) assist.getInvoker(FlexTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        List<Object> paramList = lambdaMarkInvoker.getParamList();
//        Set<String> tableSet = flexTableInvoker.getTableSet();
        return new SqlInfo(sql, paramList, null);
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
        return new Invoker[]{new LambdaMarkInvoker()};
    }

    protected abstract List<Invoker> invokerList();
}

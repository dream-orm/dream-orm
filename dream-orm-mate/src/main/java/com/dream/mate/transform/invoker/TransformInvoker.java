package com.dream.mate.transform.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.tenant.inject.TenantInject;
import com.dream.mate.transform.handler.LetterHandler;
import com.dream.mate.transform.inject.TransformHandler;
import com.dream.mate.transform.inject.TransformInject;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.factory.InjectFactory;
import com.dream.util.common.LowHashSet;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.resource.ResourceUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class TransformInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_block";
    private TransformHandler transformHandler;
    @Override
    public void init(Assist assist) {
        Configuration configuration = assist.getCustom(Configuration.class);
        InjectFactory injectFactory = configuration.getInjectFactory();
        TransformInject transformInject = injectFactory.getInject(TransformInject.class);
        this.transformHandler = transformInject.getTransformHandler();
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement statement = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList()[0];
        String sql = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        invokerStatement.replaceWith(statement);
        return sql;
    }

    @Override
    protected Handler[] handler() {
        return new Handler[]{new LetterHandler(this)};
    }

    public boolean intercept(String column,List<Invoker> invokerList) {
        return transformHandler.intercept(column,invokerList);
    }

    @Override
    public Invoker newInstance() {
        return this;
    }

    @Override
    public String function() {
        return FUNCTION;
    }

}


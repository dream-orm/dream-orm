package com.dream.regular.factory;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.AntlrInvokerFactory;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.regular.invoker.TakeMarkInvoker;
import com.dream.regular.invoker.TakeTableInvoker;
import com.dream.system.config.Command;
import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.DefaultTypeHandlerFactory;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.util.exception.DreamRunTimeException;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DefaultCommandDialectFactory implements CommandDialectFactory {
    private TypeHandlerFactory typeHandlerFactory;
    private ToSQL toSQL;

    public DefaultCommandDialectFactory(ToSQL toSQL) {
        this(new DefaultTypeHandlerFactory(), toSQL);
    }

    public DefaultCommandDialectFactory(TypeHandlerFactory typeHandlerFactory, ToSQL toSQL) {
        this.typeHandlerFactory = typeHandlerFactory;
        this.toSQL = toSQL;
    }

    @Override
    public MappedStatement compile(Command command, Statement statement, MethodInfo methodInfo) {
        InvokerFactory invokerFactory = new AntlrInvokerFactory();
        invokerFactory.addInvokers(defaultInvokers());
        Assist assist = new Assist(invokerFactory, new HashMap<>());
        String sql;
        try {
            sql = toSQL.toStr(statement, assist, null);
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        TakeMarkInvoker takeMarkInvoker = (TakeMarkInvoker) assist.getInvoker(TakeMarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        TakeTableInvoker takeTableInvoker = (TakeTableInvoker) assist.getInvoker(TakeTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        List<Object> paramList = takeMarkInvoker.getParamList();
        List<MappedParam> mappedParamList = null;
        if (paramList != null && !paramList.isEmpty()) {
            mappedParamList = new ArrayList<>(paramList.size());
            for (Object param : paramList) {
                MappedParam mappedParam = new MappedParam();
                try {
                    mappedParam.setTypeHandler(typeHandlerFactory.getTypeHandler(param != null ? param.getClass() : Object.class, Types.NULL));
                } catch (TypeHandlerNotFoundException e) {
                    throw new DreamRunTimeException(e);
                }
                mappedParam.setParamValue(param);
                mappedParamList.add(mappedParam);
            }
        }
        Set<String> tableSet = takeTableInvoker.getTableSet();
        return new MappedStatement
                .Builder()
                .methodInfo(methodInfo)
                .command(command)
                .sql(sql)
                .tableSet(tableSet)
                .mappedParamList(mappedParamList)
                .build();
    }

    protected Invoker[] defaultInvokers() {
        return new Invoker[]{new TakeMarkInvoker(), new TakeTableInvoker()};
    }
}
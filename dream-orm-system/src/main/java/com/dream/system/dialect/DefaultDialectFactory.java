package com.dream.system.dialect;

import com.dream.antlr.config.Assist;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.antlr.invoker.ScanInvoker;
import com.dream.system.config.*;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultDialectFactory extends AbstractDialectFactory {
    protected ToSQL toSQL;

    @Override
    public MappedStatement compileAntlr(MethodInfo methodInfo, Object arg) throws Exception {
        Configuration configuration = methodInfo.getConfiguration();
        PackageStatement statement = methodInfo.getStatement();
        ScanInvoker.ScanInfo scanInfo = statement.getValue(ScanInvoker.ScanInfo.class);
        String sql;
        List<MarkInvoker.ParamInfo> paramInfoList;
        if (scanInfo == null) {
            Assist assist = getAssist(methodInfo, arg);
            assist.setCustom(PackageStatement.class, statement);
            sql = toSQL.toStr(methodInfo.getStatement(), assist, null);
            scanInfo = statement.getValue(ScanInvoker.ScanInfo.class);
            paramInfoList = getParamInfoList(assist);
        } else {
            sql = scanInfo.getSql();
            if (sql == null) {
                Assist assist = getAssist(methodInfo, arg);
                sql = toSQL.toStr(methodInfo.getStatement(), assist, null);
                paramInfoList = getParamInfoList(assist);
            } else {
                paramInfoList = scanInfo.getParamInfoList();
                if (!ObjectUtil.isNull(paramInfoList)) {
                    ObjectWrapper paramWrapper = ObjectWrapper.wrapper(arg);
                    for (MarkInvoker.ParamInfo paramInfo : paramInfoList) {
                        paramInfo.setParamValue(paramWrapper.get(paramInfo.getParamName()));
                    }
                }
            }
        }
        List<MappedParam> mappedParamList = new ArrayList<>();
        if (!ObjectUtil.isNull(paramInfoList)) {
            TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
            for (MarkInvoker.ParamInfo paramInfo : paramInfoList) {
                String paramName = paramInfo.getParamName();
                Object value = paramInfo.getParamValue();
                TypeHandler typeHandler = typeHandlerFactory.getTypeHandler(value == null ? Object.class : value.getClass(), Types.NULL);
                mappedParamList.add(new MappedParam().setParamName(paramName).setParamValue(paramInfo.getParamValue()).setJdbcType(Types.NULL).setTypeHandler(typeHandler));
            }
        }
        return new MappedStatement
                .Builder()
                .methodInfo(methodInfo)
                .command(Command.valueOf(scanInfo.getCommand()))
                .sql(sql)
                .tableSet(scanInfo.getTableScanInfoMap().keySet())
                .mappedParamList(mappedParamList)
                .arg(arg)
                .build();
    }

    protected List<MarkInvoker.ParamInfo> getParamInfoList(Assist assist) {
        MarkInvoker invoker = (MarkInvoker) assist.getInvoker(MarkInvoker.FUNCTION);
        return invoker.getParamInfoList();
    }

    protected Assist getAssist(MethodInfo methodInfo, Object arg) {
        Map<Class, Object> customMap = new HashMap<>(4);
        Configuration configuration = methodInfo.getConfiguration();
        customMap.put(MethodInfo.class, methodInfo);
        customMap.put(Configuration.class, configuration);
        if (arg == null) {
            arg = new HashMap<>(4);
        }
        customMap.put(ObjectWrapper.class, ObjectWrapper.wrapper(arg));
        return new Assist(configuration.getInvokerFactory(), customMap);
    }

    public void setToSQL(ToSQL toSQL) {
        this.toSQL = toSQL;
    }
}

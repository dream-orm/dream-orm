package com.moxa.dream.system.dialect;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.system.antlr.invoker.ForEachInvoker;
import com.moxa.dream.system.antlr.invoker.MarkInvoker;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.MappedParam;
import com.moxa.dream.system.config.MappedSql;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.typehandler.TypeHandlerNotFoundException;
import com.moxa.dream.system.typehandler.handler.ObjectTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Array;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

public class UnAntlrDialectFactory implements DialectFactory {

    @Override
    public MappedStatement compile(MethodInfo methodInfo, Object arg) throws Exception {
        Command command = Command.NONE;
        Set<String> tableList = new HashSet<>();
        List<Object> paramList = new ArrayList<>();
        String sql = methodInfo.getSql();
        ExprReader exprReader = new ExprReader(sql);
        int startIndex = 0;
        StringBuilder sqlBuilder = new StringBuilder();
        String table;
        while (true) {
            ExprInfo exprInfo;
            switch (((exprInfo = exprReader.push()).getExprType())) {
                case SELECT:
                    if (command == Command.NONE) {
                        command = Command.QUERY;
                    }
                    break;
                case UPDATE:
                    if (command == Command.NONE) {
                        command = Command.UPDATE;
                    }
                    exprInfo = exprReader.push();
                    table = getTable(exprInfo);
                    if (table != null) {
                        tableList.add(table);
                    }
                    break;
                case INSERT:
                    if (command == Command.NONE) {
                        command = Command.INSERT;
                    }
                    break;
                case DELETE:
                    if (command == Command.NONE) {
                        command = Command.DELETE;
                    }
                    break;
                case FROM:
                case JOIN:
                case INTO:
                    exprInfo = exprReader.push();
                    table = getTable(exprInfo);
                    if (table != null) {
                        tableList.add(table);
                    }
                    break;
                case INVOKER:
                    exprInfo = exprReader.push();
                    String info = exprInfo.getInfo();
                    switch (info) {
                        case MarkInvoker.FUNCTION:
                        case ForEachInvoker.FUNCTION:
                            ExprInfo tempExprInfo = exprReader.push();
                            if (tempExprInfo.getExprType() == ExprType.LBRACE) {
                                sqlBuilder.append(sql, startIndex, exprInfo.getStart() - 1);
                                StringBuilder paramBuilder = new StringBuilder();
                                while ((tempExprInfo = exprReader.push()).getExprType() != ExprType.RBRACE) {
                                    paramBuilder.append(tempExprInfo.getInfo());
                                }
                                Object value = ObjectWrapper.wrapper(arg, false).get(paramBuilder.toString());
                                startIndex = tempExprInfo.getEnd();
                                if (info.equals(MarkInvoker.FUNCTION)) {
                                    paramList.add(value);
                                    sqlBuilder.append("?");
                                } else if (info.equals(ForEachInvoker.FUNCTION)) {
                                    if (value == null) {
                                        throw new DreamRunTimeException("函数" + ForEachInvoker.FUNCTION + "参数值不能为空");
                                    }
                                    if (value instanceof Collection) {
                                        if (((Collection<?>) value).isEmpty()) {
                                            throw new DreamRunTimeException("函数" + ForEachInvoker.FUNCTION + "参数值不能为空");
                                        }
                                        for (int i = 0; i < ((Collection<?>) value).size() - 1; i++) {
                                            sqlBuilder.append("?,");
                                        }
                                        sqlBuilder.append("?");
                                        paramList.addAll((Collection<?>) value);
                                    } else if (value.getClass().isArray()) {
                                        int length = Array.getLength(value);
                                        if (length == 0) {
                                            throw new DreamRunTimeException("函数" + ForEachInvoker.FUNCTION + "参数值不能为空");
                                        }
                                        int i = 0;
                                        for (; i < length - 1; i++) {
                                            sqlBuilder.append("?,");
                                            paramList.add(Array.get(value, i));
                                        }
                                        sqlBuilder.append("?");
                                        paramList.add(Array.get(value, i));
                                    }
                                }
                                break;
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            if (exprInfo.getExprType() == ExprType.ACC) {
                break;
            }
        }
        if (command == Command.NONE) {
            command = Command.UPDATE;
        }
        sqlBuilder.append(sql, startIndex, sql.length());
        CacheKey methodKey = methodInfo.getMethodKey();
        if (!paramList.isEmpty()) {
            methodKey.update(paramList.toArray());
        }
        return new MappedStatement.Builder().methodInfo(methodInfo).uniqueKey(methodKey).arg(arg).mappedSql(new MappedSql(command.name(), sqlBuilder.toString(), tableList)).mappedParamList(paramList.stream().map(par -> {
            TypeHandler typeHandler;
            if (par == null) {
                typeHandler = new ObjectTypeHandler();
            } else {
                try {
                    typeHandler = methodInfo.getConfiguration().getTypeHandlerFactory().getTypeHandler(par.getClass(), Types.NULL);
                } catch (TypeHandlerNotFoundException e) {
                    throw new DreamRunTimeException(methodInfo.getId() + "获取类型失败," + e.getMessage(), e);
                }
            }
            return new MappedParam().setJdbcType(Types.NULL).setParamValue(par).setTypeHandler(typeHandler);
        }).collect(Collectors.toList())).build();
    }

    private String getTable(ExprInfo exprInfo) {
        switch (exprInfo.getExprType()) {
            case LETTER:
            case SINGLE_MARK:
            case STR:
            case JAVA_STR:
                return exprInfo.getInfo();
            default:
                return null;
        }
    }
}

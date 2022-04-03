package com.moxa.dream.module.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.UpdateExpr;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.module.annotation.Table;
import com.moxa.dream.module.annotation.View;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.module.table.ColumnInfo;
import com.moxa.dream.module.table.TableInfo;
import com.moxa.dream.module.table.factory.TableFactory;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateInvoker extends AbstractInvoker {
    @Override
    protected String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        ObjectWrapper paramWrapper = assist.getCustom(ObjectWrapper.class);
        Object target = paramWrapper.getObject();
        Class<?> tableType = target.getClass();
        String table;
        Table tableAnnotation = tableType.getDeclaredAnnotation(Table.class);
        if (tableAnnotation == null) {
            View viewAnnotation = tableType.getDeclaredAnnotation(View.class);
            ObjectUtil.requireNonNull(viewAnnotation, "please check '" + tableType.getName() + "'");
            table = viewAnnotation.value();
        } else {
            table = tableAnnotation.value();
        }
        TableFactory tableFactory = methodInfo.getConfiguration().getTableFactory();
        TableInfo tableInfo = tableFactory.getTableInfo(table);
        ObjectUtil.requireNonNull(tableInfo, "please check '" + table + "'");
        List<Field> fieldList = ReflectUtil.findField(tableType);
        List<String> updateList = new ArrayList<>();
        if (ObjectUtil.isNull(fieldList)) {
            List<ColumnInfo> columnInfoList = fieldList.stream().map(field -> tableInfo.getColumnInfo(field.getName()))
                    .filter(columnInfo -> columnInfo != null)
                    .collect(Collectors.toList());
            if (!ObjectUtil.isNull(columnInfoList)) {
                for (ColumnInfo columnInfo : columnInfoList) {
                    String column = columnInfo.getColumn();
                    String name = columnInfo.getName();
                    updateList.add(column + "=" + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", name));
                }
            }
        }
        String updateSQL = "update " + table + "set " + String.join(",", updateList) + " where 1<>1";
        Statement statement = new UpdateExpr(new ExprReader(updateSQL)).expr();
        invokerStatement.setStatement(statement);
        return toSQL.toStr(statement, assist, invokerList);
    }
}

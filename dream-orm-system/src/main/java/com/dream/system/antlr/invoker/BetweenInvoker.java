package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.util.common.ObjectWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BetweenInvoker extends AbstractInvoker {
    public static final String FUNCTION = "between";

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        int len = columnList.length;
        if (len != 2) {
            throw new AntlrException("@" + FUNCTION + "参数个数不合法");
        }
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setOper(new OperStatement.ANDStatement());
        String list = toSQL.toStr(columnList[1], assist, invokerList);
        ObjectWrapper paramWrapper = assist.getCustom(ObjectWrapper.class);
        Object arrayList = paramWrapper.get(list);
        if (arrayList != null) {
            boolean isArray = false;
            if (arrayList instanceof Collection || (isArray = arrayList.getClass().isArray())) {
                Collection collection;
                if (isArray) {
                    collection = Arrays.asList((Object[]) arrayList);
                } else {
                    collection = (Collection) arrayList;
                }
                if (!collection.isEmpty()) {
                    if (collection.size() != 2) {
                        throw new AntlrException("集合数量必须是2");
                    }
                    conditionStatement.setLeft(AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(list + ".0")));
                    conditionStatement.setRight(AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(list + ".1")));
                }
            } else {
                throw new AntlrException("类'" + arrayList.getClass().getName() + "'不是集合或数组类型");
            }
        }
        return toSQL.toStr(AntlrUtil.conditionStatement(columnList[0], new OperStatement.BETWEENStatement(), conditionStatement), assist, invokerList);
    }

    @Override
    public String function() {
        return FUNCTION;
    }
}

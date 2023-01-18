package com.moxa.dream.system.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.common.ObjectWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ForEachInvoker extends AbstractInvoker {
    public static final String FUNCTION = "foreach";
    private final String cut = ",";
    private final String index = "index";
    private final String item = "item";

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        int len = columnList.length;
        if (len > 2 || len < 1) {
            throw new AntlrException("@foreach参数个数不合法");
        }
        String list = toSQL.toStr(columnList[0], assist, invokerList);
        ObjectWrapper paramWrapper = assist.getCustom(ObjectWrapper.class);
        Object arrayList = paramWrapper.get(list);
        if (arrayList == null) {
            return null;
        }
        boolean isArray = false;
        if (arrayList instanceof Collection || (isArray = arrayList.getClass().isArray())) {
            Collection collection;
            if (isArray) {
                collection = Arrays.asList((Object[]) arrayList);
            } else {
                collection = (Collection) arrayList;
            }
            ListColumnStatement listColumnStatement = new ListColumnStatement(cut);
            if (len == 2) {
                int index = 0;
                Map<String, Object> paramMap = (Map<String, Object>) paramWrapper.getObject();
                for (Object item : collection) {
                    paramMap.put(this.index, index++);
                    paramMap.put(this.item, item);
                    String letterItem = toSQL.toStr(columnList[1], assist, invokerList);
                    listColumnStatement.add(new SymbolStatement.LetterStatement(letterItem));
                }
                paramMap.remove(this.index);
                paramMap.remove(this.item);
            } else {
                MarkInvoker sqlInvoker = (MarkInvoker) assist.getInvoker(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
                List<MarkInvoker.ParamInfo> paramInfoList = sqlInvoker.getParamInfoList();
                int index = 0;
                for (Object item : collection) {
                    paramInfoList.add(new MarkInvoker.ParamInfo(list + "[" + index++ + "]", item));
                    listColumnStatement.add(new SymbolStatement.MarkStatement());
                }
            }
            listColumnStatement.setParentStatement(invokerStatement.getParentStatement());
            return toSQL.toStr(listColumnStatement, assist, invokerList);
        } else {
            throw new AntlrException("类'" + arrayList.getClass().getName() + "'不是集合或数组类型");
        }
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

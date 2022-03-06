package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.*;

public class ForEachInvoker extends AbstractInvoker {
    private String cut = ",";
    private String index = "index";
    private String item = "item";
    private ObjectWrapper paramWrapper;

    @Override
    public void init(ToAssist assist) {
        paramWrapper = assist.getCustom(ObjectWrapper.class);
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        int len = columnList.length;
        if (len > 2 || len < 1)
            throw new InvokerException("The number of parameters cannot meet @" + AntlrInvokerFactory.FOREACH + ":" + AntlrInvokerFactory.NAMESPACE + "(list,value?)");
        String list = toSQL.toStr(columnList[0], assist, invokerList);
        Object arrayList = paramWrapper.get(list);
        ObjectUtil.requireNonNull(arrayList, "Property 'list' is required");
        boolean isArray = false;
        if (arrayList instanceof Collection || (isArray = arrayList.getClass().isArray())) {
            Collection collection;
            if (isArray)
                collection = Arrays.asList((Object[]) arrayList);
            else
                collection = (Collection) arrayList;
            ListColumnStatement listColumnStatement = new ListColumnStatement(cut);
            if (len == 2) {
                int index = 0;
                Map<String, Object> itemMap = new HashMap<>();
                ObjectWrapper paramWrapper = assist.getCustom(ObjectWrapper.class);
                paramWrapper.setTemp(itemMap);
                for (Object item : collection) {
                    itemMap.put(this.index, index++);
                    itemMap.put(this.item, item);
                    String letterItem = toSQL.toStr(columnList[1], assist, invokerList);
                    listColumnStatement.add(new SymbolStatement.LetterStatement(letterItem));
                }
                paramWrapper.setTemp(null);
            } else {
                $Invoker sqlInvoker = ($Invoker) assist.getInvoker(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$);
                List<$Invoker.ParamInfo> paramInfoList = sqlInvoker.getParamInfoList();
                int index = 0;
                for (Object item : collection) {
                    paramInfoList.add(new $Invoker.ParamInfo(list + "[" + index++ + "]", item));
                    listColumnStatement.add(new SymbolStatement.MarkStatement());
                }
            }
            listColumnStatement.setParentStatement(invokerStatement.getParentStatement());
            return toSQL.toStr(listColumnStatement, assist, invokerList);
        } else throw new InvokerException("Class name '" + arrayList.getClass().getName() + "' not a collection type");
    }
}

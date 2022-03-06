package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
//@foreach(,@$(item))
//@foreach(,@$(item.name))
//@foreach(,`(@$(item.name),@$(item.name))`)
public class ForEachInvoker extends AbstractInvoker {
    private String join = ",";
    private String mark = "?";
    private ObjectWrapper paramWrapper;

    @Override
    public void init(ToAssist assist) {
        paramWrapper = assist.getCustom(ObjectWrapper.class);
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = invokerStatement.getListColumnStatement().getColumnList();
        int len = columnList.length;
        if (len > 2 || len < 1)
            throw new InvokerException("参数个数错误,不满足@" + AntlrInvokerFactory.FOREACH + ":" + AntlrInvokerFactory.NAMESPACE + "(list,value?)");
        String list = toSQL.toStr(columnList[0], assist, invokerList);
        Object arrayList = paramWrapper.get(list);
        if (arrayList == null)
            throw new InvokerException("参数'" + list + "'值为空");
        StringBuilder builder = new StringBuilder();
        boolean isArray = false;
        if (arrayList instanceof Collection || (isArray = arrayList.getClass().isArray())) {
            Collection collection;
            if (isArray)
                collection = Arrays.asList((Object[]) arrayList);
            else
                collection = (Collection) arrayList;
            $Invoker sqlInvoker = ($Invoker) assist.getInvoker(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$);
            List<$Invoker.ParamInfo> paramInfoList = sqlInvoker.getParamInfoList();
            if (len == 2) {
                String paramName = toSQL.toStr(columnList[1], assist, invokerList);
                Iterator<?> itemIterator = collection.iterator();
                while (itemIterator.hasNext()) {
                    Object value = ObjectWrapper.wrapper(itemIterator.next()).get(paramName);
                    paramInfoList.add(new $Invoker.ParamInfo(paramName, value));
                    builder.append(mark).append(join);
                }
            } else {
                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    paramInfoList.add(new $Invoker.ParamInfo(null, iterator.next()));
                    builder.append(mark).append(join);
                }
            }
        } else throw new InvokerException("'" + arrayList.getClass().getName() + "'非集合类型");
        int length = builder.length();
        if (length > 0)
            builder.delete(length - join.length(), length);
        return builder.toString();
    }
}

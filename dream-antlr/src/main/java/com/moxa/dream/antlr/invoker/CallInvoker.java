package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.callback.Callback;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.List;

public class CallInvoker extends AbstractInvoker {
    private Callback callback;
    private ObjectWrapper paramWrapper;

    @Override
    public void init(ToAssist assist) {
        this.callback = assist.getCustom(Callback.class);
        this.paramWrapper = assist.getCustom(ObjectWrapper.class);
        ObjectUtil.requireNonNull(callback, "Property 'callback' is required");
        this.callback.init(assist);
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        Statement statement = columnList[0];
        if (statement instanceof SymbolStatement.LetterStatement) {
            SymbolStatement.LetterStatement callFunctionStatement = (SymbolStatement.LetterStatement) statement;
            Object[] args = new Object[columnList.length - 1];
            String[] params = new String[columnList.length - 1];
            for (int i = 1; i < columnList.length; i++) {
                Statement column = columnList[i];
                if (column instanceof SymbolStatement.LetterStatement) {
                    String symbol = ((SymbolStatement.LetterStatement) column).getSymbol();
                    params[i - 1] = symbol;
                    args[i - 1] = paramWrapper.get(symbol);
                } else if (column instanceof SymbolStatement.ConstantStatement) {
                    params[i - 1] = null;
                    args[i - 1] = ((SymbolStatement.ConstantStatement<?>) column).getValue();
                } else throw new InvokerException("The parameter type is incorrect");
            }
            return toSQL.toStr(callback.call(callFunctionStatement.getPrefix(), callFunctionStatement.getSuffix(), params, args), assist, invokerList);
        } else
            throw new InvokerException("The parameter type is incorrect");
    }
}

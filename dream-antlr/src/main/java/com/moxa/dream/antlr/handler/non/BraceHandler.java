package com.moxa.dream.antlr.handler.non;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.ExprUtil;

import java.util.List;

//之所以要解析括号，比如sql:select 1 from dual where (a=null)。空去除后sql为：select 1 from dual where ()。明显报错
public class BraceHandler extends AbstractHandler {

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        //获取括号抽象树
        BraceStatement braceStatement = (BraceStatement) statement;
        Statement bstatement = braceStatement.getStatement();
        //正常翻译
        String val = toSQL.toStr(bstatement, assist, invokerList);
        //如果为空，则返回空，否则返回括号
        if (ExprUtil.isEmpty(val))
            return null;
        return new SymbolStatement.LetterStatement("(" + val + ")");
    }

    @Override
    protected boolean interest(Statement statement, ToAssist assist) {
        return statement instanceof BraceStatement;
    }
}

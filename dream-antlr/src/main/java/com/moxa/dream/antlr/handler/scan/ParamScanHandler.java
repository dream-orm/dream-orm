package com.moxa.dream.antlr.handler.scan;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.InvokerUtil;

import java.util.List;

public class ParamScanHandler extends AbstractHandler {

    private final ConditionHandler conditionHandler;

    public ParamScanHandler(ScanInvoker.ScanInfo scanInfo) {
        conditionHandler = new ConditionHandler(scanInfo);
    }

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        return statement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{conditionHandler};
    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof ConditionStatement;
    }


    class ConditionHandler extends AbstractHandler {
        private final ScanInvoker.ScanInfo scanInfo;

        public ConditionHandler(ScanInvoker.ScanInfo scanInfo) {
            this.scanInfo = scanInfo;
        }

        public void scanStatement(InvokerStatement invokerStatement) {
            Statement paramStatement = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList()[0];
            if (paramStatement instanceof SymbolStatement.LetterStatement) {
                SymbolStatement.LetterStatement letterStatement = (SymbolStatement.LetterStatement) paramStatement;
                String field = letterStatement.getSymbol();
                Statement parentStatement = invokerStatement.getParentStatement();
                if (parentStatement instanceof ConditionStatement) {
                    ConditionStatement conditionStatement = (ConditionStatement) parentStatement;
                    OperStatement oper = conditionStatement.getOper();
                    if (oper instanceof OperStatement.ANDStatement) {
                        parentStatement = parentStatement.getParentStatement();
                        if (paramStatement instanceof ConditionStatement) {
                            conditionStatement = (ConditionStatement) parentStatement;
                        }
                        Statement left = conditionStatement.getLeft();
                        if (left instanceof SymbolStatement.LetterStatement) {
                            SymbolStatement.LetterStatement letter = (SymbolStatement.LetterStatement) left;
                            String database = null;
                            String table = null;
                            String column = letter.getSuffix();
                            String prefix = letter.getPrefix();
                            if (prefix != null) {
                                String[] s = prefix.split("\\.");
                                switch (s.length) {
                                    case 1:
                                        table = s[0];
                                        break;
                                    case 2:
                                        database = s[0];
                                        table = s[1];
                                        break;
                                    default:
                                        break;
                                }
                            }
                            scanInfo.add(new ScanInvoker.ParamScanInfo(database, table, column, field));
                        }
                    }
                }
            }

        }

        @Override
        protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
            scanStatement((InvokerStatement) statement);
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, ToAssist sqlAssist) {
            return InvokerUtil.is$(statement);
        }
    }
}

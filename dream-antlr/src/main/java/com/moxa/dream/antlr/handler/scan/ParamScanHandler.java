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
    private InsertHandler insertSqlHandler;
    private NotInsertHandler notInsertSqlHandler;

    public ParamScanHandler(ScanInvoker.ScanInfo scanInfo) {
        insertSqlHandler = new InsertHandler(scanInfo);
        notInsertSqlHandler = new NotInsertHandler(scanInfo);
    }

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        return statement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{insertSqlHandler, notInsertSqlHandler};
    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof QueryStatement
                || statement instanceof InsertStatement
                || statement instanceof UpdateStatement
                || statement instanceof DeleteStatement;
    }

    static class InsertHandler extends AbstractHandler {
        private ScanInvoker.ScanInfo scanInfo;

        public InsertHandler(ScanInvoker.ScanInfo scanInfo) {
            this.scanInfo = scanInfo;
        }

        public void scanStatement(InsertStatement insertStatement) {
            Statement params = insertStatement.getParams();
            Statement values = insertStatement.getValues();
            if (values instanceof InsertStatement.ValuesStatement) {
                Statement valuesStatement = ((InsertStatement.ValuesStatement) values).getStatement();
                if (params instanceof BraceStatement && valuesStatement instanceof BraceStatement) {
                    BraceStatement paramBraceStatement = (BraceStatement) params;
                    BraceStatement valueBraceStatement = (BraceStatement) valuesStatement;
                    if (paramBraceStatement.getStatement() instanceof ListColumnStatement && valueBraceStatement.getStatement() instanceof ListColumnStatement) {
                        ListColumnStatement paramListColumnStatement = (ListColumnStatement) paramBraceStatement.getStatement();
                        ListColumnStatement valuesListColumnStatement = (ListColumnStatement) valueBraceStatement.getStatement();
                        Statement[] paramsList = paramListColumnStatement.getColumnList();
                        Statement[] valuesList = valuesListColumnStatement.getColumnList();
                        if (paramsList.length == valuesList.length) {
                            for (int i = 0; i < valuesList.length; i++) {
                                Statement valueStatement = valuesList[i];
                                if (InvokerUtil.is$(valueStatement)) {
                                    InvokerStatement invokerStatement = (InvokerStatement) valueStatement;
                                    Statement vStatement = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList()[0];
                                    if (vStatement instanceof SymbolStatement.LetterStatement) {
                                        SymbolStatement.LetterStatement value = (SymbolStatement.LetterStatement) vStatement;
                                        Statement paramStatement = paramsList[i];
                                        if (paramStatement instanceof SymbolStatement.LetterStatement) {
                                            SymbolStatement.LetterStatement param = (SymbolStatement.LetterStatement) paramStatement;
                                            scanInfo.add(new ScanInvoker.ParamScanInfo(null, null, param.getSymbol(), value.getSymbol()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        @Override
        protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
            InsertStatement insertStatement = (InsertStatement) statement;
            scanStatement(insertStatement);
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, ToAssist sqlAssist) {
            return statement instanceof InsertStatement;
        }
    }

    static class NotInsertHandler extends AbstractHandler {
        private ConditionHandler conditionHandler;

        public NotInsertHandler(ScanInvoker.ScanInfo scanInfo) {
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
            return statement instanceof QueryStatement || statement instanceof DeleteStatement || statement instanceof UpdateStatement;
        }

        class ConditionHandler extends AbstractHandler {
            private ScanInvoker.ScanInfo scanInfo;

            public ConditionHandler(ScanInvoker.ScanInfo scanInfo) {
                this.scanInfo = scanInfo;
            }

            public void scanStatement(InvokerStatement invokerStatement) {
                Statement paramStatement = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList()[0];
                if (paramStatement instanceof SymbolStatement.LetterStatement) {
                    SymbolStatement.LetterStatement letterStatement = (SymbolStatement.LetterStatement) paramStatement;
                    String field = letterStatement.getSymbol();
                    Statement parentStatement = invokerStatement.getParentStatement();
                    while (!(parentStatement instanceof PackageStatement)) {
                        if (!(parentStatement instanceof ConditionStatement)) {
                            parentStatement = parentStatement.getParentStatement();
                        } else {
                            ConditionStatement conditionStatement = (ConditionStatement) parentStatement;
                            OperStatement oper = conditionStatement.getOper();
                            if (oper instanceof OperStatement.ANDStatement || oper instanceof OperStatement.ORStatement) {
                                parentStatement = parentStatement.getParentStatement();
                            } else {
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
                                    break;
                                }
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
}

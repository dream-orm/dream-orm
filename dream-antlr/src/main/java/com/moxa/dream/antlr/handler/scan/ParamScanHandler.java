package com.moxa.dream.antlr.handler.scan;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToNativeSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.InvokerUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParamScanHandler extends AbstractHandler {

    private final ConditionHandler conditionHandler;
    private final InsertHandler insertHandler;

    public ParamScanHandler(ScanInvoker.ScanInfo scanInfo) {
        conditionHandler = new ConditionHandler(scanInfo);
        insertHandler = new InsertHandler(scanInfo);
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        return statement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{conditionHandler, insertHandler};
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof InsertStatement || statement instanceof ConditionStatement;
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
                String field = letterStatement.getValue();
                Statement parentStatement = invokerStatement.getParentStatement();
                if (parentStatement instanceof ConditionStatement) {
                    ConditionStatement conditionStatement = (ConditionStatement) parentStatement;
                    OperStatement oper = conditionStatement.getOper();
                    if (oper instanceof OperStatement.ANDStatement) {
                        parentStatement = parentStatement.getParentStatement();
                        if (paramStatement instanceof ConditionStatement) {
                            conditionStatement = (ConditionStatement) parentStatement;
                        }
                    }
                    Statement left = conditionStatement.getLeft();
                    String database = null;
                    String table = null;
                    String column = null;
                    if (left instanceof ListColumnStatement) {
                        Statement[] columnList = ((ListColumnStatement) left).getColumnList();
                        column = ((SymbolStatement) columnList[columnList.length - 1]).getValue();
                        table = ((SymbolStatement) columnList[columnList.length - 2]).getValue();
                        if (columnList.length >= 3) {
                            database = ((SymbolStatement) columnList[columnList.length - 3]).getValue();
                        }
                    } else if (left instanceof SymbolStatement) {
                        column = ((SymbolStatement) left).getValue();
                    }
                    if (column != null) {
                        scanInfo.add(new ScanInvoker.ParamScanInfo(database, table, column, field));
                    }
                }
            }

        }

        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
            scanStatement((InvokerStatement) statement);
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return InvokerUtil.is$(statement);
        }
    }

    class InsertHandler extends AbstractHandler {

        private ScanInvoker.ScanInfo scanInfo;
        private List<String> columnList;
        private String table;

        public InsertHandler(ScanInvoker.ScanInfo scanInfo) {
            this.scanInfo = scanInfo;
        }

        @Override
        protected Handler[] handlerBound() {
            return new Handler[]{new ValuesHandler()};
        }

        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
            InsertStatement insertStatement = (InsertStatement) statement;
            table = ((SymbolStatement) insertStatement.getTable()).getValue();
            if (insertStatement.getParams() != null) {
                columnList = Arrays.stream(((ListColumnStatement) ((BraceStatement) insertStatement.getParams()).getStatement()).getColumnList())
                        .map(column -> ((SymbolStatement) column).getValue())
                        .collect(Collectors.toList());
            }
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return statement instanceof InsertStatement;
        }

        class ValuesHandler extends AbstractHandler {
            @Override
            protected Handler[] handlerBound() {
                return new Handler[]{new BraceHandler()};
            }

            @Override
            protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
                return statement;
            }

            @Override
            protected boolean interest(Statement statement, Assist sqlAssist) {
                return statement instanceof InsertStatement.ValuesStatement;
            }

            class BraceHandler extends AbstractHandler {
                @Override
                protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
                    BraceStatement braceStatement = (BraceStatement) statement;
                    if (braceStatement.getStatement() instanceof ListColumnStatement) {
                        ListColumnStatement listColumnStatement = (ListColumnStatement) braceStatement.getStatement();
                        Statement[] columnList = listColumnStatement.getColumnList();
                        if (columnList.length == InsertHandler.this.columnList.size()) {
                            ToNativeSQL toNativeSQL = new ToNativeSQL();
                            for (int i = 0; i < columnList.length; i++) {
                                if (InvokerUtil.is$(columnList[i])) {
                                    String field = toNativeSQL.toStr(((InvokerStatement) columnList[i]).getParamStatement(), null, null);
                                    scanInfo.add(new ScanInvoker.ParamScanInfo(null, table, insertHandler.columnList.get(i), field));
                                }
                            }
                        }
                    }
                    return statement;
                }

                @Override
                protected boolean interest(Statement statement, Assist sqlAssist) {
                    return statement instanceof BraceStatement;
                }
            }
        }
    }
}

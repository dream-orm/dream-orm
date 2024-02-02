package com.dream.system.antlr.handler.scan;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToNativeSQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.antlr.invoker.ScanInvoker;
import com.dream.system.util.InvokerUtil;
import com.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParamScanHandler extends AbstractHandler {

    private final ConditionHandler conditionHandler;
    private final InsertHandler insertHandler;
    private final ToSQL toSQL = new ToNativeSQL();

    public ParamScanHandler(ScanInvoker.ScanInfo scanInfo) {
        conditionHandler = new ConditionHandler(scanInfo);
        insertHandler = new InsertHandler(scanInfo);
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{conditionHandler, insertHandler};
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof InsertStatement || statement instanceof ConditionStatement;
    }


    class ConditionHandler extends AbstractHandler {
        private final ScanInvoker.ScanInfo scanInfo;

        public ConditionHandler(ScanInvoker.ScanInfo scanInfo) {
            this.scanInfo = scanInfo;
        }

        public void scanStatement(ConditionStatement conditionStatement) {
            Statement leftStatement = conditionStatement.getLeft();
            String database = null;
            String table = null;
            String column = null;
            if (leftStatement instanceof ListColumnStatement) {
                Statement[] columnList = ((ListColumnStatement) leftStatement).getColumnList();
                column = ((SymbolStatement) columnList[columnList.length - 1]).getValue();
                table = ((SymbolStatement) columnList[columnList.length - 2]).getValue();
                if (columnList.length >= 3) {
                    database = ((SymbolStatement) columnList[columnList.length - 3]).getValue();
                }
            } else if (leftStatement instanceof SymbolStatement) {
                column = ((SymbolStatement) leftStatement).getValue();
            }
            if (column != null) {
                OperStatement operStatement = conditionStatement.getOper();
                if (operStatement instanceof OperStatement.ANDStatement || operStatement instanceof OperStatement.ORStatement) {
                    return;
                } else if (operStatement instanceof OperStatement.BETWEENStatement) {
                    ConditionStatement rightConditionStatement = (ConditionStatement) conditionStatement.getRight();
                    String param = parseParamName(rightConditionStatement.getLeft());
                    if (param != null) {
                        scanInfo.add(new ScanInvoker.ParamScanInfo(database, table, column, param));
                    }
                    param = parseParamName(rightConditionStatement.getRight());
                    if (param != null) {
                        scanInfo.add(new ScanInvoker.ParamScanInfo(database, table, column, param));
                    }
                } else {
                    Statement rightStatement = conditionStatement.getRight();
                    String param = parseParamName(rightStatement);
                    if (param != null) {
                        scanInfo.add(new ScanInvoker.ParamScanInfo(database, table, column, param));
                    }
                }
            }
        }

        protected String parseParamName(Statement statement) {
            if (InvokerUtil.isMark(statement)) {
                InvokerStatement invokerStatement = (InvokerStatement) statement;
                try {
                    return toSQL.toStr(invokerStatement.getParamStatement(), null, null);
                } catch (AntlrException e) {
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
            scanStatement((ConditionStatement) statement);
            return super.handlerAfter(statement, assist, sql, life);
        }

        @Override
        protected boolean interest(Statement statement, Assist assist) {
            return statement instanceof ConditionStatement;
        }
    }

    class InsertHandler extends AbstractHandler {

        private ScanInvoker.ScanInfo scanInfo;
        private List<String> paramList = new ArrayList<>();
        private String table;

        public InsertHandler(ScanInvoker.ScanInfo scanInfo) {
            this.scanInfo = scanInfo;
        }

        @Override
        protected Handler[] handlerBound() {
            return new Handler[]{new ValuesHandler()};
        }

        @Override
        protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
            InsertStatement insertStatement = (InsertStatement) statement;
            Statement tableStatement = insertStatement.getTable();
            if (tableStatement instanceof SymbolStatement) {
                this.table = ((SymbolStatement) insertStatement.getTable()).getValue();
            }
            if (insertStatement.getColumns() != null) {
                List<String> columnList = Arrays.stream(((ListColumnStatement) ((BraceStatement) insertStatement.getColumns()).getStatement()).getColumnList())
                        .map(column -> ((SymbolStatement) column).getValue())
                        .collect(Collectors.toList());
                if (!ObjectUtil.isNull(paramList)) {
                    for (int i = 0; i < columnList.size(); i++) {
                        String param = paramList.get(i);
                        if (param != null) {
                            scanInfo.add(new ScanInvoker.ParamScanInfo(null, this.table, columnList.get(i), param));
                        }
                    }
                }
            }
            return super.handlerAfter(statement, assist, sql, life);
        }

        @Override
        protected boolean interest(Statement statement, Assist assist) {
            return statement instanceof InsertStatement;
        }

        class ValuesHandler extends AbstractHandler {
            @Override
            protected Handler[] handlerBound() {
                return new Handler[]{new BraceHandler()};
            }


            @Override
            protected boolean interest(Statement statement, Assist assist) {
                return statement instanceof InsertStatement.ValuesStatement;
            }

            class BraceHandler extends AbstractHandler {

                @Override
                protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
                    BraceStatement braceStatement = (BraceStatement) statement;
                    ListColumnStatement listColumnStatement = (ListColumnStatement) braceStatement.getStatement();
                    Statement[] columnList = listColumnStatement.getColumnList();
                    for (int i = 0; i < columnList.length; i++) {
                        String field = null;
                        if (InvokerUtil.isMark(columnList[i])) {
                            field = toSQL.toStr(((InvokerStatement) columnList[i]).getParamStatement(), null, null);
                        }
                        paramList.add(field);
                    }
                    return super.handlerAfter(statement, assist, sql, life);
                }

                @Override
                protected boolean interest(Statement statement, Assist assist) {
                    return statement instanceof BraceStatement;
                }
            }
        }
    }
}

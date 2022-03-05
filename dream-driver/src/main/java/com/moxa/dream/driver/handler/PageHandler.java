package com.moxa.dream.driver.handler;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.CompareExpr;
import com.moxa.dream.antlr.expr.FunctionExpr;
import com.moxa.dream.antlr.expr.QueryExpr;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.scan.QueryScanHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToDREAM;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.ExprUtil;
import com.moxa.dream.driver.interceptor.PageInterceptor;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.module.table.ColumnInfo;
import com.moxa.dream.module.table.TableFactory;
import com.moxa.dream.module.table.TableInfo;
import com.moxa.dream.module.util.NonCollection;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.List;

public class PageHandler extends AbstractHandler {
    private boolean offset;
    private Statement first;
    private Statement second;
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private Invoker invoker;
    private ToDREAM toDREAM = new ToDREAM();

    public PageHandler(Invoker invoker, MethodInfo methodInfo) {
        ObjectUtil.requireNonNull(methodInfo, "Property 'methodInfo' is required");
        this.invoker = invoker;
        this.methodInfo = methodInfo;
        this.tableFactory = methodInfo.getConfiguration().getTableFactory();
    }

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        handlerCount((QueryStatement) statement);
        handlerPage((QueryStatement) statement);
        invoker.setAccessible(false);
        return statement;
    }

    void handlerCount(QueryStatement statement) throws InvokerException {
        QueryStatement queryStatement = new QueryStatement();
        ExprUtil.copy(queryStatement, statement);
        queryStatement.setOrderStatement(null);
        SelectStatement selectStatement = queryStatement.getSelectStatement();
        PreSelectStatement preSelect = selectStatement.getPreSelect();
        String countSql = null;
        if (!preSelect.isDistinct()) {
            UnionStatement unionStatement = queryStatement.getUnionStatement();
            if (unionStatement == null) {
                SelectStatement countSelectStatement = new SelectStatement();
                ExprUtil.copy(countSelectStatement, selectStatement);
                ListColumnStatement listColumnStatement = new ListColumnStatement();
                listColumnStatement.add(new FunctionExpr(new ExprReader("count(1)")).expr());
                countSelectStatement.setSelectList(listColumnStatement);
                queryStatement.setSelectStatement(countSelectStatement);
                countSql = toDREAM.toStr(queryStatement, null, null);
            }
        }
        if (ObjectUtil.isNull(countSql)) {
            countSql = "select count(1) from (" + toDREAM.toStr(queryStatement, null, null) + ")t_tmp";
        }
        MethodInfo methodInfo = new MethodInfo.Builder(this.methodInfo.getConfiguration())
                .sql(countSql)
                .colType(Long.class)
                .rowType(NonCollection.class)
                .paramNameList(this.methodInfo.getParamNameList())
                .name(this.methodInfo.getName())
                .timeOut(this.methodInfo.getTimeOut())
                .build();
        PageInterceptor.PageCount pageCount = this.methodInfo.get(PageInterceptor.PageCount.class);
        if (pageCount == null) {
            pageCount = new PageInterceptor.PageCount();
            this.methodInfo.set(PageInterceptor.PageCount.class, pageCount);
        }
        pageCount.setMethodInfo(methodInfo);
    }

    void handlerPage(QueryStatement queryStatement) throws InvokerException {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(offset);
        limitStatement.setFirst(first);
        limitStatement.setSecond(second);
        if (tableFactory != null) {
            FromStatement fromStatement = queryStatement.getFromStatement();
            Statement joinList = fromStatement.getJoinList();
            if (joinList == null) {
                Statement mainTable = fromStatement.getMainTable();
                ScanInvoker.ScanInfo scanInfo = new ScanInvoker.ScanInfo();
                QueryScanHandler queryScanHandler = new QueryScanHandler(scanInfo);
                queryScanHandler.scanStatement(mainTable, true);
                ScanInvoker.TableScanInfo[] tableScanInfoList = scanInfo.getTableScanInfoMap().values().toArray(new ScanInvoker.TableScanInfo[0]);
                if (tableScanInfoList.length == 1) {
                    ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoList[0];
                    String table = tableScanInfo.getTable();
                    String alias = tableScanInfo.getAlias();
                    TableInfo tableInfo = tableFactory.getTableInfo(table);
                    if (tableInfo != null) {
                        ColumnInfo primary = tableInfo.getPrimary();
                        if (primary != null) {
                            String column = primary.getColumn();
                            String sql = alias + "." + column + " in(select " + column + " from " + table + " " + toDREAM.toStr(limitStatement, null, null) + ")";
                            Statement inStatement = new CompareExpr(new ExprReader(sql)).expr();
                            WhereStatement whereStatement = queryStatement.getWhereStatement();
                            if (whereStatement == null) {
                                whereStatement = new WhereStatement();
                                whereStatement.setCondition(inStatement);
                                queryStatement.setWhereStatement(whereStatement);
                            } else {
                                Statement condition = whereStatement.getCondition();
                                ConditionStatement conditionStatement = new ConditionStatement();
                                conditionStatement.setLeft(condition);
                                conditionStatement.setOper(new OperStatement.ANDStatement());
                                conditionStatement.setRight(inStatement);
                            }
                            return;
                        }
                    }
                }
            }
        }
        String sql = "select t_tmp.* from(" + toDREAM.toStr(queryStatement, null, null) + ")t_tmp " + toDREAM.toStr(limitStatement, null, null);
        QueryStatement pageQueryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
        ExprUtil.copy(queryStatement, pageQueryStatement);
    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof QueryStatement;
    }

    public void setParamList(Statement first, Statement second, boolean offset) {
        this.first = first;
        this.second = second;
        this.offset = offset;
    }

}

package com.moxa.dream.drive.antlr.handler;

import com.moxa.dream.antlr.config.Assist;
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
import com.moxa.dream.antlr.sql.ToNativeSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.drive.action.SqlAction;
import com.moxa.dream.drive.page.annotation.PageQuery;
import com.moxa.dream.system.mapper.Action;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.util.List;

public class PageHandler extends AbstractHandler {
    private final TableFactory tableFactory;
    private final MethodInfo methodInfo;
    private final Invoker invoker;
    private final ToNativeSQL toNativeSQL = new ToNativeSQL();
    private boolean offset;
    private Statement first;
    private Statement second;

    public PageHandler(Invoker invoker, MethodInfo methodInfo) {
        this.invoker = invoker;
        this.methodInfo = methodInfo;
        this.tableFactory = methodInfo.getConfiguration().getTableFactory();
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        handlerCount((QueryStatement) statement);
        handlerPage(assist, toSQL, invokerList, (QueryStatement) statement);
        invoker.setAccessible(false);
        return statement;
    }

    void handlerCount(QueryStatement statement) throws InvokerException {
        QueryStatement queryStatement = new QueryStatement();
        ReflectUtil.copy(queryStatement, statement);
        queryStatement.setOrderStatement(null);
        SelectStatement selectStatement = queryStatement.getSelectStatement();
        PreSelectStatement preSelect = selectStatement.getPreSelect();
        String countSql = null;
        if (!preSelect.isDistinct()) {
            UnionStatement unionStatement = queryStatement.getUnionStatement();
            if (unionStatement == null) {
                SelectStatement countSelectStatement = new SelectStatement();
                ReflectUtil.copy(countSelectStatement, selectStatement);
                ListColumnStatement listColumnStatement = new ListColumnStatement();
                listColumnStatement.add(new FunctionExpr(new ExprReader("count(1)")).expr());
                countSelectStatement.setSelectList(listColumnStatement);
                queryStatement.setSelectStatement(countSelectStatement);
                countSql = toNativeSQL.toStr(queryStatement, null, null);
            }
        }
        if (ObjectUtil.isNull(countSql)) {
            countSql = "select count(1) from (" + toNativeSQL.toStr(queryStatement, null, null) + ")t_tmp";
        }
        Action[] initActionList = this.methodInfo.getInitActionList();
        Action[] countInitActionList;
        if (initActionList == null) {
            countInitActionList = new Action[1];
        } else {
            countInitActionList = new Action[initActionList.length + 1];
            System.arraycopy(initActionList, 0, countInitActionList, 0, initActionList.length);
        }
        PageQuery pageQuery = methodInfo.get(PageQuery.class);
        String value = pageQuery.value();
        String property = "total";
        if (!ObjectUtil.isNull(value)) {
            property = value + "." + property;
        }
        countInitActionList[countInitActionList.length - 1] = new SqlAction(methodInfo.getConfiguration(), property, countSql);
        ObjectWrapper.wrapper(methodInfo).set("initActionList", countInitActionList);
    }

    void handlerPage(Assist assist, ToSQL toSQL, List<Invoker> invokerList, QueryStatement queryStatement) throws InvokerException {
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
                queryScanHandler.scanStatement(assist, toSQL, invokerList, mainTable, true);
                ScanInvoker.TableScanInfo[] tableScanInfoList = scanInfo.getTableScanInfoMap().values().toArray(new ScanInvoker.TableScanInfo[0]);
                if (tableScanInfoList.length == 1) {
                    ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoList[0];
                    String table = tableScanInfo.getTable();
                    String alias = tableScanInfo.getAlias();
                    TableInfo tableInfo = tableFactory.getTableInfo(table);
                    if (tableInfo != null) {
                        ColumnInfo primColumnInfo = tableInfo.getPrimColumnInfo();
                        if (primColumnInfo != null) {
                            String column = primColumnInfo.getColumn();
                            String where = new ToNativeSQL().toStr(queryStatement.getWhereStatement(), null, null);
                            String sql = alias + "." + column + " in(select " + column + " from (select " + column + " from " + table + " " + alias + " " + where + " " + toNativeSQL.toStr(limitStatement, null, null) + ")t_tmp)";
                            Statement inStatement = new CompareExpr(new ExprReader(sql)).expr();
                            WhereStatement whereStatement = new WhereStatement();
                            whereStatement.setCondition(inStatement);
                            queryStatement.setWhereStatement(whereStatement);
                            return;
                        }
                    }
                }
            }
        }
        String sql = "select t_tmp.* from(" + toNativeSQL.toStr(queryStatement, null, null) + ")t_tmp " + toNativeSQL.toStr(limitStatement, null, null);
        QueryStatement pageQueryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
        Statement parentStatement = queryStatement.getParentStatement();
        ReflectUtil.copy(queryStatement, pageQueryStatement);
        queryStatement.setParentStatement(parentStatement);
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }

    public void setParamList(Statement first, Statement second, boolean offset) {
        this.first = first;
        this.second = second;
        this.offset = offset;
    }

    public static class PageCount {
        private final MethodInfo methodInfo;

        public PageCount(MethodInfo methodInfo) {
            this.methodInfo = methodInfo;
        }

        public MethodInfo getMethodInfo() {
            return methodInfo;
        }
    }
}

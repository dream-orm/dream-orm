package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;

import java.util.List;

/**
 * SQL原始字符串
 */
public class ToNativeSQL extends ToSQL {
    @Override
    public String getName() {
        String name;
        String className = this.getClass().getSimpleName().toLowerCase();
        if (className.length() > 2 && className.startsWith("to")) {
            name = className.substring(2);
        } else {
            name = className;
        }
        return name;
    }

    @Override
    protected String before(Statement statement) {
        return statement.getQuickValue();
    }

    @Override
    protected void after(Statement statement, String sql) {

    }

    @Override
    protected String toString(PackageStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "REPEAT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOCATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateForMatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATE_FORMAT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "STR_TO_DATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(UnionStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return " UNION" + (statement.isAll() ? " ALL " : " ") + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(ForUpdateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return " FOR UPDATE";
    }

    @Override
    protected String toString(ForUpdateNoWaitStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return " FOR UPDATE NOWAIT";
    }

    @Override
    protected String toString(AliasStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getColumn(), assist, invokerList) + (statement.isShowAlias() ? " AS " : " ") + toStr(statement.getAlias(), assist, invokerList);
    }

    @Override
    protected String toString(IntervalStatement.YearIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INTERVAL " + toStr(statement.getStatement(), assist, invokerList) + " YEAR";
    }

    @Override
    protected String toString(IntervalStatement.QuarterIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INTERVAL " + toStr(statement.getStatement(), assist, invokerList) + " QUARTER";
    }

    @Override
    protected String toString(IntervalStatement.MonthIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INTERVAL " + toStr(statement.getStatement(), assist, invokerList) + " MONTH";
    }

    @Override
    protected String toString(IntervalStatement.WeekIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INTERVAL " + toStr(statement.getStatement(), assist, invokerList) + " WEEK";
    }

    @Override
    protected String toString(IntervalStatement.DayIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INTERVAL " + toStr(statement.getStatement(), assist, invokerList) + " DAY";
    }

    @Override
    protected String toString(IntervalStatement.HourIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INTERVAL " + toStr(statement.getStatement(), assist, invokerList) + " HOUR";
    }

    @Override
    protected String toString(IntervalStatement.MinuteIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INTERVAL " + toStr(statement.getStatement(), assist, invokerList) + " MINUTE";
    }

    @Override
    protected String toString(IntervalStatement.SecondIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INTERVAL " + toStr(statement.getStatement(), assist, invokerList) + " SECOND";
    }

    @Override
    protected String toString(UpdateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "UPDATE " + toStr(statement.getTable(), assist, invokerList) + " SET " + toStr(statement.getConditionList(), assist, invokerList) + (statement.getWhere() != null ? " " + toStr(statement.getWhere(), assist, invokerList) : "");
    }

    @Override
    protected String toString(InsertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement columns = statement.getColumns();
        return "INSERT INTO " + toStr(statement.getTable(), assist, invokerList) + (columns != null ? toStr(columns, assist, invokerList) : " ") + toStr(statement.getValues(), assist, invokerList);
    }

    @Override
    protected String toString(InsertStatement.ValuesStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "VALUES" + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(DeleteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DELETE FROM " + toStr(statement.getTable(), assist, invokerList) + (statement.getWhere() != null ? " " + toStr(statement.getWhere(), assist, invokerList) : "");
    }

    @Override
    protected String toString(LimitStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        StringBuilder builder = new StringBuilder();
        if (statement.isOffset()) {
            builder.append(" LIMIT " + toStr(statement.getFirst(), assist, invokerList));
            if (statement.getSecond() != null) {
                builder.append(" OFFSET " + toStr(statement.getSecond(), assist, invokerList));
            }
        } else {
            builder.append(" LIMIT " + toStr(statement.getFirst(), assist, invokerList));
            if (statement.getSecond() != null) {
                builder.append("," + toStr(statement.getSecond(), assist, invokerList));
            }
        }
        return builder.toString();
    }

    @Override
    protected String toString(OrderStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return " ORDER BY " + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(OrderStatement.AscStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getSort(), assist, invokerList) + " ASC";
    }

    @Override
    protected String toString(OrderStatement.DescStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getSort(), assist, invokerList) + " DESC";
    }

    @Override
    protected String toString(HavingStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return " HAVING " + toStr(statement.getCondition(), assist, invokerList);
    }

    @Override
    protected String toString(GroupStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return " GROUP BY " + toStr(statement.getGroup(), assist, invokerList);
    }

    @Override
    protected String toString(WhereStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String condition = toStr(statement.getStatement(), assist, invokerList);
        return " WHERE " + condition;
    }

    @Override
    protected String toString(OperStatement.LTStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.LEQStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<=" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.GTStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.GEQStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">=" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.EQStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "=" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.NEQStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<>" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.LIKEStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " LIKE " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.ISStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " IS " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.INStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " IN " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.NOTStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " NOT " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.EXISTSStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " EXISTS " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.STARStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "*" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.DIVIDEStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "/" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.MODStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "%" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(BraceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ListColumnStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = statement.getColumnList();
        if (columnList.length == 0) {
            return "";
        }
        String cut = toStr(statement.getCut(), assist, invokerList);
        StringBuilder stringBuilder = new StringBuilder();
        int i;
        for (i = 0; i < columnList.length; i++) {
            String column = toStr(columnList[i], assist, invokerList);
            if (column != null && !column.isEmpty()) {
                stringBuilder.append(column + cut);
            }
        }
        if (stringBuilder.length() > 0) {
            return stringBuilder.substring(0, stringBuilder.length() - cut.length());
        }
        return stringBuilder.toString();
    }

    @Override
    protected String toString(SymbolStatement statement, Assist assist, List<Invoker> invokerList) {
        return statement.getSymbol();
    }

    @Override
    protected String toString(SymbolStatement.SingleMarkStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return statement.getSymbol();
    }

    @Override
    protected String toString(QueryStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String from = toStr(statement.getFromStatement(), assist, invokerList);
        String select = toStr(statement.getSelectStatement(), assist, invokerList);
        String where = toStr(statement.getWhereStatement(), assist, invokerList);
        String groupBy = toStr(statement.getGroupStatement(), assist, invokerList);
        String having = toStr(statement.getHavingStatement(), assist, invokerList);
        String orderBy = toStr(statement.getOrderStatement(), assist, invokerList);
        String limit = toStr(statement.getLimitStatement(), assist, invokerList);
        String union = toStr(statement.getUnionStatement(), assist, invokerList);
        String forUpdate = toStr(statement.getForUpdateStatement(), assist, invokerList);
        return select + from + where + groupBy + having + orderBy + limit + union + forUpdate;
    }

    @Override
    protected String toString(SelectStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SELECT " + (statement.isDistinct() ? "DISTINCT " : "") + toStr(statement.getSelectList(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.AsciiStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ASCII(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CharLengthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CHAR_LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LengthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConcatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONCAT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.GroupConcatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        boolean distinct = statement.isDistinct();
        boolean all = statement.isAll();
        Statement order = statement.getOrder();
        Statement separator = statement.getSeparator();
        StringBuilder builder = new StringBuilder();
        if (distinct) {
            builder.append("DISTINCT ");
        }
        if (all) {
            builder.append("ALL ");
        }
        builder.append(toStr(statement.getParamsStatement(), assist, invokerList));
        if (order != null) {
            builder.append(" " + toStr(order, assist, invokerList));
        }
        if (separator != null) {
            builder.append(" SEPARATOR " + toStr(separator, assist, invokerList));
        }
        return "GROUP_CONCAT(" + builder + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConcatWsStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONCAT_WS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.FindInSetStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "FIND_IN_SET(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LcaseStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LCASE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LowerStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConditionStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getOper(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.LeftStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LEFT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RIGHT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LtrimStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ReverseStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "REVERSE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ReplaceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "REPLACE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RtrimStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SubStrStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SUBSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TrimStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SPACE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.UpperStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "UPPER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AbsStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ABS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AvgStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "AVG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AcosStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ACOS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AsinStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ASIN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SinStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SIN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AtanStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ATAN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Atan2Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ATAN2(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CosStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "COS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CotStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "COT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CountStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "COUNT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ExpStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "EXP(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.FloorStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "FLOOR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LnStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG2(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG10(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MaxStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "MAX(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MinStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "MIN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ModStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "MOD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PiStatement statement, Assist assist, List<Invoker> invokerList) {
        return "PI()";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "POW(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PowerStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, Assist assist, List<Invoker> invokerList) {
        return "RAND()";
    }

    @Override
    protected String toString(FunctionStatement.RoundStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SignStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SIGN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SqrtStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SQRT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SumStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SUM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TanStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TAN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TRUNCATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATE_ADD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATE_SUB(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "CURDATE()";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEDIFF(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DAY(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DAYOFWEEK(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DAYOFYEAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "HOUR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "MINUTE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LAST_DAY(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "MONTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.UnixTimeStampStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "UNIX_TIMESTAMP(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.FromUnixTimeStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "FROM_UNIXTIME(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.NowStatement statement, Assist assist, List<Invoker> invokerList) {
        return "NOW()";
    }

    @Override
    protected String toString(FunctionStatement.SysDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "SYSDATE()";
    }

    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "QUARTER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SECOND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "WEEKOFYEAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "YEAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IsNullStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ISNULL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "IFNULL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CoalesceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "COALESCE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.NullIfStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "NULLIF(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "IF(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToCharStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToNumberStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_DATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToTimeStampStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_TIMESTAMP(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS SIGNED)";
    }

    @Override
    protected String toString(CastTypeStatement.FloatCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS FLOAT)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS CHAR)";
    }

    @Override
    protected String toString(CastTypeStatement.DateCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATE)";
    }

    @Override
    protected String toString(CastTypeStatement.TimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIME)";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATETIME)";
    }

    @Override
    protected String toString(CastTypeStatement.DecimalCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DECIMAL" + toStr(statement.getParamStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",SIGNED)";
    }

    @Override
    protected String toString(ConvertTypeStatement.FloatConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",FLOAT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",CHAR)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",DATE)";
    }

    @Override
    protected String toString(ConvertTypeStatement.TimeConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",TIME)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateTimeConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",DATETIME)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DecimalConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",DECIMAL" + toStr(statement.getParamStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(OperStatement.ADDStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "+" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.LLMStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<<" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.RRMStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">>" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.SUBStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "-" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(CaseStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CASE " + toStr(statement.getCaseColumn(), assist, invokerList) + " " + toStr(statement.getWhenthenList(), assist, invokerList) + " " + (statement.getElseColumn() != null ? "ELSE " + toStr(statement.getElseColumn(), assist, invokerList) : "") + " END";
    }

    @Override
    protected String toString(CaseStatement.WhenThenStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "WHEN " + toStr(statement.getWhen(), assist, invokerList) + " THEN " + toStr(statement.getThen(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BETWEENStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " BETWEEN " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.ANDStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " AND " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITANDStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "&" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "|" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITXORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "^" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.ORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " OR " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(FromStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String table = toStr(statement.getMainTable(), assist, invokerList);
        String joins = toStr(statement.getJoinList(), assist, invokerList);
        return " FROM " + table + ("".equals(joins) ? "" : " " + joins);
    }

    @Override
    protected String toString(JoinStatement.LeftJoinStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LEFT JOIN " + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(JoinStatement.RightJoinStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RIGHT JOIN " + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(JoinStatement.InnerJoinStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INNER JOIN " + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(JoinStatement.CrossJoinStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CROSS JOIN " + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(RowNumberStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ROW_NUMBER()" + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(RowNumberStatement.OverStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "OVER(" + toStr(statement.getPartitionStatement(), assist, invokerList) + toStr(statement.getOrderStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(RowNumberStatement.OverStatement.PartitionStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "PARTITION BY " + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(InvokerStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "@" + statement.getFunction() + (statement.getNamespace() == null ? "" : (":" + statement.getNamespace())) + "(" + toStr(statement.getParamStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(EmitStatement.ColonStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return ":" + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(EmitStatement.DollarStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "$" + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(EmitStatement.SharpStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "#" + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(MyFunctionStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return statement.getFunctionName() + "(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    public String toString(FunctionStatement.ReturnParameterStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(TruncateTableStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TRUNCATE TABLE " + toStr(statement.getTable(), assist, invokerList);
    }

    @Override
    protected String toString(DDLCreateStatement.DDLCreateDatabaseStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        boolean existCreate = statement.isExistCreate();
        return "CREATE TABLE " + (existCreate ? "" : "IF NOT EXISTS ") + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(DDLCreateStatement.DDLCreateTableStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement engine = statement.getEngine();
        Statement defaultCharset = statement.getDefaultCharset();
        Statement comment = statement.getComment();
        StringBuilder builder = new StringBuilder();
        if (engine != null) {
            builder.append(" ENGINE=" + toStr(engine, assist, invokerList));
        }
        if (defaultCharset != null) {
            builder.append(" DEFAULT CHARSET=" + toStr(defaultCharset, assist, invokerList));
        }
        if (comment != null) {
            builder.append(" COMMENT=" + toStr(comment, assist, invokerList));
        }
        ListColumnStatement columnDefineList = statement.getColumnDefineList();
        boolean existCreate = statement.isExistCreate();
        return "CREATE TABLE " + (existCreate ? "" : "IF NOT EXISTS ") + toStr(statement.getStatement(), assist, invokerList) + "(" +
                toStr(columnDefineList, assist, invokerList)
                + ")" + builder;
    }

    @Override
    protected String toString(DDLDefineStatement.DDLColumnDefineStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement column = statement.getColumn();
        ExprType columnType = statement.getColumnType();
        ListColumnStatement columnTypeParamList = statement.getColumnTypeParamList();
        Statement comment = statement.getComment();
        Statement defaultValue = statement.getDefaultValue();
        boolean autoIncrement = statement.isAutoIncrement();
        boolean nullFlag = statement.isNullFlag();
        boolean primaryKey = statement.isPrimaryKey();
        StringBuilder builder = new StringBuilder();
        if (columnTypeParamList != null) {
            builder.append("(" + toStr(columnTypeParamList, assist, invokerList) + ")");
        }
        if (!nullFlag) {
            builder.append(" NOT NULL");
        }
        if (autoIncrement) {
            builder.append(" AUTO_INCREMENT");
        }
        if (primaryKey) {
            builder.append(" PRIMARY KEY");
        }
        if (defaultValue != null) {
            builder.append(" DEFAULT " + toStr(defaultValue, assist, invokerList));
        }
        if (comment != null) {
            builder.append(" COMMENT " + toStr(comment, assist, invokerList));
        }
        return toStr(column, assist, invokerList) + " " + columnType.name() + builder;
    }

    @Override
    protected String toString(DDLDefineStatement.DDLPrimaryKeyDefineStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        StringBuilder builder = new StringBuilder();
        Statement constraint = statement.getConstraint();
        if (constraint != null) {
            builder.append("CONSTRAINT " + toStr(constraint, assist, invokerList) + " ");
        }
        ListColumnStatement primaryKeys = statement.getPrimaryKeys();
        builder.append("PRIMARY KEY(" + toStr(primaryKeys, assist, invokerList) + ")");
        return builder.toString();
    }

    @Override
    protected String toString(DDLDefineStatement.DDLForeignKeyDefineStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        StringBuilder builder = new StringBuilder();
        Statement constraint = statement.getConstraint();
        if (constraint != null) {
            builder.append("CONSTRAINT " + toStr(constraint, assist, invokerList) + " ");
        }
        Statement foreignKey = statement.getForeignKey();
        Statement foreignTable = statement.getForeignTable();
        Statement foreignColumn = statement.getForeignColumn();
        builder.append("FOREIGN KEY(" + toStr(foreignKey, assist, invokerList) + ")");
        builder.append(" REFERENCES " + toStr(foreignTable, assist, invokerList) + "(" + toStr(foreignColumn, assist, invokerList) + ")");
        return builder.toString();
    }

    @Override
    protected String toString(DDLDropStatement.DDLDropDatabaseStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DROP DATABASE " + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(DDLDropStatement.DDLDropTableStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DROP TABLE " + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(DDLAlterStatement.DDLAlterRenameStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ALTER TABLE " + toStr(statement.getTable(), assist, invokerList) + " RENAME TO " + toStr(statement.getNewTable(), assist, invokerList);
    }

    @Override
    protected String toString(DDLAlterStatement.DDLAlterDropStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ALTER TABLE " + toStr(statement.getTable(), assist, invokerList) + " DROP COLUMN " + toStr(statement.getColumn(), assist, invokerList);
    }

    @Override
    protected String toString(DDLAlterStatement.DDLAlterAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ListColumnStatement columnTypeParamList = statement.getColumnTypeParamList();
        StringBuilder builder = new StringBuilder();
        if (columnTypeParamList != null) {
            builder.append("(" + toStr(columnTypeParamList, assist, invokerList) + ")");
        }
        Statement defaultValue = statement.getDefaultValue();
        if (!statement.isNullFlag()) {
            builder.append(" NOT NULL");
        }
        if (defaultValue != null) {
            builder.append(" DEFAULT " + toStr(defaultValue, assist, invokerList));
        }
        return "ALTER TABLE " + toStr(statement.getTable(), assist, invokerList) + " ADD COLUMN " + toStr(statement.getColumn(), assist, invokerList) + " " + statement.getColumnType().name() + builder;
    }

    @Override
    protected String toString(DDLAlterStatement.DDLAlterModifyStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ListColumnStatement columnTypeParamList = statement.getColumnTypeParamList();
        StringBuilder builder = new StringBuilder();
        if (columnTypeParamList != null) {
            builder.append("(" + toStr(columnTypeParamList, assist, invokerList) + ")");
        }
        Statement defaultValue = statement.getDefaultValue();
        if (!statement.isNullFlag()) {
            builder.append(" NOT NULL");
        }
        if (defaultValue != null) {
            builder.append(" DEFAULT " + toStr(defaultValue, assist, invokerList));
        }
        return "ALTER TABLE " + toStr(statement.getTable(), assist, invokerList) + " MODIFY COLUMN " + toStr(statement.getColumn(), assist, invokerList) + " " + statement.getColumnType().name() + builder;
    }
}

package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;

import java.util.List;

public class ToDREAM extends ToSQL {
    @Override
    public String getName() {
        String name;
        String className = this.getClass().getSimpleName().toLowerCase();
        if (className.length() > 2 && className.startsWith("to"))
            name = className.substring(2);
        else
            name = className;
        return name;

    }

    protected String beforeCache(Statement statement) {
        String sql = statement.getQuickValue();
        if (sql != null)
            return sql;
        else
            return null;
    }


    protected void afterCache(Statement statement, String sql) {

    }

    @Override
    protected String toString(PackageStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "REPEAT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "INSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOCATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_DATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToCharStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToNumberStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateForMatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATE_FORMAT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "STR_TO_DATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(UnionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " UNION" + (statement.isAll() ? " ALL " : " ") + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(AliasStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getColumn(), assist, invokerList) + (statement.isShowAlias() ? " AS " : " ") + toStr(statement.getAlias(), assist, invokerList);
    }

    @Override
    protected String toString(DateOperStatement.YearDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "DATE_ADD";
        if (!statement.isPositive())
            type = "DATE_SUB";
        return type + "(" + toStr(statement.getDate(), assist, invokerList) + ",INTERVAL " + toStr(statement.getQty(), assist, invokerList) + " YEAR)";

    }

    @Override
    protected String toString(DateOperStatement.QuarterDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "DATE_ADD";
        if (!statement.isPositive())
            type = "DATE_SUB";
        return type + "(" + toStr(statement.getDate(), assist, invokerList) + ",INTERVAL " + toStr(statement.getQty(), assist, invokerList) + " QUARTER)";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "DATE_ADD";
        if (!statement.isPositive())
            type = "DATE_SUB";
        return type + "(" + toStr(statement.getDate(), assist, invokerList) + ",INTERVAL " + toStr(statement.getQty(), assist, invokerList) + " MONTH)";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "DATE_ADD";
        if (!statement.isPositive())
            type = "DATE_SUB";
        return type + "(" + toStr(statement.getDate(), assist, invokerList) + ",INTERVAL " + toStr(statement.getQty(), assist, invokerList) + " WEEK)";
    }

    @Override
    protected String toString(DateOperStatement.DayDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "DATE_ADD";
        if (!statement.isPositive())
            type = "DATE_SUB";
        return type + "(" + toStr(statement.getDate(), assist, invokerList) + ",INTERVAL " + toStr(statement.getQty(), assist, invokerList) + " DAY)";
    }

    @Override
    protected String toString(DateOperStatement.HourDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "DATE_ADD";
        if (!statement.isPositive())
            type = "DATE_SUB";
        return type + "(" + toStr(statement.getDate(), assist, invokerList) + ",INTERVAL " + toStr(statement.getQty(), assist, invokerList) + " HOUR)";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "DATE_ADD";
        if (!statement.isPositive())
            type = "DATE_SUB";
        return type + "(" + toStr(statement.getDate(), assist, invokerList) + ",INTERVAL " + toStr(statement.getQty(), assist, invokerList) + " MINUTE)";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "DATE_ADD";
        if (!statement.isPositive())
            type = "DATE_SUB";
        return type + "(" + toStr(statement.getDate(), assist, invokerList) + ",INTERVAL " + toStr(statement.getQty(), assist, invokerList) + " SECOND)";
    }

    @Override
    protected String toString(UpdateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "UPDATE " + toStr(statement.getTable(), assist, invokerList) + " SET " + toStr(statement.getConditionList(), assist, invokerList) + (statement.getWhere() != null ? " WHERE " + toStr(statement.getWhere(), assist, invokerList) : "");
    }

    @Override
    protected String toString(InsertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "INSERT INTO " + toStr(statement.getTable(), assist, invokerList) + (statement.getParams() != null ? toStr(statement.getParams(), assist, invokerList) : " ") + toStr(statement.getValues(), assist, invokerList);
    }

    @Override
    protected String toString(InsertStatement.ValuesStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "VALUES" + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(DeleteStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DELETE FROM " + toStr(statement.getTable(), assist, invokerList) + (statement.getCondition() != null ? " WHERE " + toStr(statement.getCondition(), assist, invokerList) : "");
    }

    @Override
    protected String toString(LimitStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        StringBuilder builder = new StringBuilder();
        if (statement.isOffset()) {
            builder.append(" OFFSET " + toStr(statement.getFirst(), assist, invokerList));
            if (statement.getSecond() != null) {
                builder.append(" LIMIT " + toStr(statement.getSecond(), assist, invokerList));
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
    protected String toString(OrderStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " ORDER BY " + toStr(statement.getOrder(), assist, invokerList);
    }

    @Override
    protected String toString(OrderStatement.AscStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getSort(), assist, invokerList) + " ASC";
    }

    @Override
    protected String toString(OrderStatement.DescStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getSort(), assist, invokerList) + " DESC";
    }

    @Override
    protected String toString(HavingStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " HAVING " + toStr(statement.getCondition(), assist, invokerList);
    }

    @Override
    protected String toString(GroupStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " GROUP BY " + toStr(statement.getGroup(), assist, invokerList);
    }

    @Override
    protected String toString(WhereStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " WHERE " + toStr(statement.getCondition(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.LTStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.LEQStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<=" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.GTStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.GEQStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">=" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.EQStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "=" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.NEQStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<>" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.LIKEStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " LIKE " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.ISStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " IS " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.INStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " IN " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.NOTStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " NOT " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.EXISTSStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " EXISTS " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.STARStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "*" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.DIVIDEStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "/" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.MODStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "%" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(BraceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ListColumnStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = statement.getColumnList();
        if (columnList.length == 0)
            return "";
        String cut = toStr(statement.getCut(), assist, invokerList);
        StringBuilder stringBuilder = new StringBuilder();
        int i;
        for (i = 0; i < columnList.length; i++) {
            String column = toStr(columnList[i], assist, invokerList);
            if (!column.equals(""))
                stringBuilder.append(column + cut);
        }
        if (stringBuilder.length() > 0)
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Override
    protected String toString(SymbolStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return statement.getSymbol();
    }

    @Override
    protected String toString(SingleMarkStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
//        return "`" + toStr(statement.getStatement(), assist, invokerList) + "`";
        return toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(QueryStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String from = toStr(statement.getFromStatement(), assist, invokerList);
        String where = toStr(statement.getWhereStatement(), assist, invokerList);
        String groupBy = toStr(statement.getGroupStatement(), assist, invokerList);
        String having = toStr(statement.getHavingStatement(), assist, invokerList);
        String select = toStr(statement.getSelectStatement(), assist, invokerList);
        String orderBy = toStr(statement.getOrderStatement(), assist, invokerList);
        String limit = toStr(statement.getLimitStatement(), assist, invokerList);
        String union = toStr(statement.getUnionStatement(), assist, invokerList);
        return select + from + where + groupBy + having + orderBy + limit + union;
    }

    @Override
    protected String toString(SelectStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getPreSelect(), assist, invokerList) + " " + toStr(statement.getSelectList(), assist, invokerList);
    }

    @Override
    protected String toString(PreSelectStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "SELECT" + (statement.isDistinct() ? " DISTINCT" : "");
    }

    @Override
    protected String toString(FunctionStatement.AsciiStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ASCII(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LenStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LEN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConcatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONCAT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConcatWsStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONCAT_WS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LcaseStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LCASE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LowerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConditionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getOper(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.LeftStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LEFT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "RIGHT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LtrimStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ReverseStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "REVERSE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ReplaceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "REPLACE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RtrimStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "RTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SubStrStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SUBSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TrimStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SPACE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.UpperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "UPPER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "RPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AbsStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ABS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AvgStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "AVG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AcosStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ACOS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AsinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ASIN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SIN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.AtanStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ATAN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Atan2Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ATAN2(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CosStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "COS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CotStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "COT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CountStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "COUNT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ExpStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "EXP(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.FloorStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "FLOOR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LnStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG2(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG10(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MaxStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "MAX(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "MIN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ModStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "MOD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PiStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "PI()";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "POW(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PowerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "RAND()";
    }

    @Override
    protected String toString(FunctionStatement.RoundStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SignStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SIGN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SqrtStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SQRT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SumStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SUM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TanStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TAN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TRUNCATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "CURDATE()";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEDIFF(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DAY(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DAYOFWEEK(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DAYOFYEAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "HOUR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "MINUTE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LAST_DAY(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "MONTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.NowStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "NOW()";
    }

    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "QUARTER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SECOND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "WEEKOFYEAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "YEAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IsNullStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ISNULL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "IFNULL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CoalesceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "COALESCE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.NullIfStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "NULLIF(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "IF(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS SIGNED)";
    }

    @Override
    protected String toString(CastTypeStatement.FloatCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS FLOAT)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS CHAR)";
    }

    @Override
    protected String toString(CastTypeStatement.DateCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATE)";
    }

    @Override
    protected String toString(CastTypeStatement.TimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIME)";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATETIME)";
    }

    @Override
    protected String toString(CastTypeStatement.DecimalCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DECIMAL" + toStr(statement.getParamStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",SIGNED)";
    }

    @Override
    protected String toString(ConvertTypeStatement.FloatConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",FLOAT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",CHAR)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",DATE)";
    }

    @Override
    protected String toString(ConvertTypeStatement.TimeConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",TIME)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateTimeConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",DATETIME)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DecimalConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(" + toStr(statement.getStatement(), assist, invokerList) + ",DECIMAL" + toStr(statement.getParamStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(OperStatement.ADDStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "+" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.LLMStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<<" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.RRMStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">>" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.SUBStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "-" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(CaseStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CASE " + toStr(statement.getCaseColumn(), assist, invokerList) + " " + toStr(statement.getWhenthenList(), assist, invokerList) + " " + (statement.getElseColumn() != null ? "ELSE " + toStr(statement.getElseColumn(), assist, invokerList) : "") + " END";
    }

    @Override
    protected String toString(CaseStatement.WhenThenStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "WHEN " + toStr(statement.getWhen(), assist, invokerList) + " THEN " + toStr(statement.getThen(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BETWEENStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " BETWEEN " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.ANDStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " AND " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITANDStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "&" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "|" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITXORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "^" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.ORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + " OR " + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(FromStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String table = toStr(statement.getMainTable(), assist, invokerList);
        String joins = toStr(statement.getJoinList(), assist, invokerList);
        return " FROM " + table + (joins.equals("") ? "" : " " + joins);
    }

    @Override
    protected String toString(JoinStatement.CommaJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "," + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(JoinStatement.LeftJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " LEFT JOIN " + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(JoinStatement.RightJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " RIGHT JOIN " + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(JoinStatement.InnerJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " INNER JOIN " + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(JoinStatement.CrossJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return " CROSS JOIN " + toStr(statement.getJoinTable(), assist, invokerList) + (statement.getOn() != null ? (" ON " + toStr(statement.getOn(), assist, invokerList)) : "");
    }

    @Override
    protected String toString(RowNumberStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ROW_NUMBER()" + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(RowNumberStatement.OverStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "OVER(" + toStr(statement.getPartitionStatement(), assist, invokerList) + toStr(statement.getOrderStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(RowNumberStatement.OverStatement.PartitionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "PARTITION BY " + toStr(statement.getStatement(), assist, invokerList);
    }

    @Override
    protected String toString(InvokerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "@" + statement.getFunction() + (statement.getNamespace() == null ? "" : (":" + statement.getNamespace())) + "(" + toStr(statement.getParamStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CustomFunctionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return statement.getFunctionName() + "(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }
}

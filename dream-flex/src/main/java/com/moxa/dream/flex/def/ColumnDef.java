package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.flex.function.LazyFunctionStatement;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ColumnDef {
    private TableDef tableDef;
    private Statement statement;
    private String alias;

    public ColumnDef(Statement statement) {
        this.statement = statement;
    }

    public ColumnDef(TableDef tableDef, String column, String alias) {
        this(new SymbolStatement.SingleMarkStatement(column));
        this.tableDef = tableDef;
        if (!column.equals(alias)) {
            this.alias = alias;
        }
    }

    public static ColumnDef column(String column) {
        return new ColumnDef(new SymbolStatement.LetterStatement(column));
    }
    public Statement getStatement(){
        return getStatement(false);
    }
    public Statement getStatement(boolean hasAlias) {
        Statement tempStatement = statement;
        if (tableDef != null) {
            ListColumnStatement listColumnStatement = new ListColumnStatement(".");
            listColumnStatement.add(new LazyFunctionStatement(() -> {
                AliasStatement tableStatement = tableDef.getStatement();
                Statement aliasStatement = tableStatement.getAlias();
                if (aliasStatement == null) {
                    aliasStatement = tableStatement.getColumn();
                }
                return aliasStatement;
            }));
            listColumnStatement.add(statement);
            tempStatement = listColumnStatement;
        }
        if (hasAlias&&alias != null && !alias.isEmpty()) {
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(tempStatement);
            aliasStatement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
            tempStatement = aliasStatement;
        }
        return tempStatement;
    }

    public ColumnDef as(String alias) {
        this.alias = alias;
        return this;
    }

    public ConditionDef eq(ColumnDef columnDef) {
        return conditionDef(new OperStatement.EQStatement(), columnDef.getStatement());
    }

    public ConditionDef eq(Object value) {
        return conditionDef(new OperStatement.EQStatement(), value);
    }

    public ConditionDef neq(Object value) {
        return conditionDef(new OperStatement.NEQStatement(), value);
    }

    public ConditionDef in(Object... values) {
        return in(Arrays.asList(values));
    }

    public ConditionDef in(Collection<?> values) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        Iterator<?> iterator = values.iterator();
        while (iterator.hasNext()) {
            Object value = iterator.next();
            listColumnStatement.add(new FlexMarkInvokerStatement(value));
        }
        BraceStatement braceStatement = new BraceStatement(listColumnStatement);
        return conditionDef(new OperStatement.INStatement(), braceStatement);
    }

    public ConditionDef notIn(Object... values) {
        return not(in(Arrays.asList(values)));
    }

    public ConditionDef in(SqlDef sqlDef) {
        BraceStatement braceStatement = new BraceStatement(sqlDef.getStatement());
        return conditionDef(new OperStatement.INStatement(), braceStatement);
    }

    public ConditionDef notIn(Collection<?> values) {
        return not(in(values));
    }

    public ConditionDef notIn(SqlDef sqlDef) {
        return not(in(sqlDef));
    }

    private ConditionDef not(ConditionDef conditionDef) {
        ConditionStatement statement = conditionDef.statement;
        Statement left = statement.getLeft();
        statement.setLeft(null);
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(left);
        conditionStatement.setOper(new OperStatement.NOTStatement());
        conditionStatement.setRight(statement);
        return new ConditionDef(conditionStatement);
    }

    public ConditionDef like(Object value) {
        return conditionDef(new OperStatement.LIKEStatement(), "%" + value + "%");
    }

    public ConditionDef likeLeft(Object value) {
        return conditionDef(new OperStatement.LIKEStatement(), "%" + value);
    }

    public ConditionDef likeRight(Object value) {
        return conditionDef(new OperStatement.LIKEStatement(), value + "%");
    }

    public ConditionDef between(Object start, Object end) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(this.getStatement());
        conditionStatement.setOper(new OperStatement.BETWEENStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setLeft(new SymbolStatement.LetterStatement(String.valueOf(start)));
        rightConditionStatement.setOper(new OperStatement.ANDStatement());
        rightConditionStatement.setRight(new SymbolStatement.LetterStatement(String.valueOf(end)));
        conditionStatement.setRight(rightConditionStatement);
        return new ConditionDef(conditionStatement);
    }

    public ConditionDef notBetween(Object start, Object end) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(this.getStatement());
        conditionStatement.setOper(new OperStatement.BETWEENStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setLeft(new SymbolStatement.LetterStatement(String.valueOf(start)));
        rightConditionStatement.setOper(new OperStatement.ANDStatement());
        rightConditionStatement.setRight(new SymbolStatement.LetterStatement(String.valueOf(end)));
        conditionStatement.setRight(rightConditionStatement);
        return new ConditionDef(conditionStatement);
    }

    protected ConditionDef conditionDef(OperStatement operStatement, Object value) {
        return conditionDef(operStatement, new FlexMarkInvokerStatement(value));
    }

    protected ConditionDef conditionDef(OperStatement operStatement, Statement statement) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(this.getStatement());
        conditionStatement.setOper(operStatement);
        conditionStatement.setRight(statement);
        return new ConditionDef(conditionStatement);
    }

    public SortDef asc() {
        OrderStatement.AscStatement ascStatement = new OrderStatement.AscStatement(this.getStatement());
        return new SortDef(ascStatement);
    }

    public SortDef desc() {
        OrderStatement.DescStatement descStatement = new OrderStatement.DescStatement(this.getStatement());
        return new SortDef(descStatement);
    }
}

package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.AliasStatement;
import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.flex.invoker.FlexTableInvokerStatement;

public class TableDef {
    private AliasStatement statement;

    public TableDef(String table) {
        this(table, null);
    }

    public TableDef(String table, String alias) {
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(new FlexTableInvokerStatement(table));
        if (alias != null && !alias.isEmpty()) {
            this.as(alias);
        }
        this.statement = aliasStatement;
    }

    TableDef(AliasStatement statement) {
        this.statement = statement;
    }

    public AliasStatement getStatement() {
        return statement;
    }

    protected TableDef as(String alias) {
        statement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
        return this;
    }
}

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
        this.statement = aliasStatement;
        if (alias != null && !alias.isEmpty() && !alias.equals(table)) {
            as(alias);
        }
    }

    public TableDef(AliasStatement statement) {
        this.statement = statement;
    }

    public static TableDef table(Query query) {
        BraceStatement braceStatement = new BraceStatement(query.getStatement());
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(braceStatement);
        return new TableDef(aliasStatement);
    }

    public AliasStatement getStatement() {
        return statement;
    }

    public TableDef as(String alias) {
        statement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
        return this;
    }
}

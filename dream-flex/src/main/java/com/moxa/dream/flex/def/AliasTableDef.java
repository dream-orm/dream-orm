package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.AliasStatement;

public class AliasTableDef extends TableDef {
    protected AliasTableDef(AliasStatement statement) {
        super(statement);
    }

    @Override
    public TableDef as(String alias) {
        return super.as(alias);
    }
}

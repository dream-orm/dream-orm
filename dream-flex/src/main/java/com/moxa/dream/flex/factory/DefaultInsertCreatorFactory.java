package com.moxa.dream.flex.factory;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.InsertDef;
import com.moxa.dream.flex.def.InsertIntoColumnsDef;
import com.moxa.dream.flex.def.InsertIntoTableDef;
import com.moxa.dream.flex.def.InsertIntoValuesDef;
import com.moxa.dream.flex.def.defaults.DefaultInsertDef;
import com.moxa.dream.flex.def.defaults.DefaultInsertIntoColumnsDef;
import com.moxa.dream.flex.def.defaults.DefaultInsertIntoTableDef;
import com.moxa.dream.flex.def.defaults.DefaultInsertIntoValuesDef;

public class DefaultInsertCreatorFactory implements InsertCreatorFactory {
    @Override
    public InsertDef newInsertDef() {
        return new DefaultInsertDef(this);
    }

    @Override
    public InsertIntoTableDef newInsertIntoTableDef(InsertStatement statement) {
        return new DefaultInsertIntoTableDef(statement, this);
    }

    @Override
    public InsertIntoColumnsDef newInsertIntoColumnsDef(InsertStatement statement) {
        return new DefaultInsertIntoColumnsDef(statement, this);
    }

    @Override
    public InsertIntoValuesDef newInsertIntoValuesDef(InsertStatement statement) {
        return new DefaultInsertIntoValuesDef(statement, this);
    }
}

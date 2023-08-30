package com.dream.flex.factory;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.def.InsertIntoValuesDef;
import com.dream.flex.def.defaults.DefaultInsertDef;
import com.dream.flex.def.defaults.DefaultInsertIntoColumnsDef;
import com.dream.flex.def.defaults.DefaultInsertIntoTableDef;
import com.dream.flex.def.defaults.DefaultInsertIntoValuesDef;

public class DefaultInsertCreatorFactory implements InsertCreatorFactory {

    @Override
    public InsertIntoTableDef newInsertIntoTableDef() {
        return new DefaultInsertIntoTableDef(this);
    }

    @Override
    public InsertIntoColumnsDef newInsertIntoColumnsDef(InsertStatement statement) {
        return new DefaultInsertIntoColumnsDef(statement, this);
    }

    @Override
    public InsertIntoValuesDef newInsertIntoValuesDef(InsertStatement statement) {
        return new DefaultInsertIntoValuesDef(statement, this);
    }

    @Override
    public InsertDef newInsertDef(InsertStatement statement) {
        return new DefaultInsertDef(statement, this);
    }
}

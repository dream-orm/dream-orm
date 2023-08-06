package com.moxa.dream.flex.factory;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.InsertDef;
import com.moxa.dream.flex.def.InsertIntoColumnsDef;
import com.moxa.dream.flex.def.InsertIntoTableDef;
import com.moxa.dream.flex.def.InsertIntoValuesDef;

public interface InsertCreatorFactory
        <Insert extends InsertDef<InsertIntoTable>,
                InsertIntoTable extends InsertIntoTableDef<InsertIntoColumns>,
                InsertIntoColumns extends InsertIntoColumnsDef<InsertIntoValues>,
                InsertIntoValues extends InsertIntoValuesDef> {
    Insert newInsertDef();

    InsertIntoTable newInsertIntoTableDef(InsertStatement statement);

    InsertIntoColumns newInsertIntoColumnsDef(InsertStatement statement);

    InsertIntoValues newInsertIntoValuesDef(InsertStatement statement);
}

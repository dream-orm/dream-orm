package com.dream.flex.factory;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.def.InsertIntoValuesDef;

public interface InsertCreatorFactory
        <Insert extends InsertDef<InsertIntoTable>,
                InsertIntoTable extends InsertIntoTableDef<InsertIntoColumns, InsertIntoValues>,
                InsertIntoColumns extends InsertIntoColumnsDef<InsertIntoValues>,
                InsertIntoValues extends InsertIntoValuesDef> {
    Insert newInsertDef();

    InsertIntoTable newInsertIntoTableDef(InsertStatement statement);

    InsertIntoColumns newInsertIntoColumnsDef(InsertStatement statement);

    InsertIntoValues newInsertIntoValuesDef(InsertStatement statement);
}

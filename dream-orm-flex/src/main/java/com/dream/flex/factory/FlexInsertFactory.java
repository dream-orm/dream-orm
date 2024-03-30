package com.dream.flex.factory;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.def.InsertIntoValuesDef;

public interface FlexInsertFactory
        <InsertIntoTable extends InsertIntoTableDef<InsertIntoColumns>,
                InsertIntoColumns extends InsertIntoColumnsDef<InsertIntoValues, Insert>,
                InsertIntoValues extends InsertIntoValuesDef<Insert>,
                Insert extends InsertDef> {

    InsertIntoTable newInsertIntoTableDef();

    InsertIntoColumns newInsertIntoColumnsDef(InsertStatement statement);

    InsertIntoValues newInsertIntoValuesDef(InsertStatement statement);

    Insert newInsertDef(InsertStatement statement);
}

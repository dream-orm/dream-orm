package com.dream.flex.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.factory.FlexInsertFactory;


public interface InsertDef {
    FlexInsertFactory creatorFactory();

    InsertStatement statement();
}

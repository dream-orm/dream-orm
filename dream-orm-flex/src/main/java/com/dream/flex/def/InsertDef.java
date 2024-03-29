package com.dream.flex.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.factory.InsertCreatorFactory;


public interface InsertDef {
    InsertStatement statement();

    InsertCreatorFactory creatorFactory();
}

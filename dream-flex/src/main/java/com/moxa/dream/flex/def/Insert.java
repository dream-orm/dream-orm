package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.factory.InsertCreatorFactory;


public interface Insert {
    InsertStatement statement();

    InsertCreatorFactory creatorFactory();
}

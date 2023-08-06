package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;


public interface Delete {
    DeleteStatement statement();

    DeleteCreatorFactory creatorFactory();
}

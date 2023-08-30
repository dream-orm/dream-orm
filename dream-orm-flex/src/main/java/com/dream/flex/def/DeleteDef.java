package com.dream.flex.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.factory.DeleteCreatorFactory;

public interface DeleteDef {
    DeleteStatement statement();

    DeleteCreatorFactory creatorFactory();
}

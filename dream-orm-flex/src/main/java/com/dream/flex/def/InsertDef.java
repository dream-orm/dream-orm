package com.dream.flex.def;

import com.dream.flex.factory.FlexInsertFactory;
import com.dream.instruct.command.Insert;


public interface InsertDef extends Insert {
    FlexInsertFactory creatorFactory();
}

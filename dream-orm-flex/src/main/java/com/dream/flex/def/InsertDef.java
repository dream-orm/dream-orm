package com.dream.flex.def;

import com.dream.flex.factory.FlexInsertFactory;
import com.dream.struct.command.Insert;


public interface InsertDef extends Insert {
    FlexInsertFactory creatorFactory();
}

package com.dream.flex.def;

import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.instruct.command.Delete;

public interface DeleteDef extends Delete {
    FlexDeleteFactory creatorFactory();
}

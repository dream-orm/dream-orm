package com.dream.flex.def.defaults;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDeleteDef;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.factory.FlexDeleteFactory;

public class DefaultDeleteTableDef extends AbstractDeleteDef implements DeleteTableDef {
    public DefaultDeleteTableDef(FlexDeleteFactory creatorFactory) {
        super(new DeleteStatement(), creatorFactory);
    }
}

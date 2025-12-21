package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.def.AbstractInsertDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdFileStatement;
import com.dream.tdengine.statement.TdInsertStatement;

public abstract class AbstractTdChainInsertDef extends AbstractInsertDef implements InsertDef, TdChainInsert {
    private FlexMapper flexMapper;

    public AbstractTdChainInsertDef(InsertStatement statement, FlexInsertFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    protected TdChainInsertDef file(String file) {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.StrStatement(file));
        TdFileStatement tdFileStatement = new TdFileStatement();
        tdFileStatement.setParamsStatement(listColumnStatement);
        tdInsertStatement.setValues(tdFileStatement);
        return (TdChainInsertDef) creatorFactory().newInsertDef(statement());
    }

    @Override
    public int execute() {
        return flexMapper.execute(this);
    }
}

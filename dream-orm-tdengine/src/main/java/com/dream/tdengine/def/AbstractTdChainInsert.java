package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.def.AbstractInsert;
import com.dream.flex.def.Insert;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdFileStatement;
import com.dream.tdengine.statement.TdInsertStatement;

public abstract class AbstractTdChainInsert extends AbstractInsert implements Insert, TdChainInsert {
    private FlexMapper flexMapper;

    public AbstractTdChainInsert(InsertStatement statement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    protected TdChainInsertIntoValuesDef file(String file) {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.StrStatement(file));
        TdFileStatement tdFileStatement = new TdFileStatement();
        tdFileStatement.setParamsStatement(listColumnStatement);
        tdInsertStatement.setValues(tdFileStatement);
        return (TdChainInsertIntoValuesDef) creatorFactory().newInsertIntoValuesDef(statement());
    }

    @Override
    public int execute() {
        return flexMapper.insert(this);
    }
}

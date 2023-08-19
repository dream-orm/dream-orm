package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.invoker.FlexMarkInvokerStatement;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdInsertStatement;

public class TdChainInsertIntoTableDef extends AbstractTdChainInsert implements InsertIntoTableDef<TdChainInsertIntoColumnsDef, TdChainInsertIntoValuesDef> {
    public TdChainInsertIntoTableDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
    public TdChainInsertIntoTableDef using(TableDef tableDef){
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        tdInsertStatement.setStdTable(tableDef.getStatement());
        return this;
    }

    public TdChainInsertIntoTableDef tags(Object...values){
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        ListColumnStatement listColumnStatement=new ListColumnStatement(",");
        for(Object value:values){
            listColumnStatement.add(new FlexMarkInvokerStatement(value));
        }
        tdInsertStatement.setTags(listColumnStatement);
        return this;
    }
}

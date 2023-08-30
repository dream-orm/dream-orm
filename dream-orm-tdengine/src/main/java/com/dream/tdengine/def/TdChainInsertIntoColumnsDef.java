package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.invoker.FlexMarkInvokerStatement;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdInsertStatement;

public class TdChainInsertIntoColumnsDef extends AbstractTdChainInsertDef implements InsertIntoColumnsDef<TdChainInsertIntoValuesDef, TdChainInsertDef> {
    public TdChainInsertIntoColumnsDef(InsertStatement statement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }

    public TdChainInsertIntoColumnsDef using(TableDef tableDef) {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        tdInsertStatement.setStdTable(tableDef.getStatement());
        return this;
    }

    public TdChainInsertIntoColumnsDef using(String table) {
        return using(FunctionDef.table(table));
    }

    public TdChainInsertIntoColumnsDef tags(Object... values) {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (Object value : values) {
            listColumnStatement.add(new FlexMarkInvokerStatement(value));
        }
        tdInsertStatement.setTags(listColumnStatement);
        return this;
    }

    @Override
    public TdChainInsertDef file(String file) {
        return super.file(file);
    }
}

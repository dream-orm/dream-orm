package com.dream.tdengine.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.flex.statement.TakeMarkInvokerStatement;
import com.dream.tdengine.statement.TdInsertStatement;

import java.util.Arrays;

public class TdChainInsertIntoTableDef extends AbstractTdChainInsertDef implements InsertIntoTableDef<TdChainInsertIntoColumnsDef> {
    public TdChainInsertIntoTableDef(FlexInsertFactory creatorFactory, FlexMapper flexMapper) {
        super(new TdInsertStatement(), creatorFactory, flexMapper);
    }

    public TdChainInsertIntoTableDef using(TableDef tableDef) {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        tdInsertStatement.setStdTable(tableDef.getStatement());
        return this;
    }

    public TdChainInsertIntoTableDef using(String table) {
        return using(FunctionDef.table(table));
    }

    public TdChainInsertIntoTableDef tags(Object... values) {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(Arrays.stream(values).map(value -> new TakeMarkInvokerStatement(null, value)).toArray(Statement[]::new));
        tdInsertStatement.setTags(listColumnStatement);
        return this;
    }

    @Override
    public TdChainInsertDef file(String file) {
        return super.file(file);
    }
}

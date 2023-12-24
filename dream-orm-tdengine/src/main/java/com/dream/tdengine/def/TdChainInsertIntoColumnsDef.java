package com.dream.tdengine.def;

import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.invoker.FlexMarkInvokerStatement;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdInsertStatement;

import java.util.*;

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

    public TdChainInsertIntoColumnsDef tags(Collection<Object> values) {
        return tags(values.toArray());
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

    public TdChainInsertIntoColumnsDef tagMap(Map<String, Object> tagMap) {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        Set<Map.Entry<String, Object>> entrySet = tagMap.entrySet();
        ListColumnStatement tagColumnStatement = new ListColumnStatement(",");
        ListColumnStatement tagValueStatement = new ListColumnStatement(",");
        for (Map.Entry<String, Object> entry : entrySet) {
            tagColumnStatement.add(new SymbolStatement.SingleMarkStatement(entry.getKey()));
            tagValueStatement.add(new FlexMarkInvokerStatement(entry.getValue()));
        }
        tdInsertStatement.setTagColumn(new BraceStatement(tagColumnStatement));
        tdInsertStatement.setTags(tagValueStatement);
        return this;
    }

    @Override
    public TdChainInsertDef file(String file) {
        return super.file(file);
    }

    public TdChainInsertIntoValuesDef columns(String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        for (int i = 0; i < columnDefs.length; i++) {
            columnDefs[i] = FunctionDef.column(columns[i]);
        }
        return columns(columnDefs);
    }

    public TdChainInsertDef valuesStrMap(Map<String, Object> valueStrMap) {
        Map<ColumnDef, Object> valueMap = new HashMap<>(valueStrMap.size());
        Iterator<Map.Entry<String, Object>> iterator = valueStrMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            valueMap.put(FunctionDef.column(entry.getKey()), entry.getValue());
        }
        return valuesMap(valueMap);
    }
}

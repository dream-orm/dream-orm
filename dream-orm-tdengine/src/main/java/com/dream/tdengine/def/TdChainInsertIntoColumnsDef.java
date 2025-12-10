package com.dream.tdengine.def;

import com.dream.antlr.smt.*;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.flex.statement.TakeMarkInvokerStatement;
import com.dream.tdengine.statement.TdInsertStatement;

import java.util.*;

public class TdChainInsertIntoColumnsDef extends AbstractTdChainInsertDef implements InsertIntoColumnsDef<TdChainInsertIntoValuesDef, TdChainInsertDef> {
    public TdChainInsertIntoColumnsDef(InsertStatement statement, FlexInsertFactory creatorFactory, FlexMapper flexMapper) {
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
        listColumnStatement.add(Arrays.stream(values).map(value -> new TakeMarkInvokerStatement(null, value)).toArray(Statement[]::new));
        tdInsertStatement.setTags(listColumnStatement);
        return this;
    }

    public TdChainInsertIntoColumnsDef tagMap(Map<String, Object> tagMap) {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) statement();
        Set<Map.Entry<String, Object>> entrySet = tagMap.entrySet();
        ListColumnStatement tagColumnStatement = new ListColumnStatement(",");
        ListColumnStatement tagValueStatement = new ListColumnStatement(",");
        Statement[] columns = new Statement[entrySet.size()];
        Statement[] values = new Statement[entrySet.size()];
        int index = 0;
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            columns[index] = new SymbolStatement.SingleMarkStatement(key);
            values[index] = new TakeMarkInvokerStatement(key, entry.getValue());
            index++;
        }
        tagColumnStatement.add(columns);
        tagValueStatement.add(values);
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
        Map<ColumnDef, Object> valueMap = new LinkedHashMap<>(valueStrMap.size());
        Iterator<Map.Entry<String, Object>> iterator = valueStrMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            valueMap.put(FunctionDef.column(entry.getKey()), entry.getValue());
        }
        return valuesMap(valueMap);
    }
}

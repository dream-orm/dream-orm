package com.dream.flex.def;

import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public interface InsertIntoColumnsDef<InsertDefIntoValues extends InsertIntoValuesDef<Insert>, Insert extends InsertDef> extends InsertIntoValuesDef<Insert>, InsertDef {
    default InsertDefIntoValues columns(List<ColumnDef> columnDefList) {
        return columns(columnDefList.toArray(new ColumnDef[columnDefList.size()]));
    }

    default InsertDefIntoValues columns(ColumnDef... columnDefs) {
        ListColumnStatement paramsListStatement = new ListColumnStatement(",");
        Statement[] statements = new Statement[columnDefs.length];
        for (int i = 0; i < columnDefs.length; i++) {
            ColumnDef columnDef = columnDefs[i];
            Statement statement = columnDef.getStatement();
            if (statement instanceof ListColumnStatement) {
                Statement[] columnList = ((ListColumnStatement) statement).getColumnList();
                statement = columnList[columnList.length - 1];
            }
            statements[i] = statement;
        }
        paramsListStatement.add(statements);
        statement().setColumns(new BraceStatement(paramsListStatement));
        return (InsertDefIntoValues) creatorFactory().newInsertIntoValuesDef(statement());
    }

    default Insert valuesMap(Map<ColumnDef, Object> valueMap) {
        ColumnDef[] columnDefs = new ColumnDef[valueMap.size()];
        Object[] values = new Object[valueMap.size()];
        Iterator<Map.Entry<ColumnDef, Object>> iterator = valueMap.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<ColumnDef, Object> entry = iterator.next();
            columnDefs[index] = entry.getKey();
            values[index] = entry.getValue();
            index++;
        }
        return columns(columnDefs).values(values);
    }
}

package com.dream.flex.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SelectStatement;
import com.dream.antlr.smt.SymbolStatement;

public interface QueryDef<Select extends SelectDef<From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate>, From extends FromDef<From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate>, Where extends WhereDef, GroupBy extends GroupByDef, Having extends HavingDef, OrderBy extends OrderByDef, Limit extends LimitDef, Union extends UnionDef, ForUpdate extends ForUpdateDef> extends Query {

    default Select select(ColumnDef... columnDefs) {
        return select(false, columnDefs);
    }

    default Select select(boolean distinct, ColumnDef... columnDefs) {
        SelectStatement selectStatement = new SelectStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if (columnDefs != null && columnDefs.length > 0) {
            for (ColumnDef columnDef : columnDefs) {
                listColumnStatement.add(columnDef.getStatement());
            }
        } else {
            listColumnStatement.add(new SymbolStatement.LetterStatement("*"));
        }
        selectStatement.setDistinct(distinct);
        selectStatement.setSelectList(listColumnStatement);
        statement().setSelectStatement(selectStatement);
        return (Select) creatorFactory().newSelectDef(statement());
    }
}

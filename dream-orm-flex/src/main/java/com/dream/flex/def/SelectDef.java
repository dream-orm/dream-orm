package com.dream.flex.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SelectStatement;
import com.dream.antlr.smt.SymbolStatement;

public interface SelectDef<
        From extends FromDef<Where, Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Where extends WhereDef<Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Group extends GroupByDef<Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingDef<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByDef<Limit, Union, ForUpdate, Query>,
        Limit extends LimitDef<Union, ForUpdate, Query>,
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends QueryDef {

    default From select(ColumnDef... columnDefs) {
        return select(false, columnDefs);
    }

    default From select(boolean distinct, ColumnDef... columnDefs) {
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
        return (From) creatorFactory().newFromDef(statement());
    }
}

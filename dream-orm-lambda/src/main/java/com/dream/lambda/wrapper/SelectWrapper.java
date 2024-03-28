package com.dream.lambda.wrapper;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SelectStatement;
import com.dream.antlr.smt.SymbolStatement;

public interface SelectWrapper<
        From extends FromWrapper<Where, Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Where extends WhereWrapper<Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Group extends GroupByWrapper<Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingWrapper<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByWrapper<Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends QueryWrapper {

    default From select(String... columns) {
        SelectStatement selectStatement = new SelectStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if (columns != null && columns.length > 0) {
            for (String column : columns) {
                listColumnStatement.add(new SymbolStatement.LetterStatement(column));
            }
        } else {
            listColumnStatement.add(new SymbolStatement.LetterStatement("*"));
        }
        selectStatement.setSelectList(listColumnStatement);
        statement().setSelectStatement(selectStatement);
        return (From) creatorFactory().newFromWrapper(statement());
    }
}

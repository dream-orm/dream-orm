package com.dream.stream.wrapper;

import com.dream.antlr.smt.GroupStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

import java.util.Arrays;

public interface GroupByWrapper<T,
        Having extends HavingWrapper<T, OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByWrapper<T, Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<T, Union, ForUpdate, Query>,
        Union extends UnionWrapper<T, ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<T, Query>,
        Query extends QueryWrapper<T>> extends HavingWrapper<T, OrderBy, Limit, Union, ForUpdate, Query> {
    default Having groupBy(String... columns) {
        GroupStatement groupStatement = new GroupStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(Arrays.stream(columns).map(SymbolStatement.LetterStatement::new).toArray(Statement[]::new));
        groupStatement.setGroup(listColumnStatement);
        statement().setGroupStatement(groupStatement);
        return (Having) creatorFactory().newHavingWrapper(entityType(), statement());
    }
}

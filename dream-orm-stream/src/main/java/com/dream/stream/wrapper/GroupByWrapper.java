package com.dream.stream.wrapper;

import com.dream.antlr.smt.GroupStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SymbolStatement;

public interface GroupByWrapper<
        Having extends HavingWrapper<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByWrapper<Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends HavingWrapper<OrderBy, Limit, Union, ForUpdate, Query> {
    default Having groupBy(String... columns) {
        GroupStatement groupStatement = new GroupStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (String column : columns) {
            listColumnStatement.add(new SymbolStatement.SingleMarkStatement(column));
        }
        groupStatement.setGroup(listColumnStatement);
        statement().setGroupStatement(groupStatement);
        return (Having) creatorFactory().newHavingWrapper(statement());
    }
}

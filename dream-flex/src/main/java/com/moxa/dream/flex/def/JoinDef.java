package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;

public class JoinDef<T extends FromDef> {
    private T fromDef;
    private JoinStatement joinStatement;

    protected JoinDef(T fromDef, QueryStatement queryStatement, JoinStatement joinStatement, AliasStatement joinTableStatement) {
        this.fromDef = fromDef;
        this.joinStatement = joinStatement;
        joinStatement.setJoinTable(joinTableStatement);
        FromStatement fromStatement = queryStatement.getFromStatement();
        Statement joinList = fromStatement.getJoinList();
        if (joinList == null) {
            joinList = new ListColumnStatement(" ");
            fromStatement.setJoinList(joinList);
        }
        ((ListColumnStatement) joinList).add(joinStatement);
    }

    public T on(ConditionDef conditionDef) {
        joinStatement.setOn(conditionDef.getStatement());
        return fromDef;
    }

}

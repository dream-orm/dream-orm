package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;

public interface FromDef<From extends FromDef, Where extends WhereDef> extends WhereDef {


    default JoinDef leftJoin(TableDef tableDef) {
        return new JoinDef(this, statement(), new JoinStatement.LeftJoinStatement(), tableDef.getStatement());
    }

    default JoinDef rightJoin(TableDef tableDef) {
        return new JoinDef(this,statement(), new JoinStatement.RightJoinStatement(), tableDef.getStatement());
    }

    default JoinDef innerJoin(TableDef tableDef) {
        return new JoinDef(this, statement(), new JoinStatement.InnerJoinStatement(), tableDef.getStatement());
    }

    default From crossJoin(TableDef tableDef) {
        JoinStatement.CrossJoinStatement joinStatement = new JoinStatement.CrossJoinStatement();
        joinStatement.setJoinTable(tableDef.getStatement());
        FromStatement fromStatement = statement().getFromStatement();
        Statement joinList = fromStatement.getJoinList();
        if (joinList == null) {
            joinList = new ListColumnStatement(" ");
            fromStatement.setJoinList(joinList);
        }
        ((ListColumnStatement) joinList).add(joinStatement);
        return (From) this;
    }

    default Where where(ConditionDef conditionDef) {
        ConditionStatement conditionStatement = conditionDef.getStatement();
        if (conditionStatement != null) {
            WhereStatement whereStatement = new WhereStatement();
            whereStatement.setCondition(conditionDef.getStatement());
            statement().setWhereStatement(whereStatement);
        }
        return (Where) queryCreatorFactory().newWhereDef(statement());
    }
}

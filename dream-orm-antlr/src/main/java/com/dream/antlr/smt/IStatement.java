package com.dream.antlr.smt;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public interface IStatement {
    String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException;
}

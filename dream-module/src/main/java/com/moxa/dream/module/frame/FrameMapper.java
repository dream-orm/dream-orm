package com.moxa.dream.module.frame;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.module.annotation.Sql;

public interface FrameMapper {
    @Sql("@"+AntlrInvokerFactory.CALL+":"+AntlrInvokerFactory.NAMESPACE+"("+ FrameConstant.SELECT_BY_ID+")")
    <T> T selectById(T view);
    @Sql("@"+AntlrInvokerFactory.CALL+":"+AntlrInvokerFactory.NAMESPACE+"("+ FrameConstant.UPDATE_BY_ID+")")
    <T> int updateById(T view);
    @Sql("@"+AntlrInvokerFactory.CALL+":"+AntlrInvokerFactory.NAMESPACE+"("+ FrameConstant.INSERT+")")
    <T> int insert(T view);
    @Sql("@"+AntlrInvokerFactory.CALL+":"+AntlrInvokerFactory.NAMESPACE+"("+ FrameConstant.DELETE_BY_ID+")")
    <T> int deleteById(T view);

}

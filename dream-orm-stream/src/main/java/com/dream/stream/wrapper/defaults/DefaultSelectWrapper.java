package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.FromStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.SelectStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractWhereConditionQueryWrapper;
import com.dream.stream.wrapper.SelectWrapper;
import com.dream.struct.invoker.TakeColumnInvokerStatement;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;

public class DefaultSelectWrapper<T> extends AbstractWhereConditionQueryWrapper<T, DefaultSelectWrapper<T>> implements SelectWrapper<T, DefaultFromWrapper<T>, DefaultWhereWrapper<T>, DefaultGroupByWrapper<T>, DefaultHavingWrapper<T>, DefaultOrderByWrapper<T>, DefaultLimitWrapper<T>, DefaultUnionWrapper<T>, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {
    public DefaultSelectWrapper(Class<T> entityType, StreamQueryFactory creatorFactory) {
        super(entityType, new QueryStatement(), creatorFactory);
        if (entityType == null) {
            throw new DreamRunTimeException("参数'entityType'不能为空");
        }
        String tableName = SystemUtil.getTableName(entityType);
        if (ObjectUtil.isNull(tableName)) {
            throw new DreamRunTimeException(entityType.getName() + "未绑定表");
        }
        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(new SymbolStatement.SingleMarkStatement(tableName));
        statement().setFromStatement(fromStatement);
        SelectStatement selectStatement = new SelectStatement();
        selectStatement.setSelectList(AntlrUtil.listColumnStatement(",", new TakeColumnInvokerStatement(entityType)));
        statement().setSelectStatement(selectStatement);
    }
}

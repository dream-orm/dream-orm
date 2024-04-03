package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.FromStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.SelectStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.struct.invoker.TakeColumnInvokerStatement;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.wrap.factory.WrapQueryFactory;
import com.dream.wrap.wrapper.AbstractWhereConditionQueryWrapper;
import com.dream.wrap.wrapper.SelectWrapper;

public class DefaultSelectWrapper extends AbstractWhereConditionQueryWrapper<DefaultSelectWrapper> implements SelectWrapper<DefaultFromWrapper, DefaultWhereWrapper, DefaultGroupByWrapper, DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultSelectWrapper(Class<?> entityType, WrapQueryFactory creatorFactory) {
        super(new QueryStatement(), creatorFactory);
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

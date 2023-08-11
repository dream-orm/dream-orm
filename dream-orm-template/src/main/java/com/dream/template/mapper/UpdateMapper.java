package com.dream.template.mapper;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.config.Command;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.template.annotation.WrapType;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class UpdateMapper extends WrapMapper {
    private final int CODE = 2;


    public UpdateMapper(Session session) {
        super(session);

    }

    @Override
    protected MethodInfo getWrapMethodInfo(Configuration configuration, TableInfo tableInfo, List<Field> fieldList, Object arg) {
        String table = tableInfo.getTable();
        List<String> setList = new ArrayList<>();
        if (!ObjectUtil.isNull(fieldList)) {
            List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
            Set<String> primKeyNameSet = null;
            if (primKeys != null && !primKeys.isEmpty()) {
                primKeyNameSet = primKeys.stream().map(primKey -> primKey.getName()).collect(Collectors.toSet());
            }
            for (Field field : fieldList) {
                if (primKeyNameSet == null || !primKeyNameSet.contains(field.getName())) {
                    String name = field.getName();
                    ColumnInfo columnInfo = tableInfo.getColumnInfo(name);
                    if (columnInfo != null) {
                        setList.add(columnInfo.getColumn() + "=" +
                                AntlrUtil.invokerSQL(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, DREAM_TEMPLATE_PARAM + "." + columnInfo.getName()));
                    }
                }
            }
        }
        String updateParam = getUpdateParam(setList);
        String other = getOther(configuration, tableInfo, arg);
        String sql = "update " + table + " set " + updateParam + " " + other;
        return new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(NonCollection.class)
                .setColType(Integer.class)
                .setSql(sql);
    }

    @Override
    protected boolean accept(WrapType wrapType) {
        return (CODE & wrapType.getCode()) > 0;
    }

    protected abstract String getOther(Configuration configuration, TableInfo tableInfo, Object arg);

    protected String getUpdateParam(List<String> setList) {
        return String.join(",", setList);
    }

    @Override
    protected Command getCommand() {
        return Command.UPDATE;
    }
}

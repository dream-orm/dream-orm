package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.annotation.WrapType;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class UpdateMapper extends WrapMapper {
    protected String param = "param";
    private int CODE = 2;

    public UpdateMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo doGetMethodInfo(Configuration configuration, TableInfo tableInfo, List<Field> fieldList, Object arg) {
        String table = tableInfo.getTable();
        List<String> setList = new ArrayList<>();
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                if (!tableInfo.getPrimColumnInfo().getName().equals(field.getName())) {
                    String name = field.getName();
                    ColumnInfo columnInfo = tableInfo.getColumnInfo(name);
                    if (columnInfo != null) {
                        setList.add(columnInfo.getColumn() + "=" +
                                InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", param + "." + columnInfo.getName()));
                    }
                }
            }
        }
        String updateParam = getUpdateParam(setList);
        String other = getOther(configuration, tableInfo, arg);
        String sql = "update " + table + " set " + updateParam + " " + other;
        return new MethodInfo.Builder(configuration)
                .rowType(NonCollection.class)
                .colType(Integer.class)
                .sql(sql)
                .build();
    }

    @Override
    protected boolean accept(WrapType wrapType) {
        return (CODE & wrapType.getCode()) > 0;
    }

    protected abstract String getOther(Configuration configuration, TableInfo tableInfo, Object arg);

    protected String getUpdateParam(List<String> setList) {
        return String.join(",", setList);
    }
}

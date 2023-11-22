package com.dream.helloworld.h2;

import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.ConditionDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.config.Command;
import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.listener.Listener;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.util.common.ObjectUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dream.flex.def.FunctionDef.column;
import static com.dream.flex.def.FunctionDef.select;

public class DataAuditListener implements Listener {
    @Override
    public void before(MappedStatement mappedStatement) {
        if (Command.UPDATE == mappedStatement.getCommand()) {
            Set<String> tableSet = mappedStatement.getTableSet();
            String table = tableSet.toArray(new String[1])[0];
            TableFactory tableFactory = mappedStatement.getConfiguration().getTableFactory();
            TableInfo tableInfo = tableFactory.getTableInfo(table);
            if (tableInfo != null) {
                List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
                if (!ObjectUtil.isNull(primKeys)) {
                    Map<String, Object> newValueMap = new HashMap<>();
                    List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
                    if (!ObjectUtil.isNull(mappedParamList)) {
                        for (MappedParam mappedParam : mappedParamList) {
                            String paramName = mappedParam.getParamName();
                            if (!ObjectUtil.isNull(paramName)) {
                                int index = paramName.lastIndexOf(".");
                                if (index > 0) {
                                    paramName = paramName.substring(index + 1);
                                }
                                ColumnInfo columnInfo = tableInfo.getColumnInfo(paramName);
                                if (columnInfo != null) {
                                    newValueMap.put(columnInfo.getColumn(), mappedParam.getParamValue());
                                }
                            } else {
                                return;
                            }
                        }
                        ConditionDef conditionDef = null;
                        for (ColumnInfo columnInfo : primKeys) {
                            Object value = newValueMap.remove(columnInfo.getColumn());
                            if (value == null) {
                                throw new RuntimeException("主键更新，主键值不能为空");
                            }
                            if (conditionDef == null) {
                                conditionDef = column(columnInfo.getColumn()).eq(value);
                            } else {
                                conditionDef = conditionDef.and(column(columnInfo.getColumn()).eq(value));
                            }
                        }
                        ColumnDef[] columnDefs = newValueMap.keySet().stream().map(column -> column(column)).collect(Collectors.toList()).toArray(new ColumnDef[newValueMap.size()]);
                        FlexMapper flexMapper = SpringUtil.getBean(FlexMapper.class);
                        QueryDef queryDef = select(columnDefs).from(FunctionDef.table(table)).where(conditionDef);
                        Map oldValueMap = flexMapper.selectOne(queryDef, Map.class);
                        System.out.println("=====数据更新=====");
                        System.out.println("操作表：" + table);
                        System.out.println("旧值：" + oldValueMap);
                        System.out.println("新值：" + newValueMap);
                    }
                }
            }
        }
    }

    @Override
    public void afterReturn(Object result, MappedStatement mappedStatement) {
        System.out.println();
    }

    @Override
    public void exception(Throwable e, MappedStatement mappedStatement) {
        System.out.println();
    }
}

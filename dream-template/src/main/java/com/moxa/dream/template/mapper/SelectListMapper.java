package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.template.condition.Condition;
import com.moxa.dream.template.util.ConditionObject;
import com.moxa.dream.template.util.SortObject;
import com.moxa.dream.template.util.TemplateUtil;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.*;
import java.util.stream.Collectors;

public class SelectListMapper extends SelectMapper {
    protected Session session;

    public SelectListMapper(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    protected String getOther(Configuration configuration, TableInfo tableInfo, Object arg) {
        if (arg != null) {
            Class<?> argType = arg.getClass();
            Set<String> tableNameSet = TemplateUtil.getTableNameSet(argType);
            TableFactory tableFactory = configuration.getTableFactory();
            String where = getWhereSql(argType, tableNameSet, tableFactory);
            String order = getOrderSql(argType, tableNameSet, tableFactory);
            return where + order;
        }
        return "";
    }

    protected Class<? extends Collection> getRowType() {
        return List.class;
    }

    protected String getWhereSql(Class type, Set<String> tableSet, TableFactory tableFactory) {
        List<ConditionObject> conditionObjectList = TemplateUtil.getCondition(type);
        if (!ObjectUtil.isNull(conditionObjectList)) {
            Map<Boolean, List<ConditionObject>> booleanConditionObjectListMap = conditionObjectList.stream().collect(Collectors.groupingBy(conditionObject -> conditionObject.isFilterNull()));
            List<ConditionObject> conditionObjectFalseList = booleanConditionObjectListMap.get(false);
            List<ConditionObject> conditionObjectTrueList = booleanConditionObjectListMap.get(true);
            String whereFalseSql = getWhereSql(tableSet, tableFactory, conditionObjectFalseList);
            String whereTrueSql = getWhereSql(tableSet, tableFactory, conditionObjectTrueList);
            String whereSql = " where ";
            if (!ObjectUtil.isNull(whereFalseSql)) {
                whereSql = whereSql + whereFalseSql;
            }
            if (!ObjectUtil.isNull(whereTrueSql)) {
                whereTrueSql = InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.NOT, ",", whereTrueSql);
                if (!ObjectUtil.isNull(whereFalseSql)) {
                    whereSql = whereSql + " and " + whereTrueSql;
                } else {
                    whereSql = whereSql + whereTrueSql;
                }
            }
            return whereSql;
        }
        return null;
    }

    protected String getWhereSql(Set<String> tableSet, TableFactory tableFactory, List<ConditionObject> conditionObjectList) {
        List<String> conditionList = new ArrayList<>();
        if (!ObjectUtil.isNull(conditionObjectList)) {
            for (ConditionObject conditionObject : conditionObjectList) {
                String table = conditionObject.getTable();
                String property = conditionObject.getProperty();
                Condition condition = conditionObject.getCondition();
                if (!ObjectUtil.isNull(table)) {
                    if (!tableSet.contains(table)) {
                        throw new DreamRunTimeException("条件表名限定" + tableSet);
                    }
                    TableInfo tableInfo = tableFactory.getTableInfo(table);
                    String fieldName = tableInfo.getFieldName(property);
                    ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                    String conditionSql = condition.getCondition(tableInfo.getTable(), columnInfo.getColumn(), property);
                    conditionList.add(conditionSql);
                } else {
                    List<TableInfo> tableInfoList = tableSet.stream().map(tab -> {
                        TableInfo tableInfo = tableFactory.getTableInfo(tab);
                        return tableInfo;
                    }).filter(tableInfo -> {
                        String fieldName = tableInfo.getFieldName(conditionObject.getProperty());
                        if (fieldName == null) {
                            return false;
                        } else {
                            return true;
                        }
                    }).collect(Collectors.toList());
                    if (ObjectUtil.isNull(tableInfoList)) {
                        throw new DreamRunTimeException("条件字段" + conditionObject.getProperty() + "在" + tableSet + "对应的类未注册");
                    }
                    if (tableInfoList.size() > 1) {
                        throw new DreamRunTimeException("条件字段" + conditionObject.getProperty() + "在" + tableInfoList.stream().map(tableInfo -> tableInfo.getTable()).collect(Collectors.toList()) + "对应的类都存在，请指定具体表名");
                    }
                    TableInfo tableInfo = tableInfoList.get(0);
                    String fieldName = tableInfo.getFieldName(property);
                    ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                    String conditionSql = condition.getCondition(tableInfo.getTable(), columnInfo.getColumn(), property);
                    conditionList.add(conditionSql);
                }
            }
        }
        if (!ObjectUtil.isNull(conditionList)) {
            return String.join(" and ", conditionList);
        }
        return null;
    }

    protected String getOrderSql(Class type, Set<String> tableSet, TableFactory tableFactory) {
        List<SortObject> sortObjectList = TemplateUtil.getSort(type);
        List<String> orderList = new ArrayList<>();
        if (!ObjectUtil.isNull(sortObjectList)) {
            for (SortObject sortObject : sortObjectList) {
                String table = sortObject.getTable();
                if (!ObjectUtil.isNull(table)) {
                    if (!tableSet.contains(table)) {
                        throw new DreamRunTimeException("排序表名限定" + tableSet);
                    } else {
                        TableInfo tableInfo = tableFactory.getTableInfo(table);
                        String fieldName = tableInfo.getFieldName(sortObject.getProperty());
                        orderList.add(table + "." + tableInfo.getColumnInfo(fieldName).getColumn() + " " + sortObject.getOrderType());
                    }
                } else {
                    List<TableInfo> tableInfoList = tableSet.stream().map(tab -> {
                        TableInfo tableInfo = tableFactory.getTableInfo(tab);
                        return tableInfo;
                    }).filter(tableInfo -> {
                        String fieldName = tableInfo.getFieldName(sortObject.getProperty());
                        if (fieldName == null) {
                            return false;
                        } else {
                            return true;
                        }
                    }).collect(Collectors.toList());
                    if (tableInfoList == null) {
                        throw new DreamRunTimeException("排序字段" + sortObject.getProperty() + "在" + tableSet + "对应的类未注册");
                    }
                    if (tableInfoList.size() > 1) {
                        throw new DreamRunTimeException("排序字段" + sortObject.getProperty() + "在" + tableInfoList.stream().map(tableInfo -> tableInfo.getTable()).collect(Collectors.toList()) + "对应的类都存在，请指定具体表名");
                    }
                    TableInfo tableInfo = tableInfoList.get(0);
                    orderList.add(tableInfo.getColumnInfo(tableInfo.getFieldName(sortObject.getProperty())).getColumn() + " " + sortObject.getOrderType());
                }
            }
        }
        if (!ObjectUtil.isNull(orderList)) {
            return " order by " + String.join(",", orderList);
        } else {
            return "";
        }
    }
}


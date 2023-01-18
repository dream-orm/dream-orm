package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.antlr.invoker.NotInvoker;
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

    public SelectListMapper(Session session) {
        super(session);
    }

    @Override
    protected String getOther(Configuration configuration, TableInfo tableInfo, Class<?> type, Object arg) {
        if (arg != null) {
            Class<?> argType = arg.getClass();
            Set<String> tableNameSet = TemplateUtil.getTableNameSet(type);
            TableFactory tableFactory = configuration.getTableFactory();
            String where = getWhereSql(argType, tableNameSet, tableFactory);
            String order = getOrderSql(argType, tableNameSet, tableFactory);
            return where + order;
        }
        return "";
    }

    @Override
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
                whereTrueSql = AntlrUtil.invokerSQL(NotInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, whereTrueSql);
                if (!ObjectUtil.isNull(whereFalseSql)) {
                    whereSql = whereSql + " and " + whereTrueSql;
                } else {
                    whereSql = whereSql + whereTrueSql;
                }
            }
            return whereSql;
        }
        return "";
    }

    protected String getWhereSql(Set<String> tableSet, TableFactory tableFactory, List<ConditionObject> conditionObjectList) {
        List<String> andConditionList = new ArrayList<>();
        List<String> orConditionList = new ArrayList<>();
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
                    if (conditionObject.isOr()) {
                        orConditionList.add(conditionSql);
                    } else {
                        andConditionList.add(conditionSql);
                    }
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
                    if (conditionObject.isOr()) {
                        orConditionList.add(conditionSql);
                    } else {
                        andConditionList.add(conditionSql);
                    }
                }
            }
        }
        String orSql = "";
        String andSql = "";
        if (!ObjectUtil.isNull(orConditionList)) {
            orSql = "(" + String.join(" or ", orConditionList) + ")";
        }
        if (!ObjectUtil.isNull(andConditionList)) {
            andSql = String.join(" and ", andConditionList);
            if (!"".equals(orSql)) {
                andSql = "and " + andSql;
            }
        }
        return orSql + andSql;
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


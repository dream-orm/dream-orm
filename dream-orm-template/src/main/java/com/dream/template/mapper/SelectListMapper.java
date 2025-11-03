package com.dream.template.mapper;

import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.NotInvoker;
import com.dream.system.config.Configuration;
import com.dream.system.core.session.Session;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.template.condition.Condition;
import com.dream.template.util.ConditionObject;
import com.dream.template.util.SortObject;
import com.dream.template.util.TemplateUtil;
import com.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelectListMapper extends SelectMapper {

    public SelectListMapper(Session session) {
        super(session);
    }

    @Override
    protected String getOther(Configuration configuration, TableInfo tableInfo, Class<?> type, Object arg) {
        if (arg != null) {
            Class<?> argType = arg.getClass();
            String tableName = SystemUtil.getTableName(type);
            TableFactory tableFactory = configuration.getTableFactory();
            String where = getWhereSql(argType, tableName, tableFactory);
            String order = getOrderSql(argType, tableName, tableFactory);
            return where + order;
        }
        return "";
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return List.class;
    }

    protected String getWhereSql(Class type, String tableName, TableFactory tableFactory) {
        List<ConditionObject> conditionObjectList = TemplateUtil.getCondition(type);
        if (!ObjectUtil.isNull(conditionObjectList)) {
            Map<Boolean, List<ConditionObject>> booleanConditionObjectListMap = conditionObjectList.stream().collect(Collectors.groupingBy(conditionObject -> conditionObject.isFilterNull()));
            List<ConditionObject> conditionObjectFalseList = booleanConditionObjectListMap.get(false);
            List<ConditionObject> conditionObjectTrueList = booleanConditionObjectListMap.get(true);
            String whereFalseSql = getWhereSql(tableName, tableFactory, conditionObjectFalseList);
            String whereTrueSql = getWhereSql(tableName, tableFactory, conditionObjectTrueList);
            String whereSql = " where ";
            if (!ObjectUtil.isNull(whereFalseSql)) {
                whereSql = whereSql + whereFalseSql;
            }
            if (!ObjectUtil.isNull(whereTrueSql)) {
                whereTrueSql = AntlrUtil.invokerSQL(NotInvoker.FUNCTION, whereTrueSql);
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

    protected String getWhereSql(String tableName, TableFactory tableFactory, List<ConditionObject> conditionObjectList) {
        List<String> andConditionList = new ArrayList<>();
        List<String> orConditionList = new ArrayList<>();
        if (!ObjectUtil.isNull(conditionObjectList)) {
            for (ConditionObject conditionObject : conditionObjectList) {
                String column = conditionObject.getColumn();
                Condition condition = conditionObject.getCondition();
                TableInfo tableInfo = tableFactory.getTableInfo(tableName);
                ColumnInfo columnInfo = tableInfo.getColumnInfo(column);
                String conditionSql = condition.getCondition(columnInfo.getColumn(), conditionObject.getField());
                if (conditionObject.isOr()) {
                    orConditionList.add(conditionSql);
                } else {
                    andConditionList.add(conditionSql);
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

    protected String getOrderSql(Class type, String tableName, TableFactory tableFactory) {
        List<SortObject> sortObjectList = TemplateUtil.getSort(type);
        List<String> orderList = new ArrayList<>();
        if (!ObjectUtil.isNull(sortObjectList)) {
            for (SortObject sortObject : sortObjectList) {
                TableInfo tableInfo = tableFactory.getTableInfo(tableName);
                String property = sortObject.getProperty();
                ColumnInfo columnInfo = tableInfo.getColumnInfo(property);
                if (columnInfo == null) {
                    orderList.add(property + " " + sortObject.getOrderType());
                } else {
                    orderList.add(SystemUtil.key(tableName) + "." + SystemUtil.key(columnInfo.getColumn()) + " " + sortObject.getOrderType());
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


package com.dream.template.util;

public class SortObject implements Comparable<SortObject> {
    private String table;
    private String property;
    private String orderType;
    private int order;

    public SortObject(String table, String property, String orderType, int order) {
        this.table = table;
        this.property = property;
        this.orderType = orderType;
        this.order = order;
    }

    public String getTable() {
        return table;
    }

    public String getProperty() {
        return property;
    }

    public String getOrderType() {
        return orderType;
    }

    @Override
    public int compareTo(SortObject o) {
        return this.order - o.order;
    }
}

package com.dream.template.util;

public class SortObject implements Comparable<SortObject> {
    private String property;
    private String orderType;
    private int order;

    public SortObject(String property, String orderType, int order) {
        this.property = property;
        this.orderType = orderType;
        this.order = order;
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

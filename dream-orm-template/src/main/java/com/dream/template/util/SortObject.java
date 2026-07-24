package com.dream.template.util;

public class SortObject {
    private String property;
    private String orderType;

    public SortObject(String property, String orderType) {
        this.property = property;
        this.orderType = orderType;
    }

    public String getProperty() {
        return property;
    }

    public String getOrderType() {
        return orderType;
    }
}

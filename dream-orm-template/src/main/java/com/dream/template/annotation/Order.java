package com.dream.template.annotation;

public enum Order {
    ASC("asc"),
    DESC("desc");
    private String orderType;

    Order(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }
}

package com.dream.helloworld.h2.condition;

import com.dream.template.annotation.Conditional;
import com.dream.template.annotation.Order;
import com.dream.template.annotation.Sort;
import com.dream.template.condition.ContainsCondition;
import com.dream.template.condition.GeqCondition;
import com.dream.template.condition.LeqCondition;

public class OrderAccountCondition {
    @Conditional(ContainsCondition.class)
    @Sort(value = Order.ASC, order = 1)
    private String name;
    @Conditional(value = GeqCondition.class, column = "age")
    private Integer minAge;
    @Conditional(value = LeqCondition.class, column = "age")
    private Integer maxAge;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }
}

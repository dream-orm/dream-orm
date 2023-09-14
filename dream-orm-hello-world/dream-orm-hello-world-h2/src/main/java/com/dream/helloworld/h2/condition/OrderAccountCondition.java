package com.dream.helloworld.h2.condition;

import com.dream.template.annotation.Conditional;
import com.dream.template.annotation.Order;
import com.dream.template.annotation.Sort;
import com.dream.template.condition.ContainsCondition;

public class OrderAccountCondition {
    @Conditional(ContainsCondition.class)
    @Sort(value = Order.ASC, order = 1)
    private String name;
    @Sort(value = Order.DESC, order = 0)
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

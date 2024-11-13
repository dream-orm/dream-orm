package com.dream.helloworld.h2.condition;

import com.dream.template.annotation.Conditional;
import com.dream.template.condition.BetweenCondition;
import com.dream.template.condition.ContainsCondition;
import com.dream.template.condition.InCondition;

import java.util.List;

public class AccountCondition {
    @Conditional(value = ContainsCondition.class)
    private String name;

    @Conditional(value = InCondition.class)
    private List<Integer> age;
    @Conditional(value = BetweenCondition.class, column = "age")
    private Integer[] ages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAge() {
        return age;
    }

    public void setAge(List<Integer> age) {
        this.age = age;
    }

    public Integer[] getAges() {
        return ages;
    }

    public void setAges(Integer[] ages) {
        this.ages = ages;
    }
}

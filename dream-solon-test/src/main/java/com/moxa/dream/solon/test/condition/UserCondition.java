package com.moxa.dream.solon.test.condition;

import com.moxa.dream.template.annotation.Conditional;
import com.moxa.dream.template.condition.InCondition;
import com.moxa.dream.template.condition.StartWithCondition;

import java.util.List;

public class UserCondition {
    @Conditional(table = "user", value = StartWithCondition.class)
    private String name;

    @Conditional(value = InCondition.class)
    private List<Integer> age;

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
}

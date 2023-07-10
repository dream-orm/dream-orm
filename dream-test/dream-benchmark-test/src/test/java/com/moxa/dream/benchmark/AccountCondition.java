package com.moxa.dream.benchmark;

import com.moxa.dream.system.annotation.View;
import com.moxa.dream.template.annotation.Conditional;
import com.moxa.dream.template.condition.EqCondition;
import com.moxa.dream.template.condition.GeqCondition;

@View(Account.class)
public class AccountCondition {
    @Conditional(value = GeqCondition.class, or = true)
    private Long id;
    @Conditional(value = EqCondition.class, or = true)
    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

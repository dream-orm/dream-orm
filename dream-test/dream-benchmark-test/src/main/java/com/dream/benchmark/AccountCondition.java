package com.dream.benchmark;

import com.dream.system.annotation.View;
import com.dream.template.annotation.Conditional;
import com.dream.template.condition.EqCondition;
import com.dream.template.condition.GeqCondition;

@View(Account.class)
public class AccountCondition {
    @Conditional(value = GeqCondition.class, or = true, nullFlag = false)
    private Long id;
    @Conditional(value = EqCondition.class, or = true, nullFlag = false)
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

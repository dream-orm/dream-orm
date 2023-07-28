package com.moxa.dream.solon.test.view;

import com.moxa.dream.solon.test.table.User;
import com.moxa.dream.system.annotation.View;

@View(User.class)
public class UserView2 {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

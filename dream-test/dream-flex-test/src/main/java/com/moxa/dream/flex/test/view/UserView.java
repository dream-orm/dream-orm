package com.moxa.dream.flex.test.view;

import com.moxa.dream.flex.test.table.User;
import com.moxa.dream.system.annotation.View;

@View(User.class)
public class UserView {
    private Integer id;
    private String name;
    private String email;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

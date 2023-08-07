package com.dream.base.view;

import com.dream.base.table.Blog;
import com.dream.base.table.User;
import com.dream.system.annotation.Ignore;
import com.dream.system.annotation.View;

import java.util.List;

@View(User.class)
public class UserView {
    private Integer id;
    private String name;
    @Ignore
    private String email;
    @Ignore
    private List<Blog> blogList;

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

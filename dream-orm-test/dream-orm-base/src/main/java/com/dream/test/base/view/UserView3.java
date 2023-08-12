package com.dream.test.base.view;

import com.dream.system.annotation.View;
import com.dream.test.base.table.User;

import java.util.List;

@View(User.class)
public class UserView3 {
    private Integer id;
    private String name;
    private List<BlogView> blogList;

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

    public List<BlogView> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<BlogView> blogList) {
        this.blogList = blogList;
    }
}

package com.moxa.dream.solon.test.view;

import com.moxa.dream.solon.test.table.Blog;
import com.moxa.dream.system.annotation.View;

@View(Blog.class)
public class BlogView {
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

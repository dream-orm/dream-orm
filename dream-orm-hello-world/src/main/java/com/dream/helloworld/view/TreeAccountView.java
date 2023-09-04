package com.dream.helloworld.view;

import com.dream.helloworld.table.Account;
import com.dream.system.annotation.View;
import com.dream.util.tree.Tree;

import java.util.ArrayList;
import java.util.List;

@View(Account.class)
public class TreeAccountView implements Tree<Integer> {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private List<TreeAccountView> children;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "TreeAccountView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public Integer getTreeId() {
        return id;
    }

    @Override
    public Integer getParentId() {
        return id - 1;
    }

    public void setChildren(List<TreeAccountView> children) {
        this.children = children;
    }

    public List<TreeAccountView> getChildren() {
        return children;
    }

    @Override
    public List<? extends Tree> getChildrenOrNew() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }
}

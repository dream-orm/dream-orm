package com.dream.helloworld.h2.view;

import com.dream.helloworld.h2.table.Account;
import com.dream.system.annotation.View;

@View(Account.class)
public class AccountView {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private String ap;

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getAp() {
        return ap;
    }

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
        return "AccountView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}

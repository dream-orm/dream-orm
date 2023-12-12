package com.dream.helloworld.h2.view;

import com.dream.helloworld.h2.table.Account;
import com.dream.system.annotation.Fetch;
import com.dream.system.annotation.View;

@View(Account.class)
public class AccountView {
    private Integer id;
    @Fetch(sql = "select concat(name,' fetch test') from account where id=:row.id")
    private String name;
    private Integer age;
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

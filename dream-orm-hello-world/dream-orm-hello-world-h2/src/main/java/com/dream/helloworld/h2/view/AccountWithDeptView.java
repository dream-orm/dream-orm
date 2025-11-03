package com.dream.helloworld.h2.view;

import com.dream.helloworld.h2.table.Account;
import com.dream.system.annotation.Fetch;
import com.dream.system.annotation.View;

@View(Account.class)
public class AccountWithDeptView {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private Integer deptId;
    private String deptName;
    @Fetch("select name from dept where id=@?(deptId)")
    private String deptName2;

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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName2() {
        return deptName2;
    }

    public void setDeptName2(String deptName2) {
        this.deptName2 = deptName2;
    }

    @Override
    public String toString() {
        return "AccountWithDeptView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", deptName2='" + deptName2 + '\'' +
                '}';
    }
}

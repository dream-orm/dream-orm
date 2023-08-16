package com.dream.helloworld.view;

import com.dream.helloworld.table.Account;
import com.dream.system.annotation.View;
import com.dream.template.annotation.validate.Length;
import com.dream.template.annotation.validate.NotBlank;
import com.dream.template.annotation.validate.NotNull;
import com.dream.template.annotation.validate.Unique;

@View(Account.class)
public class ValidatedAccountView {
    @Unique(msg = "数据库已经存在该字段")
    private Integer id;
    @NotBlank(msg = "名称不能为空")
    @Length(min = 5,max = 20,msg = "名称长度必须在【5,20】之间")
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
        return "ValidatedAccountView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}

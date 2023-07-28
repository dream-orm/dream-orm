package com.moxa.dream.solon.test.view;

import com.moxa.dream.solon.test.RandomExtractor;
import com.moxa.dream.solon.test.table.User;
import com.moxa.dream.system.annotation.Extract;
import com.moxa.dream.system.annotation.View;
import com.moxa.dream.template.annotation.Wrap;
import com.moxa.dream.template.annotation.WrapType;
import com.moxa.dream.template.wrap.ZeroWrapper;

@View(User.class)
public class UserView4 {
    private Integer id;
    @Extract(RandomExtractor.class)
    private String name;
    private String email;
    @Wrap(value = ZeroWrapper.class, type = WrapType.INSERT)
    private Integer delFlag;

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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "UserView4{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}

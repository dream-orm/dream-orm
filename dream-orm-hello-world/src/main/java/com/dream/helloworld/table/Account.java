package com.dream.helloworld.table;

import com.dream.system.annotation.Column;
import com.dream.system.annotation.Id;
import com.dream.system.annotation.Table;

@Table("account")
public class Account {
    @Id
    @Column("id")
    private Integer id;
    @Column(value = "name")
    private String name;
    @Column("age")
    private Integer age;
    @Column("email")
    private String email;
    @Column("tenant_id")
    private Integer tenantId;
    @Column("dept_id")
    private Integer deptId;
    @Column("del_flag")
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

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}

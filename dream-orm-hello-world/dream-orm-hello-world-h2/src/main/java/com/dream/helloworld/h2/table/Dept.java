package com.dream.helloworld.h2.table;

import com.dream.system.annotation.Column;
import com.dream.system.annotation.Id;
import com.dream.system.annotation.Table;

@Table("dept")
public class Dept {
    @Id
    @Column("id")
    private Integer id;
    @Column(value = "name")
    private String name;
    @Column("tenant_id")
    private Integer tenantId;
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

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}

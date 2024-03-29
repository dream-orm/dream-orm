package com.dream.helloworld.solon.table;

import com.dream.system.annotation.Column;
import com.dream.system.annotation.Id;
import com.dream.system.annotation.Table;

import java.util.Date;

@Table("blog")
public class Blog {
    @Id
    @Column("id")
    private Integer id;
    @Column("name")
    private String name;
    @Column("publish_time")
    private Date publishTime;
    @Column("user_id")
    private Integer userId;
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

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

package org.example.dreamormhellospringboot.table;

import com.dream.system.annotation.Column;
import com.dream.system.annotation.Id;
import com.dream.system.annotation.Table;
import lombok.Data;

import java.sql.Types;
import java.util.Date;

/**
 * @Description: 用户信息表
 * @Author: moxa
 * @Date: 2023-09-04 09:42:38
 */
@Data
@Table("sys_user")
public class SysUser {


    /**
     * 用户ID
     */
    @Id
    @Column(value = "id", jdbcType = Types.BIGINT)
    private Long id;

    /**
     * 租户编号
     */
    @Column(value = "tenant_id", jdbcType = Types.VARCHAR)
    private String tenantId;

    /**
     * 部门ID
     */
    @Column(value = "dept_id", jdbcType = Types.BIGINT)
    private Long deptId;

    /**
     * 用户账号
     */
    @Column(value = "user_name", jdbcType = Types.VARCHAR)
    private String userName;

    /**
     * 用户昵称
     */
    @Column(value = "nick_name", jdbcType = Types.VARCHAR)
    private String nickName;

    /**
     * 用户头像
     */
    @Column(value = "avatar", jdbcType = Types.VARCHAR)
    private String avatar;

    /**
     * 用户类型（sys_user系统用户）
     */
    @Column(value = "user_type", jdbcType = Types.VARCHAR)
    private String userType;

    /**
     * 用户邮箱
     */
    @Column(value = "email", jdbcType = Types.VARCHAR)
    private String email;

    /**
     * 手机号码
     */
    @Column(value = "phone", jdbcType = Types.VARCHAR)
    private String phone;

    /**
     * 用户性别（0男 1女）
     */
    @Column(value = "sex", jdbcType = Types.INTEGER)
    private Integer sex;

    /**
     * 密码
     */
    @Column(value = "password", jdbcType = Types.VARCHAR)
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Column(value = "status", jdbcType = Types.INTEGER)
    private Integer status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @Column(value = "del_flag", jdbcType = Types.INTEGER)
    private Integer delFlag;

    /**
     * 最后登录IP
     */
    @Column(value = "login_ip", jdbcType = Types.VARCHAR)
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Column(value = "login_date", jdbcType = Types.TIMESTAMP)
    private java.util.Date loginDate;

    /**
     * 创建者
     */
    @Column(value = "create_by", jdbcType = Types.BIGINT)
    private Long createBy;

    /**
     * 创建时间
     */
    @Column(value = "create_time", jdbcType = Types.TIMESTAMP)
    private java.util.Date createTime;

    /**
     * 更新者
     */
    @Column(value = "update_by", jdbcType = Types.BIGINT)
    private Long updateBy;

    /**
     * 更新时间
     */
    @Column(value = "update_time", jdbcType = Types.TIMESTAMP)
    private java.util.Date updateTime;

    /**
     * 备注
     */
    @Column(value = "remark", jdbcType = Types.VARCHAR)
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

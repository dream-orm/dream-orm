package org.example.dreamormhellospringboot.view;

import com.dream.system.annotation.View;
import com.dream.template.annotation.Conditional;
import com.dream.template.condition.ContainsCondition;
import com.dream.template.condition.InCondition;
import lombok.Data;
import org.example.dreamormhellospringboot.table.SysUser;

import java.util.List;

@Data
@View(SysUser.class)
public class SysUserDto {
    private Long deptId;
    @Conditional(value = ContainsCondition.class)
    private String userName;
    @Conditional(value = ContainsCondition.class)
    private String nickName;
    @Conditional(value = ContainsCondition.class)
    private String phone;
    @Conditional(value = InCondition.class, column = "dept_id")
    private List<Long> deptIds;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Long> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Long> deptIds) {
        this.deptIds = deptIds;
    }
}

package org.example.dreamormhellospringboot3.mapper;

import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.Param;
import com.dream.system.annotation.Sql;
import org.example.dreamormhellospringboot3.view.SysUserVo;

import java.util.List;

@Mapper
public interface SysUserMapper {

    @Sql("update sys_user set login_ip=@?(loginIp),login_date=now() where user_name=@?(userName)")
    int updateLoginInfo(@Param("userName") String userName, @Param("loginIp") String loginIp);

    @Sql("select id,dept_id from sys_user where dept_id in (@foreach(deptIds))")
    List<SysUserVo> queryByDeptIds(@Param("deptIds") List<Long> deptIds);

    @Sql("select nick_name from sys_user where id=@?(id)")
    SysUserVo getUser(@Param("id") Long id);

    @Sql("select password from sys_user where id=@?(userId)")
    String getPwd(@Param("userId") Long userId);

    @Sql("update sys_user set password=@?(password) where id=@?(userId)")
    int updatePwd(@Param("userId") Long userId, @Param("password") String password);
}

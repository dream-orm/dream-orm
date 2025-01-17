package org.example.dreamormhellospringboot.service;

import com.dream.boot.impl.ServiceImpl;
import com.dream.jdbc.mapper.JdbcMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.dreamormhellospringboot.mapper.SysUserMapper;
import org.example.dreamormhellospringboot.view.SysUserBo;
import org.example.dreamormhellospringboot.view.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserVo, SysUserBo> implements ISysUserService {
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private JdbcMapper jdbcMapper;

    @Override
    public SysUserVo queryUserByUserName(String userName, String tenantId) {
        return jdbcMapper.selectOne(SysUserVo.class, "user_name=? and tenant_id=? and del_flag=0", userName, tenantId);
    }

    @Override
    public Integer updateLoginInfo(String userName, String loginIp) {
        return userMapper.updateLoginInfo(userName, loginIp);
    }


}

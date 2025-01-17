package org.example.dreamormhellospringboot.service;

import com.dream.template.service.IService;
import org.example.dreamormhellospringboot.view.SysUserBo;
import org.example.dreamormhellospringboot.view.SysUserVo;

public interface ISysUserService extends IService<SysUserVo, SysUserBo> {
    SysUserVo queryUserByUserName(String userName, String tenantId);

    Integer updateLoginInfo(String userName, String loginIp);
}

package org.example.dreamormhellospringboot3.service;

import com.dream.template.service.IService;
import org.example.dreamormhellospringboot3.view.SysUserBo;
import org.example.dreamormhellospringboot3.view.SysUserVo;

public interface ISysUserService extends IService<SysUserVo, SysUserBo> {
    SysUserVo queryUserByUserName(String userName, String tenantId);

    Integer updateLoginInfo(String userName, String loginIp);
}

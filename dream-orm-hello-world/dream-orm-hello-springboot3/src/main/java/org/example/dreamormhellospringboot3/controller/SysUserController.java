package org.example.dreamormhellospringboot3.controller;

import com.dream.system.config.Page;
import lombok.RequiredArgsConstructor;
import org.example.dreamormhellospringboot3.model.PageModel;
import org.example.dreamormhellospringboot3.model.R;
import org.example.dreamormhellospringboot3.service.ISysUserService;
import org.example.dreamormhellospringboot3.view.SysUserBo;
import org.example.dreamormhellospringboot3.view.SysUserDto;
import org.example.dreamormhellospringboot3.view.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息
 *
 * @author moxa
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public R<Page<SysUserVo>> list(SysUserDto user, PageModel pageModel) {
        return R.ok(userService.selectPage(user, pageModel));
    }

    /**
     * 获取用户详细信息
     *
     * @param id 主键
     */
    @GetMapping("/query")
    public R<SysUserBo> query(@RequestParam Long id) {
        return R.ok(userService.selectById(id));
    }

    /**
     * 新增用户
     */
    @PostMapping("/add")
    public R<Void> add(@RequestBody SysUserBo user) {
        user.setStatus(0);
        return toAjax(userService.insert(user));
    }

    /**
     * 修改用户
     */
    @PutMapping("/edit")
    public R<Void> edit(@RequestBody SysUserBo user) {
        return toAjax(userService.updateById(user));
    }

    /**
     * 删除用户
     *
     * @param id 角色ID
     */
    @DeleteMapping("/remove")
    public R<Void> remove(@RequestParam Long id) {
        return toAjax(userService.deleteById(id));
    }
}

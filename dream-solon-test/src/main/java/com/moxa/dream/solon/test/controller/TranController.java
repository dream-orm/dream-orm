package com.moxa.dream.solon.test.controller;

import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.solon.test.mapper.UserMapper;
import com.moxa.dream.template.mapper.TemplateMapper;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.data.annotation.Tran;

@Mapping("/tran/")
@Controller
public class TranController {
    @Inject
    UserMapper userMapper;

    @Inject
    TemplateMapper templateMapper;


    @Inject
    FlexMapper flexMapper;


    @Tran
    @Mapping("test")
    public void test() throws Exception {
        userMapper.findAll();
    }

}

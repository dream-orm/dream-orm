package com.dream.drive.factory;

import com.dream.flex.mapper.FlexMapper;
import com.dream.system.core.session.Session;
import com.dream.template.mapper.TemplateMapper;

public interface DriveFactory {
    /**
     * session会话
     *
     * @return session会话
     */
    Session session();

    /**
     * 模板操作接口
     *
     * @return 模板操作接口
     */
    TemplateMapper templateMapper();

    /**
     * Flex操作接口
     *
     * @return Flex操作接口
     */
    FlexMapper flexMapper();
}

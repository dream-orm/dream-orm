package com.dream.drive.factory;

import com.dream.flex.mapper.FlexMapper;
import com.dream.jdbc.mapper.JdbcMapper;
import com.dream.stream.mapper.StreamMapper;
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

    /**
     * Stream操作接口
     *
     * @return Stream操作接口
     */
    StreamMapper streamMapper();

    /**
     * 不翻译直接执行SQL接口
     *
     * @return 不翻译直接执行SQL接口
     */
    JdbcMapper jdbcMapper();
}

package com.dream.drive.factory;

import com.dream.chain.mapper.FlexChainMapper;
import com.dream.flex.mapper.FlexMapper;
import com.dream.jdbc.mapper.JdbcMapper;
import com.dream.system.core.session.Session;
import com.dream.template.mapper.TemplateMapper;
import com.dream.template.session.SessionTemplate;

public interface DriveFactory {
    /**
     * session会话
     * @return session会话
     */
    Session session();

    /**
     * 模板操作接口
     * @return 模板操作接口
     */
    TemplateMapper templateMapper();

    /**
     * 链式操作接口
     * @return 链式操作接口
     */
    FlexMapper flexMapper();

    /**
     * 链式强化接口
     * @return 链式强化接口
     */
    FlexChainMapper flexChainMapper();

    /**
     * 不翻译直接执行SQL接口
     * @return 不翻译直接执行SQL接口
     */
    JdbcMapper jdbcMapper();
}

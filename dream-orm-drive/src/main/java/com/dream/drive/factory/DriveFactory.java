package com.dream.drive.factory;

import com.dream.chain.mapper.FlexChainMapper;
import com.dream.flex.mapper.FlexMapper;
import com.dream.jdbc.mapper.JdbcMapper;
import com.dream.template.mapper.TemplateMapper;
import com.dream.template.session.SessionTemplate;

public interface DriveFactory {
    SessionTemplate sessionTemplate();

    TemplateMapper templateMapper();

    FlexMapper flexMapper();

    FlexChainMapper flexChainMapper();

    JdbcMapper jdbcMapper();

}

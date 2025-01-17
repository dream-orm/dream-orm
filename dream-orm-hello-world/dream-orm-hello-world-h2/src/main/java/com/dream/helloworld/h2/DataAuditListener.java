package com.dream.helloworld.h2;

import com.dream.drive.listener.FlexAuditListener;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.config.Command;
import com.dream.system.table.TableInfo;

import java.util.List;
import java.util.Map;

public class DataAuditListener extends FlexAuditListener {

    @Override
    protected FlexMapper flexMapper() {
        return SpringUtil.getBean(FlexMapper.class);
    }

    @Override
    protected void handle(Command command, TableInfo tableInfo, Map<String, Object> primValueMap, List<AuditColumn> auditColumnList) {
        System.out.println(command);
        System.out.println(auditColumnList);
    }
}

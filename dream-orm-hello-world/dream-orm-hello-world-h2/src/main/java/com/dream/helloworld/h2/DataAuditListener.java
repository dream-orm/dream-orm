package com.dream.helloworld.h2;

import com.dream.drive.listener.AbstractAuditListener;
import com.dream.system.config.Command;
import com.dream.system.core.session.Session;
import com.dream.system.table.TableInfo;

import java.util.List;
import java.util.Map;

public class DataAuditListener extends AbstractAuditListener {
    @Override
    protected void handle(Command command, TableInfo tableInfo, Map<String, Object> primValueMap, List<AuditColumn> auditColumnList, Session session) {
        System.out.println(command);
        System.out.println(auditColumnList);
    }
}

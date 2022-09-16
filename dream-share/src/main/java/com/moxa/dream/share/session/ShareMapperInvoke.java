package com.moxa.dream.share.session;

import com.moxa.dream.share.annotation.DataSource;
import com.moxa.dream.share.holder.DataSourceHolder;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.mapper.MapperInvoke;

public class ShareMapperInvoke implements MapperInvoke {
    private Session session;

    public ShareMapperInvoke(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(MethodInfo methodInfo, Object arg) {
        String dataSourceName = null;
        DataSource dataSource = methodInfo.get(DataSource.class);
        if (dataSource != null) {
            dataSourceName = dataSource.value();
        }
        DataSourceHolder.set(dataSourceName);
        try {
            return session.execute(methodInfo, arg);
        } finally {
            DataSourceHolder.remove();
        }
    }
}

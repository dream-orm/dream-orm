package com.moxa.dream.mate.share.session;

import com.moxa.dream.mate.share.annotation.Share;
import com.moxa.dream.mate.share.holder.DataSourceHolder;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.mapper.MapperInvoke;

import java.util.Map;

public class ShareMapperInvoke implements MapperInvoke {
    private Session session;

    public ShareMapperInvoke(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(MethodInfo methodInfo, Map<String, Object> argMap) {
        String dataSourceName = null;
        Share share = methodInfo.get(Share.class);
        if (share != null) {
            dataSourceName = share.value();
        }
        DataSourceHolder.set(dataSourceName);
        try {
            return session.execute(methodInfo, argMap);
        } finally {
            DataSourceHolder.remove();
        }
    }
}

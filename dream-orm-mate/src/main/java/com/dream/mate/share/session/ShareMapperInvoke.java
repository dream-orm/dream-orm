package com.dream.mate.share.session;

import com.dream.mate.share.annotation.Share;
import com.dream.mate.share.holder.DataSourceHolder;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.mapper.MapperInvoke;

import java.lang.reflect.Method;
import java.util.Map;

public class ShareMapperInvoke implements MapperInvoke {
    private Session session;

    public ShareMapperInvoke(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(MethodInfo methodInfo, Map<String, Object> argMap, Class<?> type) {
        if (DataSourceHolder.get() == null) {
            String dataSourceName = null;
            Share share = null;
            Method method = methodInfo.getMethod();
            if (method != null) {
                share = method.getAnnotation(Share.class);
            }
            if (share == null) {
                share = type.getAnnotation(Share.class);
            }
            if (share != null) {
                dataSourceName = share.value();
            }
            Object result = DataSourceHolder.use(dataSourceName, () -> session.execute(methodInfo, argMap));
            return result;
        } else {
            return session.execute(methodInfo, argMap);
        }
    }
}

package com.dream.solon.share;

import com.dream.drive.config.DreamProperties;
import com.dream.mate.share.datasource.ShareDataSource;
import com.dream.mate.share.listener.ShardListener;
import com.dream.system.core.listener.Listener;
import com.dream.system.core.listener.factory.ListenerFactory;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.wrap.ClassWrap;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfiguration implements Plugin {
    @org.noear.solon.annotation.Inject("${dream}")
    private DreamProperties dreamProperties;

    @Bean
    public DataSource dataSource() {
        EnableShare enableShare = Solon.app().source().getAnnotation(EnableShare.class);
        Class<? extends DataSource> dataSourceType = enableShare.value();
        Map<String, Map<String, Object>> datasource = dreamProperties.getDatasource();
        if (datasource == null || datasource.isEmpty()) {
            throw new DreamRunTimeException("数据源未配置");
        }
        Map<String, DataSource> dataSourceMap = new HashMap<>(4);
        datasource.forEach((k, v) -> {
            Properties properties = new Properties();
            properties.putAll(v);
            Object value = ClassWrap.get(dataSourceType).newBy(properties);
            dataSourceMap.put(k, (DataSource) value);
        });
        return new ShareDataSource(dataSourceMap);
    }

    @Override
    public void start(AppContext context) throws Throwable {
        context.getBeanAsync(ListenerFactory.class, listenerFactory -> {
            Listener[] listeners = listenerFactory.getListeners();
            if (!ObjectUtil.isNull(listeners)) {
                for (Listener listener : listeners) {
                    if (listener instanceof ShardListener) {
                        return;
                    }
                }
            }
            listeners = ObjectUtil.merge(listeners, new Listener[]{new ShardListener()}).toArray(new Listener[0]);
            listenerFactory.listeners(listeners);
        });
    }
}

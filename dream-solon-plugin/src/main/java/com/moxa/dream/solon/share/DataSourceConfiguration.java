package com.moxa.dream.solon.share;

import com.moxa.dream.mate.share.datasource.ShareDataSource;
import com.moxa.dream.mate.share.session.ShareMapperInvokerFactory;
import com.moxa.dream.solon.plugin.DreamProperties;
import com.moxa.dream.system.mapper.MapperInvokeFactory;
import com.moxa.dream.util.exception.DreamRunTimeException;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.core.wrap.ClassWrap;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfiguration {
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
            v.forEach((key, value) -> {
                properties.put(key, value);
            });
            Object value = ClassWrap.get(dataSourceType).newBy(properties);
            dataSourceMap.put(k, (DataSource) value);
        });
        return new ShareDataSource(dataSourceMap);
    }

    @Bean
    public MapperInvokeFactory mapperInvokeFactory() {
        return new ShareMapperInvokerFactory();
    }
}

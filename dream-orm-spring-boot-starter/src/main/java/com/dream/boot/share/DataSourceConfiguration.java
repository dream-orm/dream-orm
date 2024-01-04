package com.dream.boot.share;

import com.dream.boot.autoconfigure.DreamProperties;
import com.dream.mate.share.datasource.ShareDataSource;
import com.dream.mate.share.session.ShareMapperInvokerFactory;
import com.dream.mate.share.trategy.DefaultShardStrategy;
import com.dream.mate.share.trategy.ShardStrategy;
import com.dream.system.mapper.MapperInvokeFactory;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceConfiguration implements ImportAware {
    private Class<? extends DataSource> dataSourceType;


    @Bean
    public DataSource dataSource(DreamProperties dreamProperties) {
        Map<String, Map<String, Object>> datasource = dreamProperties.getDatasource();
        if (datasource == null || datasource.isEmpty()) {
            throw new DreamRunTimeException("数据源未配置");
        }
        Map<String, DataSource> dataSourceMap = new HashMap<>(4);
        datasource.forEach((k, v) -> {
            DataSource dataSource = ReflectUtil.create(dataSourceType);
            BeanMap beanMap = BeanMap.create(dataSource);
            beanMap.putAll(v);
            dataSourceMap.put(k, dataSource);
        });
        return new ShareDataSource(dataSourceMap);
    }

    @Bean
    @ConditionalOnMissingBean
    public ShardStrategy shardStrategy() {
        return new DefaultShardStrategy();
    }

    @Bean
    public MapperInvokeFactory mapperInvokeFactory(ShardStrategy shardStrategy) {
        return new ShareMapperInvokerFactory(shardStrategy);
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> annotationAttributes = importMetadata.getAnnotationAttributes(EnableShare.class.getName());
        this.dataSourceType = (Class<? extends DataSource>) annotationAttributes.get("value");
    }
}

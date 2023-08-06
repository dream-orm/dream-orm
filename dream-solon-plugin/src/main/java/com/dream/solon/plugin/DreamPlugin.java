package com.dream.solon.plugin;

import com.dream.solon.bean.ConfigurationBean;
import com.dream.system.config.Configuration;
import com.dream.system.mapper.MapperFactory;
import com.dream.template.session.SessionTemplate;
import org.noear.solon.core.AopContext;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Plugin;

import java.util.Collection;

public class DreamPlugin implements Plugin {
    @Override
    public void start(AopContext context) throws Throwable {
        context.getBeanAsync(ConfigurationBean.class, configurationBean -> {
            Configuration configuration = configurationBean.getObject();
            BeanWrap beanWrap = new BeanWrap(context, Configuration.class, configuration);
            context.putWrap(Configuration.class, beanWrap);
            context.beanScan(DreamAutoConfiguration.class);
        });
        context.getBeanAsync(SessionTemplate.class, sessionTemplate -> {
            Configuration configuration = context.getBean(Configuration.class);
            MapperFactory mapperFactory = configuration.getMapperFactory();
            Collection<Class> mapperTypeList = mapperFactory.getMapperTypeList();
            if (mapperTypeList != null && !mapperTypeList.isEmpty()) {
                for (Class mapperType : mapperTypeList) {
                    BeanWrap beanWrap = new BeanWrap(context, mapperType, sessionTemplate.getMapper(mapperType));
                    context.putWrap(mapperType, beanWrap);
                }
            }
        });
    }


}

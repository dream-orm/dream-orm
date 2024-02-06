package com.dream.solon.plugin;

import com.dream.solon.bean.ConfigurationBean;
import com.dream.system.config.Configuration;
import com.dream.system.mapper.MapperFactory;
import com.dream.template.session.SessionTemplate;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Plugin;

import java.util.Collection;
import java.util.List;

public class DreamPlugin implements Plugin {
    @Override
    public void start(AppContext context) {
        context.lifecycle(() -> {
            if (context.hasWrap(ConfigurationBean.class) == false) {
                context.putWrap(ConfigurationBean.class, new BeanWrap(context, ConfigurationBean.class, new ConfigurationBean()));
            }
        });
        context.getBeanAsync(ConfigurationBean.class, configurationBean -> {
            List<String> tablePackages = context.cfg().getList("dream.tablePackages");
            List<String> mapperPackages = context.cfg().getList("dream.mapperPackages");
            configurationBean.addTablePackages(tablePackages);
            configurationBean.addMapperPackages(mapperPackages);
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

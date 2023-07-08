package com.moxa.dream.boot.bean;

import com.moxa.dream.drive.config.DefaultConfig;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.DefaultMapperFactory;
import com.moxa.dream.system.table.factory.DefaultTableFactory;
import com.moxa.dream.util.common.ObjectUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.Collection;
import java.util.List;

public class ConfigurationBean implements BeanDefinitionRegistryPostProcessor, FactoryBean<Configuration> {
    private DefaultConfig defaultConfig;
    private Configuration configuration;

    public ConfigurationBean(List<String> tablePackages, List<String> mapperPackages) {
        this.defaultConfig = this.initDefaultConfig(tablePackages, mapperPackages);
    }

    @Override
    public Configuration getObject() {
        return configuration;
    }

    @Override
    public Class<?> getObjectType() {
        return Configuration.class;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        configuration = defaultConfig.toConfiguration();
        Collection<Class> mapperTypeList = configuration
                .getMapperFactory()
                .getMapperTypeList();
        if (!ObjectUtil.isNull(mapperTypeList)) {
            for (Class mapperType : mapperTypeList) {
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(MapperFactoryBean.class);
                beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapperType);
                beanDefinitionRegistry.registerBeanDefinition(mapperType.getName(), beanDefinition);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    protected DefaultConfig initDefaultConfig(List<String> tablePackages, List<String> mapperPackages) {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig
                .setMapperFactory(new DefaultMapperFactory())
                .setTableFactory(new DefaultTableFactory());
        defaultConfig.setTablePackages(tablePackages);
        defaultConfig.setMapperPackages(mapperPackages);
        return defaultConfig;
    }
}

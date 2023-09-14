package com.dream.boot.bean;

import com.dream.drive.config.DefaultConfig;
import com.dream.system.config.Configuration;
import com.dream.system.mapper.DefaultMapperFactory;
import com.dream.system.table.factory.DefaultTableFactory;
import com.dream.util.common.ObjectUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfigurationBean implements BeanDefinitionRegistryPostProcessor, FactoryBean<Configuration>, EnvironmentAware {
    private Configuration configuration;
    private List<String> tablePackages = new ArrayList<>();
    private List<String> mapperPackages = new ArrayList<>();

    public ConfigurationBean() {
        this(null, null);
    }

    public ConfigurationBean(List<String> tablePackages, List<String> mapperPackages) {
        if (tablePackages != null) {
            this.tablePackages.addAll(tablePackages);
        }
        if (mapperPackages != null) {
            this.mapperPackages.addAll(mapperPackages);
        }
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
        DefaultConfig defaultConfig = this.defaultConfig(tablePackages, mapperPackages);
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

    protected DefaultConfig defaultConfig(List<String> tablePackages, List<String> mapperPackages) {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig
                .setMapperFactory(new DefaultMapperFactory())
                .setTableFactory(new DefaultTableFactory());
        defaultConfig.setTablePackages(tablePackages);
        defaultConfig.setMapperPackages(mapperPackages);
        return defaultConfig;
    }

    @Override
    public void setEnvironment(Environment environment) {
        int index = 0;
        while (true) {
            String tablePackage = environment.getProperty("dream.tablePackages[" + index + "]");
            String mapperPackage = environment.getProperty("dream.mapperPackages[" + index + "]");
            if (tablePackage == null && mapperPackage == null) {
                break;
            }
            if (tablePackage != null) {
                tablePackages.add(tablePackage);
            }
            if (mapperPackage != null) {
                mapperPackages.add(mapperPackage);
            }
            index++;
        }
    }
}

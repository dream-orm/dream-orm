package com.moxa.dream.solon.bean;

//import com.moxa.dream.template.session.SessionTemplate;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;


//public class MapperFactoryBean<T> implements FactoryBean<T> {
//    private Class<T> mapperInterface;
//    private SessionTemplate sessionTemplate;
//
//    public MapperFactoryBean(Class<T> mapperInterface) {
//        this.mapperInterface = mapperInterface;
//    }
//
//    @Override
//    public T getObject() {
//        return sessionTemplate.getMapper(this.mapperInterface);
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return this.mapperInterface;
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
//
//    @Autowired
//    public void setSessionTemplate(SessionTemplate sessionTemplate) {
//        this.sessionTemplate = sessionTemplate;
//    }
//}

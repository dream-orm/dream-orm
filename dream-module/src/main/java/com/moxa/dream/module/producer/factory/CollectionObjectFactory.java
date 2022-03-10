package com.moxa.dream.module.producer.factory;


import com.moxa.dream.module.producer.ProducerException;
import com.moxa.dream.module.producer.PropertyInfo;

import java.lang.reflect.Modifier;
import java.util.*;

public class CollectionObjectFactory implements ObjectFactory{
     Collection result;
     CollectionObjectFactory(){

     }
    public CollectionObjectFactory(Class<?extends Collection>type){
        int modifiers = type.getModifiers();
        if(Modifier.isInterface(modifiers)||Modifier.isAbstract(modifiers)){
                if (type.isAssignableFrom(ArrayList.class))
                    result =new ArrayList();
                else if (type.isAssignableFrom(HashSet.class)){
                    result=new HashSet<>();
                }else if(type.isAssignableFrom(LinkedList.class)){
                    result=new LinkedList();
                }else
                    throw new ProducerException("The class name '"+type+"' not find implementation class");
        }else{
            try {
                result = type.getConstructor().newInstance();
            }catch (Exception e){
                throw new ProducerException(e);
            }
        }
    }
    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        result.add(value);
    }

    @Override
    public Object get(PropertyInfo propertyInfo) {
        return null;
    }

    @Override
    public Object getObject() {
        return result;
    }
}

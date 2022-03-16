package com.moxa.dream.antlr.callback.factory;

import com.moxa.dream.antlr.callback.Callback;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.util.HashMap;
import java.util.Map;

public class CacheCallbackFactory implements CallbackFactory {
    private Map<String,Callback> callbackMap=new HashMap<>();
    @Override
    public Callback getCallback(String className) {
        Callback callback = callbackMap.get(className);
        if(callback==null){
            synchronized (callbackMap){
                if(callback==null){
                    callback=callbackMap.get(className);
                    if(callback==null){
                        Class<Callback>type = ReflectUtil.loadClass(className);
                        callback = ReflectUtil.create(type);
                        callbackMap.put(className,callback);
                    }
                }
            }
        }
        return callback;
    }
}

package com.moxa.dream.module.frame.callback;

import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.module.mapper.MethodInfo;

import java.util.HashMap;
import java.util.Map;

public class SelectByIdCallback {
    protected Map<Class,Statement> cacheMap=new HashMap<>();
    public Statement call(MethodInfo methodInfo,Object arg) {
        Class<?> type = arg.getClass();
        Statement statement = cacheMap.get(type);
        if(statement==null){
            synchronized (cacheMap){
                statement=cacheMap.get(type);
                if(statement==null){
                    

                }
            }
        }
        return statement;
    }
}

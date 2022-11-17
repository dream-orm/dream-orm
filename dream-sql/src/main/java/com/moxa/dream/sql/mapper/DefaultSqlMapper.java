package com.moxa.dream.sql.mapper;

import com.moxa.dream.sql.mock.DefaultMockCompileFactory;
import com.moxa.dream.sql.mock.MockCompileFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.ObjectMap;

import java.util.List;
import java.util.Map;

public  class DefaultSqlMapper implements SqlMapper {
    private MockCompileFactory mockCompileFactory;
    private Session session;

    public DefaultSqlMapper(Session session, Configuration configuration) {
        this(session, new DefaultMockCompileFactory(configuration));
    }

    public DefaultSqlMapper(Session session, MockCompileFactory mockCompileFactory) {
        this.session = session;
        this.mockCompileFactory = mockCompileFactory;
    }

    @Override
    public <T> List<T> selectList(String sql,Object param, Class<T> type, boolean cache, int timeOut) {
        if(param!=null&&!(param instanceof Map)){
            param=new ObjectMap(param);
        }
        MappedStatement mappedStatement = mockCompileFactory.compile(sql,param, List.class, type , cache, timeOut);
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public Integer execute(String sql, Object param) {
        MappedStatement mappedStatement = mockCompileFactory.compile(sql,param, NonCollection.class, Integer.class, false, 0);
        return (Integer) session.execute(mappedStatement);
    }
}

package com.dream.system.core.session;

import com.dream.system.action.ActionProvider;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.config.Page;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectMap;

import java.io.Closeable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Session extends Closeable {

    /**
     * 根据接口类型获取代理的对象
     *
     * @param type 接口
     * @param <T>  代理的对象
     * @return
     */
    <T> T getMapper(Class<T> type);

    /**
     * 查询一条数据
     *
     * @param sql  sql语句
     * @param arg  参数
     * @param type 返回类型
     * @param <T>
     * @return
     */
    default <T> T selectOne(String sql, Object arg, Class<T> type) {
        return (T) execute(new ActionProvider() {
            @Override
            public String sql() {
                return sql;
            }

            @Override
            public Class<? extends Collection> rowType() {
                return NonCollection.class;
            }

            @Override
            public Class<?> colType() {
                return type;
            }
        }, arg);
    }

    /**
     * 查询多条数据
     *
     * @param sql  sql语句
     * @param arg  参数
     * @param type 返回类型
     * @param <T>
     * @return
     */
    default <T> List<T> selectList(String sql, Object arg, Class<T> type) {
        return (List<T>) execute(new ActionProvider() {
            @Override
            public String sql() {
                return sql;
            }

            @Override
            public Class<? extends Collection> rowType() {
                return List.class;
            }

            @Override
            public Class<?> colType() {
                return type;
            }
        }, arg);
    }

    default <T> Page<T> selectPage(String sql, Object arg, Page<T> page, Class<T> type) {
        Map argMap;
        String PAGE = "page";
        if (arg == null) {
            arg = new HashMap<>();
        }
        if (arg instanceof Map) {
            argMap = (Map) arg;
        } else {
            argMap = new ObjectMap(arg);
        }
        argMap.put(PAGE, page);
        List<T> list = (List<T>) execute(new ActionProvider() {
            @Override
            public String sql() {
                return sql;
            }

            @Override
            public Class<? extends Collection> rowType() {
                return List.class;
            }

            @Override
            public Class<?> colType() {
                return type;
            }

            @Override
            public String page() {
                return PAGE;
            }
        }, argMap);
        page.setRows(list);
        return page;
    }

    /**
     * 执行sql
     *
     * @param sql sql语句
     * @param arg 参数
     * @return
     */
    default int execute(String sql, Object arg) {
        return (Integer) execute(new ActionProvider() {
            @Override
            public String sql() {
                return sql;
            }

            @Override
            public Class<? extends Collection> rowType() {
                return NonCollection.class;
            }

            @Override
            public Class<?> colType() {
                return Integer.class;
            }
        }, arg);
    }

    /**
     * 执行SQL操作
     *
     * @param actionProvider 映射接口方法与SQL
     * @param arg            参数
     * @return
     */
    Object execute(ActionProvider actionProvider, Object arg);


    /**
     * 执行SQL操作
     *
     * @param methodInfo 接口方法详尽信息
     * @param argMap     参数
     * @return
     */
    Object execute(MethodInfo methodInfo, Map<String, Object> argMap);

    /**
     * 执行SQL操作
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @return
     */
    Object execute(MappedStatement mappedStatement);

    /**
     * 返回是否自动提交
     *
     * @return
     */
    boolean isAutoCommit();

    /**
     * 提交
     */
    void commit();

    /**
     * 回滚
     */
    void rollback();

    /**
     * 关闭
     */
    @Override
    void close();

    /**
     * 返回配置
     *
     * @return
     */
    Configuration getConfiguration();
}


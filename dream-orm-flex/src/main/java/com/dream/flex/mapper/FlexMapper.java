package com.dream.flex.mapper;

import com.dream.flex.def.Delete;
import com.dream.flex.def.Insert;
import com.dream.flex.def.Query;
import com.dream.flex.def.Update;
import com.dream.system.config.Page;
import com.dream.system.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 链式操作类
 */
public interface FlexMapper {
    /**
     * 白名单，列如开启插件后，链式并不默认添加，需要在白名单额外增加
     */
    Set<Class<? extends Inject>> WHITE_SET = new HashSet<>();

    /**
     * 查询并返回一条
     *
     * @param query 查询定义器
     * @param type  返回类型
     * @param <T>
     * @return 单条数据
     */
    <T> T selectOne(Query query, Class<T> type);

    /**
     * 查询并返回集合
     *
     * @param query 查询定义器
     * @param type  返回类型
     * @param <T>
     * @return 集合数据
     */
    <T> List<T> selectList(Query query, Class<T> type);

    /**
     * 查询并返回分页
     *
     * @param query 查询定义器
     * @param type  返回类型
     * @param page  分页
     * @param <T>
     * @return 分页数据
     */
    <T> Page<T> selectPage(Query query, Class<T> type, Page page);

    /**
     * 更新操作
     *
     * @param update 更新定义器
     * @return 更新数量
     */
    int update(Update update);

    /**
     * 删除操作
     *
     * @param delete 删除定义器
     * @return 删除数量
     */
    int delete(Delete delete);

    /**
     * 插入操作
     *
     * @param insert 插入定义器
     * @return 插入数量
     */
    int insert(Insert insert);
}

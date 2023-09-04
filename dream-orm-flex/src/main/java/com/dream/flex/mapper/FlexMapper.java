package com.dream.flex.mapper;

import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;
import com.dream.system.config.Page;
import com.dream.system.inject.Inject;
import com.dream.util.tree.Tree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 链式操作接口
 */
public interface FlexMapper {
    /**
     * 白名单，列如开启插件后，链式并不默认添加，需要在白名单额外增加
     */
    Set<Class<? extends Inject>> WHITE_SET = new HashSet<>();

    /**
     * 查询并返回一条
     *
     * @param queryDef 查询定义器
     * @param type     返回类型
     * @param <T>
     * @return 单条数据
     */
    <T> T selectOne(QueryDef queryDef, Class<T> type);

    /**
     * 查询并返回集合
     *
     * @param queryDef 查询定义器
     * @param type     返回类型
     * @param <T>
     * @return 集合数据
     */
    <T> List<T> selectList(QueryDef queryDef, Class<T> type);

    /**
     * 查询树形结构
     * @param queryDef 查询定义器
     * @param type 返回类型
     * @param <T>
     * @return
     */
    <T extends Tree> List<T> selectTree(QueryDef queryDef, Class<T> type);
    /**
     * 查询并返回分页
     *
     * @param queryDef 查询定义器
     * @param type     返回类型
     * @param page     分页
     * @param <T>
     * @return 分页数据
     */
    <T> Page<T> selectPage(QueryDef queryDef, Class<T> type, Page page);

    /**
     * 更新操作
     *
     * @param updateDef 更新定义器
     * @return 更新数量
     */
    int update(UpdateDef updateDef);

    /**
     * 删除操作
     *
     * @param deleteDef 删除定义器
     * @return 删除数量
     */
    int delete(DeleteDef deleteDef);

    /**
     * 插入操作
     *
     * @param insertDef 插入定义器
     * @return 插入数量
     */
    int insert(InsertDef insertDef);

    /**
     * 数据是否存在
     *
     * @param queryDef
     * @return
     */
    boolean exists(QueryDef queryDef);
}

package com.dream.template.mapper;

import com.dream.system.config.Page;
import com.dream.util.tree.Tree;

import java.util.Collection;
import java.util.List;

/**
 * 模板操作接口
 */
public interface TemplateMapper {
    /**
     * 根据主键查询
     *
     * @param type 接收类型
     * @param id   主键值
     * @param <T>
     * @return
     */
    <T> T selectById(Class<T> type, Object id);

    /**
     * 根据主键集合查询
     *
     * @param type   接收类型
     * @param idList 主键值集合
     * @param <T>
     * @return
     */
    <T> List<T> selectByIds(Class<T> type, Collection<?> idList);

    /**
     * 根据对象生成注解条件，并查询一条
     *
     * @param type            接收类型
     * @param conditionObject 对象注解条件
     * @param <T>
     * @return
     */
    <T> T selectOne(Class<T> type, Object conditionObject);

    /**
     * 根据对象生成注解条件，并查询集合
     *
     * @param type            接收类型
     * @param conditionObject 对象注解条件
     * @param <T>
     * @return
     */
    <T> List<T> selectList(Class<T> type, Object conditionObject);

    /**
     * 根据对象生成注解条件，并查询树形结构
     *
     * @param type            接收类型
     * @param conditionObject 对象注解条件
     * @param <T>
     * @return
     */
    <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject);

    /**
     * 根据对象生成注解条件，并分页查询
     *
     * @param type            接收类型
     * @param conditionObject 对象注解条件
     * @param page            分页
     * @param <T>
     * @return
     */
    <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page);

    /**
     * 根据注解对象生成的条件，并统计数量
     *
     * @param type            java类型映射的表
     * @param conditionObject 注解对象条件
     * @return 数量
     */
    Integer selectCount(Class<?> type, Object conditionObject);

    /**
     * 根据主键更新
     *
     * @param view 更新对象
     * @return
     */
    int updateById(Object view);

    /**
     * 根据主键非空更新
     *
     * @param view 更新对象
     * @return
     */
    int updateNonById(Object view);

    /**
     * 插入数据
     *
     * @param view 插入对象
     * @return
     */
    int insert(Object view);

    /**
     * 插入数据并返回主键值
     *
     * @param view 插入对象
     * @return
     */
    Object insertFetchKey(Object view);

    /**
     * 根据主键删除
     *
     * @param type 删除java类型映射的表数据
     * @param id   主键值
     * @return
     */
    int deleteById(Class<?> type, Object id);

    /**
     * 根据主键删除
     *
     * @param view 删除对象，根据主键删除，同时可进行参数校验
     * @return
     */
    int delete(Object view);

    /**
     * 根据主键集合删除
     *
     * @param type   java类型映射的表
     * @param idList 主键值集合
     * @return
     */
    int deleteByIds(Class<?> type, Collection<?> idList);

    /**
     * 判断主键是否存在
     *
     * @param type java类型映射的表
     * @param id   主键值
     * @return
     */
    boolean existById(Class<?> type, Object id);

    /**
     * 根据注解对象生成的条件判断是否存在
     *
     * @param type            java类型映射的表
     * @param conditionObject 注解对象条件
     * @return 是否存在
     */
    boolean exist(Class<?> type, Object conditionObject);

    /**
     * 批量插入
     *
     * @param viewList 数据集合
     * @return
     */
    List<Object> batchInsert(Collection<?> viewList);

    /**
     * 批量更新
     *
     * @param viewList 数据集合
     * @return
     */
    List<Object> batchUpdateById(Collection<?> viewList);
}

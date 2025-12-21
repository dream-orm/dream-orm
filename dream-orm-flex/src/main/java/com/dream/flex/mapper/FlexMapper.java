package com.dream.flex.mapper;

import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;
import com.dream.system.config.Page;

import java.util.List;

/**
 * 链式操作接口
 */
public interface FlexMapper {
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
    int execute(UpdateDef updateDef);

    /**
     * 批量更新
     *
     * @param updateDefList 更新定义器集合
     * @return
     */
    default List<int[]> execute(UpdateDef[] updateDefList) {
        return execute(updateDefList, 1000);
    }

    /**
     * 批量插入
     *
     * @param updateDefList 更新定义器集合
     * @param batchSize     批量大小
     * @return
     */
    List<int[]> execute(UpdateDef[] updateDefList, int batchSize);

    /**
     * 删除操作
     *
     * @param deleteDef 删除定义器
     * @return 删除数量
     */
    int execute(DeleteDef deleteDef);

    /**
     * 插入操作
     *
     * @param insertDef 插入定义器
     * @return 插入数量
     */
    int execute(InsertDef insertDef);

    /**
     * 批量插入
     *
     * @param insertDefList 插入定义器集合
     * @return
     */
    default List<int[]> execute(InsertDef[] insertDefList) {
        return execute(insertDefList, 1000);
    }

    /**
     * 批量插入
     *
     * @param insertDefList 插入定义器集合
     * @param batchSize     批量大小
     * @return
     */
    List<int[]> execute(InsertDef[] insertDefList, int batchSize);

    /**
     * 数据是否存在
     *
     * @param queryDef
     * @return
     */
    boolean exists(QueryDef queryDef);
}

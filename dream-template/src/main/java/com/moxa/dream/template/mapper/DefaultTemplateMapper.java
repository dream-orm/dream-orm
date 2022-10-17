package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Page;
import com.moxa.dream.system.core.session.Session;

import java.util.List;

public class DefaultTemplateMapper implements TemplateMapper {
    private SqlMapper selectByIdSqlMapper;
    private SqlMapper selectByIdsSqlMapper;
    private SqlMapper selectListSqlMapper;
    private SqlMapper selectPageSqlMapper;
    private SqlMapper deleteByIdSqlMapper;
    private SqlMapper deleteByIdsSqlMapper;
    private SqlMapper existByIdSqlMapper;
    private SqlMapper updateByIdSqlMapper;
    private SqlMapper updateNonByIdSqlMapper;
    private SqlMapper insertSqlMapper;
    private SqlMapper insertBatchSqlMapper;
    private SqlMapper insertFetchKeySqlMapper;

    public DefaultTemplateMapper(Session session) {
        selectByIdSqlMapper = new SelectByIdSqlMapper(session);
        selectByIdsSqlMapper = new SelectByIdsSqlMapper(session);
        selectListSqlMapper = new SelectListSqlMapper(session);
        selectPageSqlMapper = new SelectPageSqlMapper(session);
        deleteByIdSqlMapper = new DeleteByIdSqlMapper(session);
        deleteByIdsSqlMapper = new DeleteByIdsSqlMapper(session);
        existByIdSqlMapper = new ExistByIdSqlMapper(session);
        updateByIdSqlMapper = new UpdateByIdSqlMapper(session);
        updateNonByIdSqlMapper = new UpdateNonByIdSqlMapper(session);
        insertSqlMapper = new InsertSqlMapper(session);
        insertBatchSqlMapper = new InsertBatchSqlMapper(session);
        insertFetchKeySqlMapper = new InsertFetchKeySqlMapper(session);
    }

    @Override
    public <T> T selectById(Class<T> type, Object id) {
        return (T) selectByIdSqlMapper.execute(type, id);
    }

    @Override
    public <T> List<T> selectByIds(Class<T> type, List<Object> idList) {
        return (List<T>) selectByIdsSqlMapper.execute(type, idList);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, Object conditionObject) {
        return (List<T>) selectListSqlMapper.execute(type, conditionObject);
    }

    @Override
    public <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page) {
        return (Page<T>) selectPageSqlMapper.execute(type, conditionObject, page);
    }

    @Override
    public int updateById(Object view) {
        return (int) updateByIdSqlMapper.execute(view.getClass(), view);
    }

    @Override
    public int updateNonById(Object view) {
        return (int) updateNonByIdSqlMapper.execute(view.getClass(), view);
    }

    @Override
    public int insert(Object view) {
        Class<?> type = view.getClass();
        return (int) insertSqlMapper.execute(type, view);
    }

    @Override
    public int insertFetchKey(Object view) {
        Class<?> type = view.getClass();
        return (int) insertFetchKeySqlMapper.execute(type, view);
    }

    @Override
    public int[] insertBatch(List<?> viewList) {
        return (int[]) insertBatchSqlMapper.execute(viewList.get(0).getClass(), viewList);
    }

    @Override
    public int deleteById(Class<?> type, Object id) {
        return (int) deleteByIdSqlMapper.execute(type, id);
    }

    @Override
    public int deleteByIds(Class<?> type, List<?> idList) {
        return (int) deleteByIdsSqlMapper.execute(type, idList);
    }

    @Override
    public boolean existById(Class<?> type, Object id) {
        Integer result = (Integer) existByIdSqlMapper.execute(type, id);
        return result != null;
    }
}

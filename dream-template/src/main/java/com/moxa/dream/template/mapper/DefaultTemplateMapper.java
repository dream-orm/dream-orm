package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Page;
import com.moxa.dream.system.core.session.Session;

import java.util.List;

public class DefaultTemplateMapper implements TemplateMapper {
    private SelectByIdTemplateMapper selectByIdSqlMapper;
    private SelectByIdsTemplateMapper selectByIdsSqlMapper;
    private SelectOneTemplateMapper selectOneSqlMapper;
    private SelectListTemplateMapper selectListTemplateMapper;
    private SelectPageTemplateMapper selectPageSqlMapper;
    private DeleteByIdTemplateMapper deleteByIdSqlMapper;
    private DeleteByIdsTemplateMapper deleteByIdsSqlMapper;
    private ExistByIdTemplateMapper existByIdSqlMapper;
    private UpdateByIdTemplateMapper updateByIdSqlMapper;
    private UpdateNonByIdTemplateMapper updateNonByIdSqlMapper;
    private InsertTemplateMapper insertSqlMapper;
    private InsertBatchTemplateMapper insertBatchSqlMapper;
    private InsertFetchKeyTemplateMapper insertFetchKeySqlMapper;

    public DefaultTemplateMapper(Session session) {
        selectByIdSqlMapper = new SelectByIdTemplateMapper(session);
        selectByIdsSqlMapper = new SelectByIdsTemplateMapper(session);
        selectOneSqlMapper = new SelectOneTemplateMapper(session);
        selectListTemplateMapper = new SelectListTemplateMapper(session);
        selectPageSqlMapper = new SelectPageTemplateMapper(session);
        deleteByIdSqlMapper = new DeleteByIdTemplateMapper(session);
        deleteByIdsSqlMapper = new DeleteByIdsTemplateMapper(session);
        existByIdSqlMapper = new ExistByIdTemplateMapper(session);
        updateByIdSqlMapper = new UpdateByIdTemplateMapper(session);
        updateNonByIdSqlMapper = new UpdateNonByIdTemplateMapper(session);
        insertSqlMapper = new InsertTemplateMapper(session);
        insertBatchSqlMapper = new InsertBatchTemplateMapper(session);
        insertFetchKeySqlMapper = new InsertFetchKeyTemplateMapper(session);
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
    public <T> T selectOne(Class<T> type, Object conditionObject) {
        return (T) selectOneSqlMapper.execute(type, conditionObject);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, Object conditionObject) {
        return (List<T>) selectListTemplateMapper.execute(type, conditionObject);
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

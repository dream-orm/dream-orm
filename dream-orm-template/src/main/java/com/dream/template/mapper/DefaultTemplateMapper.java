package com.dream.template.mapper;

import com.dream.system.config.Page;
import com.dream.system.core.session.Session;
import com.dream.template.sequence.BatchSequence;
import com.dream.template.sequence.FetchKeySequence;
import com.dream.template.sequence.Sequence;
import com.dream.util.tree.Tree;

import java.util.Collection;
import java.util.List;

public class DefaultTemplateMapper implements TemplateMapper {
    private SelectByIdMapper selectByIdSqlMapper;
    private SelectByIdsMapper selectByIdsSqlMapper;
    private SelectOneMapper selectOneSqlMapper;
    private SelectListMapper selectListMapper;
    private SelectTreeMapper selectTreeMapper;
    private SelectPageMapper selectPageSqlMapper;
    private DeleteByIdMapper deleteByIdSqlMapper;
    private DeleteByIdsMapper deleteByIdsSqlMapper;
    private DeleteOneMapper deleteOneMapper;
    private UpdateByIdMapper updateByIdSqlMapper;
    private BatchUpdateByIdMapper batchUpdateByIdMapper;
    private UpdateNonByIdMapper updateNonByIdSqlMapper;
    private InsertMapper insertSqlMapper;
    private InsertMapper insertFetchKeyMapper;
    private BatchInsertMapper batchInsertMapper;
    private ExistByIdMapper existByIdMapper;
    private ExistMapper existMapper;

    public DefaultTemplateMapper(Session session, Sequence sequence) {
        selectByIdSqlMapper = new SelectByIdMapper(session);
        selectByIdsSqlMapper = new SelectByIdsMapper(session);
        selectOneSqlMapper = new SelectOneMapper(session);
        selectListMapper = new SelectListMapper(session);
        selectTreeMapper = new SelectTreeMapper(session);
        selectPageSqlMapper = new SelectPageMapper(session);
        deleteByIdSqlMapper = new DeleteByIdMapper(session);
        deleteByIdsSqlMapper = new DeleteByIdsMapper(session);
        deleteOneMapper = new DeleteOneMapper(session);
        updateByIdSqlMapper = new UpdateByIdMapper(session);
        batchUpdateByIdMapper = new BatchUpdateByIdMapper(session);
        updateNonByIdSqlMapper = new UpdateNonByIdMapper(session);
        insertSqlMapper = new InsertMapper(session, sequence);
        insertFetchKeyMapper = new InsertMapper(session, new FetchKeySequence(sequence));
        batchInsertMapper = new BatchInsertMapper(session, new BatchSequence(sequence));
        existByIdMapper = new ExistByIdMapper(session);
        existMapper = new ExistMapper(session);
    }

    @Override
    public <T> T selectById(Class<T> type, Object id) {
        return (T) selectByIdSqlMapper.execute(type, id);
    }

    @Override
    public <T> List<T> selectByIds(Class<T> type, Collection<?> idList) {
        return (List<T>) selectByIdsSqlMapper.execute(type, idList);
    }

    @Override
    public <T> T selectOne(Class<T> type, Object conditionObject) {
        return (T) selectOneSqlMapper.execute(type, conditionObject);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, Object conditionObject) {
        return (List<T>) selectListMapper.execute(type, conditionObject);
    }

    @Override
    public <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject) {
        return (List<T>) selectTreeMapper.execute(type, conditionObject);
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
    public Object insertFetchKey(Object view) {
        Class<?> type = view.getClass();
        return insertFetchKeyMapper.execute(type, view);
    }

    @Override
    public int deleteById(Class<?> type, Object id) {
        return (int) deleteByIdSqlMapper.execute(type, id);
    }

    @Override
    public int delete(Object view) {
        return (int) deleteOneMapper.execute(view.getClass(), view);
    }

    @Override
    public int deleteByIds(Class<?> type, Collection<?> idList) {
        return (int) deleteByIdsSqlMapper.execute(type, idList);
    }

    @Override
    public boolean existById(Class<?> type, Object id) {
        Integer result = (Integer) existByIdMapper.execute(type, id);
        return result != null;
    }

    @Override
    public boolean exist(Class<?> type, Object conditionObject) {
        Integer result = (Integer) existMapper.execute(type, conditionObject);
        return result != null;
    }

    @Override
    public List<Object> batchInsert(Collection<?> viewList) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        return (List<Object>) batchInsertMapper.execute(viewList.iterator().next().getClass(), viewList);
    }

    @Override
    public List<Object> batchUpdateById(Collection<?> viewList) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        return (List<Object>) batchUpdateByIdMapper.execute(viewList.iterator().next().getClass(), viewList);
    }
}

package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Page;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.resolve.MappedResolve;
import com.moxa.dream.template.resulthandler.Tree;
import com.moxa.dream.template.sequence.BatchSequence;
import com.moxa.dream.template.sequence.FetchKeySequence;
import com.moxa.dream.template.sequence.Sequence;

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
    private UpdateByIdMapper updateByIdSqlMapper;
    private BatchUpdateByIdMapper batchUpdateByIdMapper;
    private UpdateNonByIdMapper updateNonByIdSqlMapper;
    private InsertMapper insertSqlMapper;
    private InsertMapper insertFetchKeyMapper;
    private BatchInsertMapper batchInsertMapper;
    private ExistByIdMapper existByIdMapper;
    private ExistMapper existMapper;
    private ExecuteMapper executeMapper;

    public DefaultTemplateMapper(Session session, Sequence sequence) {
        selectByIdSqlMapper = new SelectByIdMapper(session);
        selectByIdsSqlMapper = new SelectByIdsMapper(session);
        selectOneSqlMapper = new SelectOneMapper(session);
        selectListMapper = new SelectListMapper(session);
        selectTreeMapper = new SelectTreeMapper(session);
        selectPageSqlMapper = new SelectPageMapper(session);
        deleteByIdSqlMapper = new DeleteByIdMapper(session);
        deleteByIdsSqlMapper = new DeleteByIdsMapper(session);
        updateByIdSqlMapper = new UpdateByIdMapper(session);
        batchUpdateByIdMapper = new BatchUpdateByIdMapper(session);
        updateNonByIdSqlMapper = new UpdateNonByIdMapper(session);
        insertSqlMapper = new InsertMapper(session, sequence);
        insertFetchKeyMapper = new InsertMapper(session, new FetchKeySequence(sequence));
        batchInsertMapper = new BatchInsertMapper(session, new BatchSequence(sequence));
        existByIdMapper = new ExistByIdMapper(session);
        existMapper = new ExistMapper(session);
        executeMapper = new ExecuteMapper(session);
    }

    @Override
    public <T> T selectById(Class<T> type, Object id, MappedResolve mappedResolve) {
        return (T) selectByIdSqlMapper.execute(type, id, mappedResolve);
    }

    @Override
    public <T> List<T> selectByIds(Class<T> type, List<?> idList, MappedResolve mappedResolve) {
        return (List<T>) selectByIdsSqlMapper.execute(type, idList, mappedResolve);
    }

    @Override
    public <T> T selectOne(Class<T> type, Object conditionObject, MappedResolve mappedResolve) {
        return (T) selectOneSqlMapper.execute(type, conditionObject, mappedResolve);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, Object conditionObject, MappedResolve mappedResolve) {
        return (List<T>) selectListMapper.execute(type, conditionObject, mappedResolve);
    }

    @Override
    public <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject, MappedResolve mappedResolve) {
        return (List<T>) selectTreeMapper.execute(type, conditionObject, mappedResolve);
    }

    @Override
    public <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page, MappedResolve mappedResolve) {
        return (Page<T>) selectPageSqlMapper.execute(type, conditionObject, page, mappedResolve);
    }

    @Override
    public int updateById(Object view, MappedResolve mappedResolve) {
        return (int) updateByIdSqlMapper.execute(view.getClass(), view, mappedResolve);
    }

    @Override
    public int updateNonById(Object view, MappedResolve mappedResolve) {
        return (int) updateNonByIdSqlMapper.execute(view.getClass(), view, mappedResolve);
    }

    @Override
    public int insert(Object view, MappedResolve mappedResolve) {
        Class<?> type = view.getClass();
        return (int) insertSqlMapper.execute(type, view, mappedResolve);
    }

    @Override
    public Object insertFetchKey(Object view, MappedResolve mappedResolve) {
        Class<?> type = view.getClass();
        return insertFetchKeyMapper.execute(type, view, mappedResolve);
    }

    @Override
    public int deleteById(Class<?> type, Object id, MappedResolve mappedResolve) {
        return (int) deleteByIdSqlMapper.execute(type, id, mappedResolve);
    }

    @Override
    public int deleteByIds(Class<?> type, List<?> idList, MappedResolve mappedResolve) {
        return (int) deleteByIdsSqlMapper.execute(type, idList, mappedResolve);
    }

    @Override
    public boolean existById(Class<?> type, Object id, MappedResolve mappedResolve) {
        Integer result = (Integer) existByIdMapper.execute(type, id, mappedResolve);
        return result != null;
    }

    @Override
    public boolean exist(Class<?> type, Object conditionObject, MappedResolve mappedResolve) {
        Integer result = (Integer) existMapper.execute(type, conditionObject, mappedResolve);
        return result != null;
    }

    @Override
    public List<Object> batchInsert(List<?> viewList, MappedResolve mappedResolve) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        return (List<Object>) batchInsertMapper.execute(viewList.get(0).getClass(), viewList, mappedResolve);
    }

    @Override
    public List<Object> batchUpdateById(List<?> viewList, MappedResolve mappedResolve) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        return (List<Object>) batchUpdateByIdMapper.execute(viewList.get(0).getClass(), viewList, mappedResolve);
    }

    @Override
    public Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType, MappedResolve mappedResolve) {
        return executeMapper.execute(sql, param, rowType, colType, mappedResolve);
    }
}

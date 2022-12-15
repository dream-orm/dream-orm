package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Page;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.attach.AttachMent;
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

    public DefaultTemplateMapper(Session session, Sequence sequence, AttachMent attachMent) {
        selectByIdSqlMapper = new SelectByIdMapper(session, attachMent);
        selectByIdsSqlMapper = new SelectByIdsMapper(session, attachMent);
        selectOneSqlMapper = new SelectOneMapper(session, attachMent);
        selectListMapper = new SelectListMapper(session, attachMent);
        selectPageSqlMapper = new SelectPageMapper(session, attachMent);
        deleteByIdSqlMapper = new DeleteByIdMapper(session, attachMent);
        deleteByIdsSqlMapper = new DeleteByIdsMapper(session, attachMent);
        updateByIdSqlMapper = new UpdateByIdMapper(session, attachMent);
        batchUpdateByIdMapper = new BatchUpdateByIdMapper(session, attachMent);
        updateNonByIdSqlMapper = new UpdateNonByIdMapper(session, attachMent);
        insertSqlMapper = new InsertMapper(session, sequence);
        insertFetchKeyMapper = new InsertMapper(session, new FetchKeySequence(sequence));
        batchInsertMapper = new BatchInsertMapper(session, new BatchSequence(sequence));
        existByIdMapper = new ExistByIdMapper(session, attachMent);
        existMapper = new ExistMapper(session, attachMent);
        executeMapper = new ExecuteMapper(session);
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
        return (List<T>) selectListMapper.execute(type, conditionObject);
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
    public int deleteByIds(Class<?> type, List<?> idList) {
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
    public List<Object> batchInsert(List<?> viewList, int batchSize) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        return (List<Object>) batchInsertMapper.execute(viewList.get(0).getClass(), viewList, batchSize);
    }

    @Override
    public List<Object> batchUpdateById(List<?> viewList, int batchSize) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        return (List<Object>) batchUpdateByIdMapper.execute(viewList.get(0).getClass(), viewList, batchSize);
    }

    @Override
    public Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType, boolean cache) {
        return executeMapper.execute(sql, param, rowType, colType, cache);
    }
}

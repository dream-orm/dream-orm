package com.dream.template.mapper;

import com.dream.system.config.Page;
import com.dream.system.core.session.Session;
import com.dream.template.sequence.BatchSequence;
import com.dream.template.sequence.FetchKeySequence;
import com.dream.template.sequence.NoFetchKeySequence;
import com.dream.template.sequence.Sequence;
import com.dream.util.tree.Tree;

import java.util.Collection;
import java.util.List;

public class DefaultTemplateMapper implements TemplateMapper {
    private final String baseName = TemplateMapper.class.getName();
    private final String selectById = baseName + ".selectById";
    private final String selectByIds = baseName + ".selectByIds";
    private final String selectOne = baseName + ".selectOne";
    private final String selectList = baseName + ".selectList";
    private final String selectTree = baseName + ".selectTree";
    private final String selectPage = baseName + ".selectPage";
    private final String selectCount = baseName + ".selectCount";
    private final String deleteById = baseName + ".deleteById";
    private final String delete = baseName + ".delete";
    private final String deleteByIds = baseName + ".deleteByIds";
    private final String existById = baseName + ".existById";
    private final String exist = baseName + ".exist";
    private final String updateById = baseName + ".updateById";
    private final String batchUpdateById = baseName + ".batchUpdateById";
    private final String updateNonById = baseName + ".updateNonById";
    private final String insert = baseName + ".insert";
    private final String insertFetchKey = baseName + ".insertFetchKey";
    private final String batchInsert = baseName + ".batchInsert";
    private final SelectByIdMapper selectByIdSqlMapper;
    private final SelectByIdsMapper selectByIdsSqlMapper;
    private final SelectOneMapper selectOneSqlMapper;
    private final SelectListMapper selectListMapper;
    private final SelectTreeMapper selectTreeMapper;
    private final SelectPageMapper selectPageSqlMapper;
    private final DeleteByIdMapper deleteByIdSqlMapper;
    private final DeleteByIdsMapper deleteByIdsSqlMapper;
    private final DeleteOneMapper deleteOneMapper;
    private final UpdateByIdMapper updateByIdSqlMapper;
    private final BatchUpdateByIdMapper batchUpdateByIdMapper;
    private final UpdateNonByIdMapper updateNonByIdSqlMapper;
    private final InsertMapper insertSqlMapper;
    private final InsertMapper insertFetchKeyMapper;
    private final BatchInsertMapper batchInsertMapper;
    private final ExistByIdMapper existByIdMapper;
    private final ExistMapper existMapper;
    private SelectCountMapper selectCountSqlMapper;

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
        insertSqlMapper = new InsertMapper(session, new NoFetchKeySequence(sequence));
        insertFetchKeyMapper = new InsertMapper(session, new FetchKeySequence(sequence));
        batchInsertMapper = new BatchInsertMapper(session, new BatchSequence(sequence));
        existByIdMapper = new ExistByIdMapper(session);
        existMapper = new ExistMapper(session);
        selectCountSqlMapper = new SelectCountMapper(session);
    }

    @Override
    public <T> T selectById(Class<T> type, Object id) {
        return (T) selectByIdSqlMapper.execute(selectById + ":" + type.getName(), type, id);
    }

    @Override
    public <T> List<T> selectByIds(Class<T> type, Collection<?> idList) {
        return (List<T>) selectByIdsSqlMapper.execute(selectByIds + ":" + type.getName(), type, idList);
    }

    @Override
    public <T> T selectOne(Class<T> type, Object conditionObject) {
        String id = selectOne + ":" + type.getName();
        if (conditionObject != null) {
            id += ":" + conditionObject.getClass().getName();
        }
        return (T) selectOneSqlMapper.execute(id, type, conditionObject);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, Object conditionObject) {
        String id = selectList + ":" + type.getName();
        if (conditionObject != null) {
            id += ":" + conditionObject.getClass().getName();
        }
        return (List<T>) selectListMapper.execute(id, type, conditionObject);
    }

    @Override
    public <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject) {
        String id = selectTree + ":" + type.getName();
        if (conditionObject != null) {
            id += ":" + conditionObject.getClass().getName();
        }
        return (List<T>) selectTreeMapper.execute(id, type, conditionObject);
    }

    @Override
    public <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page) {
        String id = selectPage + ":" + type.getName();
        if (conditionObject != null) {
            id += ":" + conditionObject.getClass().getName();
        }
        return (Page<T>) selectPageSqlMapper.execute(id, type, conditionObject, page);
    }

    @Override
    public Integer selectCount(Class<?> type, Object conditionObject) {
        String id = selectCount + ":" + type.getName();
        if (conditionObject != null) {
            id += ":" + conditionObject.getClass().getName();
        }
        return (Integer) selectCountSqlMapper.execute(id, type, conditionObject);
    }

    @Override
    public int updateById(Object view) {
        Class<?> type = view.getClass();
        return (int) updateByIdSqlMapper.execute(updateById + ":" + type.getName(), type, view);
    }

    @Override
    public int updateNonById(Object view) {
        Class<?> type = view.getClass();
        return (int) updateNonByIdSqlMapper.execute(updateNonById + ":" + type.getName(), type, view);
    }

    @Override
    public int insert(Object view) {
        Class<?> type = view.getClass();
        return (int) insertSqlMapper.execute(insert + ":" + type.getName(), type, view);
    }

    @Override
    public Object insertFetchKey(Object view) {
        Class<?> type = view.getClass();
        return insertFetchKeyMapper.execute(insertFetchKey + ":" + type.getName(), type, view);
    }

    @Override
    public int deleteById(Class<?> type, Object id) {
        return (int) deleteByIdSqlMapper.execute(deleteById + ":" + type.getName(), type, id);
    }

    @Override
    public int delete(Object view) {
        Class<?> type = view.getClass();
        return (int) deleteOneMapper.execute(delete + ":" + type.getName(), type, view);
    }

    @Override
    public int deleteByIds(Class<?> type, Collection<?> idList) {
        return (int) deleteByIdsSqlMapper.execute(deleteByIds + ":" + type.getName(), type, idList);
    }

    @Override
    public boolean existById(Class<?> type, Object id) {
        Integer result = (Integer) existByIdMapper.execute(existById + ":" + type.getName(), type, id);
        return result != null;
    }

    @Override
    public boolean exist(Class<?> type, Object conditionObject) {
        String id = exist + ":" + type.getName();
        if (conditionObject != null) {
            id += ":" + conditionObject.getClass().getName();
        }
        Integer result = (Integer) existMapper.execute(id, type, conditionObject);
        return result != null;
    }

    @Override
    public List<Object> batchInsert(Collection<?> viewList) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        Class<?> type = viewList.iterator().next().getClass();
        return (List<Object>) batchInsertMapper.execute(batchInsert + ":" + type.getName(), type, viewList);
    }

    @Override
    public List<Object> batchUpdateById(Collection<?> viewList) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        Class<?> type = viewList.iterator().next().getClass();
        return (List<Object>) batchUpdateByIdMapper.execute(batchUpdateById + ":" + type.getName(), type, viewList);
    }
}

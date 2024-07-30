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
    private final String selectById = TemplateMapper.class.getName() + ".selectById";
    private final String selectByIds = TemplateMapper.class.getName() + ".selectByIds";
    private final String selectOne = TemplateMapper.class.getName() + ".selectOne";
    private final String selectList = TemplateMapper.class.getName() + ".selectList";
    private final String selectTree = TemplateMapper.class.getName() + ".selectTree";
    private final String selectPage = TemplateMapper.class.getName() + ".selectPage";
    private final String deleteById = TemplateMapper.class.getName() + ".deleteById";
    private final String delete = TemplateMapper.class.getName() + ".delete";
    private final String deleteByIds = TemplateMapper.class.getName() + ".deleteByIds";
    private final String existById = TemplateMapper.class.getName() + ".existById";
    private final String exist = TemplateMapper.class.getName() + ".exist";
    private final String updateById = TemplateMapper.class.getName() + ".updateById";
    private final String batchUpdateById = TemplateMapper.class.getName() + ".batchUpdateById";
    private final String updateNonById = TemplateMapper.class.getName() + ".updateNonById";
    private final String insert = TemplateMapper.class.getName() + ".insert";
    private final String insertFetchKey = TemplateMapper.class.getName() + ".insertFetchKey";
    private final String batchInsert = TemplateMapper.class.getName() + ".batchInsert";
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
        insertSqlMapper = new InsertMapper(session, new NoFetchKeySequence(sequence));
        insertFetchKeyMapper = new InsertMapper(session, new FetchKeySequence(sequence));
        batchInsertMapper = new BatchInsertMapper(session, new BatchSequence(sequence));
        existByIdMapper = new ExistByIdMapper(session);
        existMapper = new ExistMapper(session);
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
            id += conditionObject.getClass().getName();
        }
        return (T) selectOneSqlMapper.execute(id, type, conditionObject);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, Object conditionObject) {
        String id = selectList + ":" + type.getName();
        if (conditionObject != null) {
            id += conditionObject.getClass().getName();
        }
        return (List<T>) selectListMapper.execute(id, type, conditionObject);
    }

    @Override
    public <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject) {
        String id = selectTree + ":" + type.getName();
        if (conditionObject != null) {
            id += conditionObject.getClass().getName();
        }
        return (List<T>) selectTreeMapper.execute(id, type, conditionObject);
    }

    @Override
    public <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page) {
        String id = selectPage + ":" + type.getName();
        if (conditionObject != null) {
            id += conditionObject.getClass().getName();
        }
        return (Page<T>) selectPageSqlMapper.execute(id, type, conditionObject, page);
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
            id += conditionObject.getClass().getName();
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

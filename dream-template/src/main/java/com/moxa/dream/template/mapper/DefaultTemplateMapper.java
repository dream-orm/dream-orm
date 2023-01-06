package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.config.Page;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.resulthandler.Tree;
import com.moxa.dream.template.sequence.BatchSequence;
import com.moxa.dream.template.sequence.FetchKeySequence;
import com.moxa.dream.template.sequence.Sequence;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

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
    public TemplateMapper methodInfo(Consumer<MethodInfo> consumer) {
        return new TutorTemplateMapper(this).methodInfo(consumer);
    }

    @Override
    public TemplateMapper mappedStatement(Consumer<MappedStatement> consumer) {
        return new TutorTemplateMapper(this).mappedStatement(consumer);
    }

    @Override
    public <T> T selectById(Class<T> type, Object id) {
        return selectById(type, id, null, null);
    }

    @Override
    public <T> List<T> selectByIds(Class<T> type, List<?> idList) {
        return selectByIds(type, idList, null, null);
    }

    @Override
    public <T> T selectOne(Class<T> type, Object conditionObject) {
        return selectOne(type, conditionObject, null, null);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, Object conditionObject) {
        return selectList(type, conditionObject, null, null);
    }

    @Override
    public <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject) {
        return selectTree(type, conditionObject, null, null);
    }

    @Override
    public <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page) {
        return selectPage(type, conditionObject, page, null, null);
    }

    @Override
    public int updateById(Object view) {
        return updateById(view, null, null);
    }

    @Override
    public int updateNonById(Object view) {
        return updateNonById(view, null, null);
    }

    @Override
    public int insert(Object view) {
        return insert(view, null, null);
    }

    @Override
    public Object insertFetchKey(Object view) {
        return insertFetchKey(view, null, null);
    }

    @Override
    public int deleteById(Class<?> type, Object id) {
        return deleteById(type, id, null, null);
    }

    @Override
    public int deleteByIds(Class<?> type, List<?> idList) {
        return deleteByIds(type, idList, null, null);
    }

    @Override
    public boolean existById(Class<?> type, Object id) {
        return existById(type, id, null, null);
    }

    @Override
    public boolean exist(Class<?> type, Object conditionObject) {
        return exist(type, conditionObject, null, null);
    }

    @Override
    public List<Object> batchInsert(List<?> viewList) {
        return batchInsert(viewList, null, null);
    }

    @Override
    public List<Object> batchUpdateById(List<?> viewList) {
        return batchUpdateById(viewList, null, null);
    }

    @Override
    public Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType) {
        return execute(sql, param, rowType, colType, null, null);
    }


    protected <T> T selectById(Class<T> type, Object id, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (T) selectByIdSqlMapper.execute(type, id, methodInfoConsumer, mappedStatementConsumer);
    }

    protected <T> List<T> selectByIds(Class<T> type, List<?> idList, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (List<T>) selectByIdsSqlMapper.execute(type, idList, methodInfoConsumer, mappedStatementConsumer);
    }

    protected <T> T selectOne(Class<T> type, Object conditionObject, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (T) selectOneSqlMapper.execute(type, conditionObject, methodInfoConsumer, mappedStatementConsumer);
    }

    protected <T> List<T> selectList(Class<T> type, Object conditionObject, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (List<T>) selectListMapper.execute(type, conditionObject, methodInfoConsumer, mappedStatementConsumer);
    }

    protected <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (List<T>) selectTreeMapper.execute(type, conditionObject, methodInfoConsumer, mappedStatementConsumer);
    }

    protected <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (Page<T>) selectPageSqlMapper.execute(type, conditionObject, page, methodInfoConsumer, mappedStatementConsumer);
    }

    protected int updateById(Object view, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (int) updateByIdSqlMapper.execute(view.getClass(), view, methodInfoConsumer, mappedStatementConsumer);
    }

    protected int updateNonById(Object view, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (int) updateNonByIdSqlMapper.execute(view.getClass(), view, methodInfoConsumer, mappedStatementConsumer);
    }

    protected int insert(Object view, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        Class<?> type = view.getClass();
        return (int) insertSqlMapper.execute(type, view, methodInfoConsumer, mappedStatementConsumer);
    }

    protected Object insertFetchKey(Object view, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        Class<?> type = view.getClass();
        return insertFetchKeyMapper.execute(type, view, methodInfoConsumer, mappedStatementConsumer);
    }

    protected int deleteById(Class<?> type, Object id, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (int) deleteByIdSqlMapper.execute(type, id, methodInfoConsumer, mappedStatementConsumer);
    }

    protected int deleteByIds(Class<?> type, List<?> idList, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return (int) deleteByIdsSqlMapper.execute(type, idList, methodInfoConsumer, mappedStatementConsumer);
    }

    protected boolean existById(Class<?> type, Object id, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        Integer result = (Integer) existByIdMapper.execute(type, id, methodInfoConsumer, mappedStatementConsumer);
        return result != null;
    }

    protected boolean exist(Class<?> type, Object conditionObject, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        Integer result = (Integer) existMapper.execute(type, conditionObject, methodInfoConsumer, mappedStatementConsumer);
        return result != null;
    }

    protected List<Object> batchInsert(List<?> viewList, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        return (List<Object>) batchInsertMapper.execute(viewList.get(0).getClass(), viewList, methodInfoConsumer, mappedStatementConsumer);
    }

    protected List<Object> batchUpdateById(List<?> viewList, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        return (List<Object>) batchUpdateByIdMapper.execute(viewList.get(0).getClass(), viewList, methodInfoConsumer, mappedStatementConsumer);
    }

    protected Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return executeMapper.execute(sql, param, rowType, colType, methodInfoConsumer, mappedStatementConsumer);
    }
}

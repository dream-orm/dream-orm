package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.config.Page;
import com.moxa.dream.template.resulthandler.Tree;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class TutorTemplateMapper implements TemplateMapper {
    private DefaultTemplateMapper templateMapper;
    private Consumer<MethodInfo> methodInfoConsumer;
    private Consumer<MappedStatement> mappedStatementConsumer;

    public TutorTemplateMapper(DefaultTemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }

    @Override
    public TemplateMapper methodInfo(Consumer<MethodInfo> consumer) {
        this.methodInfoConsumer = consumer;
        return this;
    }

    @Override
    public TemplateMapper mappedStatement(Consumer<MappedStatement> consumer) {
        this.mappedStatementConsumer = consumer;
        return this;
    }

    @Override
    public <T> T selectById(Class<T> type, Object id) {
        return templateMapper.selectById(type, id, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public <T> List<T> selectByIds(Class<T> type, List<?> idList) {
        return templateMapper.selectByIds(type, idList, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public <T> T selectOne(Class<T> type, Object conditionObject) {
        return templateMapper.selectOne(type, conditionObject, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, Object conditionObject) {
        return templateMapper.selectList(type, conditionObject, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject) {
        return templateMapper.selectList(type, conditionObject, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page) {
        return templateMapper.selectPage(type, conditionObject, page, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public int updateById(Object view) {
        return templateMapper.updateById(view, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public int updateNonById(Object view) {
        return templateMapper.updateNonById(view, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public int insert(Object view) {
        return templateMapper.insert(view, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public Object insertFetchKey(Object view) {
        return templateMapper.insert(view, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public int deleteById(Class<?> type, Object id) {
        return templateMapper.deleteById(type, id, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public int deleteByIds(Class<?> type, List<?> idList) {
        return templateMapper.deleteByIds(type, idList, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public boolean existById(Class<?> type, Object id) {
        return templateMapper.existById(type, id, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public boolean exist(Class<?> type, Object conditionObject) {
        return templateMapper.existById(type, conditionObject, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public List<Object> batchInsert(List<?> viewList) {
        return templateMapper.batchInsert(viewList, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public List<Object> batchUpdateById(List<?> viewList) {
        return templateMapper.batchInsert(viewList, methodInfoConsumer, mappedStatementConsumer);
    }

    @Override
    public Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType) {
        return templateMapper.execute(sql, param, rowType, colType, methodInfoConsumer, mappedStatementConsumer);
    }
}

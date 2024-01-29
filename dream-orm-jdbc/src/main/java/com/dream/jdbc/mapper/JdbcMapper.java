package com.dream.jdbc.mapper;

import com.dream.jdbc.core.JdbcStatementSetter;
import com.dream.jdbc.core.StatementSetter;
import com.dream.jdbc.row.BeanPropertyRowMapping;
import com.dream.jdbc.row.ColumnMapRowMapping;
import com.dream.jdbc.row.RowMapping;
import com.dream.jdbc.row.SingleColumnRowMapping;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.util.List;
import java.util.Map;

/**
 * 不翻译直接执行SQL接口
 */
public interface JdbcMapper {
    /**
     * SQL操作
     *
     * @param sql  原始SQL
     * @param args 参数
     * @return 操作条数
     */
    default int execute(String sql, Object... args) {
        return execute(sql, new JdbcStatementSetter(args));
    }

    /**
     * SQL操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @return 操作条数
     */
    int execute(String sql, StatementSetter statementSetter);

    /**
     * 批量操作
     *
     * @param sql             原始SQL
     * @param argList         集合数据
     * @param statementSetter 自定义参数设置
     * @param <T>
     * @return 操作条数
     */
    default <T> List<Object> batchExecute(String sql, List<T> argList, StatementSetter statementSetter) {
        return batchExecute(sql, argList, statementSetter, 1000);
    }

    /**
     * 批量操作
     *
     * @param sql             原始SQL
     * @param argList         集合数据
     * @param statementSetter 自定义参数设置
     * @param batchSize       批量大小
     * @param <T>
     * @return
     */
    <T> List<Object> batchExecute(String sql, List<T> argList, StatementSetter statementSetter, int batchSize);

    /**
     * 查询操作
     *
     * @param sql  原始SQL
     * @param type 返回类型
     * @param args 参数
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, Class<T> type, Object... args) {
        return getOne(queryForList(sql, type, args));
    }

    /**
     * 查询操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @param type            返回类型
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, StatementSetter statementSetter, Class<T> type) {
        return getOne(queryForList(sql, statementSetter, type));
    }

    /**
     * 查询操作
     *
     * @param sql        原始SQL
     * @param rowMapping 自定义映射
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, RowMapping<T> rowMapping) {
        return getOne(queryForList(sql, rowMapping));
    }

    /**
     * 查询操作
     *
     * @param sql        原始SQL
     * @param rowMapping 自定义映射
     * @param args       参数
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, RowMapping<T> rowMapping, Object... args) {
        return getOne(queryForList(sql, new JdbcStatementSetter(args), rowMapping));
    }

    /**
     * 查询操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @param rowMapping      自定义映射
     * @param <T>
     * @return 查询的数据
     */
    default <T> T queryForObject(String sql, StatementSetter statementSetter, RowMapping<T> rowMapping) {
        return getOne(queryForList(sql, statementSetter, rowMapping));
    }

    /**
     * 查询操作
     *
     * @param sql  原始SQL
     * @param type 查询类型
     * @param args 参数
     * @param <T>
     * @return 查询的数据
     */
    default <T> List<T> queryForList(String sql, Class<T> type, Object... args) {
        return queryForList(sql, new JdbcStatementSetter(args), type);
    }

    /**
     * 查询操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @param type            查询类型
     * @param <T>
     * @return 查询的数据
     */
    default <T> List<T> queryForList(String sql, StatementSetter statementSetter, Class<T> type) {
        RowMapping rowMapping;
        if (ReflectUtil.isBaseClass(type)) {
            rowMapping = new SingleColumnRowMapping<>(type);
        } else if (Map.class.isAssignableFrom(type)) {
            rowMapping = new ColumnMapRowMapping();
        } else {
            rowMapping = new BeanPropertyRowMapping(type);
        }
        return queryForList(sql, statementSetter, rowMapping);
    }

    /**
     * 查询操作
     *
     * @param sql        原始SQL
     * @param rowMapping 自定义映射
     * @param <T>
     * @return 查询的数据
     */
    default <T> List<T> queryForList(String sql, RowMapping<T> rowMapping) {
        return queryForList(sql, new JdbcStatementSetter(null), rowMapping);
    }

    /**
     * 查询操作
     *
     * @param sql        原始SQL
     * @param rowMapping 自定义映射
     * @param args       参数
     * @param <T>
     * @return 查询的数据
     */
    default <T> List<T> queryForList(String sql, RowMapping<T> rowMapping, Object... args) {
        return queryForList(sql, new JdbcStatementSetter(args), rowMapping);
    }

    /**
     * 查询操作
     *
     * @param sql             原始SQL
     * @param statementSetter 自定义参数设置
     * @param rowMapping      自定义映射
     * @param <T>
     * @return 查询的数据
     */
    <T> List<T> queryForList(String sql, StatementSetter statementSetter, RowMapping<T> rowMapping);

    default <T> T getOne(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        throw new DreamRunTimeException("数据存在多条");
    }

    /**
     * 根据主键更新
     *
     * @param view 更新对象
     * @return
     */
    int updateById(Object view);

    /**
     * 根据主键非空更新
     *
     * @param view 更新对象
     * @return
     */
    int updateNonById(Object view);

    /**
     * 插入数据
     *
     * @param view 插入对象
     * @return
     */
    int insert(Object view);

    /**
     * 批量插入
     *
     * @param viewList 数据集合
     * @return
     */
    <T> List<Object> batchInsert(List<T> viewList);

    /**
     * 批量更新
     *
     * @param viewList 数据集合
     * @return
     */
    <T> List<Object> batchUpdateById(List<T> viewList);
}

package com.dream.drive.transaction;

import com.dream.util.exception.DreamRunTimeException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class TransManager {
    private static final ThreadLocal<Map<String, Connection>> CONNECTION_HOLDER = ThreadLocal.withInitial(ConcurrentHashMap::new);

    private TransManager() {
    }

    /**
     * 执行事务
     *
     * @param supplier 自定义内容
     * @param <T>
     * @return 数据
     */
    public static <T> T exec(Supplier<T> supplier) {
        return exec(supplier, Propagation.REQUIRED);
    }

    /**
     * 执行事务
     *
     * @param supplier    自定义内容
     * @param propagation 事务传播机制
     * @param <T>
     * @return 数据
     */
    public static <T> T exec(Supplier<T> supplier, Propagation propagation) {
        String currentXID = TransactionContext.getXID();
        try {
            switch (propagation) {
                //若存在当前事务，则加入当前事务，若不存在当前事务，则创建新的事务
                case REQUIRED:
                    if (currentXID != null) {
                        return supplier.get();
                    } else {
                        return execNewTransactional(supplier);
                    }
                    //若存在当前事务，则加入当前事务，若不存在当前事务，则已非事务的方式运行
                case SUPPORTS:
                    return supplier.get();
                //若存在当前事务，则加入当前事务，若不存在当前事务，则已非事务的方式运行
                case MANDATORY:
                    if (currentXID != null) {
                        return supplier.get();
                    } else {
                        throw new DreamRunTimeException("未找到标记为'MANDATORY'传播的现有事务");
                    }
                    //始终以新事务的方式运行，若存在当前事务，则暂停（挂起）当前事务。
                case REQUIRES_NEW:
                    return execNewTransactional(supplier);
                //以非事务的方式运行，若存在当前事务，则暂停（挂起）当前事务。
                case NOT_SUPPORTED:
                    if (currentXID != null) {
                        TransactionContext.release();
                    }
                    return supplier.get();
                //以非事务的方式运行，若存在当前事务，则抛出异常。
                case NEVER:
                    if (currentXID != null) {
                        throw new DreamRunTimeException("标记为'NEVER'的事务找到现有事务");
                    }
                    return supplier.get();
                case NESTED:
                    //暂时不支持这种事务传递方式
                    //default 为 nested 方式
                default:
                    throw new DreamRunTimeException("事务管理器不允许嵌套事务");
            }
        } catch (SQLException e) {
            throw new DreamRunTimeException(e);
        } finally {
            //恢复上一级事务
            if (currentXID != null) {
                TransactionContext.holdXID(currentXID);
            }
        }
    }

    static <T> T execNewTransactional(Supplier<T> supplier) throws SQLException {
        String xid = startTransactional();
        T result;
        try {
            result = supplier.get();
            commit(xid);
        } catch (Throwable e) {
            rollback(xid);
            throw new DreamRunTimeException(e.getMessage(), e);
        }
        return result;
    }

    static Connection getConnection(DataSource dataSource) throws SQLException {
        String xid = TransactionContext.getXID();
        if (xid != null) {
            Connection connection = getConnection(xid);
            if (connection == null) {
                connection = dataSource.getConnection();
                if (connection.getAutoCommit()) {
                    connection.setAutoCommit(false);
                }
                CONNECTION_HOLDER.get().put(xid, connection);
            }
            return connection;
        } else {
            return dataSource.getConnection();
        }
    }

    static Connection getConnection(String xid) {
        Connection connection = CONNECTION_HOLDER.get().get(xid);
        return connection;
    }

    static String startTransactional() {
        String xid = UUID.randomUUID().toString();
        TransactionContext.holdXID(xid);
        return xid;
    }

    static void commit(String xid) throws SQLException {
        Connection connection = getConnection(xid);
        try {
            if (connection != null) {
                connection.commit();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } finally {
                CONNECTION_HOLDER.remove();
                TransactionContext.release();
            }
        }
    }

    static void rollback(String xid) throws SQLException {
        Connection connection = getConnection(xid);
        try {
            connection.rollback();
        } finally {
            try {
                connection.close();
            } finally {
                CONNECTION_HOLDER.remove();
                TransactionContext.release();
            }
        }
    }

    public static boolean isTrans() {
        return TransactionContext.getXID() != null;
    }
}

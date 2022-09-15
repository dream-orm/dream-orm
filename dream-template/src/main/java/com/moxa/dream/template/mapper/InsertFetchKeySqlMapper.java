package com.moxa.dream.template.mapper;

import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.dialect.DbType;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.mapper.fetch.*;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class InsertFetchKeySqlMapper extends InsertSqlMapper {
    private FetchKey fetchKey;

    public InsertFetchKeySqlMapper(Session session) {
        super(session);
        DbType dbType = session.getConfiguration().getDialectFactory().getDbType();
        switch (dbType) {
            case MYSQL:
                fetchKey = new MySQLFetchKey(session.getConfiguration());
                break;
            case MSSQL:
                fetchKey = new MSSQLFetchKey(session.getConfiguration());
                break;
            case PGSQL:
                fetchKey = new PGSQLFetchKey(session.getConfiguration());
                break;
            case ORACLE:
                fetchKey = new ORACLEFetchKey(session.getConfiguration());
                break;
            default:
                throw new DreamRunTimeException(dbType + "类型尚未支持获取自增主键");
        }

    }

    @Override
    protected Action[] initActionList(TableInfo tableInfo) {
        return fetchKey.initActionList(tableInfo);
    }

    @Override
    protected Action[] destroyActionList(TableInfo tableInfo) {
        return fetchKey.destroyActionList(tableInfo);
    }

    @Override
    protected String[] getColumnNames(TableInfo tableInfo) {
        return fetchKey.getColumnNames(tableInfo);
    }
}

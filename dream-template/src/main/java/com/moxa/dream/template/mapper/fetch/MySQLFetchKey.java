package com.moxa.dream.template.mapper.fetch;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.typehandler.factory.TypeHandlerFactory;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.util.common.ObjectMap;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Types;

public class MySQLFetchKey implements FetchKey {
    private TypeHandler typeHandler;
    private Configuration configuration;

    public MySQLFetchKey(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Action[] destroyActionList(TableInfo tableInfo) {
        return new Action[]{new FetchKeyAction(configuration, tableInfo, tableInfo.getPrimColumnInfo().getName())};
    }

    @Override
    public String[] getColumnNames(TableInfo tableInfo) {
        ColumnInfo columnInfo = tableInfo.getPrimColumnInfo();
        ObjectUtil.requireNonNull(columnInfo, "表'" + tableInfo.getTable() + "'未注册主键");
        return new String[]{columnInfo.getColumn()};
    }

    class FetchKeyAction implements Action {
        private String fieldName;
        private TypeHandlerFactory typeHandlerFactory;
        private String property;

        public FetchKeyAction(Configuration configuration, TableInfo tableInfo, String property) {
            ColumnInfo columnInfo = tableInfo.getPrimColumnInfo();
            ObjectUtil.requireNonNull(columnInfo, "表'" + tableInfo.getTable() + "'未注册主键");
            this.fieldName = columnInfo.getName();
            this.typeHandlerFactory = configuration.getTypeHandlerFactory();
            this.property = property;
        }

        @Override
        public void doAction(Executor executor, Object arg) throws Exception {
            if(arg instanceof ObjectMap){
                arg=((ObjectMap) arg).getDefaultValue();
            }
            ResultSet generatedKeys = executor.getStatement().getGeneratedKeys();
            if (generatedKeys.next()) {
                if (typeHandler == null) {
                    synchronized (this) {
                        if (typeHandler == null) {
                            Field field = arg.getClass().getDeclaredField(fieldName);
                            typeHandler = typeHandlerFactory.getTypeHandler(field.getType(), Types.BIGINT);
                        }
                    }
                }
                Object result = typeHandler.getResult(generatedKeys, 1, Types.BIGINT);
                if (!ObjectUtil.isNull(property)) {
                    ObjectWrapper.wrapper(arg).set(property, result);
                }
            }
        }
    }
}

package com.dream.jdbc.core;

import com.dream.system.config.Configuration;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.util.exception.DreamRunTimeException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class JdbcStatementSetter implements StatementSetter {
    private Object[] args;

    public JdbcStatementSetter(Object[] args) {
        this.args = args;
    }

    @Override
    public void setter(PreparedStatement ps, Configuration configuration, Object arg) throws SQLException {
        TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                arg = args[i];
                if (arg == null) {
                    ps.setNull(i + 1, Types.NULL);
                } else {
                    TypeHandler typeHandler;
                    try {
                        typeHandler = typeHandlerFactory.getTypeHandler(arg.getClass(), Types.NULL);
                    } catch (TypeHandlerNotFoundException e) {
                        throw new DreamRunTimeException(e);
                    }
                    typeHandler.setParam(ps, i + 1, arg, Types.NULL);
                }
            }
        }
    }
}

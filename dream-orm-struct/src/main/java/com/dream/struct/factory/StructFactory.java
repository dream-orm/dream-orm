package com.dream.struct.factory;

import com.dream.antlr.smt.*;
import com.dream.struct.command.Delete;
import com.dream.struct.command.Insert;
import com.dream.struct.command.Query;
import com.dream.struct.command.Update;
import com.dream.system.config.Command;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;

public interface StructFactory {
    default MappedStatement compile(Query query, MethodInfo methodInfo) {
        return compile(Command.QUERY, query.statement(), methodInfo);
    }

    default MappedStatement compile(Update update, MethodInfo methodInfo) {
        return compile(Command.UPDATE, update.statement(), methodInfo);
    }

    default MappedStatement compile(Insert insert, MethodInfo methodInfo) {
        return compile(Command.INSERT, insert.statement(), methodInfo);
    }

    default MappedStatement compile(Delete delete, MethodInfo methodInfo) {
        return compile(Command.DELETE, delete.statement(), methodInfo);
    }

    default MappedStatement compile(Statement statement, MethodInfo methodInfo) {
        if (statement instanceof QueryStatement) {
            return compile(Command.QUERY, statement, methodInfo);
        } else if (statement instanceof UpdateStatement) {
            return compile(Command.UPDATE, statement, methodInfo);
        } else if (statement instanceof InsertStatement) {
            return compile(Command.INSERT, statement, methodInfo);
        } else if (statement instanceof DeleteStatement) {
            return compile(Command.DELETE, statement, methodInfo);
        } else {
            return compile(Command.NONE, statement, methodInfo);
        }
    }

    MappedStatement compile(Command command, Statement statement, MethodInfo methodInfo);
}

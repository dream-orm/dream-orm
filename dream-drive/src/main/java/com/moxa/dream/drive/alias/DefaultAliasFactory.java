package com.moxa.dream.drive.alias;

import com.moxa.dream.drive.dialect.MSSQLDialectFactory;
import com.moxa.dream.drive.dialect.MySQLDialectFactory;
import com.moxa.dream.drive.dialect.ORACLEDialectFactory;
import com.moxa.dream.drive.dialect.PGSQLDialectFactory;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Properties;

public class DefaultAliasFactory extends AbstractAliasFactory {
    public DefaultAliasFactory() {
        properties.put("mysql", MySQLDialectFactory.class.getName());
        properties.put("pgsql", PGSQLDialectFactory.class.getName());
        properties.put("mssql", MSSQLDialectFactory.class.getName());
        properties.put("sqlserver", MSSQLDialectFactory.class.getName());
        properties.put("oracle", ORACLEDialectFactory.class.getName());
        properties.put("MYSQL", MySQLDialectFactory.class.getName());
        properties.put("PGSQL", PGSQLDialectFactory.class.getName());
        properties.put("MSSQL", MSSQLDialectFactory.class.getName());
        properties.put("SQLSERVER", MSSQLDialectFactory.class.getName());
        properties.put("ORACLE", ORACLEDialectFactory.class.getName());
    }

    @Override
    public Properties getProp(Properties properties) {
        Properties cloneProperties = (Properties) this.properties.clone();
        if (!ObjectUtil.isNull(properties))
            cloneProperties.putAll(properties);
        return cloneProperties;
    }

}

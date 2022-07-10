package com.moxa.dream.driver.alias;

import com.moxa.dream.antlr.sql.ToMSSQL;
import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToORACLE;
import com.moxa.dream.antlr.sql.ToPGSQL;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Properties;

public class DefaultAliasFactory extends AbstractAliasFactory {
    public DefaultAliasFactory() {
        properties.put("mysql" , ToMYSQL.class.getName());
        properties.put("pgsql" , ToPGSQL.class.getName());
        properties.put("mssql" , ToMSSQL.class.getName());
        properties.put("sqlserver" , ToMSSQL.class.getName());
        properties.put("oracle" , ToORACLE.class.getName());
        properties.put("MYSQL" , ToMYSQL.class.getName());
        properties.put("PGSQL" , ToPGSQL.class.getName());
        properties.put("MSSQL" , ToMSSQL.class.getName());
        properties.put("SQLSERVER" , ToMSSQL.class.getName());
        properties.put("ORACLE" , ToORACLE.class.getName());
    }

    @Override
    public Properties getProp(Properties properties) {
        Properties cloneProperties = (Properties) this.properties.clone();
        if (!ObjectUtil.isNull(properties))
            cloneProperties.putAll(properties);
        return cloneProperties;
    }

}

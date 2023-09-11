package com.dream.generator;

import com.dream.system.util.SystemUtil;
import com.dream.util.exception.DreamRunTimeException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.*;

public class Generator {
    public Map<Integer, DataType> dataTypeMap = new HashMap<>();
    private DataSource dataSource;
    private GeneratorHandler generatorHandler;

    public Generator(DataSource dataSource, GeneratorHandler generatorHandler) {
        this.dataSource = dataSource;
        this.generatorHandler = generatorHandler;
        dataTypeMap.put(Types.BIT, new DataType("BIT", "Byte"));
        dataTypeMap.put(Types.TINYINT, new DataType("TINYINT", "Byte"));
        dataTypeMap.put(Types.SMALLINT, new DataType("SMALLINT", "Short"));
        dataTypeMap.put(Types.INTEGER, new DataType("INTEGER", "Integer"));
        dataTypeMap.put(Types.BIGINT, new DataType("BIGINT", "Long"));
        dataTypeMap.put(Types.FLOAT, new DataType("FLOAT", "Float"));
        dataTypeMap.put(Types.REAL, new DataType("REAL", "Float"));
        dataTypeMap.put(Types.DOUBLE, new DataType("DOUBLE", "Double"));
        dataTypeMap.put(Types.NUMERIC, new DataType("NUMERIC", "java.math.BigDecimal"));
        dataTypeMap.put(Types.DECIMAL, new DataType("DECIMAL", "java.math.BigDecimal"));
        dataTypeMap.put(Types.CHAR, new DataType("CHAR", "String"));
        dataTypeMap.put(Types.VARCHAR, new DataType("VARCHAR", "String"));
        dataTypeMap.put(Types.LONGVARCHAR, new DataType("LONGVARCHAR", "String"));
        dataTypeMap.put(Types.DATE, new DataType("DATE", "java.util.Date"));
        dataTypeMap.put(Types.TIME, new DataType("TIME", "java.sql.Time"));
        dataTypeMap.put(Types.TIMESTAMP, new DataType("TIMESTAMP", "java.util.Date"));
        dataTypeMap.put(Types.BINARY, new DataType("BINARY", "Byte[]"));
        dataTypeMap.put(Types.VARBINARY, new DataType("VARBINARY", "Byte[]"));
        dataTypeMap.put(Types.LONGVARBINARY, new DataType("LONGVARBINARY", "Byte[]"));
        dataTypeMap.put(Types.NULL, new DataType("NULL", "Object"));
        dataTypeMap.put(Types.OTHER, new DataType("OTHER", "Object"));
        dataTypeMap.put(Types.BLOB, new DataType("BLOB", "java.io.InputStream"));
        dataTypeMap.put(Types.CLOB, new DataType("CLOB", "String"));
        dataTypeMap.put(Types.BOOLEAN, new DataType("BOOLEAN", "Boolean"));
        dataTypeMap.put(Types.NCHAR, new DataType("NCHAR", "String"));
        dataTypeMap.put(Types.NVARCHAR, new DataType("NVARCHAR", "String"));
        dataTypeMap.put(Types.LONGNVARCHAR, new DataType("LONGNVARCHAR", "String"));
        dataTypeMap.put(Types.NCLOB, new DataType("NCLOB", "String"));
    }

    public void generate() {
        Map<String, Object> dataModel = new HashMap<>(64);
        dataModel.put("author", generatorHandler.author());
        dataModel.put("dateTime", generatorHandler.dateTime());
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(connection.getCatalog(), connection.getSchema(), null, new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (generatorHandler.support(tableName)) {
                    dataModel.put("table", tableName);
                    String remark = resultSet.getString("REMARKS");
                    dataModel.put("remark", remark);
                    List<Column> columnList = listColumn(connection, tableName);
                    dataModel.put("columns", columnList);
                    String boClassName = generatorHandler.boClassName(tableName);
                    if (boClassName != null && !boClassName.isEmpty()) {
                        dataModel.put("boClassName", boClassName);
                        dataModel.put("boPackageName", getPackageName(boClassName));
                        dataModel.put("boName", getName(boClassName));
                    }
                    String voClassName = generatorHandler.voClassName(tableName);
                    if (voClassName != null && !voClassName.isEmpty()) {
                        dataModel.put("voClassName", voClassName);
                        dataModel.put("voPackageName", getPackageName(voClassName));
                        dataModel.put("voName", getName(voClassName));
                    }
                    String dtoClassName = generatorHandler.dtoClassName(tableName);
                    if (dtoClassName != null && !dtoClassName.isEmpty()) {
                        dataModel.put("dtoClassName", dtoClassName);
                        dataModel.put("dtoPackageName", getPackageName(dtoClassName));
                        dataModel.put("dtoName", getName(dtoClassName));
                    }
                    String tableClassName = generatorHandler.tableClassName(tableName);
                    if (tableClassName != null && !tableClassName.isEmpty()) {
                        dataModel.put("tableClassName", tableClassName);
                        dataModel.put("tablePackageName", getPackageName(tableClassName));
                        dataModel.put("tableName", getName(tableClassName));
                    }
                    String serviceClassName = generatorHandler.serviceClassName(tableName);
                    if (serviceClassName != null && !serviceClassName.isEmpty()) {
                        dataModel.put("serviceClassName", serviceClassName);
                        dataModel.put("servicePackageName", getPackageName(serviceClassName));
                        dataModel.put("serviceName", getName(serviceClassName));
                    }
                    String controllerClassName = generatorHandler.controllerClassName(tableName);
                    if (serviceClassName != null && !serviceClassName.isEmpty()) {
                        dataModel.put("controllerClassName", controllerClassName);
                        dataModel.put("controllerPackageName", getPackageName(controllerClassName));
                        dataModel.put("controllerName", getName(controllerClassName));
                    }
                    String serviceImplClassName = generatorHandler.serviceImplClassName(tableName);
                    if (serviceClassName != null && !serviceClassName.isEmpty()) {
                        dataModel.put("serviceImplClassName", serviceImplClassName);
                        dataModel.put("serviceImplPackageName", getPackageName(serviceImplClassName));
                        dataModel.put("serviceImplName", getName(serviceImplClassName));
                    }
                    if (boClassName != null && !boClassName.isEmpty()) {
                        generate(dataModel, TemplateEnum.BO.getPath(), generatorHandler.sourceDir() + "\\" + boClassName.replace(".", "\\") + ".java");
                    }
                    if (voClassName != null && !voClassName.isEmpty()) {
                        generate(dataModel, TemplateEnum.VO.getPath(), generatorHandler.sourceDir() + "\\" + voClassName.replace(".", "\\") + ".java");
                    }
                    if (dtoClassName != null && !dtoClassName.isEmpty()) {
                        generate(dataModel, TemplateEnum.DTO.getPath(), generatorHandler.sourceDir() + "\\" + dtoClassName.replace(".", "\\") + ".java");
                    }
                    if (tableClassName != null && !tableClassName.isEmpty()) {
                        generate(dataModel, TemplateEnum.TABLE.getPath(), generatorHandler.sourceDir() + "\\" + tableClassName.replace(".", "\\") + ".java");
                    }
                    if (serviceClassName != null && !serviceClassName.isEmpty()) {
                        generate(dataModel, TemplateEnum.SERVICE.getPath(), generatorHandler.sourceDir() + "\\" + serviceClassName.replace(".", "\\") + ".java");
                    }
                    if (controllerClassName != null && !controllerClassName.isEmpty()) {
                        generate(dataModel, TemplateEnum.CONTROLLER.getPath(), generatorHandler.sourceDir() + "\\" + controllerClassName.replace(".", "\\") + ".java");
                    }
                    if (serviceImplClassName != null && !serviceImplClassName.isEmpty()) {
                        generate(dataModel, TemplateEnum.SERVICE_IMPL.getPath(), generatorHandler.sourceDir() + "\\" + serviceImplClassName.replace(".", "\\") + ".java");
                    }
                }
            }

        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
    }

    private String getPackageName(String className) {
        return className.substring(0, className.lastIndexOf("."));
    }

    private String getName(String className) {
        return className.substring(className.lastIndexOf(".") + 1);
    }


    private void generate(Map<String, Object> dataModel, String path, String sourceFile) throws IOException, TemplateException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path)) {
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                generate(dataModel, reader, sourceFile);
            }
        }
    }

    private void generate(Map<String, Object> dataModel, Reader reader, String sourceFile) throws IOException, TemplateException {
        Template template = new Template((String) dataModel.get("name"), reader, null, "utf8");
        File file = new File(sourceFile);
        if (!file.exists() || generatorHandler.override((String) dataModel.get("table"))) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(sourceFile);
            template.process(dataModel, fileWriter);
        }
    }

    public List<Column> listColumn(Connection connection, String table) {
        List<Column> columnList = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            Set<String> keySet = new HashSet<>();
            try (ResultSet resultSet = metaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), table)) {
                while (resultSet.next()) {
                    String columnName = resultSet.getString("COLUMN_NAME");
                    keySet.add(columnName);
                }
            }
            try (ResultSet resultSet = metaData.getColumns(connection.getCatalog(), connection.getSchema(), table, null)) {
                while (resultSet.next()) {
                    String columnName = resultSet.getString("COLUMN_NAME");
                    String remark = resultSet.getString("REMARKS");
                    int dataTypeId = resultSet.getInt("DATA_TYPE");
                    DataType dataType = dataTypeMap.get(dataTypeId);
                    Column column = new Column(columnName, remark, dataTypeId, dataType.getJdbcType(), dataType.getJavaType(), keySet.contains(columnName));
                    columnList.add(column);
                }
            }

        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
        return columnList;
    }

    public static class Column {
        private String columnName;
        private String attrName;
        private String remark;
        private Integer dataType;
        private String jdbcType;
        private String javaType;
        private boolean prim;

        public Column(String columnName, String remark, Integer dataType, String jdbcType, String javaType, boolean prim) {
            this.columnName = columnName;
            this.attrName = SystemUtil.underlineToCamel(columnName);
            this.remark = remark;
            this.dataType = dataType;
            this.jdbcType = jdbcType;
            this.javaType = javaType;
            this.prim = prim;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getAttrName() {
            return attrName;
        }

        public String getRemark() {
            return remark;
        }

        public Integer getDataType() {
            return dataType;
        }

        public String getJdbcType() {
            return jdbcType;
        }

        public String getJavaType() {
            return javaType;
        }

        public boolean isPrim() {
            return prim;
        }
    }

    class DataType {
        private String jdbcType;
        private String javaType;

        public DataType(String jdbcType, String javaType) {
            this.jdbcType = jdbcType;
            this.javaType = javaType;
        }

        public String getJdbcType() {
            return jdbcType;
        }

        public String getJavaType() {
            return javaType;
        }
    }

}

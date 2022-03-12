package com.moxa.dream.module.typehandler.handler;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class BlobTypeHandler extends BaseTypeHandler<byte[]> {

    @Override
    public void setParameter(PreparedStatement ps, int i, byte[] parameter, int jdbcType) throws SQLException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parameter);
        ps.setBinaryStream(i, byteArrayInputStream, parameter.length);
    }

    @Override
    public byte[] getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        Blob blob = rs.getBlob(columnIndex);
        if (null != blob) {
            return blob.getBytes(1, (int) blob.length());
        }
        return null;
    }

    @Override
    public int getNullType() {
        return Types.BLOB;
    }
}

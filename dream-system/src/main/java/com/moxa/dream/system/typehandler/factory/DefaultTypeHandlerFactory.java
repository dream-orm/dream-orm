package com.moxa.dream.system.typehandler.factory;

import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;
import com.moxa.dream.system.typehandler.wrapper.*;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.HashMap;
import java.util.Map;


public class DefaultTypeHandlerFactory implements TypeHandlerFactory {
    protected Map<Integer, TypeHandler> typeHandlerMap = new HashMap<>();

    public DefaultTypeHandlerFactory() {
        wrapper(getTypeHandlerWrapperList());
    }

    @Override
    public void wrapper(TypeHandlerWrapper[] typeHandlerWrapperList) {
        if (!ObjectUtil.isNull(typeHandlerWrapperList)) {
            for (TypeHandlerWrapper typeHandlerWrapper : typeHandlerWrapperList) {
                TypeHandler typeHandler = typeHandlerWrapper.getTypeHandler();
                if (typeHandler != null) {
                    typeHandler = wrapper(typeHandler);
                    Integer[] typeCode = typeHandlerWrapper.typeCode();
                    if (!ObjectUtil.isNull(typeCode)) {
                        for (int typeHandlerCode : typeCode) {
                            typeHandlerMap.put(typeHandlerCode, typeHandler);
                        }
                    }
                }
            }
        }
    }

    @Override
    public TypeHandler getTypeHandler(Class javaType, int jdbcType) {
        TypeHandler typeHandler = getTypeHandler(TypeUtil.hash(javaType, jdbcType));
        if (typeHandler == null)
            return getNoneTypeHandler(javaType, jdbcType);
        else
            return typeHandler;
    }

    protected TypeHandler getTypeHandler(int typeCode) {
        TypeHandler typeHandler = typeHandlerMap.get(typeCode);
        return typeHandler;
    }

    protected TypeHandlerWrapper[] getTypeHandlerWrapperList() {
        return new TypeHandlerWrapper[]{
                new BigDecimalTypeHandlerWrapper(),
                new BlobInputStreamTypeHandlerWrapper(),
                new BlobTypeHandlerWrapper(),
                new BooleanTypeHandlerWrapper(),
                new ByteTypeHandlerWrapper(),
                new SqlDateTypeHandlerWrapper(),
                new UtilDateTypeHandlerWrapper(),
                new DoubleTypeHandlerWrapper(),
                new FloatTypeHandlerWrapper(),
                new IntegerTypeHandlerWrapper(),
                new LongTypeHandlerWrapper(),
                new ShortTypeHandlerWrapper(),
                new StringTypeHandlerWrapper(),
                new TimeStampTypeHandlerWrapper(),
                new TimeTypeHandlerWrapper(),
                new ObjectTypeHandlerWrapper()
        };
    }

    protected TypeHandler wrapper(TypeHandler typeHandler) {
        return typeHandler;
    }

    protected TypeHandler getNoneTypeHandler(Class javaType, int jdbcType) {
        throw new DreamRunTimeException("类型选择器未找到,javaType '" + javaType.getTypeName() + "',jdbcType '" + TypeUtil.getTypeName(jdbcType) + "'");
    }
}

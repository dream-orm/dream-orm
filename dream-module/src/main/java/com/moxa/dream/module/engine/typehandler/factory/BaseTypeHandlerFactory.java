package com.moxa.dream.module.engine.typehandler.factory;

import com.moxa.dream.module.engine.typehandler.handler.TypeHandler;
import com.moxa.dream.module.engine.typehandler.util.TypeUtil;
import com.moxa.dream.module.engine.typehandler.wrapper.*;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseTypeHandlerFactory implements TypeHandlerFactory {
    protected Map<Integer, TypeHandler> typeHandlerMap = new HashMap<>();

    public BaseTypeHandlerFactory() {
        wrapper(getTypeHandlerWrapperList());
    }

    @Override
    public void wrapper(List<? extends TypeHandlerWrapper> typeHandlerWrapperList) {
        if (!ObjectUtil.isNull(typeHandlerWrapperList)) {
            for (TypeHandlerWrapper typeHandlerWrapper : typeHandlerWrapperList) {
                TypeHandler typeHandler = typeHandlerWrapper.getTypeHandler();
                if (typeHandler != null) {
                    typeHandler = wrapper(typeHandler);
                    ObjectUtil.requireNonNull(typeHandler, "Property 'typeHandler' is required");
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

    protected List<TypeHandlerWrapper> getTypeHandlerWrapperList() {
        List<TypeHandlerWrapper> defaultTypeHandlerWrapperList = Arrays.asList(
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
                new TimeTypeHandlerWrapper()
        );
        return defaultTypeHandlerWrapperList;
    }

    protected TypeHandler wrapper(TypeHandler typeHandler) {
        return typeHandler;
    }

    protected TypeHandler getNoneTypeHandler(Class javaType, int jdbcType) {
        throw new NullPointerException("typeHandler not found,javaType '" + javaType.getTypeName() + "',jdbcType '" + TypeUtil.getTypeName(jdbcType) + "'");
    }
}

package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.annotation.Validated;
import com.moxa.dream.template.validate.Validator;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ValidateMapper extends AbstractMapper {
    public ValidateMapper(Session session) {
        super(session);
    }

    @Override
    protected final MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        MethodInfo methodInfo = getValidateMethodInfo(configuration, tableInfo, type, arg);
        if (arg != null) {
            Class validateType = arg.getClass();
            List<Field> fieldList = ReflectUtil.findField(validateType);
            if (!ObjectUtil.isNull(fieldList)) {
                ValidatePackageList validatePackageList = new ValidatePackageList();
                for (Field field : fieldList) {
                    Annotation[] annotations = field.getAnnotations();
                    if (!ObjectUtil.isNull(annotations)) {
                        for (Annotation annotation : annotations) {
                            Class<? extends Annotation> annotationType = annotation.annotationType();
                            Validated validatedAnnotation = annotationType.getAnnotation(Validated.class);
                            if (validatedAnnotation != null) {
                                Map<String, Object> paramMap = getParamMap(annotation);
                                Class<? extends Validator> validatorType = validatedAnnotation.value();
                                Validator validator = ReflectUtil.create(validatorType);
                                if (validator.isValid(session, validateType, field, getCommand())) {
                                    validatePackageList.validatePackageList.add(new ValidatePackage(validator, field.getName(), paramMap));
                                }
                            }
                        }
                    }

                }
                if (!validatePackageList.validatePackageList.isEmpty()) {
                    methodInfo.set(ValidatePackageList.class, validatePackageList);
                }
            }
        }
        return methodInfo;
    }

    protected abstract MethodInfo getValidateMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg);

    protected Map<String, Object> getParamMap(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        Method[] methods = annotationType.getDeclaredMethods();
        Map<String, Object> paramMap = new HashMap<>(4);
        if (!ObjectUtil.isNull(methods)) {
            for (Method method : methods) {
                try {
                    Object value = method.invoke(annotation);
                    paramMap.put(method.getName(), value);
                } catch (Exception e) {
                    throw new DreamRunTimeException(e);
                }
            }
        }
        return paramMap;
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg, Consumer<MappedStatement> mappedStatementConsumer) {
        if (arg != null) {
            ValidatePackageList validatePackageList = methodInfo.get(ValidatePackageList.class);
            if (validatePackageList != null) {
                Command command = getCommand();
                if (command == Command.BATCH) {
                    List<?> argList = (List) arg;
                    for (int i = 0; i < argList.size(); i++) {
                        validate(argList.get(i), validatePackageList);
                    }
                } else {
                    validate(arg, validatePackageList);
                }
            }
        }
        return executeValidate(methodInfo, arg, mappedStatementConsumer);
    }

    protected Object executeValidate(MethodInfo methodInfo, Object arg, Consumer<MappedStatement> mappedStatementConsumer) {
        return super.execute(methodInfo, arg, mappedStatementConsumer);
    }

    protected void validate(Object arg, ValidatePackageList validatePackageList) {
        ObjectWrapper wrapper = ObjectWrapper.wrapper(arg);
        for (ValidatePackage validatePackage : validatePackageList.validatePackageList) {
            validatePackage.validate(wrapper);
        }
    }

    protected abstract Command getCommand();

    class ValidatePackageList {
        private List<ValidatePackage> validatePackageList = new ArrayList<>();
    }

    class ValidatePackage {
        private Validator validator;

        private String name;
        private Map<String, Object> paramMap;

        public ValidatePackage(Validator validator, String name, Map<String, Object> paramMap) {
            this.validator = validator;
            this.name = name;
            this.paramMap = paramMap;
        }

        public void validate(ObjectWrapper wrapper) {
            validator.validate(wrapper.get(name), paramMap);
        }
    }
}

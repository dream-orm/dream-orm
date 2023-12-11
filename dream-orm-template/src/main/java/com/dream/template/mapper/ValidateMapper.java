package com.dream.template.mapper;

import com.dream.system.config.Command;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.table.TableInfo;
import com.dream.template.annotation.Validated;
import com.dream.template.validate.ValidatedRunTimeException;
import com.dream.template.validate.Validator;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.reflect.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                                Map<String, Object> paramMap = ReflectUtil.getAnnotationMap(annotation);
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

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg) {
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
        return executeValidate(methodInfo, arg);
    }

    protected Object executeValidate(MethodInfo methodInfo, Object arg) {
        return super.execute(methodInfo, arg);
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
            String validateMsg = validator.validate(wrapper.get(name), paramMap);
            if (validateMsg != null && !validateMsg.isEmpty()) {
                throw new ValidatedRunTimeException(validateMsg);
            }
        }
    }
}

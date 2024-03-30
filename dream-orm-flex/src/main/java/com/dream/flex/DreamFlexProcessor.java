package com.dream.flex;

import com.dream.flex.annotation.EnableFlexAPT;
import com.dream.system.annotation.Column;
import com.dream.system.annotation.Table;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;

/**
 * 编译生成代码
 */
public class DreamFlexProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.typeUtils = processingEnv.getTypeUtils();
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            Set<? extends Element> flexAptElements = roundEnv.getElementsAnnotatedWith(EnableFlexAPT.class);
            if (flexAptElements == null || flexAptElements.isEmpty()) {
                return true;
            } else if (flexAptElements.size() > 1) {
                throw new DreamRunTimeException("注解" + EnableFlexAPT.class.getName() + "存在多个");
            }
            Element flexAPTElement = flexAptElements.iterator().next();
            EnableFlexAPT enableFlexAPT = flexAPTElement.getAnnotation(EnableFlexAPT.class);
            if (enableFlexAPT != null) {
                String classSuffix = enableFlexAPT.classSuffix();
                String dir = enableFlexAPT.dir();
                Set<? extends Element> tableElements = roundEnv.getElementsAnnotatedWith(Table.class);
                for (Element tableElement : tableElements) {
                    String entityClass = tableElement.toString();
                    String className = getClassName(entityClass);
                    String tableDefPackage = buildTableDefPackage(entityClass, dir);
                    String tableDefClassName = className.concat(classSuffix);
                    Table table = tableElement.getAnnotation(Table.class);
                    String tableName = ObjectUtil.isNull(table.value()) ? SystemUtil.camelToUnderline(className) : table.value();
                    List<String> columnList = columnInfoList((TypeElement) tableElement, fieldElement -> {
                        Column column = fieldElement.getAnnotation(Column.class);
                        if (column != null) {
                            String fieldName = fieldElement.toString();
                            String columnName = ObjectUtil.isNull(column.value()) ? SystemUtil.camelToUnderline(fieldName) : column.value();
                            return columnName;
                        } else {
                            return null;
                        }
                    });
                    String content = buildTableDef(tableName, tableDefPackage, tableDefClassName, columnList);
                    try {
                        processGenClass(tableDefPackage, tableDefClassName, content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(Table.class.getCanonicalName());
        return supportedAnnotationTypes;
    }

    public List<String> columnInfoList(TypeElement classElement, Function<Element, String> function) {
        List<String> columnInfoList = new ArrayList<>();
        for (Element fieldElement : classElement.getEnclosedElements()) {
            if (ElementKind.FIELD == fieldElement.getKind()) {
                String column = function.apply(fieldElement);
                if (column != null && !column.isEmpty()) {
                    columnInfoList.add(column);
                }
            }
        }
        classElement = (TypeElement) typeUtils.asElement(classElement.getSuperclass());
        if (classElement != null) {
            columnInfoList.addAll(columnInfoList(classElement, function));
        }
        return columnInfoList;
    }

    private String buildTableDefPackage(String entityClass, String dir) {
        Deque<String> deque = new ArrayDeque<>();
        if (dir.startsWith("/")) {
            dir = dir.substring(1);
        } else {
            String[] strs = entityClass.split("\\.");
            for (int i = 0; i < strs.length; i++) {
                if (!strs[i].isEmpty()) {
                    deque.add(strs[i]);
                }
            }
        }
        String[] parentFiles = dir.split("\\.\\./");
        for (String parentFile : parentFiles) {
            deque.pollLast();
            String[] files = parentFile.split("\\./");
            for (String file : files) {
                if (!file.isEmpty()) {
                    deque.addLast(file.replace("/", "."));
                }
            }

        }
        return String.join(".", deque);
    }

    private String buildTableDef(String table, String tableDefPackage, String tableDefClassName, List<String> columnList) {
        StringBuilder content = new StringBuilder("package ");
        content.append(tableDefPackage).append(";\n\n");
        content.append("import com.dream.flex.def.ColumnDef;\n");
        content.append("import com.dream.flex.def.TableDef;\n\n");
        content.append("public class ").append(tableDefClassName).append(" extends TableDef {\n\n");
        content.append("    public static final ")
                .append(tableDefClassName)
                .append(' ')
                .append(table)
                .append(" = new ")
                .append(tableDefClassName)
                .append("();\n\n");
        columnList.forEach((column) -> {
            content.append("    public final ColumnDef ")
                    .append(column)
                    .append(" = new ColumnDef(")
                    .append("this,")
                    .append("\"").append(column).append("\");\n");
        });
        content.append("\n    public final ColumnDef[]columns")
                .append(" = new ColumnDef[]{")
                .append(String.join(",", columnList))
                .append("};\n");
        content
                .append("\n    public ").append(tableDefClassName).append("() {\n")
                .append("        this").append("(null);\n")
                .append("    }")
                .append("\n    public ").append(tableDefClassName).append("(String alias) {\n")
                .append("        this").append("(\"").append(table).append("\",alias);\n")
                .append("    }")
                .append("\n    public ").append(tableDefClassName).append("(String table,String alias) {\n")
                .append("        super(table,alias);\n")
                .append("    }\n\n}\n");
        return content.toString();
    }

    private void processGenClass(String tableDefPackage, String tableDefClassName, String content) throws IOException {
        Writer writer = null;
        try {
            JavaFileObject sourceFile = filer.createSourceFile(tableDefPackage + "." + tableDefClassName);
            writer = sourceFile.openWriter();
            writer.write(content);
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String getClassName(String str) {
        return str.substring(str.lastIndexOf(".") + 1);
    }

    private String getTableClassName(String viewStr) {
        String paramStr = viewStr.substring(viewStr.lastIndexOf("(") + 1, viewStr.lastIndexOf(")"));
        String[] params = paramStr.split(",");
        String tableClassName = null;
        for (String param : params) {
            param = param.trim();
            if (param.contains("=")) {
                if (param.startsWith("value=")) {
                    tableClassName = param.substring(6);
                    break;
                }
            } else {
                tableClassName = param;
                break;
            }
        }
        if (tableClassName != null && tableClassName.endsWith(".class")) {
            tableClassName = tableClassName.substring(0, tableClassName.length() - 6);
        }
        return tableClassName;
    }
}

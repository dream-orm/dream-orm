package ${boPackageName};

import com.dream.system.annotation.View;
import lombok.Data;
import ${tableClassName};

import java.sql.Types;

/**
 * @Description: ${remark!''}
 * @Author: ${author!''}
 * @Date: ${dateTime!''}
 */
@Data
@View(${tableName}.class)
public class ${boName} {
<#list columns as column>

    /**
     * ${column.remark!''}
     */
    private ${column.javaType} ${column.attrName};
</#list>
}

package ${tablePackageName};

import com.dream.system.annotation.Column;
import com.dream.system.annotation.Id;
import com.dream.system.annotation.Table;
import lombok.Data;

import java.sql.Types;

/**
 * @Description: ${remark!''}
 * @Author: ${author!''}
 * @Date: ${dateTime!''}
 */
@Data
@Table("${table}")
public class ${tableName} {
<#list columns as column>

    /**
     * ${column.remark!''}
     */
    <#if column.prim>
    @Id
    </#if>
    @Column(value = "${column.columnName}", jdbcType = Types.${column.jdbcType})
    private ${column.javaType} ${column.attrName};
</#list>
}

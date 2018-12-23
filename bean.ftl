package ${bean.packageName!"com.unit_test.entity"};


import lombok.Data;
<#list bean.importList! as item>
import ${item}
</#list>

@Data
public class ${bean.className} {
<#list bean.columns! as column>
    /**
     * ${column.comment!}
     */
     private ${column.type} ${column.name};
</#list>
}


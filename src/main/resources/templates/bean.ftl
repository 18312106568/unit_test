package ${bean.packageName};


import lombok.Data;
<#list bean.importList as item>
import ${item}
</#list>

@Data
public class ${bean.className} {

    <#list bean.fieldList as field>
         private ${field.type} ${field.name};

    </#list>
}


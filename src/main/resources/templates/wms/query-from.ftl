<form code="entity${bean.projectName}NorQueryForm">
<#list bean.fieldList as field>
    <attribute code="${field.name}">
        <data-item-code>${field.name?upper_case}</data-item-code>
        <message msgid="di.${field.name}"/>
    </attribute>
</#list>
</form>

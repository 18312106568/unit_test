<form code="entity${bean.projectName?cap_first}PitchForm">
<#list bean.fieldList as field>
    <attribute code="${field.name}">
        <data-item-code>${field.name?upper_case}</data-item-code>
        <message msgid="di.${field.name}"/>
    </attribute>
</#list>
</form>
<form code="entity${bean.projectName?cap_first}NorQueryForm">
    <#list bean.fieldList as field>
        <attribute code="${field.name}">
            <data-item-code>${field.name?upper_case}</data-item-code>
            <message msgid="di.${field.name}"/>
        </attribute>
    </#list>
</form>

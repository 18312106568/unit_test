    <tree-view code="wms-${bean.projectName}-tree">
        <message msgid="module.Wms${bean.projectName?cap_first}.name"/>
        <tree-node>
            <type>text</type>
            <property name="text">
                <value>operation.screen.name</value>
            </property>
            <property name="href">
                <value>#</value>
            </property>
            <tree-node>
                <type>text</type>
                <property name="text">
                    <value>operation.close.name</value>
                </property>
                <property name="href">
                    <value>javascript:window.close();</value>
                </property>
            </tree-node>
        </tree-node>
        <tree-node>
            <type>text</type>
            <property name="text">
                <value>operation.row.name</value>
            </property>
            <property name="href">
                <value>#</value>
            </property>
    <#list bean.module! as module>
            <tree-node>
                <type>module</type>
                <property name="moduleId">
                    <value>${module.id}</value>
                </property>
                <property name="target">
                    <value>${module.target}</value>
                </property>
                <property name="optype">
                    <value>row</value>
                </property>
            </tree-node>
    </#list>
        </tree-node>
    </tree-view>

    <query-def code="entity${bean.projectName?cap_first}Query">
        <disp-style>
            <column-config>
                <content>
                    <class-name>radio</class-name>
                    <property name="formName">
                        <value>radioForm</value>
                    </property>
                </content>
            </column-config>
            <column-config>
                <content>
                    <class-name>sequence</class-name>
                    <property name="fieldName">
                        <value>rownum</value>
                    </property>
                </content>
            </column-config>

        <#list bean.fieldList as field>
            <column-config>
                <content>
                    <class-name>data-field</class-name>
                    <property name="fieldname">
                        <value>${field.name}</value>
                    </property>
                </content>
            </column-config>

        </#list>

            <row-config>
                <class-name>control</class-name>
                <data>false</data>
                <property name="action">
                    <value>/wms_${bean.projectName}_query</value>
                </property>
            </row-config>
            <row-config>
                <class-name>sort_title</class-name>
                <data>false</data>
                <property name="queryAction">
                    <value>/wms_${bean.projectName}_query</value>
                </property>
                <property name="htmlProps">
                    <value>str:class="data002"</value>
                </property>
            </row-config>
            <row-config>
                <class-name>data</class-name>
                <data>true</data>
                <property name="htmlProps">
                    <value>str:class="data004" id="datarow"</value>
                </property>
            </row-config>
            <row-config>
                <class-name>page2</class-name>
                <data>false</data>
                <property name="queryAction">
                    <value>/wms_${bean.projectName}_query</value>
                </property>
                <property name="htmlProps">
                    <value>str:class="data006"</value>
                </property>
            </row-config>
            <table-property>
                class="query" cellPadding="0" cellspacing="1" background="/helix/pic/tab_bg.JPG"
            </table-property>
        </disp-style>

    <#list bean.fieldList as field>
            <attribute code="${field.name}">
                <title-msg msgid="di.${field.name}"/>
                <data-item-code>${field.name?upper_case}</data-item-code>
                <readonly>false</readonly>
                <required>false</required>
            </attribute>
    </#list>
        <sql-def>
            <sql>
                ${bean.sql}
            </sql>
        </sql-def>
    <#list bean.pkList as pk>
        <pkcolumn-name>${pk.name}</pkcolumn-name>
    </#list>
        <record-num-perpage>10</record-num-perpage>
    </query-def>

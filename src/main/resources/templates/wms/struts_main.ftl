
    <action forward="wms.${bean.projectName}.main" path="/to_${bean.projectName}_main" scope="request"  validate="false">
        <set-property value="all" property="modules"/>
    </action>
    <action name="entity${bean.projectName?cap_first}PitchForm" path="/wms_${bean.projectName}_pitch" scope="request" type="com.est.helix.wms.${bean.projectName}.PitchAction" validate="false">
        <set-property value="all" property="modules"/>
        <forward name="error" path="wms.${bean.projectName}.pitch"/>
        <forward name="success" path="wms.${bean.projectName}.pitch"/>
    </action>
    <action name="entity${bean.projectName?cap_first}QueryForm" path="/wms_${bean.projectName}_query" type="com.est.helix.actions.mdActions.BaseQueryAction" scope="session" validate="false">
        <set-property value="all" property="modules"/>
        <forward name="error" path="wms.${bean.projectName}.main" />
        <forward name="success" path="wms.${bean.projectName}.main" />
    </action>
    <action name="entity${bean.projectName?cap_first}NorQueryForm" path="/wms_${bean.projectName}_nor_query" type="com.est.helix.actions.mdActions.NorQueryAction" scope="session"  validate="false">
        <set-property value="all" property="modules"/>
        <forward name="error" path="wms.${bean.projectName}.main" />
        <forward name="success" path="wms.${bean.projectName}.main" />
    </action>

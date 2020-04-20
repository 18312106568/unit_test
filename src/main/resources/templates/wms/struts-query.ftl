<action name="${bean.queryFormName}"  path="${bean.queryFormAction}"
        type="com.est.helix.actions.mdActions.${bean.projectName?cap_first}QueryAction" scope="request"  validate="false">
    <set-property property="modules" value="all"/>
    <forward name="success" path="wms.${bean.projectName?cap_first}.main"/>
    <forward name="error" path="wms.${bean.projectName?cap_first}.main"/>
</action>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/helix-core.tld" prefix="helix" %>

<%String action = (String)request.getAttribute("action");%>
<table width=100% height=0
       border=0 cellpadding=0 cellspacing=8 bgcolor="#DEE1E4" style="border:2px solid #004E9B">
    <html:form action="<%=action%>">
<#list bean.fieldList as field>
        <tr>
            <td><helix:title property="${field.name}"/> </td>
            <td><helix:property property="${field.name}"/></td>
        </tr>
</#list>

        <tr>
            <td><html:submit property="ok" value=" 查询 "/><html:button property="clear" value = " 清空 " onclick="clearForm(this.form)"/></td>
        </tr>
    </html:form>

    <html:javascript formName="entity${bean.projectName?cap_first}NorQueryForm" dynamicJavascript="true" staticJavascript="false"/>
</table>
<SCRIPT for=entity${bean.projectName?cap_first}NorQueryForm event=onkeypress LANGUAGE=javascript>
    performNorQuery(entity${bean.projectName?cap_first}NorQueryForm);
</SCRIPT>

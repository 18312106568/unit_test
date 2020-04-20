<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/helix-core.tld" prefix="helix" %>
<%@ page import="com.est.helix.Constants" %>

<body>
<table align=center width="100%" border="1">
    <tr>
        <td height="180">
            <div align="center">
                <helix:showForm action="/wms_${bean.projectName}_pitch.mid" readOnly="false"/>
            </div>
        </td>
    </tr>
</table>
</body>

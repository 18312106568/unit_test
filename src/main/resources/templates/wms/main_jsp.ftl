<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/helix-core.tld" prefix="helix" %>
<%@ page import="com.est.helix.report.*" %>


<div id=div32 style="Z-INDEX: 1; LEFT: 180px; VISIBILITY: hidden; WIDTH: 800px; POSITION: absolute; TOP: 180px; HEIGHT: 322px;DISPLAY: none">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right" bgcolor="#004E9B">
                <a href="javascript:toExit('hide','div32')"><img src="<%=request.getContextPath()%>/pic/x.png" width="15" height="15"></a>
            </td>
        </tr>
    </table>
    <helix:showForm action="/wms_${bean.projectName}_nor_query.mid" readOnly="false"/>
</div>

<div id="Layer1" style="position:absolute; left:1px; top:147px; width:148px; height:199px; z-index:2;">
    <table width="148" border="0" cellspacing="0" cellpadding="0" >
        <tr>
            <td align="center" valign="bottom"><helix:showTree tree="wms-${bean.projectName}-tree"/></td>
        </tr>
    </table>
</div>


<table width="850" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="#E6E6E6">
        <td width="5" rowspan="3">&nbsp;</td>
        <td height="35" valign="top">
            <table height="30" border="0" cellpadding="0" cellspacing="0" background="pic/new_patten.gif">
                <tr>
                    <td width="22" align="center" valign="top"> <strong><img src="pic/new_hand.gif" width="22" height="26"></strong>
                    </td>
                    <td width="79" align="left" valign="top"><a href="javascript:toExit('show','div32')"><img name="new_line02" src="pic/new_line02.gif" width="79" height="26" border="0" alt=""></a></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td height="1"></td>
    </tr>
    <tr>
        <td align="left">
            <helix:showScrollResult queryName="entity${bean.projectName?cap_first}Query"/>
            </p>
            <p>&nbsp;</p>
        </td>
        <td>
        <td>
            <DEADBEEF:COMBOBOX size="44" name="server">
                <option selected>deadbeef.com
                <option>microsoft.com
                <option>sageconspiracy.com
            </DEADBEEF:COMBOBOX>
        </td>
        </td>
    </tr>
</table>

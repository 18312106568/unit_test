package com.est.helix.wms.${bean.projectName};

import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.logging.*;

import com.est.helix.struts.*;


public class ${bean.projectName?cap_first}QueryAction extends HelixAction {
    static private Log log = LogFactory.getLog(${bean.projectName?cap_first}QueryAction.class.getName());

    public ActionForward go(HelixActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws java.lang.Exception {
        //TODO
        return (mapping.findForward("success"));
    }
}

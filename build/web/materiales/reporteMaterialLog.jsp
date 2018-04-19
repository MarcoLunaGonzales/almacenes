<%-- 
    Document   : reporteMaterialLog
    Created on : 10-07-2017, 05:58:42 PM
    Author     : rcondori
--%>

<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.cofar.util.*"%>
<%@ page import="com.cofar.bean.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>


<%
            Materiales material = (Materiales) request.getSession().getAttribute("material");

            Map parameters = new HashMap();


            parameters.put("codMaterial", material.getCodMaterial());
            parameters.put("nombreMaterial", material.getNombreMaterial());
            parameters.put("nombreUnidadMedida", material.getUnidadesMedida().getNombreUnidadMedida());
            parameters.put("nombreMaterialCompra", material.getNombreComercialMaterial());
            parameters.put("nombreUnidadMedidaCompra", material.getUnidadesMedidaCompra().getNombreUnidadMedida());
            parameters.put("nombreEstado", material.getEstadoRegistro().getNombreEstadoRegistro());
            parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
            
            String reportFileName = application.getRealPath("/materiales/reporteMaterialLog.jasper");
           
            application.getRealPath("");

            Connection con = null;
            con = Util.openConnection(con);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
            session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            System.out.println(jasperPrint.getLocaleCode());

%>

<html>
    <body bgcolor="white" onload="location='../servlets/pdf'" >
    </body>
</html>

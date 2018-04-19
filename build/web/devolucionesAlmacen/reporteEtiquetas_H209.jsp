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
Procesando ......


<%
            int codSolicitudDevolucion = Integer.parseInt(request.getParameter("codSolicitudDevolucion"));

            Map parameters = new HashMap();


            parameters.put("codigo", codSolicitudDevolucion);

            parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));

            String reportFileName = application.getRealPath("/devolucionesAlmacen/impresionEtiquetasSolicitudDevolucionH209.jasper");



            System.out.println(reportFileName);
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




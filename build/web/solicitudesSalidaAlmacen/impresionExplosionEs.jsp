<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Connection"%>

<%@ page import="com.cofar.util.*"%>
<%@ page import="com.cofar.bean.*"%>
<%@ page import="java.io.*"%>

<%@ page import="java.sql.*"%>
<%
            System.out.println("test report click jasper");     
            Map parameters = new HashMap();
            Connection con = null;
            con = Util.openConnection(con);
            //parameters.put("dirLogoCofar", application.getRealPath("/img/nuevoLogoCofar.jpg"));
            parameters.put("codExplosionEmpaqueSecundarioAlmacen", Integer.valueOf(request.getParameter("codExplosionEmpaqueSecundarioAlmacen")));
            String reportFileName = application.getRealPath("/solicitudesSalidaAlmacen/impresionExplosionEs.jasper");
            System.out.println("report "+reportFileName);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
            session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            con.close();
            


%>

<html>
    <body bgcolor="white" onload="location='../servlets/pdf'" >
    </body>
</html>




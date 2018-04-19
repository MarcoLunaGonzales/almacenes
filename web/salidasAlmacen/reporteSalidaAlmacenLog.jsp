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
            SalidasAlmacen salidasAlmacen = (SalidasAlmacen) request.getSession().getAttribute("salidasAlmacen");
            System.out.println("salida almacen: "+salidasAlmacen.getCodSalidaAlmacen());
            Map parameters = new HashMap();
            parameters.put("codSalidaAlmacen", salidasAlmacen.getCodSalidaAlmacen());


            String reportFileName = application.getRealPath("/salidasAlmacen/reporteSalidaAlmacenLog.jasper");
            
            Connection con = null;
            con = Util.openConnection(con);
            parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
            session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            System.out.println(jasperPrint.getLocaleCode());



%>

<html>
    <body bgcolor="white" onload="location='../servlets/pdf'" >
    </body>
</html>




package salidasAlmacen;

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

            Map parameters = new HashMap();


            parameters.put("codigo", salidasAlmacen.getCodSalidaAlmacen());


            String reportFileName = application.getRealPath("/salidasAlmacen/reporteSalida.jasper");
            String reportFileName2 = application.getRealPath("");
            reportFileName2+="/salidasAlmacen/";

            //reportFileName2="D:\\PROYECTO\\ALMACENES\\web\\salidasAlmacen\\";
            System.out.println(reportFileName2);
            Connection con = null;
            con = Util.openConnection(con);

//SUBREPORT_DIR

            File logofile = new File(application.getRealPath("/img/cofar.png"));
            java.io.FileInputStream inputStream = new java.io.FileInputStream(logofile);

            parameters.put("logo", inputStream);
            parameters.put("SUBREPORT_DIR", reportFileName2);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
            session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            System.out.println(jasperPrint.getLocaleCode());



%>

<html>
    <body bgcolor="white" onload="location='../servlets/pdf'" >
    </body>
</html>




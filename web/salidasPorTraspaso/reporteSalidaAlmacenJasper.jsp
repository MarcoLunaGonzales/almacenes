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
            String codSalidasAlmacen = String.valueOf(request.getSession().getAttribute("codSalidaAlmacen"));
            String reporte = request.getParameter("reporte")==null?"":request.getParameter("reporte");

            Map parameters = new HashMap();


            parameters.put("codigo", Integer.parseInt(codSalidasAlmacen));


            String reportFileName = application.getRealPath("/salidasPorTraspaso/"+reporte);
           
            String reportFileName2 = application.getRealPath("");
            reportFileName2+="/salidasPorTraspaso/";
            application.getRealPath("");

            //reportFileName2="D:\\PROYECTO\\ALMACENES\\web\\salidasAlmacen\\";
            System.out.println(reportFileName2);
            System.out.println("el reportex :::: "+reporte);
            Connection con = null;
            con = Util.openConnection(con);

            parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
            parameters.put("SUBREPORT_DIR", reportFileName2);
            System.out.println("entro detalle "+reportFileName+" " +reportFileName2);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
            session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            System.out.println(jasperPrint.getLocaleCode());


%>

<html>
    <body bgcolor="white" onload="location='../servlets/pdf'" >
    </body>
</html>




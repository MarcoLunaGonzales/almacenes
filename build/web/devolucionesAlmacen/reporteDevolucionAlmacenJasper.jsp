package devolucionesAlmacen;

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
            IngresosAlmacen ingresosAlmacen = (IngresosAlmacen) request.getSession().getAttribute("ingresosAlmacen");

            Map parameters = new HashMap();


            parameters.put("codigo", ingresosAlmacen.getDevoluciones().getCodDevolucion());
            parameters.put("COD_ALMACEN", ingresosAlmacen.getAlmacenes().getCodAlmacen());
            parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
            
            String reportFileName = application.getRealPath("/devolucionesAlmacen/reporteDevolucion.jasper");
            String sp=System.getProperty("file.separator");
            System.out.println("COD_ALMACEN"+ ingresosAlmacen.getAlmacenes().getCodAlmacen());
            String reportFileName2 = application.getRealPath("");
            reportFileName2 += sp+"devolucionesAlmacen"+sp;

            System.out.println(reportFileName2);
            parameters.put("SUBREPORT_DIR", reportFileName2);
            Connection con=null;
            con=Util.openConnection(con);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
            session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            System.out.println(jasperPrint.getLocaleCode());



%>

<html>
    <body bgcolor="white" onload="location='../servlets/pdf'" >
    </body>
</html>




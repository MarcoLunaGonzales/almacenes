<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.cofar.util.*" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import="java.sql.*,java.io.*" %>
<%@ page import="java.text.*" %>
<%! public String[] literal(String codigo, Connection con) {
        String[] v = new String[2];
        String codSolicitud = codigo;
        System.out.println("codSolicitud: " + codSolicitud);
        v[0] = String.valueOf(codSolicitud);
        v[1] = "";
        return v;
    }
%>

<%
    try {
        Connection con = null;
        con = Util.openConnection(con);
        String codSolicitudCompra = request.getParameter("codigo");
        String literal[] = literal(codSolicitudCompra, con);
        String reporte = "";
        int tipoReporte = 0;

        String sql = "SELECT dbo.ObtenerTipoReporteSolCompras(COD_SOLICITUD_COMPRA) "
                + " FROM SOLICITUDES_COMPRA where COD_SOLICITUD_COMPRA =" + codSolicitudCompra;
        System.out.println(sql);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            tipoReporte = rs.getInt(1);
        }

        if (tipoReporte == 1) {
            reporte = "reporteSolicitudCompraEvento.jasper";
        } else {
            reporte = "reporteSolicitudCompra.jasper";

        }
        System.out.println(literal[0]);
        System.out.println(literal[1]);
        String reportFileName = application.getRealPath("reportes\\solicitudesCompra\\" + reporte);
        String a = application.getRealPath("");
        System.out.println("parametro2VVVVVVVVVVVVVVVV:  " + a + " " + reportFileName);
        File reportFile = new File(reportFileName);
        if (!reportFile.exists()) {
            throw new JRRuntimeException("El Reporte no existe");
        }
        Map parameters = new HashMap();
        parameters.put("codSolicitudCompra", Integer.parseInt(codSolicitudCompra));
        //parameters.put("SUBREPORT_DIR", a + "\\reportes\\ordenesCompra\\");
        System.out.println("codSolicitudCompra = " + codSolicitudCompra);

        System.out.println("literal[1] " + literal[1]);
        System.out.println("literal[0] " + literal[0]);
        parameters.put("codSolicitudCompra", Integer.parseInt(literal[0]));
        parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
        System.out.println("los parametros del reporte: " + parameters);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
        session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
%>

<html>
    <%if (literal[1].equals("0")) {%>
    <body bgcolor="white" >
        No es posible imprimir este comprobante.
        <%} else {%>
    <body bgcolor="white" onload="location = '../../servlets/pdf'">
        <%}
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </body>
</html>




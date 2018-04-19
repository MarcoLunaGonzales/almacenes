<%@ page import="net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter"%>
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
    
    Connection con = null;
    try{
        con = Util.openConnection(con);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaInicio = sdf.parse(request.getParameter("fechaInicio")+" 00:00");
        Date fechaFinal = sdf.parse(request.getParameter("fechaFinal")+" 23:59");
        int codTipoProveedor = Integer.valueOf(request.getParameter("codTipoProveedor"));
        String nombreTipoProveedor = "TODOS";
        String consulta = "select tp.NOMBRE_TIPO_PROVEEDOR from TIPOS_PROVEEDOR tp where tp.COD_TIPO_PROVEEDOR="+codTipoProveedor;
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery(consulta);
        if(res.next()){
            nombreTipoProveedor = res.getString("NOMBRE_TIPO_PROVEEDOR");
        }
        System.out.println("nombre tipo provee "+nombreTipoProveedor);
        Map parameters = new HashMap();
        parameters.put("codTipoProveedor", codTipoProveedor);
        parameters.put("nombreTipoProveedor", nombreTipoProveedor);
        parameters.put("fechaInicio", fechaInicio);
        parameters.put("fechaFinal", fechaFinal);
        parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
        String reportFileName = application.getRealPath("/reportes/reporteIngresosConObservacion/reporteIngresosConObservacion.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
        /*session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);*/
        JRXlsxExporter exporter=new JRXlsxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        response.setHeader("content-disposition", "inline; filename=\"reporteIngresosConObservacion.xlsx\"");
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,response.getOutputStream());
        exporter.exportReport();
        response.setContentType("application/vnd.ms-excel");
        con.close();
    }
    catch(Exception ex){
        ex.printStackTrace();
        out.println(ex.getMessage());
        out.println(ex.getCause());
    }

%>


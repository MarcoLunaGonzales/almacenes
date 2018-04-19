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
    SimpleDateFormat formatoFecha = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
    int codMaterial=Integer.valueOf(request.getParameter("codMaterial")== null ? "0" : request.getParameter("codMaterial"));
    int codAlmacen=Integer.valueOf(request.getParameter("codAlmacen")== null ? "''" : request.getParameter("codAlmacen"));
    Date fechaIni= formatoFecha.parse(request.getParameter("fechaIni")+" 00:00");
    Date fechaFin= formatoFecha.parse(request.getParameter("fechaFin")+" 23:59");
    System.out.println(fechaIni+" "+fechaFin+" "+codMaterial+" "+codAlmacen);
    Connection con= null;
    try {
        Map parameters = new HashMap();
        con = Util.openConnection(con);
        parameters.put("COD_ALMACEN",codAlmacen);
        parameters.put("COD_MATERIAL",codMaterial);    
        parameters.put("FECHA_INICIO", new Timestamp(fechaIni.getTime()));
        parameters.put("FECHA_FINAL",new Timestamp(fechaFin.getTime()));
        parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
        String reportFileName = application.getRealPath("/reportes/reporteKardexValoradoNuevo/reporteKardexValoradoNuevo.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
        JRPdfExporter exporter=new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        response.setContentType("application/pdf");  
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,response.getOutputStream());       
        exporter.exportReport();
    } catch (Exception e) {
        e.printStackTrace();
    }
    finally{
        con.close();
    }
%>

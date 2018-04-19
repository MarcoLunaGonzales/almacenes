<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="com.cofar.util.Util"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%-- 
    Document   : reporteCapitulo
    Created on : 23-feb-2018, 17:55:17
    Author     : tarancibia
--%>

<%
    int codCapitulo=Integer.valueOf(request.getParameter("codCapitulo"));
    System.out.print("codigo capitulo :"+codCapitulo);
    try{
        Map parameters = new HashMap();
        Connection con = null;
        con = Util.openConnection(con);
        parameters.put("codCapitulo",codCapitulo);
        String reportFileName = application.getRealPath("/capitulos/capitulosLog.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
        JRPdfExporter exporter=new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        response.setContentType("application/pdf");
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,response.getOutputStream());
        exporter.exportReport();  
        con.close();
    }
    catch (Exception e) {
        e.printStackTrace();
    }
%>

<%-- 
    Document   : reporteGrupos
    Created on : 26-feb-2018, 15:40:14
    Author     : tarancibia
--%>

<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="com.cofar.util.Util"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map"%>
<%
    int codGrupo=Integer.valueOf(request.getParameter("codGrupo"));
    System.out.print("codigo grupo:"+codGrupo);
    try{
        Map parameters = new HashMap();
        Connection con = null;
        con = Util.openConnection(con);
        parameters.put("codGrupo",codGrupo);
        String reportFileName = application.getRealPath("/grupos/gruposLog.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
        JRPdfExporter exporter=new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        response.setContentType("application/pdf");
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,response.getOutputStream());
        exporter.exportReport();  
        con.close();
    }
    catch (Exception e) {
        System.out.println("ERROR:" + e.getMessage());
    }
%>

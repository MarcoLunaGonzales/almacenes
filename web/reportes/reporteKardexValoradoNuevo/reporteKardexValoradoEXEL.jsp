<%@page import= "net.sf.jasperreports.j2ee.servlets.BaseHttpServlet"%>
<%@page import= "net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import= "net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@page import= "net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import= "net.sf.jasperreports.engine.JasperPrint"%>
<%@page import= "java.util.HashMap"%>
<%@page import= "java.sql.Connection"%>
<%@page import= "java.util.Map"%>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "java.util.Date"%>
<%@ page import="com.cofar.util.*" %>
<%@ page language="java" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import = "java.sql.Connection"%>
<%@ page import = "java.sql.DriverManager"%>
<%@ page import = "java.sql.ResultSet"%>
<%@ page import = "java.sql.Statement"%>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.text.SimpleDateFormat"%>
<%@ page import = "javax.servlet.http.HttpServletRequest"%>
<%@ page import = "java.text.DecimalFormat"%>
<%@ page import = "java.text.NumberFormat"%>
<%@ page import = "java.util.Locale"%>
<%@ page import="java.util.*,com.cofar.util.CofarConnection" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="net.sf.jasperreports.engine.export.JExcelApiExporterParameter" %>
<%@ page import="net.sf.jasperreports.engine.export.JRXlsExporterParameter" %>
<%@ page import="net.sf.jasperreports.engine.export.ooxml.JRDocxExporter" %>
<%@ page import="net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter" %>

<%-- 
    Document   : reporteKardexValorado
    Created on : 20-feb-2018, 8:51:06
    Author     : tarancibia
--%>
<%--@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"--%>

<%  
    response.setContentType("application/xlsx");
    response.addHeader("Content-Disposition", "attachment;filename=reporteKardexValorado.xlsx");
    SimpleDateFormat formatoFecha = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
    int codMaterial=Integer.valueOf(request.getParameter("codMaterial")== null ? "0" : request.getParameter("codMaterial"));
    int codAlmacen=Integer.valueOf(request.getParameter("codAlmacen")== null ? "''" : request.getParameter("codAlmacen"));
    Date fechaIni= formatoFecha.parse(request.getParameter("fechaIni")+" 00:00");
    Date fechaFin= formatoFecha.parse(request.getParameter("fechaFin")+" 23:59");
    Connection con = null;
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
              
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        xlsxExporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
        xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
        xlsxExporter.exportReport();   
    } catch (Exception e) {
        e.printStackTrace();
    }
    finally{con.close();}
%>


<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import="com.cofar.util.*" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import = "java.sql.Connection"%>
<%@ page import = "java.sql.DriverManager"%>
<%@ page import = "java.sql.ResultSet"%>
<%@ page import = "java.sql.Statement"%>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.text.SimpleDateFormat"%>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "java.util.Date"%>
<%@ page import = "javax.servlet.http.HttpServletRequest"%>
<%@ page import = "java.text.DecimalFormat"%>
<%@ page import = "java.text.NumberFormat"%>
<%@ page import = "java.util.Locale"%>
<%@ page language="java" import="java.util.*,com.cofar.util.CofarConnection" %>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
<%@ page import="java.io.*"%>
<%@ page import="com.cofar.web.ManagedAccesoSistema;" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../js/general.js"></script>
    </head> 
<%
    Connection con = null;
    try {
        con = Util.openConnection(con);
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat formato = (DecimalFormat) nf;
        formato.applyPattern("#,###.00");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Timestamp fechaInicio = null;
        Timestamp fechaFinal = null;
        if(request.getParameter("check") != null){
            fechaInicio = new Timestamp(sdf.parse(request.getParameter("fechaIni")+" 00:00").getTime());
            fechaFinal = new Timestamp(sdf.parse(request.getParameter("fechaFin")+" 00:00").getTime());
        }
        else{
            System.out.println("asignando un año de intervalo de fechas");
            String consulta = "select getdate() as fechaFinal, DATEADD(YEAR,-1,getdate()) as fechaInicio";
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(consulta);
            if(res.next()){
                fechaInicio = res.getTimestamp("fechaInicio");
                fechaFinal = res.getTimestamp("fechaFinal");
            }
        }
        
        
        Map parameters = new HashMap();
        parameters.put("fechaInicio",fechaInicio);System.out.println("fechaInicio: "+fechaInicio);
        parameters.put("fechaFinal",fechaFinal);System.out.println("fechaFinal: "+fechaFinal);
        parameters.put("codigosLotes",request.getParameter("lotesArray"));System.out.println("codigosLotes: "+request.getParameter("lotesArray"));
        parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
        String reportFileName = application.getRealPath("/reportes/reporteSalidasAlmacen/reporteResumenGeneralLotesCantidades/reporteGeneralLotesCantidades_.jasper");
        System.out.println("-----------------------INICIO REPORTE--------------------------------------");
        con = Util.openConnection(con);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
        session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
        System.out.println("-----------------------FINAL REPORTE--------------------------------------");

    }
    catch(Exception ex){
        ex.printStackTrace();
    }
%>
<html>
    <body bgcolor="white" onload="location='../../../servlets/pdf'" >
    </body>
</html>
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
        String reporte = request.getParameter("reporte")==null?"":request.getParameter("reporte");
        Map parameters = new HashMap();
        parameters.put("codigo", salidasAlmacen.getCodSalidaAlmacen());
        String reportFileName = application.getRealPath("/salidasAlmacen/"+reporte);
        if(salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()==3 && salidasAlmacen.getAlmacenes().getCodAlmacen()==1 && !reporte.equals("reporteSalidaCostoFRV.jasper")){
            reportFileName=application.getRealPath("/salidasAlmacen/reporteSalidaTraspaso.jasper");
        }
        String reportFileName2 = application.getRealPath("");
        reportFileName2+="/salidasAlmacen/";
        application.getRealPath("");
        System.out.println(reportFileName2);
        System.out.println("el reportex :::: "+reporte);
        Connection con = null;
        con = Util.openConnection(con);
        parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
        System.out.println("report "+reportFileName);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
        jasperPrint.setPageHeight(600);
        JRPdfExporter exporter=new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        response.setContentType("application/pdf");
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,response.getOutputStream());
        exporter.exportReport();
        con.close();
%>

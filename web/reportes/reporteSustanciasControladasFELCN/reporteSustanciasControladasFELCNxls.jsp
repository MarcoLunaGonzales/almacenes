
<%@page import="java.awt.Desktop"%>
<%@page import="net.sf.jasperreports.export.SimpleXlsxReportConfiguration"%>
<%@page import="net.sf.jasperreports.export.SimpleOutputStreamExporterOutput"%>
<%@page import="net.sf.jasperreports.export.SimpleExporterInput"%>
<%@page import="javax.mail.internet.MimeBodyPart"%>
<%@page import="javax.mail.BodyPart"%>
<%@page import="javax.activation.FileDataSource"%>
<%@page import="javax.activation.DataHandler"%>
<%@page import="net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>

<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>

<%@ page import="java.sql.Connection"%>
<%@ page import="com.cofar.util.*"%>
<%@ page import="java.io.*"%>

<%@ page import="java.sql.*"%>   

        <% 
            String anio=request.getParameter("anio");
            String codMes=request.getParameter("codMes");
            Date fechaInicio=null;
            Date fechaFinal=null;
            Connection con=null;
            try
            {
                con=Util.openConnection(con);
                String consulta = "select cast('"+anio+"/"+codMes+"/01 00:00' as datetime) as fechaInicio,dateadd(minute,-1,dateadd(month,1,cast('"+anio+"/"+codMes+"/01 00:00' as datetime))) as fechaFinal";
                System.out.println("consulta fechas "+consulta);
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta.toString());
                Map parameters = new HashMap();
                if(res.next())
                {
                    parameters.put("fechaInicio",res.getTimestamp("fechaInicio"));
                    parameters.put("fechaFinal",res.getTimestamp("fechaFinal"));
                }
                String au="reporteSustanciasControladasFELCN";
                String reportFileName = application.getRealPath("/reportes/reporteSustanciasControladasFELCN/"+au+".jasper");
                JasperPrint print = JasperFillManager.fillReport(reportFileName, parameters, con);
                JRXlsxExporter exporter=new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                response.setHeader("content-disposition", "inline; filename=\"reporteSustanciasControladasFELCN.xlsx\"");
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,response.getOutputStream());
                exporter.exportReport();
                response.setContentType("application/vnd.ms-excel");
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            finally{
                con.close();
            }
                    
                
           
%>
<html>
    <body bgcolor="white">
    </body>
</html>




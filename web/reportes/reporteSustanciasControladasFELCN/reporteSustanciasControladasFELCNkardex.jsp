<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
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
                String consulta = "select cast('"+anio+"/"+codMes+"/01 00:00' as datetime) as fechaInicio,dateadd(minute,-1,dateadd(month,1,cast('"+anio+"/"+codMes+"/01 23:59' as datetime))) as fechaFinal";
                System.out.println("consulta fechas "+consulta);
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta.toString());
                Map parameters = new HashMap();
                if(res.next())
                {
                    parameters.put("fechaInicio",res.getTimestamp("fechaInicio"));
                    parameters.put("fechaFinal",res.getTimestamp("fechaFinal"));
                    parameters.put("dirLogoCofar",application.getRealPath("/img/cofar.png"));
                }
                String reportFileName1 = application.getRealPath("/reportes/reporteSustanciasControladasFELCN/reporteSustanciasControladasFELCNkardex.jasper");                
                //JasperPrint jasper=new JasperPrint();
                JasperPrint jasper = JasperFillManager.fillReport(reportFileName1, parameters, con);
                //System.out.println("cdcdcd"+reportFileName);
                session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasper);
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            con.close();
        }
         
            
            
            
              
%>
<html>
<body bgcolor="white" onload="location='../../servlets/pdf'">
</body>
</html>




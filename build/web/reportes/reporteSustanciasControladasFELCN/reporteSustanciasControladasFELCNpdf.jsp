
<%@page import="javax.mail.internet.MimeBodyPart"%>
<%@page import="javax.mail.BodyPart"%>
<%@page import="javax.activation.FileDataSource"%>
<%@page import="javax.activation.DataHandler"%>
<%@page import="net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter"%>
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

        <% //bgcolor="white" onload="location='../../servlets/pdf'" 
            String f1=request.getParameter("fecha1")+" "+request.getParameter("ano1");
            String f2=request.getParameter("fecha2")+" "+request.getParameter("ano2");            
            String[] Mes = new String[13];
            Mes[1]="Enero";
            Mes[2]="Febrero";
            Mes[3]="Marzo";
            Mes[4]="Abril";
            Mes[5]="Mayo";
            Mes[6]="Junio";
            Mes[7]="Julio";
            Mes[8]="Agosto";
            Mes[9]="Septiembre";
            Mes[10]="Octubre";
            Mes[11]="Noviembre";
            Mes[12]="Diciembre";
            
            String mes=null;
            String ano;
            String[] part=new String[3]; 
            part=f1.split(" ");
            ano=part[1];
            String mescadena1=part[0];
            for(int i=1;i<=12;i++)
            {   if(part[0].equals(Mes[i]))
                {   //out.println("mes:"+i);
                    if(i<10)
                    {   mes="0"+i; 
                    }  
                    else
                    {    mes=String.valueOf(i);                   
                    }
                    break;
                }               
            }    
            f1=ano+"-"+mes+"-01";
            ////**********MES 2
            part=f2.split(" ");
            ano=part[1];
            String mescadena2=part[0];
            int mes2=0;
            int ano2 = 0;
            ano2 = Integer.parseInt(part[1]);
            for(int i=1;i<=12;i++)
            {   if(part[0].equals(Mes[i]))
                {   //out.println("mes:"+i);
                    mes2=i-1;
                    break;
                }               
            }   
            
            //out.println("fecha 1: "+f1);
            //out.println("Mes 2::::"+mes2+" ano 2:"+ano2);
            //fecha 1 string
            
            //int af=2016;
            
            Calendar cal = GregorianCalendar.getInstance();
            cal.set(ano2, mes2, 1); // Febrero 2006, los meses empiezan en 0.
            mes2++;
            String mes2s;
            if(mes2<10)
                mes2s="0"+mes2;
            else
                mes2s=""+mes2;
            f2=ano2+"-"+mes2s+"-"+ cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            //out.println("Fecha 2: " +f2);
            f1=f1+" 00:00:00";
            f2=f2+" 23:59:59";
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map parameters = new HashMap();
            Connection con = null;
            con = Util.openConnection(con);
            //int cod=(Integer.valueOf(request.getParameter("material")));
            //String[] items = request.getParameterValues("material");
            
            /* for(int loopIndex = 0; loopIndex < items.length; loopIndex++)
            {   //cod[loopIndex ]=Integer.valueOf(items[loopIndex]);
                out.println(cod[loopIndex] + "<BR>");
                
            }*/
            out.println("<br>FECHA 1:"+f1+"FECHA 2:"+f2);
            //*****************RELLENAMOS TABLA TEMPORAL CODIGO*****************
            //crear tabla
           /* try
            {   con=Util.openConnection(con);
                
                StringBuilder query1=new StringBuilder("IF OBJECT_ID('tempdb..#temporal_material') IS NOT NULL");
                                         query1.append(" BEGIN truncate table #temporal_material");
                                         query1.append(" END ELSE CREATE TABLE #temporal_material( id INT , description NVARCHAR(100) )");
                                                            //consulta.append(" inner join CAPITULOS cp on gr.COD_CAPITULO=cp.COD_CAPITULO and gr.COD_CAPITULO=2");
                                                       // consulta.append(" order by ma.nombre_material");//order by ma.nombre_material
                System.out.println("crea tabla TEMP:"+query1.toString());
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate(query1.toString());
                //if(st.executeUpdate(query1.toString())>0) System.out.println("creado tabla temporal");
                int[] cod=new int[1000];
                for(int loopIndex = 0; loopIndex < items.length; loopIndex++)
                {   cod[loopIndex ]=Integer.valueOf(items[loopIndex]);
                    StringBuilder   query2=new StringBuilder("insert into #temporal_material values(");
                                    query2.append(Integer.valueOf(items[loopIndex])).append(",'willy grover catari')");
                    // 1, 'willy grover')");
                    System.out.println("inserta datos:"+query2.toString());
                    Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    st2.executeUpdate(query2.toString());               
                }
                con.commit();
                
                //leemos datos
                StringBuilder consulta3=new StringBuilder("select id,description from #temporal_material");
                                                  
                System.out.println("Consulta material:"+consulta3.toString());
                Statement st3=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st3.executeQuery(consulta3.toString());
                while(res.next())
                    out.println("codigo:"+res.getInt("id")+" DEscricption :"+res.getString("description")+"<br>");
            }
            catch(SQLException ex)
            {   ex.printStackTrace();
            }
            /*finally
            {   con.close();
            }*/
            
            //System.out.println("cdcdcd");
            //parameters.put("cod1",Integer.valueOf(items[0]));
            Date d1=sdf.parse(f1);
            Date d2=sdf.parse(f2);
            Timestamp t1 = new Timestamp(d1.getTime());
            Timestamp t2 = new Timestamp(d2.getTime());
            parameters.put("fecha1",t1);
            parameters.put("fecha2",t2);
             
            parameters.put("mes1",mescadena1);
            parameters.put("mes2",mescadena2);
            
            String au="reporteSustanciasControladasFELCN";
           
            String reportFileName = application.getRealPath("/reportes/reporteSustanciasControladasFELCN/"+au+".jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
            //System.out.println("cdcdcd"+reportFileName);
            session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            con.close();
           
%>
<html>
<body bgcolor="white" onload="location='../../servlets/pdf'" >
    </body>
</html>




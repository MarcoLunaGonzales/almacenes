<%@page contentType="text/xml"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
Connection con=null;
try
{   int cod=(Integer.valueOf(request.getParameter("codAlmacAcond")));
    String f1=request.getParameter("f1");
    String f2=request.getParameter("f2");
    Date d1=Util.converterStringDate(f1);
    Date d2=Util.converterStringDate(f2);
    String f3=d1.toString();
    String f4=d2.toString();
    con=Util.openConnection(con);
    //diferenciamos entre todos o un solo almacen
    String aux=null;
    if(cod==0)
        aux="";
    else
        aux="a.cod_almacenacond="+cod+" and ";
    StringBuilder  consulta=new StringBuilder("select s.nro_salidaacond,a.NOMBRE_ALMACENACOND,cp.nombre_generico,s.fecha_salidaacond,sd.CANT_TOTAL_SALIDADETALLEACOND");
                   consulta.append(" from almacenes_acond a,salidas_acond s,salidas_detalleacond sd,componentes_prod cp");
                   consulta.append(" where "+aux+"a.cod_almacenacond=s.cod_almacenacond and s.COD_SALIDA_ACOND=sd.COD_SALIDA_ACOND and sd.COD_COMPPROD=cp.COD_COMPPROD and s.fecha_salidaacond>'"+f3+" 00:00:00' and s.fecha_salidaacond<'"+f4+" 23:59:59' order by s.fecha_salidaacond");
    System.out.println("CONSULTA REPORTE ACOND:"+consulta.toString());
    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet res=st.executeQuery(consulta.toString());
    //out.println(consulta.toString()); 
    out.println("<table class='tablaReporte' cellpadding='0' cellspacing='0' align='center'>");
        out.println("<thead>");
            out.println("<tr>");
             
                out.println("<td>NRO SALIDA</td>");
                out.println("<td>ALMACEN</td>");
                out.println("<td>PRODUCTO</td>");
                out.println("<td>FECHA</td>");
                out.println("<td>CANTIDAD</td>");
            out.println("</tr>");
        out.println("</thead><tbody>");
   int cont=0;
   while(res.next())
    {   out.println("<tr>");
        cont++;
        out.println("<td>"+res.getInt("nro_salidaacond")+"</td>");
        out.println("<td>"+res.getString("NOMBRE_ALMACENACOND")+"</td>");
                
        out.println("<td>"+res.getString("nombre_generico") +"</td>");
        out.println("<td>"+res.getString("fecha_salidaacond") +"</td>");
        out.println("<td>"+res.getInt("CANT_TOTAL_SALIDADETALLEACOND") +"</td>");
        out.println("</tr>");
    }
    out.println("</tbody></table>");
    out.println("<div align='center'>Total:"+cont+"</div>");
}
catch(SQLException ex)
{
    ex.printStackTrace();
}
catch(Exception e)
{
    e.printStackTrace();
}
finally
{
    con.close();
}
%>



s<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/xml"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>

<%
    try
    {
String codLote=request.getParameter("codLote");
String codCompProd =request.getParameter("codCompProd");
String nroSalida=request.getParameter("nroSalida");
String fechaFinal=request.getParameter("fechaFinal");
String fechaInicio=request.getParameter("fechaInicio");
String consulta="select sa.COD_SALIDA_ALMACEN,sa.NRO_SALIDA_ALMACEN,cp.nombre_prod_semiterminado,sa.COD_LOTE_PRODUCCION,sa.FECHA_SALIDA_ALMACEN"+
             " from SALIDAS_ALMACEN sa inner join COMPONENTES_PROD cp on sa.COD_PROD=cp.COD_COMPPROD"+
             " where sa.COD_ALMACEN=1"+
             (codCompProd.equals("0")?"":" and sa.COD_PROD='"+codCompProd+"'")+
             (codLote.length()>0?" and sa.COD_LOTE_PRODUCCION='"+codLote+"'":"")+
             (nroSalida.length()>0?" and sa.NRO_SALIDA_ALMACEN='"+nroSalida+"'":"");
             if(fechaInicio.length()>0&&fechaFinal.length()>0)
             { 
                 String[] arrayFecha=fechaInicio.split("/");
                 fechaInicio=arrayFecha[2]+"/"+arrayFecha[1]+"/"+arrayFecha[0];
                 arrayFecha=fechaFinal.split("/");
                 fechaFinal=arrayFecha[2]+"/"+arrayFecha[1]+"/"+arrayFecha[0];
                consulta+=" and sa.FECHA_SALIDA_ALMACEN BETWEEN '"+fechaInicio+" 00:00' and '"+fechaFinal+" 23:59'";
             }
             consulta+=" and sa.COD_SALIDA_ALMACEN in  ( select sad.COD_SALIDA_ALMACEN from SALIDAS_ALMACEN_DETALLE sad inner join materiales m on "+
             "  sad.COD_MATERIAL=m.COD_MATERIAL inner join grupos g on g.COD_GRUPO=m.COD_GRUPO"+
             "  and g.COD_CAPITULO=2)"+
             " order by sa.NRO_SALIDA_ALMACEN";
System.out.println("consulta salidas "+consulta);
Connection con=null;
con=Util.openConnection(con);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet res=st.executeQuery(consulta);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
out.println("<table cellpadding='0' cellspacing='0' class='tablaSalidas'>");
out.println("<thead><tr ><td>Nro Salida</td><td>Producto</td><td>Lote</td><td>FechaSalida</td><td>Detallar</td></tr></thead>");
while(res.next())
{
    out.println("<tr>");
    out.println("<td>"+res.getInt("NRO_SALIDA_ALMACEN")+"</td>");
    out.println("<td>"+res.getString("nombre_prod_semiterminado")+"</td>");
    out.println("<td>"+res.getString("COD_LOTE_PRODUCCION")+"</td>");
    out.println("<td>"+sdf.format(res.getTimestamp("FECHA_SALIDA_ALMACEN"))+"</td>");
    out.println("<td><a  onclick='detallarSalida("+res.getInt("COD_SALIDA_ALMACEN")+",this)' href='#'><img src='../../img/h2bg.gif'/></a></td>");
    out.println("</tr>");
}
out.println("</table>");

    }
    catch(SQLException ex)
    {
        ex.printStackTrace();
    }
    %>

    

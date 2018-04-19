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
<%@ page errorPage="ExceptionHandler.jsp" %>
<%! Connection con=null;


%>
<%! public String nombrePresentacion1(String codPresentacion){
    

 
String  nombreproducto="";

try{
con=Util.openConnection(con);
String sql_aux="select cod_presentacion, nombre_producto_presentacion from presentaciones_producto where cod_presentacion='"+codPresentacion+"'";
System.out.println("PresentacionesProducto:sql:"+sql_aux);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs=st.executeQuery(sql_aux);
while (rs.next()){
String codigo=rs.getString(1);
nombreproducto=rs.getString(2);
}
} catch (SQLException e) {
e.printStackTrace();
    }
    return "";
}

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%--meta http-equiv="Content-Type" content="text/html; charset=UTF-8"--%>
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <script src="../js/general.js"></script>
    </head>
    <body>
        <h3 align="center">Traspaso a Almacen</h3>
        <br>
        <form>
            <div align="center">
                <table align="center" border="0" width="40%" class="outputText2">
                <tbody>
                    <tr>
                        <td style="background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>Material Admitido</td>
                        <td style="background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>Material No Admitido en el Almacen</td>
                    </tr>
                </tbody>
            </table>
            
                <%
                
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#,###");                


                    String codSalidaAlmacen=request.getParameter("codSalidaAlmacen")==null?"0":request.getParameter("codSalidaAlmacen");
                    ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                    //codOrdenCompra = "10069";

                    String consulta = " select s.COD_SALIDA_ALMACEN,m.COD_MATERIAL , m.NOMBRE_MATERIAL,gr.COD_GRUPO," +
                             " gr.NOMBRE_GRUPO,c.COD_CAPITULO,c.NOMBRE_CAPITULO,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,s.CANTIDAD_SALIDA_ALMACEN, " +
                             " ( select top 1 s.cod_seccion cod_seccion " +
                                    " from secciones s,secciones_detalle sd, almacenes a " +
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material=m.COD_MATERIAL and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=gr.COD_GRUPO) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo=c.COD_CAPITULO)) ) seccion " +
                             " from SALIDAS_ALMACEN_DETALLE s  " +
                             " inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL " +
                             " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                             " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO " +
                             " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA " +
                             " where s.COD_SALIDA_ALMACEN = '"+codSalidaAlmacen+"'  ";
                System.out.println("consulta"+consulta);
                con = Util.openConnection(con);
                Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs1=st1.executeQuery(consulta);
                
                out.print("<table class='outputText2' style='border : solid #f2f2f2 1px;' cellpadding='0' cellspacing='0'>");
                out.print("<tr class='tablaFiltroReporte'>");
                out.print("<td align='center' class='bordeNegroTd'></td>");
                out.print("<td align='center' class='bordeNegroTd'>Item</td>");
                out.print("<td align='center' class='bordeNegroTd'>Cantidad</td>");
                out.print("<td align='center' class='bordeNegroTd'>Unidad</td>");
                out.print("</tr>");
                while(rs1.next()){
                    %>
                    <tr>
                        <td style="background-color:<%=(rs1.getInt("seccion")>0)?"black":"red"%> " >&nbsp;&nbsp;</td>
                        <td class="bordeNegroTd" align="left"><%=rs1.getString("NOMBRE_MATERIAL")%></td>
                        <td class="bordeNegroTd" align="left"><%=rs1.getFloat("CANTIDAD_SALIDA_ALMACEN")%></td>
                        <td class="bordeNegroTd" align="left"><%=rs1.getString("NOMBRE_UNIDAD_MEDIDA")%></td>
                    </tr>

                    <%
                }
                out.print("</table>");

                %>                
            

            


            <br>
            <br>
            <br>
            
            <table style="background:#BBFFCC;text-align:left " ></table>
            </div>
            
            
        </form>
    </body>
</html>
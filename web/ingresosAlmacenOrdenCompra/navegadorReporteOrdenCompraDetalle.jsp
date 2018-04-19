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
        <h3 align="center">Detalle de Orden de Compra</h3>
        <br>
        <form>
            

            <table align="center" width="90%">

                <%
                
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#,###");                


                    String codOrdenCompra=request.getParameter("codOrdenCompra")==null?"0":request.getParameter("codOrdenCompra");
                    String codAlmacen=request.getParameter("codAlmacen")==null?"0":request.getParameter("codAlmacen");
                    //codOrdenCompra = "10069";
                
                %>                
            </table>
            <table align="center" border="0" width="80%" class="outputText2">
                <tbody>
                    <tr>
                        <td style="background-color:#000000">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>pendiente de ingreso</td>
                        <td style="background-color:#81F781">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>Ingresado Parcialmente</td>
                        <td style="background-color:#FFFF00">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>Pertenece a Compras</td>
                        <td style="background-color:#58ACFA" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>Ingresado en su Totalidad</td>
                        <td style="background-color:#F78181">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>No Admitido</td>
                    </tr>
                </tbody>
            </table>
            <br/>
            <table  align="center" width="60%" class="outputText2" style="border : solid #f2f2f2 1px;" cellpadding="0" cellspacing="0">

                <tr class="tablaFiltroReporte">
                    <td  align="center" class="bordeNegroTd"><b>&nbsp;</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Item</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Cantidad</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Cantidad Ingresada a Almacen</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Unidades</b></td>
                </tr>

                <%
                try{                


                String consulta = " select ocd.cod_orden_compra,ocd.cod_material,ocd.cantidad_neta,ocd.precio_unitario,ocd.precio_total,ocd.cantidad_ingreso_almacen, " +
                        " (select g.cod_capitulo from grupos g, materiales m where ocd.cod_material=m.cod_material and m.cod_grupo=g.cod_grupo)as cod_capitulo, " +
                        " (select m.cod_grupo from materiales m where ocd.cod_material=m.cod_material)as cod_grupo,ocd.cod_material, " +
                        " (select m.material_almacen from materiales m where ocd.cod_material=m.cod_material)as material_almacen, " +
                        " (select m.nombre_material from materiales m where ocd.cod_material=m.cod_material)as nombre_material,ocd.cod_unidad_medida, " +
                        " (select um.abreviatura from unidades_medida um  where ocd.cod_unidad_medida=um.cod_unidad_medida)as abreviatura  " +
                        " from ordenes_compra_detalle ocd where cod_orden_compra= '"+codOrdenCompra+"' order by nombre_material ";



                System.out.println("consulta"+consulta);
                con = Util.openConnection(con);                            
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs=st.executeQuery(consulta);

                while (rs.next()){
                    // codOrdenCompra = rs.getString("cod_orden_compra");
                    String codMaterial = rs.getString("cod_material");
                    float cantidadNeta = rs.getFloat("cantidad_neta");
                    float precioUnitario = rs.getFloat("precio_unitario");
                    double precioTotal = rs.getDouble("precio_total");
                    int cantidatIngresoAlmacen = rs.getInt("cantidad_ingreso_almacen");
                    int codCapitulo = rs.getInt("cod_capitulo");
                    int codGrupo = rs.getInt("cod_grupo");
                    String materialAlmacen = rs.getString("material_almacen");
                    String nombreMaterial = rs.getString("nombre_material");
                    int codUnidadMedida = rs.getInt("cod_unidad_medida");
                    String abreviatura = rs.getString("abreviatura");
                    int codSeccion = 0;
                    String estiloFila = " #000000 ";
                    consulta = " select material_almacen from materiales where material_almacen=1 and cod_material='"+codMaterial+"' ";
                        System.out.println("consulta " + consulta);
                        Statement stMaterialAlmacen=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ResultSet rsMaterialAlmacen = stMaterialAlmacen.executeQuery(consulta);
                        if(rsMaterialAlmacen.next()){
                            //hallar si el material corresponde a una seccion de almacen
                            consulta = " select distinct(s.cod_seccion) cod_seccion,s.nombre_seccion " +
                                    " from secciones s,secciones_detalle sd, almacenes a " +
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+codAlmacen+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material='"+codMaterial+"' and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo='"+codGrupo+"') " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='"+codCapitulo+"')) ";
                            System.out.println("consulta " + consulta );
                            Statement stSeccion=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                            ResultSet rsSeccion = stSeccion.executeQuery(consulta);
                            if(rsSeccion.next()){
                                codSeccion = rsSeccion.getInt("cod_seccion");
                            }
                         if(codSeccion!=0){
                             if(cantidatIngresoAlmacen>0){
                             if(cantidatIngresoAlmacen<cantidadNeta){
                                 estiloFila = "#81F781"; //verde ingresado parcialmente
                             }
                             else{
                                 estiloFila = "#58ACFA"; //azul ingresado en su totalidad
                                  }
                             }else{
                                 estiloFila = "#000000"; //negro pendiente de ingreso
                             }
                             
                             
                         }else{
                               estiloFila = "#F78181"; //rojo no admitido
                         }
                        }else{
                            estiloFila = "#FFFF00"; //amarillo pertenece a compras
                        }
                    
                    out.print("<tr>");
                    out.print("<td class='bordeNegroTd' align='right' style='background-color:"+estiloFila+"'>&nbsp;</td>");
                    out.print("<td class='bordeNegroTd' align='right'>"+nombreMaterial+"</td>");
                    out.print("<td class='bordeNegroTd' align='right'>"+cantidadNeta+"</td>");
                    out.print("<td class='bordeNegroTd' align='right'>"+cantidatIngresoAlmacen +"</td>");
                    out.print("<td class='bordeNegroTd' align='right'>"+abreviatura+"</td>");
                    out.print("</tr>");
                    
                    }
                
                }catch(Exception e){
                e.printStackTrace();
                }
                %>
               
            </table>
            <table style="background:#BBFFCC"></table>
            
            
            
        </form>
    </body>
</html>
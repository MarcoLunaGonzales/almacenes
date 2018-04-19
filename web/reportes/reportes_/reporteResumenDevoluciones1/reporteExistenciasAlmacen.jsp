
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


<%! Connection con = null;
String codPresentacion = "";
String nombrePresentacion = "";
String linea_mkt = "";
String agenciaVenta = "";
%>
<%! public String nombrePresentacion1() {



    String nombreproducto = "";
//ManagedAccesoSistema bean1=(ManagedAccesoSistema)com.cofar.util.Util.getSessionBean("ManagedAccesoSistema");
    try {
        con = Util.openConnection(con);
        String sql_aux = "select cod_presentacion, nombre_producto_presentacion from presentaciones_producto where cod_presentacion='" + codPresentacion + "'";
        System.out.println("PresentacionesProducto:sql:" + sql_aux);
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery(sql_aux);
        while (rs.next()) {
            String codigo = rs.getString(1);
            nombreproducto = rs.getString(2);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return nombreproducto;
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Existencias Almacen</h4>

                    <%
                    try {

                /*
                         <input type="hidden" name="codProgramaProduccionPeriodo">
                        <input type="hidden" name="codCompProd">
                        <input type="hidden" name="nombreCompProd">
                        */

                        

                        NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat) numeroformato;
                        formato.applyPattern("####.##;(####.##)");

                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");

                        con = Util.openConnection(con);



                        String fechaInicial=request.getParameter("fecha_inicio")==null?"01/01/2011":request.getParameter("fecha_inicio");
                        String arrayfecha[]=fechaInicial.split("/");
                        fechaInicial=arrayfecha[2] +"/"+ arrayfecha[1]+"/"+arrayfecha[0];
                        String fechaFinal=request.getParameter("fecha_final")==null?"01/01/2011":request.getParameter("fecha_final");
                        String arrayfecha1[]=fechaFinal.split("/");
                        fechaFinal=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                        String codTipoSalidaAlmacen=request.getParameter("codTipoSalida")==null?"0":request.getParameter("codTipoSalida");
                        String nombreTipoSalidaAlmacen=request.getParameter("nombreTipoSalida")==null?"0":request.getParameter("nombreTipoSalida");
                        //codMaterial = codMaterial.substring(1);
                        String conLote=request.getParameter("conLote")==null?"0":request.getParameter("conLote");
                        String todosLote=request.getParameter("todosLote")==null?"0":request.getParameter("todosLote");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");
                        System.out.println("parametros" + fechaInicial +"" + conLote+""+todosLote);
                   


                    %>

                    <%--
            <div class="outputText0" align="center">
                PROGRAMA PRODUCCION: <%= nombreProgramaProduccionPeriodo %> <br>
                AREA(S) : <%= arrayNombreAreaEmpresa %><br>
                DE <%= arraydesde[0] +"/"+ arraydesde[1]+"/"+arraydesde[2] %> <br>
                HASTA <%= arrayhasta[0] +"/"+ arrayhasta[1]+"/"+arrayhasta[2] %>
                <%--PRODUCTO<%=nombreComponenteProd%>--%>
                <%--
            </div--%>

            <br>
            <table width="70%" border="0" align="center">
                <tr>
                    <td>
            <img src="../../img/cofar.png" alt="logo cofar" align="left" />
            </td><td>
            <table border="0" class="outputText1" >
                <tbody>
                    <tr>
                        <td><b>Filial</b></td>
                        <td><%=nombreFilial%></td>
                    </tr>
                    <tr>
                        <td><b>Almacen</b></td>
                        <td><%=nombreAlmacen%></td>
                    </tr>
                    <tr>
                        <td><b>Tipos de Salida Almacen</b></td>
                        <td><%=nombreTipoSalidaAlmacen%></td>
                    </tr>
                    <tr>
                        <td><b>fecha Inicial</b></td>
                        <td><%=fechaInicial%></td>
                    </tr>
                    <tr>
                        <td><b>fecha Final</b></td>
                        <td><%=fechaFinal%></td>
                    </tr>
                </tbody>
            </table>
            </td>
            </tr>
            </table>
            


            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr bgcolor="#cccccc">
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Area</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Salidas</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Devoluciones</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Total General</b></td>
                    
                    
                    
                </tr>

                <%

                String consulta =" select sum(total_salida) cant_salidas,cod_area_empresa, nombre_area_empresa,sum(devolucion_total) cant_devoluciones from( " +
                         "select s.COD_AREA_EMPRESA,sad.cod_salida_almacen,ae.NOMBRE_AREA_EMPRESA, " +
                         " isnull(sad.cantidad_salida_almacen, 0) as cantidad_salida_almacen, ( " +
                         " select top 1 isnull(sadi.costo_salida_actualizado, 0) " +
                         " from salidas_almacen_detalle_ingreso sadi " +
                         " where sad.cod_material = sadi.cod_material and " +
                         " sad.cod_salida_almacen = sadi.cod_salida_almacen ) as costo_salida," +
                         "(select isnull(sum(sadi.costo_salida_actualizado * sadi.cantidad), 0)" +
                         " from salidas_almacen_detalle_ingreso sadi " +
                         " where sad.cod_material = sadi.cod_material and sad.cod_salida_almacen = sadi.cod_salida_almacen) as total_salida " +
                         " ,(select sum(iad.CANT_TOTAL_INGRESO_FISICO * iad.COSTO_UNITARIO_ACTUALIZADO) from DEVOLUCIONES d " +
                         " inner join INGRESOS_ALMACEN ia on ia.COD_DEVOLUCION = d.COD_DEVOLUCION and ia.COD_ALMACEN = d.COD_ALMACEN " +
                         " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = sad.COD_MATERIAL" +
                         " where d.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN and d.COD_ALMACEN = s.COD_ALMACEN " +
                         " and ia.FECHA_INGRESO_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59' and d.ESTADO_SISTEMA = 1 and" +
                         " ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1 and d.COD_ESTADO_DEVOLUCION = 1) devolucion_total" +
                         " from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on" +
                         " sad.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA where s.FECHA_SALIDA_ALMACEN " +
                         " between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59' " +
                         " and  s.COD_ALMACEN = "+codAlmacen+" "+(todosLote.equals("0")?(conLote.equals("0")?" and (s.cod_lote_produccion = '' or s.cod_lote_produccion is null)":" and (s.cod_lote_produccion <> '' or s.cod_lote_produccion is not null)"):"")+" ) as tabla group by cod_area_empresa,nombre_area_empresa order by nombre_area_empresa asc ";

                
                System.out.println("consulta "+ consulta);
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                while (rs.next()) {
                    //System.out.println("consulta 1 "+ consulta);
                                
                                out.print("<tr>");
                                out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+(rs.getString("NOMBRE_AREA_EMPRESA")==null?"":rs.getString("NOMBRE_AREA_EMPRESA"))+"</td>");
                                out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px' >"+formato.format(rs.getDouble("cant_salidas"))+"</td>");
                                out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px' >"+formato.format(rs.getDouble("cant_devoluciones"))+"</td>");
                                out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px' >"+formato.format(rs.getDouble("cant_salidas"))+"</td>");
                                out.print("</tr>");
                                
                  }




               %>
               </table>

                <%



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                %>
            <br>

            <br>
            <div align="center">
                <%--<INPUT type="button" class="commandButton" name="btn_registrar" value="<-- Atrás" onClick="cancelar();"  >--%>

            </div>
        </form>
    </body>
</html>
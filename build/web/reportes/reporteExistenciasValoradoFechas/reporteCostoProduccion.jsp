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
 

<%! /*public String nombrePresentacion1() {



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
    }*/
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
            <h4 align="center">Reporte de Existencias Por Fechas Valorado</h4>

            <%
            
        try {
            Connection con = null;
            String codPresentacion = "";
            String nombrePresentacion = "";
            String linea_mkt = "";
            String agenciaVenta = "";
            NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("#,##0.00####");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            con = Util.openConnection(con);
            String fechaFinal=request.getParameter("fechaFin")==null?"01/01/2011":request.getParameter("fechaFin");
            String fechaIni = request.getParameter("fechaIni") == null ? "01/01/2011" : request.getParameter("fechaIni");
            
            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            String codGrupo = request.getParameter("codGrupo") == null ? "''" : request.getParameter("codGrupo");
            String movimiento = request.getParameter("movimiento") == null ? "''" : request.getParameter("movimiento");
            String codCapitulo = request.getParameter("codCapitulo") == null ? "''" : request.getParameter("codCapitulo");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");



            
            %>

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
                                    <td><b>Capitulo</b></td>
                                    <td><%=nombreCapitulo%></td>
                                </tr>
                                <tr>
                                    <td><b>Grupo</b></td>
                                    <td><%=nombreGrupo%></td>
                                </tr>
                                <tr>
                                    <td><b>Fecha Inicio</b></td>
                                    <td><%=(fechaIni)%></td>
                                </tr>
                                <tr>
                                    <td><b>Fecha Final</b></td>
                                    <td><%=(fechaFinal)%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>



            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr bgcolor="#cccccc">
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" rowspan="2" ><b>Cod Material</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" rowspan="2"><b>Item</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" rowspan="2"><b>Unidades</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center" colspan="3"><b><%=(fechaIni)%></b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center" colspan="3"><b><%=(fechaFinal)%></b></td>
                </tr>
                <tr bgcolor="#cccccc">
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Existencia</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Costo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Monto Valorado</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Existencia</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Costo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Monto Valorado</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Último Cierre Costo antes (<%=(fechaIni)%>)</b></td>
                </tr>

                <%
                String[] arrayfecha=fechaFinal.split("/");
                fechaFinal=arrayfecha[2]+"/"+arrayfecha[1]+"/"+arrayfecha[0];
                arrayfecha= fechaIni.split("/");
                fechaIni=arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
                System.out.println("fechas "+fechaIni+"  "+fechaFinal);
                String consulta = " select m.cod_material, m.nombre_material,um.abreviatura,m.stock_minimo_material,m.stock_maximo_material," +
                                  " m.stock_seguridad " +
                                  " ,(isnull((select sum(iad.CANT_TOTAL_INGRESO_FISICO) from INGRESOS_ALMACEN ia inner join INGRESOS_ALMACEN_DETALLE iad on "+
                                  " ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and ia.COD_ESTADO_INGRESO_ALMACEN<>2 and"+
                                  " ia.COD_ALMACEN="+codAlmacen+" and iad.COD_MATERIAL=m.COD_MATERIAL and ia.FECHA_INGRESO_ALMACEN<='"+fechaIni+" 23:59:59'),0)-"+
                                  " isnull((select sum(sad.CANTIDAD_SALIDA_ALMACEN)  from SALIDAS_ALMACEN sa inner join SALIDAS_ALMACEN_DETALLE sad on "+
                                  " sa.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN and sa.COD_ALMACEN="+codAlmacen+" and sad.COD_MATERIAL=m.COD_MATERIAL"+
                                  " and sa.COD_ESTADO_SALIDA_ALMACEN<>2 and sa.FECHA_SALIDA_ALMACEN<='"+fechaIni+" 23:59:59' ),0)) as stockInicio"+
                                  " ,(isnull((select sum(iad1.CANT_TOTAL_INGRESO_FISICO) from INGRESOS_ALMACEN ia1 inner join INGRESOS_ALMACEN_DETALLE iad1 on "+
                                  " ia1.COD_INGRESO_ALMACEN=iad1.COD_INGRESO_ALMACEN and ia1.COD_ESTADO_INGRESO_ALMACEN<>2 and"+
                                  " ia1.COD_ALMACEN="+codAlmacen+" and iad1.COD_MATERIAL=m.COD_MATERIAL and  ia1.FECHA_INGRESO_ALMACEN<='"+fechaFinal+" 23:59:59'),0)-"+
                                  " isnull((select sum(sad1.CANTIDAD_SALIDA_ALMACEN)  from SALIDAS_ALMACEN sa1 inner join SALIDAS_ALMACEN_DETALLE sad1 on "+
                                  " sa1.COD_SALIDA_ALMACEN=sad1.COD_SALIDA_ALMACEN and sa1.COD_ALMACEN="+codAlmacen+" and sad1.COD_MATERIAL=m.COD_MATERIAL"+
                                  " and sa1.COD_ESTADO_SALIDA_ALMACEN<>2 and sa1.FECHA_SALIDA_ALMACEN<='"+fechaFinal+" 23:59:59' ),0)) as stockFinal" +
                                  " ,(select top 1 cmp.COSTO_MATERIAL from COSTOS_MATERIAL_POR_MES cmp where cmp.COD_MES='"+fechaIni.substring(5, 7)+"' and cmp.COD_MATERIAL=m.COD_MATERIAL"+
                                  " and cmp.COD_ALMACEN="+codAlmacen+"  and cod_gestion=(select cod_gestion from gestiones where '"+fechaIni+"' between fecha_ini and fecha_fin) order by cmp.FECHA desc ) as costoInicio"+
                                  " ,(select top 1 cmp1.COSTO_MATERIAL from COSTOS_MATERIAL_POR_MES cmp1 where cmp1.COD_MES='"+fechaFinal.substring(5, 7)+"' and cmp1.COD_MATERIAL=m.COD_MATERIAL"+
                                  " and cmp1.COD_ALMACEN="+codAlmacen+" and cod_gestion=(select cod_gestion from gestiones where '"+fechaFinal+"' between fecha_ini and fecha_fin) order by cmp1.FECHA desc ) as costoFinal"+
                                  " ,(select top 1 cmp1.COSTO_MATERIAL from COSTOS_MATERIAL_POR_MES cmp1 where  cmp1.COD_MATERIAL=m.COD_MATERIAL"+
                                  " and cmp1.COD_ALMACEN="+codAlmacen+" and cmp1.FECHA<'"+fechaIni+"' order by cmp1.FECHA desc ) as ultimocosto"+
                                  " from materiales m inner join grupos g on m.COD_GRUPO=g.COD_GRUPO"+
                                  " inner join CAPITULOS c on c.COD_CAPITULO=g.COD_CAPITULO"+
                                  " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA"+
                                  " where m.COD_MATERIAL in ("+codMaterial+") and m.COD_ESTADO_REGISTRO=1 and m.MOVIMIENTO_ITEM="+movimiento+
                                  " order by m.NOMBRE_MATERIAL,c.NOMBRE_CAPITULO,g.NOMBRE_GRUPO";

                System.out.println("consulta 1 " + consulta);
                con = Util.openConnection(con);
                Statement st = con.createStatement();
                ResultSet res = st.executeQuery(consulta);
                while (res.next()) {
                    
                        out.print("<tr>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + res.getInt("COD_MATERIAL") + "</td>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + res.getString("nombre_material") + "</td>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" +res.getString("abreviatura")+ "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(res.getDouble("stockInicio")) + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(res.getDouble("costoInicio")) + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(res.getDouble("costoInicio")*res.getDouble("stockInicio")) + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(res.getDouble("stockFinal")) + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(res.getDouble("costoFinal")) + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(res.getDouble("costoFinal")*res.getDouble("stockFinal")) + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(res.getDouble("ultimocosto")) + "</td>");
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
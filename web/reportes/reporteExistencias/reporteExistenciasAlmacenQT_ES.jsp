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
            <h4 align="center">Reporte de Existencias Almacen con Detalle de última salida</h4>
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

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    con = Util.openConnection(con);

                    String fecha = request.getParameter("fecha") == null ? "01/01/2011" : request.getParameter("fecha");
                    String arrayfecha[] = fecha.split("/");
                    fecha = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
                    String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
                    //codMaterial = codMaterial.substring(1);
                    String codGrupo = request.getParameter("codGrupo") == null ? "''" : request.getParameter("codGrupo");
                    String movimiento = request.getParameter("movimiento") == null ? "''" : request.getParameter("movimiento");
                    String codCapitulo = request.getParameter("codCapitulo") == null ? "''" : request.getParameter("codCapitulo");
                    String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
                    String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
                    String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
                    String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
                    String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
                    String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");
                    codAlmacen="2";//almacen quintanilla
                    System.out.println("parametros" + fecha + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " "
                            + "" + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);
                    String codAlmacenT="17";//almacen transitorio

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
                                    <td><b>Capitulo</b></td>
                                    <td><%=nombreCapitulo%></td>
                                </tr>
                                <tr>
                                    <td><b>Grupo</b></td>
                                    <td><%=nombreGrupo%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>



            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr bgcolor="#cccccc">
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Capítulo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Grupo</b></td>
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Cod Material</b></td>
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Cod</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Item</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unidades</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Total Quintanilla</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Total Transitorio</b></td>
         <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Total Q + T</b></td>
         
<td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Nro. ult. Salida Quintanilla</b></td>
<td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center" width="150px"><b>Fecha ult. salida Quintanilla</b></td>
<td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Lote Producción Quintanilla</b></td>
<td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad ult. Salida Quintanilla</b></td>

<td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Nro. ult. Salida Transitorio</b></td>
<td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center" width="150px"><b>Fecha ult. salida Transitorio</b></td>
<td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Lote Producción Transitorio</b></td>
<td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad ult. Salida Transitorio</b></td>



                </tr>

                <%

                    String consulta = " select m.cod_material,m.CODIGO_ANTIGUO,m.nombre_material,um.abreviatura,m.stock_minimo_material,m.stock_maximo_material,m.stock_seguridad, "
                            + " (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia "
                            + " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen "
                            + " and ia.cod_almacen=" + codAlmacen + " and ia.fecha_ingreso_almacen<='" + fecha + " 23:59:59' and iade.cod_estado_material in(1, 2,3,4,5,6,7,8) and iade.cantidad_restante>0)as total_quintanilla, "
                            + " (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia "
                            + " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen "
                            + " and ia.cod_almacen=" + codAlmacenT + " and ia.fecha_ingreso_almacen<='" + fecha + " 23:59:59' and iade.cod_estado_material in(1, 2,3,4,5,6,7,8) and iade.cantidad_restante>0)as total_transitorio, "
                           
                                                        +" salidas.NRO_SALIDA_ALMACEN"
       +" ,salidas.FECHA_SALIDA_ALMACEN"
       +" ,salidas.COD_LOTE_PRODUCCION"
       +" ,salidas.CANTIDAD_SALIDA_ALMACEN"
                            
                              +", salidasT.NRO_SALIDA_ALMACEN NRO_SALIDA_ALMACENT"
       +" ,salidasT.FECHA_SALIDA_ALMACEN FECHA_SALIDA_ALMACENT"
       +" ,salidasT.COD_LOTE_PRODUCCION COD_LOTE_PRODUCCIONT"
       +" ,salidasT.CANTIDAD_SALIDA_ALMACEN CANTIDAD_SALIDA_ALMACENT"
                        + " ,c.nombre_capitulo" 
                            + " ,g.nombre_grupo"
                            + " from materiales m"
                            + " inner join grupos g on m.cod_grupo=g.cod_grupo"
                            + " inner join capitulos c on g.cod_capitulo=c.cod_capitulo"
                            + " inner join unidades_medida um on m.cod_unidad_medida = um.cod_unidad_medida"
                            + " left join ("
                            + " select "
                            + " sa.COD_SALIDA_ALMACEN"
                            + " , NRO_SALIDA_ALMACEN"
                            + " , COD_LOTE_PRODUCCION"
                            + " , FECHA_SALIDA_ALMACEN"
                            + " , sad.COD_MATERIAL"
                            + " , sad.CANTIDAD_SALIDA_ALMACEN"

                            + " from SALIDAS_ALMACEN sa "
                            + " inner join SALIDAS_ALMACEN_DETALLE sad"
                            + " on sa.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"
                            + " inner join("
                            + " select  max(sa.FECHA_SALIDA_ALMACEN) fecha_salida, sad.COD_MATERIAL "
                            + " from SALIDAS_ALMACEN sa"
                            + " inner join SALIDAS_ALMACEN_DETALLE sad"
                            + " on sa.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"
                            + " where sa.COD_ESTADO_SALIDA_ALMACEN=1"
                            + " and sa.COD_TIPO_SALIDA_ALMACEN<>13"
                            + " and sa.COD_ALMACEN='" + codAlmacen + "'"
                            + " group by  sad.COD_MATERIAL "
                            + " ) material_fecha"
                            + " on sa.FECHA_SALIDA_ALMACEN=material_fecha.fecha_salida"
                            + " and sad.COD_MATERIAL=material_fecha.COD_MATERIAL"
                            + " where sa.COD_ESTADO_SALIDA_ALMACEN=1"
                            + " and sa.COD_TIPO_SALIDA_ALMACEN<>13"
                            + " and sa.COD_ALMACEN='" + codAlmacen + "'"
                            + " ) salidas on salidas.cod_material=m.cod_material"
                            
                            
                            + " left join ("
                            + " select "
                            + " sa.COD_SALIDA_ALMACEN"
                            + " , NRO_SALIDA_ALMACEN"
                            + " , COD_LOTE_PRODUCCION"
                            + " , FECHA_SALIDA_ALMACEN"
                            + " , sad.COD_MATERIAL"
                            + " , sad.CANTIDAD_SALIDA_ALMACEN"

                            + " from SALIDAS_ALMACEN sa "
                            + " inner join SALIDAS_ALMACEN_DETALLE sad"
                            + " on sa.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"
                            + " inner join("
                            + " select  max(sa.FECHA_SALIDA_ALMACEN) fecha_salida, sad.COD_MATERIAL "
                            + " from SALIDAS_ALMACEN sa"
                            + " inner join SALIDAS_ALMACEN_DETALLE sad"
                            + " on sa.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"
                            + " where sa.COD_ESTADO_SALIDA_ALMACEN=1"
                            + " and sa.COD_TIPO_SALIDA_ALMACEN<>13"
                            + " and sa.COD_ALMACEN='" + codAlmacenT + "'"
                            + " group by  sad.COD_MATERIAL "
                            + " ) material_fecha"
                            + " on sa.FECHA_SALIDA_ALMACEN=material_fecha.fecha_salida"
                            + " and sad.COD_MATERIAL=material_fecha.COD_MATERIAL"
                            + " where sa.COD_ESTADO_SALIDA_ALMACEN=1"
                            + " and sa.COD_TIPO_SALIDA_ALMACEN<>13"
                            + " and sa.COD_ALMACEN='" + codAlmacenT + "'"
                            + " ) salidasT on salidasT.cod_material=m.cod_material"
                            
                            
                            
                            + " where  m.material_almacen=1 ";
                    if (!codCapitulo.equals("''")) {
                        consulta += " and (c.cod_capitulo in (" + codCapitulo + "))";
                    }
                    consulta += " and  m.movimiento_item=" + movimiento + " and m.cod_material in (" + codMaterial + ") and m.cod_estado_registro=1 order by m.nombre_material,c.nombre_capitulo,g.nombre_grupo  ";

                    System.out.println("consulta 1 " + consulta.substring(0, 3000));
                    System.out.println("consulta 1 " + consulta.substring(3001));
                    con = Util.openConnection(con);
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = st.executeQuery(consulta);
                    while (rs.next()) {
                        //System.out.println("consulta 1 "+ consulta);
                        //double aprobados = rs.getDouble("aprobados");
                        //aprobados = aprobados + rs.getDouble("devoluciones") - rs.getDouble("salidas");
                        //aprobados = aprobados - rs.getDouble("cuarentena");
                        //aprobados = aprobados- rs.getDouble("rechazado");
                        //aprobados = aprobados - rs.getDouble("vencido");
                        //aprobados = aprobados - rs.getDouble("reanalisis");

                        /*  <option value="0"> Todos los Item </option>
                         <option value="1"> Items con Stock menores al Stock Mínimo</option>
                         <option value="2"> Items con Stock menores al Stock de Seguridad</option>
                         <option value="3"> Items con Stock Normales</option>
                         <option value="4"> Items con Stock mayores al Stock Máximo</option> */
                        int sw = 0;

                        if (codStock.equals("0")) {
                            sw = 1;
                        }

                       /* if (codStock.equals("1") && aprobados <= rs.getDouble("stock_minimo_material")) {
                            sw = 1;
                        }

                        if (codStock.equals("2") && aprobados >= rs.getDouble("stock_minimo_material") && aprobados < rs.getDouble("stock_seguridad")) {
                            sw = 1;
                        }
                        if (codStock.equals("3") && aprobados >= rs.getDouble("stock_minimo_material") && aprobados < rs.getDouble("stock_maximo_material")) {
                            sw = 1;
                        }
                        if (codStock.equals("4") && aprobados > rs.getDouble("stock_maximo_material")) {
                            sw = 1;
                        }
*/
                        if (sw == 1) {
                            //System.out.println("el saldo " + aprobados);
                            out.print("<tr>");
                            out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + (rs.getString("nombre_capitulo") == null ? "" : rs.getString("nombre_capitulo")) + "</td>");
                            out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + (rs.getString("nombre_grupo") == null ? "" : rs.getString("nombre_grupo")) + "</td>");
                            out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + (rs.getString("cod_material") == null ? "" : rs.getString("cod_material")) + "</td>");
                            out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + (rs.getString("CODIGO_ANTIGUO") == null ? "" : rs.getString("CODIGO_ANTIGUO")) + "</td>");
                            out.print("<td  class='border'style='border-bottom:1px solid #cccccc;padding:8px' align='left'  >" + rs.getString("nombre_material") + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + (rs.getString("abreviatura") == null ? "" : rs.getString("abreviatura")) + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(rs.getDouble("total_quintanilla")) + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(rs.getDouble("total_transitorio")) + "</td>");
                out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(rs.getDouble("total_quintanilla")+rs.getDouble("total_transitorio")) + "</td>");
                
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + (rs.getString("nro_salida_almacen") == null ? "" : rs.getString("nro_salida_almacen")) + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + (rs.getString("fecha_salida_almacen") == null ? "" : rs.getString("fecha_salida_almacen")) + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + (rs.getString("cod_lote_produccion") == null ? "" : rs.getString("cod_lote_produccion")) + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px' >" + formato.format(rs.getDouble("cantidad_salida_almacen")) + "</td>");
                            
                              out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + (rs.getString("nro_salida_almacenT") == null ? "" : rs.getString("nro_salida_almacenT")) + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + (rs.getString("fecha_salida_almacenT") == null ? "" : rs.getString("fecha_salida_almacenT")) + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + (rs.getString("cod_lote_produccionT") == null ? "" : rs.getString("cod_lote_produccionT")) + "</td>");
                            out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px' >" + formato.format(rs.getDouble("cantidad_salida_almacenT")) + "</td>");
                            
                            
                            
                            out.print("</tr>");
                        }
                    }


                %>
            </table>

            <%                    } catch (Exception e) {
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
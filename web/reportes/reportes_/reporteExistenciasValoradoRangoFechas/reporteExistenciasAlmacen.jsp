package reportes.reporteExistenciasValoradoRangoFechas;




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
            <h4 align="center">Reporte de Existencias Almacen Valorado</h4>

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

            String fechaF = request.getParameter("fechaF") == null ? "01/01/2011" : request.getParameter("fechaF");

            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            //codMaterial = codMaterial.substring(1);
            String codGrupo = request.getParameter("codGrupo") == null ? "''" : request.getParameter("codGrupo");
            String codCapitulo = request.getParameter("codCapitulo") == null ? "''" : request.getParameter("codCapitulo");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");



            System.out.println("parametros" + fecha + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " " +
                    "" + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);




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
                                <tr>
                                    <td><b>Fecha Inicio : </b></td>
                                    <td><%=fecha%></td>
                                </tr>
                                <tr>
                                    <td><b>Fecha Final : </b></td>
                                    <td><%=fechaF%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>

<br>
<br>

            <table width="100%" align="center" class="outputText0" style="border : solid #cccccc 1px;" cellpadding="1" cellspacing="0" >


                <tr bgcolor="#cccccc">
                    <td  height="20px" style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;" bgcolor="#cccccc"  align="center" ><b>Cod</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;" bgcolor="#cccccc"  align="center" ><b>Item</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;" bgcolor="#cccccc"  align="center"><b>Unidades</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;"   bgcolor="#cccccc"  align="center"><b>Aprobado</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;" bgcolor="#cccccc"  align="center"><b>Cuarentena</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;" bgcolor="#cccccc"  align="center"><b>Rechazado</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;"   bgcolor="#cccccc"  align="center"><b>Obsoleto</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;"  bgcolor="#cccccc"  align="center"><b>Vencido</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;" bgcolor="#cccccc"  align="center"><b>Total Malos</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;"  bgcolor="#cccccc"  align="center"><b>Total Buenos</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;"  bgcolor="#cccccc"  align="center"><b>C. Total</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;"  bgcolor="#cccccc"  align="center"><b> c/u Act.</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;"  bgcolor="#cccccc"  align="center"><b>Dif. Act</b></td>
                    <td  style="border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;"  bgcolor="#cccccc"  align="center"><b>Imp. Act</b></td>


                </tr>

                <%
                String arrayfecha[] = fecha.split("/");
                fecha = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
                String arrayfechaF[] = fechaF.split("/");
                fechaF = arrayfechaF[2] + "/" + arrayfechaF[1] + "/" + arrayfechaF[0];

                String consulta = " select m.cod_material,m.CODIGO_ANTIGUO,m.nombre_material,um.abreviatura,m.stock_minimo_material,m.stock_maximo_material,m.stock_seguridad, " +
                        " (select SUM(iade.cantidad_parcial) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                        " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                        " and ia.cod_almacen=" + codAlmacen + " and ia.fecha_ingreso_almacen>='" + fecha + " 00:00:00' and ia.fecha_ingreso_almacen<='" + fechaF + " 23:59:59' )as aprobados, " +
                        " (select SUM(sadi.cantidad) from salidas_almacen_detalle sad,salidas_almacen_detalle_ingreso sadi,ingresos_almacen_detalle_estado iade, salidas_almacen sa " +
                        " WHERE sa.cod_salida_almacen=sad.cod_salida_almacen and sa.estado_sistema=1 and sa.cod_estado_salida_almacen=1 and sad.cod_salida_almacen=sadi.cod_salida_almacen and sad.cod_material=sadi.cod_material and " +
                        " sadi.cod_ingreso_almacen=iade.cod_ingreso_almacen and sadi.cod_material=iade.cod_material and sadi.ETIQUETA=iade.ETIQUETA  and sa.cod_almacen=" + codAlmacen + " " +
                        " and sad.cod_material=m.cod_material and sa.fecha_salida_almacen>='" + fecha + " 00:00:00' and sa.fecha_salida_almacen<='" + fechaF + " 23:59:59' )as salidas, " +
                        " (select sum(iad.cant_total_ingreso_fisico) from DEVOLUCIONES d, ingresos_almacen ia,INGRESOS_ALMACEN_DETALLE iad " +
                        " where ia.cod_devolucion=d.cod_devolucion and ia.fecha_ingreso_almacen>='" + fecha + " 00:00:00' and ia.fecha_ingreso_almacen<='" + fechaF + " 23:59:59' and d.cod_estado_devolucion=1 and d.estado_sistema=1 and ia.cod_estado_ingreso_almacen=1 " +
                        " and ia.cod_almacen=" + codAlmacen + " and d.cod_almacen=" + codAlmacen + " " +
                        " and ia.cod_almacen=d.cod_almacen and ia.cod_ingreso_almacen=iad.cod_ingreso_almacen and iad.cod_material=m.cod_material)as devoluciones, " +
                        " (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                        " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                        " and ia.cod_almacen=" + codAlmacen + " and iade.cod_estado_material=1 and ia.fecha_ingreso_almacen>='" + fecha + " 00:00:00' and ia.fecha_ingreso_almacen<='" + fechaF + " 23:59:59' )as cuarentena, " +
                        " (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                        " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                        " and ia.cod_almacen=" + codAlmacen + " and iade.cod_estado_material=3 and ia.fecha_ingreso_almacen>='" + fecha + " 00:00:00' and ia.fecha_ingreso_almacen<='" + fechaF + " 23:59:59' )as rechazado, " +
                        " (select SUM(iade.cantidad_restante)  from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                        " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                        " and ia.cod_almacen=" + codAlmacen + " and iade.cod_estado_material=4 and ia.fecha_ingreso_almacen>='" + fecha + " 00:00:00' and ia.fecha_ingreso_almacen<='" + fechaF + " 23:59:59' )as vencido, " +
                        " (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                        " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                        " and ia.cod_almacen=" + codAlmacen + " and iade.cod_estado_material=5 and ia.fecha_ingreso_almacen>='" + fecha + " 00:00:00' and ia.fecha_ingreso_almacen<='" + fechaF + " 23:59:59')as obsoleto, " +
                        " (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                        " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                        " and ia.cod_almacen=" + codAlmacen + " and iade.cod_estado_material in(3,4,5) and ia.fecha_ingreso_almacen>='" + fecha + " 00:00:00' and ia.fecha_ingreso_almacen<='" + fechaF + " 23:59:59' )as observado " +
                        " from materiales m,grupos g,capitulos c,unidades_medida um where m.cod_grupo=g.cod_grupo and g.cod_capitulo=c.cod_capitulo " +
                        " and m.cod_unidad_medida = um.cod_unidad_medida and  m.material_almacen=1 " +
                        " and (c.cod_capitulo in (" + codCapitulo + "))  and  m.movimiento_item=1 and m.cod_material in (" + codMaterial + ") and m.cod_estado_registro=1 order by m.nombre_material,c.nombre_capitulo,g.nombre_grupo  ";

                System.out.println("consulta 1 " + consulta.substring(0, 3000));
                System.out.println("consulta 1 " + consulta.substring(3001));
                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                while (rs.next()) {
                    int codigo_material = rs.getInt("cod_material");
                    //System.out.println("consulta 1 "+ consulta);
                    double aprobados = rs.getDouble("aprobados");
                    aprobados = aprobados + rs.getDouble("devoluciones") - rs.getDouble("salidas");
                    aprobados = aprobados - rs.getDouble("cuarentena");
                    aprobados = aprobados - rs.getDouble("rechazado");
                    aprobados = aprobados - rs.getDouble("vencido");
                    aprobados = aprobados - rs.getDouble("obsoleto");

                    /*  <option value="0"> Todos los Item </option>
                    <option value="1"> Items con Stock menores al Stock Mínimo</option>
                    <option value="2"> Items con Stock menores al Stock de Seguridad</option>
                    <option value="3"> Items con Stock Normales</option>
                    <option value="4"> Items con Stock mayores al Stock Máximo</option> */

                    int sw = 0;

                    if (codStock.equals("0")) {
                        sw = 1;
                    }

                    if (codStock.equals("1") && aprobados <= rs.getDouble("stock_minimo_material")) {
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


                    String costo = "SELECT top 1 isnull(costo_material,0)as costo_material from costos_material_por_mes ";
                    costo += " where cod_material=" + codigo_material + " and fecha<='" + fechaF + " 23:59:59'  ";
                    costo += " and cod_almacen=" + codAlmacen + " order by fecha desc ";
                    System.out.println("costo:" + costo);
                    double total_costo_actualizado = 0;
                    double total_costo = 0;
                    double costo_unitario = 0;
                    Statement st_costo = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_costo = st_costo.executeQuery(costo);
                    while (rs_costo.next()) {
                        costo_unitario = rs_costo.getDouble(1);
                        total_costo = costo_unitario * aprobados;
                        total_costo_actualizado = total_costo;
                    }


                    if (sw == 1) {
                        System.out.println("el saldo " + aprobados);
                        out.print("<tr>");
                        out.print("<th height='20px' class='border' align='right'  style='border-bottom : solid #cccccc 1px;padding:8px' >" + (rs.getString("CODIGO_ANTIGUO") == null ? "" : rs.getString("CODIGO_ANTIGUO")) + "</th>");
                        out.print("<td style='border-bottom : solid #cccccc 1px;'  align='left;padding:8px'  >" + rs.getString("nombre_material") + "</td>");
                        out.print("<th style='border-bottom : solid #cccccc 1px;padding:8px'  align='center'   >[" + (rs.getString("abreviatura") == null ? "" : rs.getString("abreviatura")) + "]</th>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >" + formato.format(aprobados) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'    >" + formato.format(rs.getDouble("cuarentena")) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'   >" + formato.format(rs.getDouble("rechazado")) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >" + formato.format(rs.getDouble("obsoleto")) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >" + formato.format(rs.getDouble("vencido")) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >" + formato.format(rs.getDouble("observado")) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >" + formato.format(aprobados + rs.getDouble("cuarentena")) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >" + formato.format((costo_unitario) * (aprobados + rs.getDouble("cuarentena"))) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >" + (costo_unitario) + "</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >0.0</td>");
                        out.print("<td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  >" + formato.format((costo_unitario) * (aprobados + rs.getDouble("cuarentena"))) + "</td>");
                        out.print("</tr>");
                    }
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
package reportes.reporteExistenciasEstado;


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
            <h4 align="center">Reporte de Existencias Por Estado</h4>

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
            String codCapitulo = request.getParameter("codCapitulo") == null ? "''" : request.getParameter("codCapitulo");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");
            String estadoMaterial = request.getParameter("codCapituloEstadoMaterial") == null ? "''" : request.getParameter("codCapituloEstadoMaterial");
            String nombreEstado = request.getParameter("nombreEstado") == null ? "''" : request.getParameter("nombreEstado");
            System.out.println("estadoMaterial:" + estadoMaterial);
            System.out.println("nombreEstado:" + nombreEstado);



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
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>



            <table width="70%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <th height="30PX" class="border" style="border : solid #D8D8D8 1px;padding:8px" bgcolor="#f2f2f2"  align="center" ><b>Cod</b></th>
                    <th  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Item</b></th>
                    <th  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unidades</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b><%=nombreEstado%></b></th>
                    <%--td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Cuarentena</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Rechazado</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Obsoleto</b></td>
                    <td style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Vencido</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Total Malos</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Total Buenos</b></td--%>


                </tr>

                <%
                //String sql_mat = "select cod_material,nombre_material,CODIGO_ANTIGUO from materiales where cod_material in (" + codMaterial + ")";
                String sql_mat = "select m.cod_material,m.nombre_material,m.CODIGO_ANTIGUO,u.ABREVIATURA from materiales m , UNIDADES_MEDIDA u where m.cod_material in (" + codMaterial + ") and u.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA";
                System.out.println("sql_mat:" + sql_mat);
                con = Util.openConnection(con);
                Statement st_mat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_mat = st_mat.executeQuery(sql_mat);
                while (rs_mat.next()) {


                    String sql_report = " select iade.cod_material,iade.etiqueta,iade.cod_ingreso_almacen,ia.nro_ingreso_almacen,ia.fecha_ingreso_almacen,";
                    sql_report += " isnull((select top 1 fc.cantidad  from fechas_cambiosestado fc where iade.etiqueta = fc.etiqueta and";
                    sql_report += " iade.cod_material = fc.cod_material and iade.cod_ingreso_almacen = fc.cod_ingreso_almacen AND";
                    sql_report += " fc.fecha_cambioestado <= '" + fecha + " 23:59:59' and fc.cod_estado_control_salida = 0";
                    sql_report += " order by fc.fecha_cambioestado desc), 0) as cantidad_ingreso,";
                    sql_report += " ( select top 1 fc.cod_estado_material from fechas_cambiosestado fc  where iade.etiqueta = fc.etiqueta and";
                    sql_report += " iade.cod_material = fc.cod_material and iade.cod_ingreso_almacen = fc.cod_ingreso_almacen AND";
                    sql_report += " fc.fecha_cambioestado <= '" + fecha + " 23:59:59' and fc.cod_estado_control_salida = 0 order by fc.fecha_cambioestado desc) as estado_material,";
                    sql_report += " isnull(( select sum(sadi.cantidad) from salidas_almacen_detalle_ingreso sadi, salidas_almacen sa";
                    sql_report += " where sa.cod_salida_almacen = sadi.cod_salida_almacen and";
                    sql_report += " iade.etiqueta = sadi.etiqueta and iade.cod_material = sadi.cod_material and iade.cod_ingreso_almacen = sadi.cod_ingreso_almacen AND";
                    sql_report += " sa.fecha_salida_almacen <= '" + fecha + " 23:59:59' and sa.cod_estado_salida_almacen = 1 and";
                    sql_report += " sa.fecha_salida_almacen >= ( select top 1 fc.fecha_cambioestado  from fechas_cambiosestado fc  where sadi.etiqueta = fc.etiqueta and";
                    sql_report += " sadi.cod_material = fc.cod_material and sadi.cod_ingreso_almacen = fc.cod_ingreso_almacen AND";
                    sql_report += " fc.fecha_cambioestado <='" + fecha + " 23:59:59' and fc.cod_estado_control_salida = 0 order by fc.fecha_cambioestado desc ) ), 0) as cantidad_salida,";
                    sql_report += " isnull(( select sum(dde.cantidad_devuelta) from devoluciones_detalle_etiquetas dde, devoluciones d,ingresos_almacen ia1";
                    sql_report += " where d.cod_devolucion = dde.cod_devolucion and iade.etiqueta = dde.etiqueta and iade.cod_material = dde.cod_material and";
                    sql_report += " iade.cod_ingreso_almacen =dde.cod_ingreso_almacen AND ia1.fecha_ingreso_almacen <= '" + fecha + " 23:59:59' and";
                    sql_report += " ia1.cod_estado_ingreso_almacen = 1 and d.cod_devolucion = ia1.cod_devolucion and ia1.fecha_ingreso_almacen >=";
                    sql_report += " (select top 1 fc.fecha_cambioestado from fechas_cambiosestado fc where dde.etiqueta = fc.etiqueta and dde.cod_material = fc.cod_material and";
                    sql_report += " dde.cod_ingreso_almacen =fc.cod_ingreso_almacen AND fc.fecha_cambioestado <= '" + fecha + " 23:59:59' and fc.cod_estado_control_salida = 0";
                    sql_report += " order by fc.fecha_cambioestado desc) ), 0) as cantidad_devuelta";
                    sql_report += " from ingresos_almacen_detalle_estado iade,  ingresos_almacen_detalle iad,  ingresos_almacen ia";
                    sql_report += " where iade.cod_material in ( " + rs_mat.getInt(1) + ") and ia.cod_estado_ingreso_almacen = 1 and  iad.cod_ingreso_almacen = ia.cod_ingreso_almacen and";
                    sql_report += " ia.estado_sistema = 1 and iade.cod_material = iad.cod_material and iade.cod_ingreso_almacen = iad.cod_ingreso_almacen and";
                    sql_report += " ia.fecha_ingreso_almacen <= '" + fecha + " 23:59:59' and ia.cod_almacen = " + codAlmacen + "";



                    System.out.println("consulta 1 " + sql_report);
                    //System.out.println("consulta 1 " + sql_report.substring(2001, 4000));
                    //System.out.println("consulta 1 " + sql_report.substring(4001)); //,6000
                    //System.out.println("consulta 1 "+ consulta.substring(6001,8000) );
                    //System.out.println("consulta 1 "+ consulta.substring(4001,5000) );
                    //System.out.println("consulta 1 "+ consulta.substring(2001,3000) );

                    //con = Util.openConnection(con);
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = st.executeQuery(sql_report);
                    double aprobado = 0;
                    int cod_ingreso = 0;
                    Object obj = request.getSession().getAttribute("ManagedAccesoSistema");
                    ManagedAccesoSistema var = (ManagedAccesoSistema) obj;
                    String codigoPersonal = var.getUsuarioModuloBean().getCodUsuarioGlobal();
                    String sql_del = "delete from reporte_existencias_almacen where cod_personal=" + codigoPersonal + "";
                    System.out.println("sql_del:" + sql_del);
                    Statement st_del = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    int rs_del = st_del.executeUpdate(sql_del);
                    while (rs.next()) {

                        int estado_material = rs.getInt("estado_material");
                        int codigo_material = rs.getInt("cod_material");
                        System.out.println("Integer.parseInt(estadoMaterial):" + Integer.parseInt(estadoMaterial));
                        System.out.println("estado_material:" + estado_material);
                        if (Integer.parseInt(estadoMaterial) == estado_material) {
                            cod_ingreso++;

                            aprobado = rs.getDouble("cantidad_ingreso") - rs.getDouble("cantidad_salida") + rs.getDouble("cantidad_devuelta");
                            if (aprobado > 0) {
                                String sql_insert = " insert into reporte_existencias_almacen (cod_personal,COD_INGRESO_ALMACEN,COD_MATERIAL,APROBADOS,total)";
                                sql_insert += " values (" + codigoPersonal + "," + cod_ingreso + "," + codigo_material + ",";
                                sql_insert += " " + aprobado + "," + rs.getDouble("cantidad_ingreso") + ")";
                                System.out.println("sql_insert:" + sql_insert);
                                Statement st_insert = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                int rs_insert = st_insert.executeUpdate(sql_insert);
                            }


                       }

                    }
                    String sql_kardex = "select SUM(r.APROBADOS) as sumCan from REPORTE_EXISTENCIAS_ALMACEN r,MATERIALES m ";
                    sql_kardex += " where r.COD_PERSONAL=" + codigoPersonal + " and m.COD_MATERIAL=r.COD_MATERIAL GROUP by m.NOMBRE_MATERIAL";
                    Statement st_kardex = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_kardex = st_kardex.executeQuery(sql_kardex);
                    while (rs_kardex.next()) {
                        out.print("<tr>");
                        out.print("<td height='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;PADDING:8PX' >" + (rs_mat.getString("CODIGO_ANTIGUO") == null ? "" : rs_mat.getString("CODIGO_ANTIGUO")) + "</td>");
                        out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >" + rs_mat.getString("nombre_material") + "</td>");
                        out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >" + rs_mat.getString("ABREVIATURA") + "</td>");
                        
                        out.print("<td align='right' style='border : solid #D8D8D8 1px;PADDING:8PX' class='border'   >" + formato.format(rs_kardex.getDouble("sumCan")) + "</td>");

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
package reportes.reporteIngresosAlmacen.consumoPromedioMensualPorItem;


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
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../js/general.js"></script>
        <script type="text/javascript" src="jquery/jsapi"></script>
        <script type="text/javascript" src="jquery/jquery-1.4.4.min.js"></script>
        <script type="text/javascript" src="jquery/jquery.gvChart-1.1.min.js"></script>
        <script type="text/javascript">
            gvChartInit();
            jQuery(document).ready(function(){
                jQuery('#myTable2').gvChart({
                    chartType: 'LineChart',
                    gvSettings: {
                        vAxis: {title: 'Cantidad de Salida'},
                        hAxis: {title: 'Consumo Mensual en Unidades'},
                        //width: 720,
                        width: 720,
                        //height: 300
                        height: 300
                    }
                });


                /*jQuery('#myTable2').gvChart({
                chartType: 'LineChart',
                gvSettings: {
                    vAxis: {title: 'No of players'},
                    hAxis: {title: 'Month'},
                    width: 720,
                    height: 300
                    }
            });*/

                jQuery('#myTable3').gvChart({
                    chartType: 'BarChart',
                    gvSettings: {
                        vAxis: {title: 'No of players'},
                        hAxis: {title: 'Month'},
                        width: 720,
                        height: 300
                    }
                });


                jQuery('#myTable4').gvChart({
                    chartType: 'ColumnChart',
                    gvSettings: {
                        vAxis: {title: 'No of players'},
                        hAxis: {title: 'Month'},
                        width: 720,
                        height: 300
                    }
                });

                jQuery('#myTable5').gvChart({
                    chartType: 'PieChart',
                    gvSettings: {
                        vAxis: {title: 'No of players'},
                        hAxis: {title: 'Month'},
                        width: 720,
                        height: 300
                    }
                });
            });
        </script>
        <style>
            body{
                text-align: center;
                font-family: Arial, sans-serif;
                font-size: 12px;
            }

            a{
                text-decoration: none;
                font-weight: bold;
                color: #555;
            }

            a:hover{
                color: #000;
            }

            div.main{
                margin: auto;
                text-align: left;
                width: 720px;
            }

            div.clean{
                border: 1px solid #850000;
            }

            .clean td, .clean th{
                border: 2px solid #bbb;
                background: #ddd;
                padding: 5px 10px;
                text-align: center;
                border-radius: 2px;
            }

            .clean table{
                margin: auto;
                margin-top: 15px;
                margin-bottom: 15px;
            }

            .clean caption{
                font-weight: bold;

            }

            .gvChart,.clean{
                border: 2px solid #850000;
                border-radius: 5px;
                -moz-border-radius: 10px;
                width: 720px;

                margin: auto;
                margin-top: 20px;
            }

            pre{
                background: #eee;
                padding: 10px;
                border-radius: 10px;
                -moz-border-radius: 10px;
            }
        </style>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Ingresos Por Item</h4>

            <%
        try {

            /*
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="codCompProd">
            <input type="hidden" name="nombreCompProd">
             */

            /* NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("####.####;(####.####)");*/
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) nf;
            formato.applyPattern("#,###.00");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");

            con = Util.openConnection(con);



            String fechaInicial = request.getParameter("fecha1") == null ? "01/01/2011" : request.getParameter("fecha1");
            String fechaInicial1 = fechaInicial;
            String arrayfecha1[] = fechaInicial.split("/");
            fechaInicial = arrayfecha1[2] + "/" + arrayfecha1[1] + "/" + arrayfecha1[0];

            String fechaFinal = request.getParameter("fecha2") == null ? "01/01/2011" : request.getParameter("fecha2");
            String fechaFinal1 = fechaFinal;
            String arrayfecha2[] = fechaFinal.split("/");
            fechaFinal = arrayfecha2[2] + "/" + arrayfecha2[1] + "/" + arrayfecha2[0];

            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            String nombreMaterialP = request.getParameter("nombreMaterialP") == null ? "''" : request.getParameter("nombreMaterialP");
            //codMaterial = codMaterial.substring(1);
            String codGrupo = request.getParameter("codGrupoArray") == null ? "''" : request.getParameter("codGrupoArray");
            String codCapitulo = request.getParameter("codCapituloArray") == null ? "''" : request.getParameter("codCapituloArray");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");
            String codProveedor = request.getParameter("codProveedor") == null ? "''" : request.getParameter("codProveedor");
            String codTipoIngresoAlmacen = request.getParameter("codTipoIngresoAlmacen") == null ? "''" : request.getParameter("codTipoIngresoAlmacen");
            String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");

            System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " " +
                    "" + codFilial + " " + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);
            double costoTotal = 0;



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
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">
                        <img src="../../../img/cofar.png" alt="logo cofar" align="left" />
                    </td><td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">
                        <table border="0" class="outputText1" >
                            <tbody>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><b>Filial</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=nombreFilial%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><b>Almacen</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=nombreAlmacen%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><b>Capitulo</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=nombreCapitulo%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><b>Grupo</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=nombreGrupo%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><b>Fecha Inicial</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>

            <BR>
            <BR>





            <%

                String consulta = " select DISTINCT m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.ABREVIATURA" +
                        " from INGRESOS_ALMACEN ia " +
                        " inner join TIPOS_INGRESO_ALMACEN t on ia.COD_TIPO_INGRESO_ALMACEN = t.COD_TIPO_INGRESO_ALMACEN " +
                        " inner join PROVEEDORES p on p.COD_PROVEEDOR = ia.COD_PROVEEDOR " +
                        " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN " +
                        " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA " +
                        " inner join MATERIALES m on m.COD_MATERIAL = iad.COD_MATERIAL " +
                        " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO " +
                        " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO" +
                        " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                        " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL " +
                        " left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA " +
                        " where ia.COD_ESTADO_INGRESO_ALMACEN<>2 and 0=0  ";
                /*if (!nombreMaterialP.equals("")) {
                consulta = consulta + " and m.nombre_material like '" + nombreMaterialP + "%' ";
                }*/
                if (!codFilial.equals("") && !codFilial.equals("0")) {
                    consulta = consulta + " and f.cod_filial = '" + codFilial + "' ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    consulta = consulta + " and ia.COD_ALMACEN = '" + codAlmacen + "' ";
                }
                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    consulta = consulta + " and ia.FECHA_INGRESO_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }
                if (!codProveedor.equals("") && !codProveedor.equals("0")) {
                    consulta = consulta + " and ia.COD_PROVEEDOR = '" + codProveedor + "' ";
                }
                if (!codTipoIngresoAlmacen.equals("") && !codTipoIngresoAlmacen.equals("0")) {
                    consulta = consulta + " and ia.COD_TIPO_INGRESO_ALMACEN = '" + codTipoIngresoAlmacen + "' ";
                }
                if (!codCapitulo.equals("") && !codCapitulo.equals("0")) {
                    consulta = consulta + " and c.COD_CAPITULO in (" + codCapitulo + ") ";
                }
                if (!codGrupo.equals("") && !codGrupo.equals("0")) {
                    consulta = consulta + " and g.COD_GRUPO in (" + codGrupo + ") ";
                }
                if (!codMaterial.equals("") && !codMaterial.equals("0")) {
                    consulta = consulta + " and m.cod_material in (" + codMaterial + ") ";
                }
                consulta = consulta + " order by m.NOMBRE_MATERIAL desc ";



                System.out.println("consulta 1 " + consulta);

                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                while (rs.next()) {
                    int codMaterialKardex = rs.getInt(1);
                    String nomMaterialKardex = rs.getString(2);
                    /*******************************************************************************************************************/
                    /********************************** begin ************************************************/
                    Object obj = request.getSession().getAttribute("ManagedAccesoSistema");
                    ManagedAccesoSistema var = (ManagedAccesoSistema) obj;
                    String codigoPersonal = var.getUsuarioModuloBean().getCodUsuarioGlobal();

                    String sql_delete = "delete from kardex_item_movimiento";
                    sql_delete += " where cod_persona=" + codigoPersonal + "";
                    System.out.println("sql_delete:" + sql_delete);
                    Statement st_delete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    int rs_delete = st_delete.executeUpdate(sql_delete);
                    int ingresos_total_anterior = 0;
                    int salidas_total_anterior = 0;
                    int cantidad_restante_anterior = 0;
                    int cantidad_restante_anterior_kardexmovimiento_global = 0;



                    String sql_ingresos = "SELECT sum(iad.cant_total_ingreso_fisico)as ingresos_total_anterior";
                    sql_ingresos += " FROM ingresos_almacen_detalle iad,ingresos_almacen ia";
                    sql_ingresos += " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen";
                    sql_ingresos += " and ia.cod_estado_ingreso_almacen=1 and ia.estado_sistema=1";
                    sql_ingresos += " and iad.cod_material=" + codMaterialKardex + "";
                    sql_ingresos += " and ia.cod_almacen=" + codAlmacen + "";
                    sql_ingresos += " and ia.fecha_ingreso_almacen<'" + fechaInicial + "' ";
                    System.out.println("sql_ingresos:" + sql_ingresos);
                    Statement st_ingresos = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_ingresos = st_ingresos.executeQuery(sql_ingresos);
                    while (rs_ingresos.next()) {
                        ingresos_total_anterior = rs_ingresos.getInt("ingresos_total_anterior");
                    }




                    String sql_salidas = " select sum(sad.cantidad_salida_almacen)as salidas_total_anterior";
                    sql_salidas += " from salidas_almacen_detalle sad,salidas_almacen sa";
                    sql_salidas += " where sad.cod_salida_almacen=sa.cod_salida_almacen";
                    sql_salidas += " and sa.cod_estado_salida_almacen=1 and sa.estado_sistema=1";
                    sql_salidas += " and sad.cod_material=" + codMaterialKardex + "";
                    sql_salidas += " and sa.cod_almacen=" + codAlmacen + "";
                    sql_salidas += " and sa.fecha_salida_almacen<='" + fechaInicial + "'";
                    System.out.println("sql_salidas:" + sql_salidas);
                    Statement st_salidas = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_salidas = st_salidas.executeQuery(sql_salidas);
                    while (rs_salidas.next()) {
                        salidas_total_anterior = rs_salidas.getInt("salidas_total_anterior");
                    }

                    cantidad_restante_anterior = (ingresos_total_anterior) - (salidas_total_anterior);
                    cantidad_restante_anterior_kardexmovimiento_global = (cantidad_restante_anterior);


                    /***************************************************************/
                    String fecha_cambio = " select month(fecha)as mes,year(fecha)as anio,m.nombre_mes";
                    fecha_cambio += " from TIPOCAMBIOS_MONEDA tc, meses m";
                    fecha_cambio += " where month(fecha)=m.cod_mes and fecha>='" + fechaInicial + "' and fecha<='" + fechaFinal + "'";
                    fecha_cambio += " group by month(fecha),year(fecha),m.nombre_mes";
                    fecha_cambio += " order by year(fecha), month(fecha)";
                    System.out.println("fecha_cambio:" + fecha_cambio);
                    Statement st_cambio = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_cambio = st_cambio.executeQuery(fecha_cambio);
                    int suma_total_ingresos = 0;
                    int suma_total_salidas = 0;


            %>
            <p align="center" class="outputText2"><B>ITEM</B> :<%=nomMaterialKardex%></p>
            <table class="outputText2" align="CENTER" width="60%">
                <tr bgcolor="#cccccc">
                    <th style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">Año</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">Mes</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">Ingreso</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">Salida</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">Saldo</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">Proveedor</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">Estado</th>
                </tr>

                <%

                                int cod_reporte = 1;
                                while (rs_cambio.next()) {
                                    int mes = rs_cambio.getInt(1);
                                    int anio = rs_cambio.getInt(2);
                                    String nombre_mes = rs_cambio.getString(3);
                %>
                <tr>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=anio%></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=mes%></td>

                    <%
                                                    sql_ingresos = " SELECT round(isnull(sum(iad.cant_total_ingreso_fisico),0),5)as cantidad_ingresos_almacen";
                                                    sql_ingresos += " FROM ingresos_almacen_detalle iad,ingresos_almacen ia";
                                                    sql_ingresos += " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen";
                                                    sql_ingresos += " and ia.cod_estado_ingreso_almacen=1 and ia.estado_sistema=1";
                                                    sql_ingresos += " and iad.cod_material=" + codMaterialKardex + "";

                                                    sql_ingresos += " and ia.cod_almacen=" + codAlmacen + "";

                                                    sql_ingresos += " and month(ia.fecha_ingreso_almacen)=" + mes + " and year(ia.fecha_ingreso_almacen)=" + anio + "";
                                                    System.out.println("sql_ingresos--:" + sql_ingresos);
                                                    st_ingresos = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                                    rs_ingresos = st_ingresos.executeQuery(sql_ingresos);
                                                    int CANTIDAD_INGRESO = 0;
                                                    if (rs_ingresos.next()) {
                                                        suma_total_ingresos = suma_total_ingresos + rs_ingresos.getInt("cantidad_ingresos_almacen");
                                                        cantidad_restante_anterior = cantidad_restante_anterior + rs_ingresos.getInt("cantidad_ingresos_almacen");
                                                        CANTIDAD_INGRESO = rs_ingresos.getInt("cantidad_ingresos_almacen");
                                                    }
                    %>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=CANTIDAD_INGRESO%></td>

                    <%



                                                    sql_salidas = " select round(isnull(sum(sad.cantidad_salida_almacen),0),5)as cantidad_salida_almacen";
                                                    sql_salidas += " from salidas_almacen sa, SALIDAS_ALMACEN_DETALLE sad";
                                                    sql_salidas += " where sa.cod_salida_almacen=sad.cod_salida_almacen and sad.cod_material=" + codMaterialKardex + "";
                                                    sql_salidas += " and sa.cod_estado_salida_almacen=1 and sa.estado_sistema=1";
                                                    sql_salidas += " and sa.cod_almacen=" + codAlmacen + "";

                                                    /*if (chk_lote.Checked = true) {
                                                    sql_salidas += " and cod_lote_produccion is not null and cod_lote_produccion <> ''";
                                                    }*/

                                                    sql_salidas += " and month(sa.fecha_salida_almacen)=" + mes + " and year(sa.fecha_salida_almacen)=" + anio + "";
                                                    System.out.println("sql_salidas--:" + sql_salidas);
                                                    st_salidas = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                                    rs_salidas = st_salidas.executeQuery(sql_salidas);

                                                    int CANTIDAD_SALIDA = 0;
                                                    while (rs_salidas.next()) {
                                                        suma_total_salidas = suma_total_salidas + rs_salidas.getInt("cantidad_salida_almacen");
                                                        CANTIDAD_SALIDA = rs_salidas.getInt("cantidad_salida_almacen");
                                                        System.out.println("CANTIDAD_SALIDA:" + CANTIDAD_SALIDA);
                                                        cantidad_restante_anterior = cantidad_restante_anterior - rs_salidas.getInt("cantidad_salida_almacen");
                                                    }
                    %>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=CANTIDAD_SALIDA%></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=cantidad_restante_anterior%></td>


                    <%




                                                    // verifica si hay observaciones

                                                    sql_ingresos = " SELECT top 1 isnull(obs_control_calidad,0)as obs_control_calidad ";
                                                    sql_ingresos += " FROM ingresos_almacen_detalle_estado iad,ingresos_almacen ia";
                                                    sql_ingresos += " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen";
                                                    sql_ingresos += " and ia.cod_estado_ingreso_almacen=1 and ia.estado_sistema=1";
                                                    sql_ingresos += " and iad.cod_material=" + codMaterialKardex + "";
                                                    sql_ingresos += " and ia.cod_almacen=" + codAlmacen + "";
                                                    sql_ingresos += " and month(ia.fecha_ingreso_almacen)=" + mes + " and year(ia.fecha_ingreso_almacen)=" + anio + " order by obs_control_calidad desc";
                                                    System.out.println("sql_ingresos:" + sql_ingresos);
                                                    st_ingresos = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                                    rs_ingresos = st_ingresos.executeQuery(sql_ingresos);
                                                    String ingresos_observados = "";
                                                    while (rs_ingresos.next()) {
                                                        if (rs_ingresos.getInt("obs_control_calidad") == 1) {
                                                            ingresos_observados = "OBSERVADO";
                                                        } else {
                                                            ingresos_observados = "APROBADO";
                                                        }
                                                    }
                                                    // fin verifica si hay observaciones

                                                    String sql_prov = " SELECT distinct p.nombre_proveedor from proveedores p, ingresos_almacen ia, ingresos_almacen_detalle iad ";
                                                    sql_prov += " where ia.cod_proveedor=p.cod_proveedor and ia.cod_estado_ingreso_almacen=1 and ia.estado_sistema=1";
                                                    sql_prov += " and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and iad.cod_material=" + codMaterialKardex + "";
                                                    sql_prov += " and ia.cod_almacen=" + codAlmacen + "";
                                                    sql_prov += " and month(ia.fecha_ingreso_almacen)=" + mes + " and year(ia.fecha_ingreso_almacen)=" + anio + "";
                                                    System.out.println("sql_prov:" + sql_prov);
                                                    Statement st_prov = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                                    ResultSet rs_prov = st_prov.executeQuery(sql_prov);
                                                    String proveedor = "";
                                                    while (rs_prov.next()) {
                                                        proveedor = proveedor + rs_prov.getString("nombre_proveedor") + ",";
                                                    }
                    %>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=proveedor%></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px"><%=ingresos_observados%></td>
                </tr>

                <%


                                    String sql_insert = "insert into kardex_item_movimiento(cod_reporte,cod_persona,TIPO,TIPO_INGRESO_SALIDA,CANTIDAD_INGRESO,CANTIDAD_SALIDA,SALDO,unidad_medida,PRODUCTO)";
                                    sql_insert += " values(" + cod_reporte + "," + codigoPersonal + ",";
                                    sql_insert += " " + mes + "," + anio + ",";
                                    sql_insert += " " + CANTIDAD_INGRESO + "," + CANTIDAD_SALIDA + "," + cantidad_restante_anterior + ",'" + proveedor + "','" + proveedor + "')";
                                    System.out.println("sql_insert:" + sql_insert);
                                    Statement st_insert = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                    int rs_insert = st_insert.executeUpdate(sql_insert);
                                    cod_reporte = cod_reporte + 1;

                                }
                %>

            </table>
            <BR>
            <BR>
            <BR>
            <div class="main">


                <h2 id="line">Consumo Promedio Mensual por Item</h2>

                <table id='myTable2'>
                    <caption></caption>
                    <thead>
                        <tr>
                            <th>Wilson</th>
                            <th>Ene</th>
                            <th>Feb</th>
                            <th>Mar</th>
                            <th>Abr</th>
                            <th>May</th>
                            <th>Jun</th>
                            <th>Jul</th>
                            <th>Ago</th>
                            <th>Sep</th>
                            <th>Oct</th>
                            <th>Nov</th>
                            <th>Dec</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                                String sql_reporte = "select DISTINCT k.TIPO_INGRESO_SALIDA from KARDEX_ITEM_MOVIMIENTO k where k.COD_PERSONA="+codigoPersonal+" ";
                                System.out.println("sql_reporte:" + sql_reporte);
                                Statement st_reporte = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_reporte = st_reporte.executeQuery(sql_reporte);
                                String meses = "ENERO,FEBRERO,MARZO,ABRIL,MAYO,JUNIO,JULIO,AGOSTO,SEPTIEMBRE,OCTUBRE,NOVIEMBRE,DICIEMBRE";
                                String mesesV[] = meses.split(",");

                                while (rs_reporte.next()) {
                                    int gestion = rs_reporte.getInt(1);
                        %>
                        <tr>
                            <th><%=gestion%></th>
                            <%
                            for (int i = 1; i < 13; i++) {
                                String sql_reporte1 = "select isnull(k.CANTIDAD_SALIDA,0),isnull(k.TIPO,0)  from KARDEX_ITEM_MOVIMIENTO k where k.COD_PERSONA="+codigoPersonal+"" +
                                        " and k.TIPO_INGRESO_SALIDA=" + gestion + " and k.tipo=" + i + " order by k.COD_REPORTE";
                                System.out.println("sql_reporte1:" + sql_reporte1);
                                Statement st_reporte1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_reporte1 = st_reporte1.executeQuery(sql_reporte1);
                                int cantidad = 0;
                                while (rs_reporte1.next()) {

                                    cantidad = rs_reporte1.getInt(1);
                                    int mesKardex = rs_reporte1.getInt(2);
                         

                                }
                                   %>
                            <td><%=cantidad%></td>
                            <%

                            }
                            %>
                        </tr>
                        <%
                                }
                        %>

                        <%--tr>
                            <th>2010</th>
                            <td>11125</td>
                            <td>185</td>
                            <td>327</td>
                            <td>359</td>
                            <td>376</td>
                            <td>398</td>
                            <td>0</td>
                            <td>0</td>
                            <td>0</td>
                            <td>0</td>
                            <td>0</td>
                            <td>0</td>
                        </tr>
                        <tr>
                            <th>2009</th>
                            <td>1167</td>
                            <td>1110</td>
                            <td>691</td>
                            <td>165</td>
                            <td>135</td>
                            <td>157</td>
                            <td>139</td>
                            <td>136</td>
                            <td>938</td>
                            <td>1120</td>
                            <td>55</td>
                            <td>55</td>
                        </tr>
                        <tr>
                            <th>2008</th>
                            <td>1000</td>
                            <td>1110</td>
                            <td>691</td>
                            <td>165</td>
                            <td>135</td>
                            <td>157</td>
                            <td>139</td>
                            <td>136</td>
                            <td>938</td>
                            <td>1120</td>
                            <td>550</td>
                            <td>550</td>
                        </tr--%>
                    </tbody>
                </table>



            </div>
            <%
                    String sql_uni = " SELECT abreviatura from unidades_medida u, materiales m ";
                    sql_uni += " where u.cod_unidad_medida=m.cod_unidad_medida and m.cod_material=" + codMaterialKardex + "";

                    double consumo_promedio = 0;
                    consumo_promedio = suma_total_salidas / (cod_reporte - 1);


                    String sql_insert = "UPDATE kardex_item_movimiento SET costo_ingreso=" + consumo_promedio + " where cod_persona=" + codigoPersonal + "";
                    System.out.println("sql_update:" + sql_insert);
                    Statement st_insert = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    int rs_insert = st_insert.executeUpdate(sql_insert);


                /********************************** e n d******************************************/
                /*******************************************************************************************************************/
                /*out.print("<tr>");
                out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("NRO_INGRESO_ALMACEN") == null ? "" : rs.getString("NRO_INGRESO_ALMACEN")) + "</td>");
                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + format.format(rs.getDate("FECHA_INGRESO_ALMACEN")) + " &nbsp; " + format1.format(rs.getTimestamp("FECHA_INGRESO_ALMACEN").getTime()) + "</td>");
                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + (rs.getString("NRO_ORDEN_COMPRA") == null ? "" : rs.getString("NRO_ORDEN_COMPRA")) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + (rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN") == null ? "" : rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + (rs.getString("NOMBRE_PROVEEDOR") == null ? "" : rs.getString("NOMBRE_PROVEEDOR")) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (rs.getString("NOMBRE_MATERIAL") == null ? "" : rs.getString("NOMBRE_MATERIAL")) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (rs.getString("NOMBRE_CAPITULO") == null ? "" : rs.getString("NOMBRE_CAPITULO")) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (rs.getString("NOMBRE_GRUPO") == null ? "" : rs.getString("NOMBRE_GRUPO")) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (rs.getString("ABREVIATURA") == null ? "" : rs.getString("ABREVIATURA")) + "</td>");
                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(rs.getDouble("CANT_TOTAL_INGRESO_FISICO"))) + "</td>");
                out.print("</tr>");
                costoTotal = costoTotal + rs.getDouble("CANT_TOTAL_INGRESO_FISICO");*/
                }

            %>

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
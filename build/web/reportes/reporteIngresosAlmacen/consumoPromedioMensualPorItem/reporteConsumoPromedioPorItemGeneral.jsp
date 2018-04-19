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
        <script type="text/javascript" src="jquery/jquery.min.js"></script>
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
    <body >
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
            formato.applyPattern("#,##0.00");

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
                        String consulta1="select  um.ABREVIATURA from MATERIALES m inner join UNIDADES_MEDIDA um on m.COD_UNIDAD_MEDIDA=um.COD_UNIDAD_MEDIDA and m.COD_MATERIAL='"+codMaterial+"'";
                        Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ResultSet res1=st1.executeQuery(consulta1);
                        String abreviatura="";
                        if(res1.next())
                        {
                            abreviatura=res1.getString("ABREVIATURA");
                        }


            %>

            <br>
            <table width="70%" border="0" align="center">
                <tr>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">
                        <img src="../../../img/cofar.png" alt="logo cofar" align="left" />
                    </td><td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px">
                        <table border="0" class="outputText1" >
                            <tbody>
                                <tr style="width:12px;">
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Filial</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=nombreFilial%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Almacen</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=nombreAlmacen%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Capitulo</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=nombreCapitulo%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Grupo</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=nombreGrupo%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Fecha Inicial</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>

          
            <%

                String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL from materiales m where m.COD_MATERIAL='"+codMaterial+"'";



                System.out.println("consulta 1 " + consulta);

                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                if (rs.next()) {
                    int codMaterialKardex = Integer.valueOf(codMaterial);
                    String nomMaterialKardex = rs.getString(2);
                    /*******************************************************************************************************************/
                    /********************************** begin ************************************************/
                    Object obj = request.getSession().getAttribute("ManagedAccesoSistema");
                    ManagedAccesoSistema var = (ManagedAccesoSistema) obj;
                    String codigoPersonal = var.getUsuarioModuloBean().getCodUsuarioGlobal();

                    String sql_delete = "delete from KARDEX_ITEM_MOVIMIENTO_BACO_BACO";
                    sql_delete += " where cod_persona=" + codigoPersonal + "";
                    System.out.println("sql_delete:" + sql_delete);
                    Statement st_delete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    int rs_delete = st_delete.executeUpdate(sql_delete);
                    double ingresos_total_anterior = 0;
                    double salidas_total_anterior = 0;
                    double cantidad_restante_anterior = 0;
                    double cantidad_restante_anterior_kardexmovimiento_global = 0;



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
                        ingresos_total_anterior = rs_ingresos.getDouble("ingresos_total_anterior");
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
                        salidas_total_anterior = rs_salidas.getDouble("salidas_total_anterior");
                    }

                    cantidad_restante_anterior = (ingresos_total_anterior) - (salidas_total_anterior);
                    cantidad_restante_anterior_kardexmovimiento_global = (cantidad_restante_anterior);


                    /***************************************************************/
                    String fecha_cambio = " select month(fecha)as mes,year(fecha)as anio,m.nombre_mes";
                    fecha_cambio += " from TIPOCAMBIOS_MONEDA tc, meses m";
                    fecha_cambio += " where month(fecha)=m.cod_mes and fecha>='" + fechaInicial + "' and fecha<='" + fechaFinal + "'";
                    fecha_cambio += " group by month(fecha),year(fecha),m.nombre_mes";
                    fecha_cambio += " order by year(fecha) asc, month(fecha) asc";
                    System.out.println("fecha_cambio:" + fecha_cambio);
                    Statement st_cambio = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_cambio = st_cambio.executeQuery(fecha_cambio);
                    double suma_total_ingresos = 0;
                    double suma_total_salidas = 0;


            %>
            <p align="center" class="outputText2"><B>ITEM</B> :<%=nomMaterialKardex%></p>
            <p align="center" class="outputText2"><B>Existencia a Fecha Inicio Reporte:&nbsp;</B><%=formato.format(cantidad_restante_anterior)+' '+abreviatura%></p>
            <table class="outputText2" align="CENTER" width="100%">
                <tr bgcolor="#cccccc" style="width:12px;">
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Año</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Mes</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Ingreso</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Salida</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Saldo</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Proveedor</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Estado</th>
                </tr>

                <%

                                int cod_reporte = 1;
                                int cont=0;
                                double sumaCantidadSalida=0;
                                double sumaCantidadIngreso=0;
                                
                                

                                while (rs_cambio.next()) {
                                    cont++;
                                    int mes = rs_cambio.getInt(1);
                                    int anio = rs_cambio.getInt(2);
                                    String nombre_mes = rs_cambio.getString(3);
                %>
                <tr style="width:12px;">
                    <td style="border-bottom:SOLID 1PX #CCCCCC;"><%=anio%></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;"><%=nombre_mes%></td>

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
                                                    double CANTIDAD_INGRESO = 0;
                                                    if (rs_ingresos.next()) {
                                                        suma_total_ingresos = suma_total_ingresos + rs_ingresos.getDouble("cantidad_ingresos_almacen");
                                                        cantidad_restante_anterior = cantidad_restante_anterior + rs_ingresos.getInt("cantidad_ingresos_almacen");
                                                        CANTIDAD_INGRESO = rs_ingresos.getDouble("cantidad_ingresos_almacen");
                                                    }
                                                   sumaCantidadIngreso+=CANTIDAD_INGRESO;
                    %>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;" align="right"><%=formato.format(CANTIDAD_INGRESO)%></td>

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

                                                    double CANTIDAD_SALIDA = 0;
                                                    while (rs_salidas.next()) {
                                                        suma_total_salidas = suma_total_salidas + rs_salidas.getDouble("cantidad_salida_almacen");
                                                        CANTIDAD_SALIDA = rs_salidas.getDouble("cantidad_salida_almacen");
                                                        System.out.println("CANTIDAD_SALIDA:" + CANTIDAD_SALIDA);
                                                        cantidad_restante_anterior = cantidad_restante_anterior - rs_salidas.getDouble("cantidad_salida_almacen");
                                                    }
                                                    sumaCantidadSalida+=CANTIDAD_SALIDA;
                    %>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;" align="right"><%=formato.format(CANTIDAD_SALIDA)%></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;" align="right"></td>


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
                    <td style="border-bottom:SOLID 1PX #CCCCCC;"><%=proveedor%></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;"><%=ingresos_observados%></td>
                </tr>


                <%


                                    String sql_insert = "insert into KARDEX_ITEM_MOVIMIENTO_BACO_BACO(cod_reporte,cod_persona,TIPO,TIPO_INGRESO_SALIDA,CANTIDAD_INGRESO,CANTIDAD_SALIDA,SALDO,unidad_medida,PRODUCTO)";
                                    sql_insert += " values(" + cod_reporte + "," + codigoPersonal + ",";
                                    sql_insert += " " + mes + "," + anio + ",";
                                    sql_insert += " " + CANTIDAD_INGRESO + "," + CANTIDAD_SALIDA + "," + cantidad_restante_anterior + ",'" + proveedor + "','" + proveedor + "')";
                                    System.out.println("sql_insert:" + sql_insert);
                                    Statement st_insert = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                    int rs_insert = st_insert.executeUpdate(sql_insert);
                                    cod_reporte = cod_reporte + 1;

                                }
                                String consulta2="select( ISNULL((SELECT sum(iad.cant_total_ingreso_fisico)as ingresos_total_anterior FROM ingresos_almacen_detalle iad,ingresos_almacen ia where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen=1 and ia.estado_sistema=1 and iad.cod_material='"+codMaterialKardex+"' and ia.cod_almacen= '"+codAlmacen+"'  and ia.fecha_ingreso_almacen<'"+fechaFinal+"' ),0)-"+
                                                 "ISNULL((select sum(sad.cantidad_salida_almacen)as salidas_total_anterior from salidas_almacen_detalle sad,salidas_almacen sa where sad.cod_salida_almacen=sa.cod_salida_almacen and sa.cod_estado_salida_almacen=1 and sa.estado_sistema=1 and sad.cod_material='"+codMaterialKardex+"' and sa.cod_almacen= '"+codAlmacen+"'  and sa.fecha_salida_almacen<='"+fechaFinal+"'),0)) as cant";
                                System.out.println("consulta saldo "+consulta2);
                                Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                ResultSet res2=st2.executeQuery(consulta2);
                                double cantSaldo=0d;
                                if(res2.next())
                                {
                                    cantSaldo=res2.getDouble("cant");
                                }
                                res2.close();
                                st2.close();

                %>
                <tr>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;"><b></b></td>
                     <td style="border-bottom:SOLID 1PX #CCCCCC;"><b></b></td>
                     <td style="border-bottom:SOLID 1PX #CCCCCC;" align="right"><b><%=formato.format(sumaCantidadIngreso)%></b></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;" align="right"><b><%=formato.format(sumaCantidadSalida)%></b></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;" align="right"><b><%=formato.format(cantSaldo)%></b></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;"><b></b></td>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;"><b></b></td>
                </tr>
            </table>
           
                        <%
                        

                                String sql_reporte = "select DISTINCT k.TIPO_INGRESO_SALIDA from KARDEX_ITEM_MOVIMIENTO_BACO_BACO k where k.COD_PERSONA="+codigoPersonal+" ";
                                System.out.println("sql_reporte:" + sql_reporte);
                                Statement st_reporte = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_reporte = st_reporte.executeQuery(sql_reporte);
                                String meses = "ENERO,FEBRERO,MARZO,ABRIL,MAYO,JUNIO,JULIO,AGOSTO,SEPTIEMBRE,OCTUBRE,NOVIEMBRE,DICIEMBRE";
                                String mesesV[] = meses.split(",");
                                String construccion="<script type='text/javascript'> " +
                                        " $(function () {var chart;$(document).ready(function() {"+
                                        " chart = new Highcharts.Chart({ chart: {"+
                                        " renderTo: 'container',"+
                                        " type: 'line',zoomType: 'xy'"+
                                        " },"+
                                        " title:{"+
                                        " text: 'Consumo Promedio Mensual:"+formato.format(sumaCantidadSalida/Double.valueOf(cont))+"',style: {"+
                                        " color: '#000000'"+
                                        " }"+
                                        " },"+
                                        " subtitle: {"+
                                        " text: 'Consumo Mensual en "+abreviatura+"',style: {"+
                                        " color: '#000000'"+
                                        " }"+
                                        " },"+
                                        " xAxis:{"+
                                        " categories: ['ENERO','FEBRERO','MARZO','ABRIL','MAYO','JUNIO','JULIO','AGOSTO','SEPTIEMBRE','OCTUBRE','NOVIEMBRE','DICIEMBRE'],style: {"+
                                        " color: '#000000'"+
                                        " }"+
                                        " },"+
                                        " yAxis:[{"+
                                        " labels: {"+
                                        " formatter: function() {"+
                                        " return this.value ;"+
                                        " },"+
                                        " style: {"+
                                        " color: '#000000'"+
                                        " }"+
                                        " },"+
                                        " title: {"+
                                        " text: 'Cantidad de Salida'," +
                                        " style: {"+
                                        " color: '#000000'"+
                                        " }"+
                                        " },opposite: false"+
                                        " }],"+
                                        " tooltip: {"+
                                        " enabled: false,"+
                                        " formatter: function() {"+
                                        " return '<b>'+ this.series.name +'</b><br/>'+"+
                                        " this.x +': '+ this.y +'°C';"+
                                        " }"+
                                        " },"+
                                        " plotOptions: {"+
                                        " line: {"+
                                        " dataLabels: {"+
                                        " enabled: true"+
                                        " },"+
                                        " enableMouseTracking: false"+
                                        " }"+
                                        " }," +
                                        "series: [";
                                 boolean primerValor=true;
                                boolean primera=true;
                                while (rs_reporte.next()) {
                                    int gestion = rs_reporte.getInt(1);
                                    if(primera)
                                        {
                                    construccion+="{name:'"+gestion+"',data:[";
                                    primera=false;
                                    }
                                    else
                                        {construccion+=",{name:'"+gestion+"',data:[";}
                                    primerValor=true;
                                    formato.applyPattern("###0.00");
                                    for (int i = 1; i < 13; i++) {
                                        String sql_reporte1 = "select isnull(k.CANTIDAD_SALIDA,0),isnull(k.TIPO,0)  from KARDEX_ITEM_MOVIMIENTO_BACO_BACO k where k.COD_PERSONA="+codigoPersonal+"" +
                                                " and k.TIPO_INGRESO_SALIDA=" + gestion + " and k.tipo=" + i + " order by k.COD_REPORTE";
                                        System.out.println("sql_reporte1:" + sql_reporte1);
                                        Statement st_reporte1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                        ResultSet rs_reporte1 = st_reporte1.executeQuery(sql_reporte1);
                                        double cantidad = 0;
                                        while (rs_reporte1.next()) {

                                            cantidad = rs_reporte1.getDouble(1);
                                            int mesKardex = rs_reporte1.getInt(2);


                                        }
                                        if(primerValor)
                                            {
                                       construccion+=formato.format(cantidad);primerValor=false;
                                       }
                                        else
                                            {
                                            construccion+=","+formato.format(cantidad);}
                                   }

                                    construccion+="]}";
                                    
                                }
                                
                                construccion+=" ]});});});</script>";
                                
                                out.println(construccion);
                        %>
                        
                    <script src="jquery/highcharts.js"></script>
                    <script src="jquery/exporting.js"></script>
                <div id="container" style=" width:680px;height:360px; margin: 0 auto"></div>


                       


            
            <%

                }

            %>

            <%



        } catch (Exception e) {
            e.printStackTrace();
        }

            %>

        </form>
    </body>
</html>
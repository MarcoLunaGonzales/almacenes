package reportes.reporteSalidasAlmacen.reporteResumenGeneralLotes;


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
    </head>
    <body>
        <form>
            <h4 align="center">Reporte Resúmen General de Lotes</h4>
            <%!    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }
            %>


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
            System.out.println("nombreMaterialP:" + nombreMaterialP);

            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");


            String codProductoP = request.getParameter("codProductoP") == null ? "'0'" : request.getParameter("codProductoP");
            String nombreProductoP = request.getParameter("nombreProductoP") == null ? "''" : request.getParameter("nombreProductoP");
            String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");
            String numLoteP = request.getParameter("numLoteP") == null ? "''" : request.getParameter("numLoteP");

            //System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " "+ codCapitulo +" " + codAlmacen + " " + codStock + " " +
            //"" + codFilial + " " +  nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo );
            double costoTotal = 0;
            double costoTotalCosto = 0;
            double costoTotalDevolucion = 0;
            double totalSalidas = 0;
            double totalDevolucion = 0;



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
                        <img src="../../../img/cofar.png" alt="logo cofar" align="left" />
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
                                    <td><b>Producto</b></td>
                                    <td><%=nombreProductoP%></td>
                                </tr>

                                <tr>
                                    <td><b>Fecha Inicial</b></td>
                                    <td><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>

            <BR>
            <BR>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="2" ><b>&nbsp;</b></td>
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="2" ><b>&nbsp;MATERIA PRIMA&nbsp;</b></td>
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="2" ><b>&nbsp;EMPAQUE PRIMARIO&nbsp;</b></td>
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="2" ><b>&nbsp;EMPAQUE SECUNDARIO&nbsp;</b></td>
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="2" ><b>&nbsp;EMPAQUE SECUNDARIO(MM)&nbsp;</b></td>
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="2" ><b>&nbsp;VARIOS&nbsp;</b></td>
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>&nbsp;TOTAL&nbsp;</b></td>


                </tr>

                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Lote</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Producto</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Devoluciones</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Devoluciones</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Devoluciones</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Devoluciones</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Devoluciones</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Devoluciones</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Neto</b></td>
                </tr>


                <%
                
                double total_salidas_MP = 0;
                double total_dev_MP = 0;
                double total_salidas_EP = 0;
                double total_dev_EP = 0;
                double total_salidas_ES = 0;
                double total_dev_ES = 0;
                double total_salidas_ESMM = 0;
                double total_dev_ESMM = 0;
                double total_salidas_VARIOS = 0;
                double total_dev_VARIOS = 0;
                double total_salidas_TOTAL = 0;
                double total_dev_TOTAL = 0;

                String sql_lote = "select  distinct  sa.COD_LOTE_PRODUCCION,cp.COD_COMPPROD,cp.nombre_prod_semiterminado";
                sql_lote += " from salidas_almacen_detalle sad";
                sql_lote += " inner join SALIDAS_ALMACEN sa on sa.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN ";
                sql_lote += " inner join MATERIALES m on m.COD_MATERIAL = sad.COD_MATERIAL";
                sql_lote += " inner join ALMACENES a on a.COD_ALMACEN = sa.COD_ALMACEN";
                sql_lote += " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = sad.COD_UNIDAD_MEDIDA";
                sql_lote += " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO";
                sql_lote += " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO";
                sql_lote += " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = sa.COD_AREA_EMPRESA";
                sql_lote += " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL";
                sql_lote += " inner join COMPONENTES_PROD cp on cp.COD_COMPPROD = sa.COD_PROD";

                sql_lote += " WHERE  sa.COD_ESTADO_SALIDA_ALMACEN<>2 ";
                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    sql_lote = sql_lote + " and sa.FECHA_SALIDA_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }
                if (!numLoteP.equals("")) {
                    sql_lote = sql_lote + " and sa.COD_LOTE_PRODUCCION like '" + numLoteP + "%' ";
                }
                if (!codProductoP.equals("") && !codProductoP.equals("0")) {
                    sql_lote = sql_lote + " and sa.COD_PROD = " + codProductoP + " ";
                }
                if (!codFilial.equals("") && !codFilial.equals("0")) {
                    sql_lote = sql_lote + " and f.cod_filial = '" + codFilial + "' ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    sql_lote = sql_lote + " and sa.COD_ALMACEN = '" + codAlmacen + "' ";
                }

                //salida += " ORDER BY sa.FECHA_SALIDA_ALMACEN desc,sa.NRO_SALIDA_ALMACEN,m.NOMBRE_MATERIAL,sa.COD_LOTE_PRODUCCION   ";

                sql_lote += " union";
                sql_lote += " select  distinct sa.COD_LOTE_PRODUCCION, cp.COD_COMPPROD, cp.nombre_prod_semiterminado";
                sql_lote += " from devoluciones d, salidas_almacen sa, ingresos_almacen ia, ingresos_almacen_detalle iad,COMPONENTES_PROD cp";
                sql_lote += " where d.estado_sistema = 1 and d.cod_salida_almacen = sa.cod_salida_almacen and d.cod_devolucion = ia.cod_devolucion and";
                sql_lote += " ia.cod_almacen = d.cod_almacen and iad.cod_ingreso_almacen = ia.cod_ingreso_almacen ";

                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    sql_lote = sql_lote + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    sql_lote = sql_lote + " and sa.cod_almacen='" + codAlmacen + "' and d.cod_almacen='" + codAlmacen + "' ";
                }

                sql_lote += " and ia.cod_estado_ingreso_almacen=1 and d.cod_estado_devolucion=1 and cp.COD_COMPPROD=sa.COD_PROD";

                sql_lote += " ORDER BY sa.COD_LOTE_PRODUCCION   ";

                System.out.println("sql_lote: " + sql_lote);

                con = Util.openConnection(con);
                Statement st_lote = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_lote = st_lote.executeQuery(sql_lote);
                String codLoteProd = "";
                int codProdSemiterminado = 0;
                String nombreProdSemiterminado = "";
                String capitulos = "2,3,4,8,1000";
                int capituloInt = 0;
                while (rs_lote.next()) {
                    codLoteProd = rs_lote.getString(1);
                    codProdSemiterminado = rs_lote.getInt(2);
                    nombreProdSemiterminado = rs_lote.getString(3);
                    String capVector[] = capitulos.split(",");
                    out.print("<tr>");
                    out.print("<td HEIGHT='25PX' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + codLoteProd + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + nombreProdSemiterminado + "</td>");
                    
                    double totalCostoSalidas = 0;
                double totalCostoDevoluciones = 0;
                    for (int j = 0; j < capVector.length; j++) {

                        capituloInt = Integer.parseInt(capVector[j]);
                        System.out.println("capituloInt:" + capituloInt);

                        String salida = "select  isnull(sum(sadi.costo_salida_actualizado * sadi.cantidad), 0) as total_salida";
                        salida += " from salidas_almacen_detalle sad";
                        salida += " inner join SALIDAS_ALMACEN sa on sa.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN";
                        salida += " inner join MATERIALES m on m.COD_MATERIAL = sad.COD_MATERIAL";
                        salida += " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL=m.COD_MATERIAL";
                        salida += " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO";
                        salida += " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO";

                        salida += " WHERE  sa.COD_ESTADO_SALIDA_ALMACEN<>2 ";
                        if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                            salida = salida + " and sa.FECHA_SALIDA_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                        }
                        //salida = salida + " and sa.FECHA_SALIDA_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                        salida = salida + " and sa.COD_LOTE_PRODUCCION ='" + codLoteProd + "' ";
                        salida = salida + " and sa.COD_PROD = " + codProdSemiterminado + " ";
                        if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                            salida = salida + " and sa.COD_ALMACEN = '" + codAlmacen + "' ";
                        }

                        //salida += " ORDER BY sa.FECHA_SALIDA_ALMACEN desc,sa.NRO_SALIDA_ALMACEN,m.NOMBRE_MATERIAL,sa.COD_LOTE_PRODUCCION   ";
                        if (capituloInt != 1000) {
                            salida += " and c.COD_CAPITULO=" + capituloInt + "";
                        } else {
                            salida += " and c.COD_CAPITULO>4 AND c.cod_capitulo <> 8";
                        }
                        System.out.println("salida: " + salida);

                        //con = Util.openConnection(con);
                        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs = st.executeQuery(salida);
                        costoTotal = 0;
                        costoTotalCosto = 0;
                        costoTotalDevolucion = 0;
                        int c = 0;
                        int nroSalidaAux = 0;
                        while (rs.next()) {

                            String devoluciones = " select ";
                            devoluciones += " sum(isnull(iad.COSTO_UNITARIO_ACTUALIZADO*iad.CANT_TOTAL_INGRESO_FISICO,0))as total_salida";
                            //devoluciones += " (select top 1 isnull(ia1.COD_ORDEN_COMPRA,0)" ;
                            //devoluciones += " from salidas_almacen_detalle_ingreso sadi,INGRESOS_ALMACEN ia1,INGRESOS_ALMACEN_DETALLE_ESTADO iade";
                            //devoluciones += " where iad.cod_material=sadi.cod_material and sa.cod_salida_almacen=sadi.cod_salida_almacen and sadi.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN";
                            //devoluciones += " and sadi.COD_MATERIAL=iade.COD_MATERIAL and sadi.ETIQUETA=iade.ETIQUETA and iade.COD_INGRESO_ALMACEN=ia1.COD_INGRESO_ALMACEN order by ia1.cod_orden_compra desc)as cod_oc";
                            devoluciones += " from devoluciones d,salidas_almacen sa,ingresos_almacen ia,ingresos_almacen_detalle iad,MATERIALES m" +
                                    "  where d.estado_sistema=1 and d.cod_salida_almacen=sa.cod_salida_almacen and d.cod_devolucion=ia.cod_devolucion " +
                                    "  and ia.cod_almacen=d.cod_almacen ";
                            devoluciones += " and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen ";


                            if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                                devoluciones = devoluciones + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                            }

                            devoluciones = devoluciones + " and sa.COD_LOTE_PRODUCCION = '" + codLoteProd + "' ";
                            devoluciones = devoluciones + " and sa.COD_PROD = " + codProdSemiterminado + " ";

                            if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                                devoluciones = devoluciones + " and sa.cod_almacen='" + codAlmacen + "' and d.cod_almacen='" + codAlmacen + "' ";
                            }
                            if (capituloInt != 1000) {
                                //salida += " and c.COD_CAPITULO=" + capituloInt + "";
                                devoluciones += " and m.COD_MATERIAL=iad.COD_MATERIAL and m.COD_GRUPO in ( select cod_grupo from grupos where cod_capitulo = " + capituloInt + ")";
                            } else {
                                devoluciones += " and m.COD_MATERIAL=iad.COD_MATERIAL and m.COD_GRUPO in ( select cod_grupo from grupos where cod_capitulo > " + capituloInt + " AND cod_capitulo <> 8) ";
                                //salida += " and c.COD_CAPITULO>4 AND c.cod_capitulo <> 8";
                            }
                            devoluciones += " and m.COD_MATERIAL=iad.COD_MATERIAL and m.COD_GRUPO in ( select cod_grupo from grupos where cod_capitulo = " + capituloInt + ")";

                            devoluciones += " and ia.cod_estado_ingreso_almacen=1 and d.cod_estado_devolucion=1 ";

                            System.out.println("devoluciones: " + devoluciones);
                            Statement st_devoluciones = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet rs_devoluciones = st_devoluciones.executeQuery(devoluciones);
                            double cantDev = 0;
                            double costoUnitDev = 0;
                            double costoTotalDev = 0;
                            while (rs_devoluciones.next()) {

                                System.out.println("rs_devoluciones.getString(3)" + rs_devoluciones.getDouble("total_salida"));
                                costoTotalDev = rs_devoluciones.getDouble("total_salida");

                            }
                            totalCostoSalidas += rs.getDouble("total_salida");
                            totalCostoDevoluciones += costoTotalDev;
                            if (capituloInt == 2) {
                                total_salidas_MP += rs.getDouble("total_salida");
                                total_dev_MP += costoTotalDev;
                            }
                            if (capituloInt == 3) {
                                total_salidas_EP += rs.getDouble("total_salida");
                                total_dev_EP += costoTotalDev;
                            }
                            if (capituloInt == 4) {
                                total_salidas_ES += rs.getDouble("total_salida");
                                total_dev_ES += costoTotalDev;
                            }
                            if (capituloInt == 8) {
                                total_salidas_ESMM += rs.getDouble("total_salida");
                                total_dev_ESMM += costoTotalDev;
                            }
                            if (capituloInt == 1000) {
                                total_salidas_VARIOS += rs.getDouble("total_salida");
                                total_dev_VARIOS += costoTotalDev;
                            }
                            out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(rs.getDouble("total_salida"))) + "</td>");
                            out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(costoTotalDev)) + "</td>");

                        }

                    }
                    out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(totalCostoSalidas)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(totalCostoDevoluciones)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(totalCostoSalidas - totalCostoDevoluciones)) + "</td>");

                    out.print("</tr>");


                }
                out.print("<tr BGCOLOR='#C2C2C2'>");
                out.print("<td HEIGHT='25PX' colspan='2' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >&nbsp;</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_salidas_MP) + "</td>");
                out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;' class='border'   >" + formato.format(total_dev_MP) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_salidas_EP) + "</td>");
                out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_dev_EP) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_salidas_ES) + "</td>");
                out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_dev_ES) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_salidas_ESMM) + "</td>");
                out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_dev_ESMM) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_salidas_VARIOS) + "</td>");
                out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_dev_VARIOS) + "</td>");
                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_salidas_MP+total_salidas_EP+total_salidas_ES+total_salidas_ESMM+total_salidas_VARIOS) + "</td>");
                out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_dev_MP+total_dev_EP+total_dev_ES+total_dev_ESMM+total_dev_VARIOS) + "</td>");
                out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format((total_salidas_MP+total_salidas_EP+total_salidas_ES+total_salidas_ESMM+total_salidas_VARIOS)-(total_dev_MP+total_dev_EP+total_dev_ES+total_dev_ESMM+total_dev_VARIOS)) + "</td>");
                out.print("</tr>");




                %>
            </table>
            <td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-left:5PX;font-family:monospace;font-size:x-large' >


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
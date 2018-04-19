
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
        <script src="../../../js/general.js"></script>
    </head>
    <body>
        <form>
            <h4 align="center">RESUMEN DE SALIDAS DE ALMACEN POR CUENTA (HISTÓRICO)</h4>

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

            //codMaterial = codMaterial.substring(1);
            String codTipoSalidaArray = request.getParameter("codTipoSalidaArray") == null ? "''" : request.getParameter("codTipoSalidaArray");
            String nomTipoSalidaArray = request.getParameter("nomTipoSalidaArray") == null ? "''" : request.getParameter("nomTipoSalidaArray");

            String codAreaEmpresa = request.getParameter("codAreaEmpresa") == null ? "''" : request.getParameter("codAreaEmpresa");

            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");

            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");


            System.out.println("parametros" + fechaInicial + " " + fechaFinal + "  " + codAlmacen + "  " +
                    "" + codFilial + " " + nombreFilial + "  " + nombreAlmacen + " ");
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
                                    <td><b>Tipo Salida</b></td>
                                    <td><%=nomTipoSalidaArray%></td>
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
                    <td HEIGHT='30PX' class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#F2F2F2" colspan="2" align="center" >&nbsp;</td>
                    <tH HEIGHT='30PX' class="border" style="COLOR:silver;border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#333333" colspan="3" align="center" >MATERIALES CON LOTE</tH>
                    <tH HEIGHT='30PX' class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#D2D2D2" colspan="3" align="center" >MATERIALES SIN LOTE</tH>
                    <td HEIGHT='30PX' class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#F2F2F2" colspan="1" align="center" >TOTALES</td>
                    
                </tr>
                <tr class="">
                    <td HEIGHT='30PX' class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#f2f2f2"  align="center" ><b>Cuenta</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#F2F2F2"  align="center" ><b>Sección</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;COLOR:silver;" bgcolor="#333333"  align="center"><b>Imp. Total</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;COLOR:silver;" class="border"  bgcolor="#333333"  align="center"><b>Devolución</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;COLOR:silver;" bgcolor="#333333"  align="center"><b>Total Neto</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#D2D2D2"  align="center"><b>Imp. Total</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" class="border"  bgcolor="#D2D2D2"  align="center"><b>Devolución</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#D2D2D2"  align="center"><b>Total Neto</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#f2f2f2"  align="center"><b>TOTAL</b></td>

                </tr>

                <%




                String consulta = "  SELECT cu.cod_cuenta,cu.NRO_CUENTA,cu.NOMBRE_CUENTA,";
                consulta += " (SELECT SUM(SADI.CANTIDAD*SADI.COSTO_SALIDA_ACTUALIZADO)";
                consulta += " FROM SALIDAS_ALMACEN_DETALLE_INGRESO SADI,SALIDAS_ALMACEN SA";
                consulta += " WHERE  SA.COD_ESTADO_SALIDA_ALMACEN=1 AND SADI.COD_SALIDA_ALMACEN=SA.COD_SALIDA_ALMACEN AND SA.ESTADO_SISTEMA=1";
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    consulta += " AND SA.COD_ALMACEN=" + codAlmacen + " ";
                }
                if (!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")) {
                    consulta += " AND SA.cod_area_empresa=" + codAreaEmpresa + " ";
                }
                if (!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")) {
                    consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN (" + codTipoSalidaArray + ") ";
                }

                consulta += " AND SA.COD_LOTE_PRODUCCION<>'' AND SA.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA";
                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    consulta += " AND SA.FECHA_SALIDA_ALMACEN>='" + fechaInicial + "' AND SA.FECHA_SALIDA_ALMACEN<='" + fechaFinal + " 23:59:59' ";
                }
                consulta += " ) AS IMP_TOTAL_CL,";
                //consulta += "  AND SA.FECHA_SALIDA_ALMACEN>='11/1/2012' AND SA.FECHA_SALIDA_ALMACEN<='11/30/2012 23:59:59' )AS IMP_TOTAL_CL,";

                consulta += " (SELECT SUM(DDE.CANTIDAD_DEVUELTA*IAD.COSTO_UNITARIO_ACTUALIZADO)";
                consulta += " FROM DEVOLUCIONES D, DEVOLUCIONES_DETALLE DD,DEVOLUCIONES_DETALLE_ETIQUETAS DDE,INGRESOS_ALMACEN IA,INGRESOS_ALMACEN_DETALLE IAD,";
                consulta += " SALIDAS_ALMACEN SA, TIPOS_SALIDAS_ALMACEN TS";
                consulta += " WHERE SA.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA AND SA.COD_ESTADO_SALIDA_ALMACEN=1 AND SA.ESTADO_SISTEMA=1";
                consulta += " AND DD.COD_MATERIAL=IAD.COD_MATERIAL AND D.ESTADO_SISTEMA = 1 AND IA.COD_ALMACEN = D.COD_ALMACEN ";
                if (!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")) {
                    consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN (" + codTipoSalidaArray + ") ";
                }
                if (!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")) {
                    consulta += " AND SA.cod_area_empresa=" + codAreaEmpresa + " ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    consulta += " AND SA.COD_ALMACEN=" + codAlmacen + " AND D.COD_ALMACEN=" + codAlmacen + "  AND IA.COD_ALMACEN=" + codAlmacen + "";
                }

                consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN=TS.COD_TIPO_SALIDA_ALMACEN AND SA.COD_LOTE_PRODUCCION<>''";
                consulta += " AND SA.COD_SALIDA_ALMACEN=D.COD_SALIDA_ALMACEN AND D.COD_DEVOLUCION=IA.COD_DEVOLUCION AND D.COD_ESTADO_DEVOLUCION=1";
                consulta += " AND IA.COD_ESTADO_INGRESO_ALMACEN=1 AND D.COD_DEVOLUCION=DD.COD_DEVOLUCION AND DD.COD_DEVOLUCION=DDE.COD_DEVOLUCION";
                consulta += " AND DD.COD_MATERIAL=DDE.COD_MATERIAL AND IA.COD_INGRESO_ALMACEN=IAD.COD_INGRESO_ALMACEN";

                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    consulta += " AND IA.FECHA_INGRESO_ALMACEN>='" + fechaInicial + "' AND IA.FECHA_INGRESO_ALMACEN<='" + fechaFinal + " 23:59:59' ";
                }
 
                consulta += " ) AS DEVOLUCIONES_CL," +
                            "(select SUM(iad.CANT_TOTAL_INGRESO_FISICO * IAD.COSTO_UNITARIO_ACTUALIZADO)" +
                                " from ingresos_almacen ia" +
                                " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN" +
                                " inner join devoluciones d on d.COD_DEVOLUCION = ia.COD_DEVOLUCION" +
                                " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = d.COD_SALIDA_ALMACEN"
                                + " and s.COD_ALMACEN = d.COD_ALMACEN and s.COD_AREA_EMPRESA = ae.COD_AREA_EMPRESA" +
                                " where ia.FECHA_INGRESO_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'" +
                                " and ia.COD_ESTADO_INGRESO_ALMACEN = 1"
                        + " and d.COD_ESTADO_DEVOLUCION = 1"
                        + " and s.COD_LOTE_PRODUCCION <>''"
                        + " and s.cod_estado_salida_almacen=1"
                        + " and s.estado_sistema=1 "
                        + " and d.estado_sistema=1 "
                        ;
                        if(!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")){
                            consulta += " AND S.COD_TIPO_SALIDA_ALMACEN IN ("+codTipoSalidaArray+") ";
                        }
                        if(!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")){
                            consulta += " AND S.cod_area_empresa="+codAreaEmpresa+" ";
                        }
                        if(!codAlmacen.equals("") && !codAlmacen.equals("0")){
                            consulta += " AND S.COD_ALMACEN="+codAlmacen+" AND D.COD_ALMACEN="+codAlmacen+"  AND IA.COD_ALMACEN="+codAlmacen+"";
                        }
                        consulta += ") as DEVOLUCIONES_CL_1,";
                consulta += " (SELECT SUM(SADI.CANTIDAD*SADI.COSTO_SALIDA_ACTUALIZADO)";
                consulta += " FROM SALIDAS_ALMACEN_DETALLE_INGRESO SADI,SALIDAS_ALMACEN SA, TIPOS_SALIDAS_ALMACEN TS";
                consulta += " WHERE  SA.COD_ESTADO_SALIDA_ALMACEN=1 AND SADI.COD_SALIDA_ALMACEN=SA.COD_SALIDA_ALMACEN AND SA.ESTADO_SISTEMA=1";
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    consulta += " AND SA.COD_ALMACEN=" + codAlmacen + " ";
                }
                if (!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")) {
                    consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN (" + codTipoSalidaArray + ") ";
                }
                if (!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")) {
                    consulta += " AND SA.cod_area_empresa=" + codAreaEmpresa + " ";
                }

                consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN=TS.COD_TIPO_SALIDA_ALMACEN AND (SA.COD_LOTE_PRODUCCION='' OR SA.COD_LOTE_PRODUCCION IS NULL) AND SA.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA ";
                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    consulta += " AND SA.FECHA_SALIDA_ALMACEN>='" + fechaInicial + "' AND SA.FECHA_SALIDA_ALMACEN<='" + fechaFinal + " 23:59:59' ";
                }
                consulta += " )AS IMP_TOTAL_SL,";
                //consulta += " AND SA.FECHA_SALIDA_ALMACEN>='11/1/2012' AND SA.FECHA_SALIDA_ALMACEN<='11/30/2012 23:59:59')AS IMP_TOTAL_SL,";
                consulta += " (SELECT SUM(DDE.CANTIDAD_DEVUELTA*IAD.COSTO_UNITARIO_ACTUALIZADO)";
                consulta += " FROM DEVOLUCIONES D, DEVOLUCIONES_DETALLE DD,DEVOLUCIONES_DETALLE_ETIQUETAS DDE,INGRESOS_ALMACEN IA,INGRESOS_ALMACEN_DETALLE IAD,";
                consulta += " SALIDAS_ALMACEN SA, TIPOS_SALIDAS_ALMACEN TS";
                consulta += " WHERE SA.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA AND SA.COD_ESTADO_SALIDA_ALMACEN=1 AND SA.ESTADO_SISTEMA=1";
                consulta += " AND DD.COD_MATERIAL=IAD.COD_MATERIAL AND D.ESTADO_SISTEMA = 1 AND IA.COD_ALMACEN = D.COD_ALMACEN ";
                if (!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")) {
                    consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN (" + codTipoSalidaArray + ") ";
                }
                if (!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")) {
                    consulta += " AND SA.cod_area_empresa=" + codAreaEmpresa + " ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    consulta += " AND SA.COD_ALMACEN=" + codAlmacen + " AND D.COD_ALMACEN=" + codAlmacen + "  AND IA.COD_ALMACEN=" + codAlmacen + "";
                }

                consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN=TS.COD_TIPO_SALIDA_ALMACEN AND (SA.COD_LOTE_PRODUCCION='' OR SA.COD_LOTE_PRODUCCION IS NULL)";
                consulta += " AND SA.COD_SALIDA_ALMACEN=D.COD_SALIDA_ALMACEN AND D.COD_DEVOLUCION=IA.COD_DEVOLUCION AND D.COD_ESTADO_DEVOLUCION=1";
                consulta += " AND IA.COD_ESTADO_INGRESO_ALMACEN=1 AND D.COD_DEVOLUCION=DD.COD_DEVOLUCION AND DD.COD_DEVOLUCION=DDE.COD_DEVOLUCION";
                consulta += " AND DD.COD_MATERIAL=DDE.COD_MATERIAL AND IA.COD_INGRESO_ALMACEN=IAD.COD_INGRESO_ALMACEN";
                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    consulta += " AND IA.FECHA_INGRESO_ALMACEN>='" + fechaInicial + "' AND IA.FECHA_INGRESO_ALMACEN<='" + fechaFinal + " 23:59:59' ";
                }

                consulta += " )AS DEVOLUCIONES_SL";
                //consulta += " AND IA.FECHA_INGRESO_ALMACEN>='11/1/2012' AND IA.FECHA_INGRESO_ALMACEN<='11/30/2012 23:59:59' )AS DEVOLUCIONES_SL";

                consulta += " FROM AREAS_EMPRESA_HISTORICO AE,CUENTAS cu WHERE cu.COD_CUENTA=ae.COD_CUENTA and  AE.COD_ESTADO_REGISTRO=1";

                consulta += " and cu.COD_CUENTA>0";
                consulta += " ORDER BY cu.NOMBRE_CUENTA";



                System.out.println("consulta 1 " + consulta);

                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                double imp_cl = 0;
                double imp_sl = 0;
                double dev_cl = 0;
                double dev_sl = 0;
                double total_imp_cl = 0;
                double total_imp_sl = 0;
                double total_dev_cl = 0;
                double total_dev_sl = 0;
                int cod_cuenta = 0;
                int cod_cuenta_ant = 0;
                int sw = 0;
                while (rs.next()) {
                    cod_cuenta = rs.getInt(1);
                    if (cod_cuenta != cod_cuenta_ant && cod_cuenta != 0) {
                        if (cod_cuenta_ant != 0) {
                            out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'    >" + formato.format(imp_cl) + "</td>");
                            out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + formato.format(dev_cl) + "</td>");
                            out.print("<tH  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + (formato.format(imp_cl - dev_cl)) + "</tH>");

                            out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + formato.format(imp_sl) + "</td>");
                            out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + formato.format(dev_sl) + "</td>");
                            out.print("<tH  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + (formato.format(imp_sl - dev_sl)) + "</tH>");
                            out.print("<tH  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" +(formato.format(imp_cl - dev_cl +(imp_sl - dev_sl))) + "</tH>");

                            out.print("</tr>");
                            imp_cl = 0;
                            imp_sl = 0;
                            dev_cl = 0;
                            dev_sl = 0;
                            sw = 0;

                        }

                        out.print("<tr>");
                        //out.print("<td HEIGHT='30PX' class='border' align='left'  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-left:5PX;' >&nbsp;</td>");
                        out.print("<th HEIGHT='30PX' class='border' align='left'  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-left:5PX;color:fuchsia;' >" + (rs.getString("NRO_CUENTA") == null ? "" : rs.getString("NRO_CUENTA")) + "</th>");
                        out.print("<th HEIGHT='30PX' class='border' align='left'  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-left:5PX;' >" + (rs.getString("NOMBRE_CUENTA") == null ? "" : rs.getString("NOMBRE_CUENTA")) + "</th>");


                        if (rs.getString("IMP_TOTAL_CL") != null) {
                            imp_cl += rs.getDouble("IMP_TOTAL_CL");
                            total_imp_cl += rs.getDouble("IMP_TOTAL_CL");
                        }
                        if (rs.getString("IMP_TOTAL_SL") != null) {
                            imp_sl += rs.getDouble("IMP_TOTAL_SL");
                            total_imp_sl += rs.getDouble("IMP_TOTAL_SL");
                        }
                        if (rs.getString("DEVOLUCIONES_CL") != null) {
                            dev_cl += rs.getDouble("DEVOLUCIONES_CL");
                            total_dev_cl += rs.getDouble("DEVOLUCIONES_CL");
                        }
                        if (rs.getString("DEVOLUCIONES_SL") != null) {
                            dev_sl += rs.getDouble("DEVOLUCIONES_SL");
                            total_dev_sl += rs.getDouble("DEVOLUCIONES_SL");
                        }
                    //out.print("<td class='border' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;;padding-left:5PX;'    >"+format.format( rs.getDate("FECHA_INGRESO_ALMACEN")) +" &nbsp; "+ format1.format( rs.getTimestamp("FECHA_INGRESO_ALMACEN").getTime()) +"</td>");

                    } else {
                        if (rs.getString("IMP_TOTAL_CL") != null) {
                            imp_cl += rs.getDouble("IMP_TOTAL_CL");
                            total_imp_cl += rs.getDouble("IMP_TOTAL_CL");
                        }
                        if (rs.getString("IMP_TOTAL_SL") != null) {
                            imp_sl += rs.getDouble("IMP_TOTAL_SL");
                            total_imp_sl += rs.getDouble("IMP_TOTAL_SL");
                        }
                        if (rs.getString("DEVOLUCIONES_CL") != null) {
                            dev_cl += rs.getDouble("DEVOLUCIONES_CL");
                            total_dev_cl += rs.getDouble("DEVOLUCIONES_CL");
                        }
                        if (rs.getString("DEVOLUCIONES_SL") != null) {
                            dev_sl += rs.getDouble("DEVOLUCIONES_SL");
                            total_dev_sl += rs.getDouble("DEVOLUCIONES_SL");
                        }
                    }

                    cod_cuenta_ant = cod_cuenta;

                }
                out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'    >" + formato.format(imp_cl) + "</td>");
                out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + formato.format(dev_cl) + "</td>");
                out.print("<tH  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + (formato.format(imp_cl - dev_cl)) + "</tH>");

                out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + formato.format(imp_sl) + "</td>");
                out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + formato.format(dev_sl) + "</td>");
                out.print("<tH  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" + (formato.format(imp_sl - dev_sl)) + "</tH>");
                out.print("<tH  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >" +(formato.format(imp_cl - dev_cl +(imp_sl - dev_sl))) + "</tH>");

                out.print("</tr>");

                out.print("<tr>");
                out.print("<th HEIGHT='30PX' ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;;padding:5PX;' colspan = '2'  >TOTALES</th>");

                out.print("<th ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;color:silver' bgcolor='#333333' >"+formato.format(total_imp_cl)+"</th>");
                out.print("<th ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;color:silver' bgcolor='#333333' >"+formato.format(total_dev_cl)+"</th>");
                out.print("<th ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;color:silver' bgcolor='#333333' >"+formato.format(total_imp_cl-total_dev_cl)+"</th>");
                out.print("<th ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#cdcdcd' >"+formato.format(total_imp_sl)+"</th>");
                out.print("<th ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#cdcdcd' >"+formato.format(total_dev_sl)+"</th>");
                out.print("<th ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#cdcdcd' >"+formato.format(total_imp_sl-total_dev_sl)+"</th>");
                out.print("<th ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;'  >"+formato.format((total_imp_cl-total_dev_cl)+(total_imp_sl-total_dev_sl))+"</th>");
                out.print("</tr>");





                %>
            </table>
            <td class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;;padding-left:5PX;' >
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
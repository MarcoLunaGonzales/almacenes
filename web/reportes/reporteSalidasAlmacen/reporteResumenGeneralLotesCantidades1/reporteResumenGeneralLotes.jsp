
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

            //con = Util.openConnection(con);


            String fechaInicial ="";
            if(request.getParameter("fechaIni").equals("0")){
                 fechaInicial="";
            }else{
                fechaInicial =request.getParameter("fechaIni");
            }
            String fechaFinal  = "";
            if(request.getParameter("fechaFin").equals("0")){
                 fechaFinal="";
            }else{
                fechaFinal =request.getParameter("fechaFin");
            }
            System.out.println("fec11:"+fechaInicial);
            System.out.println("fec12:"+fechaFinal);
            String fechaInicial1 = fechaInicial;
            if(!fechaInicial.equals("")){
                String arrayfecha1[] = fechaInicial.split("/");
                fechaInicial = arrayfecha1[2] + "/" + arrayfecha1[1] + "/" + arrayfecha1[0];
            }
            
            String fechaFinal1 = fechaFinal;
            if(!fechaFinal.equals("")){
                String arrayfecha2[] = fechaFinal.split("/");
                fechaFinal = arrayfecha2[2] + "/" + arrayfecha2[1] + "/" + arrayfecha2[0];
            }

            
            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            String nombreMaterialP = request.getParameter("nombreMaterialP") == null ? "''" : request.getParameter("nombreMaterialP");
            System.out.println("nombreMaterialP:" + nombreMaterialP);

            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");


            String lotesArray = request.getParameter("lotesArray") == null ? "'0'" : request.getParameter("lotesArray");
            String salidasArray = request.getParameter("salidasArray") == null ? "''" : request.getParameter("salidasArray");
            System.out.println("lotesArray:"+lotesArray);
            System.out.println("salidasArray:"+salidasArray);
            String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");
            //String numLoteP = request.getParameter("numLoteP") == null ? "''" : request.getParameter("numLoteP");

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

            <table width="110%" align="center" class="outputText0" style="border : solid #666666 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='35px'  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;" bgcolor="#f2f2f2"  align="center" colspan="2" ><b>&nbsp;</b></td>
                    <td HEIGHT='35px'  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>&nbsp;MATERIA PRIMA&nbsp;</b></td>
                    <td HEIGHT='35px'  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>&nbsp;EMPAQUE PRIMARIO&nbsp;</b></td>
                    <td HEIGHT='35px'  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>&nbsp;EMPAQUE SECUNDARIO&nbsp;</b></td>
                    <td HEIGHT='35px'  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>&nbsp;EMPAQUE SECUNDARIO(MM)&nbsp;</b></td>
                    <td HEIGHT='35px'  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>&nbsp;VARIOS&nbsp;</b></td>



                </tr>

                <tr style="text-transform:uppercase">
                    <td HEIGHT='35px'  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;" bgcolor="#f2f2f2"  align="center"><b>Lote</b></td>
                    <td   style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;" bgcolor="#f2f2f2"  align="center"><b>Producto</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"    bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol.</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol. Malos</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol.</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol. Malos</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol.</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol. Malos</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol.</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol. Malos</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Salidas</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol.</b></td>
                    <td  style="border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;"   bgcolor="#f2f2f2"  align="center"><b>Devol. Malos</b></td>

                </tr>


                <%

                    double totalsalMP=0;
                    double totaldevMP=0;
                    double totalmalosMP=0;
                    double totalsalEP=0;
                    double totaldevEP=0;
                    double totalmalosEP=0;
                    double totalsalES=0;
                    double totaldevES=0;
                    double totalmalosES=0;
                    double totalsalESMM=0;
                    double totaldevESMM=0;
                    double totalmalosESMM=0;
                    double totalsalVA=0;
                    double totaldevVA=0;
                    double totalmalosVA=0;

                System.out.println("fechaInicial:"+fechaInicial);
                System.out.println("fechaFinal:"+fechaFinal);
                String sql_lote = "";
                //sql_lote += "select distinct cod_lote_produccion, cod_prod from salidas_almacen sa where sa.estado_sistema=1 and sa.cod_estado_salida_almacen=1 ";
                sql_lote += " select distinct cod_lote_produccion, cod_prod from salidas_almacen sa where sa.estado_sistema=1 and sa.cod_estado_salida_almacen=1 and sa.COD_LOTE_PRODUCCION <> '' ";
                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    sql_lote = sql_lote + " and sa.FECHA_SALIDA_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }
                if(!lotesArray.equals("")){
                    sql_lote += " and sa.cod_lote_produccion IN ("+lotesArray+")";
                }
                
                System.out.println("sql_lote: " + sql_lote);

                con = Util.openConnection(con);
                Statement st_lote = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_lote = st_lote.executeQuery(sql_lote);
               

                //if (0==1) {
                while (rs_lote.next()) {
                    String codLoteProduccion=rs_lote.getString(1);
                    int codProd=rs_lote.getInt(2);
                    String nombreProd="";
                    String sql_prod="select c.nombre_prod_semiterminado from COMPONENTES_PROD c where c.COD_COMPPROD="+codProd+"";
                    Statement st_prod = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_prod = st_prod.executeQuery(sql_prod);
                    while(rs_prod.next()){
                        nombreProd=rs_prod.getString(1);
                    }
                    String sql_cantidad = " select distinct cod_lote_produccion,";
                    sql_cantidad += " (select sum(sadi.cantidad) from SALIDAS_ALMACEN s,SALIDAS_ALMACEN_DETALLE_INGRESO sadi, materiales m, grupos g";
                    sql_cantidad += " where s.COD_SALIDA_ALMACEN=sadi.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL=m.COD_MATERIAL and s.ESTADO_SISTEMA=1 and s.COD_ESTADO_SALIDA_ALMACEN=1 and";
                    sql_cantidad += " s.cod_prod="+codProd+"  ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and s.fecha_salida_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    //sql_cantidad += "       s.fecha_salida_almacen>='8/7/2012' and s.fecha_salida_almacen<='11/7/2012 23:59:59' and";
                    }

                    sql_cantidad += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=2 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_mp,";
                    sql_cantidad += " (select sum(sadi.cantidad) from SALIDAS_ALMACEN s,SALIDAS_ALMACEN_DETALLE_INGRESO sadi, materiales m, grupos g";
                    sql_cantidad += " where s.COD_SALIDA_ALMACEN=sadi.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL=m.COD_MATERIAL and s.ESTADO_SISTEMA=1 and s.COD_ESTADO_SALIDA_ALMACEN=1 and";
                    sql_cantidad += " s.cod_prod="+codProd+"  ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and s.fecha_salida_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }

                    sql_cantidad += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=3 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_ep,";
                    sql_cantidad += " (select sum(sadi.cantidad) from SALIDAS_ALMACEN s,SALIDAS_ALMACEN_DETALLE_INGRESO sadi, materiales m, grupos g";
                    sql_cantidad += " where s.COD_SALIDA_ALMACEN=sadi.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL=m.COD_MATERIAL and s.ESTADO_SISTEMA=1 and s.COD_ESTADO_SALIDA_ALMACEN=1 and";
                    sql_cantidad += " s.cod_prod="+codProd+"  ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and s.fecha_salida_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }

                    sql_cantidad += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=4 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_es,";
                    sql_cantidad += " (select sum(sadi.cantidad) from SALIDAS_ALMACEN s,SALIDAS_ALMACEN_DETALLE_INGRESO sadi, materiales m, grupos g";
                    sql_cantidad += " where s.COD_SALIDA_ALMACEN=sadi.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL=m.COD_MATERIAL and s.ESTADO_SISTEMA=1 and s.COD_ESTADO_SALIDA_ALMACEN=1 and";
                    sql_cantidad += " s.cod_prod="+codProd+"  ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and s.fecha_salida_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_cantidad += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=8 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_esm,";
                    sql_cantidad += " (select sum(sadi.cantidad) from SALIDAS_ALMACEN s,SALIDAS_ALMACEN_DETALLE_INGRESO sadi, materiales m, grupos g";
                    sql_cantidad += " where s.COD_SALIDA_ALMACEN=sadi.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL=m.COD_MATERIAL and s.ESTADO_SISTEMA=1 and s.COD_ESTADO_SALIDA_ALMACEN=1 and";
                    sql_cantidad += " s.cod_prod="+codProd+"  ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and s.fecha_salida_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_cantidad += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO>4 and g.COD_CAPITULO<>8 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_va";
                    sql_cantidad += " from SALIDAS_ALMACEN sa";
                    sql_cantidad += " where sa.COD_LOTE_PRODUCCION='"+codLoteProduccion+"' AND sa.cod_prod="+codProd+"";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and sa.fecha_salida_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    System.out.println("sql_cantidad: " + sql_cantidad);
                    Statement st_cantidad = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_cantidad = st_cantidad.executeQuery(sql_cantidad);
                    double salMP=0;
                    double devMP=0;
                    double malosMP=0;
                    double salEP=0;
                    double devEP=0;
                    double malosEP=0;
                    double salES=0;
                    double devES=0;
                    double malosES=0;
                    double salESMM=0;
                    double devESMM=0;
                    double malosESMM=0;
                    double salVA=0;
                    double devVA=0;
                    double malosVA=0;
                    while (rs_cantidad.next()) {
                        salMP=rs_cantidad.getDouble("costo_mp");
                        totalsalMP +=salMP;
                        salEP=rs_cantidad.getDouble("costo_ep");
                        totalsalEP +=salEP;
                        salES=rs_cantidad.getDouble("costo_es");
                        totalsalES +=salES;
                        salESMM=rs_cantidad.getDouble("costo_esm");
                        totalsalESMM +=salESMM;
                        salVA=rs_cantidad.getDouble("costo_va");
                        totalsalVA +=salVA;
                        System.out.println("Entro:"+rs_cantidad.getDouble("costo_mp"));

                    }

                    String sql_devol = " select distinct cod_lote_produccion,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN=ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=2 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_mp,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN=ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=3 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_ep,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN=ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=4 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_es,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN=ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=8 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_esm,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN=ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO>4 and g.COD_CAPITULO<>8 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_va,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN<>ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=2 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_mpmalo,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN<>ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=3 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_epmalo,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN<>ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=4 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_esmalo,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN<>ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=8 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_esmmalo,";
                    sql_devol += " (select sum(iad.CANT_TOTAL_INGRESO_FISICO) from SALIDAS_ALMACEN s,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " ,INGRESOS_ALMACEN_DETALLE iad, materiales m, grupos g";
                    sql_devol += " where s.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN<>ia.COD_ALMACEN and";
                    sql_devol += " s.cod_prod="+codProd+" and ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 and ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN and iad.COD_MATERIAL=m.COD_MATERIAL";
                    sql_devol += " and m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO>4 and g.COD_CAPITULO<>8 and s.COD_LOTE_PRODUCCION=sa.COD_LOTE_PRODUCCION)as costo_vamalo";
                    sql_devol += " from SALIDAS_ALMACEN sa,devoluciones d,INGRESOS_ALMACEN ia";
                    sql_devol += " where sa.COD_LOTE_PRODUCCION='"+codLoteProduccion+"' AND sa.cod_prod="+codProd+"";
                    sql_devol += " and sa.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and ia.COD_DEVOLUCION=d.COD_DEVOLUCION and d.COD_ALMACEN=ia.COD_ALMACEN and";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_cantidad = sql_cantidad + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    sql_devol += " d.ESTADO_SISTEMA=1 and d.COD_ESTADO_DEVOLUCION=1 ";
                    System.out.println("sql_devol: " + sql_devol);
                    Statement st_devol = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_devol = st_devol.executeQuery(sql_devol);
                    while (rs_devol.next()) {
                        devMP=rs_devol.getDouble("costo_mp");
                        totaldevMP +=devMP;
                        devEP=rs_devol.getDouble("costo_ep");
                        totaldevEP +=devEP;
                        devES=rs_devol.getDouble("costo_es");
                        totaldevES +=devES;
                        devESMM=rs_devol.getDouble("costo_esm");
                        totaldevESMM +=devESMM;
                        devVA=rs_devol.getDouble("costo_va");
                        totaldevVA +=devVA;

                        malosMP=rs_devol.getDouble("costo_mpmalo");
                        totalmalosMP +=malosMP;
                        malosEP=rs_devol.getDouble("costo_epmalo");
                        totalmalosEP +=malosEP;
                        malosES=rs_devol.getDouble("costo_esmalo");
                        totalmalosES +=malosES;
                        malosESMM=rs_devol.getDouble("costo_esmmalo");
                        totalmalosESMM +=malosESMM;
                        malosVA=rs_devol.getDouble("costo_vamalo");
                        totalmalosVA +=malosVA;
                  }
                   out.print("<tr >");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + codLoteProduccion + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + nombreProd+ "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(salMP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(devMP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(malosMP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(salEP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(devEP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(malosEP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(salES) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(devES) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(malosES) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(salESMM) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(devESMM) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(malosESMM) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(salVA) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(devVA) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(malosVA) + "</td>");
                   out.print("</tr>");



                }


                   out.print("<tr >");

                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border' colspan='2'   >Total :&nbsp;</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalsalMP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totaldevMP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalmalosMP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalsalEP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totaldevEP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalmalosEP) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalsalES) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totaldevES) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalmalosES) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalsalESMM) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totaldevESMM) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalmalosESMM) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalsalVA) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totaldevVA) + "</td>");
                    out.print("<td style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding:5PX;' class='border'   >" + formato.format(totalmalosVA) + "</td>");
                   out.print("</tr>");
                %>
            </table>
            <td class='border' align='right'  style='border-bottom: solid #cccccc 1px;border-left:solid #cccccc 1px;;padding-left:5PX;font-family:monospace;font-size:x-large' >


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
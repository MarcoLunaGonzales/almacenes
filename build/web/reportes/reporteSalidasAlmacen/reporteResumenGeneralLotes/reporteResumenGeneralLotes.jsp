
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
            <h4 align="center">Reporte Resumen General de Lotes</h4>
            <%!    public double redondear(double numero, int decimales) {
                    return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
                }
            %>


            <%
                try {

                    String codTipoSalida = request.getParameter("codTipoSalida").trim();
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    DecimalFormat formato = (DecimalFormat) nf;
                    formato.applyPattern("#,###.00");

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");

                    con = Util.openConnection(con);

                    String tiposSalidaReporteparam = "1,2,4,5,6,8";
                    String cadenaNombreTipoSalida = request.getParameter("nombreTipoSalida");
                    if(cadenaNombreTipoSalida.trim().length() == 0)cadenaNombreTipoSalida="--TODOS--";
                    boolean devolucionesTodosAlmacenes = (request.getParameter("devolucionesTodosAlmacenes") != null);
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
                    System.out.println("tipos salida" + codTipoSalida);

                    String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
                    String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
                    String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
                    String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");

                    String codProductoP = request.getParameter("codProductoP") == null ? "'0'" : request.getParameter("codProductoP");
                    String nombreProductoP = request.getParameter("nombreProductoP") == null ? "''" : request.getParameter("nombreProductoP");
                    String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");
                    String numLoteP = request.getParameter("numLoteP") == null ? "''" : request.getParameter("numLoteP");
                    String codTipoLoteP = request.getParameter("codTipoLoteP") == null ? "''" : request.getParameter("codTipoLoteP");

                    System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " TL" + codTipoLoteP  +"--tl " + codAlmacen + " " + codStock + " " );
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
                                    <td><b>Tipo Salida</b></td>
                                    <td><%=cadenaNombreTipoSalida%></td>
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
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>&nbsp;</b></td>
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
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Linea(s)</b></td>
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
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Neto222</b></td>
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

                    String sql_lote = "select distinct  sa.COD_LOTE_PRODUCCION,cp.COD_COMPPROD,cp.nombre_prod_semiterminado,isnull(temp.lineas,'') as lineas";
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
//codigo para incorporar las lineas
                    sql_lote += " left join (select cpp.COD_COMPPROD, STUFF(";
                    sql_lote += " (SELECT  distinct(cast(lmtk1.COD_LINEAMKT as varchar(50))) +',' ";
                    sql_lote += " FROM COMPONENTES_PRESPROD cpp1";
                    sql_lote += " inner join PRESENTACIONES_PRODUCTO pp1 on pp1.cod_presentacion=cpp1.COD_PRESENTACION";
                    sql_lote += " inner join LINEAS_MKT lmtk1 on lmtk1.COD_LINEAMKT=pp1.COD_LINEAMKT";
                    sql_lote += " WHERE cpp1.COD_COMPPROD=cpp.COD_COMPPROD";
                    sql_lote += " FOR XML PATH (''))";
                    sql_lote += " , 1, 0, '')  AS [Codigos_lineas],";
                    sql_lote += " STUFF(";
                    sql_lote += " (SELECT  distinct(NOMBRE_LINEAMKT) +', '";
                    sql_lote += " FROM COMPONENTES_PRESPROD cpp1";
                    sql_lote += " inner join PRESENTACIONES_PRODUCTO pp1 on pp1.cod_presentacion=cpp1.COD_PRESENTACION";
                    sql_lote += " inner join LINEAS_MKT lmtk1 on lmtk1.COD_LINEAMKT=pp1.COD_LINEAMKT";
                    sql_lote += " WHERE cpp1.COD_COMPPROD=cpp.COD_COMPPROD";

                    sql_lote += " FOR XML PATH (''))";
                    sql_lote += "  , 1, 0, '')  AS [lineas]";
                    sql_lote += " from COMPONENTES_PRESPROD cpp";
                    sql_lote += " inner join PRESENTACIONES_PRODUCTO pp on pp.cod_presentacion=cpp.COD_PRESENTACION";
                    sql_lote += " inner join LINEAS_MKT lmtk on lmtk.COD_LINEAMKT=pp.COD_LINEAMKT";
                    sql_lote += " group by cpp.COD_COMPPROD) temp on temp.COD_COMPPROD=cp.COD_COMPPROD";
                    //fin del codigo para incorporar las lineas
                    sql_lote += " WHERE  sa.COD_ESTADO_SALIDA_ALMACEN<>2 ";
                    if(codTipoSalida.length() > 0)
                        sql_lote += " and sa.COD_TIPO_SALIDA_ALMACEN in (" + codTipoSalida + ") ";
                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_lote = sql_lote + " and sa.FECHA_SALIDA_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    if (!numLoteP.equals("")) {
                        sql_lote = sql_lote + " and sa.COD_LOTE_PRODUCCION like '" + numLoteP + "%' ";
                    }
                    if (!codProductoP.equals("") && !codProductoP.equals("0")) {
                        sql_lote = sql_lote + " and sa.COD_PROD = " + codProductoP + " ";
                    }
                    /*if (!codFilial.equals("") && !codFilial.equals("0")) {
                     sql_lote = sql_lote + " and f.cod_filial = '" + codFilial + "' ";
                     }*/
                    if (!codAlmacen.equals("") && !codAlmacen.equals("-1")) {
                        sql_lote = sql_lote + " and sa.COD_ALMACEN  in (" + codAlmacen + ")";
                    }
                    if (codTipoLoteP.equals("2")) {
                        sql_lote = sql_lote + " and sa.COD_LOTE_PRODUCCION like '6%' ";
                    }
                    if (codTipoLoteP.equals("1")) {
                        sql_lote = sql_lote + " and sa.COD_LOTE_PRODUCCION not like '6%' ";
                    }
                    //salida += " ORDER BY sa.FECHA_SALIDA_ALMACEN desc,sa.NRO_SALIDA_ALMACEN,m.NOMBRE_MATERIAL,sa.COD_LOTE_PRODUCCION   ";
                    sql_lote += " union";
                    sql_lote += " select  distinct sa.COD_LOTE_PRODUCCION, cp.COD_COMPPROD, cp.nombre_prod_semiterminado,isnull(temp.lineas,'') as lineas";
                    sql_lote += " from devoluciones d";
                    sql_lote += " inner join salidas_almacen sa on d.cod_salida_almacen = sa.cod_salida_almacen";
                    sql_lote += " inner join ingresos_almacen ia on d.cod_devolucion = ia.cod_devolucion and ia.cod_almacen = d.cod_almacen";
                    sql_lote += " inner join ingresos_almacen_detalle iad on iad.cod_ingreso_almacen = ia.cod_ingreso_almacen";
                    sql_lote += " inner join COMPONENTES_PROD cp on cp.COD_COMPPROD=sa.COD_PROD";
                    //codigo para incorporar las lineas
                    sql_lote += " left join (select cpp.COD_COMPPROD, STUFF(";
                    sql_lote += " (SELECT  distinct(cast(lmtk1.COD_LINEAMKT as varchar(50))) +',' ";
                    sql_lote += " FROM COMPONENTES_PRESPROD cpp1";
                    sql_lote += " inner join PRESENTACIONES_PRODUCTO pp1 on pp1.cod_presentacion=cpp1.COD_PRESENTACION";
                    sql_lote += " inner join LINEAS_MKT lmtk1 on lmtk1.COD_LINEAMKT=pp1.COD_LINEAMKT";
                    sql_lote += " WHERE cpp1.COD_COMPPROD=cpp.COD_COMPPROD";
                    sql_lote += " FOR XML PATH (''))";
                    sql_lote += " , 1, 0, '')  AS [Codigos_lineas],";
                    sql_lote += " STUFF(";
                    sql_lote += " (SELECT  distinct(NOMBRE_LINEAMKT) +', '";
                    sql_lote += " FROM COMPONENTES_PRESPROD cpp1";
                    sql_lote += " inner join PRESENTACIONES_PRODUCTO pp1 on pp1.cod_presentacion=cpp1.COD_PRESENTACION";
                    sql_lote += " inner join LINEAS_MKT lmtk1 on lmtk1.COD_LINEAMKT=pp1.COD_LINEAMKT";
                    sql_lote += " WHERE cpp1.COD_COMPPROD=cpp.COD_COMPPROD";

                    sql_lote += " FOR XML PATH (''))";
                    sql_lote += "  , 1, 0, '')  AS [lineas]";
                    sql_lote += " from COMPONENTES_PRESPROD cpp";
                    sql_lote += " inner join PRESENTACIONES_PRODUCTO pp on pp.cod_presentacion=cpp.COD_PRESENTACION";
                    sql_lote += " inner join LINEAS_MKT lmtk on lmtk.COD_LINEAMKT=pp.COD_LINEAMKT";
                    sql_lote += " group by cpp.COD_COMPPROD) temp on temp.COD_COMPPROD=cp.COD_COMPPROD";
                    //fin del codigo para incorporar las lineas
                    sql_lote += " where d.estado_sistema = 1 ";

                    if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                        sql_lote = sql_lote + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    if (!codAlmacen.equals("") && !codAlmacen.equals("-1")) {
                        sql_lote = sql_lote + " and sa.cod_almacen in (" + codAlmacen + ") and d.cod_almacen in (" + codAlmacen + ") ";
                    }
                    if (!numLoteP.equals("")) {
                        sql_lote = sql_lote + " and sa.COD_LOTE_PRODUCCION like '" + numLoteP + "%' ";
                    }
                    if (codTipoLoteP.equals("2")) {
                        sql_lote = sql_lote + " and sa.COD_LOTE_PRODUCCION like '6%' ";
                    }
                    if (codTipoLoteP.equals("1")) {
                        sql_lote = sql_lote + " and sa.COD_LOTE_PRODUCCION not like '6%' ";
                    } 
                    sql_lote += " and ia.cod_estado_ingreso_almacen=1 and d.cod_estado_devolucion=1";
                    if(codTipoSalida.length() > 0)
                        sql_lote += " and sa.COD_TIPO_SALIDA_ALMACEN in (" + codTipoSalida + ") ";
                    //sql_lote += " AND sa.COD_LOTE_PRODUCCION= '110072'";
                    // AND sa.COD_LOTE_PRODUCCION= '110072'

                    sql_lote += " ORDER BY sa.COD_LOTE_PRODUCCION   ";

                    System.out.println("sql_lote: " + sql_lote);

                    //con = Util.openConnection(con);
                    Statement st_lote = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_lote = st_lote.executeQuery("set dateformat 'ymd'"+sql_lote);
                    String codLoteProd = "";
                    int codProdSemiterminado = 0;
                    String nombreProdSemiterminado = "";
                    String capitulos = "2,3,4,8,1000";
                    int capituloInt = 0;
                    String loteAnt = "";
                    while (rs_lote.next()) {

                        codLoteProd = rs_lote.getString(1);
                        if (!loteAnt.equals(codLoteProd)) {

                            codProdSemiterminado = rs_lote.getInt(2);
                            nombreProdSemiterminado = rs_lote.getString(3);
                            String capVector[] = capitulos.split(",");
                            out.print("<tr>");
                            out.print("<td HEIGHT='25PX' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + codLoteProd + "</td>");
                            out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + nombreProdSemiterminado + "</td>");
                            //aumentar el campo de la linea de productos
                            out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + rs_lote.getString("lineas") + "</td>");

                            String salida =""; 
                            salida +=  "SELECT * ";
                            salida += " FROM (";
                                salida += " select";
                                    salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO = 2 THEN isnull((sadi.costo_salida_actualizado * sadi.cantidad),0) ELSE 0 END), 0) as sal2,";
                                    salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO = 3 THEN isnull((sadi.costo_salida_actualizado * sadi.cantidad),0) ELSE 0 END), 0) as sal3,";
                                    salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO = 4 THEN isnull((sadi.costo_salida_actualizado * sadi.cantidad),0) ELSE 0 END), 0) as sal4,";
                                    salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO = 8 THEN isnull((sadi.costo_salida_actualizado * sadi.cantidad),0) ELSE 0 END), 0) as sal8,";
                                    salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO > 4 AND c.cod_capitulo <> 8 THEN isnull((sadi.costo_salida_actualizado * sadi.cantidad),0) ELSE 0 END), 0) as sal_varios,";
                                    salida += " isnull(sum(isnull((sadi.costo_salida_actualizado * sadi.cantidad),0)), 0) as total_salida";
                                salida += " from salidas_almacen_detalle sad";
                                    salida += " inner join SALIDAS_ALMACEN sa on sa.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN";
                                    salida += " inner join MATERIALES m on m.COD_MATERIAL = sad.COD_MATERIAL";
                                    salida += " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL=m.COD_MATERIAL";
                                    salida += " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO";
                                    salida += " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO";
                                salida += " where  sa.COD_ESTADO_SALIDA_ALMACEN=1 and sa.ESTADO_SISTEMA=1  ";
                            if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                                salida += " and sa.FECHA_SALIDA_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                            }
                                salida += " and sa.COD_LOTE_PRODUCCION ='" + codLoteProd + "' ";
                            if (!codAlmacen.equals("") && !codAlmacen.equals("-1")) {
                                salida += " and sa.COD_ALMACEN in (" + codAlmacen + ") ";
                            }
                            if(codTipoSalida.length() > 0)
                                salida += " and sa.COD_TIPO_SALIDA_ALMACEN in (" + codTipoSalida+ ")";
                            salida += " )x";
                            salida += " FULL OUTER JOIN (";
                                salida += " select";
                                salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO = 2 THEN isnull((iad.COSTO_UNITARIO_ACTUALIZADO * iad.CANT_TOTAL_INGRESO_FISICO),0) ELSE 0 END),0) as dev2,";
                                salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO = 3 THEN isnull((iad.COSTO_UNITARIO_ACTUALIZADO * iad.CANT_TOTAL_INGRESO_FISICO),0) ELSE 0 END),0) as dev3,";
                                salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO = 4 THEN isnull((iad.COSTO_UNITARIO_ACTUALIZADO * iad.CANT_TOTAL_INGRESO_FISICO),0) ELSE 0 END),0) as dev4,";
                                salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO = 8 THEN isnull((iad.COSTO_UNITARIO_ACTUALIZADO * iad.CANT_TOTAL_INGRESO_FISICO),0) ELSE 0 END),0) as dev8,";
                                salida += " isnull(SUM(CASE WHEN c.COD_CAPITULO > 4 AND c.cod_capitulo <> 8 THEN isnull((iad.COSTO_UNITARIO_ACTUALIZADO * iad.CANT_TOTAL_INGRESO_FISICO),0) ELSE 0 END),0) as dev_varios,";
                                salida += " isnull(SUM(isnull(iad.COSTO_UNITARIO_ACTUALIZADO * iad.CANT_TOTAL_INGRESO_FISICO, 0)),0) as total_dev";
                                salida += " from devoluciones d";
                                    salida += " inner join  salidas_almacen sa on d.cod_salida_almacen = sa.cod_salida_almacen";
                                    salida += " inner join  ingresos_almacen ia on d.cod_devolucion = ia.cod_devolucion and ia.cod_almacen = d.cod_almacen";
                                    salida += " inner join  ingresos_almacen_detalle iad on iad.cod_ingreso_almacen = ia.cod_ingreso_almacen";
                                    salida += " inner join  MATERIALES m on m.COD_MATERIAL = iad.COD_MATERIAL";
                                    salida += " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO";
                                    salida += " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO";
                                    salida += " where d.estado_sistema=1 ";
                                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                                    salida += " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                                }
                                salida += " and sa.COD_LOTE_PRODUCCION = '" + codLoteProd + "' ";
                                if (!codAlmacen.equals("") && !codAlmacen.equals("-1")) {
                                    salida += " and sa.cod_almacen in (" + codAlmacen + ")";
                                    if(!devolucionesTodosAlmacenes){
                                        salida += " and d.cod_almacen in (" + codAlmacen + ") ";
                                    }
                                }
                                    salida += " and ia.cod_estado_ingreso_almacen=1 and d.cod_estado_devolucion=1   ";
                                    if(codTipoSalida.length() > 0)
                                        salida += " and sa.COD_TIPO_SALIDA_ALMACEN in (" + codTipoSalida + ")";
                            salida += " )y on 1=1";

                            System.out.println("salida: " + salida);

                            //con = Util.openConnection(con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet rs = st.executeQuery(salida);
                            costoTotal = 0;
                            costoTotalCosto = 0;
                            costoTotalDevolucion = 0;
                            int c = 0;
                            int nroSalidaAux = 0;
                            double sal2 = 0d;
                            double sal3 = 0d;
                            double sal4 = 0d;
                            double sal8 = 0d;
                            double salVarios = 0d;
                            double salTotal = 0d;
                            double dev2 = 0d;
                            double dev3 = 0d;
                            double dev4 = 0d;
                            double dev8 = 0d;
                            double devVarios = 0d;
                            double devTotal = 0d;
                            if (rs.next()) {

                                sal2 = rs.getDouble("sal2");
                                sal3 = rs.getDouble("sal3");
                                sal4 = rs.getDouble("sal4");
                                sal8 = rs.getDouble("sal8");
                                salVarios = rs.getDouble("sal_varios");
                                salTotal = rs.getDouble("total_salida");
                                dev2 = rs.getDouble("dev2");
                                dev3 = rs.getDouble("dev3");
                                dev4 = rs.getDouble("dev4");
                                dev8 = rs.getDouble("dev8");
                                devVarios = rs.getDouble("dev_varios");
                                devTotal = rs.getDouble("total_dev");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(sal2)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(dev2)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(sal3)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(dev3)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(sal4)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(dev4)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(sal8)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(dev8)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(salVarios)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(devVarios)) + "</td>");

                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(salTotal)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(devTotal)) + "</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(salTotal - devTotal)) + "</td>");


                                total_salidas_MP += sal2;
                                total_dev_MP += dev2;

                                total_salidas_EP += sal3;
                                total_dev_EP += dev3;

                                total_salidas_ES += sal4;
                                total_dev_ES += dev4;

                                total_salidas_ESMM += sal8;
                                total_dev_ESMM += dev8;

                                total_salidas_VARIOS += salVarios;
                                total_dev_VARIOS += devVarios;

                            }
                            out.print("</tr>");

                        }
                        loteAnt = codLoteProd;
                        //break;
                    }
                    out.print("<tr BGCOLOR='#C2C2C2'>");
                    out.print("<td HEIGHT='25PX' colspan='3' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >&nbsp;</td>");
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
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_salidas_MP + total_salidas_EP + total_salidas_ES + total_salidas_ESMM + total_salidas_VARIOS) + "</td>");
                    out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(total_dev_MP + total_dev_EP + total_dev_ES + total_dev_ESMM + total_dev_VARIOS) + "</td>");
                    out.print("<td HEIGHT='25PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format((total_salidas_MP + total_salidas_EP + total_salidas_ES + total_salidas_ESMM + total_salidas_VARIOS) - (total_dev_MP + total_dev_EP + total_dev_ES + total_dev_ESMM + total_dev_VARIOS)) + "</td>");
                    out.print("</tr>");

                %>
            </table>
            <td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-left:5PX;font-family:monospace;font-size:x-large' >


                <%                } catch (Exception e) {
                        e.printStackTrace();
                    }

                %>
                <br>

                <br>
                <div align="center">
                    <%--<INPUT type="button" class="commandButton" name="btn_registrar" value="<-- AtrÃ¡s" onClick="cancelar();"  >--%>

                </div>
        </form>
    </body>
</html>
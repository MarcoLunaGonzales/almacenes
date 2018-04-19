
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
            <h4 align="center">Reporte Costos por Fórmula Maestra</h4>

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
                    formato.applyPattern("####0.00000000");

                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");

                    con = Util.openConnection(con);

                    /*String fechaInicial=request.getParameter("fecha1")==null?"01/01/2011":request.getParameter("fecha1");
                     String fechaInicial1 = fechaInicial;
                     String arrayfecha1[]=fechaInicial.split("/");
                     fechaInicial=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];

                     String fechaFinal=request.getParameter("fecha2")==null?"01/01/2011":request.getParameter("fecha2");
                     String fechaFinal1 = fechaFinal;
                     String arrayfecha2[]=fechaFinal.split("/");
                     fechaFinal=arrayfecha2[2] +"/"+ arrayfecha2[1]+"/"+arrayfecha2[0];*/
                    System.out.println("fec11:" + request.getParameter("fechaInicial"));
                    System.out.println("fec12:" + request.getParameter("fechaFinal"));
                    String fechaInicial = "";
                    if (request.getParameter("fechaInicial").equals("0")) {
                        System.out.println("1:");
                        fechaInicial = "";
                    } else {
                        System.out.println("12:");
                        fechaInicial = request.getParameter("fechaInicial");
                    }
                    String fechaFinal = "";
                    if (request.getParameter("fechaFinal").equals("0")) {
                        System.out.println("13:");
                        fechaFinal = "";
                    } else {
                        System.out.println("14:");
                        fechaFinal = request.getParameter("fechaFinal");
                    }
                    System.out.println("fec11:" + fechaInicial);
                    System.out.println("fec12:" + fechaFinal);
                    String fechaInicial1 = fechaInicial;
                    if (!fechaInicial.equals("")) {
                        String arrayfecha1[] = fechaInicial.split("/");
                        fechaInicial = arrayfecha1[2] + "/" + arrayfecha1[1] + "/" + arrayfecha1[0];
                    }

                    String fechaFinal1 = fechaFinal;
                    if (!fechaFinal.equals("")) {
                        String arrayfecha2[] = fechaFinal.split("/");
                        fechaFinal = arrayfecha2[2] + "/" + arrayfecha2[1] + "/" + arrayfecha2[0];
                    }

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
                    String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");

                    System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " "
                            + "" + codFilial + " " + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);
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

            <BR>
            <BR>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Nro. </b></td>

                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Lote  </b></td>

                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Lote Cerrado Zeus (S/N)</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Código Pres.</b></td> 
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Tipo Prog. Prod.</b></td> 
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Estado Prog. Prod.</b></td> 
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Presentación</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Material</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad U x Item</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Costo U x Item</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Costo U x Item x FM</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Cantidad Ingresada APT</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Cantidad Lote Producción</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Costo Salidas BACO</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Costo Devoluciones BACO</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Costo Total BACO</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Nombre Area Empresa</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Nombre Producto Semiterminado</b></td>




                </tr>

                <%

                    String consulta = " exec [USP_GET_LISTA_LOTE_PRESENTACION_COSTO] '" + fechaInicial + " 00:00:00','" + fechaFinal + " 23:59:59'";
                    System.out.println("consulta 1 " + consulta);

                    con = Util.openConnection(con);
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    st.execute(consulta);
                    consulta = " exec [USP_GET_LISTA_COSTO_PRODUCTOS_FM] '" + fechaInicial + " 00:00:00','" + fechaFinal + " 23:59:59' ";
                    System.out.println("consulta 2 " + consulta);

                    ResultSet rs = st.executeQuery(consulta);
                    costoTotal = 0;
                    
                    int c = 0;
                    int cantidad_datos=7;
                    double cantidad_totalAPT=0;
                    while (rs.next()) {
                        String lote_produccion = rs.getString("COD_LOTE_PRODUCCION");
                        String cod_presentacion = rs.getString("COD_PRESENTACION");
                        String nombre_presentacion = rs.getString("NOMBRE_PRODUCTO_PRESENTACION");
                        double cantidad_total_apt = rs.getDouble("CANTIDAD_TOTAL");
                        double cant_total_produccion = rs.getDouble("CANT_LOTE_PRODUCCION");
                        int cod_compprod = rs.getInt("cod_compprod");
                        int cod_programa_prod = rs.getInt("cod_programa_prod");

                        int cod_tipo_programa_prod = rs.getInt("cod_tipo_programa_prod");
                        String nombre_tipo_programa_prod = rs.getString("nombres_tipos_prog_prod");
                        
                        String NOMBRE_TIPO_PROGRAMA_PROD_1=rs.getString("NOMBRE_TIPO_PROGRAMA_PROD");
                        
                        String codigos_estado = rs.getString("Codigos_estados");
                        String nombre_estado = rs.getString("nombres_estados_prog_prod");

                        double costo_lote = rs.getDouble("costo_lote");
                        double costo_lote_devolucion = rs.getDouble("costo_lote_devolucion");

                        String lote_cerrado = rs.getString("cierre_lote");
                        String nombre_area_empresa = rs.getString("NOMBRE_AREA_EMPRESA");
                        String nombre_prod_semiterminado = rs.getString("nombre_prod_semiterminado");
                        out.print("<tr>");
                        c++;
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "" + c + "   "
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "" + (lote_produccion == null ? "" : lote_produccion) + "   "
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "" + (lote_cerrado.equals("0") ? "NO" : "SI") + "   "
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "" + (cod_presentacion == null ? "" : cod_presentacion)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "" + (nombre_tipo_programa_prod == null ? "" : nombre_tipo_programa_prod)
                                + "</th>");

                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "" + (nombre_estado == null ? "" : nombre_estado)
                                + "</th>");

                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border' colspan='5'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + (nombre_presentacion == null ? "" : nombre_presentacion)
                                + "</th>");

                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + cantidad_total_apt
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + cant_total_produccion
                                + "</th>");

                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(costo_lote)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(costo_lote_devolucion)
                                + "</th>");
                        double costo_total_lote = costo_lote - costo_lote_devolucion;
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(costo_total_lote)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + (nombre_area_empresa == null ? "" : nombre_area_empresa)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + (nombre_prod_semiterminado == null ? "" : nombre_prod_semiterminado)
                                + "</th>");
                        out.print("</tr>");
                        cantidad_totalAPT+=cantidad_total_apt;
                        
                        
                        
                        
                        out.print("<tr>");
                        out.print("<th HEIGHT='30PX' colspan='"+cantidad_datos+"' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + ""
                                + "</th>");
                        
                        
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' colspan='5' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "MATERIA PRIMA"
                                + "</th>");
                        out.print("</tr>");
                        
                        Statement stMP = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        
                        String consultaMP = "exec [USP_GET_LISTA_MATERIA_PRIMA_LOTE_COSTO] '" + lote_produccion + "', " + cod_programa_prod + ", "+ cod_compprod + ",'" + fechaFinal + "',"+cod_tipo_programa_prod;
                        if(nombre_tipo_programa_prod.equals("NO CONSIDERADO")){
                            consultaMP = "exec [USP_GET_LISTA_MATERIA_PRIMA_LOTE_COSTO_SIN_PROGPROD] '" + lote_produccion + "' " + ",'" + fechaFinal + "',"+cantidad_totalAPT;
                        }
                        System.out.println("consulta MP " + consultaMP);
                        ResultSet rsMP = stMP.executeQuery(consultaMP);

                        double total_cantidadUxItemMP = 0;
                        double total_costoUxItemMP = 0;
                        double total_costoFMMP = 0;
                        double total_costoLoteMP = 0;
                        double total_costoLoteDevMP = 0;
                        while (rsMP.next()) {

                            out.print("<tr>");
                            out.print("<td HEIGHT='30PX' colspan='"+cantidad_datos+"' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;</td>");
                            
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'  bgcolor='#FFF2CC' >" + (rsMP.getString("NOMBRE_MATERIAL") == null ? "" : rsMP.getString("NOMBRE_MATERIAL")) + "</td>");
                            double cantidad_unitaria_fm = rsMP.getDouble("cantidad") / rs.getDouble("CANT_LOTE_PRODUCCION");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#FFF2CC' >" + formato.format(cantidad_unitaria_fm) + "</td>");
                            double costo_unitario = rsMP.getDouble("costo_calculado");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#FFF2CC' >" + formato.format(costo_unitario) + "</td>");
                            double costo_unitario_total = rsMP.getDouble("costo_calculado") * cantidad_unitaria_fm;
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#FFF2CC' >" + formato.format(costo_unitario_total) + "</td>");

                            out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + "" + "</td>");

                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + "" + "</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + "" + "</td>");

                            out.print("</tr>");
                            costoTotal = costoTotal + rs.getDouble("cantidad_total");
                            total_cantidadUxItemMP += cantidad_unitaria_fm;
                            total_costoUxItemMP += costo_unitario;
                            total_costoFMMP += costo_unitario_total;
                            total_costoLoteMP += rsMP.getDouble("costo_lote");
                            total_costoLoteDevMP += rsMP.getDouble("costo_lote_devolucion");
                        }
                        
                        out.print("<tr>");
                        out.print("<th HEIGHT='30PX' colspan='"+cantidad_datos+"' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + ""
                                + "</th>");
                        
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "Totales"
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_cantidadUxItemMP)
                                + "</th>");

                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoUxItemMP)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoFMMP)
                                + "</th>");
                        double total_costoAPTMP = cantidad_total_apt * total_costoFMMP;
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoAPTMP)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoLoteMP)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoLoteDevMP)
                                + "</th>");
                        double costoTotalMP = total_costoLoteMP - total_costoLoteDevMP;
                        out.print("<th HEIGHT='30PX' bgcolor='#FFD966' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(costoTotalMP)
                                + "</th>");
                        out.print("</tr>");

                        
                        
                        out.print("<tr>");
                        out.print("<th HEIGHT='30PX' colspan='"+cantidad_datos+"' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + ""
                                + "</th>");
                        
                        
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' colspan='5' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "MATERIA REACTIVOS"
                                + "</th>");
                        out.print("</tr>");
                        
                        
                        
                                   Statement stMR = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        
                        String consultaMR = "exec [USP_GET_LISTA_MATERIA_REACTIVOS_LOTE_COSTO] '" + lote_produccion + "', " + cod_programa_prod + ", "+ cod_compprod + ",'" + fechaFinal + "',"+cod_tipo_programa_prod;
                        
                        System.out.println("consulta MR " + consultaMR);
                        ResultSet rsMR = stMR.executeQuery(consultaMR);

                        double total_cantidadUxItemMR = 0;
                        double total_costoUxItemMR = 0;
                        double total_costoFMMR = 0;
                        double total_costoLoteMR = 0;
                        double total_costoLoteDevMR = 0;
                        while (rsMR.next()) {

                            out.print("<tr>");
                            out.print("<td HEIGHT='30PX' colspan='"+cantidad_datos+"' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;</td>");
                            
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #FCE4D6 1px;padding-right:5PX;'  bgcolor='#FFF2CC' >" + (rsMR.getString("NOMBRE_MATERIAL") == null ? "" : rsMR.getString("NOMBRE_MATERIAL")) + "</td>");
                            double cantidad_unitaria_fm = rsMR.getDouble("cantidad") / rs.getDouble("CANT_LOTE_PRODUCCION");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #FCE4D6 1px;padding-right:5PX;' bgcolor='#FFF2CC' >" + formato.format(cantidad_unitaria_fm) + "</td>");
                            double costo_unitario = rsMR.getDouble("costo_calculado");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #FCE4D6 1px;padding-right:5PX;' bgcolor='#FFF2CC' >" + formato.format(costo_unitario) + "</td>");
                            double costo_unitario_total = rsMR.getDouble("costo_calculado") * cantidad_unitaria_fm;
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #FCE4D6 1px;padding-right:5PX;' bgcolor='#FFF2CC' >" + formato.format(costo_unitario_total) + "</td>");

                            out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + "" + "</td>");

                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #FCE4D6 1px;padding-right:5PX;' >" + "" + "</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #FCE4D6 1px;padding-right:5PX;' >" + "" + "</td>");

                            out.print("</tr>");
                            costoTotal = costoTotal + rs.getDouble("cantidad_total");
                            total_cantidadUxItemMR += cantidad_unitaria_fm;
                            total_costoUxItemMR += costo_unitario;
                            total_costoFMMR += costo_unitario_total;
                            total_costoLoteMR += rsMR.getDouble("costo_lote");
                            total_costoLoteDevMR += rsMR.getDouble("costo_lote_devolucion");
                        }
                        
                        out.print("<tr>");
                        out.print("<th HEIGHT='30PX' colspan='"+cantidad_datos+"' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + ""
                                + "</th>");
                        
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "Totales"
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_cantidadUxItemMR)
                                + "</th>");

                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoUxItemMR)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoFMMR)
                                + "</th>");
                        double total_costoAPTMR = cantidad_total_apt * total_costoFMMR;
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoAPTMR)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoLoteMR)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoLoteDevMR)
                                + "</th>");
                        double costoTotalMR = total_costoLoteMR - total_costoLoteDevMR;
                        out.print("<th HEIGHT='30PX' bgcolor='#ED7D31' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(costoTotalMR)
                                + "</th>");
                        out.print("</tr>");
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        out.print("<tr>");
                        out.print("<th HEIGHT='30PX' colspan='"+cantidad_datos+"' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + ""
                                + "</th>");
                        
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' colspan='5' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "EMPAQUE PRIMARIO"
                                + "</th>");
                        out.print("</tr>");

                        Statement stEP = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        String consultaEP = "exec [USP_GET_LISTA_EMPAQUE_PRIMARIO_LOTE_COSTO] '" + lote_produccion + "', " + cod_programa_prod + ", "  + cod_compprod + ",'" + fechaFinal + "',"+cod_tipo_programa_prod;
                        if(nombre_tipo_programa_prod.equals("NO CONSIDERADO")){
                            consultaEP = "exec [USP_GET_LISTA_EMPAQUE_PRIMARIO_LOTE_COSTO_SIN_PROGPROD] '" + lote_produccion + "' " + ",'" + fechaFinal + "',"+cantidad_totalAPT;
                        }
                        ResultSet rsEP = stEP.executeQuery(consultaEP);

                        System.out.println("consulta EP " + consultaEP);
                        double total_cantidadUxItemEP = 0;
                        double total_costoUxItemEP = 0;
                        double total_costoFMEP = 0;
                        double total_costoLoteEP = 0;
                        double total_costoLoteDevEP = 0;
                        while (rsEP.next()) {

                            out.print("<tr>");
                            out.print("<td HEIGHT='30PX' colspan='"+cantidad_datos+"' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;</td>");
       
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'  bgcolor='#D9E1F2' >" + (rsEP.getString("NOMBRE_MATERIAL") == null ? "" : rsEP.getString("NOMBRE_MATERIAL")) + "</td>");
                            double cantidad_unitaria_fm = rsEP.getDouble("cantidad") / rs.getDouble("CANT_LOTE_PRODUCCION");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#D9E1F2' >" + formato.format(cantidad_unitaria_fm) + "</td>");
                            double costo_unitario = rsEP.getDouble("costo_calculado");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#D9E1F2' >" + formato.format(costo_unitario) + "</td>");
                            double costo_unitario_total = rsEP.getDouble("costo_calculado") * cantidad_unitaria_fm;
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#D9E1F2' >" + formato.format(costo_unitario_total) + "</td>");

                            out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + "" + "</td>");

                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + "" + "</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + "" + "</td>");

                            out.print("</tr>");
                            costoTotal = costoTotal + rs.getDouble("cantidad_total");
                            total_cantidadUxItemEP += cantidad_unitaria_fm;
                            total_costoUxItemEP += costo_unitario;
                            total_costoFMEP += costo_unitario_total;
                            total_costoLoteEP += rsEP.getDouble("costo_lote");
                            total_costoLoteDevEP += rsEP.getDouble("costo_lote_devolucion");
                        }
                        out.print("<tr>");
                        out.print("<th HEIGHT='30PX' colspan='"+cantidad_datos+"' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + ""
                                + "</th>");
                        
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "Totales"
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_cantidadUxItemEP)
                                + "</th>");

                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoUxItemEP)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoFMEP)
                                + "</th>");
                        double total_costoAPTEP = cantidad_total_apt * total_costoFMEP;
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoAPTEP)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoLoteEP)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoLoteDevEP)
                                + "</th>");
                        double costoTotalEP = total_costoLoteEP - total_costoLoteDevEP;
                        out.print("<th HEIGHT='30PX' bgcolor='#9BC2E6' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(costoTotalEP)
                                + "</th>");
                        out.print("</tr>");

                        
                        
                        out.print("<tr>");
                        out.print("<th HEIGHT='30PX' colspan='"+cantidad_datos+"' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + ""
                                + "</th>");
                        
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' colspan='5' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "EMPAQUE SECUNDARIO"
                                + "</th>");
                        out.print("</tr>");

                        Statement stES = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        String consultaES = "exec [USP_GET_LISTA_EMPAQUE_SECUNDARIO_LOTE_COSTO] '" + lote_produccion + "', " + cod_programa_prod + ", "  + cod_compprod + ",'" + fechaFinal + "',"+cod_tipo_programa_prod;
                        if(nombre_tipo_programa_prod.equals("NO CONSIDERADO")){
                            consultaES = "exec [USP_GET_LISTA_EMPAQUE_SECUNDARIO_LOTE_COSTO_SIN_PROGPROD] '" + lote_produccion + "' " + ",'" + fechaFinal + "',"+cantidad_totalAPT+","+cod_presentacion;
                        }
                        ResultSet rsES = stES.executeQuery(consultaES);

                        System.out.println("consulta ES " + consultaES);
                        double total_cantidadUxItemES = 0;
                        double total_costoUxItemES = 0;
                        double total_costoFMES = 0;
                        double total_costoLoteES = 0;
                        double total_costoLoteDevES = 0;
                        while (rsES.next()) {

                            out.print("<tr>");
                            out.print("<td HEIGHT='30PX' colspan='"+cantidad_datos+"' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;</td>");
             
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'  bgcolor='#E2EFDA' >" + (rsES.getString("NOMBRE_MATERIAL") == null ? "" : rsES.getString("NOMBRE_MATERIAL")) + "</td>");
                            double cantidad_unitaria_fm = rsES.getDouble("cantidad") / rs.getDouble("CANT_LOTE_PRODUCCION");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#E2EFDA' >" + formato.format(cantidad_unitaria_fm) + "</td>");
                            double costo_unitario = rsES.getDouble("costo_calculado");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#E2EFDA' >" + formato.format(costo_unitario) + "</td>");
                            double costo_unitario_total = rsES.getDouble("costo_calculado") * cantidad_unitaria_fm;
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' bgcolor='#E2EFDA' >" + formato.format(costo_unitario_total) + "</td>");

                            out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + "" + "</td>");

                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + "" + "</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + "" + "</td>");

                            out.print("</tr>");
                            costoTotal = costoTotal + rs.getDouble("cantidad_total");
                            total_cantidadUxItemES += cantidad_unitaria_fm;
                            total_costoUxItemES += costo_unitario;
                            total_costoFMES += costo_unitario_total;
                            total_costoLoteES += rsES.getDouble("costo_lote");
                            total_costoLoteDevES += rsES.getDouble("costo_lote_devolucion");
                        }
                        out.print("<tr>");
                        out.print("<th HEIGHT='30PX' colspan='"+cantidad_datos+"' bgcolor='#cccccc' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + ""
                                + "</th>");
                        
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "Totales"
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_cantidadUxItemES)
                                + "</th>");

                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoUxItemES)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoFMES)
                                + "</th>");
                        double total_costoAPTES = cantidad_total_apt * total_costoFMES;
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoAPTES)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoLoteES)
                                + "</th>");
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(total_costoLoteDevES)
                                + "</th>");
                        double costoTotalES = total_costoLoteES - total_costoLoteDevES;
                        out.print("<th HEIGHT='30PX' bgcolor='#A9D08E' align='right' class='border'  align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;"
                                + formato.format(costoTotalES)
                                + "</th>");
                        out.print("</tr>");

                        out.print("<tr>");

                        
                    }
                    out.print("<tr>");
                    out.print("<th HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '3'  >TOTAL APT</th>");

                    out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + cantidad_totalAPT + "</th>");
                    out.print("</tr>");

                    costoTotal = 0;


                %>
            </table>
            <td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-left:5PX;' >
                <%                } catch (Exception e) {
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
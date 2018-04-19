

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
            <h4 align="center">Reporte Devoluciones por Nro. de Lote</h4>
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
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Nro</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Fecha</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Lote</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Area/Dpto.</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Producto</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Item</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Capítulo</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Grupo</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Unidad</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad Buenos</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad Malos</b></td>
                </tr>


                <%


                String sql_lote = "SELECT cp.nombre_prod_semiterminado,ae.NOMBRE_AREA_EMPRESA,um.ABREVIATURA,m.NOMBRE_MATERIAL,d.COD_DEVOLUCION,";
                sql_lote += " d.NRO_DEVOLUCION,ia.FECHA_INGRESO_ALMACEN,sa.COD_LOTE_PRODUCCION,dde.CANTIDAD_DEVUELTA,dde.CANTIDAD_FALLADOS,c.NOMBRE_CAPITULO,g.NOMBRE_GRUPO";
                sql_lote += " FROM DEVOLUCIONES d";
                sql_lote += " LEFT OUTER JOIN SALIDAS_ALMACEN sa  ON d.COD_SALIDA_ALMACEN = sa.COD_SALIDA_ALMACEN";
                sql_lote += " LEFT OUTER JOIN INGRESOS_ALMACEN ia ON d.COD_DEVOLUCION = ia.COD_DEVOLUCION AND d.COD_ALMACEN = ia.COD_ALMACEN";
                sql_lote += " INNER JOIN DEVOLUCIONES_DETALLE dd  ON dd.COD_DEVOLUCION = d.COD_DEVOLUCION";
                sql_lote += " LEFT OUTER JOIN AREAS_EMPRESA ae    ON sa.COD_AREA_EMPRESA = ae.COD_AREA_EMPRESA";
                sql_lote += " LEFT OUTER JOIN COMPONENTES_PROD cp ON sa.COD_PROD = cp.COD_COMPPROD";
                sql_lote += " INNER JOIN DEVOLUCIONES_DETALLE_ETIQUETAS dde ON dd.COD_DEVOLUCION = dde.COD_DEVOLUCION AND dd.COD_MATERIAL = dde.COD_MATERIAL";
                sql_lote += " INNER JOIN MATERIALES m ON dd.COD_MATERIAL = m.COD_MATERIAL";
                sql_lote += " INNER JOIN UNIDADES_MEDIDA um ON dd.COD_UNIDAD_MEDIDA = um.COD_UNIDAD_MEDIDA";
                sql_lote += " INNER JOIN GRUPOS g ON g.COD_GRUPO = m.COD_GRUPO ";
                sql_lote += " INNER JOIN CAPITULOS c ON c.COD_CAPITULO = g.COD_CAPITULO";
                

                sql_lote += " WHERE  ia.COD_ESTADO_INGRESO_ALMACEN<>2 ";
                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    sql_lote = sql_lote + " and ia.FECHA_INGRESO_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }
                if (!numLoteP.equals("")) {
                    sql_lote = sql_lote + " and sa.COD_LOTE_PRODUCCION like '" + numLoteP + "%' ";
                }
                if (!codProductoP.equals("") && !codProductoP.equals("0")) {
                    sql_lote = sql_lote + " and sa.COD_PROD = " + codProductoP + " ";
                }
                
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    sql_lote = sql_lote + " and sa.COD_ALMACEN = '" + codAlmacen + "' ";
                }

                sql_lote += " ORDER BY d.NRO_DEVOLUCION,m.NOMBRE_MATERIAL";

                System.out.println("sql_lote: " + sql_lote);

                con = Util.openConnection(con);
                Statement st_lote = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_lote = st_lote.executeQuery(sql_lote);

                while (rs_lote.next()) {
                    String nombreProdSemiterminado = rs_lote.getString(1);
                    String nombreAreaEmpresa = rs_lote.getString(2);
                    String abreviatura = rs_lote.getString(3);
                    String nombreMaterial = rs_lote.getString(4);
                    int nroDevolucion = rs_lote.getInt(6);
                    String fechaDevolucion = format.format(rs_lote.getDate(7)) + " &nbsp; " + format1.format(rs_lote.getTimestamp(7).getTime());
                    String codLoteProduccion = rs_lote.getString(8);
                    double cantidadDevuelta = rs_lote.getDouble(9);
                    double cantidadFallados = rs_lote.getDouble(10);
                    String nombreCapitulo = rs_lote.getString(11);
                    String nombreGrupo = rs_lote.getString(12);

                    out.print("<tr>");
                    out.print("<td HEIGHT='25PX' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + nroDevolucion + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + fechaDevolucion + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + codLoteProduccion + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + nombreAreaEmpresa + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + nombreProdSemiterminado + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + nombreMaterial + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + nombreCapitulo + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + nombreGrupo + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + abreviatura + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(cantidadDevuelta) + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + formato.format(cantidadFallados) + "</td>");
                    out.print("</tr>");
                }

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
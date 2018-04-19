package reportes.reporteIngresosAlmacen.reporteIngresosAlmacenPorItem;

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
            <h4 align="center">Reporte de Ingresos y Compras Por Item</h4>

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
            formato.applyPattern("#,##0.00;(#,##0.00)");

            NumberFormat nf1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato1 = (DecimalFormat) nf1;
            formato1.applyPattern("#.#####;(#.#####)");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");

            con = Util.openConnection(con);




            System.out.println("fec11:" + request.getParameter("fechaIni"));
            System.out.println("fec12:" + request.getParameter("fechaFin"));
            String fechaInicial = "";
            if (request.getParameter("fechaIni").equals("0")) {
                System.out.println("1:");
                fechaInicial = "";
            } else {
                System.out.println("12:");
                fechaInicial = request.getParameter("fechaIni");
            }
            String fechaFinal = "";
            if (request.getParameter("fechaFin").equals("0")) {
                System.out.println("13:");
                fechaFinal = "";
            } else {
                System.out.println("14:");
                fechaFinal = request.getParameter("fechaFin");
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

            System.out.println("material: " + codMaterial);

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
                                    <td><b>Capitulo</b></td>
                                    <td><%=nombreCapitulo%></td>
                                </tr>
                                <tr>
                                    <td><b>Grupo</b></td>
                                    <td><%=nombreGrupo%></td>
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

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Nro</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Item</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Fecha Ingreso</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Fecha Compra</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Nro OC</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Tipo Ingreso</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Tipo Compra</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Proveedor</b></td>
                    <%--td style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Grupo</b></td--%>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unid.</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Costo Unitario (Bs.)</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Precio Compra (Bs.)</b></td>
                </tr>

                <%
            String sqlMateriales = "select cod_material, nombre_material, " +
                                       " (select u.NOMBRE_UNIDAD_MEDIDA from UNIDADES_MEDIDA u where u.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA_COMPRA) " +
                                       " from materiales m where m.cod_material in (" + codMaterial + ")";
            Statement stMateriales = con.createStatement();
            ResultSet rsMateriales = stMateriales.executeQuery(sqlMateriales);
            while (rsMateriales.next()) {
                String codMaterialReporte = rsMateriales.getString(1);
                String nombreMaterialReporte = rsMateriales.getString(2);
                String unidadMedidaCompra=rsMateriales.getString(3);

                String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.ABREVIATURA,ia.COD_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN," +
                        " ia.FECHA_INGRESO_ALMACEN,isnull(oc.NRO_ORDEN_COMPRA,0)NRO_ORDEN_COMPRA,t.NOMBRE_TIPO_INGRESO_ALMACEN, " +
                        " p.NOMBRE_PROVEEDOR,iad.CANT_TOTAL_INGRESO_FISICO,iad.COSTO_UNITARIO,g.NOMBRE_GRUPO,c.NOMBRE_CAPITULO,u.ABREVIATURA, " +
                        " iad.COSTO_UNITARIO*iad.CANT_TOTAL_INGRESO_FISICO costo_total, m.COD_UNIDAD_MEDIDA_COMPRA, m.COD_UNIDAD_MEDIDA " +
                        " from INGRESOS_ALMACEN ia " +
                        " inner join TIPOS_INGRESO_ALMACEN t on ia.COD_TIPO_INGRESO_ALMACEN = t.COD_TIPO_INGRESO_ALMACEN " +
                        " left outer join PROVEEDORES p on p.COD_PROVEEDOR = ia.COD_PROVEEDOR " +
                        " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN " +
                        " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA " +
                        " inner join MATERIALES m on m.COD_MATERIAL = iad.COD_MATERIAL " +
                        " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO " +
                        " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO" +
                        " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                        " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL " +
                        " left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA " +
                        " where ia.COD_ESTADO_INGRESO_ALMACEN<>2 and 0=0 ";
                if (!codMaterial.equals("0")) {
                    consulta = consulta + " and m.cod_material = '" + codMaterialReporte + "' ";
                }
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
                consulta = consulta + " order by ia.FECHA_INGRESO_ALMACEN asc";



                System.out.println("consulta 1 " + consulta);

                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                double costoTotal1 = 0;
                while (rs.next()) {
                    String codMaterialIngreso = rs.getString(1);
                    String codIngresoRep = rs.getString(4);
                    int codUnidadMedidaCompra = rs.getInt("COD_UNIDAD_MEDIDA_COMPRA");
                    int codUnidadMedida = rs.getInt("COD_UNIDAD_MEDIDA");
                    double cantidadIngreso = rs.getDouble("CANT_TOTAL_INGRESO_FISICO");

                    System.out.println("cant ingreso: " + cantidadIngreso);

                    String sqlEqui = "select e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e where e.COD_UNIDAD_MEDIDA=" + codUnidadMedidaCompra + " " +
                            " and e.COD_UNIDAD_MEDIDA2=" + codUnidadMedida;
                    System.out.println("sql equi: " + sqlEqui);
                    Statement stEqui = con.createStatement();
                    ResultSet rsEqui = stEqui.executeQuery(sqlEqui);
                    double factorEquivalencia = 0d;
                    if (rsEqui.next()) {
                        factorEquivalencia = rsEqui.getDouble(1);
                        System.out.println("factor equi: " + factorEquivalencia);
                    }

                    double cantidadConvertCompra = cantidadIngreso / factorEquivalencia;

                    double precioCompra = 0d;
                    double precioCompraBs = 0d;
                    String fechaCompra = "";
                    String codMoneda = "";
                    String nombreTipoCompra = "";
                    if (!rs.getString("NRO_ORDEN_COMPRA").equals("0")) {
                        String sqlOC = "select od.CANTIDAD_NETA, od.PRECIO_UNITARIO, od.COD_UNIDAD_MEDIDA, convert(varchar, FECHA_EMISION, 103), " +
                                " COD_MONEDA, " +
                                " (select tc.nombre_tipo_compra from tipos_compra tc where tc.cod_tipo_compra=o.cod_tipo_compra)tipoCompra" +
                                " from ORDENES_COMPRA o, ORDENES_COMPRA_DETALLE od, INGRESOS_ALMACEN i where  " +
                                " o.COD_ORDEN_COMPRA=od.COD_ORDEN_COMPRA and o.COD_ORDEN_COMPRA=i.COD_ORDEN_COMPRA and " +
                                " i.COD_INGRESO_ALMACEN=" + codIngresoRep + " and od.COD_MATERIAL=" + codMaterialIngreso + "";
                        Statement stOC = con.createStatement();
                        ResultSet rsOC = stOC.executeQuery(sqlOC);
                        if (rsOC.next()) {
                            precioCompra = rsOC.getDouble(2);
                            fechaCompra = rsOC.getString(4);
                            codMoneda = rsOC.getString(5);
                            nombreTipoCompra = rsOC.getString(6);

                            precioCompraBs = precioCompra;
                            if (codMoneda.equals("2")) {
                                precioCompraBs = precioCompra * 6.96;
                            }
                            if (codMoneda.equals("3")) {
                                precioCompraBs = precioCompra * 8.50571774;
                            }
                        }
                    }
                    System.out.println("despues de oc.");

                    out.print("<tr>");
                    out.print("<td HEIGHT='30PX'  align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("NRO_INGRESO_ALMACEN") == null ? "" : rs.getString("NRO_INGRESO_ALMACEN")) + "</td>");
                    out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + nombreMaterialReporte + "</td>");
                    out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + format.format(rs.getDate("FECHA_INGRESO_ALMACEN")) + "</td>");
                    out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + fechaCompra + "</td>");
                    out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + (rs.getString("NRO_ORDEN_COMPRA") == null ? "" : rs.getString("NRO_ORDEN_COMPRA")) + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + (rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN") == null ? "" : rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")) + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + nombreTipoCompra + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + (rs.getString("NOMBRE_PROVEEDOR") == null ? "" : rs.getString("NOMBRE_PROVEEDOR")) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(cantidadConvertCompra)) + "</td>");
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + unidadMedidaCompra  + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato1.format(rs.getDouble("COSTO_UNITARIO") * factorEquivalencia)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(precioCompra)) + "</td>");
                    out.print("</tr>");
                    costoTotal = costoTotal + rs.getDouble("CANT_TOTAL_INGRESO_FISICO");
                    costoTotal1 = costoTotal1 + rs.getDouble("costo_total");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
                %>
            </table>
            <br><br>
            <br>

            <br>
            <div align="center">
                <%--<INPUT type="button" class="commandButton" name="btn_registrar" value="<-- Atrás" onClick="cancelar();"  >--%>

            </div>
        </form>
    </body>
</html>

cdcd
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
            <h4 align="center">Reporte Lotes de Proveedor</h4>

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



            /*String fechaInicial=request.getParameter("fecha1")==null?"01/01/2011":request.getParameter("fecha1");
            String fechaInicial1 = fechaInicial;
            String arrayfecha1[]=fechaInicial.split("/");
            fechaInicial=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];

            String fechaFinal=request.getParameter("fecha2")==null?"01/01/2011":request.getParameter("fecha2");
            String fechaFinal1 = fechaFinal;
            String arrayfecha2[]=fechaFinal.split("/");
            fechaFinal=arrayfecha2[2] +"/"+ arrayfecha2[1]+"/"+arrayfecha2[0];*/
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
            String codProveedor = request.getParameter("codProveedorArray") == null ? "''" : request.getParameter("codProveedorArray");
            String codTipoIngresoAlmacen = request.getParameter("codTipoIngresoAlmacen") == null ? "''" : request.getParameter("codTipoIngresoAlmacen");
            String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");

            System.out.println("parametros" + codProveedor + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " " +
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
                      
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>

            <BR>
            <BR>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Item</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Lote </b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Lote Interno</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>F. Vencimiento</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Proveedor</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Estado</b></td>
                    


                </tr>

                <%
                SimpleDateFormat sdfMMyyyy =new SimpleDateFormat("MM/yyyy");
                String consulta = " select sum(iade.CANTIDAD_RESTANTE)as cantidad ,iad.cod_material, ia.COD_PROVEEDOR,";
                consulta = consulta + " isnull(iade.LOTE_MATERIAL_PROVEEDOR,'-')as LOTE_MATERIAL_PROVEEDOR,iade.FECHA_VENCIMIENTO, iade.COD_ESTADO_MATERIAL,p.NOMBRE_PROVEEDOR,e.NOMBRE_ESTADO_MATERIAL,m.NOMBRE_MATERIAL" +
                                      ",isnull(ocd.LOTE_INTERNO,'') as LOTE_INTERNO";
                consulta = consulta + " from INGRESOS_ALMACEN ia";
                consulta = consulta + " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN";
                consulta = consulta + " inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN";                
                consulta = consulta + " inner join MATERIALES m on m.COD_MATERIAL = iad.COD_MATERIAL and m.COD_MATERIAL = iade.COD_MATERIAL";
                consulta = consulta + " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL";
                consulta = consulta + " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO";
                consulta = consulta + " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO";
                consulta = consulta + " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN";
                consulta = consulta + " left outer join proveedores p on p.COD_PROVEEDOR=ia.COD_PROVEEDOR"+
                                      " left outer join ORDENES_COMPRA_DETALLE ocd on ocd.COD_ORDEN_COMPRA="+
                                      " ia.COD_ORDEN_COMPRA and iad.COD_MATERIAL=iade.COD_MATERIAL";
                consulta = consulta + " where ia.cod_estado_ingreso_almacen=1 ";
                consulta = consulta + " and iade.CANTIDAD_RESTANTE>0 and m.material_almacen=1";


                if (!nombreMaterialP.equals("")) {
                    consulta = consulta + " and m.nombre_material like '" + nombreMaterialP + "%' ";
                }
                /*if (!codFilial.equals("") && !codFilial.equals("0")) {
                    consulta = consulta + " and f.cod_filial = '" + codFilial + "' ";
                }*/
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    consulta = consulta + " and ia.COD_ALMACEN = '" + codAlmacen + "' ";
                }
               /* if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    consulta = consulta + " and ia.FECHA_INGRESO_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }*/
                if (!codProveedor.equals("") && !codProveedor.equals("0")) {
                    consulta = consulta + " and ia.COD_PROVEEDOR = '" + codProveedor + "' ";
                }
                if (!codCapitulo.equals("") && !codCapitulo.equals("0")) {
                    consulta = consulta + " and c.COD_CAPITULO in (" + codCapitulo + ") ";
                }
                if (!codGrupo.equals("") && !codGrupo.equals("0")) {
                    consulta = consulta + " and g.COD_GRUPO in (" + codGrupo + ") ";
                }
                consulta = consulta + " GROUP BY iad.cod_material,ocd.LOTE_INTERNO,ia.COD_PROVEEDOR,iade.LOTE_MATERIAL_PROVEEDOR,iade.FECHA_VENCIMIENTO, iade.COD_ESTADO_MATERIAL,p.NOMBRE_PROVEEDOR,e.NOMBRE_ESTADO_MATERIAL,m.NOMBRE_MATERIAL order by m.NOMBRE_MATERIAL asc";

                System.out.println("consulta 1 " + consulta);

                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                int cod_material=0;
                int cod_material_ant=0;
                int c=0;
                while (rs.next()) {

                    if(rs.getInt("cod_material")!=cod_material_ant){
                        if(c>0){
                            out.print("<tr>");
                            out.print("<th HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '2'  >TOTAL&nbsp;</th>");

                            out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(costoTotal)) + "</th>");
                            out.print("</tr>");
                            costoTotal=0;
                        }
                        out.print("<tr>");
                            out.print("<th HEIGHT='30PX' bgcolor='#cccccc' class='border' colspan='7' align='left'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;" + (rs.getString("NOMBRE_MATERIAL") == null ? "" : rs.getString("NOMBRE_MATERIAL")) + "</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("LOTE_MATERIAL_PROVEEDOR") == null ? "" : rs.getString("LOTE_MATERIAL_PROVEEDOR")) + "</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("LOTE_INTERNO")) + "&nbsp;</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("cantidad") == null ? "" : formato.format(rs.getDouble("cantidad"))) + "</td>");

                            out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" +(rs.getDate("FECHA_VENCIMIENTO")!=null?(sdfMMyyyy.format(rs.getDate("FECHA_VENCIMIENTO"))):"&nbsp;") + "</td>");

                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("NOMBRE_PROVEEDOR") == null ? "" : rs.getString("NOMBRE_PROVEEDOR")) + "</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("NOMBRE_ESTADO_MATERIAL") == null ? "" : rs.getString("NOMBRE_ESTADO_MATERIAL")) + "</td>");

                        out.print("</tr>");
                        costoTotal = costoTotal + rs.getDouble("cantidad");
                        
                        
                    }else{
                        out.print("<tr>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >&nbsp;</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("LOTE_MATERIAL_PROVEEDOR") == null ? "" : rs.getString("LOTE_MATERIAL_PROVEEDOR")) + "</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("LOTE_INTERNO")) + "&nbsp;</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("cantidad") == null ? "" : formato.format(rs.getDouble("cantidad"))) + "</td>");

                            out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" +(rs.getDate("FECHA_VENCIMIENTO")!=null?(sdfMMyyyy.format(rs.getDate("FECHA_VENCIMIENTO"))):"&nbsp;") + "</td>");

                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("NOMBRE_PROVEEDOR") == null ? "" : rs.getString("NOMBRE_PROVEEDOR")) + "</td>");
                            out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("NOMBRE_ESTADO_MATERIAL") == null ? "" : rs.getString("NOMBRE_ESTADO_MATERIAL")) + "</td>");

                        out.print("</tr>");
                         costoTotal = costoTotal + rs.getDouble("cantidad");
                    }
                   
                    
                   
                    cod_material_ant=rs.getInt("cod_material");
                    c++;
                }
                 out.print("<tr>");
                            out.print("<th HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '2'  >TOTAL&nbsp;</th>");

                            out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(costoTotal)) + "</th>");
                            out.print("</tr>");
                            costoTotal=0;





                %>
            </table>
            <td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-left:5PX;' >
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
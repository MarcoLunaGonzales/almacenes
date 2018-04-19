
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
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Existencias Por Estado</h4>

            <%
        try {

            /*
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="codCompProd">
            <input type="hidden" name="nombreCompProd">
             */



            NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("####.##;(####.##)");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            con = Util.openConnection(con);



            String fecha = request.getParameter("fecha") == null ? "01/01/2011" : request.getParameter("fecha");
            String arrayfecha[] = fecha.split("/");
            fecha = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            //codMaterial = codMaterial.substring(1);
            String codGrupo = request.getParameter("codGrupo") == null ? "''" : request.getParameter("codGrupo");
            String codCapitulo = request.getParameter("codCapitulo") == null ? "''" : request.getParameter("codCapitulo");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");
            String estadoMaterial = request.getParameter("codCapituloEstadoMaterial") == null ? "''" : request.getParameter("codCapituloEstadoMaterial");
            String nombreEstado = request.getParameter("nombreEstado") == null ? "''" : request.getParameter("nombreEstado");
            System.out.println("estadoMaterial:" + estadoMaterial);
            System.out.println("nombreEstado:" + nombreEstado);



            System.out.println("parametros" + fecha + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " " +
                    "" + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);




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



            <table width="70%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <th height="30PX" class="border" style="border : solid #D8D8D8 1px;padding:8px" bgcolor="#f2f2f2"  align="center" ><b>Cod</b></th>
                    <th  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Item</b></th>
                    <th  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unidades</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b><%=nombreEstado%></b></th>
                    <%--td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Cuarentena</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Rechazado</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Obsoleto</b></td>
                    <td style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Vencido</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Total Malos</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Total Buenos</b></td--%>


                </tr>

                <%
                //String sql_mat = "select cod_material,nombre_material,CODIGO_ANTIGUO from materiales where cod_material in (" + codMaterial + ")";
                String sql_mat = "select m.cod_material,m.nombre_material,m.CODIGO_ANTIGUO,u.ABREVIATURA from materiales m , UNIDADES_MEDIDA u where m.cod_material in (" + codMaterial + ") and u.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA";
                System.out.println("sql_mat:" + sql_mat);
                con = Util.openConnection(con);
                Statement st_mat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_mat = st_mat.executeQuery(sql_mat);
                double cantidad=0d;
                while (rs_mat.next()) {

                    
                    String consulta="select ISNULL(sum(id.CANTIDAD_RESTANTE), 0) cantidad_r from INGRESOS_ALMACEN i"+
                                    " inner join INGRESOS_ALMACEN_DETALLE_ESTADO id on id.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN"+
                                    " and i.COD_ESTADO_INGRESO_ALMACEN = 1 and i.fecha_ingreso_almacen <= '"+fecha+" 23:59:59'"+
                                    " where id.COD_MATERIAL ='"+rs_mat.getString("cod_material")+"' and id.CANTIDAD_RESTANTE > 0 and id.COD_ESTADO_MATERIAL = '"+estadoMaterial+"' " +
                                    " and i.COD_ALMACEN = '"+codAlmacen+"'";
                    Statement stdetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    System.out.println("consulta cantidad "+consulta);
                    ResultSet res=stdetalle.executeQuery(consulta);
                    if(res.next())
                    {
                        cantidad=res.getDouble("cantidad_r");
                    }

                        out.print("<tr>");
                        out.print("<td height='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;PADDING:8PX' >" + (rs_mat.getString("CODIGO_ANTIGUO") == null ? "" : rs_mat.getString("CODIGO_ANTIGUO")) + "</td>");
                        out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >" + rs_mat.getString("nombre_material") + "</td>");
                        out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >" + rs_mat.getString("ABREVIATURA") + "</td>");
                        
                        out.print("<td align='right' style='border : solid #D8D8D8 1px;PADDING:8PX' class='border'   >" + formato.format(cantidad) + "</td>");

                    res.close();
                    stdetalle.close();

                }

                %>
            </table>

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
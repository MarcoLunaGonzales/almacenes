
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
            <h4 align="center">Reporte de Existencias Por Ubicación</h4>

            <%
            Connection con = null;
            try {
                NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat formato = (DecimalFormat) numeroformato;
                formato.applyPattern("#,##0.00;(#,##0.00)");

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                con = Util.openConnection(con);
                int codCapitulo = Integer.valueOf(request.getParameter("codCapitulo"));
                int codGrupo = Integer.valueOf(request.getParameter("codGrupo"));
                String nombreMaterial = request.getParameter("nombreMaterial").trim();
                String ubicacion = request.getParameter("ubicacion") == null ? "''" : request.getParameter("ubicacion");
                String estante = request.getParameter("estante") == null ? "''" : request.getParameter("estante");
                String codPasillo = request.getParameter("codPasillo") == null ? "''" : request.getParameter("codPasillo");
                Integer codAmbiente = Integer.valueOf(request.getParameter("codAmbiente"));
                Integer codAlmacen = Integer.valueOf(request.getParameter("codAlmacen"));
                String nombreAlmacen = "";
                String nombreAmbiente ="--TODOS--";
                String nombreEstante ="--TODOS--";
                String nombreCapitulo = "--TODOS--";
                String nombreGrupo = "--TODOS--";
                String consulta = "select a.NOMBRE_ALMACEN"+
                                " from almacenes a where a.COD_ALMACEN ="+codAlmacen;
                Statement st = con.createStatement();
                ResultSet res = st.executeQuery(consulta);
                if(res.next())nombreAlmacen = res.getString("NOMBRE_ALMACEN");
                consulta = "select aa.NOMBRE_AMBIENTE"+
                            " from AMBIENTE_ALMACEN aa "+
                            " where aa.COD_AMBIENTE = "+codAmbiente;
                res = st.executeQuery(consulta);
                if(res.next())nombreAmbiente = res.getString("NOMBRE_AMBIENTE");
                consulta = "select ea.NOMBRE_ESTANTE"+
                            " from ESTANTE_AMBIENTE ea "+
                            " where ea.COD_ESTANTE = '"+codPasillo+"'";
                res = st.executeQuery(consulta);
                if(res.next())nombreEstante = res.getString("NOMBRE_ESTANTE");
                consulta = "select c.NOMBRE_CAPITULO"+
                            " from capitulos c"+
                            " where c.COD_CAPITULO = "+codCapitulo;
                res = st.executeQuery(consulta);
                if(res.next())nombreCapitulo = res.getString("NOMBRE_CAPITULO");
                consulta = "SELECT g.NOMBRE_GRUPO"+
                            " FROM GRUPOS g  "+
                            " where g.COD_GRUPO = "+codGrupo;
                res = st.executeQuery(consulta);
                if(res.next())nombreGrupo = res.getString("NOMBRE_GRUPO");
            %>
            <br>
            <table width="0%" border="0" align="center">
                <tr>
                    <td>
                        <img src="../../img/cofar.png" alt="logo cofar" align="left" />
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <table>
                            <tr>
                                <td class="outputTextBold">Almacen</td>
                                <td class="outputTextBold">::</td>
                                <td class="outputText2"><%=(nombreAlmacen)%></td>
                            </tr>
                            <tr>
                                <td class="outputTextBold">Ambiente</td>
                                <td class="outputTextBold">::</td>
                                <td class="outputText2"><%=(nombreAmbiente)%></td>
                            </tr>
                            <tr>
                                <td class="outputTextBold">Pasillo</td>
                                <td class="outputTextBold">::</td>
                                <td class="outputText2"><%=(nombreEstante)%></td>
                            </tr>
                            <tr>
                                <td class="outputTextBold">Estante</td>
                                <td class="outputTextBold">::</td>
                                <td class="outputText2"><%=(estante)%></td>
                            </tr>
                            <tr>
                                <td class="outputTextBold">Ubicación</td>
                                <td class="outputTextBold">::</td>
                                <td class="outputText2"><%=(ubicacion)%></td>
                            </tr>
                            <tr>
                                <td class="outputTextBold">Capitulo</td>
                                <td class="outputTextBold">::</td>
                                <td class="outputText2"><%=(nombreCapitulo)%></td>
                            </tr>
                            <tr>
                                <td class="outputTextBold">Grupo</td>
                                <td class="outputTextBold">::</td>
                                <td class="outputText2"><%=(nombreGrupo)%></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>



            <table width="90%" align="center" class="outputText2" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <th height="30PX" class="border" style="border : solid #D8D8D8 1px;padding:8px" bgcolor="#f2f2f2"  align="center" ><b>Ambiente</b></th>
                    <th  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Pasillo</b></th>
                    <th  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Estante</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Ubicacion</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Capitulo</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Grupo</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Material</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Estado</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Lote Proveedor</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Fecha Vencimiento</b></th>
                    <th  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b></b></th><%--=nombreEstado--%>
                </tr>

                <%
                
                    
                             consulta = " select  *"+
                                        " from VISTA_INGRESOS_ALMACEN_UBICACION_ESTADO viau " +
                                        " where 1=1"+
                                                (codAlmacen > 0 ? "and viau.COD_ALMACEN = "+codAlmacen : "")+
                                                (codAmbiente > 0?" and viau.COD_AMBIENTE = '"+codAmbiente+"'":"") +
                                                (codAmbiente < 0 ? " and (viau.COD_AMBIENTE = 0 or viau.COD_AMBIENTE is null)":"")+
                                                (!codPasillo.equals("-1")?" and viau.COD_ESTANTE = '"+codPasillo+"'":"") +
                                                (!estante.trim().equals("")?" and viau.FILA = '"+estante+"'":"") +
                                                (!ubicacion.trim().equals("")?" and viau.COLUMNA = '"+ubicacion+"'":"") +
                                                (codCapitulo > 0 ? " AND viau.cod_CAPITULO = "+codCapitulo : "")+
                                                (codGrupo > 0 ? " AND viau.COD_GRUPO = "+codGrupo : "")+
                                                " and viau.NOMBRE_MATERIAL LIKE ?"+
                                        " order by  viau.nombre_ambiente,viau.NOMBRE_ESTANTE,viau.FILA,viau.COLUMNA,viau.nombre_estado_material,viau.nombre_material ";
                    System.out.println("consulta "  + consulta);
                    PreparedStatement pst = con.prepareStatement(consulta);
                    pst.setString(1,"%"+nombreMaterial+"%");System.out.println("p1: "+nombreMaterial);
                    ResultSet rs = pst.executeQuery();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfMMyyyy = new SimpleDateFormat("MM/yyyy");
                    while (rs.next()) {
                            out.print("<tr>");
                                out.print("<td height='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;PADDING:8PX' >&nbsp;" + rs.getString("nombre_ambiente") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >&nbsp;" + rs.getString("nombre_Estante") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >&nbsp;" + rs.getString("fila") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >&nbsp;" + rs.getString("columna") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='left'  >" + rs.getString("NOMBRE_CAPITULO") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='left'  >" + rs.getString("NOMBRE_GRUPO") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='left'  >" + rs.getString("nombre_material") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >&nbsp;" + formato.format(rs.getDouble("cantidad_restante")) + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >&nbsp;" + rs.getString("nombre_estado_material") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >&nbsp;" + rs.getString("LOTE_MATERIAL_PROVEEDOR") + "</td>");
                                out.print("<td  class='border' style='border : solid #D8D8D8 1px;PADDING:8PX'  align='right'  >&nbsp;" + sdfMMyyyy.format(rs.getTimestamp("FECHA_VENCIMIENTO")) + "</td>");
                            out.println("</tr>");
                    }

                %>
            </table>

            <%



            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                con.close();
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

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
        <script type="text/javascript">
            function cambiarUbicacionMaterial()
            {
                
            }
        </script>
        <style>
            .tablaDetalle
            {
                border-top:1px solid #D8D8D8;
                border-right:1px solid #D8D8D8;
            }
            .tablaDetalle tr td
            {
                border-bottom:1px solid #D8D8D8;
                border-left:1px solid #D8D8D8;
                padding:0.2em;
            }
        </style>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Existencias Por Ubicacion</h4>

            <%
        try {

            /*
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="codCompProd">
            <input type="hidden" name="nombreCompProd">
             */


             Connection con=null;
            NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("####.##;(####.##)");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            con = Util.openConnection(con);



            String fecha = request.getParameter("fecha") == null ? "01/01/2011" : request.getParameter("fecha");
            String arrayfecha[] = fecha.split("/");
            fecha = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
            
            //codMaterial = codMaterial.substring(1);
            String ubicacion = request.getParameter("ubicacion") == null ? "''" : request.getParameter("ubicacion");
            String estante = request.getParameter("estante") == null ? "''" : request.getParameter("estante");
        String codPasillo = request.getParameter("codPasillo") == null ? "''" : request.getParameter("codPasillo");
        String codAmbiente = request.getParameter("codAmbiente") == null ? "''" : request.getParameter("codAmbiente");
        String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
        String codFilial = request.getParameter("codFilial") == null ? "0" : request.getParameter("codFilial");
          



            %>

            <br>
            <table widtd="70%" border="0" align="center">
                <tr>
                    <td>
                        <img src="../img/cofar.png" alt="logo cofar" align="left" />
                    </td><td>
                        
                    </td>
                </tr>
            </table>



            <table widtd="70%" align="center" class="tablaDetalle"  cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td height="30PX" class="border" style="border : solid #D8D8D8 1px;padding:8px" bgcolor="#f2f2f2"  align="center" ><b>Ambiente</b></td>
                    <td  class="border"  bgcolor="#f2f2f2"  align="center" ><b>Pasillo</b></td>
                    <td  class="border"  bgcolor="#f2f2f2"  align="center"><b>Estante</b></td>
                    <td   class="border"  bgcolor="#f2f2f2"  align="center"><b>Ubicacion</b></td>
                    <td   class="border"  bgcolor="#f2f2f2"  align="center"><b>Material</b></td>
                    <td   class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
                    <td   class="border"  bgcolor="#f2f2f2"  align="center"><b>Estado</b></td>
                    <td   class="border"  bgcolor="#f2f2f2"  align="center"><b>Cambiar Ubicación</b></td>
                </tr>

                <%
                
                con = Util.openConnection(con);
                Statement st = con.createStatement();
                String consulta = " select  a.NOMBRE_AMBIENTE,e.NOMBRE_ESTANTE,i.FILA,i.COLUMNA,m.NOMBRE_MATERIAL,sum(i.CANTIDAD_RESTANTE) cantidad_restante,es.nombre_estado_material" +
                        " from INGRESOS_ALMACEN_DETALLE_ESTADO i " +
                        " inner join ESTANTE_AMBIENTE e on e.COD_ESTANTE = i.COD_ESTANTE" +
                        " inner join AMBIENTE_ALMACEN a on a.COD_AMBIENTE = e.COD_AMBIENTE" +
                        " inner join materiales m on m.COD_MATERIAL = i.COD_MATERIAL" +
                        " inner join estados_material es on es.cod_estado_material = i.cod_estado_material" +
                        " where i.CANTIDAD_RESTANTE>0.1" +
                        (!codAmbiente.equals("-1")?" and a.COD_AMBIENTE = '"+codAmbiente+"'":"") +
                        (!codPasillo.equals("-1")?" and e.COD_ESTANTE = '"+codPasillo+"'":"") +
                        (!estante.trim().equals("")?" and i.FILA = '"+estante+"'":"") +
                        (!ubicacion.trim().equals("")?" and i.COLUMNA = '"+ubicacion+"'":"") +
                        " group by a.nombre_ambiente,e.NOMBRE_ESTANTE,i.FILA,i.COLUMNA,es.nombre_estado_material,m.nombre_material" +
                        " order by  a.nombre_ambiente,e.NOMBRE_ESTANTE,i.FILA,i.COLUMNA,es.nombre_estado_material,m.nombre_material ";
                System.out.println("consulta "  + consulta);
                ResultSet rs = st.executeQuery(consulta);

               
                while (rs.next()) {
                  
                     
                        out.print("<tr>");
                        out.print("<td><span class='outputText2'>&nbsp;" + rs.getString("nombre_ambiente") + "</span></td>");
                        out.print("<td align='right'  ><span class='outputText2'>" + rs.getString("nombre_Estante") + "</span></td>");
                        out.print("<td    align='right'  >&nbsp;" + rs.getString("fila") + "</td>");
                        out.print("<td    align='right'  >&nbsp;" + rs.getString("columna") + "</td>");
                        out.print("<td    align='right'  >&nbsp;" + rs.getString("nombre_material") + "</td>");
                        out.print("<td    align='right'  ><span class='outputText2'>&nbsp;" + formato.format(rs.getDouble("cantidad_restante")) + "</span></td>");
                        out.print("<td    align='right'  >&nbsp;" + rs.getString("nombre_estado_material") + "</td>");
                        out.print("<td    align='right'  ><a href='#'>Cambiar Ubicación</a></td>");
                        
                      
                

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
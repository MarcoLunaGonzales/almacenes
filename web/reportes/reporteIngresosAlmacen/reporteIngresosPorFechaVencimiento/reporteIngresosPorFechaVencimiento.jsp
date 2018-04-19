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
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../../js/general.js"></script>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Ingresos Por Fecha de Vencimiento</h4>

            <%
        try {
            NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("#,##0.00;(#,##0.00)");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Connection con=null;
            con = Util.openConnection(con);
            String fechaF = request.getParameter("fechaF") == null ? "01/01/2011" : request.getParameter("fechaF");

            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            String codGrupo = request.getParameter("codGrupo") == null ? "''" : request.getParameter("codGrupo");
            String codCapitulo = request.getParameter("codCapitulo") == null ? "''" : request.getParameter("codCapitulo");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");
            System.out.println("parametros " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " " +
                    "" + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);




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
                                    <td><b>Fecha Final : </b></td>
                                    <td><%=fechaF%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>

<br>
<br>

            <table width="100%" align="center" class="outputText0" style="border : solid #cccccc 1px;" cellpadding="1" cellspacing="0" >


                

                <%
                    SimpleDateFormat sdfMMyyyy =new SimpleDateFormat("MM/yyyy");
                out.println("<tr bgcolor='#cccccc'>"+
                    "<td   style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;' bgcolor='#cccccc'  align='center' ><b>Nro</b></td>"+
                    "<td  style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;' bgcolor='#cccccc'  align='center' ><b>Fecha Ingreso</b></td>"+
                    "<td  style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;' bgcolor='#cccccc'  align='center'><b>Item</b></td>"+
                    "<td  style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;'   bgcolor='#cccccc'  align='center'><b>Proveedor</b></td>"+
                    "<td  style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;' bgcolor='#cccccc'  align='center'><b>Cantidad P.</b></td>"+
                    "<td  style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;' bgcolor='#cccccc'  align='center'><b>Cantidad Res.</b></td>"+
                    "<td  style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;'   bgcolor='#cccccc'  align='center'><b>Unid.</b></td>"+
                    "<td  style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;'  bgcolor='#cccccc'  align='center'><b>Fecha Ven.</b></td>"+
                    "<td  style='border-bottom : solid #f2f2f2 1px;padding:5px;border-left : solid #f2f2f2 1px;' bgcolor='#cccccc'  align='center'><b>Lote</b></td>"+

                "</tr>");
                String arrayfechaF[] = fechaF.split("/");
                String fechaVenc = arrayfechaF[2] + "/" + arrayfechaF[1] + "/" + arrayfechaF[0];

                String consulta = " SELECT ia.NRO_INGRESO_ALMACEN,ia.FECHA_INGRESO_ALMACEN,m.NOMBRE_MATERIAL,p.NOMBRE_PROVEEDOR,ISNULL(sum(iade.CANTIDAD_PARCIAL),0) as cantidadP,"+
                                  " ISNULL(sum(iade.CANTIDAD_RESTANTE),0) as cantidadRestante,um.ABREVIATURA,iade.FECHA_VENCIMIENTO, iade.LOTE_MATERIAL_PROVEEDOR"+
                                  " FROM   INGRESOS_ALMACEN ia LEFT OUTER JOIN INGRESOS_ALMACEN_DETALLE iad  ON ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN "+
                                  " LEFT OUTER JOIN PROVEEDORES p on p.COD_PROVEEDOR=ia.COD_PROVEEDOR LEFT OUTER JOIN "+
                                  " UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=iad.COD_UNIDAD_MEDIDA"+
                                  " LEFT OUTER JOIN INGRESOS_ALMACEN_DETALLE_ESTADO iade ON iad.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN AND iad.COD_MATERIAL=iade.COD_MATERIAL "+
                                  " LEFT OUTER JOIN MATERIALES m on m.COD_MATERIAL=iad.COD_MATERIAL"+
                                  " WHERE cast(ia.COD_INGRESO_ALMACEN as varchar)+' '+cast(iad.cod_material as varchar) in (select cast(iade1.COD_INGRESO_ALMACEN as varchar)+' '+cast(iade1.cod_material as varchar) from INGRESOS_ALMACEN_DETALLE_ESTADO iade1"+
                                  " where iade1.FECHA_VENCIMIENTO <= '"+fechaVenc+" 23:59:59' "+
                                  " and iade1.CANTIDAD_RESTANTE>0  "+(codMaterial=="0"?"":" and iade1.COD_MATERIAL in ("+codMaterial+")")+")"+
                                  " and ia.COD_ALMACEN ='"+codAlmacen+"' and ia.COD_ESTADO_INGRESO_ALMACEN=1"+
                                  " group by ia.NRO_INGRESO_ALMACEN,ia.FECHA_INGRESO_ALMACEN,m.NOMBRE_MATERIAL,p.NOMBRE_PROVEEDOR,um.ABREVIATURA,iade.FECHA_VENCIMIENTO, iade.LOTE_MATERIAL_PROVEEDOR" +
                                  " order by m.NOMBRE_MATERIAL asc,ia.FECHA_INGRESO_ALMACEN desc";
                System.out.println("consulta detalle "+consulta);
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta);
                SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");
                SimpleDateFormat sdf2= new SimpleDateFormat("dd/MM/yyyy");

                while(res.next())
                {
                    %>
                    <tr>
                    <td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%=res.getString("NRO_INGRESO_ALMACEN")==null?"":res.getString("NRO_INGRESO_ALMACEN")%></td>
                    <td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%=res.getTimestamp("FECHA_INGRESO_ALMACEN")==null?"":sdf.format(res.getTimestamp("FECHA_INGRESO_ALMACEN")) %></td>
                    <td align='left' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%=res.getString("NOMBRE_MATERIAL")==null?"":res.getString("NOMBRE_MATERIAL") %></td>
                    <td align='left' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%= res.getString("NOMBRE_PROVEEDOR")==null?"":res.getString("NOMBRE_PROVEEDOR") %></td>
                    <td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%=formato.format(res.getDouble("cantidadP")) %></td>
                    <td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%=formato.format(res.getDouble("cantidadRestante")) %></td>
                    <td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%= res.getString("ABREVIATURA")==null?"":res.getString("ABREVIATURA") %></td>
                    <td align='right' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%=res.getTimestamp("FECHA_VENCIMIENTO")==null?"":sdfMMyyyy.format(res.getTimestamp("FECHA_VENCIMIENTO")) %></td>
                    <td align='left' style='border-bottom : solid #cccccc 1px;padding:8px'  ><%=res.getString("LOTE_MATERIAL_PROVEEDOR")==null?"":res.getString("LOTE_MATERIAL_PROVEEDOR") %></td>

                    </tr>
                    <%
                }
                res.close();
                st.close();
                con.close();
               




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
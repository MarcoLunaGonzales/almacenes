..
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
            <h4 align="center">Reporte de Existencias General</h4>

            <%
        try {

            NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("#,###.00");
            boolean existenciaReactivos=(request.getParameter("conExistenciasAlmacen").equals("1"));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            con = Util.openConnection(con);



            String fecha = request.getParameter("fecha") == null ? "01/01/2011" : request.getParameter("fecha");
            String arrayfecha[] = fecha.split("/");
            fecha = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            //codMaterial = codMaterial.substring(1);
            String codGrupo = request.getParameter("codGrupo") == null ? "''" : request.getParameter("codGrupo");
            String movimiento = request.getParameter("movimiento") == null ? "''" : request.getParameter("movimiento");
            String codEstadoItem=request.getParameter("codEstadoItem");
            String codCapitulo = request.getParameter("codCapitulo") == null ? "''" : request.getParameter("codCapitulo");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");



            System.out.println("parametros" + fecha + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " " +
                    "" + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);




            %>

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



            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr bgcolor="#cccccc">
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Cod Material</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Item</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unidades</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Total Existencias</b></td>
                    <%=(existenciaReactivos?"<td  style='border : solid #D8D8D8 1px' class='border' bgcolor='#f2f2f2' align='center'><b>Total Existencia<BR>(Almacen de Reactivos)</b></td>":"")%>
                </tr>

                <%

                String consulta = " select m.cod_material, m.nombre_material,um.abreviatura,m.stock_minimo_material,m.stock_maximo_material," +
                        " m.stock_seguridad" +
                        (existenciaReactivos?",ISNULL((isnull((select sum(id.CANT_TOTAL_INGRESO_FISICO) from INGRESOS_ALMACEN i inner join  INGRESOS_ALMACEN_DETALLE id on"+
                        " i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN where i.COD_ALMACEN=9 and i.COD_ESTADO_INGRESO_ALMACEN<>2 and id.COD_MATERIAL=m.COD_MATERIAL "+
                        " and i.FECHA_INGRESO_ALMACEN <= '"+fecha+" 23:59:59'),0)-"+
                        " isnull((select sum(sd.CANTIDAD_SALIDA_ALMACEN) from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sd on"+
                        " s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN where s.COD_ALMACEN=9 and   sd.COD_MATERIAL=m.COD_MATERIAL "+
                        " and s.FECHA_SALIDA_ALMACEN <='"+fecha+" 23:59:59' and s.COD_ESTADO_SALIDA_ALMACEN<>2),0)),0) as existenciaReactivo":"")+
                        " from materiales m, grupos g, capitulos c, unidades_medida um where " +
                        " m.cod_grupo=g.cod_grupo and g.cod_capitulo=c.cod_capitulo and m.cod_unidad_medida = um.cod_unidad_medida";
                        
                consulta += " and  m.movimiento_item=" + movimiento + " and m.cod_material in (" + codMaterial + ")" +
                            (codEstadoItem.equals("0")?"":" and m.cod_estado_registro="+codEstadoItem)+
                            " order by m.nombre_material,c.nombre_capitulo,g.nombre_grupo  ";

                System.out.println("consulta 1 " + consulta);
                con = Util.openConnection(con);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(consulta);
                while (rs.next()) {
                    String codItem=rs.getString(1);
                    String nombreItem=rs.getString(2);
                    String abreviatura=rs.getString(3);
                    float stockMin=rs.getFloat(4);
                    float stockMax=rs.getFloat(5);
                    float stockSeg=rs.getFloat(6);

                    String sqlIng="select sum(id.CANT_TOTAL_INGRESO_FISICO) from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE id " +
                            " where i.COD_ALMACEN="+codAlmacen+" and i.COD_ESTADO_INGRESO_ALMACEN<>2 and id.COD_MATERIAL="+codItem+"  " +
                            " and i.FECHA_INGRESO_ALMACEN <= '"+fecha+" 23:59:59' and i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN";
                    System.out.println("sqlIng :"+sqlIng);
                    Statement stIng=con.createStatement();
                    ResultSet rsIng=stIng.executeQuery(sqlIng);
                    double cantIng=0;
                    if(rsIng.next()){
                        cantIng=rsIng.getDouble(1);
                    }
                    double cantSal=0;
                    String sqlSal="select sum(sd.CANTIDAD_SALIDA_ALMACEN) from SALIDAS_ALMACEN s, SALIDAS_ALMACEN_DETALLE sd " +
                             " where s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN and s.COD_ALMACEN="+codAlmacen+" and  " +
                             " sd.COD_MATERIAL="+codItem+" and s.FECHA_SALIDA_ALMACEN <='"+fecha+" 23:59:59' and s.COD_ESTADO_SALIDA_ALMACEN<>2";
                    System.out.println("sqlSal :"+sqlSal);
                    Statement stSal=con.createStatement();
                    ResultSet rsSal=stSal.executeQuery(sqlSal);
                    if(rsSal.next()){
                        cantSal=rsSal.getDouble(1);
                    }

                    System.out.println("codItem: "+codItem+" cantIngreso: "+cantIng+" cantSalida: "+cantSal);
                    
                    double saldoItem=0;
                    saldoItem=cantIng-cantSal;

                    int sw = 0;
                    if (codStock.equals("0")) {
                        sw = 1;
                    }
                    if (codStock.equals("1") && saldoItem <= stockMin) {
                        sw = 1;
                    }

                    if (codStock.equals("2") && saldoItem >= stockMin && saldoItem < stockSeg) {
                        sw = 1;
                    }
                    if (codStock.equals("3") && saldoItem >= stockMin && saldoItem < stockMax) {
                        sw = 1;
                    }
                    if (codStock.equals("4") && saldoItem > stockMax) {
                        sw = 1;
                    }

                    if (sw == 1) {
                        //System.out.println("el saldo " + aprobados);
                        out.print("<tr>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + codItem + "</td>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + nombreItem + "</td>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + abreviatura + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(saldoItem) + "</td>");
                        if(existenciaReactivos)out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(rs.getDouble("existenciaReactivo")) + "</td>");
                        out.print("</tr>");
                    }
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
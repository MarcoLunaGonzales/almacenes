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
public Double costoMaterial(String fecha,String codMaterial,String codAlmacen){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/dd/MM");
    Double costo = 0.0;
    try{
        Connection con = null;
        con = Util.openConnection(con);
        Statement st = con.createStatement();
        SimpleDateFormat sdf3=new SimpleDateFormat("MM");
        /*String consulta = "SELECT top 1 isnull(costo_material, 0) as costo_material" +
                 " from costos_material_por_mes" +
                 " where cod_material = '"+codMaterial+"' and" +
                 " fecha <= '"+fecha+" 23:59:59'"
                + " and cod_almacen = '"+codAlmacen+"' order by fecha desc";*/
        System.out.println("fecha consulta"+fecha+"  "+fecha.substring(5, 7)) ;   
        String consulta = "SELECT top 1 isnull(costo_material, 0) as costo_material" +
                 " from costos_material_por_mes" +
                 " where cod_material = '"+codMaterial+"' and" +
                 " cod_mes = '"+fecha.substring(5, 7)+"' and cod_gestion=(select cod_gestion from gestiones where '"+fecha+"' between fecha_ini and fecha_fin)"
                + " and cod_almacen = '"+codAlmacen+"' ";
        ResultSet rs = st.executeQuery(consulta);
        System.out.println("consulta " + consulta);
        if(rs.next()){
            costo = rs.getDouble("costo_material");
        }
    }catch(Exception e){e.printStackTrace();}
    return costo;
}
public Double deduccionCostoMaterial(String fecha,String codMaterial,String codAlmacen,Double saldoActual){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/dd/MM");
    Double costo = 0.0;
    try{
        Connection con = null;
        con = Util.openConnection(con);
        Statement st = con.createStatement();
        String consulta = "select top 1 * from ( SELECT  isnull(c.costo_material, 0) as costo_material,c.fecha,((select sum(id.CANT_TOTAL_INGRESO_FISICO) from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE id " +
                            " where i.COD_ALMACEN="+codAlmacen+" and i.COD_ESTADO_INGRESO_ALMACEN<>2 and id.COD_MATERIAL="+codMaterial+"  " +
                            " and i.FECHA_INGRESO_ALMACEN <= c.fecha and i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN)-(select sum(sd.CANTIDAD_SALIDA_ALMACEN) from SALIDAS_ALMACEN s, SALIDAS_ALMACEN_DETALLE sd " +
                             " where s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN and s.COD_ALMACEN="+codAlmacen+" and  " +
                             " sd.COD_MATERIAL="+codMaterial+" and s.FECHA_SALIDA_ALMACEN <=c.fecha and s.COD_ESTADO_SALIDA_ALMACEN<>2)) saldo,t.cambio ufv" +
                 " from costos_material_por_mes c inner join tipocambios_moneda t on t.fecha = c.fecha and t.cod_moneda = 4" +
                 " where c.cod_material = '"+codMaterial+"' and" +
                 " c.fecha <= '"+fecha+" 23:59:59' and c.cod_almacen = '"+codAlmacen+"' and c.costo_material >0 ) as tabla where saldo>0 order by fecha desc ";
        System.out.println("consulta xxxxxxxxxxxxxx " + consulta);
        ResultSet rs = st.executeQuery(consulta);
        Double costoAnterior = 0.0;
        Double ufvAnterior = 0.0;
        Double saldoAnterior = 0.0;
        if(rs.next()){
            costoAnterior = rs.getDouble("costo_material");
            ufvAnterior = rs.getDouble("ufv");
            saldoAnterior = rs.getDouble("saldo");
        }
        consulta = " select top 1 cambio from tipocambios_moneda where fecha <= '"+fecha+"' order by fecha desc";
        System.out.println("consulta " + consulta);
        Statement st1 = con.createStatement();
        ResultSet rs1 = st1.executeQuery(consulta);
        Double ufvActual = 0.0;
        if(rs1.next()){
            ufvActual = rs1.getDouble("cambio");
        }
        System.out.println("datos "+ufvAnterior + " " + ufvActual + " " +saldoAnterior  + " " + costoAnterior + " " + saldoActual);
        costo = ((ufvActual/ufvAnterior)-1)*(saldoAnterior*costoAnterior);
        Double montoActual = costo*saldoActual;
        costo = montoActual/saldoActual;
        System.out.println("costo actual " + costo);
    }catch(Exception e){e.printStackTrace();}
    return costo;
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

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            con = Util.openConnection(con);



            String fecha = request.getParameter("fecha") == null ? "01/01/2011" : request.getParameter("fecha");
            String arrayfecha[] = fecha.split("/");
            fecha = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            //codMaterial = codMaterial.substring(1);
            String codGrupo = request.getParameter("codGrupo") == null ? "''" : request.getParameter("codGrupo");
            String movimiento = request.getParameter("movimiento") == null ? "''" : request.getParameter("movimiento");
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
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Total</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>c/u Actualizado</b></td>
                </tr>

                <%

                String consulta = " select m.cod_material, m.nombre_material,um.abreviatura,m.stock_minimo_material,m.stock_maximo_material," +
                        " m.stock_seguridad from materiales m, grupos g, capitulos c, unidades_medida um where " +
                        " m.cod_grupo=g.cod_grupo and g.cod_capitulo=c.cod_capitulo and m.cod_unidad_medida = um.cod_unidad_medida";
                if (!codCapitulo.equals("''")) {
                    consulta += " and (c.cod_capitulo in (" + codCapitulo + "))";
                }
                consulta += " and  m.movimiento_item=" + movimiento + " and m.cod_material in (" + codMaterial + ") and m.cod_estado_registro=1 order by m.nombre_material,c.nombre_capitulo,g.nombre_grupo  ";

                System.out.println("consulta 1 " + consulta);
                con = Util.openConnection(con);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(consulta);
                Double costoUnitario = 0.0;
                while (rs.next()) {
                    String codItem=rs.getString(1);
                    String nombreItem=rs.getString(2);
                    String abreviatura=rs.getString(3);
                    float stockMin=rs.getFloat(4);
                    float stockMax=rs.getFloat(5);
                    float stockSeg=rs.getFloat(6);

                    String sqlIng="select sum(id.CANT_TOTAL_INGRESO_FISICO) from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE id " +
                            " where i.COD_ALMACEN="+codAlmacen+" and i.COD_ESTADO_INGRESO_ALMACEN<>2 and id.COD_MATERIAL="+codItem+"  " +
                            " and i.FECHA_INGRESO_ALMACEN <= '"+fecha+"' and i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN";
                    Statement stIng=con.createStatement();
                    ResultSet rsIng=stIng.executeQuery(sqlIng);
                    double cantIng=0;
                    if(rsIng.next()){
                        cantIng=rsIng.getDouble(1);
                    }
                    double cantSal=0;
                    String sqlSal="select sum(sd.CANTIDAD_SALIDA_ALMACEN) from SALIDAS_ALMACEN s, SALIDAS_ALMACEN_DETALLE sd " +
                             " where s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN and s.COD_ALMACEN="+codAlmacen+" and  " +
                             " sd.COD_MATERIAL="+codItem+" and s.FECHA_SALIDA_ALMACEN <='"+fecha+"' and s.COD_ESTADO_SALIDA_ALMACEN<>2";
                    Statement stSal=con.createStatement();
                    ResultSet rsSal=stSal.executeQuery(sqlSal);
                    if(rsSal.next()){
                        cantSal=rsSal.getDouble(1);
                    }

                    System.out.println("codItem: "+codItem+" cantIngreso: "+cantIng+" cantSalida: "+cantSal);
                    
                    double saldoItem=0;
                    saldoItem=cantIng-cantSal;
                    
                        //System.out.println("el saldo " + aprobados);
                        costoUnitario = this.costoMaterial(fecha, codItem, codAlmacen);
                        if(costoUnitario==0.0){
                            costoUnitario = this.deduccionCostoMaterial(fecha, codItem, codAlmacen, saldoItem);
                        }
                        out.print("<tr>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + codItem + "</td>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + nombreItem + "</td>");
                        out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>" + abreviatura + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(saldoItem) + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + formato.format(costoUnitario*saldoItem) + "</td>");
                        out.print("<td align='right'style='border-bottom:1px solid #cccccc;padding:8px'class='border'   >" + costoUnitario + "</td>");
                        out.print("</tr>");
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
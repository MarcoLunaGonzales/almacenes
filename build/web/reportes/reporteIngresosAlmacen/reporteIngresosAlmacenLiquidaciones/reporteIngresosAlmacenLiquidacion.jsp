
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
            <h4 align="center">Reporte de Liquidación de Ingresos </h4>

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
                        DecimalFormat formato = (DecimalFormat)nf;
                        formato.applyPattern("#,###.00");

                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat format1=new SimpleDateFormat("HH:mm:ss");

                        con = Util.openConnection(con);



                        String fechaInicial=request.getParameter("fecha1")==null?"01/01/2011":request.getParameter("fecha1");
                        String fechaInicial1 = fechaInicial;
                        String arrayfecha1[]=fechaInicial.split("/");
                        fechaInicial=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                        
                        String fechaFinal=request.getParameter("fecha2")==null?"01/01/2011":request.getParameter("fecha2");
                        String fechaFinal1 = fechaFinal;
                        String arrayfecha2[]=fechaFinal.split("/");
                        fechaFinal=arrayfecha2[2] +"/"+ arrayfecha2[1]+"/"+arrayfecha2[0];
                        
                        String codMaterial=request.getParameter("codMaterial")==null?"0":request.getParameter("codMaterial");
                        String nombreMaterialP=request.getParameter("nombreMaterialP")==null?"''":request.getParameter("nombreMaterialP");
                        //codMaterial = codMaterial.substring(1);
                        String codGrupo=request.getParameter("codGrupoArray")==null?"''":request.getParameter("codGrupoArray");
                        String codCapitulo=request.getParameter("codCapituloArray")==null?"''":request.getParameter("codCapituloArray");
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String codStock=request.getParameter("codStock")==null?"''":request.getParameter("codStock");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");
                        String nombreCapitulo=request.getParameter("nombreCapitulo")==null?"''":request.getParameter("nombreCapitulo");
                        String nombreGrupo=request.getParameter("nombreGrupo")==null?"''":request.getParameter("nombreGrupo");
                        String codProveedor=request.getParameter("codProveedor")==null?"''":request.getParameter("codProveedor");
                        String codTipoIngresoAlmacen=request.getParameter("codTipoIngresoAlmacen")==null?"''":request.getParameter("codTipoIngresoAlmacen");
                        String codFilial=request.getParameter("codFilial")==null?"''":request.getParameter("codFilial");

                        System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " "+ codCapitulo +" " + codAlmacen + " " + codStock + " " +
                                "" + codFilial + " " +  nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo );
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
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Nro</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Fecha</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Nro OC</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Tipo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Proveedor</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Item</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Capitulo</b></td>
                    <td style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Grupo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unid.</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
    
                    
                    
                </tr>

                <%

                String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.ABREVIATURA,ia.COD_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN," +
                        " ia.FECHA_INGRESO_ALMACEN,oc.NRO_ORDEN_COMPRA,t.NOMBRE_TIPO_INGRESO_ALMACEN, " +
                        " p.NOMBRE_PROVEEDOR,iad.CANT_TOTAL_INGRESO_FISICO,iad.COSTO_UNITARIO,g.NOMBRE_GRUPO,c.NOMBRE_CAPITULO,u.ABREVIATURA " +
                        " from INGRESOS_ALMACEN ia " +
                        " inner join TIPOS_INGRESO_ALMACEN t on ia.COD_TIPO_INGRESO_ALMACEN = t.COD_TIPO_INGRESO_ALMACEN " +
                        " inner join PROVEEDORES p on p.COD_PROVEEDOR = ia.COD_PROVEEDOR " +
                        " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN " +
                        " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA " +
                        " inner join MATERIALES m on m.COD_MATERIAL = iad.COD_MATERIAL " +
                        " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO " +
                        " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO" +
                        " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                        " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL " +
                        " left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA " +
                        " where ia.COD_ESTADO_INGRESO_ALMACEN<>2 and 0=0 ";
                        if(!nombreMaterialP.equals("") ){
                            consulta = consulta + " and m.nombre_material like '"+nombreMaterialP+"%' " ;
                        }
                        if(!codFilial.equals("") && !codFilial.equals("0")){
                            consulta = consulta + " and f.cod_filial = '"+codFilial+"' " ;
                        }
                        if(!codAlmacen.equals("") && !codAlmacen.equals("0")){
                            consulta = consulta + " and ia.COD_ALMACEN = '"+codAlmacen+"' " ;
                        }
                        if(!fechaInicial.equals("") && !fechaFinal.equals("")){
                            consulta = consulta + " and ia.FECHA_INGRESO_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59' ";
                        }
                        if(!codProveedor.equals("") && !codProveedor.equals("0")){
                          consulta = consulta + " and ia.COD_PROVEEDOR = '"+codProveedor+"' ";
                        }
                        if(!codTipoIngresoAlmacen.equals("") && !codTipoIngresoAlmacen.equals("0")){
                            consulta = consulta +" and ia.COD_TIPO_INGRESO_ALMACEN = '"+codTipoIngresoAlmacen+"' ";
                        }
                        if(!codCapitulo.equals("") && !codCapitulo.equals("0")){
                            consulta = consulta +" and c.COD_CAPITULO in ("+codCapitulo+") ";
                        }
                        if(!codGrupo.equals("") && !codGrupo.equals("0")){
                            consulta = consulta +" and g.COD_GRUPO in ("+codGrupo+") ";
                        }
                        consulta = consulta + " order by ia.FECHA_INGRESO_ALMACEN desc ";
                        
                

                System.out.println("consulta 1 "+ consulta);
                
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                while (rs.next()) {
                    //System.out.println("consulta 1 "+ consulta);
                                //String codMaterial1 = rs.getString("COD_MATERIAL");
                                //String nombreMaterial = rs.getString("NOMBRE_MATERIAL");
                                //String abreviatura = rs.getString("ABREVIATURA");
                                //String codIngresoAlmacen = rs.getString("COD_INGRESO_ALMACEN");
                                //String nroIngresoAlmacen = rs.getString("NRO_INGRESO_ALMACEN");
                                //Date fechaIngresoAlmacen = rs.getDate("");
                                //String nroOrdenCompra = rs.getString("NRO_ORDEN_COMPRA");
                                //String nombreTipoIngresoAlmacen = rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN");
                                //String nombreProveedor = rs.getString("NOMBRE_PROVEEDOR");
                                //double cantTotalIngresoFisico = rs.getDouble("CANT_TOTAL_INGRESO_FISICO");
                                //double costoPromedio = rs.getDouble("COSTO_PROMEDIO");


                                out.print("<tr>");
                                out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("NRO_INGRESO_ALMACEN")==null?"":rs.getString("NRO_INGRESO_ALMACEN"))+"</td>");
                                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+format.format( rs.getDate("FECHA_INGRESO_ALMACEN")) +" &nbsp; "+ format1.format( rs.getTimestamp("FECHA_INGRESO_ALMACEN").getTime()) +"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("NRO_ORDEN_COMPRA")==null?"":rs.getString("NRO_ORDEN_COMPRA"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")==null?"":rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("NOMBRE_PROVEEDOR")==null?"":rs.getString("NOMBRE_PROVEEDOR"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("NOMBRE_MATERIAL")==null?"":rs.getString("NOMBRE_MATERIAL"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("NOMBRE_CAPITULO")==null?"":rs.getString("NOMBRE_CAPITULO"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("NOMBRE_GRUPO")==null?"":rs.getString("NOMBRE_GRUPO"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("ABREVIATURA")==null?"":rs.getString("ABREVIATURA"))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(formato.format(rs.getDouble("CANT_TOTAL_INGRESO_FISICO")))+"</td>");
                                out.print("</tr>");
                                costoTotal = costoTotal + rs.getDouble("CANT_TOTAL_INGRESO_FISICO") ;
                  }
                                out.print("<tr>");
                                out.print("<td HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '9'  >TOTAL&nbsp;</td>");

                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(formato.format(costoTotal))+"</td>");
                                out.print("</tr>");





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
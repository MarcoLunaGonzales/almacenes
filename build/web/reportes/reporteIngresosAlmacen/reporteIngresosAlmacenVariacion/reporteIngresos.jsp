
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
            <h4 align="center">Reporte de Ingresos </h4>

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
                        formato.applyPattern("#,###.0000");

                        NumberFormat nf1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato1 = (DecimalFormat)nf1;
                        formato1.applyPattern("#,###.0");
                        

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
                        System.out.println("nombreMaterialP:"+nombreMaterialP);
                        String codGrupo=request.getParameter("codGrupoArray")==null?"''":request.getParameter("codGrupoArray");
                        String codCapitulo=request.getParameter("codCapituloArray")==null?"''":request.getParameter("codCapituloArray");
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String codStock=request.getParameter("codStock")==null?"''":request.getParameter("codStock");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");
                        String nombreCapitulo=request.getParameter("nombreCapitulo")==null?"''":request.getParameter("nombreCapitulo");
                        String nombreGrupo=request.getParameter("nombreGrupo")==null?"''":request.getParameter("nombreGrupo");
                        String codProveedor=request.getParameter("codProveedor")==null?"''":request.getParameter("codProveedor");
                        String codTipoIngresoAlmacen=request.getParameter("codTipoIngresoAlmacenArray")==null?"''":request.getParameter("codTipoIngresoAlmacenArray");
                        String nombreTipoIngresoAlmacenArray=request.getParameter("nombreTipoIngresoAlmacenArray")==null?"''":request.getParameter("nombreTipoIngresoAlmacenArray");
                        String codFilial=request.getParameter("codFilial")==null?"''":request.getParameter("codFilial");
                        String numIngresoP=request.getParameter("numIngresoP")==null?"''":request.getParameter("numIngresoP");
                        String numOCP=request.getParameter("numOCP")==null?"''":request.getParameter("numOCP");

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
                        <td><b>Tipo de Ingreso</b></td>
                        <td><%=nombreTipoIngresoAlmacenArray%></td>
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
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Item</b></td>
                    
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Fecha</b></td>
                    
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Tipo Ing.</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Nro OC</b></td>
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Nro Ing.</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>C/U </b></td>
                    <td style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>C/Total</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Proveedor</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>% Variacion</b></td>
                    
                    
                    
    
                    
                    
                </tr>

                <%

                String consulta = " select i.COD_INGRESO_ALMACEN,i.FECHA_INGRESO_ALMACEN, i.NRO_INGRESO_ALMACEN,id.CANT_TOTAL_INGRESO_FISICO, " +
                        " id.COSTO_UNITARIO,(id.CANT_TOTAL_INGRESO_FISICO*id.COSTO_UNITARIO) as costoTotal, p.NOMBRE_PROVEEDOR,oc.NRO_ORDEN_COMPRA,ti.NOMBRE_TIPO_INGRESO_ALMACEN,m.NOMBRE_MATERIAL,m.COD_MATERIAL " +
                        " from  INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE id, ORDENES_COMPRA oc,TIPOS_INGRESO_ALMACEN ti, PROVEEDORES p ,materiales m" +
                        " where i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN and oc.COD_ORDEN_COMPRA=i.COD_ORDEN_COMPRA and " +
                        " ti.COD_TIPO_INGRESO_ALMACEN=i.COD_TIPO_INGRESO_ALMACEN and p.COD_PROVEEDOR=oc.COD_PROVEEDOR and p.COD_PROVEEDOR=i.COD_PROVEEDOR " +
                        " and i.COD_TIPO_INGRESO_ALMACEN=1 " +
                        " and i.COD_ESTADO_INGRESO_ALMACEN<>2  and m.COD_MATERIAL=id.COD_MATERIAL" +
                        " ";
        
                        if(!numOCP.equals("") ){
                            consulta = consulta + " and oc.nro_orden_compra = "+numOCP+" " ;
                        }
                        
                        if(!nombreMaterialP.equals("") ){
                            consulta = consulta + " and m.nombre_material like '"+nombreMaterialP+"%' " ;
                        }
                        
                        if(!codAlmacen.equals("") && !codAlmacen.equals("0")){
                            consulta = consulta + " and i.COD_ALMACEN = '"+codAlmacen+"' " ;
                        }
                        if(!fechaInicial.equals("") && !fechaFinal.equals("")){
                            consulta = consulta + " and i.FECHA_INGRESO_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59' ";
                        }
                        if(!codProveedor.equals("") && !codProveedor.equals("0")){
                          consulta = consulta + " and i.COD_PROVEEDOR = '"+codProveedor+"' ";
                        }
                        
                        if(!codCapitulo.equals("") && !codCapitulo.equals("0")){
                            consulta = consulta +" and m.COD_GRUPO in (select g.COD_GRUPO from grupos g where g.COD_CAPITULO in ("+codCapitulo+")) ";
                        }
                        if(!codGrupo.equals("") && !codGrupo.equals("0")){
                            consulta = consulta +" and m.COD_GRUPO in ("+codGrupo+") ";
                        }
                        consulta = consulta + " order by m.NOMBRE_MATERIAL, oc.NRO_ORDEN_COMPRA, i.FECHA_INGRESO_ALMACEN ";
                        
                

                System.out.println("consulta 1 "+ consulta);
                
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                double costoUnitarioAnt = 0;
                double costoUnitarioPos = 0;
                double desviacion = 0;
                int codMaterialAnt=0;
                int codMaterialPos=0;
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
                                //
                                codMaterialPos=rs.getInt("COD_MATERIAL");
                                costoUnitarioPos=rs.getDouble("COSTO_UNITARIO");
                                if (costoUnitarioAnt==0  ){
                                    desviacion=0;
                                }else{
                                    if (codMaterialPos!=codMaterialAnt){
                                        desviacion=0;
                                    }else{
                                        desviacion=1-(costoUnitarioPos/costoUnitarioAnt);
                                    }
                                    
                                }

                                out.print("<tr>");
                                out.print("<td ALIGN='left' style='border : solid #D8D8D8 1px;padding:5PX;'  >"+(rs.getString("NOMBRE_MATERIAL"))+"</td>");
                                
                                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+format.format( rs.getDate("FECHA_INGRESO_ALMACEN")) +" &nbsp; "+ format1.format( rs.getTimestamp("FECHA_INGRESO_ALMACEN").getTime()) +"</td>");
                                
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")==null?"":rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN"))+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;color:blue;font-size:13px' class='border;'   >"+(rs.getString("NRO_ORDEN_COMPRA")==null?"":rs.getString("NRO_ORDEN_COMPRA"))+"</td>");
                                out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;color:blue' >"+(rs.getString("NRO_INGRESO_ALMACEN")==null?"":rs.getString("NRO_INGRESO_ALMACEN"))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;color:fuchsia'  >"+(formato1.format(rs.getDouble("CANT_TOTAL_INGRESO_FISICO")))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;color:fuchsia'  >"+(formato.format(rs.getDouble("COSTO_UNITARIO")))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;color:fuchsia'  >"+(formato1.format(rs.getDouble("costoTotal")))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(rs.getString("NOMBRE_PROVEEDOR"))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;;color:red;font-size:12px'  >"+(formato1.format(desviacion*100))+"</td>");
                                

                                out.print("</tr>");
                                costoUnitarioAnt=costoUnitarioPos;
                                codMaterialAnt=codMaterialPos;
                                //costoTotal = costoTotal + rs.getDouble("CANT_TOTAL_INGRESO_FISICO") ;
                  }
                                




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
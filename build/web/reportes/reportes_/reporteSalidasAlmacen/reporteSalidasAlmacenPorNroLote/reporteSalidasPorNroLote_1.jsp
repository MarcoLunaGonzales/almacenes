package reportes.reporteSalidasAlmacen.reporteSalidasAlmacenPorNroLote;


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
            <h4 align="center">Reporte de Salidas por Nro. de Lote</h4>
            <%!
    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }
%>


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


                        System.out.println("fechaInicial:"+request.getParameter("fechaReporte1"));
                        System.out.println("fechaInicial2:"+request.getParameter("fechaReporte2"));

                        if(request.getParameter("fechaReporte1").equals("null")){
                            
                        }
                        if(request.getParameter("fechaReporte2")==null){
                            System.out.println("hola entro null 2");
                        }
                        String fechaInicial=request.getParameter("fechaReporte1").equals("null")?"01/01/2011":request.getParameter("fechaReporte1");
                        System.out.println("fechaInicial:--"+fechaInicial);
                        String fechaInicial1 = fechaInicial;
                        String arrayfecha1[]=fechaInicial.split("/");
                        fechaInicial=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                        
                        String fechaFinal=request.getParameter("fechaReporte2").equals("null")?"01/01/2011":request.getParameter("fechaReporte2");
                        String fechaFinal1 = fechaFinal;
                        String arrayfecha2[]=fechaFinal.split("/");
                        fechaFinal=arrayfecha2[2] +"/"+ arrayfecha2[1]+"/"+arrayfecha2[0];
                        
                        String codMaterial=request.getParameter("codMaterial")==null?"0":request.getParameter("codMaterial");
                        String nombreMaterialP=request.getParameter("nombreMaterialP")==null?"''":request.getParameter("nombreMaterialP");
                        System.out.println("nombreMaterialP:"+nombreMaterialP);
                       
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String codStock=request.getParameter("codStock")==null?"''":request.getParameter("codStock");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");

                        
                        String codProductoP=request.getParameter("codProductoP")==null?"'0'":request.getParameter("codProductoP");
                        String nombreProductoP=request.getParameter("nombreProductoP")==null?"''":request.getParameter("nombreProductoP");
                        String codFilial=request.getParameter("codFilial")==null?"''":request.getParameter("codFilial");
                        String numLoteP=request.getParameter("numLoteP")==null?"''":request.getParameter("numLoteP");

                        //System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " "+ codCapitulo +" " + codAlmacen + " " + codStock + " " +
                                //"" + codFilial + " " +  nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo );
                        double costoTotal = 0;
                        double costoTotalCosto = 0;
                        


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
                        <td><b>Producto</b></td>
                        <td><%=nombreProductoP%></td>
                    </tr>
                    <%
                    if(!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01")){
                        %>
                        <tr>
                        <td><b>Fecha Inicial</b></td>
                        <td><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                    </tr>
                        <%
                    }
                    %>
                    
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
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Lote</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Area/Dpto</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Producto</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Codigo Ant.</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Item</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Capitulo</b></td>
                    <td style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Grupo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unid.</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Unit.</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Total</b></td>
    
                    
                    
                </tr>

                <%

                String salida = "SELECT m.NOMBRE_MATERIAL,sad.CANTIDAD_SALIDA_ALMACEN,u.ABREVIATURA,";
                salida += " sa.COD_SALIDA_ALMACEN,sa.FECHA_SALIDA_ALMACEN,sa.NRO_SALIDA_ALMACEN,";
                salida += " ae.NOMBRE_AREA_EMPRESA,sad.COD_MATERIAL,cp.nombre_prod_semiterminado,";
                salida += " sadi.COSTO_SALIDA_ACTUALIZADO,sa.COD_LOTE_PRODUCCION,";
                salida += " m.CODIGO_ANTIGUO,C.NOMBRE_CAPITULO,G.NOMBRE_GRUPO,isnull(sadi. COSTO_SALIDA_ACTUALIZADO,0),";
                salida += " sa.COD_PROD,sum(sadi.CANTIDAD)as cantidad,sum(sadi.CANTIDAD*isnull(sadi. COSTO_SALIDA_ACTUALIZADO,0))as cantidadT";
                salida += " FROM SALIDAS_ALMACEN sa";
                salida += " inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN = sa.COD_SALIDA_ALMACEN";
                salida += " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = sa.COD_AREA_EMPRESA";
                salida += " inner join COMPONENTES_PROD cp on cp.COD_COMPPROD = sa.COD_PROD";
                salida += " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = sad.COD_UNIDAD_MEDIDA";
                salida += " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN  = sa.COD_SALIDA_ALMACEN";
                salida += " and sad.COD_SALIDA_ALMACEN=sadi.COD_SALIDA_ALMACEN";
                salida += " inner join MATERIALES m on m.COD_MATERIAL = sad.COD_MATERIAL  and sadi.COD_MATERIAL=sad.COD_MATERIAL";
                salida += " inner join ALMACENES a on a.COD_ALMACEN = sa.COD_ALMACEN";
                salida += " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL";
                salida += " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO";
                salida += " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO";

                salida += " WHERE  sa.COD_ESTADO_SALIDA_ALMACEN<>2";
                System.out.println("fechaInicial --:"+fechaInicial);
                System.out.println("fechaFinal --:"+fechaFinal);
                if(!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01")){
                     salida = salida + " and sa.FECHA_SALIDA_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59' ";
                }
                if(!numLoteP.equals("") ){
                     salida = salida + " and sa.COD_LOTE_PRODUCCION like '"+numLoteP+"%' " ;
                }
                if(!codProductoP.equals("") && !codProductoP.equals("0") ){
                     salida = salida + " and sa.COD_PROD = "+codProductoP+" " ;
                }
                if(!codFilial.equals("") && !codFilial.equals("0")){
                    salida = salida + " and f.cod_filial = '"+codFilial+"' " ;
                }
                if(!codAlmacen.equals("") && !codAlmacen.equals("0")){
                    salida = salida + " and sa.COD_ALMACEN = '"+codAlmacen+"' " ;
                }


                salida += " group by  m.NOMBRE_MATERIAL,sad.CANTIDAD_SALIDA_ALMACEN,u.ABREVIATURA,sa.COD_SALIDA_ALMACEN,";
                salida += " sa.FECHA_SALIDA_ALMACEN,sa.NRO_SALIDA_ALMACEN,ae.NOMBRE_AREA_EMPRESA,sad.COD_MATERIAL,cp.nombre_prod_semiterminado,";
                salida += " sadi.COSTO_SALIDA_ACTUALIZADO,sa.COD_LOTE_PRODUCCION, m.CODIGO_ANTIGUO , C.NOMBRE_CAPITULO,";
                salida += " G.NOMBRE_GRUPO,sadi. COSTO_SALIDA_ACTUALIZADO,sa.COD_PROD";

                salida += " ORDER BY sa.FECHA_SALIDA_ALMACEN desc,sa.NRO_SALIDA_ALMACEN,m.NOMBRE_MATERIAL,sa.COD_LOTE_PRODUCCION   ";

                        
                

                System.out.println("salida: "+ salida);
                
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(salida);
                costoTotal = 0;
                costoTotalCosto = 0;
                int c=0;
                int nroSalidaAux=0;
                while (rs.next()) {
                    if(c==0){
                        nroSalidaAux=rs.getInt("NRO_SALIDA_ALMACEN");
                        c++;
                    }else{
                        if(nroSalidaAux!=rs.getInt("NRO_SALIDA_ALMACEN")){
                                out.print("<tr bgcolor=#f3f3f3>");
                                out.print("<th HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '10'  >TOTAL&nbsp;</th>");

                                out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;font-family:monospace;font-size:small'  >"+(formato.format(costoTotal))+"</th>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >&nbsp;</td>");
                                out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;font-family:monospace;font-size:small'  >"+(formato.format(costoTotalCosto))+"</th>");
                                out.print("</tr>");
                                costoTotal=0;
                                costoTotalCosto=0;

                        }

                    }



                                out.print("<tr>");
                                out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("NRO_SALIDA_ALMACEN")==null?"":rs.getString("NRO_SALIDA_ALMACEN"))+"</td>");
                                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+format.format( rs.getDate("FECHA_SALIDA_ALMACEN")) +" &nbsp; "+ format1.format( rs.getTimestamp("FECHA_SALIDA_ALMACEN").getTime()) +"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("COD_LOTE_PRODUCCION")==null?"":rs.getString("COD_LOTE_PRODUCCION"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("NOMBRE_AREA_EMPRESA")==null?"":rs.getString("NOMBRE_AREA_EMPRESA"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("nombre_prod_semiterminado")==null?"":rs.getString("nombre_prod_semiterminado"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("CODIGO_ANTIGUO")==null?"":rs.getString("CODIGO_ANTIGUO"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("NOMBRE_MATERIAL")==null?"":rs.getString("NOMBRE_MATERIAL"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("NOMBRE_CAPITULO")==null?"":rs.getString("NOMBRE_CAPITULO"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("NOMBRE_GRUPO")==null?"":rs.getString("NOMBRE_GRUPO"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("ABREVIATURA")==null?"":rs.getString("ABREVIATURA"))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(formato.format(rs.getDouble("cantidad")))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(redondear(rs.getDouble("COSTO_SALIDA_ACTUALIZADO"),4))+"</td>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(formato.format(rs.getDouble("cantidadT")))+"</td>");
                                out.print("</tr>");
                                costoTotal = costoTotal + rs.getDouble("cantidad") ;
                                costoTotalCosto = costoTotalCosto + rs.getDouble("cantidadT") ;
                                nroSalidaAux=rs.getInt("NRO_SALIDA_ALMACEN");
                  }
                                
                         out.print("<tr bgcolor=#f3f3f3>");
                                out.print("<th HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '10'  >TOTAL&nbsp;</th>");

                                out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;font-family:monospace;font-size:small'  >"+(formato.format(costoTotal))+"</th>");
                                out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >&nbsp;</td>");
                                out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;font-family:monospace;font-size:small'  >"+(formato.format(costoTotalCosto))+"</th>");
                                out.print("</tr>");



               %>
               </table>
<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-left:5PX;font-family:monospace;font-size:x-large' >


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
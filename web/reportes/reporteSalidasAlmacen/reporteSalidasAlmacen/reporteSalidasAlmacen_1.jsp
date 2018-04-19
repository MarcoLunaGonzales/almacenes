
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
<%!
    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }
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
            <h4 align="center">Reporte de Salidas</h4>

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
                        String codAlmacenDestino=request.getParameter("codAlmacenDestino")==null?"''":request.getParameter("codAlmacenDestino");
                        String codStock=request.getParameter("codStock")==null?"''":request.getParameter("codStock");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");
                        String nombreCapitulo=request.getParameter("nombreCapitulo")==null?"''":request.getParameter("nombreCapitulo");
                        String nombreGrupo=request.getParameter("nombreGrupo")==null?"''":request.getParameter("nombreGrupo");
                        String codProveedor=request.getParameter("codProveedor")==null?"''":request.getParameter("codProveedor");
                        String codTipoIngresoAlmacen=request.getParameter("codTipoIngresoAlmacen")==null?"''":request.getParameter("codTipoIngresoAlmacen");
                        
                        String codFilial=request.getParameter("codFilial")==null?"''":request.getParameter("codFilial");


                        String codAreaDestino=request.getParameter("codAreaDestino")==null?"":request.getParameter("codAreaDestino");
                        String codProdSemiterminado=request.getParameter("codProdSemiterminado")==null?"":request.getParameter("codProdSemiterminado");
                        String nroSalidaAlmacen=request.getParameter("numSalidaAlmacen")==null?"":request.getParameter("numSalidaAlmacen");
                        String codTipoSalidaAlmacen=request.getParameter("codTipoSalidaAlmacen")==null?"":request.getParameter("codTipoSalidaAlmacen");

                        System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " "+ codCapitulo +" " + codAlmacen + " " + codStock + " " +
                                "" + codFilial + " " +  nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo );
                        double cantidadTotal = 0;
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
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Area/Dpto</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Tipo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Almacen Destino</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Item</b></td>
                    
                    <td style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Unid.</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Unit.</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Importe Total</b></td>

    
                    
                    
                </tr>

                <%

                String consulta = " select sa.nro_salida_almacen,sa.cod_salida_almacen,sa.cod_personal,sa.cod_tipo_salida_almacen,";
                consulta += " p.ap_paterno_personal+' '+ p.ap_materno_personal+' '+p.nombres_personal,g.nombre_gestion ,";
                consulta += " ts.nombre_tipo_salida_almacen ,c.nombre_prod_semiterminado ,ae.nombre_area_empresa ,al.nombre_almacen ,";
                consulta += " sa.fecha_salida_almacen,sa.obs_salida_almacen,sa.cod_lote_produccion,m.nombre_material,sad.CANTIDAD_SALIDA_ALMACEN,um.abreviatura,";
                consulta += " (select top 1  sss.COSTO_SALIDA_ACTUALIZADO from SALIDAS_ALMACEN_DETALLE_INGRESO sss where sss.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and sss.COD_MATERIAL=sad.COD_MATERIAL and sss.CANTIDAD>0) as costo";
                consulta += " from salidas_almacen sa";
                consulta += " inner join salidas_almacen_detalle sad on sad.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN";
                consulta += " inner join personal p on p.COD_PERSONAL=sa.COD_PERSONAL";
                consulta += " inner join gestiones g on g.COD_GESTION=sa.COD_GESTION";
                consulta += " inner join tipos_salidas_almacen ts on ts.COD_TIPO_SALIDA_ALMACEN=sa.COD_TIPO_SALIDA_ALMACEN";
                consulta += " left join componentes_prod c on c.COD_COMPPROD =sa.COD_PROD";
                consulta += " LEFT OUTER JOIN UNIDADES_MEDIDA um ON sad.COD_UNIDAD_MEDIDA = um.COD_UNIDAD_MEDIDA";
                consulta += " left join areas_empresa ae on ae.COD_AREA_EMPRESA=sa.COD_AREA_EMPRESA";
                consulta += " inner join ALMACENES al on al.COD_ALMACEN=sa.COD_ALMACEN";
                consulta += " left join traspasos tr on tr.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN ";
                consulta += " inner join MATERIALES m on m.COD_MATERIAL = sad.COD_MATERIAL ";
                consulta += " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO ";
                consulta += " inner join CAPITULOS ca on ca.COD_CAPITULO = gr.COD_CAPITULO";

                consulta += " where sa.estado_sistema=1 and sa.cod_estado_salida_almacen=1";
                /*consulta += " and sa.cod_prod=255";
                consulta += " and sa.nro_salida_almacen=2169";
                consulta += " and sa.cod_tipo_salida_almacen=1";
                consulta += " and sa.cod_area_empresa=82";*/
               

                        if(!nombreMaterialP.equals("") ){
                            consulta = consulta + " and m.nombre_material like '"+nombreMaterialP+"%' " ;
                        }
                        
                        if(!codAreaDestino.equals("") && !codAreaDestino.equals("0")){
                             consulta += " and sa.cod_area_empresa="+codAreaDestino+"";
                        }
                        if(!codProdSemiterminado.equals("") && !codProdSemiterminado.equals("0")){
                             consulta += " and sa.cod_prod="+codProdSemiterminado+"";
                        }
                        if(!nroSalidaAlmacen.equals("") && !nroSalidaAlmacen.equals("0")){
                             consulta += " and sa.nro_salida_almacen like '%"+nroSalidaAlmacen+"%'";
                        }
                        if(!codTipoSalidaAlmacen.equals("") && !codTipoSalidaAlmacen.equals("0")){
                             consulta += " and sa.cod_tipo_salida_almacen="+codTipoSalidaAlmacen+"";
                        }
                        if(!codAlmacenDestino.equals("") && !codAlmacenDestino.equals("-1")){
                            consulta = consulta + " and tr.cod_almacen_origen=sa.cod_almacen and tr.cod_almacen_destino="+codAlmacenDestino+"";
                        }
                        if(!codAlmacen.equals("") && !codAlmacen.equals("0")){
                            consulta = consulta + " and sa.cod_almacen = '"+codAlmacen+"' " ;
                        }
                        if(!fechaInicial.equals("") && !fechaFinal.equals("")){
                            consulta = consulta + " and sa.fecha_salida_almacen between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59' ";
                        }
                  
                  
                        if(!codCapitulo.equals("") && !codCapitulo.equals("0")){
                            consulta = consulta +" and ca.COD_CAPITULO in ("+codCapitulo+") ";
                        }
                        if(!codGrupo.equals("") && !codGrupo.equals("0")){
                            consulta = consulta +" and gr.COD_GRUPO in ("+codGrupo+") ";
                        }
                        consulta = consulta + " order by sa.fecha_salida_almacen desc ";
                        
                

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
                                out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("nro_salida_almacen")==null?"":rs.getString("nro_salida_almacen"))+"</td>");
                                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+format.format( rs.getDate("fecha_salida_almacen")) +" &nbsp; "+ format1.format( rs.getTimestamp("fecha_salida_almacen").getTime()) +"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("nombre_area_empresa")==null?"":rs.getString("nombre_area_empresa"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("nombre_tipo_salida_almacen")==null?"":rs.getString("nombre_tipo_salida_almacen"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("nombre_almacen")==null?"":rs.getString("nombre_almacen"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("NOMBRE_MATERIAL")==null?"":rs.getString("NOMBRE_MATERIAL"))+"</td>");
                                out.print("<th style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("abreviatura")==null?"":rs.getString("abreviatura"))+"</th>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding:5PX;' align='right' >"+(rs.getString("CANTIDAD_SALIDA_ALMACEN")==null?"":formato.format(rs.getDouble("CANTIDAD_SALIDA_ALMACEN")))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding:5PX;' align='right' >"+(rs.getString("costo")==null?"":redondear(rs.getDouble("costo"),4))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding:5PX;' align='right' >"+(formato.format(rs.getDouble("CANTIDAD_SALIDA_ALMACEN")*rs.getDouble("costo")))+"</td>");
                                

                                out.print("</tr>");
                                cantidadTotal = cantidadTotal + rs.getDouble("CANTIDAD_SALIDA_ALMACEN") ;
                                costoTotal = costoTotal + (rs.getDouble("costo")*rs.getDouble("CANTIDAD_SALIDA_ALMACEN")) ;
                  }
                                out.print("<tr>");
                                out.print("<td HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '7'  >TOTAL&nbsp;</td>");

                                out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(formato.format(cantidadTotal))+"</th>");
                                out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >&nbsp;</th>");
                                out.print("<th ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(formato.format(costoTotal))+"</th>");
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
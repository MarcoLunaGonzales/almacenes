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
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Salidas y solicitudes de devolución</h4>
            <%!
    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }
%>



                    <%
                    Connection con = null;
                    try {
                        Boolean reporteConFechas=(request.getParameter("check")!=null);
                        Boolean reporteConFechaSolicitud=(request.getParameter("check2")!=null);
                        //formato de numero
                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat)nf;
                        formato.applyPattern("#,###.00");
                        //formato de fecha
                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat format1=new SimpleDateFormat("HH:mm:ss");
                        con = Util.openConnection(con);
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");
                        String codProductoP=request.getParameter("codProductoP")==null?"'0'":request.getParameter("codProductoP");
                        String nombreProductoP=request.getParameter("nombreProductoP")==null?"''":request.getParameter("nombreProductoP");
                        String codFilial=request.getParameter("codFilial")==null?"''":request.getParameter("codFilial");
                        String numLoteP=request.getParameter("numLoteP")==null?"''":request.getParameter("numLoteP");
                        double costoTotal = 0;
                        double costoTotalCosto = 0;
                        


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
                        <td><b>Producto</b></td>
                        <td><%=nombreProductoP%></td>
                    </tr>
                   <%
                   if(reporteConFechas)
                   {
                       out.println("<tr><td><b>Fecha Inicio Salida</b></td><td>"+request.getParameter("fecha1")+"</td></tr>");
                       out.println("<tr><td><b>Fecha Final Salida</b></td><td>"+request.getParameter("fecha2")+"</td></tr>");
                   }
                   if(reporteConFechaSolicitud)
                   {
                       out.println("<tr><td><b>Fecha Inicio Solicitud Devolución</b></td><td>"+request.getParameter("fechaInicioSolicitud")+"</td></tr>");
                       out.println("<tr><td><b>Fecha Final Solicitud Devolución</b></td><td>"+request.getParameter("fechaFinalSolicitud")+"</td></tr>");
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
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Tipo de Salida</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Fecha</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Lote</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Producto</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Material</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unid.</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad Salida</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Fecha Solicitud</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Estado Solicitud</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad Devuelta</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad Devuelta Fallados</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad Devuelta Fallados Proovedor</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Observación</b></td>
                    
                    
                    
                </tr>

                <%

                String salida = "SELECT m.NOMBRE_MATERIAL,sad.CANTIDAD_SALIDA_ALMACEN,u.ABREVIATURA,";
                salida += " sa.COD_SALIDA_ALMACEN,sa.FECHA_SALIDA_ALMACEN,sa.NRO_SALIDA_ALMACEN,";
                salida += " ae.NOMBRE_AREA_EMPRESA,sad.COD_MATERIAL,cp.nombre_prod_semiterminado,";
                salida += " sadi.COSTO_SALIDA_ACTUALIZADO,sa.COD_LOTE_PRODUCCION,";
                salida += " m.CODIGO_ANTIGUO,isnull(sadi. COSTO_SALIDA_ACTUALIZADO,0),";
                salida += " sa.COD_PROD,sum(sadi.CANTIDAD)as cantidad,sum(sadi.CANTIDAD*isnull(sadi. COSTO_SALIDA_ACTUALIZADO,0))as cantidadT" +
                          ",ts.NOMBRE_TIPO_SALIDA_ALMACEN";
                salida += " FROM SALIDAS_ALMACEN sa";
                salida += " inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN = sa.COD_SALIDA_ALMACEN";
                salida += " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = sa.COD_AREA_EMPRESA";
                salida += " inner join COMPONENTES_PROD cp on cp.COD_COMPPROD = sa.COD_PROD";
                salida += " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = sad.COD_UNIDAD_MEDIDA";
                salida += " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN  = sa.COD_SALIDA_ALMACEN";
                salida += " and sad.COD_SALIDA_ALMACEN=sadi.COD_SALIDA_ALMACEN";
                salida += " inner join MATERIALES m on m.COD_MATERIAL = sad.COD_MATERIAL  and sadi.COD_MATERIAL=sad.COD_MATERIAL";
                salida += " inner join ALMACENES a on a.COD_ALMACEN = sa.COD_ALMACEN";
                salida += " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL" +
                          " inner join TIPOS_SALIDAS_ALMACEN ts on ts.COD_TIPO_SALIDA_ALMACEN=sa.COD_TIPO_SALIDA_ALMACEN";

                salida += " WHERE  sa.COD_ESTADO_SALIDA_ALMACEN<>2";
                
                if(!numLoteP.equals("") ){
                     salida = salida + " and sa.COD_LOTE_PRODUCCION = '"+numLoteP+"' " ;
                }
                if(!codProductoP.equals("") && !codProductoP.equals("0") ){
                     salida = salida + " and sa.COD_PROD = "+codProductoP+" " ;
                }
                if(!codFilial.equals("") && !codFilial.equals("0")&& !codFilial.equals("-1")){
                    salida = salida + " and f.cod_filial = '"+codFilial+"' " ;
                }
                if(!codAlmacen.equals("") && !codAlmacen.equals("0") && !codAlmacen.equals("-1")){
                    salida = salida + " and sa.COD_ALMACEN = '"+codAlmacen+"' " ;
                }
                if(reporteConFechas)
                {
                    String[] arrayfecha=request.getParameter("fecha1").split("/");
                    salida+=" and sa.FECHA_SALIDA_ALMACEN BETWEEN '"+arrayfecha[2]+"/"+arrayfecha[1]+"/"+arrayfecha[0]+" 00:00'";
                    arrayfecha=request.getParameter("fecha2").split("/");
                    salida+=" and '"+arrayfecha[2]+"/"+arrayfecha[1]+"/"+arrayfecha[0]+" 23:59' ";
                }
                String[] arrayfecha=request.getParameter("fechaInicioSolicitud").split("/");
                String fechaInicioSolicitud = arrayfecha[2]+"/"+arrayfecha[1]+"/"+arrayfecha[0]+" 00:00";
                arrayfecha=request.getParameter("fechaFinalSolicitud").split("/");
                String fechaFinalSolicitud = arrayfecha[2]+"/"+arrayfecha[1]+"/"+arrayfecha[0]+" 23:59";
                if(reporteConFechaSolicitud){
                    
                    salida+= " and sa.COD_SALIDA_ALMACEN in "
                            +" ("
                                    +" select sd.COD_SALIDA_ALMACEN"
                                    +" from SOLICITUD_DEVOLUCIONES sd "
                                    +" inner join SOLICITUD_DEVOLUCIONES_DETALLE sdd on sd.COD_SOLICITUD_DEVOLUCION=sdd.COD_SOLICITUD_DEVOLUCION"
                                    +" where sd.FECHA_SOLICITUD BETWEEN '"+fechaInicioSolicitud+"'"
                                    +" and '"+fechaFinalSolicitud+"' "
                            +" )";
                }


                salida += " group by  m.NOMBRE_MATERIAL,sad.CANTIDAD_SALIDA_ALMACEN,u.ABREVIATURA,sa.COD_SALIDA_ALMACEN,";
                salida += " sa.FECHA_SALIDA_ALMACEN,sa.NRO_SALIDA_ALMACEN,ae.NOMBRE_AREA_EMPRESA,sad.COD_MATERIAL,cp.nombre_prod_semiterminado,";
                salida += " sadi.COSTO_SALIDA_ACTUALIZADO,sa.COD_LOTE_PRODUCCION, m.CODIGO_ANTIGUO ,";
                salida += " sadi. COSTO_SALIDA_ACTUALIZADO,sa.COD_PROD,ts.NOMBRE_TIPO_SALIDA_ALMACEN";

                salida += " ORDER BY sa.FECHA_SALIDA_ALMACEN ASC,sa.NRO_SALIDA_ALMACEN,m.NOMBRE_MATERIAL,sa.COD_LOTE_PRODUCCION   ";

                        
                

                System.out.println("salida: "+ salida);
                
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(salida);
                costoTotal = 0;
                costoTotalCosto = 0;
                int c=0;
                int nroSalidaAux=0;
                Statement stDetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resDetalle=null;
                SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                while (rs.next()) {
                    if(c==0){
                        nroSalidaAux=rs.getInt("NRO_SALIDA_ALMACEN");
                        c++;
                    }else{
                        if(nroSalidaAux!=rs.getInt("NRO_SALIDA_ALMACEN"))
                        {
                            rs.previous();
                            String consulta="select sd.FECHA_SOLICITUD, sdd.CANTIDAD_DEVUELTA,sdd.CANTIDAD_DEVUELTA_FALLADOS,sdd.CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR,"+
                                            " sd.OBSERVACION,esd.NOMBRE_ESTADO,m.NOMBRE_MATERIAL"+
                                            " from SOLICITUD_DEVOLUCIONES sd inner join SOLICITUD_DEVOLUCIONES_DETALLE sdd on"+
                                            " sd.COD_SOLICITUD_DEVOLUCION = sdd.COD_SOLICITUD_DEVOLUCION"+
                                            " inner join ESTADOS_SOLICITUD_DEVOLUCION esd on"+
                                            " esd.COD_ESTADO_SOLICITUD_DEVOLUCION = sd.COD_ESTADO_SOLICITUD_DEVOLUCION"+
                                            " inner join materiales m on m.COD_MATERIAL=sdd.COD_MATERIAL"+
                                            " where sd.COD_SALIDA_ALMACEN = '"+rs.getInt("COD_SALIDA_ALMACEN")+"' and"+
                                            " sdd.COD_MATERIAL = '11887'";
                            if(reporteConFechaSolicitud){
                                consulta+=" and sd.FECHA_SOLICITUD BETWEEN '"+fechaInicioSolicitud+"'"
                                    +" and '"+fechaFinalSolicitud+"'";
                            }
                            System.out.println("consulta frv en proceso "+consulta);
                            resDetalle=stDetalle.executeQuery(consulta);
                            while(resDetalle.next())
                            {
                                out.print("<tr><td  HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("NRO_SALIDA_ALMACEN")==null?"":rs.getString("NRO_SALIDA_ALMACEN"))+"</td>");
                                out.print("<td  HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")==null?"":rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"))+"</td>");
                                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+format.format( rs.getDate("FECHA_SALIDA_ALMACEN")) +" &nbsp; "+ format1.format( rs.getTimestamp("FECHA_SALIDA_ALMACEN").getTime()) +"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("COD_LOTE_PRODUCCION")==null?"":rs.getString("COD_LOTE_PRODUCCION"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("nombre_prod_semiterminado")==null?"":rs.getString("nombre_prod_semiterminado"))+"</td>");
                                out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+resDetalle.getString("NOMBRE_MATERIAL")+"&nbsp;</td>");
                                out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >u</td>");
                                out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >0.00&nbsp;</td>");
                                out.print("<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+sdfFecha.format(resDetalle.getTimestamp("FECHA_SOLICITUD"))+"</td>"+
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+resDetalle.getString("NOMBRE_ESTADO")+"</td>"+
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA"))+"</td>"+
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA_FALLADOS"))+"</td>"+
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR"))+"</td>" +
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+resDetalle.getString("OBSERVACION")+"&nbsp;</td></tr>");
                                
                            }
                            rs.next();
                                out.print("<tr bgcolor=#f3f3f3>");
                                out.print("<th HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '14'  >&nbsp;</th>");
                                out.print("</tr>");
                                costoTotal=0;
                                costoTotalCosto=0;

                        }

                    }



                                out.print("<tr>");
                                
                                String consulta="select SD.FECHA_SOLICITUD,sdd.CANTIDAD_DEVUELTA,sdd.CANTIDAD_DEVUELTA_FALLADOS" +
                                                ",sdd.CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR,sd.OBSERVACION,esd.NOMBRE_ESTADO"+
                                                " from SOLICITUD_DEVOLUCIONES sd inner join SOLICITUD_DEVOLUCIONES_DETALLE sdd"+
                                                " on sd.COD_SOLICITUD_DEVOLUCION=sdd.COD_SOLICITUD_DEVOLUCION" +
                                                " inner join ESTADOS_SOLICITUD_DEVOLUCION esd on esd.COD_ESTADO_SOLICITUD_DEVOLUCION=sd.COD_ESTADO_SOLICITUD_DEVOLUCION"+
                                                " where sd.COD_SALIDA_ALMACEN='"+rs.getInt("COD_SALIDA_ALMACEN")+"'  and sdd.COD_MATERIAL='"+rs.getInt("COD_MATERIAL")+"'";
                                if(reporteConFechaSolicitud){
                                    consulta+=" and sd.FECHA_SOLICITUD BETWEEN '"+fechaInicioSolicitud+"'"
                                        +" and '"+fechaFinalSolicitud+"'";
                                }
                                System.out.println("consulta detalle dev "+consulta);
                                resDetalle=stDetalle.executeQuery(consulta);
                                String innerHTML="";
                                int contFilas=1;
                                while(resDetalle.next())
                                {
                                    System.out.println("entro");
                                    innerHTML+=(innerHTML.equals("")?"":"<tr>")+"<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+sdfFecha.format(resDetalle.getTimestamp("FECHA_SOLICITUD"))+"</td>"+
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+resDetalle.getString("NOMBRE_ESTADO")+"</td>"+
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA"))+"</td>"+
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA_FALLADOS"))+"</td>"+
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR"))+"</td>" +
                                            "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+resDetalle.getString("OBSERVACION")+"&nbsp;</td></tr>";
                                    contFilas=resDetalle.getRow();;
                                }
                                if(innerHTML.equals(""))innerHTML="<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>&nbsp;</td><td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>&nbsp;</td><td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>&nbsp;</td>" +
                                        "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>&nbsp;</td><td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>&nbsp;</td><td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>&nbsp;</td></tr>";
                                out.print("<td rowspan='"+contFilas+"' HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("NRO_SALIDA_ALMACEN")==null?"":rs.getString("NRO_SALIDA_ALMACEN"))+"</td>");
                                out.print("<td rowspan='"+contFilas+"' HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")==null?"":rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"))+"</td>");
                                out.print("<td rowspan='"+contFilas+"' class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+format.format( rs.getDate("FECHA_SALIDA_ALMACEN")) +" &nbsp; "+ format1.format( rs.getTimestamp("FECHA_SALIDA_ALMACEN").getTime()) +"</td>");
                                out.print("<td rowspan='"+contFilas+"'  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("COD_LOTE_PRODUCCION")==null?"":rs.getString("COD_LOTE_PRODUCCION"))+"</td>");
                                out.print("<td rowspan='"+contFilas+"' style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("nombre_prod_semiterminado")==null?"":rs.getString("nombre_prod_semiterminado"))+"</td>");
                                out.print("<td rowspan='"+contFilas+"' style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("NOMBRE_MATERIAL")==null?"":rs.getString("NOMBRE_MATERIAL"))+"</td>");
                                out.print("<td rowspan='"+contFilas+"' style='border : solid #D8D8D8 1px;padding-left:5PX;'  >"+(rs.getString("ABREVIATURA")==null?"":rs.getString("ABREVIATURA"))+"</td>");
                                out.print("<td rowspan='"+contFilas+"' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >"+(formato.format(rs.getDouble("cantidad")))+"</td>");
                                out.print(innerHTML);
                                
                                out.print("</tr>");
                                costoTotal = costoTotal + rs.getDouble("cantidad") ;
                                costoTotalCosto = costoTotalCosto + rs.getDouble("cantidadT") ;
                                nroSalidaAux=rs.getInt("NRO_SALIDA_ALMACEN");
                  }

                  if(nroSalidaAux>0)
                {
                    rs.previous();
                    String consulta="select SD.FECHA_SOLICITUD,sdd.CANTIDAD_DEVUELTA,sdd.CANTIDAD_DEVUELTA_FALLADOS,sdd.CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR,"+
                                    " sd.OBSERVACION,esd.NOMBRE_ESTADO,m.NOMBRE_MATERIAL"+
                                    " from SOLICITUD_DEVOLUCIONES sd inner join SOLICITUD_DEVOLUCIONES_DETALLE sdd on"+
                                    " sd.COD_SOLICITUD_DEVOLUCION = sdd.COD_SOLICITUD_DEVOLUCION"+
                                    " inner join ESTADOS_SOLICITUD_DEVOLUCION esd on"+
                                    " esd.COD_ESTADO_SOLICITUD_DEVOLUCION = sd.COD_ESTADO_SOLICITUD_DEVOLUCION"+
                                    " inner join materiales m on m.COD_MATERIAL=sdd.COD_MATERIAL"+
                                    " where sd.COD_SALIDA_ALMACEN = '"+rs.getInt("COD_SALIDA_ALMACEN")+"' and"+
                                    " sdd.COD_MATERIAL = '11887'";
                    if(reporteConFechaSolicitud){
                        consulta+=" and sd.FECHA_SOLICITUD BETWEEN '"+fechaInicioSolicitud+"'"
                            +" and '"+fechaFinalSolicitud+"'";
                    }
                    System.out.println("consulta frv en proceso "+consulta);
                    resDetalle=stDetalle.executeQuery(consulta);
                    while(resDetalle.next())
                    {
                        out.print("<tr><td  HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("NRO_SALIDA_ALMACEN")==null?"":rs.getString("NRO_SALIDA_ALMACEN"))+"</td>");
                                out.print("<td  HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")==null?"":rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"))+"</td>");
                                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+format.format( rs.getDate("FECHA_SALIDA_ALMACEN")) +" &nbsp; "+ format1.format( rs.getTimestamp("FECHA_SALIDA_ALMACEN").getTime()) +"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("COD_LOTE_PRODUCCION")==null?"":rs.getString("COD_LOTE_PRODUCCION"))+"</td>");
                                out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("nombre_prod_semiterminado")==null?"":rs.getString("nombre_prod_semiterminado"))+"</td>");
                        out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >"+resDetalle.getString("NOMBRE_MATERIAL")+"&nbsp;</td>");
                        out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >u</td>");
                        out.print("<td HEIGHT='30PX' class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >0.00&nbsp;</td>");
                        out.print("<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+sdfFecha.format(resDetalle.getTimestamp("FECHA_SOLICITUD"))+"</td>"+
                                    "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+resDetalle.getString("NOMBRE_ESTADO")+"</td>"+
                                    "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA"))+"</td>"+
                                    "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA_FALLADOS"))+"</td>"+
                                    "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+formato.format(resDetalle.getDouble("CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR"))+"</td>" +
                                    "<td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;'>"+resDetalle.getString("OBSERVACION")+"&nbsp;</td></tr>");

                    }
                    rs.next();
                }
                         out.print("<tr bgcolor=#f3f3f3>");
                                out.print("<th HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '13'  >&nbsp;</th>");
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
        </form>
    </body>
</html>
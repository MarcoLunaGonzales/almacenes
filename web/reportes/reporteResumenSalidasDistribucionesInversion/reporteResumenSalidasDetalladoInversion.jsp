
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

<%!        public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
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


            <%
        try {
            Connection con=null;

            String nombreReporte = "DISTRIBUCION MATERIAL PROMOCIONAL INVERSIÓN ";
            NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("#,###.##;(#,###.##)");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

            con = Util.openConnection(con);



            String fechaInicial = request.getParameter("fecha_inicio") == null ? "01/01/2011" : request.getParameter("fecha_inicio");
            String fechaInicial2 = fechaInicial;
            String arrayfecha[] = fechaInicial.split("/");
            fechaInicial = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
            String fechaFinal = request.getParameter("fecha_final") == null ? "01/01/2011" : request.getParameter("fecha_final");
            String fechaFinal2 = fechaFinal;
            String arrayfecha1[] = fechaFinal.split("/");
            fechaFinal = arrayfecha1[2] + "/" + arrayfecha1[1] + "/" + arrayfecha1[0];
            String codTipoSalidaAlmacen = request.getParameter("codigosTipos");
            String nombreTipoSalidaAlmacen = request.getParameter("nombreTipoSalida") == null ? "0" : request.getParameter("nombreTipoSalida");
            //codMaterial = codMaterial.substring(1);
            String conLote = request.getParameter("conLote") == null ? "0" : request.getParameter("conLote");
            String todosLote = request.getParameter("todosLote") == null ? "0" : request.getParameter("todosLote");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String codSeccion = request.getParameter("codSeccion");
            if (!codTipoSalidaAlmacen.equals("-1")) {
                nombreTipoSalidaAlmacen = "TODOS";
            }

            if (todosLote.equals("0")) {
                if (conLote.equals("0")) {
                    nombreReporte += "SIN N° DE LOTE ";
                } else {
                    nombreReporte += "CON N° DE LOTE";
                }
            }

            %>
            <h4 align="center"><%=nombreReporte%></h4>

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
                                    <td><b>Tipos de Salida Almacen</b></td>
                                    <td><%=nombreTipoSalidaAlmacen%></td>
                                </tr>
                                <tr>
                                    <td><b>fecha Inicial</b></td>
                                    <td><%=fechaInicial2%></td>
                                </tr>
                                <tr>
                                    <td><b>fecha Final</b></td>
                                    <td><%=fechaFinal2%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>



            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr bgcolor="#cccccc">
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="5" ><b>Area/Dpto</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>Costo Total Actualizado</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="6" >
                        <table width="100%">
                            <tr>
                                <th  width="20%" style='border-bottom:1px solid #cccccc;padding:8px;color:blue;'>Linea</th>
								<th  width="20%"style='border-bottom:1px solid #cccccc;padding:8px;color:blue;'>Codigo Producto</th>
                                <th  width="20%"style='border-bottom:1px solid #cccccc;padding:8px;color:blue;'>Producto</th>
                                <th  width="20%"style='border-bottom:1px solid #cccccc;padding:8px;color:blue;'>%</th>
                                <th  width="20%"style='border-bottom:1px solid #cccccc;padding:8px;color:blue;'>Distribucion</th>
                                <th  width="20%"style='border-bottom:1px solid #cccccc;padding:8px;color:blue;'>Cuenta</th>
                            </tr>
                        </table>
                    </td>


                </tr>

                <%
                
                StringBuilder consulta = new StringBuilder(" select ae.cod_area_empresa,ae.NOMBRE_AREA_EMPRESA");
                             consulta.append(" from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN");
                             consulta.append(" inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA =s.COD_AREA_EMPRESA");
                             consulta.append(" inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN");
                             consulta.append(" and sadi.COD_MATERIAL=sad.COD_MATERIAL");
                             consulta.append(" where s.FECHA_SALIDA_ALMACEN between '").append(fechaInicial).append(" 00:00:00' and '").append(fechaFinal).append(" 23:59:59'");
                             consulta.append("  and s.estado_sistema=1 and s.COD_ESTADO_SALIDA_ALMACEN=1");
                             if (!codAlmacen.equals("-1"))
                                 consulta.append(" and s.COD_ALMACEN ='").append(codAlmacen).append("'");
                             if (!codSeccion.equals("-1"))
                                consulta.append(" and s.COD_AREA_EMPRESA ='").append(codSeccion).append("'");
                             if(!codTipoSalidaAlmacen.equals("-1"))
                                consulta.append(" and s.COD_TIPO_SALIDA_ALMACEN in (").append(codTipoSalidaAlmacen).append(")");
                             else
                                consulta.append(" and s.COD_TIPO_SALIDA_ALMACEN in (select t.COD_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN t where t.COD_ESTADO_REGISTRO = 1) ");
                             if (todosLote.equals("0")) 
                             {
                                if (conLote.equals("0")) 
                                {
                                    consulta.append(" and  (s.cod_lote_produccion = '' or s.cod_lote_produccion is null)");
                                }
                                else 
                                {
                                    consulta.append(" and  (s.cod_lote_produccion <> '')");
                                }
                             }

                            consulta.append("group by ae.cod_area_empresa,ae.NOMBRE_AREA_EMPRESA order by ae.NOMBRE_AREA_EMPRESA");

                System.out.println("consulta buscar areas Empresa" + consulta.toString());
                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                String codAreaEmpresa = "";
                String nombreAreaEmpresa = "";
                double precioTotalActualizado = 0;
                double sumaTotal = 0;
                double sumaTotalArea = 0;
                //para las salidas
                String nroSalida = "";
                String nroSalidaPivote = "";
                String nroLote = "";
                String fechaSalida = "";
                String observacion = "";
                String item = "";
                String codItem = "";
                double cantidad = 0;
                double costoUnitario = 0;
                ResultSet resDetalle = null;
                Statement stdetalle = null;
                consulta=new StringBuilder("select pp.cod_presentacion,pp.NOMBRE_PRODUCTO_PRESENTACION,lm.COD_LINEAMKT,lm.ABREVIATURA,sum(sip.PARTICIPACION) as PARTICIPACION,");
                consulta.append("detalleSolicitud.NOMBRE_CUENTA");
                consulta.append(" from SOLICITUD_INVERSION_PRODUCTO sip inner join PRESENTACIONES_PRODUCTO pp on ");
                consulta.append(" pp.cod_presentacion=sip.COD_PRESENTACION");
                consulta.append(" inner join LINEAS_MKT lm on lm.COD_LINEAMKT=pp.COD_LINEAMKT");
                consulta.append(" cross apply (select top 1 sid.COD_SOLICITUDINVERSION,pdc.NOMBRE_CUENTA");
                consulta.append(" from SOLICITUD_INVERSION_DETALLE sid inner join SOLICITUD_INVERSION si on");
                consulta.append(" sid.COD_SOLICITUDINVERSION=si.COD_SOLICITUDINVERSION and sid.COD_MATPROMOCIONAL=? and si.COD_ESTADOSOLICITUD=5");
                consulta.append(" and sid.COD_SOLICITUDINVERSION=sip.COD_SOLICITUDINVERSION");
                consulta.append(" left outer join PLAN_DE_CUENTAS pdc on pdc.COD_PLAN_CUENTA=si.COD_CUENTA) detalleSolicitud");
				consulta.append(" where sip.COD_SOLICITUDINVERSION in (select top 1 sid.COD_SOLICITUDINVERSION from SOLICITUD_INVERSION_DETALLE sid inner join SOLICITUD_INVERSION si on sid.COD_SOLICITUDINVERSION = si.COD_SOLICITUDINVERSION and sid.COD_MATPROMOCIONAL = ? and si.COD_ESTADOSOLICITUD = 5)");
                consulta.append(" group by  pp.cod_presentacion,pp.NOMBRE_PRODUCTO_PRESENTACION,lm.COD_LINEAMKT,lm.ABREVIATURA,detalleSolicitud.NOMBRE_CUENTA");
                consulta.append(" order by pp.NOMBRE_PRODUCTO_PRESENTACION");
                System.out.println("consulta inv "+consulta);
                PreparedStatement pstInv=con.prepareStatement(consulta.toString());
                while (res.next()) 
                {
                    sumaTotalArea = 0;
                    codAreaEmpresa = res.getString("cod_area_empresa");
                    nombreAreaEmpresa = res.getString("nombre_area_empresa");
                    out.println("<tr>");
                    out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px' bgcolor='DCDCDC' colspan='14'><b>"+nombreAreaEmpresa+"</b></td>");
                    out.println("</tr>");
                    stdetalle = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    consulta =new StringBuilder("select s.NRO_SALIDA_ALMACEN,ISNULL(s.COD_LOTE_PRODUCCION,'') as COD_LOTE_PRODUCCION,s.FECHA_SALIDA_ALMACEN,SUBSTRING(s.OBS_SALIDA_ALMACEN,1,500) AS OBS_SALIDA_ALMACEN,");
                            consulta.append(" m.NOMBRE_MATERIAL, sum( sadi.CANTIDAD)as cantidad1,");
                            consulta.append(" sadi.COSTO_SALIDA_ACTUALIZADO,");
                            consulta.append(" Isnull((sum( sadi.CANTIDAD)*sadi.COSTO_SALIDA_ACTUALIZADO),0) as costoActualizado,m.cod_material ");
                            consulta.append(" from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN");
                            consulta.append(" inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA");
                            consulta.append(" inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL = sad.COD_MATERIAL");
                            consulta.append(" left outer join MATERIALES m on m.COD_MATERIAL=sad.COD_MATERIAL");
                            consulta.append(" where s.FECHA_SALIDA_ALMACEN between '").append(fechaInicial).append(" 00:00:00' and '").append(fechaFinal).append(" 23:59:59'");
                            consulta.append(" and s.estado_sistema = 1 and s.COD_ESTADO_SALIDA_ALMACEN = 1 ");
                            if (!codAlmacen.equals("-1"))
                                consulta.append(" and s.COD_ALMACEN ='").append(codAlmacen).append("'");
                            if (!codSeccion.equals("-1"))
                                consulta.append(" and s.COD_AREA_EMPRESA ='").append(codSeccion).append("'");
                            if (!codTipoSalidaAlmacen.equals("-1"))
                                consulta.append(" and s.COD_TIPO_SALIDA_ALMACEN in (").append(codTipoSalidaAlmacen).append(")");
                            else
                                consulta.append(" and s.COD_TIPO_SALIDA_ALMACEN in (select t.COD_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN t where t.COD_ESTADO_REGISTRO = 1) ");
                            if (todosLote.equals("0")) 
                            {
                                if (conLote.equals("0")) 
                                {
                                    consulta.append(" and  (s.cod_lote_produccion = '' or s.cod_lote_produccion is null)");
                                }
                                else 
                                {
                                    consulta.append(" and  (s.cod_lote_produccion <> '')");
                                }
                            }
                            consulta.append(" and s.COD_AREA_EMPRESA='").append(codAreaEmpresa).append("'");
                            consulta.append(" group by s.NRO_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION,s.FECHA_SALIDA_ALMACEN,SUBSTRING(s.OBS_SALIDA_ALMACEN,1,500),");
                            consulta.append("m.NOMBRE_MATERIAL,sadi.COSTO_SALIDA_ACTUALIZADO,m.cod_material");
                            consulta.append(" order by s.NRO_SALIDA_ALMACEN,m.NOMBRE_MATERIAL");

                    System.out.println("consulta detalle salidas por area" + consulta.toString());
                    resDetalle = stdetalle.executeQuery(consulta.toString());
                    nroSalidaPivote = "";
                    while (resDetalle.next()) 
                    {
                        nroSalida = resDetalle.getString("NRO_SALIDA_ALMACEN");
                        nroLote = resDetalle.getString("COD_LOTE_PRODUCCION");
                        observacion = resDetalle.getString("OBS_SALIDA_ALMACEN");
                        fechaSalida = format.format(resDetalle.getTimestamp("FECHA_SALIDA_ALMACEN"));
                        item = resDetalle.getString("NOMBRE_MATERIAL");

                        cantidad = resDetalle.getDouble("cantidad1");
                        costoUnitario = resDetalle.getDouble("COSTO_SALIDA_ACTUALIZADO");
                        precioTotalActualizado = resDetalle.getDouble("costoActualizado");
                        codItem = resDetalle.getString("COD_MATERIAL");
                        sumaTotal += precioTotalActualizado;
                        sumaTotalArea += precioTotalActualizado;
                        if (!nroSalidaPivote.equals(nroSalida)) 
                        {
                            out.println("<tr>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Nro:</b></td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Nro Lote:</b></td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Fecha:</b></td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Observaciones:</b></td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Item:</b></td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Cantidad:</b></td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Costo U.:</b></td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Costo Total:</b></td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>&nbsp;</td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>&nbsp;</td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>&nbsp;</td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>&nbsp;</td>");
                            out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>&nbsp;</td>");
							out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>&nbsp;</td>");
                            out.println("</tr>");
                            nroSalidaPivote = nroSalida;
                        }
                        pstInv.setString(1, codItem);
						pstInv.setString(2, codItem);
                        System.out.println("inv codItem "+codItem);
                        ResultSet resInv = pstInv.executeQuery();
                        int cont=0;
                        StringBuilder detalleInversion=new StringBuilder("");
                        while (resInv.next()) 
                        {
                            cont++;
                            detalleInversion.append(detalleInversion.length()>0?"<tr>":"");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+resInv.getString(4)+"</th>");
							detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+resInv.getString(1)+"</th>");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+resInv.getString(2)+"</th>");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+redondear(resInv.getDouble(5), 2)+"</th>");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+redondear(resInv.getDouble(5) / 100 * precioTotalActualizado, 2)+"</th>");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+resInv.getString(6)+"</th>");
                            detalleInversion.append("</tr>");   
                        }
                        if(cont==0)
                        {
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>0.0</th>");
							detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>0.0</th>");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>0.0</th>");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>0.0</th>");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>0.0</th>");
                            detalleInversion.append("<th align='left' style='border-bottom:1px solid #cccccc;padding:8px'>0.0</th>");
                            detalleInversion.append("</tr>");
                            cont=1;
                        }
                            out.println("<tr>");
                            out.println("<td rowspan='"+cont+"' class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+nroSalida+"&nbsp;</td>");
                            out.println("<td rowspan='"+cont+"' class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+nroLote+"&nbsp;</td>");
                            out.println("<td rowspan='"+cont+"'  class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+fechaSalida+"&nbsp;</td>");
                            out.println("<td rowspan='"+cont+"' class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+observacion+"&nbsp;</td>");
                            out.println("<td rowspan='"+cont+"' class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+item+"&nbsp;</td>");
                            out.println("<td rowspan='"+cont+"' class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+formato.format(cantidad)+"&nbsp;</td>");
                            out.println("<td rowspan='"+cont+"' class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+formato.format(costoUnitario)+"&nbsp;</td>");
                            out.println("<td rowspan='"+cont+"' class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+formato.format(precioTotalActualizado)+"&nbsp;</td>");
                            out.println(detalleInversion.toString());
                        
                    

                    }
                    resDetalle.close();
                    stdetalle.close();
                    out.println("<tr>");
                    out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px' colspan='7'><b>TOTAL AREA:</b></td>");
                    out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>"+formato.format(sumaTotalArea)+"</b></td>");
                    out.println("</tr>");
                }
                out.println("<tr>");
                out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px' colspan='7'><b>TOTAL:</b></td>");
                out.println("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>"+formato.format(sumaTotal)+"</b></td>");
                out.println("</tr>");
                out.println("</table>");

            


        } 
        catch (Exception e) {
            e.printStackTrace();
        }

            %>
            <br>

            <br>
            <div align="center">
                <%--<INPUT type="button" class="commandButton" name="btn_registrar" value="<-- Atr�s" onClick="cancelar();"  >--%>

            </div>
        </form>
    </body>
</html>
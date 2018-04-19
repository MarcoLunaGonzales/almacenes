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
    </head>
    <body>
        <form>


                    <%
                    Connection con = null;
                    try 
                    {
                        String codFilial=request.getParameter("codFilial");
                        String fechaInicial=request.getParameter("fecha_inicio")==null?"01/01/2011":request.getParameter("fecha_inicio");
                        String fechaInicial2=fechaInicial;
                        
                        con = Util.openConnection(con);
                        
                       
                        
                        



                        
                        String arrayfecha[]=fechaInicial.split("/");
                        fechaInicial=arrayfecha[2] +"/"+ arrayfecha[1]+"/"+arrayfecha[0];
                        String fechaFinal=request.getParameter("fecha_final")==null?"01/01/2011":request.getParameter("fecha_final");
                        String fechaFinal2=fechaFinal;
                        String arrayfecha1[]=fechaFinal.split("/");
                        fechaFinal=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                        String codTipoSalidaAlmacen=request.getParameter("codigosTipos")==null?"0":request.getParameter("codigosTipos");
                        System.out.println("codigos de tipos"+codTipoSalidaAlmacen);
                        String nombreTipoSalidaAlmacen=request.getParameter("nombreTipoSalida");

                        String conLote=request.getParameter("conLote")==null?"0":request.getParameter("conLote");
                        String todosLote=request.getParameter("todosLote")==null?"0":request.getParameter("todosLote");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");
                        String codSeccion=request.getParameter("codSeccion");
                        System.out.println("parametros" + fechaInicial +"" + conLote+""+todosLote);
                        System.out.println("cod almacen "+codAlmacen);
                        String nombreReporte="RESUMEN DE SALIDAS POR SECCION ";
                        NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat) numeroformato;
                        formato.applyPattern("#,###.##;(#,###.##)");
                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                        
                        if(!codTipoSalidaAlmacen.equals("-1"))
                             {
                            nombreTipoSalidaAlmacen="TODOS";
                            }
                        if(todosLote.equals("0"))
                                {
                                if(conLote.equals("0"))
                                {
                                   nombreReporte+="SIN N° DE LOTE ";
                                }
                                else
                                {
                                    nombreReporte+="CON N° DE LOTE";
                                }
                                }
                    /*if(codFilial.equals("-1"))
                    {

                        codAlmacen="select a.COD_ALMACEN from ALMACENES a where a.COD_ESTADO_REGISTRO=1";
                    }
                    if(codAlmacen.equals("-1"))
                    {
                        codAlmacen="select a.COD_ALMACEN from ALMACENES a where a.COD_FILIAL="+codFilial+" and a.COD_ESTADO_REGISTRO=1";

                    }*/
                        System.out.println("termino el inicio ");
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
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Area/Dpto</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Salidas</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Devoluciones</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Costo Neto</b></td>


                </tr>

                <%
                String consultadevo="select  ISNULL(SUM((iad.CANT_TOTAL_INGRESO_FISICO*iad.COSTO_UNITARIO_ACTUALIZADO)),0) AS IMPORTE_TOTAL"+
                                   " from DEVOLUCIONES d inner join SALIDAS_ALMACEN sa1 on d.COD_SALIDA_ALMACEN=sa1.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1  and sa1.COD_TIPO_SALIDA_ALMACEN in ("+codTipoSalidaAlmacen+")"+
                                   " inner join INGRESOS_ALMACEN ia on d.COD_DEVOLUCION =ia.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                                   " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN"+
                                   " where sa1.COD_AREA_EMPRESA=ae.COD_AREA_EMPRESA and ia.FECHA_INGRESO_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'"+
                                   " and d.COD_ALMACEN IN ("+codAlmacen+") and ia.COD_ESTADO_INGRESO_ALMACEN=1 and d.COD_ESTADO_DEVOLUCION=1";
                   if(todosLote.equals("0"))
                                     {
                                  if(!conLote.equals("0"))
                                          {
                                              consultadevo+=" and (sa1.cod_lote_produccion not in(''))";
                                          }
                                            else
                                           {
                                                consultadevo+=" and (sa1.cod_lote_produccion = '' or  sa1.cod_lote_produccion is null)";
                                            }
                                         }
                String consulta =" select ISNULL(sum(sadi.CANTIDAD*sadi.COSTO_SALIDA_ACTUALIZADO),0) as actualizado,ae.NOMBRE_AREA_EMPRESA,ISNULL(("+consultadevo+"),0) as cantidadDevo"+
                                 " from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN "+
                                 " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA =s.COD_AREA_EMPRESA"+
                                 " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"+
                                 " and sadi.COD_MATERIAL=sad.COD_MATERIAL"+
                                 " where s.FECHA_SALIDA_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'"+
                                 " and s.COD_ALMACEN IN ("+codAlmacen+") and s.estado_sistema=1 and s.COD_ESTADO_SALIDA_ALMACEN=1 ";
                        if(!codSeccion.equals("-1"))
                                {
                                    consulta+=" and s.COD_AREA_EMPRESA ='"+codSeccion+"'";
                                }
                                if(!codTipoSalidaAlmacen.equals("-1"))
                                   {
                                 consulta+=" and s.COD_TIPO_SALIDA_ALMACEN in ("+codTipoSalidaAlmacen+")";
                                 }
                                else
                                {
                                 consulta+=" and s.COD_TIPO_SALIDA_ALMACEN in (select t.COD_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN t where t.COD_ESTADO_REGISTRO = 1) ";
                                }
                            if(todosLote.equals("0"))
                                {
                                if(conLote.equals("0"))
                                {
                                   consulta+=" and  (s.cod_lote_produccion = '' or s.cod_lote_produccion is null)";
                                }
                                else
                                {
                                    consulta+=" and  (s.cod_lote_produccion <> '')";
                                }
                                }

                                consulta+="group by ae.NOMBRE_AREA_EMPRESA,ae.cod_area_empresa order by ae.NOMBRE_AREA_EMPRESA";

           System.out.println("consulta "+ consulta);
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                double cantTotalActualizado=0;
                double cantTotalDevolucion=0;
                double costoNeto=0;
                double cantTotalCostoNeto=0;
                double sumaTotal=0;
                double cantidadDevolucion=0;
                while (rs.next()) {
                    cantTotalActualizado=rs.getDouble("actualizado");
                    cantidadDevolucion=rs.getDouble("cantidadDevo");
                    sumaTotal+=cantTotalActualizado;
                    cantTotalDevolucion+=cantidadDevolucion;
                    costoNeto=cantTotalActualizado-cantidadDevolucion;
                    cantTotalCostoNeto+=costoNeto;
                    //System.out.println("consulta 1 "+ consulta);

                                out.print("<tr>");
                                out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>"+(rs.getString("NOMBRE_AREA_EMPRESA")==null?"":rs.getString("NOMBRE_AREA_EMPRESA"))+"</td>");
                                out.print("<td align='right'style='border:1px solid #cccccc;padding:8px' >"+formato.format(cantTotalActualizado)+"</td>");
                                out.print("<td align='right'style='border:1px solid #cccccc;padding:8px' >"+formato.format(cantidadDevolucion)+"</td>");
                                out.print("<td align='right'style='border:1px solid #cccccc;padding:8px' >"+formato.format(costoNeto)+"</td>");

                                out.print("</tr>");



                                }
                double aux=0;
                /*if(conLote.equals("0")){
                    aux=1057.08;
                    out.print("<tr>");
                    out.print("<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>ASEGURAMIENTO DE CALIDAD</td>");
                    out.print("<td align='right'style='border:1px solid #cccccc;padding:8px' >0</td>");
                    out.print("<td align='right'style='border:1px solid #cccccc;padding:8px' >1,057.08</td>");
                    out.print("<td align='right'style='border:1px solid #cccccc;padding:8px' >-1,057.08</td>");

                    out.print("</tr>");
                }*/


                    

               %>
               <tr>
               <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b>TOTAL:</b></td>
               <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b><%=formato.format(sumaTotal)%></b></td>
               <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b><%=formato.format(cantTotalDevolucion+aux)%></b></td>
               <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b><%=formato.format(cantTotalCostoNeto-aux)%></b></td>
               </tr>
               </table>

                <%


                    }
                     catch (Exception e) {
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
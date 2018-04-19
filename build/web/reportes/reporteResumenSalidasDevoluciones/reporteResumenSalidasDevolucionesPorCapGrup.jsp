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
                    try {
                        Connection con = null;
                        double x=0;
                    String codFilial=request.getParameter("codFilial");
                     String nombreReporte="RESUMEN DE SALIDAS Y DEVOLUCIONES POR CAP�?TULO Y GRUPO ";
                        NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat) numeroformato;
                        formato.applyPattern("#,###.##;(#,###.##)");

                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");

                        con = Util.openConnection(con);



                        String fechaInicial=request.getParameter("fecha_inicio")==null?"01/01/2011":request.getParameter("fecha_inicio");
                        String fechaInicial2=fechaInicial;
                        String arrayfecha[]=fechaInicial.split("/");
                        fechaInicial=arrayfecha[2] +"/"+ arrayfecha[1]+"/"+arrayfecha[0];
                        String fechaFinal=request.getParameter("fecha_final")==null?"01/01/2011":request.getParameter("fecha_final");
                        String fechaFinal2=fechaFinal;
                        String arrayfecha1[]=fechaFinal.split("/");
                        fechaFinal=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                        String codTipoSalidaAlmacen=request.getParameter("codigosTipos")==null?"0":request.getParameter("codigosTipos");
                        System.out.println("codigos de tipos"+codTipoSalidaAlmacen);
                        String nombreTipoSalidaAlmacen=request.getParameter("nombreTipoSalida");
                        //codMaterial = codMaterial.substring(1);
                        String conLote=request.getParameter("conLote")==null?"0":request.getParameter("conLote");
                        String todosLote=request.getParameter("todosLote")==null?"0":request.getParameter("todosLote");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");
                        String codSeccion=request.getParameter("codSeccion");
                        System.out.println("parametros" + fechaInicial +"" + conLote+""+todosLote);
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
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Salidas </b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Devoluciones</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Costo Neto</b></td>


                </tr>

                <%

                
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String consultaDevolucion="select  ISNULL(SUM((iad.CANT_TOTAL_INGRESO_FISICO*iad.COSTO_UNITARIO_ACTUALIZADO)),0) AS cantidaDevolucion"+
                              " from DEVOLUCIONES d inner join SALIDAS_ALMACEN sa on d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1 and sa.COD_TIPO_SALIDA_ALMACEN in ("+codTipoSalidaAlmacen+")"+
                              " inner join INGRESOS_ALMACEN ia on d.COD_DEVOLUCION =ia.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                              " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN"+
                              " where ";
                              if(!codSeccion.equals("-1"))
                               {
                                  consultaDevolucion+=" sa.COD_AREA_EMPRESA='"+codSeccion+"' and";
                              }
                             consultaDevolucion+=" ia.FECHA_INGRESO_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'"+
                              " and d.COD_ALMACEN IN ("+codAlmacen+") and ia.COD_ESTADO_INGRESO_ALMACEN=1 and d.COD_ESTADO_DEVOLUCION=1"+
                              " and iad.COD_MATERIAL in (select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO =g.COD_GRUPO)";
                        if(todosLote.equals("0"))
                                     {
                                  if(!conLote.equals("0"))
                                          {
                                              consultaDevolucion+=" and (sa.cod_lote_produccion not in(''))";
                                          }
                                            else
                                           {
                                                consultaDevolucion+=" and (sa.cod_lote_produccion = '' or  sa.cod_lote_produccion is null)";
                                            }
                                         }
                        String consultaSalida="select sum(sadi.CANTIDAD*sadi.COSTO_SALIDA_ACTUALIZADO) as cantidadSalida"+
                                 " from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN and s.COD_TIPO_SALIDA_ALMACEN in ("+codTipoSalidaAlmacen+")"+
                                 //" from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN and s.COD_TIPO_SALIDA_ALMACEN<>7"+
                                 " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA =s.COD_AREA_EMPRESA "+
                                 " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"+
                                 " and sadi.COD_MATERIAL=sad.COD_MATERIAL"+
                                 " inner join MATERIALES m on m.COD_MATERIAL=sadi.COD_MATERIAL";
                                 /*if(codCapitulo.equals("4555")){
                                     consultaSalida +=" where m.COD_GRUPO=g.COD_GRUPO and  s.COD_SALIDA_ALMACEN in (select d.COD_SALIDA_ALMACEN from devoluciones d , INGRESOS_ALMACEN ia where ia.COD_DEVOLUCION=d.COD_DEVOLUCION"+
                                     " and ia.COD_ESTADO_INGRESO_ALMACEN<>2 and ia.FECHA_INGRESO_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59' "+
                                     " and d.COD_ESTADO_DEVOLUCION<>2) ";
                                 }else{*/
                                     consultaSalida +="  where m.COD_GRUPO=g.COD_GRUPO and  s.FECHA_SALIDA_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'";
                                // }

                                 //consultaDetalle +="  where s.FECHA_SALIDA_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'";
                                 consultaSalida += "and s.COD_ALMACEN  IN ("+codAlmacen+") and s.estado_sistema=1 and s.COD_ESTADO_SALIDA_ALMACEN=1";
                                 if(!codSeccion.equals("-1"))
                                {
                                    consultaSalida+=" and s.COD_AREA_EMPRESA ='"+codSeccion+"'";
                                }
                                if(!codTipoSalidaAlmacen.equals("-1"))
                                   {
                                 consultaSalida+=" and s.COD_TIPO_SALIDA_ALMACEN in ("+codTipoSalidaAlmacen+")";
                                 }
                                else
                                {
                                 consultaSalida+=" and s.COD_TIPO_SALIDA_ALMACEN in (select t.COD_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN t where t.COD_ESTADO_REGISTRO = 1) ";
                                }
                            if(todosLote.equals("0"))
                                {
                                if(conLote.equals("0"))
                                {
                                   consultaSalida+=" and  (s.cod_lote_produccion = '' or s.cod_lote_produccion is null)";
                                }
                                else
                                {
                                    consultaSalida+=" and  (s.cod_lote_produccion <> '')";
                                }
                                }


                String consulta="select  c.COD_CAPITULO,c.NOMBRE_CAPITULO,g.NOMBRE_GRUPO,devolucion.cantidaDevolucion,salida.cantidadSalida"+
                         " from CAPITULOS c inner join GRUPOS g on c.COD_CAPITULO=g.COD_CAPITULO"+
                         " outer apply("+consultaDevolucion+")devolucion outer apply ("+consultaSalida+") salida"+
                         " where (devolucion.cantidaDevolucion>0 or salida.cantidadSalida>0)"+
                         " order by c.NOMBRE_CAPITULO,g.NOMBRE_GRUPO";
                System.out.println("consulta  "+ consulta);
                ResultSet res = st.executeQuery(consulta);
                double cantTotalActualizado=0;
                String nombreCapitulo="";
                String codCapitulo="";
                String nombreGrupo="";
                double sumaTotal=0;
                double sumTotalCapitulo=0;
                double cantidadDevolucion=0;
                double cantTotalDevolucion=0;
                double cantTotaldevolucionCapitulo=0;
                double costoNeto=0;
                double cantTotalCostoNeto=0;
                double cantTotalCapCostoNeto=0;
                int codCapituloCabecera=0;
                while (res.next()) {
                    
                    nombreCapitulo=res.getString("NOMBRE_CAPITULO");
                    codCapitulo=res.getString("COD_CAPITULO");
                    if(codCapituloCabecera!=res.getInt("COD_CAPITULO")||codCapituloCabecera==0)
                    {
                        if(codCapituloCabecera>0)out.println("<tr><td class='border' align='right' style='border-bottom:1px solid #cccccc;padding:8px'><b>TOTAL: &nbsp;&nbsp;</b></td>"+
                                                " <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>"+formato.format(sumTotalCapitulo)+"</b></td>"+
                                                "<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>"+formato.format(cantTotaldevolucionCapitulo+x)+"</b></td>"+
                                                "<td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>"+formato.format(cantTotalCapCostoNeto-x)+"</b></td>"+
                                                " </tr>");
                        out.println(" <tr><td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px' colspan='4'>"+nombreCapitulo+"</td></tr>");
                        codCapituloCabecera=res.getInt("COD_CAPITULO");
                        cantTotaldevolucionCapitulo=0;
                        sumTotalCapitulo=0;
                        cantTotalCapCostoNeto=0;
                    }
                    nombreGrupo=res.getString("NOMBRE_GRUPO");
                    cantTotalActualizado=res.getDouble("cantidadSalida");
                    sumaTotal+=cantTotalActualizado;
                    sumTotalCapitulo+=cantTotalActualizado;
                    cantidadDevolucion=res.getDouble("cantidaDevolucion");
                    cantTotalDevolucion+=cantidadDevolucion;
                    cantTotaldevolucionCapitulo+=cantidadDevolucion;
                    costoNeto=cantTotalActualizado-cantidadDevolucion;
                    cantTotalCapCostoNeto+=costoNeto;
                    cantTotalCostoNeto+=costoNeto;
                                           %>
                                         <tr>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=nombreGrupo%></td>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=formato.format(cantTotalActualizado)%></td>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=formato.format(cantidadDevolucion)%></td>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=formato.format(costoNeto)%></td>

                                        </tr>
                                <%

                                    }
                                    %>

                                    <tr>
                                            <td class='border' align='right' style='border-bottom:1px solid #cccccc;padding:8px'><b>TOTAL: &nbsp;&nbsp;</b></td>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(sumTotalCapitulo)%></b></td>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(cantTotaldevolucionCapitulo+x)%></b></td>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(cantTotalCapCostoNeto-x)%></b></td>

                                        </tr>
                                        <%
                            

                         res.close();
                         st.close();
                         con.close();
               %>


               <tr>
               <td class='border' align='right' style='border-bottom:1px solid #cccccc;padding:8px'><b>TOTAL GENERAL:&nbsp;&nbsp;</b></td>
               <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(sumaTotal)%></b></td>
               <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(cantTotalDevolucion+0)%></b></td>
               <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(cantTotalCostoNeto-0)%></b></td>
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
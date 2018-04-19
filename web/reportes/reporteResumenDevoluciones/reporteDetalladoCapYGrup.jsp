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
<%@ page import = "com.cofar.bean.*" %>
<%@ page language="java" import="java.util.*,com.cofar.util.CofarConnection" %>
<%@ page errorPage="ExceptionHandler.jsp" %>

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
                String codFilial=request.getParameter("codFilial");
                    String fechaInicio=request.getParameter("fecha1");
                    String fechaFinal=request.getParameter("fecha2");
                    String codAlmacen=request.getParameter("codAlmacen");
                    String codTipoSalida = request.getParameter("codTipoSalida");
                    String nombreFilial;
                    String todos=request.getParameter("varTodos");
                    String reporteConLotes=request.getParameter("reporteConLote");
                    String codAreaDestino= request.getParameter("codSeccion");
                    String[] arrayFechaInicial=fechaInicio.split("/");
                    String[] arrayFechaFinal=fechaFinal.split("/");
                    String fechaInicioFormato=arrayFechaFinal[2]+"/"+arrayFechaInicial[1]+"/"+arrayFechaInicial[0];
                    String fechaFinalFormato=arrayFechaFinal[2]+"/"+arrayFechaFinal[1]+"/"+arrayFechaFinal[0];
                    System.out.println("datos f ini"+fechaInicio+" f fin "+fechaFinal+" codAlmacen "+codAlmacen+" codaAreaDestino "+codAreaDestino+" con lote "+reporteConLotes);
                    Connection con=null;
                     NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat)nf;
                        formato.applyPattern("#,##0.00");
                    try {



                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat format1=new SimpleDateFormat("HH:mm:ss");

                         con=Util.openConnection(con);
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res;
                ResultSet resDetalle;
                ResultSet resDevoluciones;
                Statement stDevoluciones;
                Statement stdetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                String[] codAreaEmpresa;
                String[] nombreAreaEmpresa;
                String nombreAreaDestino="";
                String numeroDevolucion="";
                String fechaDevolucion="";
                String loteProduccion="";
                String observacion="";
                String codIngresoAlmacen="";
                String nombreItem="";

                double cantidad=0d;
                double precioUnitario=0d;
                double precioTotal=0d;
                String cabecera="RESUMEN DE DEVOLUCIONES POR CAPÍTULO Y GRUPO ";
                if(!todos.equals("1"))
                                     {
                                  if(reporteConLotes.equals("1"))
                                          {
                                              cabecera+="CON N° DE LOTE";
                                          }
                                            else
                                           {
                                                cabecera+="SIN N° DE LOTE";
                                            }
                                         }
            %>

            <h4 align="center"><%=cabecera%> </h4>

                

            <br>
            <table width="70%" border="0" align="center">
                <tr>
                    <td>
            <img src="../../img/cofar.png" alt="logo cofar" align="left" />
            </td><td>
            <table border="0" class="outputText1" >
                <tbody>
                  
                    <tr>
                        <td><b>Fecha Inicial: <%=fechaInicio%></b></td>
                        <td> &nbsp; <b>Fecha Final: <%=fechaFinal%></b></td>
                    </tr>
                </tbody>
            </table>
            </td>
            </tr>
            </table>
            
            <BR>
            <BR>

            <table width="90%" align="center"  style="border : solid #000000 1px;" class="outputText0" cellpadding="0" cellspacing="0" >
            <tr>
                                           <td bgcolor="#D3D3D3" class='border'  colspan="3"   ><b></b></td>

                                           <td  bgcolor="#D3D3D3" class='border'    ><b>Importe Actualizado</b></td>
                                           
           </tr>

            

                <%
               
                double SumaTotal=0;
                double importeActualizado=0;
                double sumaParcial=0;
                double sumaCapitulo=0;
                String nombreGrupo="";
                String codGrupo="";
                   
                   String consulta="select c.NOMBRE_CAPITULO,c.COD_CAPITULO from CAPITULOS c"+
                                   " where c.COD_CAPITULO in(select g.COD_CAPITULO from grupos g where g.COD_GRUPO in "+
                                   "(select m.COD_GRUPO from MATERIALES m where m.COD_MATERIAL in ("+
                                   " select iad.COD_MATERIAL from DEVOLUCIONES d"
                                + " inner join SALIDAS_ALMACEN sa on d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1"
                                + " AND sa.COD_TIPO_SALIDA_ALMACEN IN ("+codTipoSalida+")"+
                                   " inner join INGRESOS_ALMACEN ia on d.COD_DEVOLUCION =ia.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                                   " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN"+
                                   " where ";
                           if(!codAreaDestino.equals("-1")){
                               consulta+=" sa.COD_AREA_EMPRESA='"+codAreaDestino+"' and" ;
                                }
                            consulta+=" ia.FECHA_INGRESO_ALMACEN between '"+fechaInicioFormato+" 00:00:00' and '"+fechaFinalFormato+" 23:59:59'"+
                                   " and ia.COD_ALMACEN IN ("+codAlmacen+") and ia.COD_ESTADO_INGRESO_ALMACEN=1 and d.COD_ESTADO_DEVOLUCION=1";
                   if(!todos.equals("1"))
                                     {
                                  if(reporteConLotes.equals("1"))
                                          {
                                              consulta+=" and (sa.cod_lote_produccion not in(''))";
                                          }
                                            else
                                           {
                                                consulta+=" and (sa.cod_lote_produccion = '' or  sa.cod_lote_produccion is null)";
                                            }
                                         }
                   consulta+=")))";
                   System.out.println("consulta "+consulta);
                   st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

                   res=st.executeQuery(consulta);
                   String nombreCapitulo="";
                   String codCapitulo="";
                   String devolucionPivot="";
                   while(res.next())
                   {
                       sumaCapitulo=0;
                       sumaParcial=0;
                        nombreCapitulo=res.getString("NOMBRE_CAPITULO");
                        codCapitulo=res.getString("COD_CAPITULO");
                       %>
                          <tr>
                                           <td  class='border'   colspan="4"  ><b>CAPITULO: <%=nombreCapitulo%></b></td>

                                           
                        </tr>
                       <%
                       String consultaDetalle="select  g.COD_GRUPO,g.NOMBRE_GRUPO from grupos g where g.COD_GRUPO in("+
                             "(select m.COD_GRUPO from MATERIALES m where m.COD_MATERIAL in ("+
                                   " select iad.COD_MATERIAL "
                                + " from DEVOLUCIONES d"
                                + " inner join SALIDAS_ALMACEN sa on d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1"
                                + " and sa.COD_TIPO_SALIDA_ALMACEN IN ("+codTipoSalida+")"+
                                   " inner join INGRESOS_ALMACEN ia on d.COD_DEVOLUCION =ia.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                                   " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN"+
                                   " where ";
                           if(!codAreaDestino.equals("-1")){
                               consultaDetalle+=" sa.COD_AREA_EMPRESA='"+codAreaDestino+"' and" ;
                                }
                            consultaDetalle+=" ia.FECHA_INGRESO_ALMACEN between '"+fechaInicioFormato+" 00:00:00' and '"+fechaFinalFormato+" 23:59:59'"+
                                   " and d.COD_ALMACEN IN ("+codAlmacen+") and ia.COD_ESTADO_INGRESO_ALMACEN=1 and d.COD_ESTADO_DEVOLUCION=1";
                   if(!todos.equals("1"))
                                     {
                                  if(reporteConLotes.equals("1"))
                                          {
                                              consultaDetalle+=" and (sa.cod_lote_produccion not in(''))";
                                          }
                                            else
                                           {
                                                consultaDetalle+=" and (sa.cod_lote_produccion = '' or  sa.cod_lote_produccion is null)";
                                            }
                                         }
                   consultaDetalle+="))) and g.COD_CAPITULO='"+codCapitulo+"' order by g.NOMBRE_GRUPO";
                        System.out.println("consulta grupos "+consultaDetalle);
                        stdetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        resDetalle=stdetalle.executeQuery(consultaDetalle);
                        while(resDetalle.next())
                        { devolucionPivot="";
                           codGrupo=resDetalle.getString("COD_GRUPO");
                           nombreGrupo=resDetalle.getString("NOMBRE_GRUPO");
                           %>
                            <tr>
                                           <td  class='border'colspan="4" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%=nombreGrupo%></b></td>



                               </tr>
                               <%
                               
                             String consultaDevo = "select ia.cod_ingreso_almacen,d.cod_devolucion,d.nro_devolucion,d.cod_estado_devolucion,d.obs_devolucion,sa.nro_salida_almacen,sa.cod_lote_produccion,ia.FECHA_INGRESO_ALMACEN, "+
                                     " m.NOMBRE_MATERIAL,iad.CANT_TOTAL_INGRESO_FISICO,iad.COSTO_UNITARIO_ACTUALIZADO,(iad.CANT_TOTAL_INGRESO_FISICO*iad.COSTO_UNITARIO_ACTUALIZADO) as prod"+
                                  " from DEVOLUCIONES d "+
                                  " inner join SALIDAS_ALMACEN sa on d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1"+
                                      " and sa.COD_TIPO_SALIDA_ALMACEN IN ("+codTipoSalida+")"+
                                  " inner join INGRESOS_ALMACEN ia on d.COD_DEVOLUCION =ia.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                                  " inner join INGRESOS_ALMACEN_DETALLE iad on ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN"+
                                  " left outer join MATERIALES m on m.COD_MATERIAL=iad.COD_MATERIAL"+
                                  " where iad.COD_MATERIAL in (select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO ='"+codGrupo+"')";
                                  if(!codAreaDestino.equals("-1"))
                                 {
                                    consultaDevo+=" and sa.COD_AREA_EMPRESA='"+codAreaDestino+"' ";
                                 }
                             consultaDevo+=" and ia.FECHA_INGRESO_ALMACEN between '"+fechaInicioFormato+" 00:00:00' and '"+fechaFinalFormato+" 23:59:59'"+
                                  " and ia.COD_ALMACEN IN ("+codAlmacen+") and ia.COD_ESTADO_INGRESO_ALMACEN=1 and d.COD_ESTADO_DEVOLUCION=1";
                                 if(!todos.equals("1"))
                                     {
                                  if(reporteConLotes.equals("1"))
                                          {
                                              consultaDevo+=" and (sa.cod_lote_produccion not in(''))";
                                          }
                                            else
                                           {
                                                consultaDevo+=" and (sa.cod_lote_produccion = '' or  sa.cod_lote_produccion is null)";
                                            }
                                         }
                                          consultaDevo+=" order by d.nro_devolucion,m.NOMBRE_MATERIAL ";
                                          System.out.println("consulta devoluciones "+consultaDevo);
                              stDevoluciones=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                              resDevoluciones=stDevoluciones.executeQuery(consultaDevo);
                              sumaParcial=0;
                              while(resDevoluciones.next())
                              {
                                  numeroDevolucion=resDevoluciones.getString("nro_devolucion");
                                   fechaDevolucion=format.format(resDevoluciones.getDate("FECHA_INGRESO_ALMACEN"));
                                   loteProduccion=resDevoluciones.getString("cod_lote_produccion");
                                   observacion=resDevoluciones.getString("obs_devolucion");
                                   if(!devolucionPivot.equals(numeroDevolucion))
                                       {
                                    %>
                                <tr>
                                           <td  class='border' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Nro:</b><%=numeroDevolucion%></td>
                                           <td  class='border' ><b>Fecha:</b><%=fechaDevolucion%></td>
                                           <td  class='border' ><b>Lote:</b><%=loteProduccion%></td>
                                           <td  class='border' ><b>Obs:</b><%=observacion%></td>
                               </tr>
                               <tr>
                                             
                                           <td class='border'  style='border : solid #D8D8D8 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Item</b></td>
                                           <td class='border'  style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>Cantidad</b></td>
                                           <td class='border'  style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>Costo Unitario</b></td>
                                           <td class='border'  style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>Costo Total</b></td>
                                        </tr>

                               <%
                                     devolucionPivot=numeroDevolucion;
                                    }
                                   nombreItem=resDevoluciones.getString("NOMBRE_MATERIAL");
                                   cantidad=resDevoluciones.getDouble("CANT_TOTAL_INGRESO_FISICO");
                                   precioUnitario=resDevoluciones.getDouble("COSTO_UNITARIO_ACTUALIZADO");
                                   precioTotal=resDevoluciones.getDouble("prod");
                                   sumaParcial+=precioTotal;
                                   SumaTotal+=precioTotal;
                                   sumaCapitulo+=precioTotal;
                                   %>
                                    <tr>
                                           <td  class='border' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=nombreItem%></td>
                                           <td  class='border' ><%=formato.format(cantidad)%></td>
                                           <td  class='border' ><%=formato.format(precioUnitario)%></td>
                                           <td  class='border' ><%=formato.format(precioTotal)%></td>
                                    </tr>
                                   <%

                              }

                              resDevoluciones.close();
                              stDevoluciones.close();
                    %>                <tr>
                                           <td class='border' bgcolor="DCDCDC" colspan="3" style='border : solid #DCDCDC 1px;padding-left:5PX; text-align:right'    ><b>TOTAL GRUPO:&nbsp;&nbsp;</b></td>
                                           <td class='border' bgcolor="DCDCDC" style='border : solid #DCDCDC 1px;padding-left:5PX;'    ><b><%=formato.format(sumaParcial)%></b></td>

                </tr>
                      <%
                      }
                         resDetalle.close();
                         stdetalle.close();
                         %>                <tr>
                                           <td class='border' bgcolor="DCDCDC" colspan="3" style='border : solid #000000 1px;padding-left:5PX; text-align:right'    ><b>TOTAL CAPITULO:&nbsp;&nbsp;</b></td>
                                           <td class='border' bgcolor="DCDCDC" style='border : solid #000000 1px;padding-left:5PX;'    ><b><%=formato.format(sumaCapitulo)%></b></td>

                        </tr>
                      <%
                    
                   }
                   res.close();
                   st.close();
                                 
                

               %>
               <tr>
                                           <td class='border' colspan="3"  style='border : solid #000000 1px;padding-left:5PX; text-align:right'    ><b>TOTALES :&nbsp;&nbsp;</b></td>
                                           <td class='border'  style='border : solid #000000 1px;padding-left:5PX;'    ><b><%=formato.format(SumaTotal)%></b></td>

                </tr>
               </table>

                <%


                    con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                %>
            <br>

            <br>
            <div align="center">
               
            </div>
        </form>
    </body>
</html>
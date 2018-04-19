package reportes.reporteResumenDevoluciones;

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
                    String fechaInicio=request.getParameter("fecha1");
                    String fechaFinal=request.getParameter("fecha2");
                    String codAlmacen=request.getParameter("codAlmacen");
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
                                           <td bgcolor="#D3D3D3" class='border'     ><b></b></td>

                                           <td  bgcolor="#D3D3D3" class='border'    ><b>Importe Actualizado</b></td>
                                           
           </tr>

            

                <%
               
                double SumaTotal=0;
                double importeActualizado=0;
                double sumaParcial=0;
                String nombreGrupo="";
                  
                   
                   String consulta="select c.NOMBRE_CAPITULO,c.COD_CAPITULO from CAPITULOS c"+
                                   " where c.COD_CAPITULO in(select g.COD_CAPITULO from grupos g where g.COD_GRUPO in "+
                                   "(select m.COD_GRUPO from MATERIALES m where m.COD_MATERIAL in ("+
                                   " select iad.COD_MATERIAL from DEVOLUCIONES d inner join SALIDAS_ALMACEN sa on d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1"+
                                   " inner join INGRESOS_ALMACEN ia on d.COD_DEVOLUCION =ia.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                                   " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN"+
                                   " where ";
                           if(!codAreaDestino.equals("-1")){
                               consulta+=" sa.COD_AREA_EMPRESA='"+codAreaDestino+"' and" ;
                                }
                            consulta+=" ia.FECHA_INGRESO_ALMACEN between '"+fechaInicioFormato+" 00:00:00' and '"+fechaFinalFormato+" 23:59:59'"+
                                   " and d.COD_ALMACEN='"+codAlmacen+"' and ia.COD_ESTADO_INGRESO_ALMACEN=1 and d.COD_ESTADO_DEVOLUCION=1";
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
                   while(res.next())
                   {
                       sumaParcial=0;
                        nombreCapitulo=res.getString("NOMBRE_CAPITULO");
                        codCapitulo=res.getString("COD_CAPITULO");
                       %>
                          <tr>
                                           <td  class='border'   colspan="2"  ><b>CAPITULO: </b><%=nombreCapitulo%></td>

                                           
                        </tr>
                       <%

                       String consultaDetalle="select g.NOMBRE_GRUPO, ("+
                              " select  ISNULL(SUM((iad.CANT_TOTAL_INGRESO_FISICO*iad.COSTO_UNITARIO_ACTUALIZADO)),0) AS IMPORTE_TOTAL"+
                              " from DEVOLUCIONES d inner join SALIDAS_ALMACEN sa on d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1"+
                              " inner join INGRESOS_ALMACEN ia on d.COD_DEVOLUCION =ia.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                              " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN"+
                              " where ";
                              if(!codAreaDestino.equals("-1"))
                               {
                                  consultaDetalle+=" sa.COD_AREA_EMPRESA='"+codAreaDestino+"' and";
                              }
                             consultaDetalle+=" ia.FECHA_INGRESO_ALMACEN between '"+fechaInicioFormato+" 00:00:00' and '"+fechaFinalFormato+" 23:59:59'"+
                              " and d.COD_ALMACEN='"+codAlmacen+"' and ia.COD_ESTADO_INGRESO_ALMACEN=1 and d.COD_ESTADO_DEVOLUCION=1"+
                              " and iad.COD_MATERIAL in (select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO =g.COD_GRUPO)";
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
                        consultaDetalle+=") as precioActualizado from GRUPOS g where g.COD_CAPITULO in ('"+codCapitulo+"') order by g.NOMBRE_GRUPO";
                        System.out.println("consulta detalle "+consultaDetalle);
                        stdetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        resDetalle=stdetalle.executeQuery(consultaDetalle);
                        while(resDetalle.next())
                        {
                           importeActualizado=resDetalle.getDouble("precioActualizado");
                           nombreGrupo=resDetalle.getString("NOMBRE_GRUPO");
                           if(importeActualizado>0)
                           { SumaTotal+=importeActualizado;
                             sumaParcial+=importeActualizado;
                               %>
                                <tr>
                                           <td  class='border' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%=nombreGrupo%></td>
                                           <td  class='border' ><%=formato.format(importeActualizado)%></td>


                               </tr>
                               <%
                           }
                        }
                        resDetalle.close();
                        stdetalle.close();
                         %>
                                <tr>
                                           <td  class='border' bgcolor="DEDEDE" style="text-align:right; border : solid #000000 1px;"><b>TOTALES CAPITULO:&nbsp;&nbsp;</b></td>
                                           <td  class='border' bgcolor="DCDCDC" style='border : solid #000000 1px;' ><b><%=formato.format(sumaParcial)%></b></td>


                               </tr>
                               <%
                   }

                                 
                

               %>
               <tr>
                                           <td class='border'  style='border : solid #000000 1px;padding-left:5PX; text-align:right'    ><b>TOTALES :&nbsp;&nbsp;</b></td>
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
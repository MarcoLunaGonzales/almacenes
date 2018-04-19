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
                if(codAreaDestino.equals("-1"))
                {
                    String consulta="select ae.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA from AREAS_EMPRESA ae where ae.COD_ESTADO_REGISTRO=1 order by ae.NOMBRE_AREA_EMPRESA ASC";
                    System.out.println("consulta "+consulta);
                    res=st.executeQuery(consulta);
                    res.last();
                    codAreaEmpresa= new String[res.getRow()];
                    nombreAreaEmpresa= new String[res.getRow()];
                    res.absolute(0);
                    int cont=0;
                    while(res.next())
                    {
                        codAreaEmpresa[cont]=res.getString("COD_AREA_EMPRESA");
                        nombreAreaEmpresa[cont]=res.getString("NOMBRE_AREA_EMPRESA");
                        cont++;
                    }
                }
                else
                {

                    codAreaEmpresa= new String[1];
                    nombreAreaEmpresa=new String[1];
                    codAreaEmpresa[0]=codAreaDestino;
                    String consulta="select ae.NOMBRE_AREA_EMPRESA from AREAS_EMPRESA ae where ae.COD_AREA_EMPRESA='"+codAreaDestino+"'";
                    res=st.executeQuery(consulta);
                    if(res.next())
                    {
                        nombreAreaEmpresa[0]=res.getString("NOMBRE_AREA_EMPRESA");
                    }

                }

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


                  String cabecera="RESUMEN DE DEVOLUCIONES POR SECCION ";
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


            

                <%
               boolean primera=true;
               double sumaParcial=0;
               double SumaTotal=0;
               for(int i=0;i<codAreaEmpresa.length;i++)
                {
                   sumaParcial=0;
                    primera=true;
                    nombreAreaDestino=nombreAreaEmpresa[i];
                    codAreaDestino=codAreaEmpresa[i];
                   

                                  String consulta = "select ia.cod_ingreso_almacen,d.cod_devolucion,d.nro_devolucion,d.cod_estado_devolucion,d.obs_devolucion,sa.nro_salida_almacen,sa.cod_lote_produccion,ia.FECHA_INGRESO_ALMACEN "+
                                  " from DEVOLUCIONES d inner join SALIDAS_ALMACEN sa on d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1"+
                                  " inner join INGRESOS_ALMACEN ia on d.COD_DEVOLUCION =ia.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                                  " where sa.COD_AREA_EMPRESA='"+codAreaDestino+"' and ia.FECHA_INGRESO_ALMACEN between '"+fechaInicioFormato+" 00:00:00' and '"+fechaFinalFormato+" 23:59:59'"+
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
                                          consulta+=" order by d.nro_devolucion desc";
                                  System.out.println("consulta "+consulta);
                                  res=st.executeQuery(consulta);
                                  while(res.next())
                                  {
                                       if(primera)
                                           {
                                              %>
                                            <tr class="">
                                            <td class='border' bgcolor="D3D3D3" style='border : solid #D3D3D3 1px;padding-left:5PX;' class="outputTextNormal" colspan="4"  ><b>AREA:<%=nombreAreaDestino%></b></td>

                                            </tr>
                                        <%
                                        primera=false;
                                          }
                                      numeroDevolucion=res.getString("nro_devolucion");
                                      loteProduccion=res.getString("cod_lote_produccion");
                                      SimpleDateFormat sdt=new SimpleDateFormat("dd/MM/yyyy");
                                      observacion=res.getString("obs_devolucion");
                                      fechaDevolucion=sdt.format(res.getDate("FECHA_INGRESO_ALMACEN"));
                                      codIngresoAlmacen=res.getString("cod_ingreso_almacen");
                                      %>
                                      <tr>
                                           <td bgcolor="#DCDCDC" class='border'     ><b>Nro:</b><%=numeroDevolucion%></td>
                                           <td  bgcolor="#DCDCDC" class='border'    ><b>Fecha:</b><%=fechaDevolucion%></td>
                                           <td  bgcolor="#DCDCDC" class='border'    ><b>Lote:</b><%=loteProduccion%></td>
                                           <td bgcolor="#DCDCDC" class='border'   ><b>Obs:</b><%=observacion%></td>
                                      </tr>
                                      <%
                                      consulta="select ISNULL(m.NOMBRE_MATERIAL,'') as mater,iad.CANT_TOTAL_INGRESO_FISICO," +
                                              " iad.COSTO_UNITARIO_ACTUALIZADO,(iad.CANT_TOTAL_INGRESO_FISICO*iad.COSTO_UNITARIO_ACTUALIZADO) as prod"+
                                                " from INGRESOS_ALMACEN_DETALLE iad left outer join MATERIALES m"+
                                                " on iad.COD_MATERIAL=m.COD_MATERIAL where iad.COD_INGRESO_ALMACEN='"+codIngresoAlmacen+"' order by m.NOMBRE_MATERIAL";
                                      System.out.println("consulta "+consulta);
                                      stdetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                      resDetalle=stdetalle.executeQuery(consulta);
                                      if(resDetalle.next())
                                      {
                                        nombreItem=resDetalle.getString("mater");
                                        cantidad=resDetalle.getDouble("CANT_TOTAL_INGRESO_FISICO");
                                        precioUnitario=resDetalle.getDouble("COSTO_UNITARIO_ACTUALIZADO");
                                        precioTotal=resDetalle.getDouble("prod");
                                        sumaParcial+=precioTotal;
                                        SumaTotal+=precioTotal;
                                        %>
                                        <tr>
                                           <td class='border' bgcolor="#DCDCDC"  style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>Item</b></td>
                                           <td class='border' bgcolor="#DCDCDC"  style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>Cantidad</b></td>
                                           <td class='border' bgcolor="#DCDCDC"   style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>Costo Unitario</b></td>
                                           <td class='border' bgcolor="#DCDCDC"   style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>Costo Total</b></td>
                                        </tr>
                                         <tr class="outputText2">
                                           <td class='border' style='border : solid #D3D3D3 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%=nombreItem%></td>
                                           <td class='border' style='border : solid #D3D3D3 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=formato.format(cantidad)%></td>
                                           <td class='border' style='border : solid #D3D3D3 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=formato.format(precioUnitario)%></td>
                                           <td class='border' style='border : solid #D3D3D3 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=formato.format(precioTotal)%></td>
                                        </tr>
                                        <%
                                      }
                                      while(resDetalle.next())
                                      {
                                         
                                        nombreItem=resDetalle.getString("mater");
                                        cantidad=resDetalle.getDouble("CANT_TOTAL_INGRESO_FISICO");
                                        precioUnitario=resDetalle.getDouble("COSTO_UNITARIO_ACTUALIZADO");
                                        precioTotal=resDetalle.getDouble("prod");
                                        sumaParcial+=precioTotal;
                                        SumaTotal+=precioTotal;
                                        %>
                                         <tr class="outputText2">
                                           <td class='border' style='border : solid #D3D3D3 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=nombreItem%></td>
                                           <td class='border' style='border : solid #D3D3D3 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=formato.format(cantidad)%></td>
                                           <td class='border' style='border : solid #D3D3D3 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=formato.format(precioUnitario)%></td>
                                           <td class='border' style='border : solid #D3D3D3 1px;padding-left:5PX;'    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=formato.format(precioTotal)%></td>
                                        </tr>
                                        <%
                                      }
                                      resDetalle.close();
                                  }
                                  if(!primera)
                                      {
                                  %>
                                  <tr>
                                           <td class='border'  colspan="3" style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>TOTAL AREA:</b></td>
                                           <td class='border'   style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b><%=formato.format(sumaParcial)%></b></td>

                                        </tr>
                                  <%
                                  }
                }

               %>
               <tr>
                                           <td class='border'  colspan="3" style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b>TOTAL :</b></td>
                                           <td class='border'  style='border : solid #D8D8D8 1px;padding-left:5PX;'    ><b><%=formato.format(SumaTotal)%></b></td>

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
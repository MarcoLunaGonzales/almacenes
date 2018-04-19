package reportes.reporteResumenSalidas;

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

                     String nombreReporte="RESUMEN DE SALIDAS POR SECCION ";
                        NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat) numeroformato;
                        formato.applyPattern("#,###.##;(#,###.##)");

                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

                        con = Util.openConnection(con);



                        String fechaInicial=request.getParameter("fecha_inicio")==null?"01/01/2011":request.getParameter("fecha_inicio");
                        String fechaInicial2=fechaInicial;
                        String arrayfecha[]=fechaInicial.split("/");
                        fechaInicial=arrayfecha[2] +"/"+ arrayfecha[1]+"/"+arrayfecha[0];
                        String fechaFinal=request.getParameter("fecha_final")==null?"01/01/2011":request.getParameter("fecha_final");
                        String fechaFinal2=fechaFinal;
                        String arrayfecha1[]=fechaFinal.split("/");
                        fechaFinal=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                         String codTipoSalidaAlmacen=request.getParameter("codigosTipos");
                        System.out.println("codigos de tipos"+codTipoSalidaAlmacen);
                        String nombreTipoSalidaAlmacen=request.getParameter("nombreTipoSalida")==null?"0":request.getParameter("nombreTipoSalida");
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
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="5" ><b>Area/Dpto</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="3" ><b>Costo Total Actualizado</b></td>
                                     
                    
                </tr>

                <%
                System.out.println("cod tipo "+codTipoSalidaAlmacen);
                String consulta =" select ae.cod_area_empresa,ae.NOMBRE_AREA_EMPRESA"+
                                 " from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN"+
                                 " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA =s.COD_AREA_EMPRESA"+
                                 " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"+
                                 " and sadi.COD_MATERIAL=sad.COD_MATERIAL"+
                                 " where s.FECHA_SALIDA_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'"+
                                 " and s.COD_ALMACEN ='"+codAlmacen+"' and s.estado_sistema=1 and s.COD_ESTADO_SALIDA_ALMACEN=1";
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
                
                                consulta+="group by ae.cod_area_empresa,ae.NOMBRE_AREA_EMPRESA order by ae.NOMBRE_AREA_EMPRESA";
                
                System.out.println("consulta "+ consulta);
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                String codAreaEmpresa="";
                String nombreAreaEmpresa="";
                double precioTotalActualizado=0;
                double sumaTotal=0;
                double sumaTotalArea=0;
                //para las salidas
                String nroSalida="";
                String nroSalidaPivote="";
                String nroLote="";
                String fechaSalida="";
                String observacion="";
                String item="";
                double cantidad=0;
                double costoUnitario=0;
                ResultSet resDetalle=null;
                Statement stdetalle=null;
                while (rs.next())
                {
                    sumaTotalArea=0;
                    codAreaEmpresa=rs.getString("cod_area_empresa");
                    nombreAreaEmpresa=rs.getString("nombre_area_empresa");
                    
                    %>
                  <tr>
                               <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px' bgcolor="DCDCDC" colspan="8"><b><%=nombreAreaEmpresa%></b></td>
                                
                  </tr>
                                
                  <%

                                
                      stdetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                      consulta="select s.NRO_SALIDA_ALMACEN,ISNULL(s.COD_LOTE_PRODUCCION,'') as COD_LOTE_PRODUCCION,s.FECHA_SALIDA_ALMACEN,SUBSTRING(s.OBS_SALIDA_ALMACEN,1,500) AS OBS_SALIDA_ALMACEN,"+
                               "m.NOMBRE_MATERIAL, sum( sadi.CANTIDAD)as cantidad1,sadi.COSTO_SALIDA_ACTUALIZADO,Isnull((sum( sadi.CANTIDAD)*sadi.COSTO_SALIDA_ACTUALIZADO),0) as costoActualizado"+
                               " from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN"+
                               " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA"+
                               " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL = sad.COD_MATERIAL"+
                               " left outer join MATERIALES m on m.COD_MATERIAL=sad.COD_MATERIAL"+
                               " where s.FECHA_SALIDA_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'"+
                               " and s.COD_ALMACEN = '"+codAlmacen+"' and s.estado_sistema = 1 and s.COD_ESTADO_SALIDA_ALMACEN = 1 " ;
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
                            consulta+=" and s.COD_AREA_EMPRESA='"+codAreaEmpresa+"'"+
                                       " group by s.NRO_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION,s.FECHA_SALIDA_ALMACEN,SUBSTRING(s.OBS_SALIDA_ALMACEN,1,500),"+
                               "m.NOMBRE_MATERIAL,sadi.COSTO_SALIDA_ACTUALIZADO"+
                                      " order by s.NRO_SALIDA_ALMACEN,m.NOMBRE_MATERIAL";

                      System.out.println("consulta detalle"+consulta);
                      resDetalle=stdetalle.executeQuery(consulta);
                      nroSalidaPivote="";
                      while(resDetalle.next())
                      {
                            nroSalida=resDetalle.getString("NRO_SALIDA_ALMACEN");
                            nroLote=resDetalle.getString("COD_LOTE_PRODUCCION");
                            observacion=resDetalle.getString("OBS_SALIDA_ALMACEN");
                            fechaSalida=format.format(resDetalle.getTimestamp("FECHA_SALIDA_ALMACEN"));
                            item=resDetalle.getString("NOMBRE_MATERIAL");
                            cantidad=resDetalle.getDouble("cantidad1");
                            costoUnitario=resDetalle.getDouble("COSTO_SALIDA_ACTUALIZADO");
                            precioTotalActualizado=resDetalle.getDouble("costoActualizado");
                            sumaTotal+=precioTotalActualizado;
                            sumaTotalArea+=precioTotalActualizado;
                            if(!nroSalidaPivote.equals(nroSalida))
                            {
                                %>
                                  <tr>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Nro:</b></td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Nro Lote:</b></td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Fecha:</b></td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Observaciones:</b></td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Item:</b></td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Cantidad:</b></td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Costo U.:</b></td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b>Costo Total:</b></td>
                                
                            </tr>

                                <%
                                nroSalidaPivote=nroSalida;
                            }
                            %>
                            <tr>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=nroSalida%>&nbsp;</td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=nroLote%>&nbsp;</td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=fechaSalida%>&nbsp;</td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=observacion%>&nbsp;</td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=item%>&nbsp;</td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=formato.format(cantidad)%>&nbsp;</td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=formato.format(costoUnitario)%>&nbsp;</td>
                                <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=formato.format(precioTotalActualizado)%>&nbsp;</td>
                            </tr>
                            <%

                      }
                      resDetalle.close();
                      stdetalle.close();

                       %>
               <tr>
               <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px' colspan="7"><b>TOTAL AREA:</b></td>
               <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(sumaTotalArea)%></b></td>
               </tr>
               <%
                  }
               %>
               <tr>
               <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px' colspan="7"><b>TOTAL:</b></td>
               <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(sumaTotal)%></b></td>
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
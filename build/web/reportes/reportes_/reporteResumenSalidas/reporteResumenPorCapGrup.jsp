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
            <h4 align="center">RESUMEN DE SALIDAS POR SECCION </h4>

                    <%
                    try {

              
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
                   


                    %>

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
                    <td height="25px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Costo Total Actualizado</b></td>
                                     
                    
                </tr>

                <%

                String consulta =" select c.NOMBRE_CAPITULO,c.COD_CAPITULO "+
                                  " from CAPITULOS c where c.COD_CAPITULO in (select g.COD_CAPITULO from grupos g where g.COD_GRUPO in ("+
                                  " select m.COD_GRUPO from MATERIALES m where m.COD_MATERIAL in ("+
                                  " select sad.COD_MATERIAL"+
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
                
                                consulta+=")) ) order by c.NOMBRE_CAPITULO";
                
                System.out.println("consulta "+ consulta);
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta);
                Statement stDetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resDetalle=null;
                double cantTotalActualizado=0;
                String nombreCapitulo="";
                String codCapitulo="";
                String nombreGrupo="";
                double sumaTotal=0;
                double sumTotalCapitulo=0;
                while (res.next()) {
                    sumTotalCapitulo=0;
                    nombreCapitulo=res.getString("NOMBRE_CAPITULO");
                    codCapitulo=res.getString("COD_CAPITULO");
                        %>
                         <tr>
                        <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px' colspan="2"><b>Capítulo:</b><%=nombreCapitulo%></td>

                        </tr>
                        <%
                        String consultaDetalle="select sum(sadi.CANTIDAD*sadi.COSTO_SALIDA_ACTUALIZADO) as cantidad,g.NOMBRE_GRUPO "+
                                 " from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN"+
                                 " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA =s.COD_AREA_EMPRESA"+
                                 " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"+
                                 " and sadi.COD_MATERIAL=sad.COD_MATERIAL"+
                                 " inner join MATERIALES m on m.COD_MATERIAL=sadi.COD_MATERIAL"+
                                 " inner join grupos g on g.COD_GRUPO=m.COD_GRUPO"+
                                 " where s.FECHA_SALIDA_ALMACEN between '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'"+
                                 " and s.COD_ALMACEN ='"+codAlmacen+"' and s.estado_sistema=1 and s.COD_ESTADO_SALIDA_ALMACEN=1"+
                                 " and g.COD_CAPITULO='"+codCapitulo+"'";
                                 if(!codSeccion.equals("-1"))
                                {
                                    consultaDetalle+=" and s.COD_AREA_EMPRESA ='"+codSeccion+"'";
                                }
                                if(!codTipoSalidaAlmacen.equals("-1"))
                                   {
                                 consultaDetalle+=" and s.COD_TIPO_SALIDA_ALMACEN in ("+codTipoSalidaAlmacen+")";
                                 }
                                else
                                {
                                 consultaDetalle+=" and s.COD_TIPO_SALIDA_ALMACEN in (select t.COD_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN t where t.COD_ESTADO_REGISTRO = 1) ";
                                }
                            if(todosLote.equals("0"))
                                {
                                if(conLote.equals("0"))
                                {
                                   consultaDetalle+=" and  (s.cod_lote_produccion = '' or s.cod_lote_produccion is null)";
                                }
                                else
                                {
                                    consultaDetalle+=" and  (s.cod_lote_produccion <> '')";
                                }
                                }

                                consultaDetalle+="GROUP by g.NOMBRE_GRUPO order by g.NOMBRE_GRUPO";

                                System.out.println("consulta detalee "+ consultaDetalle);
                                    stDetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                    resDetalle=stDetalle.executeQuery(consultaDetalle);
                                    while(resDetalle.next())
                                    {
                                        nombreGrupo=resDetalle.getString("NOMBRE_GRUPO");
                                        cantTotalActualizado=resDetalle.getDouble("cantidad");
                                        sumaTotal+=cantTotalActualizado;
                                        sumTotalCapitulo+=cantTotalActualizado;

                                           %>
                                         <tr>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=nombreGrupo%></td>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><%=formato.format(cantTotalActualizado)%></td>

                                        </tr>
                                <%

                                    }
                                    resDetalle.close();
                                    stDetalle.close();
                                    %>
                                    <tr>
                                            <td class='border' align='right' style='border-bottom:1px solid #cccccc;padding:8px'><b>TOTALES CAPITULO: &nbsp;&nbsp;</b></td>
                                            <td class='border' align='left' style='border-bottom:1px solid #cccccc;padding:8px'><b><%=formato.format(sumTotalCapitulo)%></b></td>

                                        </tr>
                                        <%
                                }

                         res.close();
                         st.close();
                         con.close();
               %>
               <tr>
               <td class='border' align='right' style='border-bottom:1px solid #cccccc;padding:8px'><b>TOTAL:&nbsp;&nbsp;</b></td>
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
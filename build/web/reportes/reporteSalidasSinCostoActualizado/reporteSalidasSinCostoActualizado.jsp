
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
                        String codFilial=request.getParameter("codFilial");
                        NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat) numeroformato;
                        formato.applyPattern("#,##0.00");

                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                        con = Util.openConnection(con);
                         Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        ResultSet res = null;


                        String fechaInicial=request.getParameter("fecha_inicio");
                        String fechaInicial2=fechaInicial;
                        String arrayfecha[]=fechaInicial.split("/");
                        if(!fechaInicial.equals(""))
                            {
                        fechaInicial=arrayfecha[2] +"/"+ arrayfecha[1]+"/"+arrayfecha[0];
                        }
                        String fechaFinal=request.getParameter("fecha_final");
                        String fechaFinal2=fechaFinal;
                        String arrayfecha1[]=fechaFinal.split("/");
                        if(!fechaFinal.equals(""))
                        {
                        fechaFinal=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                        }
                        String nroSalida=request.getParameter("nroSalida");
                        String codLote=request.getParameter("nroLote");
                        
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                       String codAreaDestino=request.getParameter("codAreaDestino");
                       String nombreAreaDestino="";
                       String nombreTipoSalida="";
                       String codComprod=request.getParameter("codProducto");
                       String tipoSalida=request.getParameter("codTipoSalida");
                       System.out.println("codFilial "+codFilial+"codAlmacen"+codAlmacen+"tipoSalida"+tipoSalida+"producto"+codComprod+"NroSalida"+nroSalida+"nroLote"+codLote+"area destino"+codAreaDestino);
                        
                    if(codFilial.equals("-1"))
                    {

                        codAlmacen="select a.COD_ALMACEN from ALMACENES a where a.COD_ESTADO_REGISTRO=1";
                    }
                    if(codAlmacen.equals("-1"))
                    {
                        codAlmacen="select a.COD_ALMACEN from ALMACENES a where a.COD_FILIAL="+codFilial+" and a.COD_ESTADO_REGISTRO=1";

                    }
                      String consultaAlmacen="select a.NOMBRE_ALMACEN from almacenes a  WHERE A.COD_ALMACEN IN ("+codAlmacen+")";
                      res=st.executeQuery(consultaAlmacen);
                      String nombreAlmacen="";
                      if(res.next())
                      {
                            nombreAlmacen=res.getString("NOMBRE_ALMACEN");
                      }
                      while(res.next())
                      {
                          nombreAlmacen+=","+res.getString("NOMBRE_ALMACEN");
                      }
                      String nombreAlmacenDestino="";
                      if(!codAreaDestino.equals("-1"))
                     {
                      consultaAlmacen="select ae.nombre_area_empresa from areas_empresa ae where ae.cod_area_empresa='"+codAreaDestino+"'";
                      res=st.executeQuery(consultaAlmacen);
                      if(res.next())
                      {
                          nombreAreaDestino=res.getString("nombre_area_empresa");
                      }
                      }
                      else
                          nombreAreaDestino="TODOS";
                      if(!tipoSalida.equals("-1"))
                      {
                          consultaAlmacen="select tsa.NOMBRE_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN tsa where tsa.COD_TIPO_SALIDA_ALMACEN="+tipoSalida;
                          res=st.executeQuery(consultaAlmacen);
                          if(res.next())
                          {
                              nombreTipoSalida=res.getString("NOMBRE_TIPO_SALIDA_ALMACEN");
                          }
                      }
                      else
                          nombreTipoSalida="TODOS";


                    %>
                    <h4 align="center">REPORTE DE DEVOLUCIONES SIN COSTO ACTUALIZADO</h4>
            <br>
            <table width="70%" border="0" align="center">
                <tr>
                    <td>
            <img src="../../img/cofar.png" alt="logo cofar" align="left" />
            </td><td>
            <table border="0" class="outputText1" >
                <tbody>
                    <tr>
                        <td><b>N° Salida</b></td>
                        <td><%=nroSalida%></td>
                         <td>&nbsp;&nbsp;&nbsp;<b>Almacen:</b></td>
                        <td><%=nombreAlmacen%></td>
                    </tr>
                    <tr>
                        <td><b>Area/Dpto Destino:</b></td>
                        <td><%=nombreAreaDestino%></td>
                    </tr>
                      <tr>
                        <td><b>TipoSalida:</b></td>
                        <td><%=nombreTipoSalida%></td>
                    </tr>
                    
                    <tr>
                        <td><b>De Fecha:</b></td>
                        <td><%=fechaInicial2%></td>
                        <td>&nbsp;&nbsp;&nbsp;<b>A fecha:</b></td>
                        <td><%=fechaFinal2%></td>
                    </tr>
                    
                </tbody>
            </table>
            </td>
            </tr>
            </table>



            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <%
                
               String consulta="select sa.nro_salida_almacen,sa.FECHA_SALIDA_ALMACEN,ae.NOMBRE_AREA_EMPRESA,tsa.NOMBRE_TIPO_SALIDA_ALMACEN,"+
                               " m.NOMBRE_MATERIAL,sad.CANTIDAD_SALIDA_ALMACEN,um.ABREVIATURA"+
                               " from salidas_almacen sa inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN"+
                               " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"+
                               " and sad.COD_MATERIAL=sadi.COD_MATERIAL and (sadi.COSTO_SALIDA_ACTUALIZADO is null or sadi.COSTO_SALIDA_ACTUALIZADO=0)"+
                               " left outer join materiales m on m.COD_MATERIAL=sad.COD_MATERIAL"+
                               " left outer join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA=sa.COD_AREA_EMPRESA"+
                               " left outer join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN=sa.COD_TIPO_SALIDA_ALMACEN"+
                               " left outer join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=sad.COD_UNIDAD_MEDIDA"+
                               " where sa.estado_sistema=1 and sa.cod_estado_salida_almacen=1 ";
                             if(!codAreaDestino.equals("-1"))
                             {
                              consulta+=" and sa.cod_area_empresa="+codAreaDestino;
                             }
                             if(!codComprod.equals("-1"))
                             {
                                 consulta+=" and sa.cod_prod='"+codComprod+"'";
                             }
                             if(!nroSalida.equals(""))consulta+=" and sa.nro_salida_almacen='"+nroSalida+"'";
                             if(!codLote.equals(""))consulta+=" and sa.cod_lote_produccion='"+codLote+"'";
                             if(!tipoSalida.equals("-1"))consulta+=" and sa.cod_tipo_salida_almacen='"+tipoSalida +"'";
                             if((!fechaInicial.equals(""))&&(!fechaFinal.equals("")))consulta+=" and sa.fecha_salida_almacen BETWEEN '"+fechaInicial+" 00:00:00' and '"+fechaFinal+" 23:59:59'";
                             consulta+=" and sa.cod_almacen in ("+codAlmacen+")"+
                             " group by sa.nro_salida_almacen,sa.FECHA_SALIDA_ALMACEN,ae.NOMBRE_AREA_EMPRESA,tsa.NOMBRE_TIPO_SALIDA_ALMACEN,"+
                             " m.NOMBRE_MATERIAL,sad.CANTIDAD_SALIDA_ALMACEN,um.ABREVIATURA"+
                             " order by sa.nro_salida_almacen desc";
           System.out.println("consulta "+ consulta);
                con=Util.openConnection(con);
                res=st.executeQuery(consulta);
                String cabecera="";
                String nroSalidaMostrar="";
                String fechaSalida="";
                String areaDpto="";
                String tipoSolicitud="";
                String nombreMaterial="";
                String nombreUnidadMedida="";
                double cantidadSalida=0;
                String aux1="";
                while (res.next()) {
                    nroSalidaMostrar=res.getString("nro_salida_almacen");
                    fechaSalida=sdf.format(res.getTimestamp("FECHA_SALIDA_ALMACEN"));
                    areaDpto=res.getString("NOMBRE_AREA_EMPRESA");
                    tipoSalida=res.getString("NOMBRE_TIPO_SALIDA_ALMACEN");
                    aux1=nroSalidaMostrar+fechaSalida+areaDpto+tipoSalida;
                    if(!cabecera.equals(aux1))
                    {
                        %>
                         <tr bgcolor="#dddddd">
                    <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b>N°:&nbsp; </b><%=nroSalidaMostrar%></td>
                    <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b>Fecha:&nbsp; </b><%=fechaSalida%></td>
                    <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b>Area/Dpto:&nbsp; </b><%=areaDpto%></td>
                    <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b>Tipo:</b><%=tipoSalida%></td>

                    </tr>
                    <tr>
                  
                    <td class='border' align='left'colspan="2" ><b>Item</b></td>
                    <td class='border' align='left' ><b>Cantidad</b></td>
                    <td class='border' align='left' ><b>Unid.</b></td>

                    </tr>
                        <%
                        cabecera=aux1;
                    }
                    nombreMaterial=res.getString("NOMBRE_MATERIAL");
                    nombreUnidadMedida=res.getString("ABREVIATURA");
                    cantidadSalida=res.getDouble("CANTIDAD_SALIDA_ALMACEN");

                    %>
                    <tr >
                    <td class='border' align='left' colspan="2" >&nbsp;&nbsp;&nbsp; <%=nombreMaterial%></td>
                    <td class='border' align='left' ><%=formato.format(cantidadSalida)%></td>
                    <td class='border' align='left' ><%=nombreUnidadMedida%></td>
                   </tr>
                    <%
                  }
                res.close();
                st.close();
                con.close();


               %>
               
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
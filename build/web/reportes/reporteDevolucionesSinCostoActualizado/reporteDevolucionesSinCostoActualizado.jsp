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
                        String NroDevolcion=request.getParameter("NroDevolucion");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                       String codAreaDestino=request.getParameter("codSeccion");

                        System.out.println("parametros" + fechaInicial +"" +fechaFinal+" area destino "+codAreaDestino+" nro devolucion "+NroDevolcion);
                          if(codFilial.equals("-1"))
                    {

                        codAlmacen="select a.COD_ALMACEN from ALMACENES a where a.COD_ESTADO_REGISTRO=1";
                    }
                    if(codAlmacen.equals("-1"))
                    {
                        codAlmacen="select a.COD_ALMACEN from ALMACENES a where a.COD_FILIAL="+codFilial+" and a.COD_ESTADO_REGISTRO=1";

                    }
                      String consultaAlmacen="select a.NOMBRE_ALMACEN from almacenes a  WHERE A.COD_ALMACEN IN ("+codAlmacen+")";
                      System.out.println("consulta almacen: "+consultaAlmacen);
                      res=st.executeQuery(consultaAlmacen);
                      String nombreAlmacen="";
                      while(res.next()){
                            nombreAlmacen +=(nombreAlmacen.length() > 0 ? ",":"")+res.getString("NOMBRE_ALMACEN");
                      }
                      String nombreAlmacenDestino="";
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
                        <td><b>N° Devolucion:</b></td>
                        <td><%=NroDevolcion%></td>
                         <td>&nbsp;&nbsp;&nbsp;<b>Almacen:</b></td>
                        <td><%=nombreAlmacen%></td>
                    </tr>
                    <tr>
                        <td><b>Area/Dpto Destino:</b></td>
                        <td><%=nombreAlmacenDestino%></td>
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
                
               String consulta="select d.nro_devolucion,ia.FECHA_INGRESO_ALMACEN,isnull(sa.COD_LOTE_PRODUCCION,'') as COD_LOTE_PRODUCCION,ae.NOMBRE_AREA_EMPRESA,"+
                                        " iad.COD_MATERIAL,ISNULL(m.NOMBRE_MATERIAL,'') AS NOMBRE_MATERIAL,ISNULL(um.ABREVIATURA,'') AS NOMBRE_UNIDAD_MEDIDA,SUM(iad.CANT_TOTAL_INGRESO_FISICO) as cantidadIngreso"+
                                        " ,a.NOMBRE_ALMACEN,ia.NRO_INGRESO_ALMACEN"+
                               " from devoluciones d inner join salidas_almacen sa on"+
                               " sa.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and d.ESTADO_SISTEMA=1 inner join ingresos_almacen ia on"+
                               " ia.COD_DEVOLUCION=d.COD_DEVOLUCION and ia.COD_ALMACEN=d.COD_ALMACEN"+
                               " inner join INGRESOS_ALMACEN_DETALLE iad on ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN"+
                               " and (iad.costo_unitario_actualizado is null or iad.costo_unitario_actualizado=0)"+
                               " left outer join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA=sa.COD_AREA_EMPRESA"+
                               " inner join almacenes a on a.COD_ALMACEN = ia.COD_ALMACEN"+
                               " left outer join MATERIALES m on m.COD_MATERIAL=iad.COD_MATERIAL "+
                               " left outer join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=iad.COD_UNIDAD_MEDIDA"+
                               " where ";
                               if(!NroDevolcion.equals(""))consulta+="  d.nro_devolucion like'"+NroDevolcion+"' and ";
                               if((!fechaInicial.equals(""))&&(!fechaFinal.equals("")))consulta+=" ia.fecha_ingreso_almacen>='"+fechaInicial+" 00:00:00 'and ia.fecha_ingreso_almacen<='"+fechaFinal+" 23:59:59' and ";
                               consulta+=" ia.cod_almacen in ("+codAlmacen+")"+
                                         " and ia.cod_estado_ingreso_almacen=1 and d.cod_estado_devolucion=1"+
                                         " group by d.nro_devolucion,ia.FECHA_INGRESO_ALMACEN,sa.COD_LOTE_PRODUCCION,ae.NOMBRE_AREA_EMPRESA,"+
                                         " iad.COD_MATERIAL,m.NOMBRE_MATERIAL,um.ABREVIATURA,a.NOMBRE_ALMACEN,ia.NRO_INGRESO_ALMACEN"+
                                         " order by  ia.fecha_ingreso_almacen,d.nro_devolucion,sa.COD_LOTE_PRODUCCION,ae.NOMBRE_AREA_EMPRESA";
           System.out.println("consulta "+ consulta);
                con=Util.openConnection(con);
                res=st.executeQuery(consulta);
                String Cabecera="";
                String NroDevolucion="";
                String fechaIngreso="";
                String loteProduccion="";
                String areaDepartamento="";
                String aux1="";
                String codMaterial="";
                String nombreMaterial="";
                String nombreUM="";
                double cantidadTotalIngreso=0;
                while (res.next()) {
                    NroDevolucion=res.getString("nro_devolucion");
                    fechaIngreso=sdf.format(res.getTimestamp("FECHA_INGRESO_ALMACEN"));
                    loteProduccion=res.getString("COD_LOTE_PRODUCCION");
                    areaDepartamento=res.getString("NOMBRE_AREA_EMPRESA");
                    aux1=NroDevolucion+fechaIngreso+loteProduccion+areaDepartamento;
                    if(!Cabecera.equals(aux1))
                    {
                    %>
                    <tr bgcolor="#dddddd">
                        <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'>
                            <b>N° Devolución:&nbsp; </b><%=NroDevolucion%><br/>
                            <b>N° Ingreso:</b> <%=(res.getInt("NRO_INGRESO_ALMACEN"))%><br/>
                            <b>Almacen:</b> <%=(res.getString("NOMBRE_ALMACEN"))%><br/>
                        </td>
                    <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b>Fecha:&nbsp; </b><%=fechaIngreso%></td>
                    <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b>Lote:&nbsp; </b><%=loteProduccion%></td>
                    <td class='border' align='left' style='border:1px solid #cccccc;padding:8px'><b>Area/Dpto:</b><%=areaDepartamento%></td>

                    </tr>
                    <tr>
                    <td class='border' align='left' ><b>Codigo</b></td>
                    <td class='border' align='left' ><b>Item</b></td>
                    <td class='border' align='left' ><b>Unid.</b></td>
                    <td class='border' align='left' ><b>Cantidad</b></td>

                    </tr>
                    <%
                    Cabecera=aux1;
                    }
                    codMaterial=res.getString("COD_MATERIAL");
                    nombreMaterial=res.getString("NOMBRE_MATERIAL");
                    nombreUM=res.getString("NOMBRE_UNIDAD_MEDIDA");
                    cantidadTotalIngreso=res.getDouble("cantidadIngreso");
                      %>
                    <tr >
                    <td class='border' align='left' >&nbsp;&nbsp;&nbsp; <%=codMaterial%></td>
                    <td class='border' align='left' ><%=nombreMaterial%></td>
                    <td class='border' align='left' ><%=nombreUM%></td>
                    <td class='border' align='left' ><%=formato.format(cantidadTotalIngreso)%></td>

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
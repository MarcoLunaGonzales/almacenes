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
String codPresentacion = "";
String nombrePresentacion = "";
String linea_mkt = "";
String agenciaVenta = "";
%>
<%! public String nombrePresentacion1() {



    String nombreproducto = "";
//ManagedAccesoSistema bean1=(ManagedAccesoSistema)com.cofar.util.Util.getSessionBean("ManagedAccesoSistema");
    try {
        con = Util.openConnection(con);
        String sql_aux = "select cod_presentacion, nombre_producto_presentacion from presentaciones_producto where cod_presentacion='" + codPresentacion + "'";
        System.out.println("PresentacionesProducto:sql:" + sql_aux);
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery(sql_aux);
        while (rs.next()) {
            String codigo = rs.getString(1);
            nombreproducto = rs.getString(2);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return nombreproducto;
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../../js/general.js"></script>
    </head>
    <body>
        <form>
            <h4 align="center">RESUMEN DE SALIDAS DE ALMACEN POR SECCION</h4>

                    <%
                    try {

                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat)nf;
                        formato.applyPattern("#,###.00");

                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat format1=new SimpleDateFormat("HH:mm:ss");

                        con = Util.openConnection(con);



                        String fechaInicial=request.getParameter("fecha1")==null?"01/01/2011":request.getParameter("fecha1");
                        String fechaInicial1 = fechaInicial;
                        String arrayfecha1[]=fechaInicial.split("/");
                        fechaInicial=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                        
                        String fechaFinal=request.getParameter("fecha2")==null?"01/01/2011":request.getParameter("fecha2");
                        String fechaFinal1 = fechaFinal;
                        String arrayfecha2[]=fechaFinal.split("/");
                        fechaFinal=arrayfecha2[2] +"/"+ arrayfecha2[1]+"/"+arrayfecha2[0];

                        //codMaterial = codMaterial.substring(1);
                        String codTipoSalidaArray=request.getParameter("codTipoSalidaArray")==null?"''":request.getParameter("codTipoSalidaArray");
                        String nomTipoSalidaArray=request.getParameter("nomTipoSalidaArray")==null?"''":request.getParameter("nomTipoSalidaArray");

                        String codAreaEmpresa=request.getParameter("codAreaEmpresa")==null?"''":request.getParameter("codAreaEmpresa");

                        String codAlmacen = request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String codFilial=request.getParameter("codFilial")==null?"''":request.getParameter("codFilial");

                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");

         
                        System.out.println("parametros" + fechaInicial + " " + fechaFinal + "  " + codAlmacen + "  " +
                                "" + codFilial + " " +  nombreFilial + "  " + nombreAlmacen + " " );
                        double costoTotal = 0;
                        


                    %>

                    <%--
            <div class="outputText0" align="center">
                PROGRAMA PRODUCCION: <%= nombreProgramaProduccionPeriodo %> <br>
                AREA(S) : <%= arrayNombreAreaEmpresa %><br>
                DE <%= arraydesde[0] +"/"+ arraydesde[1]+"/"+arraydesde[2] %> <br>
                HASTA <%= arrayhasta[0] +"/"+ arrayhasta[1]+"/"+arrayhasta[2] %>
                <%--PRODUCTO<%=nombreComponenteProd%>--%>
                <%--
            </div--%>

            <br>
            <table width="70%" border="0" align="center">
                <tr>
                    <td>
            <img src="../../../img/cofar.png" alt="logo cofar" align="left" />
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
                        <td><b>Tipo Salida</b></td>
                        <td><%=nomTipoSalidaArray%></td>
                    </tr>

                    <tr>
                        <td><b>Fecha Inicial</b></td>
                        <td><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                    </tr>
                </tbody>
            </table>
            </td>
            </tr>
            </table>
            
            <BR>
            <BR>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='30PX' class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#f2f2f2"  align="center" ><b>Cuenta</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#f2f2f2"  align="center" ><b>Sección</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#f2f2f2"  align="center"><b>Imp. Total</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" class="border"  bgcolor="#f2f2f2"  align="center"><b>Devolución</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#f2f2f2"  align="center"><b>Total Neto</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#f2f2f2"  align="center"><b>Imp. Total</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" class="border"  bgcolor="#f2f2f2"  align="center"><b>Devolución</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;" bgcolor="#f2f2f2"  align="center"><b>Total Neto</b></td>
                    
                </tr>

                <%
                        String consulta = "  SELECT AE.COD_AREA_EMPRESA,AE.NOMBRE_AREA_EMPRESA,";
                        consulta += " (SELECT SUM(SADI.CANTIDAD*SADI.COSTO_SALIDA_ACTUALIZADO)";
                        consulta += " FROM SALIDAS_ALMACEN_DETALLE_INGRESO SADI,SALIDAS_ALMACEN SA";
                        consulta += " WHERE  SA.COD_ESTADO_SALIDA_ALMACEN=1 AND SADI.COD_SALIDA_ALMACEN=SA.COD_SALIDA_ALMACEN AND SA.ESTADO_SISTEMA=1";
                        consulta += " AND SA.COD_ALMACEN  in ("+codAlmacen+") ";
                        if(!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")){
                            consulta += " AND SA.cod_area_empresa="+codAreaEmpresa+" ";
                        }
                        if(!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")){
                            consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN ("+codTipoSalidaArray+") ";
                        }
                        consulta += " AND SA.COD_LOTE_PRODUCCION<>'' AND SA.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA";
                        if(!fechaInicial.equals("") && !fechaFinal.equals("")){
                            consulta += " AND SA.FECHA_SALIDA_ALMACEN>='"+fechaInicial+"' AND SA.FECHA_SALIDA_ALMACEN<='"+fechaFinal+" 23:59:59' " ;
                        }

                        consulta += " ) AS IMP_TOTAL_CL,";
                        consulta += " (SELECT SUM(DDE.CANTIDAD_DEVUELTA*IAD.COSTO_UNITARIO_ACTUALIZADO)";
                        consulta += " FROM DEVOLUCIONES D, DEVOLUCIONES_DETALLE DD,DEVOLUCIONES_DETALLE_ETIQUETAS DDE,INGRESOS_ALMACEN IA,INGRESOS_ALMACEN_DETALLE IAD,";
                        consulta += " SALIDAS_ALMACEN SA";
                        consulta += " WHERE SA.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA AND SA.COD_ESTADO_SALIDA_ALMACEN=1 AND SA.ESTADO_SISTEMA=1";
                        consulta += " AND DD.COD_MATERIAL=IAD.COD_MATERIAL AND D.ESTADO_SISTEMA = 1 AND IA.COD_ALMACEN = D.COD_ALMACEN ";
                        if(!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")){
                            consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN ("+codTipoSalidaArray+") ";
                        }
                        if(!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")){
                            consulta += " AND SA.cod_area_empresa="+codAreaEmpresa+" ";
                        }
                        consulta += " AND SA.COD_ALMACEN in ("+codAlmacen+") AND D.COD_ALMACEN in ("+codAlmacen+")  AND IA.COD_ALMACEN in ("+codAlmacen+")";
                        
                        consulta += " AND SA.COD_LOTE_PRODUCCION<>'' AND SA.COD_SALIDA_ALMACEN=D.COD_SALIDA_ALMACEN";
                        consulta += " AND D.COD_DEVOLUCION=IA.COD_DEVOLUCION AND D.COD_ESTADO_DEVOLUCION=1";
                        consulta += " AND IA.COD_ESTADO_INGRESO_ALMACEN=1 AND D.COD_DEVOLUCION=DD.COD_DEVOLUCION AND DD.COD_DEVOLUCION=DDE.COD_DEVOLUCION";
                        consulta += " AND DD.COD_MATERIAL=DDE.COD_MATERIAL AND IA.COD_INGRESO_ALMACEN=IAD.COD_INGRESO_ALMACEN";
                        if(!fechaInicial.equals("") && !fechaFinal.equals("")){
                             consulta += " AND IA.FECHA_INGRESO_ALMACEN>='"+fechaInicial+"' AND IA.FECHA_INGRESO_ALMACEN<='"+fechaFinal+" 23:59:59' " ;
                        }
                       
                        consulta += " ) AS DEVOLUCIONES_CL,";
                        consulta += " (SELECT SUM(SADI.CANTIDAD*SADI.COSTO_SALIDA_ACTUALIZADO)";
                        consulta += " FROM SALIDAS_ALMACEN_DETALLE_INGRESO SADI,SALIDAS_ALMACEN SA";
                        consulta += " WHERE  SA.COD_ESTADO_SALIDA_ALMACEN=1 AND SADI.COD_SALIDA_ALMACEN=SA.COD_SALIDA_ALMACEN AND SA.ESTADO_SISTEMA=1";
                        consulta += " AND SA.COD_ALMACEN in ("+codAlmacen+") ";
                        if(!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")){
                            consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN ("+codTipoSalidaArray+") ";
                        }
                        if(!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")){
                            consulta += " AND SA.cod_area_empresa="+codAreaEmpresa+" ";
                        }
                        //consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN (3) " ;
                        consulta += " AND (SA.COD_LOTE_PRODUCCION='' OR SA.COD_LOTE_PRODUCCION IS NULL)";
                        consulta += " AND SA.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA " ;
                        if(!fechaInicial.equals("") && !fechaFinal.equals("")){
                             consulta += " AND SA.FECHA_SALIDA_ALMACEN>='"+fechaInicial+"' AND SA.FECHA_SALIDA_ALMACEN<='"+fechaFinal+" 23:59:59' " ;
                        }
                        
                        consulta += " )AS IMP_TOTAL_SL,";
                        consulta += " (SELECT SUM(DDE.CANTIDAD_DEVUELTA*IAD.COSTO_UNITARIO_ACTUALIZADO)";
                        consulta += " FROM DEVOLUCIONES D, DEVOLUCIONES_DETALLE DD,DEVOLUCIONES_DETALLE_ETIQUETAS DDE,INGRESOS_ALMACEN IA,INGRESOS_ALMACEN_DETALLE IAD,";
                        consulta += " SALIDAS_ALMACEN SA";
                        consulta += " WHERE SA.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA AND SA.COD_ESTADO_SALIDA_ALMACEN=1 AND SA.ESTADO_SISTEMA=1";
                        consulta += " AND DD.COD_MATERIAL=IAD.COD_MATERIAL AND D.ESTADO_SISTEMA = 1 AND IA.COD_ALMACEN = D.COD_ALMACEN ";
                        if(!codTipoSalidaArray.equals("") && !codTipoSalidaArray.equals("0")){
                            consulta += " AND SA.COD_TIPO_SALIDA_ALMACEN IN ("+codTipoSalidaArray+") ";
                        }
                        if(!codAreaEmpresa.equals("") && !codAreaEmpresa.equals("0")){
                            consulta += " AND SA.cod_area_empresa="+codAreaEmpresa+" ";
                        }
                        consulta += " AND SA.COD_ALMACEN in ("+codAlmacen+") AND D.COD_ALMACEN in ("+codAlmacen+") AND IA.COD_ALMACEN in ("+codAlmacen+")";
                        //consulta += " AND SA.COD_ALMACEN=1 AND D.COD_ALMACEN=1  AND IA.COD_ALMACEN=1";
                        consulta += " AND (SA.COD_LOTE_PRODUCCION='' OR SA.COD_LOTE_PRODUCCION IS NULL)";
                        consulta += " AND SA.COD_SALIDA_ALMACEN=D.COD_SALIDA_ALMACEN AND D.COD_DEVOLUCION=IA.COD_DEVOLUCION AND D.COD_ESTADO_DEVOLUCION=1";
                        consulta += " AND IA.COD_ESTADO_INGRESO_ALMACEN=1 AND D.COD_DEVOLUCION=DD.COD_DEVOLUCION AND DD.COD_DEVOLUCION=DDE.COD_DEVOLUCION";
                        consulta += " AND DD.COD_MATERIAL=DDE.COD_MATERIAL AND IA.COD_INGRESO_ALMACEN=IAD.COD_INGRESO_ALMACEN";
                        if(!fechaInicial.equals("") && !fechaFinal.equals("")){
                             consulta += " AND IA.FECHA_INGRESO_ALMACEN>='"+fechaInicial+"' AND IA.FECHA_INGRESO_ALMACEN<='"+fechaFinal+" 23:59:59' " ;
                        }
                        
                        consulta += " )AS DEVOLUCIONES_SL";
                        consulta += " FROM AREAS_EMPRESA AE WHERE AE.COD_ESTADO_REGISTRO=1";
                        consulta += " ORDER BY AE.NOMBRE_AREA_EMPRESA";


                System.out.println("consulta 1 "+ consulta);
                
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                double imp_cl=0;
                double imp_sl=0;
                double dev_cl=0;
                double dev_sl=0;
                
                while (rs.next()) {
                    imp_cl=0;
                    imp_sl=0;
                    dev_cl=0;
                    dev_sl=0;
                    if(rs.getString("IMP_TOTAL_CL")!=null){
                        imp_cl=rs.getDouble("IMP_TOTAL_CL");
                    }
                    if(rs.getString("IMP_TOTAL_SL")!=null){
                        imp_sl=rs.getDouble("IMP_TOTAL_SL");
                    }
                    if(rs.getString("DEVOLUCIONES_CL")!=null){
                        dev_cl=rs.getDouble("DEVOLUCIONES_CL");
                    }
                    if(rs.getString("DEVOLUCIONES_SL")!=null){
                        dev_sl=rs.getDouble("DEVOLUCIONES_SL");
                    }


                    out.print("<tr>");
                    out.print("<td HEIGHT='30PX' class='border' align='left'  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-left:5PX;' >&nbsp;</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='left'  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-left:5PX;' >"+(rs.getString("NOMBRE_AREA_EMPRESA")==null?"":rs.getString("NOMBRE_AREA_EMPRESA"))+"</td>");
                    //out.print("<td class='border' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;;padding-left:5PX;'    >"+format.format( rs.getDate("FECHA_INGRESO_ALMACEN")) +" &nbsp; "+ format1.format( rs.getTimestamp("FECHA_INGRESO_ALMACEN").getTime()) +"</td>");
                    out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'    >"+(rs.getString("IMP_TOTAL_CL")==null?"0.0":(formato.format(rs.getDouble("IMP_TOTAL_CL"))))+"</td>");
                    out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >"+(rs.getString("DEVOLUCIONES_CL")==null?"0.0":(formato.format(rs.getDouble("DEVOLUCIONES_CL"))))+"</td>");
                    out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >"+(formato.format(imp_cl-dev_cl))+"</td>");

                    out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >"+(rs.getString("IMP_TOTAL_SL")==null?"0.0":(formato.format(rs.getDouble("IMP_TOTAL_SL"))))+"</td>");
                    out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >"+(rs.getString("DEVOLUCIONES_SL")==null?"0.0":(formato.format(rs.getDouble("DEVOLUCIONES_SL"))))+"</td>");
                    out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;padding-right:5PX;' align='right'   >"+(formato.format(imp_sl-dev_sl))+"</td>");


                  }
                               /* out.print("<tr>");
                                out.print("<td HEIGHT='30PX' ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;;padding-left:5PX;' colspan = '9'  >TOTAL&nbsp;</td>");

                                out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;;padding-right:5PX;'  >"+(formato.format(costoTotal))+"</td>");
                                out.print("</tr>");*/





               %>
               </table>
<td class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-right : solid #D8D8D8 1px;;padding-left:5PX;' >
                <%



                    } catch (Exception e) {
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
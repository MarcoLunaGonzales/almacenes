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
        <style type="text/css">
            .existenciaValida{
                background-color: #dcfdd8;
                border: 1px solid #a4e39d;
            }
        </style>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Existencias Almacen</h4>

                    <%
                        Connection con = null;
                    try {

                /*
                         <input type="hidden" name="codProgramaProduccionPeriodo">
                        <input type="hidden" name="codCompProd">
                        <input type="hidden" name="nombreCompProd">
                        */

                        

                        NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat) numeroformato;
                        formato.applyPattern("#,##0.00;(###0.00)");

                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");

                        con = Util.openConnection(con);
                        String fecha=request.getParameter("fecha")==null?"01/01/2011":request.getParameter("fecha");
                        String arrayfecha[]=fecha.split("/");
                        fecha=arrayfecha[2] +"/"+ arrayfecha[1]+"/"+arrayfecha[0];                        
                        String codMaterial=request.getParameter("codMaterial")==null?"0":request.getParameter("codMaterial");
                        //codMaterial = codMaterial.substring(1);
                        String codGrupo=request.getParameter("codGrupo")==null?"''":request.getParameter("codGrupo");
                        String movimiento=request.getParameter("movimiento")==null?"''":request.getParameter("movimiento");
                        String codCapitulo=request.getParameter("codCapitulo")==null?"''":request.getParameter("codCapitulo");
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String codStock=request.getParameter("codStock")==null?"''":request.getParameter("codStock");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");
                        String nombreCapitulo=request.getParameter("nombreCapitulo")==null?"''":request.getParameter("nombreCapitulo");
                        String nombreGrupo=request.getParameter("nombreGrupo")==null?"''":request.getParameter("nombreGrupo");



                        System.out.println("parametros" + fecha + " " + codMaterial + " " + codGrupo + " " + " "+ codCapitulo +" " + codAlmacen + " " + codStock + " " +
                                "" + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo );

                        


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
                        <td><b>Capitulo</b></td>
                        <td><%=nombreCapitulo%></td>
                    </tr>
                    <tr>
                        <td><b>Grupo</b></td>
                        <td><%=nombreGrupo%></td>
                    </tr>
                </tbody>
            </table>
            </table>
                <center>
                    <table>
                        <tr>
                            <td class="outputTextBold">Existencia Disponible para Transacción</td>
                            <td class="existenciaValida" style="width:3rem"></td>
                        <tr>
                    </table>
                </center>    
            <table width="90%" align="center" class="tablaReporte outputText2" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >
                <thead>
                    <tr bgcolor="#cccccc">
                        <td ><b>Cod Material</b></td>
                        <td ><b>Cod</b></td>
                        <td  ><b>Item</b></td>
                        <td ><b>Unidades</b></td>
                        <%
                            String consulta = "select em.NOMBRE_ESTADO_MATERIAL"
                                                +" from estados_material em "
                                                        +" left outer join CONFIGURACION_SALIDA_ESTADO_MATERIAL csem on em.COD_ESTADO_MATERIAL = csem.COD_ESTADO_MATERIAL"
                                                                +" and csem.COD_ALMACEN = "+codAlmacen
                                                + " where em.COD_ESTADO_REGISTRO = 1"
                                                +" order by em.NOMBRE_ESTADO_MATERIAL";
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                            ResultSet res = st.executeQuery(consulta);
                            while(res.next()){
                                out.println("<td>"+res.getString("NOMBRE_ESTADO_MATERIAL")+"</td>");
                            }
                        %>
                        <td ><b>Total Disponible</b></td>
                        <td ><b>Stock Minimo</b></td>
                        <td ><b>Stock Maximo</b></td>
                        <td ><b>Stock Reposición</b></td>
                    </tr>
                </thead>
                <tbody>
                <%
                consulta = " select m.cod_material,isnull(m.CODIGO_ANTIGUO,'') as CODIGO_ANTIGUO,m.nombre_material,um.abreviatura,"
                                   +" m.STOCK_MINIMO_MATERIAL,"
                                   +" m.STOCK_MAXIMO_MATERIAL,m.STOCK_SEGURIDAD,em.NOMBRE_ESTADO_MATERIAL,csem.COD_ALMACEN as permitido"
                                   +" ,existencia.cantidadRestante"
                            +" from materiales m "
                                    +" inner join grupos g on g.COD_GRUPO = m.COD_GRUPO"
                                +"  inner join capitulos c on c.COD_CAPITULO = g.COD_CAPITULO"
                                +" inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA"
                                +" inner join ESTADOS_MATERIAL em on em.COD_ESTADO_REGISTRO=1"
                                +" left outer join CONFIGURACION_SALIDA_ESTADO_MATERIAL csem on csem.COD_ESTADO_MATERIAL = em.COD_ESTADO_MATERIAL"
                                            +" and csem.COD_ALMACEN="+codAlmacen
                                +" left join ("
                                        +" select iad.COD_MATERIAL,iade.COD_ESTADO_MATERIAL,SUM(iade.cantidad_restante) as cantidadRestante"
                                    +" from ingresos_almacen ia"
                                            +" inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN"
                                        +" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN"
                                                    +" and iad.COD_MATERIAL = iade.COD_MATERIAL"
                                     +" WHERE ia.COD_ESTADO_INGRESO_ALMACEN = 1 "
                                           +" and iad.COD_MATERIAL IN ("+codMaterial+")"
                                           +" and ia.estado_sistema = 1 "
                                           +" and ia.cod_almacen = "+codAlmacen
                                           +" and ia.fecha_ingreso_almacen <= '"+fecha+" 23:59:59'"
                                           +" and iade.cantidad_restante > 0"
                                           +" group by iad.COD_MATERIAL,iade.COD_ESTADO_MATERIAL"
                                +" ) as existencia on existencia.COD_MATERIAL= m.COD_MATERIAL"
                                            +" and existencia.COD_ESTADO_MATERIAL= em.COD_ESTADO_MATERIAL"
                            +" where m.COD_MATERIAL IN ("+codMaterial+")"
                            +" order by m.NOMBRE_MATERIAL,em.NOMBRE_ESTADO_MATERIAL";
                System.out.println("consulta reporte: "+ consulta);
                con=Util.openConnection(con);
                res = st.executeQuery(consulta);
                int codMaterialCabecera = 0;
                Double cantidadTotalDisponible = 0d;
                while (res.next()) {
                    if(codMaterialCabecera != res.getInt("COD_MATERIAL")){
                        if(codMaterialCabecera > 0){
                            out.println("<td class='tdRight'>"+formato.format(cantidadTotalDisponible)+"</td>");
                            res.previous();
                                out.println("<td class='tdRight'>"+formato.format(res.getDouble("STOCK_MINIMO_MATERIAL"))+"</td>");
                                out.println("<td class='tdRight'>"+formato.format(res.getDouble("STOCK_MAXIMO_MATERIAL"))+"</td>");
                                out.println("<td class='tdRight'>"+formato.format(res.getDouble("STOCK_SEGURIDAD"))+"</td>");
                                out.println("</tr>");
                            res.next();
                        }
                        out.println("<tr>");
                            out.print("<td >"+res.getString("cod_material")+"</td>");
                            out.print("<td >"+res.getString("CODIGO_ANTIGUO")+"</td>");
                            out.print("<td >"+res.getString("nombre_material")+"</td>");
                            out.print("<td >"+res.getString("abreviatura")+"</td>");
                        codMaterialCabecera = res.getInt("COD_MATERIAL");
                        cantidadTotalDisponible =0d;
                    }
                    out.println("<td class='tdRight "+(res.getInt("permitido") > 0 ? "existenciaValida":"")+"'>"+formato.format(res.getDouble("cantidadRestante"))+"</td>");
                    if(res.getInt("permitido") > 0){
                        cantidadTotalDisponible += res.getDouble("cantidadRestante");
                    }
                }
                if(codMaterialCabecera > 0){
                    out.println("<td class='tdRight'>"+formato.format(cantidadTotalDisponible)+"</td>");
                    res.last();
                    out.println("<td class='tdRight'>"+formato.format(res.getDouble("STOCK_MINIMO_MATERIAL"))+"</td>");
                    out.println("<td class='tdRight'>"+formato.format(res.getDouble("STOCK_MAXIMO_MATERIAL"))+"</td>");
                    out.println("<td class='tdRight'>"+formato.format(res.getDouble("STOCK_SEGURIDAD"))+"</td>");
                    out.println("</tr>");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


               %>
                    </tbody>
                </table>

        </form>
    </body>
</html>
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
<%! Connection con=null;


%>
<%! public String nombrePresentacion1(String codPresentacion){
    

 
String  nombreproducto="";

try{
con=Util.openConnection(con);
String sql_aux="select cod_presentacion, nombre_producto_presentacion from presentaciones_producto where cod_presentacion='"+codPresentacion+"'";
System.out.println("PresentacionesProducto:sql:"+sql_aux);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs=st.executeQuery(sql_aux);
while (rs.next()){
String codigo=rs.getString(1);
nombreproducto=rs.getString(2);
}
} catch (SQLException e) {
e.printStackTrace();
    }
    return "";
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%--meta http-equiv="Content-Type" content="text/html; charset=UTF-8"--%>
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
        
        <script type="text/javascript" >
                function openPopup(f,url){
                    //window.open(url,'detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                    f.action=url;
                    f.submit();
                }
                function openPopup1(url){
                    //window.open(url,'detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                    //window.open(url,'detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                    showModalDialog(url, "dialogwidth: 800", "dialogwidth: 800px");
                    //window.openDialog(url, "dlg", "", "dlg", 6.98);
                    //(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');

                }
        </script>
    </head>
    <body>
        <h3 align="center">SOLICITUD SALIDA DE ALMACEN</h3>
        <br>
        <form target="_blank" id="form" name="form">
            <table align="center" width="90%">

                <%
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#,###");
                //String codSalidaAlmacen=request.getParameter("codSalidaAlmacen")==null?"0":request.getParameter("codSalidaAlmacen");
                //String codAlmacen=request.getParameter("codAlmacen")==null?"0":request.getParameter("codAlmacen");
                String codFormSalida = request.getSession().getAttribute("codFormSalida")==null?"0":request.getSession().getAttribute("codFormSalida").toString();
                    //codOrdenCompra = "10069";
                    //SolicitudesSalida solicitudesSalida = (SolicitudesSalida)request.getSession().getAttribute("solicitudesSalida");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                %>
            </table>
            <br>
            <br>
                <%
                try{
                    //codOrdenCompra = "10069";

                    String 
                    consulta = " select g.NOMBRE_GESTION,t.NOMBRE_TIPO_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN,(p.NOMBRE_PILA+' '+p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL) NOMBRE_SOLICITANTE,ae.NOMBRE_AREA_EMPRESA " +
                             " ,s.FECHA_SOLICITUD,s.COD_LOTE_PRODUCCION,s.OBS_SOLICITUD,s.orden_trabajo,a.NOMBRE_ALMACEN,s.cod_form_salida, cp.nombre_prod_semiterminado, pp.NOMBRE_PRODUCTO_PRESENTACION" +
                             " ,(select sum( ida.cant_ingreso_produccion) from programa_produccion_ingresos_acond ppria inner join ingresos_detalleacond ida " +
                             " on ida.cod_ingreso_acond = ppria.cod_ingreso_acond and ida.cod_lote_produccion = ppria.cod_lote_produccion and ida.cod_compprod = ppria.cod_compprod" +
                             " inner join ingresos_acond ia on ia.cod_ingreso_acond = ida.cod_ingreso_acond " +
                             " where ppria.cod_compprod = s.cod_compprod " +
                             " and ppria.cod_lote_produccion = s.cod_lote_produccion " +
                             " ) cant_ingreso_produccion,(select top 1 fm.cantidad_lote from formula_maestra fm where fm.cod_compprod = s.cod_compprod and fm.cod_estado_registro = 1) cantidad_lote " +
                             " from SOLICITUDES_SALIDA s inner join GESTIONES g on g.COD_GESTION = s.COD_GESTION " +
                              " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN " +
                              " inner join ESTADOS_SOLICITUD_SALIDAS_ALMACEN e on e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN = s.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN " +
                              " inner join PERSONAL p on p.COD_PERSONAL = s.SOLICITANTE " +
                              " inner join AREAS_EMPRESA ae on s.AREA_DESTINO_SALIDA = ae.COD_AREA_EMPRESA " +
                              " inner join ALMACENES a on a.COD_ALMACEN = s.COD_ALMACEN " +
                               " left outer join PRESENTACIONES_PRODUCTO pp on s.COD_PRESENTACION=pp.cod_presentacion" +
                               " left outer join COMPONENTES_PROD cp on s.COD_COMPPROD= cp.COD_COMPPROD " +
                               "where s.COD_FORM_SALIDA in(0 "+codFormSalida+") ";

                System.out.println("consulta"+consulta);
                con = Util.openConnection(con);
                Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs1=st1.executeQuery(consulta);

                if(rs1.next()){
                    out.print(" <table align='center' width='70%' border='0' style='text-align:left' class='outputText1'> ");
                    out.print(" <tr><td rowspan='6'><img src='../../img/cofar.png' /></td><td><b> Nro Solicitud Salida : </b></td> <td> "+(rs1.getString("cod_form_salida")==null?"":rs1.getString("cod_form_salida"))+" </td><td><b>Lote: </b></td> <td> "+(rs1.getString("COD_LOTE_PRODUCCION")==null?"":rs1.getString("COD_LOTE_PRODUCCION"))+" </td> </tr>");
                    out.print(" <tr><td><b>Fecha de Solicitud Salida: </b></td><td> "+(rs1.getDate("FECHA_SOLICITUD")==null?"":sdf.format(rs1.getDate("FECHA_SOLICITUD")))+" </td> <td><b>Producto :</b></td> <td>"+(rs1.getString("nombre_prod_semiterminado")==null?"":rs1.getString("nombre_prod_semiterminado"))+"</td> </tr>");
                    out.print(" <tr><td><b>Presentacion : </b></td> <td>"+(rs1.getString("NOMBRE_PRODUCTO_PRESENTACION")==null?"":rs1.getString("NOMBRE_PRODUCTO_PRESENTACION"))+"</td> <td><b> Area/Dpto. Destino: </b></td> <td>"+(rs1.getString("NOMBRE_AREA_EMPRESA")==null?"":rs1.getString("NOMBRE_AREA_EMPRESA"))+"</td> </tr>");
                    out.print(" <tr><td><b>Tipo Salida: </b></td><td>"+(rs1.getString("NOMBRE_TIPO_SALIDA_ALMACEN")==null?"":rs1.getString("NOMBRE_TIPO_SALIDA_ALMACEN"))+"</td> <td><b>Solicitante: </b></td><td>"+(rs1.getString("NOMBRE_SOLICITANTE")==null?"":rs1.getString("NOMBRE_SOLICITANTE"))+"</td>  </tr> ");
                    out.print(" <tr><td><b>Estado Solicitud: </b></td><td>"+(rs1.getString("NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN")==null?"":rs1.getString("NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN"))+"</td> <td></td><td></td> </tr> ");
                    out.print(" <tr><td><b>Cantidad Lote.: </b></td><td>"+(rs1.getDouble("cantidad_lote"))+"</td> <td>Cantidad Enviada Acond.:</td><td>"+(rs1.getDouble("cant_ingreso_produccion"))+"</td> </tr> ");
                    out.print(" </table> ");
                    //solicitudesSalida.setObsSolicitud(rs1.getString("OBS_SOLICITUD"));
                }

            %>
            <br>

            <table  align="center" width="90%" class="outputText2" style="border : solid #f2f2f2 1px;" cellpadding="0" cellspacing="0">


                <tr class="tablaFiltroReporte">
                    <td  align="center" class="bordeNegroTd"><b>Código</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Item</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Cantidad</b></td>
                    <td  align="center" class="bordeNegroTd"><table width="100%"><tr><td><b>Lote Proveedor</b></td><td><b>Cantidad</b></td></tr></table></td>
                    <td  align="center" class="bordeNegroTd"><b>Unidades</b></td>
                </tr>

                <%
                


                
                consulta = " select m.CODIGO_ANTIGUO,m.cod_material,m.NOMBRE_MATERIAL,u.cod_unidad_medida,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,s.CANTIDAD,s.CANTIDAD_ENTREGADA from SOLICITUDES_SALIDA_DETALLE s inner join materiales m on m.COD_MATERIAL = s.COD_MATERIAL " +
                         " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA " +
                         " where s.COD_FORM_SALIDA in(0"+codFormSalida+") ";

                

                System.out.println("consulta"+consulta);
                con = Util.openConnection(con);                            
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs=st.executeQuery(consulta);

                while (rs.next()){                    
                    String codMaterial = rs.getString("COD_MATERIAL");
                    String nombreMaterial = rs.getString("NOMBRE_MATERIAL");
                    String codUnidadMedida = rs.getString("COD_UNIDAD_MEDIDA");
                    String abreviatura = rs.getString("ABREVIATURA");
                    float cantidadSolicitada = rs.getFloat("CANTIDAD");
                    float cantidadEntregada = rs.getFloat("CANTIDAD_ENTREGADA");
                    String codigoAntiguo = rs.getString("CODIGO_ANTIGUO");
                    
                    out.print("<tr>");
                    out.print("<td class='bordeNegroTd' align='right'>"+(codigoAntiguo==null?"":codigoAntiguo)+"</td>");
                    out.print("<td class='bordeNegroTd' align='right'>"+nombreMaterial+"</td>");
                    out.print("<td class='bordeNegroTd' align='right'>"+cantidadSolicitada+"</td>");
                    out.print("<td class='bordeNegroTd' align='right'>"+cantidadEntregada+"</td>");
                    out.print("<td class='bordeNegroTd' align='right'>"+abreviatura +"</td>");
                    out.print("</tr>");
                    }
                }catch(Exception e){
                e.printStackTrace();
                }
                %>
               
            </table>
            <table width="90%" align="center">
            <tr><td>
            Glosa:
            <%//out.print(solicitudesSalida.getObsSolicitud());%>
            </td>
            </tr>
            </table>
            <br/><br/><br/><br/><br/>

            <table style="text-align:center" align="center" width="70%">
                <tr><td style="border-bottom-color:black;border-bottom-style:solid;border-bottom-width:thin;border-right-width:20px;border-right-color:white;border-right-style:solid" width="33%"></td>
                <td style="border-bottom-color:black;border-bottom-style:solid;border-bottom-width:thin;border-right-width:20px;border-right-color:white;border-right-style:solid" width="33%"> </td>
                <td style="border-bottom-color:black;border-bottom-style:solid;border-bottom-width:thin" width="33%"> </td></tr>
                <tr><td>Aprobado</td><td>Entregado <br/> Nombre y Firma</td><td>Recibido<br/> Nombre y Firma</td></tr>
            </table>

            <%--
            out.print("<td class='bordeNegroTd' align='right'>" +
                            " <a href='#'  onclick=\"openPopup1('navegadorDetalleItemSalidasAlmacen.jsf?codSalidaAlmacen="+codSalidaAlmacen+"&" +
                            "codAlmacen="+codAlmacen+"&codMaterial="+codMaterial+"')\"><img src='../img/areasdependientes.png' /></a></td> ");
                            --%>
                            


            <table style="background:#BBFFCC"></table>
            
            
            
        </form>
    </body>
</html>
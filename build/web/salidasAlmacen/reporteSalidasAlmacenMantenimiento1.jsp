<%@ page import="com.cofar.util.*" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import="com.cofar.bean.*" %>
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
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <script src="../js/general.js"></script>
        
        
    </head>
    <body>
        <h3 align="center">Salida de Almacen De Mantenimiento</h3>
        <br>
        <form target="_blank" id="form" name="form">
            <table align="center" width="90%">

                <%
                System.out.println("salidas almacen asdgasdfasdfasdfasdf " );
                
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#,###.00");
                String codSalidaAlmacen=request.getParameter("codSalidaAlmacen")==null?"0":request.getParameter("codSalidaAlmacen");
                String codAlmacen=request.getParameter("codAlmacen")==null?"0":request.getParameter("codAlmacen");
                SalidasAlmacen salidasAlmacen = (SalidasAlmacen)request.getSession().getAttribute("salidasAlmacen");
                System.out.println("salidas almacen asdgasdfasdfasdfasdf " + salidasAlmacen);
                //System.out.println("el almacen " + salidasAlmacen.getCodSalidaAlmacen());
                    //codOrdenCompra = "10069";     
                String glosa="";
                %>
            </table>
            <br>
            <br>
                <%

                    //codOrdenCompra = "10069";

                    String consulta = " select s.COD_ALMACEN,isnull(sm.NRO_ORDEN_TRABAJO,0) as NRO_ORDEN_TRABAJO,s.COD_SALIDA_ALMACEN,s.NRO_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION,s.FECHA_SALIDA_ALMACEN,cp.nombre_prod_semiterminado, " +
                            " prp.NOMBRE_PRODUCTO_PRESENTACION,a.NOMBRE_AREA_EMPRESA,t.NOMBRE_TIPO_SALIDA_ALMACEN,aa.NOMBRE_ALMACEN,s.OBS_SALIDA_ALMACEN" +
                            ",sm.FECHA_SOLICITUD_MANTENIMIENTO,sm.FECHA_CAMBIO_ESTADOSOLICITUD,isnull(sm.COD_ESTADO_SOLICITUD_MANTENIMIENTO,0) as COD_ESTADO_SOLICITUD_MANTENIMIENTO" +
                            " from SALIDAS_ALMACEN s left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD = s.COD_PROD " +
                            " left outer join PRESENTACIONES_PRODUCTO3 prp on prp.cod_presentacion = s.COD_PRESENTACION " +
                            " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA " +
                            " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN " +
                            " left outer join SOLICITUDES_MANTENIMIENTO sm on sm.NRO_ORDEN_TRABAJO=s.orden_trabajo" +
                            " inner join ALMACENES aa on aa.COD_ALMACEN=s.COD_ALMACEN" +
                            " where s.COD_SALIDA_ALMACEN = '"+codSalidaAlmacen+"'   ";

                System.out.println("consulta"+consulta);
                con = Util.openConnection(con);
                Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs1=st1.executeQuery(consulta);
                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                String codAlmacen1="";
                SimpleDateFormat sdfCon=new SimpleDateFormat("yyyy/MM/dd");
                String fecha="";
                if(rs1.next()){
                    fecha=sdfCon.format(rs1.getDate("FECHA_SALIDA_ALMACEN"));
                    codAlmacen1=rs1.getString("COD_ALMACEN");
                    glosa=rs1.getString("OBS_SALIDA_ALMACEN");
                    out.print(" <table cellspacing='2' cellpadding='2' align='center' border='0' style='text-align:left' class='outputText1'> " +
                            "<tr><th rowspan='4'><img src='../img/cofar.png' /></th>" +
                            "<th><b> Nro Salida : </b></th><th style='font-weight:normal'> "+(rs1.getString("NRO_SALIDA_ALMACEN")==null?"":rs1.getString("NRO_SALIDA_ALMACEN"))+" </th>"+
                            " <th><b>O.T.: </b></th> <th style='font-weight:normal'> "+(rs1.getInt("NRO_ORDEN_TRABAJO")>0?rs1.getString("NRO_ORDEN_TRABAJO"):"")+" </th> </tr>"+
                            " <tr><th><b>Fecha Inicial: </b></th><th style='font-weight:normal'> "+(rs1.getDate("FECHA_SOLICITUD_MANTENIMIENTO")==null?"":sdf.format(rs1.getDate("FECHA_SOLICITUD_MANTENIMIENTO")))+" </th> " +
                            "<th ><b>Almacen:</b></th> <th style='font-weight:normal'>"+(rs1.getString("NOMBRE_ALMACEN"))+"</th> </tr>"+
                            " <tr><th><b>Fecha Final: </b></th><th style='font-weight:normal'> "+(rs1.getInt("COD_ESTADO_SOLICITUD_MANTENIMIENTO")==4?sdf.format(rs1.getDate("FECHA_CAMBIO_ESTADOSOLICITUD")):"")+" </th>" +
                            " <th ><b>Destino:</b></th> <th style='font-weight:normal'>"+rs1.getString("NOMBRE_AREA_EMPRESA")+"</th> </tr>"+
                            " <tr><th><b>Tipo Salida: </b></th><th style='font-weight:normal'> "+rs1.getString("NOMBRE_TIPO_SALIDA_ALMACEN")+" </th> " +
                            "<th><b>Fecha Entrega: </b></th><th style='font-weight:normal'> "+(rs1.getDate("FECHA_SALIDA_ALMACEN")==null?"":sdf.format(rs1.getDate("FECHA_SALIDA_ALMACEN")))+" </th> " +
                            "</tr>"+
                            " </table> ");
                }

            %>
            <br>
            <table  align="center" width="60%" class="outputText2" style="border : solid #f2f2f2 1px;" cellpadding="0" cellspacing="0">

                <tr class="tablaFiltroReporte">                    
                    <td  align="center" class="bordeNegroTd"><b>Nro</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Codigo</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Item</b></td>
                    
                    <td  align="center" class="bordeNegroTd"><b>Cantidad</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Stock</b></td>
                    
                    <td  align="center" class="bordeNegroTd"><b>Unidad</b></td>
                    

                    
                </tr>

                <%
                try{                


                consulta = " select (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                        " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                        " and ia.cod_almacen=4 and ia.fecha_ingreso_almacen<='"+sdfCon.format(new Date())+" 23:59:59' and iade.cod_estado_material in( 2,6) and iade.cantidad_restante>0) as disponible," +
                            " (select sum(sd.CANTIDAD_SALIDA_ALMACEN) from SALIDAS_ALMACEN s, SALIDAS_ALMACEN_DETALLE sd " +
                             " where s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN and s.COD_ALMACEN=4 and  " +
                             " sd.COD_MATERIAL=m.COD_MATERIAL and s.FECHA_SALIDA_ALMACEN <='"+fecha+"' and s.COD_ESTADO_SALIDA_ALMACEN<>2) as sumaCantidadSalida, "+
                        "isnull(m.CODIGO_ANTIGUO,'') as CODIGO_ANTIGUO,sad.COD_SALIDA_ALMACEN,m.COD_MATERIAL,m.NOMBRE_MATERIAL" +
                        ",u.COD_UNIDAD_MEDIDA,u.ABREVIATURA,sad.CANTIDAD_SALIDA_ALMACEN,m.COSTO_UNITARIO " +
                         " from SALIDAS_ALMACEN_DETALLE sad  " +
                         " inner join materiales m on sad.COD_MATERIAL = m.COD_MATERIAL " +
                         " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = sad.COD_UNIDAD_MEDIDA " +
                         " where sad.COD_SALIDA_ALMACEN = '"+codSalidaAlmacen+"' " +
                         " order by m.NOMBRE_MATERIAL "; 

                System.out.println("consulta"+consulta);
                con = Util.openConnection(con);                            
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta);
                int nro=0;
                while (res.next()){
                    nro++;
                    out.print("<tr>"+
                    "<td class='bordeNegroTd' align='right'>"+nro+"</td>"+
                    "<td class='bordeNegroTd' align='right'>"+res.getString("CODIGO_ANTIGUO")+"</td>"+
                    "<td class='bordeNegroTd' align='right'>"+res.getString("NOMBRE_MATERIAL")+"</td>"+
                    
                    "<td class='bordeNegroTd' align='right'>"+nf.format(res.getDouble("CANTIDAD_SALIDA_ALMACEN"))+"</td>"+
                    "<td class='bordeNegroTd' align='right'>"+nf.format(res.getDouble("disponible"))+"</td>"+
                    
                    "<td class='bordeNegroTd' align='right'>"+res.getString("ABREVIATURA")+"</td>"+
                    
                    "</tr>");
                   
                    }
                
                }catch(Exception e){
                e.printStackTrace();
                }
                %>
               
            </table>

            <br>
                
            <table  align="center" width="60%" class="outputText2" style="border-top:solid #000000 1px;" cellpadding="0" cellspacing="0">
                <tr>
                    <td colspan="3" align="left"> <b>Glosa:<%=glosa%></b></td>
                </tr>
                <tr>
                    <td colspan="3" align="left" height="">&nbsp; </td>
                </tr>
                <tr>
                    <td colspan="3" align="left" height="">&nbsp; </td>
                </tr>
                <tr>
                    <td colspan="3" align="left" height="">&nbsp; </td>
                </tr>
                <tr>
                    <td  align="center">_____________________</td>
                    <td  align="center">_____________________</td>
                    <td  align="center">_____________________</td>
                </tr>
                <tr>
                    <td  align="center">Aprobado</td>
                    <td align="center"> Entregado<br>Nombre y Firma</td>
                    <td align="center"> Recibido <br>Nombre y Firma</td>
                </tr>
            </table>

            <table style="background:#BBFFCC"></table>
            
            
            
        </form>
    </body>
</html>
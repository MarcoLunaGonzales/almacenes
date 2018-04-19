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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%--meta http-equiv="Content-Type" content="text/html; charset=UTF-8"--%>
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <h3 align="center">Reporte Ingresos Material por Estado</h3>
        <br>
        <form>
            <div align="center">
            
                <%
                try{ 
                 
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#.####;(#.####)");
                String codCapitulo=request.getParameter("capitulo");
                String codGrupo=request.getParameter("grupo");
                String codEstadoMaterial=request.getParameter("estados");
                String codAlmacen=request.getParameter("almacenes");
                String[] fechaInicio=request.getParameter("fechaInicio").split("/");
                String[] fechaFinal=request.getParameter("fechaFinal").split("/");
                System.out.println(codEstadoMaterial+" "+codAlmacen);
                String consulta="select e.NOMBRE_ESTADO_MATERIAL from ESTADOS_MATERIAL e " +
                        (codEstadoMaterial.equals("")?"":"where e.COD_ESTADO_MATERIAL in ("+codEstadoMaterial+") ") +
                        "order by e.NOMBRE_ESTADO_MATERIAL";
                Connection con=null;
                con=Util.openConnection(con);
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta);
                String nombresEstadoMaterial="";
                while(res.next())
                {
                    nombresEstadoMaterial+=(nombresEstadoMaterial.equals("")?"":",")+res.getString("NOMBRE_ESTADO_MATERIAL");
                }
                    out.print(" <table border='0' style='text-align:left' class='outputText1'> ");
                    out.print(" <tr><td rowspan='3'><img src='../../img/cofar.png' /></td><td><b> Estados Material : </b></td> <td> "+nombresEstadoMaterial+" </td></tr>");
                    if(fechaInicio.length>1)out.print("<tr><td><b>Fecha Inicio:</b></td><td>"+fechaInicio[0]+"/"+fechaInicio[1]+"/"+fechaInicio[2]+"</td></tr>");
                    if(fechaFinal.length>1)out.print("<tr><td><b>Fecha Inicio:</b></td><td>"+fechaFinal[0]+"/"+fechaFinal[1]+"/"+fechaFinal[2]+"</td></tr>");
                    out.print(" </table> ");

              
                 consulta = " select ia.FECHA_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN,iade.FECHA_VENCIMIENTO, " +
                             "m.NOMBRE_MATERIAL,sum(iade.CANTIDAD_RESTANTE) as CANTIDAD_RESTANTE,u.ABREVIATURA,e.cod_estado_material,e.NOMBRE_ESTADO_MATERIAL, " +
                             "( select top 1 s.NOMBRE_SECCION from secciones s inner join SECCIONES_DETALLE sd on s.COD_SECCION = sd.COD_SECCION " +
                             "where sd.COD_CAPITULO = c.COD_CAPITULO or sd.COD_GRUPO  = gr.COD_GRUPO or sd.COD_MATERIAL = m.COD_MATERIAL) NOMBRE_SECCION,es.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO,iade.LOTE_MATERIAL_PROVEEDOR" +
                             " ,isnull(pro.NOMBRE_PROVEEDOR,'') as NOMBRE_PROVEEDOR" +
                             " from INGRESOS_ALMACEN ia  " +
                             " inner join INGRESOS_ALMACEN_DETALLE iad on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN" +
                             " inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN and iade.COD_MATERIAL = iad.COD_MATERIAL" +
                             " inner join MATERIALES m on m.COD_MATERIAL = iad.COD_MATERIAL" +
                             " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA " +
                             " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL" +
                             " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO" +
                             " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO" +
                             " inner join EMPAQUES_SECUNDARIO_EXTERNO es on es.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO" +
                             " left outer join PROVEEDORES pro on pro.COD_PROVEEDOR=ia.COD_PROVEEDOR" +
                             " where  iade.CANTIDAD_RESTANTE>0 and ia.cod_estado_ingreso_almacen=1"+
							 ((fechaInicio.length>1&&fechaFinal.length>1)?" and  ia.FECHA_INGRESO_ALMACEN BETWEEN '"+fechaInicio[2]+"/"+fechaInicio[1]+"/"+fechaInicio[0]+" 00:00:00'" +
                             " and '"+fechaFinal[2]+"/"+fechaFinal[1]+"/"+fechaFinal[0]+" 23:59:59'":"")+
                             (codAlmacen.equals("")?"":" and ia.COD_ALMACEN in ("+codAlmacen+")")+
                             (codEstadoMaterial.equals("")?"":" and e.COD_ESTADO_MATERIAL in ("+codEstadoMaterial+")")+
                             (codGrupo.equals("")?"":" and gr.COD_GRUPO in ("+codGrupo+")")+
                             (codCapitulo.equals("")?"":" and c.COD_CAPITULO in ("+codCapitulo+")")+
                             " group by ia.FECHA_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN,iade.FECHA_VENCIMIENTO,e.NOMBRE_ESTADO_MATERIAL,"+
                             " m.NOMBRE_MATERIAL,u.ABREVIATURA,e.cod_estado_material,c.COD_CAPITULO ,gr.COD_GRUPO,m.COD_MATERIAL,es.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO,"+
                             " iade.LOTE_MATERIAL_PROVEEDOR,pro.NOMBRE_PROVEEDOR"+
                             " order by m.NOMBRE_MATERIAL,ia.FECHA_INGRESO_ALMACEN asc,ia.NRO_INGRESO_ALMACEN,"+
                             " iade.LOTE_MATERIAL_PROVEEDOR asc";

                 System.out.println("consulta " + consulta);

                Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs2=st2.executeQuery(consulta);

                out.print(" <table style='border : solid #f2f2f2 1px;' class='outputText2' width='70%' cellpadding='0' cellspacing='0' > ");
                out.print(" <tr class='tablaFiltroReporte'><td class='bordeNegroTd'>FECHA INGRESO ALMACEN</td> <td class='bordeNegroTd'>NRO INGRESO ALMACEN</td> " +
                            " <td class='bordeNegroTd'>FECHA VENCIMIENTO</td><td class='bordeNegroTd'>MATERIAL</td> <td class='bordeNegroTd'>CANTIDAD PARCIAL</td> " +
                             " <td class='bordeNegroTd'>ABREVIATURA</td> <td class='bordeNegroTd'>ESTADO MATERIAL</td> <td class='bordeNegroTd'>SECCION</td> <td class='bordeNegroTd'>EMPAQUE</td> <td class='bordeNegroTd'>LOTE PROVEEDOR</td><td class='bordeNegroTd'>PROVEEDOR</td> </tr>");
                String color="";
                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdfMMyyyy=new SimpleDateFormat("MM/yyyy");

                while(rs2.next()){
                    Date fechaIngresoAlmacen = rs2.getDate("FECHA_INGRESO_ALMACEN");
                    int nroIngresoAlmacen = rs2.getInt("NRO_INGRESO_ALMACEN");
                    Date fechaVencimiento = rs2.getDate("FECHA_VENCIMIENTO");
                    
                    String nombreMaterial = rs2.getString("NOMBRE_MATERIAL");
                    Double cantidadParcial = rs2.getDouble("CANTIDAD_RESTANTE");
                    String abreviatura = rs2.getString("ABREVIATURA");
                    String nombreEstadoMaterial = rs2.getString("NOMBRE_ESTADO_MATERIAL");
                    String seccion = rs2.getString("NOMBRE_SECCION");
                    String nombreEmpaqueSecundarioExterno = rs2.getString("NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO");
                    String loteProveedor = rs2.getString("LOTE_MATERIAL_PROVEEDOR")==null?"":rs2.getString("LOTE_MATERIAL_PROVEEDOR");
                    //String observaciones = rs2.getString("OBSERVACIONES")==null?"":rs2.getString("OBSERVACIONES");
                    color = rs2.getInt("cod_estado_material")==1?"#FFFF00":"";//AMARILLO
                    color = rs2.getInt("cod_estado_material")==2?"#009900":color;//VERDE
                    color = rs2.getInt("cod_estado_material")==3?"#FF0000":color;//ROJO
                    color = rs2.getInt("cod_estado_material")==4?"#FF0000":color;//ROJO
                    color = rs2.getInt("cod_estado_material")==5?"#FF0000":color;//ROJO
                    color = rs2.getInt("cod_estado_material")==6?"#0000FF":color;//AZUL
                    out.print(" <tr  ><td class='bordeNegroTd'> "+sdf.format( fechaIngresoAlmacen)+" </td> <td class='bordeNegroTd'> "+nroIngresoAlmacen+" </td> " +
                            " <td class='bordeNegroTd'> "+sdfMMyyyy.format(fechaVencimiento)+" </td> " +
                            " <td class='bordeNegroTd'> "+nombreMaterial+" </td> <td class='bordeNegroTd'> "+form.format(cantidadParcial)+" </td> " +
                            " <td class='bordeNegroTd'> "+abreviatura+" </td> <td class='bordeNegroTd' style='background:"+color+"'> "+nombreEstadoMaterial+" </td>" +
                            " <td class='bordeNegroTd'> "+seccion+" </td> <td class='bordeNegroTd'> "+nombreEmpaqueSecundarioExterno+" </td>" +
                            " <td class='bordeNegroTd'> "+loteProveedor+"&nbsp; </td>"+
                            " <td class='bordeNegroTd'> "+rs2.getString("NOMBRE_PROVEEDOR")+"&nbsp; </td></tr>");
                            
                }
                out.print(" </table> ");
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }

                %>                
            

            <br/>
            
            <br>
            <br>
            <br>
                <br/><br/><br/><br/><br/>

           
            </div>
            
            
        </form>
    </body>
</html>
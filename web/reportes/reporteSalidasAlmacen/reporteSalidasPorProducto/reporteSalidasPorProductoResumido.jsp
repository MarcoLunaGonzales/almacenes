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
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <form>
             <%boolean conLote=!request.getParameter("conLote").equals("1");
     if(conLote)
         {%>
     <h4 align="center">Resumen de Salidas por Producto con Nro de Lote</h4>
     <%
     }
     else
         {
     %>
     <h4 align="center">Resumen de Salidas por Producto Sin Nro de Lote</h4>
     <%
     }

                    try {
                      

                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat)nf;
                        formato.applyPattern("#,##0.00");
                        NumberFormat nf1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato1 = (DecimalFormat)nf1;
                        formato1.applyPattern("#,##0.0000");

                        SimpleDateFormat sdt=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        
                        Connection con=null;
                        con = Util.openConnection(con);
                
                        String fechaInicial=request.getParameter("fecha1").equals("null")?"01/01/2011":request.getParameter("fecha1");
              
                        String fechaInicial1 = fechaInicial;
                        String arrayfecha1[]=fechaInicial.split("/");
                        fechaInicial=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];
                        
                        String fechaFinal=request.getParameter("fecha2").equals("null")?"01/01/2011":request.getParameter("fecha2");
                        String fechaFinal1 = fechaFinal;
                        String arrayfecha2[]=fechaFinal.split("/");
                        fechaFinal=arrayfecha2[2] +"/"+ arrayfecha2[1]+"/"+arrayfecha2[0];
                        
                        String codMaterial=request.getParameter("codMaterial")==null?"0":request.getParameter("codMaterial");
                        String nombreMaterialP=request.getParameter("nombreMaterialP")==null?"''":request.getParameter("nombreMaterialP");
                  
                       
                        String codAlmacen=request.getParameter("codAlmacen")==null?"''":request.getParameter("codAlmacen");
                        String codStock=request.getParameter("codStock")==null?"''":request.getParameter("codStock");
                        String nombreFilial=request.getParameter("nombreFilial")==null?"''":request.getParameter("nombreFilial");
                        String nombreAlmacen=request.getParameter("nombreAlmacen")==null?"''":request.getParameter("nombreAlmacen");

                        
                        String codProductoP=request.getParameter("codProductoP")==null?"'0'":request.getParameter("codProductoP");
                        String nombreProductoP=request.getParameter("nombreProductoP")==null?"''":request.getParameter("nombreProductoP");
                        String codFilial=request.getParameter("codFilial")==null?"''":request.getParameter("codFilial");
                        String numLoteP=request.getParameter("numLoteP")==null?"''":request.getParameter("numLoteP");
                        String codComprod=request.getParameter("codProducto");

                    %>

             
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
                        <td><b>Producto</b></td>
                        <td><%=nombreProductoP%></td>
                    </tr>
                    <%
                    if(!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01")){
                        %>
                        <tr>
                        <td><b>Fecha Inicial</b></td>
                        <td><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                    </tr>
                        <%
                    }
                    %>
                    
                </tbody>
            </table>
            </td>
            </tr>
            </table>
            
            <BR>
            <BR>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >
            <tr class="">
                     <td  class="border" style=" border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="left" ><b>PRODUCTO:</b></td>
                     <td class="border" style=" border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="right" ><b>COSTO TOTAL:</b></td>
           </tr>

                

                <%
                String consulta=" SELECT isnull(cp.nombre_prod_semiterminado,'') as nombreProducto,ISNULL(sum(sadi.CANTIDAD*sadi.COSTO_SALIDA_ACTUALIZADO), 0) AS cantidad"+
                                " FROM SALIDAS_ALMACEN sa LEFT OUTER JOIN SALIDAS_ALMACEN_DETALLE sad ON sa.COD_SALIDA_ALMACEN =sad.COD_SALIDA_ALMACEN"+
                                " LEFT OUTER JOIN SALIDAS_ALMACEN_DETALLE_INGRESO sadi ON sad.COD_SALIDA_ALMACEN = sadi.COD_SALIDA_ALMACEN " +
                                " AND sad.COD_MATERIAL =sadi.COD_MATERIAL"+
                                " left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD = sa.COD_PROD"+
                                " WHERE sa.estado_sistema = 1 and sa.cod_estado_salida_almacen = 1" +
                                " and sa.fecha_salida_almacen >= '"+fechaInicial+" 00:00:00' and sa.fecha_salida_almacen <= '"+fechaFinal+" 23:59:59'" ;
                               if(conLote)
                                {
                                    consulta+="and sa.cod_lote_produccion<>''";
                                }
                                else
                                {
                                    consulta+=" and (sa.cod_lote_produccion='' or sa.cod_lote_produccion is null)";
                                }
                                if(!codComprod.equals("0"))
                                {
                                    consulta+=" and sa.cod_prod='"+codComprod+"'";
                                }
                                    consulta+="and sa.cod_almacen in("+codAlmacen+")"+
                                              "group by cp.nombre_prod_semiterminado order by cp.nombre_prod_semiterminado";
                                
                                System.out.println("consulta detalle "+consulta);
                                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                ResultSet res=st.executeQuery(consulta);
                                String nombreProducto="";
                                double costoTotal=0.0d;
                                double sumaTotal=0.0d;
                                while(res.next())
                                {
                                   nombreProducto=res.getString("nombreProducto");
                                   costoTotal=res.getDouble("cantidad");
                                   sumaTotal+=costoTotal;
                                   %>
                                   <tr class="">
                                        <td  class="border" style=" border : solid #D8D8D8 1px"   align="left" ><%=nombreProducto%></td>
                                        <td class="border" style=" border : solid #D8D8D8 1px"   align="right" ><%=(costoTotal>0)?nf.format(costoTotal):""%></td>
                                  </tr>

                                   <%
                                }
                                %>
                                   <tr class="">
                                        <td  class="border" style=" border : solid #D8D8D8 1px"   align="right" ><b>Total:</b></td>
                                        <td class="border" style=" border : solid #D8D8D8 1px"   align="right" ><b><%=(sumaTotal>0)?nf.format(sumaTotal):""%></b></td>
                                  </tr>

                                   <%
                                res.close();
                                st.close();
                                con.close();
                                %>

               </table>

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
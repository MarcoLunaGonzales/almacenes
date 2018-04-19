
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
<%!
    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Materiales</h4>

                    <%
                    try {
                        Connection con=null;
                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat)nf;
                        formato.applyPattern("#,###.00");

                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat format1=new SimpleDateFormat("HH:mm:ss");

                        con = Util.openConnection(con);



                        String codMaterial=request.getParameter("codMaterial")==null?"":request.getParameter("codMaterial");
                        String nombreMaterialP=request.getParameter("nombreMaterial")==null?"''":request.getParameter("nombreMaterial");
                        System.out.println("nombreMaterialP:"+nombreMaterialP);
                        String codGrupo=request.getParameter("codGrupoArray")==null?"":request.getParameter("codGrupoArray");
                        String codCapitulo=request.getParameter("codCapituloArray")==null?"":request.getParameter("codCapituloArray");
                        
                        String nombreCapitulo=request.getParameter("nombreCapitulo")==null?"''":request.getParameter("nombreCapitulo");
                        String nombreGrupo=request.getParameter("nombreGrupo")==null?"''":request.getParameter("nombreGrupo");
                        
                        

                        System.out.println("parametros" + codMaterial + " " + codGrupo + " " + " "+ codCapitulo +
                                  "  "  + nombreCapitulo + " " + nombreGrupo+" "+codMaterial );
                        
                        


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
            <img src="../../img/cofar.png" alt="logo cofar" align="left" />
            </td><td>
            <table border="0" class="outputText1" >
                <tbody>
                  
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
            </td>
            </tr>
            </table>
            
            <BR>
            <BR>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >
               <%
                                Date d= new Date();
                                SimpleDateFormat fA = new SimpleDateFormat("yyyy");
                                SimpleDateFormat fM = new SimpleDateFormat("MM");
                                SimpleDateFormat fD = new SimpleDateFormat("dd");

                                int anio=Integer.parseInt(fA.format(d));
                                anio=anio-1;
                                String anioInicial=anio+"/"+fM.format(d)+"/"+fD.format(d) ;
                                String anioFinal=fA.format(d)+"/"+fM.format(d)+"/"+fD.format(d) ;
                                System.out.println(" anioInicial :"+anioInicial);
                                System.out.println(" anioFinal :"+anioFinal);
               %>

                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px;padding:5px" bgcolor="#f2f2f2"  align="center" ><b>Material</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px;padding:5px;padding:5px" bgcolor="#f2f2f2"  align="center" ><b>Nombre Material Comercial</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px;padding:5px;padding:5px" bgcolor="#f2f2f2"  align="center" ><b>Codigo Material</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px;padding:5px;padding:5px" bgcolor="#f2f2f2"  align="center"><b>Unid.<br> Medida</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Material Almancen</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px;padding:5px" bgcolor="#f2f2f2"  align="center"><b>Material Muestreo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px;padding:5px" bgcolor="#f2f2f2"  align="center"><b>Cantidad<br>Minima<br>Muestreo</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad<br>Maxima<br>Muestreo</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Capitulo</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Grupo</b></td>
                    
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b><br>1- CON MOV<br>2-SIN MOV</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cuenta</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Fecha Venc.</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Exist. x Alm.</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Existencia</b></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Año Movil</b><p><%=anioInicial%></p> <p><%=anioFinal%></p></td>
                    <td  style="border : solid #D8D8D8 1px;padding:5px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Estado</b></td>
                                       
                    
                </tr>

                <%

                String consulta = " select M.COD_MATERIAL,um.ABREVIATURA,M.NOMBRE_MATERIAL,ISNULL(M.NOMBRE_COMERCIAL_MATERIAL,'') AS MCM ," +
                                  " ISNULL(M.MATERIAL_ALMACEN,0) AS MA,ISNULL(M.MATERIAL_MUESTREO,0) AS MM,"+
                                  " ISNULL(M.CANTIDAD_MINIMAMUESTREO,0) AS CMIN,ISNULL(M.CANTIDAD_MAXIMAMUESTREO,0) AS CMAX,"+
                                  " g.NOMBRE_GRUPO,c.NOMBRE_CAPITULO,m.MOVIMIENTO_ITEM,p.NOMBRE_CUENTA,M.COD_MATERIAL,M.COD_ESTADO_REGISTRO"+
                                  " from materiales m inner join grupos g on g.COD_GRUPO=M.COD_GRUPO "+
                                  " inner join unidades_medida um on um.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA"+
                                  " inner join capitulos c on c.COD_CAPITULO=g.COD_CAPITULO"+
                                  " inner join PLAN_DE_CUENTAS p on p.COD_PLAN_CUENTA=g.COD_PLAN_CUENTA"+
                                  " where m.COD_ESTADO_REGISTRO=1 and m.MATERIAL_ALMACEN=1 "+
                                  " "+((codCapitulo.equals(""))?"":"and g.COD_CAPITULO in ("+codCapitulo+")")+
                                  " "+((codGrupo.equals(""))?"":"and g.COD_GRUPO in ("+codGrupo+")")+
                                  " "+((codMaterial.equals(""))?"":"and m.COD_MATERIAL IN ("+codMaterial+")")+
                                  " order by c.NOMBRE_CAPITULO,g.NOMBRE_GRUPO, M.NOMBRE_MATERIAL asc ";
                                     

                System.out.println("consulta 1 "+ consulta);
                
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta);
                
                while (res.next()) {
                   
                                out.print("<tr>");
                                
                                
                                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+res.getString("NOMBRE_MATERIAL")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+res.getString("MCM")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+res.getString("COD_MATERIAL")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+res.getString("ABREVIATURA")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(res.getString("MA").equals("1")?"SI":"NO")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(res.getString("MM").equals("1")?"SI":"NO")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+formato.format(res.getDouble("CMIN"))+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+formato.format(res.getDouble("CMAX"))+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(res.getString("NOMBRE_CAPITULO"))+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(res.getString("NOMBRE_GRUPO"))+"</td>");
                                
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(res.getString("MOVIMIENTO_ITEM"))+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(res.getString("NOMBRE_CUENTA"))+"</td>");
                                

                                String sql_existencia= " select isnull( sum( i.CANTIDAD_RESTANTE),0) as CANTIDAD_RESTANTE ";
                                sql_existencia += " from INGRESOS_ALMACEN_DETALLE_ESTADO i where i.COD_MATERIAL="+res.getString("COD_MATERIAL")+" ";
                                sql_existencia += " and i.COD_INGRESO_ALMACEN in (select ii.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN ii where ii.COD_ESTADO_INGRESO_ALMACEN<>2) ";
                                sql_existencia += " and i.CANTIDAD_RESTANTE>0 and i.COD_ESTADO_MATERIAL in (2,6,8)";
                                System.out.println("sql_existencia 1 "+ sql_existencia);

                                Statement st_existencia = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet res_existencia = st_existencia.executeQuery(sql_existencia);
                                double cant_rest=0;
                                while(res_existencia.next()){
                                     cant_rest=redondear( res_existencia.getDouble(1),2);
                                     
                                }

                                sql_existencia= " select top 1 ii.FECHA_VENCIMIENTO from INGRESOS_ALMACEN i , INGRESOS_ALMACEN_DETALLE_ESTADO ii ";
                                sql_existencia += "  where i.COD_INGRESO_ALMACEN=ii.COD_INGRESO_ALMACEN and i.COD_ESTADO_INGRESO_ALMACEN<>2";
                                sql_existencia += "   and ii.COD_MATERIAL="+res.getString("COD_MATERIAL")+" and ii.COD_ESTADO_MATERIAL in (2,6,8)";
                                sql_existencia += "  and ii.cantidad_restante >0 order by ii.FECHA_VENCIMIENTO asc";
                                System.out.println("sql_existencia FVENC: "+ sql_existencia);

                                st_existencia = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                res_existencia = st_existencia.executeQuery(sql_existencia);
                                String fechaVenc="";
                                SimpleDateFormat ff = new SimpleDateFormat("MM/yyyy");
                                Date f=new Date();
                                String fe="";
                                while(res_existencia.next()){
                                     f=res_existencia.getDate(1);
                                     fe="";
                                     fe=ff.format(f);
                                }

                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+fe+"</td>");


                                
                                sql_existencia= " select i.COD_ALMACEN,a.NOMBRE_ALMACEN, sum (ii.CANTIDAD_RESTANTE) ";
                                sql_existencia += "  from INGRESOS_ALMACEN i,     INGRESOS_ALMACEN_DETALLE_ESTADO ii,ALMACENES a";
                                sql_existencia += "  where i.COD_INGRESO_ALMACEN = ii.COD_INGRESO_ALMACEN and";
                                sql_existencia += "  i.COD_ESTADO_INGRESO_ALMACEN <> 2 and a.COD_ALMACEN=i.COD_ALMACEN and";
                                sql_existencia += "  ii.COD_MATERIAL = "+res.getString("COD_MATERIAL")+" and  ii.COD_ESTADO_MATERIAL in (2, 6,8) and  ii.cantidad_restante > 0";
                                sql_existencia += "  group by    i.COD_ALMACEN,a.NOMBRE_ALMACEN";
                                System.out.println("sql_existencia FVENC: "+ sql_existencia);

                                st_existencia = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                res_existencia = st_existencia.executeQuery(sql_existencia);
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'>");
                                while(res_existencia.next()){
                                     String nomAlmacen=res_existencia.getString(2);
                                     double cantResAlm=redondear( res_existencia.getDouble(3),2);
                                     out.print("<p>"+nomAlmacen+" -- > "+cantResAlm+"</p> " );
                                             
                                }

                                out.print("</td>");

                                sql_existencia= " select isnull( sum( ss.CANTIDAD_SALIDA_ALMACEN),0) from SALIDAS_ALMACEN_DETALLE ss where ss.COD_MATERIAL="+res.getString("COD_MATERIAL")+" and ss.COD_SALIDA_ALMACEN in ( ";
                                sql_existencia += " select s.COD_SALIDA_ALMACEN from SALIDAS_ALMACEN s where s.COD_ESTADO_SALIDA_ALMACEN<>2 and s.FECHA_SALIDA_ALMACEN";
                                sql_existencia += " BETWEEN '"+anioInicial+"' and '"+anioFinal+"' ";
                                sql_existencia += " )";
                                System.out.println("sql_existencia 2: "+ sql_existencia);

                                st_existencia = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                res_existencia = st_existencia.executeQuery(sql_existencia);
                                double cant_anio_movil=0;
                                while(res_existencia.next()){
                                    cant_anio_movil=redondear( res_existencia.getDouble(1),2);
                                }
                                String color="font-size:12px;";
                                if(cant_rest==0){
                                    color="color:red;font-size:14px;";
                                }

                                out.print("<td  style='border : solid #D8D8D8 1px;padding:5PX;"+color+"' class='border' align='right'  >"+cant_rest+"</td>");
                                color="font-size:12px;";
                                if(cant_anio_movil==0){
                                    color="color:red;font-size:14px;";
                                }
                                out.print("<td  style='border : solid #D8D8D8 1px;padding:5PX;"+color+"' class='border' align='right'  >"+cant_anio_movil+" </td>");
                                int cod_estado=res.getInt("COD_ESTADO_REGISTRO");
                                String nomEstado="";

                                if(cod_estado==1){
                                    nomEstado="ACTIVO";
                                }
                                if(cod_estado==2){
                                    nomEstado="NO ACTIVO";
                                }

                                out.print("<td  style='border : solid #D8D8D8 1px;padding:5PX;font-size:13px;color:#666' class='border' align='right'  >"+nomEstado+"</td>");

                                out.print("</tr>");
                  }
                                




               %>
               </table>
                <td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-left:5PX;' >
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
package reportes.reporteMateriales;

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


                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Material</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Nombre Material Comercial</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unid.<br> Medida</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Material Almancen</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Material Muestreo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Cantidad<br>Minima<br>Muestreo</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad<br>Maxima<br>Muestreo</b></td>
                                       
                    
                </tr>

                <%

                String consulta = " select um.ABREVIATURA,M.NOMBRE_MATERIAL,ISNULL(M.NOMBRE_COMERCIAL_MATERIAL,'') AS MCM ," +
                                  " ISNULL(M.MATERIAL_ALMACEN,0) AS MA,ISNULL(M.MATERIAL_MUESTREO,0) AS MM,"+
                                  " ISNULL(M.CANTIDAD_MINIMAMUESTREO,0) AS CMIN,ISNULL(M.CANTIDAD_MAXIMAMUESTREO,0) AS CMAX"+
                                  " from materiales m inner join grupos g on g.COD_GRUPO=M.COD_GRUPO "+
                                  " inner join unidades_medida um on um.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA"+
                                  " where m.COD_ESTADO_REGISTRO=1 and m.MATERIAL_ALMACEN=1 "+
                                  " "+((codCapitulo.equals(""))?"":"and g.COD_CAPITULO in ("+codCapitulo+")")+
                                  " "+((codGrupo.equals(""))?"":"and g.COD_GRUPO in ("+codGrupo+")")+
                                  " "+((codMaterial.equals(""))?"":"and m.COD_MATERIAL IN ("+codMaterial+")")+
                                  " order by M.NOMBRE_MATERIAL asc ";
                                     

                System.out.println("consulta 1 "+ consulta);
                
                con=Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta);
                
                while (res.next()) {
                   
                                out.print("<tr>");
                                
                                out.print("<td class='border' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >"+res.getString("NOMBRE_MATERIAL")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+res.getString("MCM")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+res.getString("ABREVIATURA")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(res.getString("MA").equals("1")?"SI":"NO")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(res.getString("MM").equals("1")?"SI":"NO")+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+formato.format(res.getDouble("CMIN"))+"</td>");
                                out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+formato.format(res.getDouble("CMAX"))+"</td>");
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
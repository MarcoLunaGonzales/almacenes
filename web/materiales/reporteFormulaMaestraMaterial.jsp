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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%--meta http-equiv="Content-Type" content="text/html; charset=UTF-8"--%>
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <script src="../../js/general.js"></script>
       
        <style>
            .tablaDetalle
            {
                border-top: 1px solid #aaaaaa;
                border-left: 1px solid #aaaaaa;
            }
            .tablaDetalle thead td
            {
                background-color: #CCCCCC;
                font-weight:bold;
                text-align: center;
            }
            .tablaDetalle td
            {
                border-bottom: 1px solid #aaaaaa;
                border-right: 1px solid #aaaaaa;
                padding: 5px;
                
            }
        </style>

    </head>
    <body>
            
        <center>
            <h4>Reporte de Formulas Maestras por Material</h4>
                <%
                String codMaterial=request.getParameter("codMaterial");
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#,###");
                Connection con=null;
                try
                {
                    con=Util.openConnection(con);
                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    StringBuilder consulta=new StringBuilder("select m.NOMBRE_MATERIAL");
                                          consulta.append(" from materiales m  where m.COD_MATERIAL=").append(codMaterial);
                    ResultSet res=st.executeQuery(consulta.toString());
                    out.println("<table>");
                    if(res.next())
                    {   
                        out.println("<tr>");
                            out.println("<td><b>Material:</b></td><td>"+res.getString("NOMBRE_MATERIAL")+"</td>");
                        out.println("</tr>");
                    }
                    out.println("<table>");
                    out.println("<table cellpading='0' cellspacing='0' class='tablaDetalle'>");
                        out.println("<thead>");
                            out.println("<tr>");
                                out.println("<td>Tipo Material Formula</td>");  
                                out.println("<td>Producto</td>");
                                out.println("<td>Tamaño Lote</td>");
                            out.println("</tr>");
                        out.println("</thead>");
                        out.println("<tbody>");
                            consulta=new StringBuilder("select cp.nombre_prod_semiterminado,fm.CANTIDAD_LOTE");
                                     consulta.append(" from COMPONENTES_PROD cp");
                                     consulta.append(" inner join FORMULA_MAESTRA fm on cp.COD_COMPPROD=fm.COD_COMPPROD");
                                     consulta.append(" inner join FORMULA_MAESTRA_DETALLE_MP fmd on fmd.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA");
                                     consulta.append(" where fmd.COD_MATERIAL=").append(codMaterial);
                                     consulta.append(" group by cp.nombre_prod_semiterminado,fm.CANTIDAD_LOTE");
                                     consulta.append(" order by cp.nombre_prod_semiterminado");
                            res=st.executeQuery(consulta.toString());
                            while(res.next())
                            {
                                out.println("<tr>");
                                    out.println("<td>Materia Prima</td>");
                                    out.println("<td align='left'>"+res.getString("nombre_prod_semiterminado")+"</td>");
                                    out.println("<td align='right'>"+res.getInt("CANTIDAD_LOTE")+"</td>");
                                out.println("</tr>");
                            }
                            consulta=new StringBuilder("select cp.nombre_prod_semiterminado,fm.CANTIDAD_LOTE");
                                     consulta.append(" from COMPONENTES_PROD cp");
                                     consulta.append(" inner join FORMULA_MAESTRA fm on cp.COD_COMPPROD=fm.COD_COMPPROD");
                                     consulta.append(" inner join FORMULA_MAESTRA_DETALLE_EP fmd on fmd.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA");
                                     consulta.append(" where fmd.COD_MATERIAL=").append(codMaterial);
                                     consulta.append(" group by cp.nombre_prod_semiterminado,fm.CANTIDAD_LOTE");
                                     consulta.append(" order by cp.nombre_prod_semiterminado");
                            res=st.executeQuery(consulta.toString());
                            while(res.next())
                            {
                                out.println("<tr>");
                                    out.println("<td>Empaque Primario</td>");
                                    out.println("<td align='left'>"+res.getString("nombre_prod_semiterminado")+"</td>");
                                    out.println("<td align='right'>"+res.getInt("CANTIDAD_LOTE")+"</td>");
                                out.println("</tr>");
                            }
                            consulta=new StringBuilder("select cp.nombre_prod_semiterminado,fm.CANTIDAD_LOTE");
                                     consulta.append(" from COMPONENTES_PROD cp");
                                     consulta.append(" inner join FORMULA_MAESTRA fm on cp.COD_COMPPROD=fm.COD_COMPPROD");
                                     consulta.append(" inner join FORMULA_MAESTRA_DETALLE_ES fmd on fmd.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA");
                                     consulta.append(" where fmd.COD_MATERIAL=").append(codMaterial);
                                     consulta.append(" group by cp.nombre_prod_semiterminado,fm.CANTIDAD_LOTE");
                                     consulta.append(" order by cp.nombre_prod_semiterminado");
                            res=st.executeQuery(consulta.toString());
                            while(res.next())
                            {
                                out.println("<tr>");
                                    out.println("<td>Empaque Secundario</td>");
                                    out.println("<td align='left'>"+res.getString("nombre_prod_semiterminado")+"</td>");
                                    out.println("<td align='right'>"+res.getInt("CANTIDAD_LOTE")+"</td>");
                                out.println("</tr>");
                            }
                            consulta=new StringBuilder("select cp.nombre_prod_semiterminado,fm.CANTIDAD_LOTE");
                                     consulta.append(" from COMPONENTES_PROD cp");
                                     consulta.append(" inner join FORMULA_MAESTRA fm on cp.COD_COMPPROD=fm.COD_COMPPROD");
                                     consulta.append(" inner join FORMULA_MAESTRA_DETALLE_MR fmd on fmd.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA");
                                     consulta.append(" where fmd.COD_MATERIAL=").append(codMaterial);
                                     consulta.append(" group by cp.nombre_prod_semiterminado,fm.CANTIDAD_LOTE");
                                     consulta.append(" order by cp.nombre_prod_semiterminado");
                            res=st.executeQuery(consulta.toString());
                            while(res.next())
                            {
                                out.println("<tr>");
                                    out.println("<td>Material Reactivo</td>");
                                    out.println("<td align='left'>"+res.getString("nombre_prod_semiterminado")+"</td>");
                                    out.println("<td align='right'>"+res.getInt("CANTIDAD_LOTE")+"</td>");
                                out.println("</tr>");
                            }
                        out.println("</tbody>");
                    out.println("</table>");
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
          %>
          </center>
    </body>
</html>
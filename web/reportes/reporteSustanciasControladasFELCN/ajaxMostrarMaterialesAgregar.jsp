<%@page contentType="text/html"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>

<%
String nombreMaterial=request.getParameter("nombreMaterial");
Connection con=null;
try
{
    con=Util.openConnection(con);
    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    StringBuilder consulta=new StringBuilder("select m.COD_MATERIAL,m.NOMBRE_MATERIAL,um.NOMBRE_UNIDAD_MEDIDA,M.NOMBRE_CCC");
                            consulta.append(" from MATERIALES m ");
                                    consulta.append(" inner join unidades_medida um on um.cod_unidad_medida=m.cod_unidad_medida");
                                 consulta.append(" left outer join  MATERIALES_SUSTANCIAS_CONTROLADAS mscc on mscc.cod_material=m.cod_material ");
                            consulta.append(" where mscc.cod_material is null");
                                    consulta.append(" and m.NOMBRE_MATERIAL like '%").append(nombreMaterial).append("%'");
                                consulta.append(" and m.COD_ESTADO_REGISTRO=1");
                            consulta.append(" order by m.NOMBRE_MATERIAL");
    System.out.println("consulta buscar material "+consulta.toString());
    ResultSet res=st.executeQuery(consulta.toString());
    out.println("<table cellpading='0' cellspancing='0' class='tablaMateriales' id='tablaMateriales'>");
    out.println("<thead>");
        out.println("<tr>");
            out.println("<td>Nombre Material Baco</td>");
            out.println("<td>Nombre Generico</td>");
            out.println("<td>Unidad Medida</td>");
            out.println("<td>Valor Conversión</td>");
            out.println("<td>Unidad Medida Declaración</td>");
            out.println("<td>&nbsp;</td>");
        out.println("</tr>");
    out.println("</thead>");
    out.println("<tbody>");
    while(res.next())
    {
        out.println("<tr>");
            out.println("<td>"+res.getString("NOMBRE_MATERIAL")+"</td>");
            out.println("<td>"+res.getString("NOMBRE_CCC")+"</td>");
            out.println("<td>"+res.getString("NOMBRE_UNIDAD_MEDIDA") +"</td>");
            out.println("<td><input type='text' class='inputText' name='valorConversion' value='0' style='width:90px;'/></td>");
            out.println("<td><select class='inputText' name='codUnidadMedida'><option value='1'>Litros</option><option value='3'>Kilogramos</option></select></td>");
            out.println("<td><a class='btn' onclick='guardarNuevoMaterial(this,"+res.getInt("COD_MATERIAL") +")'>Guardar</a></td>");
        out.println("</tr>");
    }
    out.println("</tbody>");
    out.println("</table>");
}   
catch(SQLException ex)
{
    ex.printStackTrace();
}
finally
{
    con.close();
}
%>

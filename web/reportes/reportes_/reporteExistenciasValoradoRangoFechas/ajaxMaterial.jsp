package reportes.reporteExistenciasValoradoRangoFechas;


<%@page contentType="text/xml"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

String codCapitulo=request.getParameter("codCapitulo")!=null?request.getParameter("codCapitulo"):"";
String codGrupo=request.getParameter("codGrupo")!=null?request.getParameter("codGrupo"):"";
String nombreMaterial=request.getParameter("nombreMaterial")!=null?request.getParameter("nombreMaterial"):"";
String movimiento=request.getParameter("movimiento")!=null?request.getParameter("movimiento"):"";
System.out.println("consulta Grupos"+codCapitulo);
System.out.println("consulta Grupos"+codGrupo);
System.out.println("consulta Grupos"+nombreMaterial);

String consulta =" SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO IN ("+codCapitulo+") AND GR.COD_ESTADO_REGISTRO = 1 ";
consulta = " select m.COD_MATERIAL, m.NOMBRE_MATERIAL, gr.COD_GRUPO, gr.NOMBRE_GRUPO, c.COD_CAPITULO, c.NOMBRE_CAPITULO  from MATERIALES m " +
        " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
        " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO " +
        " where m.NOMBRE_MATERIAL like '%"+nombreMaterial+"%' "+(codCapitulo.equals("")?"":" and c.COD_CAPITULO in ("+codCapitulo+") ") +
        (codGrupo.equals("")?"":" and gr.COD_GRUPO in ("+codGrupo+")")+" and m.MOVIMIENTO_ITEM="+movimiento+" order by m.NOMBRE_MATERIAL asc  ";

System.out.println("consulta Grupos"+consulta);
Connection con=null;
con=Util.openConnection(con);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs=st.executeQuery(consulta);
out.println("<table id='dataMateriales' class='outputText0' cellpadding='0' cellspacing='0' style='width:80%' > <tr bgcolor='#cccccc'>");
out.println("<td height='30px'  style='border-bottom: solid #666666 1px; border-left: solid #666666 1px;border-top: solid #666666 1px;' bgcolor='#f2f2f2'  align='center' ><b>Cod Material</b></td> " +
        " <td  style='border-bottom: solid #666666 1px; border-left: solid #666666 1px;border-top: solid #666666 1px;' bgcolor='#f2f2f2'  align='center' ><b>Nombre Material</b></td> " +
        " <td  style='border-bottom: solid #666666 1px; border-left: solid #666666 1px;border-top: solid #666666 1px;' bgcolor='#f2f2f2'  align='center'><b>Grupo</b></td> " +
        " <td align='center' style='border-bottom: solid #666666 1px; border-left: solid #666666 1px;border-top: solid #666666 1px;'   bgcolor='#f2f2f2'  align='center'><b>Capitulo</b></td> " +
        " </tr>");

while(rs.next()){
    out.println("<tr><td style='border-bottom: solid #666666 1px; border-left: solid #666666 1px;padding:5px' bgcolor='#f2f2f2' ><input type='checkbox' /><input type='hidden' value='"+rs.getString("COD_MATERIAL")+"'/></td><td style='border-bottom: solid #666666 1px; border-left: solid #666666 1px;;padding:5px' bgcolor='#f2f2f2' >"+rs.getString("NOMBRE_MATERIAL")+"</td><td style='border-bottom: solid #666666 1px; border-left: solid #666666 1px;;padding:5px' bgcolor='#f2f2f2' >"+rs.getString("NOMBRE_GRUPO")+"</td>" +
            "<td style='border-bottom: solid #666666 1px; border-left: solid #666666 1px;;padding:5px' bgcolor='#f2f2f2' >"+rs.getString("NOMBRE_CAPITULO")+"</td></tr>");
}
out.println("</table>");
%>
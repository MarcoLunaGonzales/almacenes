package reportes.reporteMateriales;

<%@page contentType="text/xml"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

String codCapitulo=request.getParameter("codCapitulo");
String codGrupo=request.getParameter("codGrupo")==null?"''":request.getParameter("codGrupo");
String consulta =" select m.COD_MATERIAL,m.NOMBRE_MATERIAL from MATERIALES m inner join GRUPOS g on g.COD_GRUPO=m.COD_GRUPO"+
                 " where m.COD_ESTADO_REGISTRO = 1 "+(codGrupo.equals("")?"":"and m.COD_GRUPO in ("+codGrupo+")")+(codCapitulo==null?"":" and g.COD_CAPITULO in ("+codCapitulo+")")+" order by m.NOMBRE_MATERIAL";

System.out.println("consulta Materiales"+consulta);
Connection con=null;
con=Util.openConnection(con);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs=st.executeQuery(consulta);
out.println("<select name='codItem' size='10' class='inputText' style='width:200px' multiple >");
while(rs.next()){
    out.println("<option value=\"'"+rs.getString("COD_MATERIAL")+"'\">"+""+rs.getString("NOMBRE_MATERIAL")+""+"</option>");
}
out.println("</select>");
%>

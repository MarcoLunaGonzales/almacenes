
<%@page contentType="text/xml"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>

<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");


String codAmbiente=request.getParameter("codAmbiente");

String consulta =" select cod_estante,nombre_estante from estante_ambiente e where e.cod_ambiente ='"+codAmbiente+"' ";
consulta+=" order by e.nombre_estante ";

System.out.println("consulta "+consulta);
Connection con=null;
con=Util.openConnection(con);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs=st.executeQuery(consulta);
out.println("<select name='codPasillo'  class='inputText' style='width:200px' >");
out.println("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
while(rs.next()){
    out.println("<option value='"+rs.getString("cod_estante")+"'>"+""+rs.getString("nombre_estante")+""+"</option>");
}
out.println("</select>");
%>

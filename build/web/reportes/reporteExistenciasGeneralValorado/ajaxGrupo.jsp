<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
Connection con = null;
try{
    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    String codCapitulo=request.getParameter("codCapitulo");
    String consulta =" SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO IN ("+codCapitulo+") AND GR.COD_ESTADO_REGISTRO = 1 ";
    System.out.println("consulta Grupos"+consulta);
    con=Util.openConnection(con);
    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet rs=st.executeQuery(consulta);
    out.println("<select name='codGrupo' size='10' class='inputText' multiple style='width:200px' >");
    while(rs.next()){
        out.println("<option value=\"'"+rs.getString("COD_GRUPO")+"'\">"+""+rs.getString("NOMBRE_GRUPO")+""+"</option>");
    }
    out.println("</select>");
}
catch(Exception ex){
    ex.printStackTrace();
    out.clear();
    out.println("Ocurrio un error, intente de nuevo");
}
finally{
    con.close();
}
%>

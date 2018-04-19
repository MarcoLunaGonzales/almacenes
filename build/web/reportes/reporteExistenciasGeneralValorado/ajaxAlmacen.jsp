<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
Connection con = null;
try{
    String codFilial = request.getParameter("codFilial");
    String consulta = " select cod_almacen, nombre_almacen from almacenes where cod_estado_registro=1 and cod_filial='"+codFilial+"' ";
    System.out.println("consulta Filial "+consulta);
    con=Util.openConnection(con);
    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet rs=st.executeQuery(consulta);
    out.println("<select style='color:fuchsia' name='codAlmacen'  class='inputText' >");
    while(rs.next()){
        out.println("<option value=\" "+rs.getString("cod_almacen")+" \">"+rs.getString("nombre_almacen")+"</option>");
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

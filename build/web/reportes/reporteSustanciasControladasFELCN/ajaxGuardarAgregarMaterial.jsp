<%@page contentType="text/html"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>

<%
String codMaterial=request.getParameter("codMaterial");
String valorConversion=request.getParameter("valorConversion");
String codUnidadMedida=request.getParameter("codUnidadMedida");
String mensaje="";
Connection con=null;
try
{
    con=Util.openConnection(con);
    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    StringBuilder consulta=new StringBuilder("INSERT INTO MATERIALES_SUSTANCIAS_CONTROLADAS(cod_material, estado,sustancia, VALOR_CONVERSION, COD_UNIDAD_MEDIDA_DECLARACION)");
                        consulta.append(" VALUES (");
                            consulta.append(codMaterial).append(",");
                            consulta.append("0,");
                            consulta.append("'',");
                            consulta.append(valorConversion).append(",");
                            consulta.append(codUnidadMedida);
                        consulta.append(")");
    System.out.println("consulta registrar material sustancia"+consulta.toString());
    con.setAutoCommit(false);
    PreparedStatement pst=con.prepareStatement(consulta.toString());
    if(pst.executeUpdate()>0)System.out.println("se registro la sustancia");
    con.commit();
    mensaje="1";
}   
catch(SQLException ex)
{
    ex.printStackTrace();
    mensaje="Ocurrio un error al momento de registrar la sustancia";
}
finally
{
    con.close();
}
out.clear();
out.println(mensaje);
%>

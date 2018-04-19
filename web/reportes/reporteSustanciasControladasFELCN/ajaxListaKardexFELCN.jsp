<%@page contentType="text/xml"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
String elimina=request.getParameter("valor")!=null?request.getParameter("valor"):"";
Connection con=null;
con=Util.openConnection(con);
if(elimina.equals("1"))
{   //out.println(elimina);
    //setear desactivado en tabla
    int cod=Integer.parseInt(request.getParameter("cod"));
    //out.println(cod);
    
    StringBuilder consulta=new StringBuilder("DELETE MATERIALES_SUSTANCIAS_CONTROLADAS where cod_material=").append(cod);
    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    st.executeUpdate(consulta.toString());
    con.commit();
    System.out.println("Actualiza estado inhabilitado kardex FLCN: "+consulta.toString());
}
if(elimina.equals("2"))
{   //out.println("adiciona:");
    // int[] cod=new int[1000];
    String items = request.getParameter("mat");
    //String[] cod = items.split(",");
    //out.println(items);
    //for(int i=0;i<=cod.length-1;i++)
    //{   //out.println(cod[i]+"<br>");
        int codAdd=Integer.parseInt(items);
        StringBuilder consulta=new StringBuilder("delete MATERIALES_SUSTANCIAS_CONTROLADAS where cod_material=").append(codAdd);
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        st.executeUpdate(consulta.toString());
        con.commit();
        System.out.println("adiciona kardex FELCN: "+consulta.toString());
    //}
}
//editar sustancia
if(elimina.equals("3"))
{   int id = Integer.parseInt(request.getParameter("ID"));
    String sustancia = request.getParameter("SUS");
    StringBuilder consulta=new StringBuilder("update MATERIALES_SUSTANCIAS_CONTROLADAS set sustancia='").append(sustancia);
                    consulta.append("' where cod_material=").append(id);
    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    st.executeUpdate(consulta.toString());
    con.commit();
    System.out.println("Actualiza sustancia: "+consulta.toString());
    //out.println("ID:"+id+"<br>SUS:"+sustancia);
}    

StringBuilder consulta=new StringBuilder("select ma.cod_material,um.NOMBRE_UNIDAD_MEDIDA,msc.NOMBRE_SINONIMO,msc.NOMBRE_SUSTANCIA,isnull(ma.nombre_material,'N.A.') as nombre_material,ma.CARACTERISTICAS_MATERIAL,ma.TAMANIO_MATERIAL,msc.sustancia,msc.VALOR_CONVERSION");
                         consulta.append(" from MATERIALES_SUSTANCIAS_CONTROLADAS msc left outer join MATERIALES ma on msc.cod_material=ma.cod_material ");
                                consulta.append(" inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=msc.COD_UNIDAD_MEDIDA_DECLARACION");
                                consulta.append(" where msc.estado=0");
                         consulta.append(" order by msc.orden");
System.out.println("consulta Materiales kardex FLCN: "+consulta.toString());

//con=Util.openConnection(con);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs=st.executeQuery(consulta.toString());
out.println("<table id='dataMateriales' class='outputText0' style='border : solid #000000 1px;' cellpadding='0' cellspacing='0' width='100%'>");
out.println("<tr><td class='border' style='border : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center' ></td><td  class='border' style='border : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center' ><b>Nombre Material Baco</b></td> " +
        "<td  class='border' style='border : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center' ><b>Sustancia</b></td>"+
        "<td  class='border' style='border : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center' ><b>Sinonimo</b></td>"+
        "<td  class='border' style='border : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center' ><b>Valor Equivalencia</b></td>"+
        "<td  class='border' style='border : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center' ><b>Unidad De Medida Declaración</b></td>"+
        " <td  class='border' style='border : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center' ><b></b></td> </tr>");
int nro=0;
while(rs.next()){ nro++;
    out.println("<tr id='lineaelimina"+rs.getString("COD_MATERIAL")+"'><td>"+nro+"</td><td>"+rs.getString("NOMBRE_MATERIAL"));
    out.println("<td>"+rs.getString("NOMBRE_SUSTANCIA")+"</td>");
    out.println("<td>"+rs.getString("NOMBRE_SINONIMO")+"</td>");
    out.println("<td>"+rs.getDouble("VALOR_CONVERSION")+"</td>");
    out.println("<td>"+rs.getString("NOMBRE_UNIDAD_MEDIDA")+"</td>");
    
    out.println("</tr>");
}
out.println("</table>");
%>

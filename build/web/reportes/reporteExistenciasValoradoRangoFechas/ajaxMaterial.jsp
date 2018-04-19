<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
Connection con=null;
try{
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
            " where m.NOMBRE_MATERIAL like ? "+(codCapitulo.equals("")?"":" and c.COD_CAPITULO in ("+codCapitulo+") ") +
            (codGrupo.equals("")?"":" and gr.COD_GRUPO in ("+codGrupo+")")+" and m.MOVIMIENTO_ITEM="+movimiento+" order by m.NOMBRE_MATERIAL asc  ";
    System.out.println("consulta materiales filtro: "+consulta);
    con=Util.openConnection(con);
    PreparedStatement pst = con.prepareStatement(consulta.toString());
    pst.setString(1,"%"+nombreMaterial+"%");System.out.println("p1: "+nombreMaterial);
    ResultSet rs = pst.executeQuery();
    out.println("<table id='dataMateriales' class='tablaMateriales outputText2' style='width:100%' >"+
                "<thead>"+
                    "<tr>"+
                        " <td></td> " +
                        " <td>Nombre Material</td> " +
                        " <td>Grupo</td> " +
                        " <td>Capitulo</td> " +
                    " </tr>"+
                "</thead><tbody>");
    while(rs.next()){
        out.println("<tr>"+
                        "<td><input type='checkbox' /><input type='hidden' value='"+rs.getString("COD_MATERIAL")+"'/></td>"+
                        "<td>"+rs.getString("NOMBRE_MATERIAL")+"</td>"+
                        "<td>"+rs.getString("NOMBRE_GRUPO")+"</td>" +
                        "<td>"+rs.getString("NOMBRE_CAPITULO")+"</td></tr>");
    }
    out.println("</tbody></table>");
}catch(Exception ex){
    ex.printStackTrace();
    out.clear();
    out.println("Ocurrio un error al momento de cargar la informacion, intente de nuevo");
}
finally{
    if(!con.isClosed()&& con != null){
        con.close();
    }
}
%>
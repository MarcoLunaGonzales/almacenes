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
    String movimiento=request.getParameter("movimiento")==null?"''":request.getParameter("movimiento");
    String consulta =" SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO IN ("+codCapitulo+") AND GR.COD_ESTADO_REGISTRO = 1 ";
    consulta = " select m.COD_MATERIAL, m.NOMBRE_MATERIAL, gr.COD_GRUPO, gr.NOMBRE_GRUPO, c.COD_CAPITULO, c.NOMBRE_CAPITULO"+
            " ,er.NOMBRE_ESTADO_REGISTRO,m.MOVIMIENTO_ITEM,m.MATERIAL_ALMACEN"+
            "  from MATERIALES m " +
            " inner join ESTADOS_REFERENCIALES er on er.COD_ESTADO_REGISTRO = m.COD_ESTADO_REGISTRO"+
            " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
            " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO " +
            " where m.NOMBRE_MATERIAL like ? "+(codCapitulo.equals("")?"":" and c.COD_CAPITULO in ("+codCapitulo+") ") +
            (codGrupo.equals("")?"":" and gr.COD_GRUPO in ("+codGrupo+")")+" and m.MOVIMIENTO_ITEM="+movimiento+"  order by m.NOMBRE_MATERIAL asc  ";
    System.out.println("consulta materiales: "+consulta);
    con=Util.openConnection(con);
    PreparedStatement pst = con.prepareStatement(consulta.toString());
    pst.setString(1,"%"+nombreMaterial+"%");System.out.println("p1: "+nombreMaterial);
    ResultSet rs = pst.executeQuery();
    out.println("<table id='dataMateriales' class='tablaMateriales'  style='border : solid #cccccc 1px;font-size:9px;width:100%' cellpadding='0' cellspacing='0' style='width:80%' >");
    out.println("<thead><td height='15px'    align='center' ><b>Cod Material</b></td> " +
            " <td      align='center' ><b>Nombre Material</b></td> " +
            " <td      align='center'><b>Grupo</b></td> " +
            " <td align='center'     align='center'><b>Capitulo</b></td> " +
            " <td align='center'     align='center'><b>Estado</b></td> " +
            " <td align='center'     align='center'><b>Item con movimiento</b></td> " +
            " <td align='center'     align='center'><b>Item de almacen</b></td> " +

            "</thead><tbody>");
    while(rs.next()){
        out.println("<tr>"+
                    " <td height='15px'  ><input id='check"+rs.getInt("COD_MATERIAL")+"' type='checkbox' /><input type='hidden' value='"+rs.getString("COD_MATERIAL")+"'/></td>"+
                    " <td  ><label for='check"+rs.getInt("COD_MATERIAL")+"'>"+rs.getString("NOMBRE_MATERIAL")+"</label></td><td  >"+rs.getString("NOMBRE_GRUPO")+"</td>" +
                    " <td  >"+rs.getString("NOMBRE_CAPITULO")+"</td>"+
                    "<td  >"+rs.getString("NOMBRE_ESTADO_REGISTRO")+"</td>"+
                    "<td   ><center>"+(rs.getInt("MOVIMIENTO_ITEM")== 1?"SI":"NO")+"</center></td>"+
                    "<td   ><center>"+(rs.getInt("MATERIAL_ALMACEN")== 1?"SI":"NO")+"</center></td>"+
                "</tr>");
    }
    out.println("</tbody></table>");
}catch(Exception ex){
    ex.printStackTrace();
    out.clear();
    out.println("Ocurrio un error intente de nuevo");
}
finally{
    con.close();
}
%>
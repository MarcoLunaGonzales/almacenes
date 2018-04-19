<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
    Connection con = null;
    try{
        con=Util.openConnection(con);
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        String codCapitulo=request.getParameter("codCapitulo")!=null?request.getParameter("codCapitulo"):"";
        String codGrupo=request.getParameter("codGrupo")!=null?request.getParameter("codGrupo"):"";
        String nombreMaterial=request.getParameter("nombreMaterial")!=null?request.getParameter("nombreMaterial"):"";
        String movimiento=request.getParameter("movimiento")==null?"''":request.getParameter("movimiento");
        String codEstadoItem=request.getParameter("codEstadoItem");
        
        String consulta =" SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO IN ("+codCapitulo+") AND GR.COD_ESTADO_REGISTRO = 1 ";
        consulta = " select m.COD_MATERIAL, m.NOMBRE_MATERIAL, gr.COD_GRUPO, gr.NOMBRE_GRUPO, c.COD_CAPITULO, c.NOMBRE_CAPITULO  from MATERIALES m " +
                " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO " +
                " where m.NOMBRE_MATERIAL like ? "+(codCapitulo.equals("")?"":" and c.COD_CAPITULO in ("+codCapitulo+") ") +
                (codGrupo.equals("")?"":" and gr.COD_GRUPO in ("+codGrupo+")")+" and m.MOVIMIENTO_ITEM="+movimiento+
                (codEstadoItem.equals("0")?"":" and m.cod_estado_registro='"+codEstadoItem+"'")+
                " order by m.NOMBRE_MATERIAL asc  ";

        System.out.println("consulta materiales: "+consulta);
        PreparedStatement pst = con.prepareStatement(consulta);
        pst.setString(1,"%"+nombreMaterial+"%");
        ResultSet rs = pst.executeQuery();
        out.println("<table id='dataMateriales' class='tablaMateriales outputText2' style='width:100%' >");
        out.println("<thead><tr><td height='15px'   align='center' ><b>Cod Material</b></td> " +
                " <td   style='border-bottom : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center' ><b>Nombre Material</b></td> " +
                " <td   style='border-bottom : solid #D8D8D8 1px' bgcolor='#f2f2f2'  align='center'><b>Grupo</b></td> " +
                " <td align='center' style='border-bottom : solid #D8D8D8 1px'  bgcolor='#f2f2f2'  align='center'><b>Capitulo</b></td> " +
                " </tr></thead><tbody>");
        while(rs.next()){
            out.println("<tr><td height='15px' ><input type='checkbox' /><input type='hidden' value='"+rs.getString("COD_MATERIAL")+"'/></td>"+
                    "<td>"+rs.getString("NOMBRE_MATERIAL")+"</td>"+
                    "<td>"+rs.getString("NOMBRE_GRUPO")+"</td>" +
                    "<td>"+rs.getString("NOMBRE_CAPITULO")+"</td></tr>");
        }
        out.println("</tbody></table>");
    }
    catch(Exception ex){
        ex.printStackTrace();
        out.println("Ocurrio un error, intente de nuevo");
    }
    finally{
        con.close();
    }
%>
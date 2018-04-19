
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



        String consulta =" SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO IN ("+codCapitulo+") AND GR.COD_ESTADO_REGISTRO = 1 ";
        consulta = " select m.COD_MATERIAL, m.NOMBRE_MATERIAL, gr.COD_GRUPO, gr.NOMBRE_GRUPO, c.COD_CAPITULO, c.NOMBRE_CAPITULO  from MATERIALES m " +
                " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO " +
                " where m.NOMBRE_MATERIAL like '%"+nombreMaterial+"%' ";
                if(!codCapitulo.equals("-1"))
                 consulta+=" and c.COD_CAPITULO in ("+codCapitulo+")";
                if(!codGrupo.equals("-1"))
                  consulta+=" and gr.COD_GRUPO in ("+codGrupo+")";
                consulta+="  order by m.NOMBRE_MATERIAL asc  ";

        System.out.println("consulta Grupos"+consulta);
        con=Util.openConnection(con);
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=st.executeQuery(consulta);
        out.println("<table id='dataMateriales' class='tablaMateriales outputText2' style='width:100%' >");
        out.println("<thead><tr><td >&nbsp;</td> " +
                " <td ><b>Nombre Material</b></td> " +
                " <td ><b>Grupo</b></td> " +
                " <td ><b>Capitulo</b></td> " +
                " </tr></thead><tbody>");
        while(rs.next()){
            out.println("<tr><td><input type='checkbox' id='check"+rs.getInt("COD_MATERIAL")+"' /><input type='hidden'  value='"+rs.getString("COD_MATERIAL")+"'/></td>"+
                    "<td><label for='check"+rs.getInt("COD_MATERIAL")+"'>"+rs.getString("NOMBRE_MATERIAL")+"</label></td><td>"+rs.getString("NOMBRE_GRUPO")+"</td>" +
                    "<td>"+rs.getString("NOMBRE_CAPITULO")+"</td></tr>");
        }
        out.println("</tbody></table>");
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
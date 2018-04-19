<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
    /*
    ajax general para todos los reportes
    @param codAlmacen
    */
    System.out.println(request.getParameter("codAlmacen"));
    Connection con=null;
    try{
        int codAlmacen = Integer.valueOf(request.getParameter("codAlmacen"));
        StringBuilder consulta = new StringBuilder("select c.COD_CAPITULO,c.NOMBRE_CAPITULO")
                                            .append(" from capitulos c")
                                                 .append(" inner join CAPITULOS_GESTION_UBICACION cgu on cgu.COD_CAPITULO =c.COD_CAPITULO")
                                            .append(" where cgu.COD_ALMACEN = ").append(codAlmacen)
                                            .append(" order by c.NOMBRE_CAPITULO");
        System.out.println("consulta capitulos gestion ubicacion: "+consulta.toString());
        con=Util.openConnection(con);
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=st.executeQuery(consulta.toString());
        out.println("<select name='codCapitulo' id='codCapitulo'  class='inputText' onchange='ajaxGrupo(form1)' >");
            out.println("<option value='0'>-TODOS-</option>");
                while(rs.next()){    
                    out.println("<option value='"+rs.getString("COD_CAPITULO")+"'>"+rs.getString("NOMBRE_CAPITULO")+"</option>");
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

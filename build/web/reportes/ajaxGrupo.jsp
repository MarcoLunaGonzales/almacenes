
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
    /*
        ajax general para todos los reportes
        @param codCapitulo
        @param optionTodos
        @param multiple 0 o 1
        @param size >0
    */
    Connection con = null;
    try{
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        
        String codCapitulo=request.getParameter("codCapitulo");
        boolean multiple =(request.getParameter("multiple") != null? request.getParameter("multiple").equals("1") :false);
        boolean optionTodos = (request.getParameter("optionTodos")!= null ? Integer.valueOf(request.getParameter("optionTodos")) > 0 : false);
        int size = (request.getParameter("size") != null? Integer.valueOf(request.getParameter("size")) :0);
        String consulta =" SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO IN ("+codCapitulo+") AND GR.COD_ESTADO_REGISTRO = 1 ";

        System.out.println("consulta Grupos"+consulta);
        con=Util.openConnection(con);
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=st.executeQuery(consulta);
        out.println("<select name='codGrupo' id='codGrupo'  class='inputText' "+(multiple && size > 0 ?"size='"+size+"'":"")+" "+(multiple?"multiple":"")+"  style='width:200px' >");
        if(!multiple){
            out.println("<option value='0'>"+(optionTodos ? "--TODOS--":"--NINGUNO--")+"</option>");
        }
        
        while(rs.next()){
            out.println("<option value='"+rs.getString("COD_GRUPO")+"'>"+rs.getString("NOMBRE_GRUPO")+"</option>");
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

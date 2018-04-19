<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
    /*
    ajax general para todos los reportes
    @param codFilial
    @param multiple
    @param size
    */
    Connection con=null;
    try{
        int codFilial = Integer.valueOf(request.getParameter("codFilial"));
        boolean multiple =(request.getParameter("multiple") != null? request.getParameter("multiple").equals("1") :false);
        int size = (request.getParameter("size") != null? Integer.valueOf(request.getParameter("size")) :0);
        String consulta = " select cod_almacen, nombre_almacen from almacenes"
                        + " where cod_estado_registro=1";
        if(codFilial > 0)
            consulta +=" and cod_filial='"+codFilial+"' ";
        System.out.println("consulta Filial todos: "+consulta);
        con=Util.openConnection(con);
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=st.executeQuery(consulta);
        out.println("<select name='codAlmacenSelect'  "+(multiple && size > 0 ?"size='"+size+"'":"")+" "+(multiple?"multiple":"")+" class='inputText' size='5' multiple>");
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

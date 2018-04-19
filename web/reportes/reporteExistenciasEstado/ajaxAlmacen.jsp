<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
    Connection con=null;
    try{
        String codFilial = request.getParameter("codFilial");
        
        String codAlmacenDefault = (request.getParameter("codAlmacenDefault") == null ?"":request.getParameter("codAlmacenDefault"));
        System.out.println("codALmacendefault:"+codAlmacenDefault);
        String consulta = " select cod_almacen, nombre_almacen from almacenes where cod_estado_registro=1 and cod_filial='"+codFilial+"' ";

        System.out.println("consulta cargar almacenes: "+consulta);

        con=Util.openConnection(con);
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=st.executeQuery(consulta);
        out.println("<select name='codAlmacen'  class='inputText' onchange='ajaxAmbienteAlmacenCapitulo(form1)' >");
        while(rs.next()){
            out.println("<option value='"+rs.getString("cod_almacen")+"' "
                            +((!codAlmacenDefault.equals("")) && codAlmacenDefault.equals(rs.getString("cod_almacen"))?"selected":"")
                            +" >"+rs.getString("nombre_almacen")+"</option>");
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

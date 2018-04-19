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
        StringBuilder consulta = new StringBuilder("select aa.COD_AMBIENTE,aa.NOMBRE_AMBIENTE")
                                        .append(" from AMBIENTE_ALMACEN aa")
                                        .append(" where aa.COD_ALMACEN =").append(codAlmacen)
                                        .append(" order by aa.NOMBRE_AMBIENTE");
        System.out.println("consulta ambientes almacen: "+consulta.toString());
        con=Util.openConnection(con);
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=st.executeQuery(consulta.toString());
        out.println("<select name='codAmbiente' id='codAmbiente'  class='inputText' onchange='ajaxPasillo(form1)' >");
            out.println("<option value='0'>-TODOS-</option>");
                while(rs.next()){    
                    out.println("<option value='"+rs.getString("COD_AMBIENTE")+"'>"+rs.getString("NOMBRE_AMBIENTE")+"</option>");
                }
            out.println("<option value='-1'>Sin Ubicación</option>");
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

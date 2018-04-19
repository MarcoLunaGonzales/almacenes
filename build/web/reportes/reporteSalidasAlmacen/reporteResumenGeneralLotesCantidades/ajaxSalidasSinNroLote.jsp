
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
    Connection con = null;
    try{
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        String consulta ="  select cod_salida_almacen, nro_salida_almacen"+
                            " from salidas_almacen "+
                            " where estado_sistema=1"+
                            " and isnull(cod_lote_produccion,'') = ''"+
                            " and cod_almacen=1"+
                            " and cod_estado_salida_almacen=1"+
                            " order by fecha_salida_almacen";
        System.out.println("consulta salidas sin Nro Lote"+consulta);
        con=Util.openConnection(con);
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=st.executeQuery(consulta);
        out.println("<select multiple size='8' style='width:200px;color:#a43706;font-size:12px' id='codSalidas' name='codSalidas'  class='outputText3' >");
        while (rs.next()) {
            out.print("<option  value='"+ rs.getString("cod_salida_almacen") +"' >"+rs.getString("nro_salida_almacen")+"</option>");
        }                  
        out.println("</select>");
    }
    catch(Exception ex){
        ex.printStackTrace();
        out.clear();
        out.println("Ocurrio un error al momento de cargar el registro, intente de nuevo");
    }
    finally{
        con.close();
    }

%>
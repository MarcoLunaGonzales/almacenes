package reportes.reporteSalidasAlmacen.reporteResumenGeneralLotesCantidades;


<%@page contentType="text/xml"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%
out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

String fechaInicio=request.getParameter("fechaInicial")!=null?request.getParameter("fechaInicial"):"";
String arrayfecha1[] = fechaInicio.split("/");
fechaInicio = arrayfecha1[2] + "/" + arrayfecha1[1] + "/" + arrayfecha1[0];
String fechaFinal=request.getParameter("fechaFinal")!=null?request.getParameter("fechaFinal"):"";
String arrayfecha2[] = fechaFinal.split("/");
fechaFinal = arrayfecha2[2] + "/" + arrayfecha2[1] + "/" + arrayfecha2[0];
String codAlmacen=request.getParameter("codAlmacen")!=null?request.getParameter("codAlmacen"):"";
System.out.println("codAlmacen:"+codAlmacen);



String consulta =" select DISTINCT cod_lote_produccion from salidas_almacen where estado_sistema=1 ";
consulta +=" and fecha_salida_almacen>='"+fechaInicio+"' and fecha_salida_almacen<='"+fechaFinal+" 23:59:59' ";
consulta +=" and cod_almacen="+codAlmacen+" ";
consulta +=" and cod_estado_salida_almacen=1  ";
consulta +=" and cod_lote_produccion is NOT null and cod_lote_produccion<>'' ";

System.out.println("consulta Grupos:"+consulta);
Connection con=null;
con=Util.openConnection(con);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs=st.executeQuery(consulta);



    %>

    <select multiple size="10" style="width:200px;color:#a43706;font-size:12px" name="codLote"  class="outputText3" >

                                <%
                                //int i=0;
                            while (rs.next()) {
                                %>
                              
                                <%
                                //System.out.println("i++;"+i);
                                //i++;
                                System.out.println("rs.getString(1):"+rs.getString(1));
                                out.print("<option  value="+ rs.getString("cod_lote_produccion") +" >"+rs.getString("cod_lote_produccion")+"</option>");
                            }%>
    </select>
    <%


%>

<%@ page import="com.cofar.util.*" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import = "java.sql.Connection"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import = "java.sql.*"%> 
<%@ page import = "java.text.SimpleDateFormat"%> 
<%@ page import = "java.util.ArrayList"%> 
<%@ page import = "java.util.Date"%> 
<%@ page import = "javax.servlet.http.HttpServletRequest"%>
<%@ page import = "java.text.DecimalFormat"%> 
<%@ page import = "java.text.NumberFormat"%> 
<%@ page import = "java.util.Locale"%> 

<%@ page language="java" import="java.util.*,com.cofar.util.CofarConnection" %>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%! Connection con=null;


%>
<%! public String nombrePresentacion1(String codPresentacion){
    

 
String  nombreproducto="";

try{
con=Util.openConnection(con);
String sql_aux="select cod_presentacion, nombre_producto_presentacion from presentaciones_producto where cod_presentacion='"+codPresentacion+"'";
System.out.println("PresentacionesProducto:sql:"+sql_aux);
Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs=st.executeQuery(sql_aux);
while (rs.next()){
String codigo=rs.getString(1);
nombreproducto=rs.getString(2);
}
} catch (SQLException e) {
e.printStackTrace();
    }
    return "";
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%--meta http-equiv="Content-Type" content="text/html; charset=UTF-8"--%>
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <script src="../js/general.js"></script>
        
        <script type="text/javascript" >
                function openPopup(f,url){
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                    f.action=url;
                    f.submit();
                }
                function openPopup1(url){
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                    showModalDialog(url, "dialogwidth: 800", "dialogwidth: 800px");
                    //window.openDialog(url, "dlg", "", "dlg", 6.98);
                    //(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');

                }
        </script>
    </head>
    <body>
        <h3 align="center">Detalle de Seccion</h3>
        <br>
        <form target="_blank" id="form" name="form">
            <table align="center" width="40%">

                <%
                
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#,###");
                String codSalidaAlmacen=request.getParameter("codSalidaAlmacen")==null?"0":request.getParameter("codSalidaAlmacen");
                String codAlmacen=request.getParameter("codAlmacen")==null?"0":request.getParameter("codAlmacen");
                String codSeccion = request.getParameter("codSeccion")==null?"0":request.getParameter("codSeccion");
                String nombreSeccion = request.getParameter("nombreSeccion")==null?"0":request.getParameter("nombreSeccion");

                    //codOrdenCompra = "10069";                
                %>  
                <tr><td>Seccion: </td><td><%=nombreSeccion%></td></tr>
            </table>
            <br>
            <br>
           
            <br>
            <table  align="center" width="60%" class="outputText2" style="border : solid #f2f2f2 1px;" cellpadding="0" cellspacing="0">

                <tr class="tablaFiltroReporte">                    
                    <td  align="center" class="bordeNegroTd"><b>Capitulo</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Grupo</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Item</b></td>
                </tr>

                     <%

                  String consulta = " select s.COD_SECCION,c.COD_CAPITULO,c.NOMBRE_CAPITULO,g.COD_GRUPO,g.NOMBRE_GRUPO,m.COD_MATERIAL,m.NOMBRE_MATERIAL " +
                          " from SECCIONES_DETALLE s  " +
                          " left outer join CAPITULOS c on s.COD_CAPITULO = c.COD_CAPITULO " +
                          " left outer join GRUPOS g on g.COD_CAPITULO = s.COD_CAPITULO and g.COD_GRUPO = s.COD_GRUPO " +
                          " left outer join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL " +
                          " where s.COD_SECCION = '"+codSeccion+"'  ";

                System.out.println("consulta"+consulta);
                con = Util.openConnection(con);
                Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs1=st1.executeQuery(consulta);
                while(rs1.next()){
                    %>
                      <tr class="tablaFiltroReporte">
                          <td  align="center" class="bordeNegroTd"><%=rs1.getString("NOMBRE_CAPITULO")==null?"":rs1.getString("NOMBRE_CAPITULO")%></td>
                        <td  align="center" class="bordeNegroTd"><%=rs1.getString("NOMBRE_GRUPO")==null?"":rs1.getString("NOMBRE_GRUPO")%></td>
                        <td  align="center" class="bordeNegroTd"><%=rs1.getString("NOMBRE_MATERIAL")==null?"":rs1.getString("NOMBRE_MATERIAL") %></td>
                      </tr>
                    <%
                    
                }


                    %>

                
               
            </table>
                            


            <table style="background:#BBFFCC"></table>
            
            
            
        </form>
    </body>
</html>
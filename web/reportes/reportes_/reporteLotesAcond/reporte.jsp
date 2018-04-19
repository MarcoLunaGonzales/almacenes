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
java.util.Date fechaActual = new Date();
SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
String cadenaFecha = formato.format(fechaActual);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <form>
            <table align="center" width="90%">
                <tr>
                    <td colspan="3" align="center" >
                        <h4>Existencias por Lotes de Productos Semiterminados</h4>
                    </td>
                </tr>
                <%
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#,###");
                String codAlmacen=request.getParameter("codAlmacen");
                String nombreAlmacen=request.getParameter("nombreAlmacen");   
                String codArea=request.getParameter("codArea");
                String nombreArea=request.getParameter("nombreArea");
                System.out.println("codigoArea "+codArea);
                
                %>                
                <tr>
                    <td align="left" width="25%"><img src="../../img/logo_cofar.png"></td>
                    <td align="left" class="outputText2" width="50%" >                        
                        <b>Almacen:&nbsp;::&nbsp;</b><%=nombreAlmacen%><br>                        
                    </td>
                </tr>
            </table>
            <table  align="center" width="90%" class="outputText2" style="border : solid #f2f2f2 1px;" cellpadding="0" cellspacing="0">
                
                <tr class="tablaFiltroReporte">
                    <td align="center" class="bordeNegroTd"><b>Código</b></td>
                    <td align="center" class="bordeNegroTd"><b>Producto</b></td>
                    <td align="center" class="bordeNegroTd"><b>Lote</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Fecha Vencimiento</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Cantidad</b></td>
                </tr>
                <%
                try{
                    con=Util.openConnection(con);
                    String sql="select c.COD_COMPPROD, c.nombre_prod_semiterminado, i.COD_LOTE_PRODUCCION, " +
                            " convert(varchar, i.FECHA_VEN, 103), i.CANT_RESTANTE " +
                            " from INGRESOS_DETALLEACOND i, INGRESOS_ACOND ii, COMPONENTES_PROD c " +
                            " where i.COD_INGRESO_ACOND=ii.COD_INGRESO_ACOND and c.COD_AREA_EMPRESA in ("+codArea+") and" +
                            " ii.COD_ALMACENACOND="+codAlmacen+" and ii.COD_ESTADO_INGRESOACOND not in (1,2)  " +
                            " and c.COD_COMPPROD=i.COD_COMPPROD and i.CANT_RESTANTE>0 order by c.nombre_prod_semiterminado, i.COD_LOTE_PRODUCCION;";
                    
                    System.out.println("SQL: "+sql);
                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs=st.executeQuery(sql);
                    rs.first();
                    int codPresPivote=rs.getInt(1);
                    rs.beforeFirst();
                    int sumaCant=0;
                    int sumaCantUnit=0;
                    int codPresentacion=0;
                    String nombrePresentacion="";
                    String codLote="";
                    int cantidad=0;
                    int cantidadUnitaria=0;
                    String fechaVenc="";
                    while(rs.next()){
                        codPresentacion=rs.getInt(1);
                        nombrePresentacion=rs.getString(2);
                        codLote=rs.getString(3);
                        fechaVenc=rs.getString(4);
                        cantidad=rs.getInt(5);
                        if(codPresPivote!=codPresentacion){
                %>
                <tr>
                    <th class="bordeNegroTd" colspan="4" align="left">Total</th>
                    <th class="bordeNegroTd"><%=sumaCant%></th>
                </tr>
                <tr>
                    <th class="bordeNegroTd" colspan="4" align="left"><%=nombrePresentacion%></th>
                    <th class="bordeNegroTd">&nbsp;</th>
                </tr>
                <%
                sumaCant=0;
                codPresPivote=codPresentacion;
                        }
                        sumaCant=sumaCant+cantidad;
                %>
                <tr>
                    <td class="bordeNegroTd"><%=codPresentacion%></td>
                    <td class="bordeNegroTd"><%=nombrePresentacion%></td>
                    <td class="bordeNegroTd"><%=codLote%></td>
                    <td class="bordeNegroTd" align="center"><%=fechaVenc%></td>
                    <td class="bordeNegroTd" align="center"><%=cantidad%></td>
                    
                </tr>
                <%
                    }
                %>
                <tr>
                    <th class="bordeNegroTd" colspan="4" align="left">Total: </th>
                    <th class="bordeNegroTd"><%=sumaCant%></th>
                </tr>
                <%
                
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                %>
            </table>
        </form>
    </body>
</html>
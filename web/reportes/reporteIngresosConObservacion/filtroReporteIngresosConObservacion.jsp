
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <link rel="STYLESHEET" type="text/css" href="../../css/chosen.css" />
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <h3 align="center">Reporte Indicador de Ingresos con Observación</h3>
        <form method="post" action="reporteIngresosConObservacionXLS.jsf" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="50%">
                    <thead>
                        <tr>
                            <td colspan="3" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                    
                       <%
                           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                       %>
                    <tr class="outputText3">
                        <td  height="35px" class=""><b>Fecha Inicio</b></td>
                        <td class=""><b>::</b></td>
                       
                        <td class="">
                            <input type="text"  size="12" style="color:#a43706"   value="<%=sdf.format(new Date()) %>" id="fechaInicio" name="fechaInicio" class="dlCalendar">
                        </td>
                    </tr>
                    <tr>
                        <td  height="35px" class=""><b>Fecha Final</b></td>
                        <td class=""><b>::</b></td>
                        <td class="">
                            <input type="text"  size="12" style="color:#a43706"  value="<%=sdf.format(new Date()) %>" id="fechaFinal" name="fechaFinal" class="dlCalendar">
                        </td>
                    </tr>
                    <tr>
                        <td><b>Tipo de Proveedor</b></td>
                        <td><b>::</b></td>
                        <td>
                            <select id="codTipoProveedor" name="codTipoProveedor" style="inputText">
                                <option value="0">TODOS</option>
                            <%
                                Connection con = null;
                                try{
                                    con = Util.openConnection(con);
                                    StringBuilder consulta = new StringBuilder("select tp.COD_TIPO_PROVEEDOR,tp.NOMBRE_TIPO_PROVEEDOR")
                                                                        .append(" from TIPOS_PROVEEDOR tp")
                                                                        .append(" order by tp.NOMBRE_TIPO_PROVEEDOR");
                                    Statement st = con.createStatement();
                                    ResultSet res = st.executeQuery(consulta.toString());
                                    while(res.next()){
                                        out.println("<option value='"+res.getInt("COD_TIPO_PROVEEDOR")+"'>"+res.getString("NOMBRE_TIPO_PROVEEDOR")+"</option>");
                                    }
                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            %>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="submit" class="btn"  value="Ver Reporte" name="reporte"/>
                                <input type="reset" class="btn"  value="Limpiar " name="reporteL" />
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </form>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>
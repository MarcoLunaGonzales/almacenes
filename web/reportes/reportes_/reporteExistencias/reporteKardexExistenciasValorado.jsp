package reportes.reporteExistencias;

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
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
<%@ page import = "com.cofar.bean.KardexMovimiento"%>
<%@ page import = "com.cofar.bean.KardexItemMovimiento"%>



<%! Connection con = null;
String codPresentacion = "";
String nombrePresentacion = "";
String linea_mkt = "";
String agenciaVenta = "";
%>
<%! public String nombrePresentacion1() {



    String nombreproducto = "";
//ManagedAccesoSistema bean1=(ManagedAccesoSistema)com.cofar.util.Util.getSessionBean("ManagedAccesoSistema");
    try {
        con = Util.openConnection(con);
        String sql_aux = "select cod_presentacion, nombre_producto_presentacion from presentaciones_producto where cod_presentacion='" + codPresentacion + "'";
        System.out.println("PresentacionesProducto:sql:" + sql_aux);
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery(sql_aux);
        while (rs.next()) {
            String codigo = rs.getString(1);
            nombreproducto = rs.getString(2);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return nombreproducto;
}
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
            <h4 align="center">Kardex de Movimiento Valorado</h4>

                    <%
                    try {

                /*
                         <input type="hidden" name="codProgramaProduccionPeriodo">
                        <input type="hidden" name="codCompProd">
                        <input type="hidden" name="nombreCompProd">
                        */

                        NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato = (DecimalFormat) numeroformato;
                        formato.applyPattern("#.####;(#.####)");

                        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");

                        con = Util.openConnection(con);

                         String codPersonal=request.getParameter("codPersonal")==null?"0":request.getParameter("codPersonal");
                         
                         //System.out.println("el parametro de detalle "+codTipoReporteDetallado);

                         //System.out.println("las fechas en el reporte" + desdeFecha + " " +hastaFecha );
                         //recuperacion del map de session

                       //  ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                       //  Map<String, Object> sessionMap = externalContext.getSessionMap();
                       //  String nombreAlmacen = (String)sessionMap.get("nombreAlmacen");
                       // se recoge la lista de sesion
                       List kardexMovimientoList = (ArrayList)request.getSession().getAttribute("kardexMovimientoList");
                       Iterator i = kardexMovimientoList.iterator();
                       //iteracion 1
                       while(i.hasNext()){
                           KardexMovimiento kardexMovimiento = (KardexMovimiento)i.next();
                           %>
                            <table border="0" class="outputText0" align="center">
                                <tbody>
                                    <tr>
                                        <td><b>Almacen : </b></td>
                                        <td><%=kardexMovimiento.getAlmacenes().getNombreAlmacen()%> &nbsp;</td>
                                        <td><b>Item : &nbsp;</b></td>
                                        <td><%=kardexMovimiento.getMateriales().getNombreMaterial()%></td>
                                    </tr>
                                    <tr>
                                        <td><b>Unidades :</b></td>
                                        <td><%=kardexMovimiento.getMateriales().getUnidadesMedida().getNombreUnidadMedida()%></td>
                                        <td><b>Codigo :</b></td>
                                        <td><%=kardexMovimiento.getMateriales().getCodMaterial()%></td>

                                    </tr>

                                        <tr>
                                        <td><b>Fecha Inicio :</b></td>
                                        <td><%=format.format(kardexMovimiento.getFechaInicio())%></td>
                                        <td><b>Fecha Final :</b></td>
                                        <td><%=format.format(kardexMovimiento.getFechaFinal())%></td>

                                    </tr>

                                    <tr>
                                        <td><b>Existencia a fecha Inicio de Reporte :</b></td>
                                        <td><%=kardexMovimiento.getSaldo()%></td>
                                        <td><b></b></td>
                                        <td><%=kardexMovimiento.getMonto()%></td>
                                    </tr>
                                    <tr>
                                        <td><b>Costo Unitario</b></td>
                                        <td><%=kardexMovimiento.getCostoUnitario()%></td>
                                        <td><b></b></td>
                                        <td></td>
                                    </tr>


                                </tbody>
                            </table>

                            <table width="100%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                        <tr class="">
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Fecha</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center" ><b>Tipo</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center"><b>Nro Ing/Sal</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Motivo</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center"><b>Lote</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center"><b>Entrada</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Salida</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Saldo</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center"><b>C. Ing</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>C.U. </b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Debe</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Haber</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Saldo</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Act.</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>UFV</b></td>

                        </tr>
                           <%
                              // detalle del reporte
                           Iterator i1 = kardexMovimiento.getKardexItemMovimientoList().iterator();
                           while(i1.hasNext()){
                               KardexItemMovimiento kardexItemMovimiento = (KardexItemMovimiento)i1.next();
                               out.print("<tr>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px'  width='5%' ><b>"+format.format(kardexItemMovimiento.getFecha())+"</b></td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px'   ><b>"+kardexItemMovimiento.getTipo()+"</b></td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+kardexItemMovimiento.getNumero()+"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+kardexItemMovimiento.getTipoIngresoSalida()+"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+(kardexItemMovimiento.getCodLoteProduccion()!=null?kardexItemMovimiento.getCodLoteProduccion():"")+"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getCantidadIngreso()) +"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getCantidadSalida()) +"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getSaldo())+"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;</td>");

                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getCostoUnitario())+"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getDebe())+"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getHaber())+"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getSaldoDinero())+"</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;</td>");
                                out.print("<td align='left'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getValorUfv())+"</td>");
                                out.print("</tr>");
                           }
                           %>
                           </table>
                           <%
                       }
                       }catch(Exception e){
                           e.printStackTrace();
                       }
                         
                    %>

                 
            <br>

            <br>
            <div align="center">
                <%--<INPUT type="button" class="commandButton" name="btn_registrar" value="<-- Atrás" onClick="cancelar();"  >--%>

            </div>
        </form>
    </body>
</html>
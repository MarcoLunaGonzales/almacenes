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
public int cuentaCapitulos(List kardexMovimientoList,String nombreCapitulo){
    Iterator i = kardexMovimientoList.iterator();
    int cantidadCapitulos = 0;
    while(i.hasNext()){
        KardexMovimiento kardexMovimiento = (KardexMovimiento)i.next();
        if(kardexMovimiento.getMateriales().getGrupos().getCapitulos().getNombreCapitulo().equals(nombreCapitulo)){
            cantidadCapitulos++;
        }
    }
    return cantidadCapitulos;
}
public int cuentaGrupos(List kardexMovimientoList,String nombreGrupo){
    Iterator i = kardexMovimientoList.iterator();
    int cantidadGrupos = 0;
    while(i.hasNext()){
        KardexMovimiento kardexMovimiento = (KardexMovimiento)i.next();
        if(kardexMovimiento.getMateriales().getGrupos().getNombreGrupo().equals(nombreGrupo)){
            cantidadGrupos++;
        }
    }
    return cantidadGrupos;
}
public double ultimoCostoFecha(KardexMovimiento kardexMovimiento){
    double costo = 0;
    try {
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        con = Util.openConnection(con);
        String sql_aux = "select top 1 c.COSTO_MATERIAL costo from COSTOS_MATERIAL_POR_MES c where c.COD_MATERIAL = '"+kardexMovimiento.getMateriales().getCodMaterial()+"' and c.fecha between '"+format.format(kardexMovimiento.getFechaInicio())+"' and '"+format.format(kardexMovimiento.getFechaFinal())+"' and c.COD_ALMACEN = '"+kardexMovimiento.getAlmacenes().getCodAlmacen()+"' order by c.FECHA desc";
        System.out.println("sql:" + sql_aux);
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery(sql_aux);
        if(rs.next()) {
            costo = rs.getDouble("costo");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return costo;
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
                        formato.applyPattern("#,##0.00;(#,##0.00)");

                        NumberFormat numeroformato1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        DecimalFormat formato1 = (DecimalFormat) numeroformato1;
                        formato1.applyPattern("#.#####;(#.#####)");
                        //DecimalFormat formatoCostos = (DecimalFormat) numeroformato;
                        //formatoCostos.applyPattern("#.#####;(#.#####)");

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
                       while(i.hasNext()){
                           KardexMovimiento kardexMovimiento = (KardexMovimiento)i.next();
                           %>
                           <table border="0" class="outputText0" align="center">
                                <tbody>
                                    <tr>
                                        <td><b>Almacen : </b></td>
                                        <td><%=kardexMovimiento.getAlmacenes().getNombreAlmacen()%> &nbsp;</td>
                                        <td><b>&nbsp;</b></td>
                                        <td></td>
                                    </tr>
                                    

                                        <tr>
                                        <td><b>Fecha Inicio :</b></td>
                                        <td><%=format.format(kardexMovimiento.getFechaInicio())%></td>
                                        <td><b>Fecha Final :</b></td>
                                        <td><%=format.format(kardexMovimiento.getFechaFinal())%></td>

                                    </tr>

                                </tbody>
                            </table>
                           <%
                           break;}
                       i = kardexMovimientoList.iterator();
                       //iteracion 1
                       String nombreCapitulo = "";
                       String nombreGrupo = "";
                       
                           %>
                       <table width="100%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >

                        <tr class="">
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Capitulo</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Grupo</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Item</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center" ><b>Saldo Inicial</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center"><b>Ingresos</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Salidas</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center"><b>Saldo Final</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center"><b>Monto Inicial</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Debe</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Haber</b></td>
                            <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2" width='5%' align="center"><b>Monto Saldo</b></td>
                            <td align="center" style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2" width='5%' align="center"><b>Act.</b></td>
                            

                        </tr>
                           <%
                           int codCapitulo = 0;
                           int codGrupo = 0;
                           double cantidadSaldoInicialGeneral = 0;
                           double cantidadIngresoTotalGeneral = 0;
                           double cantidadSalidaTotalGeneral = 0;
                           double cantidadSaldoTotalGeneral = 0;
                           double montoInicialGeneral = 0;
                           double montoIngresoTotalGeneral = 0;
                           double montoSalidaTotalGeneral = 0;
                           double montoSaldoTotalGeneral = 0;
                           double montoDiferenciaGeneral = 0;
                           while(i.hasNext()){
                           KardexMovimiento kardexMovimiento = (KardexMovimiento)i.next();
                           double cantidadIngresoTotal = 0;
                           double cantidadSalidaTotal = 0;
                           double cantidadSaldoTotal = 0;
                           double montoIngresoTotal = 0;
                           double montoSalidaTotal = 0;
                           double montoSaldoTotal = 0;
                           double montoDiferencia = 0;
                           double ufv = 0;
                              // detalle del reporte
                           Iterator i1 = kardexMovimiento.getKardexItemMovimientoList().iterator();
                           while(i1.hasNext()){
                               KardexItemMovimiento kardexItemMovimiento = (KardexItemMovimiento)i1.next();
                                /*out.print("<tr>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px'  width='5%' ><b>"+format.format(kardexItemMovimiento.getFecha())+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px'   ><b>"+kardexItemMovimiento.getTipo()+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+kardexItemMovimiento.getNumero()+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+kardexItemMovimiento.getTipoIngresoSalida()+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+(kardexItemMovimiento.getCodLoteProduccion()!=null?kardexItemMovimiento.getCodLoteProduccion():"")+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getCantidadIngreso()) +"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getCantidadSalida()) +"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getSaldo())+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;</td>");

                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato1.format(kardexItemMovimiento.getCostoUnitario())+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getDebe())+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getHaber())+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getSaldoDinero())+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato.format(kardexItemMovimiento.getDiferenciaActualizado())+"</td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;"+formato1.format(kardexItemMovimiento.getValorUfv())+"</td>");
                                out.print("</tr>");*/
                                cantidadIngresoTotal = cantidadIngresoTotal + kardexItemMovimiento.getCantidadIngreso();
                                cantidadSalidaTotal = cantidadSalidaTotal + kardexItemMovimiento.getCantidadSalida();
                                cantidadSaldoTotal = kardexItemMovimiento.getSaldo();
                                montoIngresoTotal = montoIngresoTotal + kardexItemMovimiento.getDebe();
                                montoSalidaTotal = montoSalidaTotal + kardexItemMovimiento.getHaber();
                                montoSaldoTotal = kardexItemMovimiento.getSaldoDinero();
                                montoDiferencia = montoDiferencia + kardexItemMovimiento.getDiferenciaActualizado();

                                
                           }
                           out.print("<tr>");
                                if(codCapitulo!=kardexMovimiento.getMateriales().getGrupos().getCapitulos().getCodCapitulo()){out.print("<td align='right'  style='border : solid #f2f2f2 1px' rowspan = '"+this.cuentaCapitulos(kardexMovimientoList, kardexMovimiento.getMateriales().getGrupos().getCapitulos().getNombreCapitulo())+"'><b>"+kardexMovimiento.getMateriales().getGrupos().getCapitulos().getNombreCapitulo()+"</b></td> ");}
                                if(codGrupo!=kardexMovimiento.getMateriales().getGrupos().getCodGrupo()){out.print("<td align='right'  style='border : solid #f2f2f2 1px' rowspan = '"+this.cuentaGrupos(kardexMovimientoList, kardexMovimiento.getMateriales().getGrupos().getNombreGrupo())+"'><b>"+kardexMovimiento.getMateriales().getGrupos().getNombreGrupo()+"</b></td> ");}
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' ><b>"+kardexMovimiento.getMateriales().getNombreMaterial()+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px'   ><b>"+formato.format(kardexMovimiento.getSaldo())+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(cantidadIngresoTotal) +"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(cantidadSalidaTotal) +"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(cantidadSaldoTotal)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(kardexMovimiento.getMonto())+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoIngresoTotal)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoSalidaTotal)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoSaldoTotal)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoDiferencia)+"</b></td>");
                                out.print("</tr>");
                                codCapitulo=kardexMovimiento.getMateriales().getGrupos().getCapitulos().getCodCapitulo();
                                codGrupo=kardexMovimiento.getMateriales().getGrupos().getCodGrupo();

                                cantidadSaldoInicialGeneral = cantidadSaldoInicialGeneral + kardexMovimiento.getSaldo();
                                cantidadIngresoTotalGeneral = cantidadIngresoTotalGeneral + cantidadIngresoTotal;
                                cantidadSalidaTotalGeneral = cantidadSalidaTotalGeneral + cantidadSalidaTotal;
                                cantidadSaldoTotalGeneral = cantidadSaldoTotalGeneral +cantidadSaldoTotal;
                                montoInicialGeneral = montoInicialGeneral + kardexMovimiento.getMonto();
                                montoIngresoTotalGeneral = montoIngresoTotalGeneral + montoIngresoTotal;
                                montoSalidaTotalGeneral = montoSalidaTotalGeneral + montoSalidaTotal;
                                montoSaldoTotalGeneral = montoSaldoTotalGeneral+ (montoSaldoTotal==0?this.ultimoCostoFecha(kardexMovimiento)*kardexMovimiento.getSaldo():montoSaldoTotal);
                                montoDiferenciaGeneral = montoDiferenciaGeneral + montoDiferencia;
                                
                       }
                             
                                out.print("</tr>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px'   ><b></b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px'   ><b></b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px'   ><b>TOTALES</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px'   ><b>"+formato.format(cantidadSaldoInicialGeneral)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(cantidadIngresoTotalGeneral) +"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(cantidadSalidaTotalGeneral) +"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(cantidadSaldoTotalGeneral)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoInicialGeneral)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoIngresoTotalGeneral)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoSalidaTotalGeneral)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoSaldoTotalGeneral)+"</b></td>");
                                out.print("<td align='right'  style='border : solid #f2f2f2 1px' >&nbsp;<b>"+formato.format(montoDiferenciaGeneral)+"</b></td>");
                                out.print("</tr>");
                           
                       %>
                       </table>
                           <%
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
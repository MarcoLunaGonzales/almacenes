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
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.ParseException" %>

<%@ page import = "javax.servlet.http.HttpServletRequest"%>
<%@ page import = "java.text.DecimalFormat"%>
<%@ page import = "java.text.NumberFormat"%>
<%@ page import = "java.util.Locale"%>
<%@ page language="java" import="java.util.*,com.cofar.util.CofarConnection" %>


<%! Connection con = null;
    String codPresentacion = "";
    String nombrePresentacion = "";
    String linea_mkt = "";
    String agenciaVenta = "";
%>
<%! 

public String nombrePresentacion1() {



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
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../../js/general.js"></script>
        <script type="text/javascript" src="jquery/jquery.min.js"></script>
        <style>
            body{
                text-align: center;
                font-family: Arial, sans-serif;
                font-size: 12px;
            }

            a{
                text-decoration: none;
                font-weight: bold;
                color: #555;
            }

            a:hover{
                color: #000;
            }

            div.main{
                margin: auto;
                text-align: left;
                width: 720px;
            }

            div.clean{
                border: 1px solid #850000;
            }

            .clean td, .clean th{
                border: 2px solid #bbb;
                background: #ddd;
                padding: 5px 10px;
                text-align: center;
                border-radius: 2px;
            }

            .clean table{
                margin: auto;
                margin-top: 15px;
                margin-bottom: 15px;
            }

            .clean caption{
                font-weight: bold;

            }

            .gvChart,.clean{
                border: 2px solid #850000;
                border-radius: 5px;
                -moz-border-radius: 10px;
                width: 720px;

                margin: auto;
                margin-top: 20px;
            }

            pre{
                background: #eee;
                padding: 10px;
                border-radius: 10px;
                -moz-border-radius: 10px;
            }
        </style>
    </head>
    <body >
        <form>
            <h4 align="center">Reporte Promedio Mensual General</h4>

            <%
        try {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) nf;
            formato.applyPattern("#,##0.00");
            String estadoMaterial=request.getParameter("estadoMaterial");
            String itemEstado=request.getParameter("itemEstado");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
            con = Util.openConnection(con);
            String fechaInicial = request.getParameter("fecha1") == null ? "01/01/2011" : request.getParameter("fecha1");
            String fechaInicial1 = fechaInicial;
            String arrayfecha1[] = fechaInicial.split("/");
            fechaInicial = arrayfecha1[2] + "/" + arrayfecha1[1] + "/01";

            String fechaFinal = request.getParameter("fecha2") == null ? "01/01/2011" : request.getParameter("fecha2");
            String fechaFinal1 = fechaFinal;
            String arrayfecha2[] = fechaFinal.split("/");
            Date fechaFin=new Date(Integer.valueOf(arrayfecha2[2]),Integer.valueOf(arrayfecha2[1]),1,1,1,1);
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(fechaFin.getTime());
            cal.add(Calendar.MONTH,1);
            
            Date resultado=new Date(cal.getTimeInMillis());
            fechaFinal =resultado.getYear()  + "/" + resultado.getMonth() + "/" + resultado.getDate();
            String fechaFinalResult = arrayfecha2[2] + "/" + arrayfecha2[1] + "/" + arrayfecha2[0];
             int cantMeses=(((Integer.valueOf(arrayfecha2[2])*12)+Integer.valueOf(arrayfecha2[1]))-((Integer.valueOf(arrayfecha1[2])*12)+Integer.valueOf(arrayfecha1[1])));
             
             cantMeses++;
             System.out.println("cant meses "+cantMeses);
            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            String nombreMaterialP = request.getParameter("nombreMaterialP") == null ? "''" : request.getParameter("nombreMaterialP");
            //codMaterial = codMaterial.substring(1);
            String codGrupo = request.getParameter("codGrupoArray") == null ? "''" : request.getParameter("codGrupoArray");
            String codCapitulo = request.getParameter("codCapituloArray") == null ? "''" : request.getParameter("codCapituloArray");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");
            String codProveedor = request.getParameter("codProveedor") == null ? "''" : request.getParameter("codProveedor");
            String codTipoIngresoAlmacen = request.getParameter("codTipoIngresoAlmacen") == null ? "''" : request.getParameter("codTipoIngresoAlmacen");
            String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");

            System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " " +
                    "" + codFilial + " " + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);
            double costoTotal = 0;
                        


            %>

            <br>
            <table width="70%" border="0" align="center">
                <tr>
                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:5px">
                        <img src="../../../img/cofar.png" alt="logo cofar" align="left" />
                    </td><td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px">
                        <table border="0" class="outputText1" >
                            <tbody>
                                <tr style="width:12px;">
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Filial</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=nombreFilial%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Almacen</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=nombreAlmacen%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Capitulo</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=nombreCapitulo%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Grupo</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=nombreGrupo%></td>
                                </tr>
                                <tr>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><b>Fecha Inicial</b></td>
                                    <td style="border-bottom:SOLID 1PX #CCCCCC;padding:1px"><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>
             <table class="outputText2" align="CENTER" width="100%">
                <tr bgcolor="#cccccc" style="width:12px;">
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Item</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Consumo Promedio Mensual</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Unid</th>
                    <th style="border-bottom:SOLID 1PX #CCCCCC;">Saldo al</th>
                </tr>

              <%

              String consulta="select m.COD_MATERIAL,m.NOMBRE_MATERIAL,ISNULL(u.ABREVIATURA,'') as nombreUnidad,"+
                              " (select round(isnull(sum(sad.cantidad_salida_almacen),0),5)as cantidad_salida_almacen"+
                              " from salidas_almacen sa, SALIDAS_ALMACEN_DETALLE sad"+
                              " where sa.cod_salida_almacen=sad.cod_salida_almacen and sad.cod_material=m.COD_MATERIAL"+
                              " and sa.cod_estado_salida_almacen=1 and sa.estado_sistema=1"+
                              " and sa.cod_almacen=" + codAlmacen + ""+
                              " and sa.FECHA_SALIDA_ALMACEN BETWEEN '"+fechaInicial+" 00:00:00' and '"+format.format(resultado)+" 00:00:00') as cantidadSal," +
                              " (select( ISNULL((SELECT sum(iad.cant_total_ingreso_fisico)as ingresos_total_anterior FROM ingresos_almacen_detalle iad,ingresos_almacen ia where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen=1 and ia.estado_sistema=1 and iad.cod_material=m.COD_MATERIAL and ia.cod_almacen= '"+codAlmacen+"'  and ia.fecha_ingreso_almacen<'"+format.format(resultado)+" 00:00:00' ),0)-"+
                              " ISNULL((select sum(sad.cantidad_salida_almacen)as salidas_total_anterior from salidas_almacen_detalle sad,salidas_almacen sa where sad.cod_salida_almacen=sa.cod_salida_almacen and sa.cod_estado_salida_almacen=1 and sa.estado_sistema=1 and sad.cod_material=m.COD_MATERIAL and sa.cod_almacen= '"+codAlmacen+"'  and sa.fecha_salida_almacen<='"+format.format(resultado)+" 00:00:00'),0)) as cant) as cantidadSaldo"+
                              " from MATERIALES m inner join GRUPOS g on m.COD_GRUPO=g.COD_GRUPO" +
                              " left outer join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA"+
                              " where 1=1 "+(codMaterial.equals("")?"":" and m.COD_MATERIAL in ("+codMaterial+")")+
                              (codGrupo.equals("")?"":" and g.COD_GRUPO in ("+codGrupo+")")+
                              (codCapitulo.equals("")?"":" and g.COD_CAPITULO in ("+codCapitulo+")")+
                              " and m.MOVIMIENTO_ITEM='"+itemEstado+"'"+
                              " and m.COD_ESTADO_REGISTRO='"+estadoMaterial+"'"+
                              " order by m.NOMBRE_MATERIAL";
              System.out.println("consulta materiales "+consulta);
              Statement stMaterial=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
              ResultSet resMaterial=stMaterial.executeQuery(consulta);
              String codMaterialRep="";
              String nombreMaterial="";
              String unidadMedida="";
              
              while(resMaterial.next())
              {
                  unidadMedida=resMaterial.getString("nombreUnidad");
                  codMaterial=resMaterial.getString("COD_MATERIAL");
                  nombreMaterial=resMaterial.getString("NOMBRE_MATERIAL");
                  out.println("<tr>");
                  out.println("<td style='border-bottom:SOLID 1PX #CCCCCC;' align='left' class='outputText2'>"+nombreMaterial+"</td>");
                  out.println("<td style='border-bottom:SOLID 1PX #CCCCCC;' align='right'>"+formato.format(resMaterial.getDouble("cantidadSal")/cantMeses)+"</td>");
                  out.println("<td style='border-bottom:SOLID 1PX #CCCCCC;'>"+unidadMedida+"</td>");
                  out.println("<td style='border-bottom:SOLID 1PX #CCCCCC;' align='right'>"+formato.format(resMaterial.getDouble("cantidadSaldo"))+"</td>");
                  out.println("</tr>");
              }
              resMaterial.close();
              stMaterial.close();
              con.close();
              %>
             </table >
              <%
        } catch (Exception e) {
            e.printStackTrace();
        }

            %>

        </form>
    </body>
</html>


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
<%@ page import="org.joda.time.DateTime" %>
<%@ page import="java.text.SimpleDateFormat" %>

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

    public double saldoInicial(String codMaterial, String fechaSaldoInicial, String codAlmacen) {



        String nombreproducto = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//ManagedAccesoSistema bean1=(ManagedAccesoSistema)com.cofar.util.Util.getSessionBean("ManagedAccesoSistema");
        double ingresos = 0;
        double salidas = 0;
        try {
            con = Util.openConnection(con);
            String consulta = " SELECT sum(iad.cant_total_ingreso_fisico)as ingresos_total_anterior " +
                    " FROM ingresos_almacen_detalle iad,ingresos_almacen ia " +
                    " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen " +
                    " and ia.cod_estado_ingreso_almacen=1 and ia.estado_sistema=1 " +
                    " and iad.cod_material=" + codMaterial + " and ia.cod_almacen='" + codAlmacen + "' " +
                    " and ia.fecha_ingreso_almacen<='" + fechaSaldoInicial + " 00:00:00' ";
            System.out.println("PresentacionesProducto:sql:" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);


            if (rs.next()) {
                ingresos = rs.getDouble("ingresos_total_anterior");
            }

            consulta = " select sum(sad.cantidad_salida_almacen)as salidas_total_anterior " +
                    " from salidas_almacen_detalle sad,salidas_almacen sa " +
                    " where sad.cod_salida_almacen=sa.cod_salida_almacen " +
                    " and sa.cod_estado_salida_almacen=1 and sa.estado_sistema=1 " +
                    " and sad.cod_material=" + codMaterial + " and sa.cod_almacen='" + codAlmacen + "' " +
                    " and sa.fecha_salida_almacen<='" + fechaSaldoInicial + " 00:00:00'  ";

            System.out.println("PresentacionesProducto:sql:" + consulta);
            Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1 = st1.executeQuery(consulta);

            if (rs1.next()) {
                salidas = rs1.getDouble("salidas_total_anterior");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ingresos - salidas);
    }
    String unidadMedida(String codMaterial){
        String unidadMedida = "";
        try{
            con = Util.openConnection(con);
            String consulta = " select um.abreviatura from materiales m inner join unidades_medida um on um.cod_unidad_medida = m.cod_unidad_medida where m.cod_material = '"+codMaterial+"' ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            System.out.println("consulta " + consulta);
            if(rs.next()){
                unidadMedida = rs.getString("abreviatura");
            }
        }catch(Exception e){e.printStackTrace();
        }
        return unidadMedida;
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
	 <style type="text/css">
/*            @media print {
            thead {
                display: table-header-group;
            }
            tfoot {
                display: table-footer-group;
            }
            }
            @media screen {
            thead {
                display: block;
            }
            tfoot {
                display: block;
            }
            }*/
        </style>
    </head>
    <body>
        <form>
            <h4 align="center">KARDEX DE MOVIMIENTO</h4>

            <%
        try {

            /*
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="codCompProd">
            <input type="hidden" name="nombreCompProd">
             */

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
         


            NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("###0.00;(###0.00)");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

            con = Util.openConnection(con);



            String fechaInicial = request.getParameter("fechaInicial") == null ? "01/01/2011" : request.getParameter("fechaInicial");
            String fechaIniR = fechaInicial;
            String arrayfecha[] = fechaInicial.split("/");
            fechaInicial = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
            
            // calculo de la fecha anterior
            DateTime dt = new DateTime();
            dt = dt.withYear(Integer.valueOf(arrayfecha[2]));
            dt = dt.withMonthOfYear(Integer.valueOf(arrayfecha[1]));
            dt = dt.withDayOfMonth(Integer.valueOf(arrayfecha[0]));
            System.out.println("fecha antes de decrementar " + dt);
            dt = dt.minusMonths(1);
            dt = dt.dayOfMonth().withMaximumValue();
            System.out.println("fecha despues de decrementar " + dt);
            Date fechaSaldoInicial = dt.toDate();
            


            String fechaFinal = request.getParameter("fechaFinal") == null ? "01/01/2011" : request.getParameter("fechaFinal");
            String fechaFinR = fechaFinal;
            arrayfecha = fechaFinal.split("/");
            fechaFinal = arrayfecha[2] + "/" + arrayfecha[1] + "/" + arrayfecha[0];
           

            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            //codMaterial = codMaterial.substring(1);
            String codGrupo = request.getParameter("codGrupo") == null ? "''" : request.getParameter("codGrupo");
            String codCapitulo = request.getParameter("codCapitulo") == null ? "''" : request.getParameter("codCapitulo");
            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");
            String nombreCapitulo = request.getParameter("nombreCapitulo") == null ? "''" : request.getParameter("nombreCapitulo");
            String nombreGrupo = request.getParameter("nombreGrupo") == null ? "''" : request.getParameter("nombreGrupo");
            String nombreMaterial = request.getParameter("nombreMateriales") == null ? "''" : request.getParameter("nombreMateriales");



            System.out.println("parametros" + fechaInicial + " " + codMaterial + " " + codGrupo + " " + " " + codCapitulo + " " + codAlmacen + " " + codStock + " " +
                    "" + nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo);



            

            %>

            <%--
            <div class="outputText0" align="center">
                PROGRAMA PRODUCCION: <%= nombreProgramaProduccionPeriodo %> <br>
                AREA(S) : <%= arrayNombreAreaEmpresa %><br>
                DE <%= arraydesde[0] +"/"+ arraydesde[1]+"/"+arraydesde[2] %> <br>
                HASTA <%= arrayhasta[0] +"/"+ arrayhasta[1]+"/"+arrayhasta[2] %>
                <%--PRODUCTO<%=nombreComponenteProd%>--%>
                <%--
            </div--%>

            <br>
                  <%
                String codMaterialV[] = codMaterial.split(",");
                String nombresMaterial[]=nombreMaterial.split(",");
                for (int i = 0; i < codMaterialV.length; i++) {
                    int cod_mate = Integer.parseInt(codMaterialV[i]);
                    double saldoInicial = this.saldoInicial(String.valueOf(cod_mate), fechaInicial, codAlmacen);

            %>
            <table width="70%" border="0" align="center">
                <tr>
                    <td>
                        <img src="../../img/cofar.png" alt="logo cofar" align="left" />
                    </td><td>
                        <table border="0" class="outputText1" >
                            <tbody>
                                <tr>
                                    <td><b>Almacen</b></td>
                                    <td><%=nombreAlmacen%></td>
                                </tr>

                                <tr>
                                    <td><b>Item</b></td>
                                    <td><%=nombresMaterial[i]%></td>
                                </tr>
                                <tr>
                                    <td><b>Fecha Inicio</b></td>
                                    <td><%=fechaIniR%></td>
                                </tr>
                                <tr>
                                    <td><b>Fecha Final</b></td>
                                    <td><%=fechaFinR%></td>
                                </tr>
                                <tr>
                                    <td><b>Existencia a Fecha Inicio Reporte :</b></td>
                                    <td><%=formato.format(saldoInicial) + " " + this.unidadMedida(codMaterial) %></td>
                                </tr>

                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>

          



            <table width="100%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >
	        <thead>
                <tr bgcolor="#cccccc" class="">
                    <td height="30px" class="border" style="border : solid #D8D8D8 1px" bgcolor="#cccccc"  align="center" ><b>Fecha</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#cccccc"  align="center" ><b>Tipo I/S</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#cccccc"  align="center"><b>Nro</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#cccccc"  align="center"><b>Lote</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#cccccc"  align="center"><b>Area</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#cccccc"  align="center"><b>Producto</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#cccccc"  align="center"><b>Entrada</b></td>
                    <td style="border : solid #D8D8D8 1px" class="border"  bgcolor="#cccccc"  align="center"><b>Salida</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#cccccc" width="8%"  align="center"><b>Saldo</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#cccccc"  align="center"><b>Tipo Ingreso/Salida</b></td>


                </tr>
		</thead>

                <%

                String consulta = " select * from ( " +
                        " SELECT  iad.cod_ingreso_almacen,ia.cod_almacen, " +
                        " '' cod_area_empresa,iad.cant_total_ingreso_fisico,'' cantidad_salida_almacen,iad.cod_material, " +
                        " um.abreviatura,(select sa.cod_lote_produccion from salidas_almacen sa, devoluciones d where d.cod_salida_almacen=sa.cod_salida_almacen and d.cod_devolucion=ia.cod_devolucion)as cod_lote_produccion, " +
                        " (select pr.nombre_prod_semiterminado from componentes_prod pr,salidas_almacen sa, devoluciones d where sa.cod_prod=pr.cod_compprod and d.cod_salida_almacen=sa.cod_salida_almacen and d.cod_devolucion=ia.cod_devolucion)as nombre_producto, " +
                        " ia.nro_ingreso_almacen,tia.nombre_tipo_ingreso_almacen, " +
                        " ia.obs_ingreso_almacen,ia.fecha_ingreso_almacen,'Ingreso' tipo,'' nombre_area_empresa FROM ingresos_almacen_detalle iad, ingresos_almacen ia,tipos_ingreso_almacen tia, " +
                        " unidades_medida um where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen=1 and iad.cod_unidad_medida=um.cod_unidad_medida " +
                        " and ia.cod_tipo_ingreso_almacen=tia.cod_tipo_ingreso_almacen"
                        + " and iad.cod_material IN (" + cod_mate + ") and ia.cod_almacen=" + codAlmacen 
                        + " and ia.fecha_ingreso_almacen>='" + fechaInicial + " 00:00:00'  " +
                        " and ia.fecha_ingreso_almacen<='" + fechaFinal + " 23:59:59' "
                        +" and ia.cod_tipo_ingreso_almacen<>11"
                        + "UNION ALL "
                        + "SELECT  sad.cod_salida_almacen,sa.cod_almacen, sa.cod_area_empresa"
                        + ",'',sad.cantidad_salida_almacen,sad.cod_material, " +
                        " um.abreviatura,sa.cod_lote_produccion, "
                        + "(select pr.nombre_prod_semiterminado from componentes_prod pr where sa.cod_prod=pr.cod_compprod)as producto, sa.nro_salida_almacen,tsa.nombre_tipo_salida_almacen, " +
                        " sa.obs_salida_almacen,sa.fecha_salida_almacen,'Salida',ae.nombre_area_empresa FROM salidas_almacen_detalle sad, salidas_almacen sa,tipos_salidas_almacen tsa,  unidades_medida um,areas_empresa ae " +
                        " where sad.cod_salida_almacen=sa.cod_salida_almacen"
                        + " and sa.cod_estado_salida_almacen=1"
                        + " and sad.cod_unidad_medida=um.cod_unidad_medida " 
                        + " and sa.cod_tipo_salida_almacen=tsa.cod_tipo_salida_almacen"
                        + " and sa.cod_area_empresa = ae.cod_area_empresa"
                        + " and sad.cod_material IN (" + cod_mate + ")"
                        + " and sa.cod_almacen=" + codAlmacen + ""
                        + " and sa.fecha_salida_almacen>='" + fechaInicial + " 00:00:00'  " 
                        + " and sa.fecha_salida_almacen<='" + fechaFinal + " 23:59:59'"
                        + " and sa.cod_tipo_salida_almacen<>14"
                        + ") as tabla order by fecha_ingreso_almacen  ";

                System.out.println("consulta 1 " + consulta);
                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                double cantIngresoTotal=0d;
                double cantSalidaTotal=0d;
                double cantSaldo=saldoInicial;
                int cont=0;
                double saldo = saldoInicial;
                String abreviatura="";
                while (rs.next()) {

                    saldo = saldo + rs.getDouble("cant_total_ingreso_fisico") - rs.getDouble("cantidad_salida_almacen");
                    out.print("<tr>");
                    out.print("<td class='border' align='left'  style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;' >" + (rs.getDate("fecha_ingreso_almacen") == null ? "" : sdf1.format(rs.getDate("fecha_ingreso_almacen")) + " " + sdf2.format(rs.getTimestamp("fecha_ingreso_almacen").getTime())) + "</td>");
                    out.print("<td  class='border' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;'  align='center'  >" + rs.getString("tipo") + "</td>");
                    out.print("<td align='right' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;' class='border'   >" + (rs.getString("nro_ingreso_almacen") == null ? "" : rs.getString("nro_ingreso_almacen")) + "</td>");
                    out.print("<td align='right' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;' class='border'   >" + (rs.getString("cod_lote_produccion") == null ? "" : rs.getString("cod_lote_produccion")) + "</td>");
                    out.print("<td align='right' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;' class='border'   >" + (rs.getString("nombre_area_empresa") == null ? "" : rs.getString("nombre_area_empresa")) + "</td>");
                    out.print("<td align='right' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;' class='border'   >" + (rs.getString("nombre_producto") == null ? "" : rs.getString("nombre_producto")) + "</td>");
                    out.print("<td align='right' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;'  >" + formato.format(rs.getDouble("cant_total_ingreso_fisico")) + " " + (rs.getString("abreviatura") == null ? "" : rs.getString("abreviatura")) + "</td>"); //abreviatura
                    out.print("<td align='right' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;'  >" + formato.format(rs.getDouble("cantidad_salida_almacen")) + " " + (rs.getString("abreviatura") == null ? "" : rs.getString("abreviatura")) + "</td>");
                    out.print("<td align='right' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;'  >" + formato.format(saldo) + " " + (rs.getString("abreviatura") == null ? "" : rs.getString("abreviatura")) + "</td>");
                    out.print("<td align='left' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 0px;'  >" + (rs.getString("nombre_tipo_ingreso_almacen") == null ? "" : rs.getString("nombre_tipo_ingreso_almacen")) + "</td>");
                    out.print("</tr>");
                    cantIngresoTotal+=rs.getDouble("cant_total_ingreso_fisico");
                    cantSalidaTotal+=rs.getDouble("cantidad_salida_almacen");
                    cantSaldo=saldo;
                    cont++;
                    abreviatura=rs.getString("abreviatura");
                }
                    NumberFormat numeroformato1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    DecimalFormat formato1 = (DecimalFormat) numeroformato1;
                    formato1.applyPattern("#,###,##0.00;(#,###,##0.00)");
                    out.print("<tr>");
                    out.print("<td colspan=6 height='30px' class='border' align='left'  style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 5px;' ></td>");
                    out.print("<td  class='border' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 5px;'  align='center'  ><b>" + formato1.format(cantIngresoTotal) + " "+abreviatura+"</b></td>");
                    out.print("<td  class='border' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 5px;'  align='center'  ><b>" + formato1.format(cantSalidaTotal) +" "+abreviatura+"</b></td>");
                    out.print("<td  class='border' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 5px;'  align='center'  ><b>" + formato1.format(cantSaldo) + " "+abreviatura+"</b></td>");
                    out.print("<td  class='border' style='border-left : solid #D8D8D8 1px;border-bottom: solid #D8D8D8 1px;padding: 5px;'  align='center'  ></td>");
                    out.print("</tr>");
                    
                    consulta="select sum(sad.cantidad_salida_almacen)as cantidad_salida_almacen,month(fecha_salida_almacen),year(fecha_salida_almacen)"+
                            " from salidas_almacen sa, SALIDAS_ALMACEN_DETALLE sad"+
                            " where sa.cod_salida_almacen=sad.cod_salida_almacen and sad.cod_material='"+codMaterial+"'"+
                            " and sa.cod_estado_salida_almacen=1"+
                            " and sa.cod_almacen='"+codAlmacen+"'"+
                            " and cod_lote_produccion is not null and cod_lote_produccion <>'' "+
                            " and fecha_salida_almacen>='"+fechaInicial+"' and fecha_salida_almacen<='"+fechaFinal+" 23:59:59'"+
                            " group by month(fecha_salida_almacen),year(fecha_salida_almacen)"+
                            " order by month(fecha_salida_almacen),year(fecha_salida_almacen)";
                    System.out.println("consulta promedio" +consulta);
                    rs=st.executeQuery(consulta);
                    double cantidadSalida=0d;
                    
                    while(rs.next())
                    {
                            cantidadSalida+=rs.getDouble("cantidad_salida_almacen");
                    }
                    int mm2=Integer.valueOf(fechaFinal.split("/")[1]);
                    
                    if((Integer.valueOf(fechaFinal.split("/")[0])-Integer.valueOf(fechaInicial.split("/")[0]))>0)
                    {
                    
                        mm2=mm2+13;
                    }
                    else
                    {
                        mm2=mm2+1;
                    }System.out.println(mm2-Integer.valueOf(fechaInicial.split("/")[1]));
                    double promedio=cantidadSalida/(mm2-Integer.valueOf(fechaInicial.split("/")[1]));

                %>
            </table>
                   
            <br>
                 <div style="width:100%" align="right"><b>Consumo Promedio Mensual:&nbsp;<%=formato1.format(promedio)%></b></div>
            <br>
            <br>

            <%

            }

        } catch (Exception e) {
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

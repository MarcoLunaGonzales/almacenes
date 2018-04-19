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
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../../js/general.js"></script>
        <script>
            function openPopup(url){
                    window.open(url,'detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
        }
        </script>
    </head>
    <body>
        <form>
           
            <%!    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }
            %>


            <%
        try {

            /*
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="codCompProd">
            <input type="hidden" name="nombreCompProd">
             */



            /* NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) numeroformato;
            formato.applyPattern("####.####;(####.####)");*/
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat formato = (DecimalFormat) nf;
            formato.applyPattern("#,###.00");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");

            con = Util.openConnection(con);



            String fechaInicial =  request.getParameter("fecha1") == null ? "01/01/2011" : request.getParameter("fecha1");
            String fechaInicial1 =  fechaInicial;
            String arrayfecha1[] = fechaInicial.split("/");
            fechaInicial = arrayfecha1[2] + "/" + arrayfecha1[1] + "/" + arrayfecha1[0];

            String fechaFinal = request.getParameter("fecha2") == null ? "01/01/2011" : request.getParameter("fecha2");
            String fechaFinal1 = fechaFinal;
            String arrayfecha2[] = fechaFinal.split("/");
            fechaFinal = arrayfecha2[2] + "/" + arrayfecha2[1] + "/" + arrayfecha2[0];

            String codMaterial = request.getParameter("codMaterial") == null ? "0" : request.getParameter("codMaterial");
            String nombreMaterialP = request.getParameter("nombreMaterialP") == null ? "''" : request.getParameter("nombreMaterialP");
            System.out.println("nombreMaterialP:" + nombreMaterialP);

            String codAlmacen = request.getParameter("codAlmacen") == null ? "''" : request.getParameter("codAlmacen");
            String codStock = request.getParameter("codStock") == null ? "''" : request.getParameter("codStock");
            String nombreFilial = request.getParameter("nombreFilial") == null ? "''" : request.getParameter("nombreFilial");
            String nombreAlmacen = request.getParameter("nombreAlmacen") == null ? "''" : request.getParameter("nombreAlmacen");


            String codProductoP = request.getParameter("codProductoP") == null ? "'0'" : request.getParameter("codProductoP");
            String nombreProductoP = request.getParameter("nombreProductoP") == null ? "''" : request.getParameter("nombreProductoP");
            String codFilial = request.getParameter("codFilial") == null ? "''" : request.getParameter("codFilial");
            String numLoteP = request.getParameter("numLoteP") == null ? "''" : request.getParameter("numLoteP");
            String check = request.getParameter("check") == null ? "" : request.getParameter("check");
            System.out.println("check:"+check);

            //System.out.println("parametros" + fechaInicial + " " + fechaFinal + " " + codMaterial + " " + codGrupo + " " + " "+ codCapitulo +" " + codAlmacen + " " + codStock + " " +
            //"" + codFilial + " " +  nombreFilial + "  " + nombreAlmacen + " " + nombreCapitulo + " " + nombreGrupo );
            double costoTotal = 0;
            double costoTotalCosto = 0;
            double costoTotalDevolucion = 0;
            double totalSalidas = 0;
            double totalDevolucion = 0;

            String consulta =  " select top 1 cp.nombre_prod_semiterminado,s.cod_lote_produccion from SALIDAS_ALMACEN s " +
                    " inner join almacenes a on a.COD_ALMACEN = s.COD_ALMACEN " +
                    " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL " +
                    " inner join COMPONENTES_PROD cp on cp.cod_compprod = s.cod_prod "+
                    " where s.COD_ESTADO_SALIDA_ALMACEN =1 ";
                consulta = consulta + " and cp.cod_compprod >0";
                if (!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01") ) {
                    consulta = consulta + " and s.FECHA_SALIDA_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }
                
                    consulta = consulta + " and s.COD_LOTE_PRODUCCION like '" + numLoteP + "%' ";
             
                if (!codProductoP.equals("") && !codProductoP.equals("0")) {
                    consulta = consulta + " and sa.COD_PROD = " + codProductoP + " ";
                }
                if (!codFilial.equals("") && !codFilial.equals("0")) {
                    consulta = consulta + " and f.cod_filial = '" + codFilial + "' ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    consulta = consulta + " and s.COD_ALMACEN = '" + codAlmacen + "' ";
                }
                
                con = Util.openConnection(con);
                Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs1 = st1.executeQuery(consulta);
                String nombreProdSemiterminado = "";
                String codLoteProduccion = "";
                if(rs1.next()){
                    nombreProdSemiterminado = rs1.getString("nombre_prod_semiterminado");
                    codLoteProduccion = rs1.getString("cod_lote_produccion");
                }
            %>
            


            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >



                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Nro</b></td>
                    <td HEIGHT='25PX' class="border" style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Tipo Salida</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Fecha</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Lote</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Codigo Ant.</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Item</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Capitulo</b></td>
                    <td style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Grupo</b></td>
                    <td  class="border" style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unid.</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#F2F2F2"  align="center"><b>Cantidad</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#F2F2F2"  align="center"><b>Costo Unit.</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#F2F2F2"  align="center"><b>Costo Total</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Unit.</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Total</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Cant. Neta</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Unit. Neto</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Neto</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Producto</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Codigo</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Presentacion</b></td>
                </tr>


                <%

                String salida = "select m.NOMBRE_MATERIAL,u.ABREVIATURA,c.NOMBRE_CAPITULO,g.NOMBRE_GRUPO,sa.FECHA_SALIDA_ALMACEN,sa.NRO_SALIDA_ALMACEN,m.CODIGO_ANTIGUO,m.CODIGO_ANTIGUO,sa.COD_LOTE_PRODUCCION,";
                salida += " sad.cod_material,sad.cod_salida_almacen,isnull(sad.cantidad_salida_almacen,0)as cantidad_salida_almacen,";
                salida += " (select top 1 isnull(sadi.costo_salida_actualizado,0) from salidas_almacen_detalle_ingreso sadi where sad.cod_material=sadi.cod_material and sad.cod_salida_almacen=sadi.cod_salida_almacen)as costo_salida,";
                salida += " (select isnull(sum(sadi.costo_salida_actualizado*sadi.cantidad),0) from salidas_almacen_detalle_ingreso sadi where sad.cod_material=sadi.cod_material and sad.cod_salida_almacen=sadi.cod_salida_almacen)as total_salida,0,0,0,";
                salida += " (select top 1 isnull(ia.COD_ORDEN_COMPRA,0) from salidas_almacen_detalle_ingreso sadi,INGRESOS_ALMACEN ia,INGRESOS_ALMACEN_DETALLE_ESTADO iade";
                salida += " where sad.cod_material=sadi.cod_material and sad.cod_salida_almacen=sadi.cod_salida_almacen and sadi.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN";
                salida += " and sadi.COD_MATERIAL=iade.COD_MATERIAL and sadi.ETIQUETA=iade.ETIQUETA and iade.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN order by ia.cod_orden_compra desc)as cod_oc";
                salida += " ,t.NOMBRE_TIPO_SALIDA_ALMACEN,";
                salida+=" ISNULL(cp.nombre_prod_semiterminado,'') AS nombre_prod_semiterminado,ISNULL((select top 1 cast(pp.cod_anterior as varchar)+'&&&'+cast(pp.NOMBRE_PRODUCTO_PRESENTACION as varchar) from PRESENTACIONES_PRODUCTO pp inner join COMPONENTES_PRESPROD cpp on cpp.COD_PRESENTACION=pp.cod_presentacion"+
                        " and cpp.COD_COMPPROD=cp.COD_COMPPROD and cpp.COD_ESTADO_REGISTRO=1 ),' &&& ') as datosPresentacion";
                salida += " from salidas_almacen_detalle sad";
                salida += " inner join SALIDAS_ALMACEN sa on sa.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN ";
                //salida += " inner join componentes_prod cp on sa.cod_prod = cp.cod_compprod";
                salida += " inner join MATERIALES m on m.COD_MATERIAL = sad.COD_MATERIAL";
                salida += " inner join ALMACENES a on a.COD_ALMACEN = sa.COD_ALMACEN";
                salida += " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = sad.COD_UNIDAD_MEDIDA";
                salida += " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO";
                salida += " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO";
                salida += " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = sa.COD_AREA_EMPRESA";
                salida += " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL";
                salida += " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN=sa.COD_TIPO_SALIDA_ALMACEN";
                salida+=" left outer join COMPONENTES_PROD cp  on cp.COD_COMPPROD=sa.COD_PROD";
                //salida += " inner join COMPONENTES_PROD cp on cp.COD_COMPPROD = sa.COD_PROD";

                System.out.println("fechaInicial:"+fechaInicial);
                System.out.println("fechaFinal:"+fechaFinal);
                salida += " WHERE  sa.COD_ESTADO_SALIDA_ALMACEN<>2  and sa.COD_TIPO_SALIDA_ALMACEN<>7";
                //salida = salida + " and sa.COD_ALMACEN in (1,2) ";
                if (!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01") ) {
                    salida = salida + " and sa.FECHA_SALIDA_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }
                
                    salida = salida + " and sa.COD_LOTE_PRODUCCION like '" + numLoteP + "%' ";

                if (!codProductoP.equals("") && !codProductoP.equals("0")) {
                    salida = salida + " and sa.COD_PROD = " + codProductoP + " ";
                }
                if (!codFilial.equals("") && !codFilial.equals("0")) {
                    salida = salida + " and f.cod_filial = '" + codFilial + "' ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    salida = salida + " and sa.COD_ALMACEN = '" + codAlmacen + "' ";
                }


                salida += " ORDER BY sa.FECHA_SALIDA_ALMACEN asc,sa.NRO_SALIDA_ALMACEN,m.NOMBRE_MATERIAL,sa.COD_LOTE_PRODUCCION   ";




                System.out.println("salida: " + salida);

                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(salida);
                costoTotal = 0;
                costoTotalCosto = 0;
                costoTotalDevolucion = 0;
                int c = 0;
                int nroSalidaAux = 0;
                String codigos="";
                while (rs.next()) {
                   
                    String devoluciones = " select d.cod_devolucion,sa.cod_salida_almacen,";
                    devoluciones += " ia.fecha_ingreso_almacen,ia.cod_ingreso_almacen,";
                    devoluciones += " iad.cod_material,";
                    devoluciones += " isnull(iad.CANT_TOTAL_INGRESO_FISICO,0)as CANT_TOTAL_INGRESO_FISICO,";
                    devoluciones += " isnull(iad.COSTO_UNITARIO_ACTUALIZADO,0)as COSTO_UNITARIO_ACTUALIZADO,";
                    devoluciones += " isnull(iad.COSTO_UNITARIO_ACTUALIZADO*iad.CANT_TOTAL_INGRESO_FISICO,0)as total_salida,";
                    devoluciones += " (select top 1 isnull(ia1.COD_ORDEN_COMPRA,0) from salidas_almacen_detalle_ingreso sadi,INGRESOS_ALMACEN ia1,INGRESOS_ALMACEN_DETALLE_ESTADO iade";
                    devoluciones += " where iad.cod_material=sadi.cod_material and sa.cod_salida_almacen=sadi.cod_salida_almacen and sadi.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN";
                    devoluciones += " and sadi.COD_MATERIAL=iade.COD_MATERIAL and sadi.ETIQUETA=iade.ETIQUETA and iade.COD_INGRESO_ALMACEN=ia1.COD_INGRESO_ALMACEN order by ia1.cod_orden_compra desc)as cod_oc";
                    devoluciones += " from devoluciones d,salidas_almacen sa,ingresos_almacen ia,ingresos_almacen_detalle iad  " +
                            " where d.estado_sistema=1 and d.cod_salida_almacen=sa.cod_salida_almacen and " +
                            " d.cod_devolucion=ia.cod_devolucion and ia.cod_almacen=d.cod_almacen ";
                    devoluciones += " and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen ";
                   
                    if (!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01")) {
                        devoluciones = devoluciones + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }
                    
                        devoluciones = devoluciones + " and sa.COD_LOTE_PRODUCCION like '" + numLoteP + "%' ";
                    
                    if (!codProductoP.equals("") && !codProductoP.equals("0")) {
                        devoluciones = devoluciones + " and sa.COD_PROD = " + codProductoP + " ";
                    }

                    if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                        devoluciones = devoluciones + " and sa.cod_almacen='" + codAlmacen + "' and d.cod_almacen='" + codAlmacen + "' ";
                    }
                    devoluciones +=" and iad.cod_material="+rs.getString("cod_material")+"";
                    devoluciones +=" and sa.cod_salida_almacen="+rs.getString("cod_salida_almacen")+"";

                    devoluciones += " and ia.cod_estado_ingreso_almacen=1 and d.cod_estado_devolucion=1 order by  ia.fecha_ingreso_almacen asc";
                    if(codigos.equals(""))
                        {
                    codigos= "'"+rs.getString("cod_material")+" "+rs.getString("cod_salida_almacen")+"'";
                    }
                    else
                     {
                        codigos+=",'"+rs.getString("cod_material")+" "+rs.getString("cod_salida_almacen")+"'";
                    }

                    System.out.println("devoluciones: " + devoluciones);
                    Statement st_devoluciones = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_devoluciones = st_devoluciones.executeQuery(devoluciones);
                    double cantDev=0;
                    double costoUnitDev=0;
                    double costoTotalDev=0;
                    while(rs_devoluciones.next()){
                        System.out.println("rs_devoluciones.getString(1)"+rs_devoluciones.getDouble("CANT_TOTAL_INGRESO_FISICO"));
                        System.out.println("rs_devoluciones.getString(2)"+rs_devoluciones.getDouble("COSTO_UNITARIO_ACTUALIZADO"));
                        System.out.println("rs_devoluciones.getString(3)"+rs_devoluciones.getDouble("total_salida"));
                        cantDev+=rs_devoluciones.getDouble("CANT_TOTAL_INGRESO_FISICO");
                        costoUnitDev+=rs_devoluciones.getDouble("COSTO_UNITARIO_ACTUALIZADO");
                        costoTotalDev+=rs_devoluciones.getDouble("total_salida");

                    }


                    out.print("<tr>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("NRO_SALIDA_ALMACEN") == null ? "" : rs.getString("NRO_SALIDA_ALMACEN")) + "</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >" + (rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN") == null ? "" : rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")) + "</td>");
                    out.print("<td class='border' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'    >" + format.format(rs.getDate("FECHA_SALIDA_ALMACEN")) + " &nbsp; " + format1.format(rs.getTimestamp("FECHA_SALIDA_ALMACEN").getTime()) + "</td>");
                    out.print("<td  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + (rs.getString("COD_LOTE_PRODUCCION") == null ? "" : rs.getString("COD_LOTE_PRODUCCION")) + "</td>");
                    //out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("NOMBRE_AREA_EMPRESA")==null?"":rs.getString("NOMBRE_AREA_EMPRESA"))+"</td>");
                    //out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >"+(rs.getString("nombre_prod_semiterminado")==null?"":rs.getString("nombre_prod_semiterminado"))+"</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;' class='border'   >" + (rs.getString("CODIGO_ANTIGUO") == null ? "" : rs.getString("CODIGO_ANTIGUO")) + "</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >" + (rs.getString("NOMBRE_MATERIAL") == null ? "" : rs.getString("NOMBRE_MATERIAL")) + "</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >" + (rs.getString("NOMBRE_CAPITULO") == null ? "" : rs.getString("NOMBRE_CAPITULO")) + "</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >" + (rs.getString("NOMBRE_GRUPO") == null ? "" : rs.getString("NOMBRE_GRUPO")) + "</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >" + (rs.getString("ABREVIATURA") == null ? "" : rs.getString("ABREVIATURA")) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(rs.getDouble("cantidad_salida_almacen"))) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (redondear(rs.getDouble("costo_salida"), 4)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(rs.getDouble("total_salida"))) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(cantDev)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (redondear(costoUnitDev, 4)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(costoTotalDev)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(rs.getDouble("cantidad_salida_almacen")-cantDev)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format((rs.getDouble("total_salida")-costoTotalDev)/(rs.getDouble("cantidad_salida_almacen")-cantDev))) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(rs.getDouble("total_salida")-costoTotalDev)) + "</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >" + rs.getString("nombre_prod_semiterminado") + "</td>");
                    String[] array=rs.getString("datosPresentacion").split("&&&");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >" + array[0] + "</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >" + array[1] + "</td>");

                    out.print("</tr>");
                    costoTotal = costoTotal + rs.getDouble("cantidad_salida_almacen");
                    costoTotalCosto = costoTotalCosto + rs.getDouble("total_salida");
                    totalSalidas=totalSalidas+rs.getDouble("total_salida");
                    costoTotalDevolucion = costoTotalDevolucion + costoTotalDev;
                    totalDevolucion=totalDevolucion+costoTotalDev;
                    nroSalidaAux = rs.getInt("NRO_SALIDA_ALMACEN");
                    
                }
                //inicio ale devoluciones
                
                String devoluciones = " select ISNULL((select top 1 cp.nombre_prod_semiterminado from COMPONENTES_PROD cp where cp.COD_COMPPROD=sa.COD_PROD),'') AS nombreProd,"+
                                      " ISNULL((select top 1 cast(pp.cod_anterior as varchar )+'&&&'+cast(pp.NOMBRE_PRODUCTO_PRESENTACION as varchar) from PRESENTACIONES_PRODUCTO pp inner join COMPONENTES_PRESPROD cpp on cpp.COD_PRESENTACION=pp.cod_presentacion"+
                                      " inner join COMPONENTES_PROD cp1 on cp1.COD_COMPPROD=cpp.COD_COMPPROD),' &&& ')as nombreProducto, d.cod_devolucion,sa.cod_salida_almacen,";
                    devoluciones += " ia.fecha_ingreso_almacen,ia.cod_ingreso_almacen,";
                    devoluciones += " iad.cod_material,";
                    devoluciones += " isnull(iad.CANT_TOTAL_INGRESO_FISICO,0)as CANT_TOTAL_INGRESO_FISICO,";
                    devoluciones += " isnull(iad.COSTO_UNITARIO_ACTUALIZADO,0)as COSTO_UNITARIO_ACTUALIZADO,";
                    devoluciones += " isnull(iad.COSTO_UNITARIO_ACTUALIZADO*iad.CANT_TOTAL_INGRESO_FISICO,0)as total_salida,";
                    devoluciones += " (select top 1 isnull(ia1.COD_ORDEN_COMPRA,0) from salidas_almacen_detalle_ingreso sadi,INGRESOS_ALMACEN ia1,INGRESOS_ALMACEN_DETALLE_ESTADO iade";
                    devoluciones += " where iad.cod_material=sadi.cod_material and sa.cod_salida_almacen=sadi.cod_salida_almacen and sadi.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN";
                    devoluciones += " and sadi.COD_MATERIAL=iade.COD_MATERIAL and sadi.ETIQUETA=iade.ETIQUETA and iade.COD_INGRESO_ALMACEN=ia1.COD_INGRESO_ALMACEN order by ia1.cod_orden_compra desc)as cod_oc";
                    devoluciones+=",m.NOMBRE_MATERIAL,sa.NRO_SALIDA_ALMACEN,sa.FECHA_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN,sa.COD_LOTE_PRODUCCION,m.CODIGO_ANTIGUO"+
                                  ",g.NOMBRE_GRUPO,c.NOMBRE_CAPITULO,um.ABREVIATURA";
                    devoluciones += " from UNIDADES_MEDIDA um ,devoluciones d,salidas_almacen sa,ingresos_almacen ia,ingresos_almacen_detalle iad , " ;
                            devoluciones+=" SALIDAS_ALMACEN_DETALLE sad,materiales m,TIPOS_SALIDAS_ALMACEN tsa,grupos g,CAPITULOS c"+
                            " where d.estado_sistema=1 and  sad.COD_SALIDA_ALMACEN=d.COD_SALIDA_ALMACEN and sad.COD_MATERIAL=iad.COD_MATERIAL and m.cod_material=iad.COD_MATERIAL"+
                            " AND tsa.COD_TIPO_SALIDA_ALMACEN=sa.COD_TIPO_SALIDA_ALMACEN and m.COD_GRUPO=g.COD_GRUPO and c.COD_CAPITULO=g.COD_CAPITULO and d.cod_salida_almacen=sa.cod_salida_almacen and um.COD_UNIDAD_MEDIDA=iad.COD_UNIDAD_MEDIDA and" +
                            " d.cod_devolucion=ia.cod_devolucion and ia.cod_almacen=d.cod_almacen ";
                    devoluciones += " and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen ";
                   
                    if (!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01")) {
                        devoluciones = devoluciones + " and ia.fecha_ingreso_almacen between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                    }

                        devoluciones = devoluciones + " and sa.COD_LOTE_PRODUCCION like '" + numLoteP + "%' ";

                    if (!codProductoP.equals("") && !codProductoP.equals("0")) {
                        devoluciones = devoluciones + " and sa.COD_PROD = " + codProductoP + " ";
                    }

                    if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                        devoluciones = devoluciones + " and sa.cod_almacen='" + codAlmacen + "' and d.cod_almacen='" + codAlmacen + "' ";
                    }
                    devoluciones +=(codigos.equals("")?"":" and cast(iad.cod_material as varchar)+' '+cast(sa.cod_salida_almacen as varchar) not in ("+codigos+")");

                    devoluciones += " and ia.cod_estado_ingreso_almacen=1 and d.cod_estado_devolucion=1 order by sa.NRO_SALIDA_ALMACEN, ia.fecha_ingreso_almacen asc";

                    System.out.println("devoluciones 2: " + devoluciones);
                    Statement std=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs_devoluciones=std.executeQuery(devoluciones);
                    double cantDev=0;
                    double costoUnitDev=0;
                    double costoTotalDev=0;
                    SimpleDateFormat sdf2= new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String cabecera="";
                    while(rs_devoluciones.next()){
                        cantDev=rs_devoluciones.getDouble("CANT_TOTAL_INGRESO_FISICO");
                        costoUnitDev=rs_devoluciones.getDouble("COSTO_UNITARIO_ACTUALIZADO");
                        costoTotalDev=rs_devoluciones.getDouble("total_salida");
                        String[] presentacion=rs_devoluciones.getString("nombreProducto").split("&&&");
                    out.print("<tr>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >"+rs_devoluciones.getString("NRO_SALIDA_ALMACEN")+"</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >"+rs_devoluciones.getString("NOMBRE_TIPO_SALIDA_ALMACEN")+"</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >"+sdf2.format(rs_devoluciones.getTimestamp("FECHA_SALIDA_ALMACEN"))+"</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >"+rs_devoluciones.getString("COD_LOTE_PRODUCCION")+"</td>");
                    
                    
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >"+rs_devoluciones.getString("CODIGO_ANTIGUO")+"</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >"+rs_devoluciones.getString("NOMBRE_MATERIAL")+"</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >"+rs_devoluciones.getString("NOMBRE_CAPITULO")+"</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >"+rs_devoluciones.getString("NOMBRE_GRUPO")+"</td>");
                    
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >"+rs_devoluciones.getString("ABREVIATURA")+"</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >0.00</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >0.00</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >0.00</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(cantDev)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (redondear(costoUnitDev, 4)) + "</td>");
                    out.print("<td ALIGN='RIGHT' style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(costoTotalDev)) + "</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >-"+(formato.format(cantDev))+"</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >"+redondear(costoUnitDev, 4)+"</td>");
                    out.print("<td HEIGHT='30PX' class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-right:5PX;' >-"+formato.format(costoTotalDev)+"</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >"+rs_devoluciones.getString("nombreProd")+"</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >"+presentacion[0]+"</td>");
                    out.print("<td style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;'  >"+presentacion[1]+"</td>");

                    out.print("</tr>");
                    
                    costoTotalDevolucion = costoTotalDevolucion + costoTotalDev;
                    totalDevolucion=totalDevolucion+costoTotalDev;

                    }
                // final ale devoluciones

                

                %>
            </table>
            <td class='border' align='right'  style='border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px;padding-left:5PX;font-family:monospace;font-size:x-large' >


            <%



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
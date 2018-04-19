package reportes.reporteIngresosAlmacen.consumoPromedioMensualPorItem;


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
        <script src="../../js/general.js"></script>
    </head>
    <body>
        <form>
            <h4 align="center">Reporte de Ingresos Por Item</h4>

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



            String fechaInicial = request.getParameter("fecha1") == null ? "01/01/2011" : request.getParameter("fecha1");
            String fechaInicial1 = fechaInicial;
            String arrayfecha1[] = fechaInicial.split("/");
            fechaInicial = arrayfecha1[2] + "/" + arrayfecha1[1] + "/" + arrayfecha1[0];

            String fechaFinal = request.getParameter("fecha2") == null ? "01/01/2011" : request.getParameter("fecha2");
            String fechaFinal1 = fechaFinal;
            String arrayfecha2[] = fechaFinal.split("/");
            fechaFinal = arrayfecha2[2] + "/" + arrayfecha2[1] + "/" + arrayfecha2[0];

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
            <table width="70%" border="0" align="center">
                <tr>
                    <td height="20px" style="border-bottom:1px solid #cccccc">
                        <img src="../../../img/cofar.png" alt="logo cofar" align="left" />
                    </td><td height="20px" style="border-bottom:1px solid #cccccc">
                        <table border="0" class="outputText1" >
                            <tbody>
                                <tr>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><b>Filial</b></td>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><%=nombreFilial%></td>
                                </tr>
                                <tr>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><b>Almacen</b></td>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><%=nombreAlmacen%></td>
                                </tr>
                                <tr>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><b>Capitulo</b></td>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><%=nombreCapitulo%></td>
                                </tr>
                                <tr>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><b>Grupo</b></td>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><%=nombreGrupo%></td>
                                </tr>
                                <tr>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><b>Fecha Inicial</b></td>
                                    <td height="20px" style="border-bottom:1px solid #cccccc"><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>

            <BR>
            <BR>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Item</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Consumo Prom. Mensual</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unidad de Medida</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Saldo </b></td>

                </tr>

                <%

                String consulta = " select distinct m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.ABREVIATURA" +
                        //" ia.FECHA_INGRESO_ALMACEN,oc.NRO_ORDEN_COMPRA,t.NOMBRE_TIPO_INGRESO_ALMACEN, " +
                        //" p.NOMBRE_PROVEEDOR,iad.CANT_TOTAL_INGRESO_FISICO,iad.COSTO_UNITARIO,g.NOMBRE_GRUPO,c.NOMBRE_CAPITULO,u.ABREVIATURA " +
                        " from INGRESOS_ALMACEN ia " +
                        " inner join TIPOS_INGRESO_ALMACEN t on ia.COD_TIPO_INGRESO_ALMACEN = t.COD_TIPO_INGRESO_ALMACEN " +
                        " inner join PROVEEDORES p on p.COD_PROVEEDOR = ia.COD_PROVEEDOR " +
                        " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN " +
                        " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA " +
                        " inner join MATERIALES m on m.COD_MATERIAL = iad.COD_MATERIAL " +
                        " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO " +
                        " inner join CAPITULOS c on c.COD_CAPITULO = g.COD_CAPITULO" +
                        " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                        " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL " +
                        " left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA " +
                        " where ia.COD_ESTADO_INGRESO_ALMACEN<>2 and 0=0 ";
                /*if (!nombreMaterialP.equals("")) {
                    consulta = consulta + " and m.nombre_material like '" + nombreMaterialP + "%' ";
                }*/
                if (!codFilial.equals("") && !codFilial.equals("0")) {
                    consulta = consulta + " and f.cod_filial = '" + codFilial + "' ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                    consulta = consulta + " and ia.COD_ALMACEN = '" + codAlmacen + "' ";
                }
                if (!fechaInicial.equals("") && !fechaFinal.equals("")) {
                    consulta = consulta + " and ia.FECHA_INGRESO_ALMACEN between '" + fechaInicial + " 00:00:00' and '" + fechaFinal + " 23:59:59' ";
                }
                if (!codProveedor.equals("") && !codProveedor.equals("0")) {
                    consulta = consulta + " and ia.COD_PROVEEDOR = '" + codProveedor + "' ";
                }
                if (!codTipoIngresoAlmacen.equals("") && !codTipoIngresoAlmacen.equals("0")) {
                    consulta = consulta + " and ia.COD_TIPO_INGRESO_ALMACEN = '" + codTipoIngresoAlmacen + "' ";
                }
                if (!codCapitulo.equals("") && !codCapitulo.equals("0")) {
                    consulta = consulta + " and c.COD_CAPITULO in (" + codCapitulo + ") ";
                }
                if (!codGrupo.equals("") && !codGrupo.equals("0")) {
                    consulta = consulta + " and g.COD_GRUPO in (" + codGrupo + ") ";
                }
                if (!codMaterial.equals("") && !codMaterial.equals("0")) {
                    consulta = consulta + " and m.cod_material in (" + codMaterial + ") ";
                }
                consulta = consulta + " order by m.NOMBRE_MATERIAL desc ";



                System.out.println("consulta 1 " + consulta);

                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                costoTotal = 0;
                while (rs.next()) {
                    int codMaterialKardex = rs.getInt(1);
                    String nomMaterialKardex = rs.getString(2);
                    String abrevUM = rs.getString(3);
                %>
                <td height="20px" style="border-bottom:1px solid #cccccc;padding:5px"><%=nomMaterialKardex%></td>
                <%

                                    /******************************************************************************************************************************/
                                    int suma_total = 0;
                                    int ingresos_total_anterior = 0;
                                    int salidas_total_anterior = 0;
                                    int promedio = 0;
                                    int meses = 0;
                                    int cantidad_restante_anterior = 0;
                                    int cantidad_restante_anterior_kardexmovimiento_global = 0;
                                    Object obj = request.getSession().getAttribute("ManagedAccesoSistema");
                                    ManagedAccesoSistema var = (ManagedAccesoSistema) obj;
                                    String codigoPersonal = var.getUsuarioModuloBean().getCodUsuarioGlobal();

                                    String sql_delete = " delete from kardex_item_movimiento";
                                    sql_delete += " where cod_persona="+codigoPersonal+"";
                                    System.out.println("sql_delete: " + sql_delete);
                                    Statement st_delete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                    int rs_delete = st_delete.executeUpdate(sql_delete);

                                    //saca el consumo promedio mensual
                                    promedio = 0;
                                    suma_total = 0;
                                    int aa = Integer.parseInt(arrayfecha1[2]);
                                    int aa2 = Integer.parseInt(arrayfecha2[2]);
                                    System.out.println("aa:"+aa);
                                    System.out.println("aa2:"+aa2);
                                    int mm = Integer.parseInt(arrayfecha1[1]);
                                    int mm2 = Integer.parseInt(arrayfecha2[1]);
                                    /*decodedate(DTP_fechainicioConsumo.DateTime,aa,mm,dd);
                                    fecha_inicio:=inttostr(mm)+'/'+inttostr(dd)+'/'+inttostr(aa);
                                    decodedate(DTP_fechafinalConsumo.DateTime,aa2,mm2,dd2);
                                    fecha_final:=inttostr(mm2)+'/'+inttostr(dd2)+'/'+inttostr(aa2)+' 23:59:59' ;*/
                                    if (aa2 - aa > 0) {
                                        mm2 = mm2 + 13;
                                    } else {
                                        mm2 = mm2 + 1;
                                    }
                                    System.out.println("mm:"+mm);
                                    System.out.println("mm2:"+mm2);
                                    meses = mm2 - mm;

                                    String sql_salidas = " select sum(sad.cantidad_salida_almacen)as cantidad_salida_almacen,month(fecha_salida_almacen),year(fecha_salida_almacen)";
                                    sql_salidas += " from salidas_almacen sa, SALIDAS_ALMACEN_DETALLE sad";
                                    sql_salidas += " where sa.cod_salida_almacen=sad.cod_salida_almacen and sad.cod_material="+codMaterialKardex+"";
                                    sql_salidas += " and sa.cod_estado_salida_almacen=1";
                                    sql_salidas += " and sa.cod_almacen=" + codAlmacen;
                                    //if(chk_lote.Checked=true)then AQ_consultas.SQL.Add('and cod_lote_produccion is not null and cod_lote_produccion <>''''";
                                    sql_salidas += " and fecha_salida_almacen  >= '" + fechaInicial + " ' and  fecha_salida_almacen  <= '" + fechaFinal + " 23:59:59'";
                                    sql_salidas += " group by month(fecha_salida_almacen),year(fecha_salida_almacen)";
                                    sql_salidas += " order by month(fecha_salida_almacen),year(fecha_salida_almacen)";
                                    System.out.println("sql_salidas--:" + sql_salidas);
                                    Statement st_salidas = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                    ResultSet rs_salidas = st_salidas.executeQuery(sql_salidas);
                                    suma_total=0;
                                    while (rs_salidas.next()) {
                                        System.out.println("suma_total:"+rs_salidas.getInt("cantidad_salida_almacen"));
                                        suma_total = suma_total + rs_salidas.getInt("cantidad_salida_almacen");

                                    }
                                     System.out.println("suma_total--:"+suma_total);
                                     System.out.println("meses--"+meses);
                                    if (suma_total > 0) {
                                        promedio = suma_total / meses;
                                    }

                %>
                <td height="20px" style="border-bottom:1px solid #cccccc;padding:5px"><%=promedio%></td>
                <%

                                    /*decodedate(DTP_fechafinal.Date,aa,mm,dd);
                                    fecha_anterior:=inttostr(mm)+'/'+inttostr(dd)+'/'+inttostr(aa)+' 23:59:59';*/


                                    String sql_ingreso = " SELECT sum(iad.cant_total_ingreso_fisico)as ingresos_total_anterior";
                                    sql_ingreso += " FROM ingresos_almacen_detalle iad,ingresos_almacen ia";
                                    sql_ingreso += " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen";
                                    sql_ingreso += " and ia.cod_estado_ingreso_almacen=1 and ia.estado_sistema=1";
                                    sql_ingreso += " and iad.cod_material=" + codMaterialKardex + "";
                                    sql_ingreso += " and ia.cod_almacen=" + codAlmacen + "";
                                    sql_ingreso += " and ia.fecha_ingreso_almacen<='" + fechaFinal + " 23:59:59'  ";
                                    System.out.println("sql_ingreso:" + sql_ingreso);
                                    Statement st_ingreso = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                    ResultSet rs_ingreso = st_ingreso.executeQuery(sql_ingreso);
                                    ingresos_total_anterior = 0;
                                    while (rs_ingreso.next()) {

                                        ingresos_total_anterior = rs_ingreso.getInt("ingresos_total_anterior");

                                    }
           

                    sql_salidas = " select sum(sad.cantidad_salida_almacen)as salidas_total_anterior";
                    sql_salidas += " from salidas_almacen_detalle sad,salidas_almacen sa";
                    sql_salidas += " where sad.cod_salida_almacen=sa.cod_salida_almacen";
                    sql_salidas += " and sa.cod_estado_salida_almacen=1 and sa.estado_sistema=1";
                    sql_salidas += " and sad.cod_material=" + codMaterialKardex + "";
                    sql_salidas += " and sa.cod_almacen=" + codAlmacen + "";
                    sql_salidas += " and sa.fecha_salida_almacen<='" + fechaFinal + " 23:59:59'  ";
                    System.out.println("sql_salidas:" + sql_salidas);
                    st_salidas = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    rs_salidas = st_salidas.executeQuery(sql_salidas);
                    salidas_total_anterior = 0;
                    while (rs_salidas.next()) {

                        salidas_total_anterior = rs_salidas.getInt("salidas_total_anterior");

                    }
     

                    cantidad_restante_anterior = (ingresos_total_anterior) - (salidas_total_anterior);
                    if (cantidad_restante_anterior > 0.0001) {
                        cantidad_restante_anterior_kardexmovimiento_global = (cantidad_restante_anterior);
                    } else {
                        cantidad_restante_anterior_kardexmovimiento_global = '0';
                    }

  %>
                <td height="20px" align="center" style="border-bottom:1px solid #cccccc"><%=abrevUM%></td>
                <td height="20px" style="border-bottom:1px solid #cccccc;padding:5px"><%=cantidad_restante_anterior%></td>
                </tr>
              
                <%

                }

                %>
            </table>
            <td class='border' align='right'  style='border : solid #D8D8D8 1px;padding-left:5PX;' >
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
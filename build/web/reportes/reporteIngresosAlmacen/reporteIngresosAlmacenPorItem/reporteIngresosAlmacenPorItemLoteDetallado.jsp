----asdadasdasdasdas
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
public String detalleSalidasAlmacen(String codMaterial,String codIngresoAlmacen){
    String detalle = "";
    try{
        con = Util.openConnection(con);
        String consulta = " select s.COD_LOTE_PRODUCCION,c.nombre_prod_semiterminado" +
                 " from SALIDAS_ALMACEN s" +
                 " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN" +
                 " inner join COMPONENTES_PROD c on c.COD_COMPPROD = s.COD_PROD" +
                 " where s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1" +
                 " and sadi.COD_MATERIAL = '"+codMaterial+"' and sadi.COD_INGRESO_ALMACEN = '"+codIngresoAlmacen+"' ";
        System.out.println("consulta " + consulta);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        detalle += " <table class='outputText0'> ";
        while(rs.next()){
            detalle +="<tr><td>"+rs.getString("cod_lote_produccion")+"</td><td>"+rs.getString("nombre_prod_semiterminado")+"</td></tr>";
        }
        detalle +="</table>";
        
           }catch(Exception e){
        e.printStackTrace();
    }
    return detalle;
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

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

            con = Util.openConnection(con);



            /*String fechaInicial=request.getParameter("fecha1")==null?"01/01/2011":request.getParameter("fecha1");
            String fechaInicial1 = fechaInicial;
            String arrayfecha1[]=fechaInicial.split("/");
            fechaInicial=arrayfecha1[2] +"/"+ arrayfecha1[1]+"/"+arrayfecha1[0];

            String fechaFinal=request.getParameter("fecha2")==null?"01/01/2011":request.getParameter("fecha2");
            String fechaFinal1 = fechaFinal;
            String arrayfecha2[]=fechaFinal.split("/");
            fechaFinal=arrayfecha2[2] +"/"+ arrayfecha2[1]+"/"+arrayfecha2[0];*/
             System.out.println("fec11:" + request.getParameter("fechaIni"));
            System.out.println("fec12:" + request.getParameter("fechaFin"));
            String fechaInicial = "";
            if (request.getParameter("fechaIni").equals("0")) {
                System.out.println("1:");
                fechaInicial = "";
            } else {
                System.out.println("12:");
                fechaInicial = request.getParameter("fechaIni");
            }
            String fechaFinal = "";
            if (request.getParameter("fechaFin").equals("0")) {
                System.out.println("13:");
                fechaFinal = "";
            } else {
                System.out.println("14:");
                fechaFinal = request.getParameter("fechaFin");
            }
            System.out.println("fec11:" + fechaInicial);
            System.out.println("fec12:" + fechaFinal);
            String fechaInicial1 = fechaInicial;
            if (!fechaInicial.equals("")) {
                String arrayfecha1[] = fechaInicial.split("/");
                fechaInicial = arrayfecha1[2] + "/" + arrayfecha1[1] + "/" + arrayfecha1[0];
            }

            String fechaFinal1 = fechaFinal;
            if (!fechaFinal.equals("")) {
                String arrayfecha2[] = fechaFinal.split("/");
                fechaFinal = arrayfecha2[2] + "/" + arrayfecha2[1] + "/" + arrayfecha2[0];
            }

            String codMaterial=request.getParameter("codMaterial")==null?"0":request.getParameter("codMaterial");
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
                    <td>
                        <img src="../../../img/cofar.png" alt="logo cofar" align="left" />
                    </td><td>
                        <table border="0" class="outputText1" >
                            <tbody>
                                <tr>
                                    <td><b>Filial</b></td>
                                    <td><%=nombreFilial%></td>
                                </tr>
                                <tr>
                                    <td><b>Almacen</b></td>
                                    <td><%=nombreAlmacen%></td>
                                </tr>
                                <tr>
                                    <td><b>Capitulo</b></td>
                                    <td><%=nombreCapitulo%></td>
                                </tr>
                                <tr>
                                    <td><b>Grupo</b></td>
                                    <td><%=nombreGrupo%></td>
                                </tr>
                                <tr>
                                    <td><b>Fecha Inicial</b></td>
                                    <td><%=fechaInicial1%> &nbsp; <b>Fecha Final</b><%=fechaFinal1%></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>
            <div align="center">
                <b><%=nombreMaterialP.toUpperCase()%></b>
            </div>
            <%--BR>
            <BR--%>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Nro</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" ><b>Fecha Ingreso</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Tipo</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Proveedor</b></td>
                    <td  style="border : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Eti.</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Cantidad</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Unid.</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Fecha Venc.</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"><b>Lote</b></td>
                    <td  class="border" style="border : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center"></td>
                    



                </tr>

                <%

                String consulta = "SELECT ia.COD_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN,ia.FECHA_INGRESO_ALMACEN,tia.COD_TIPO_INGRESO_ALMACEN,tia.NOMBRE_TIPO_INGRESO_ALMACEN,p.NOMBRE_PROVEEDOR,"+
                                  " iade.ETIQUETA,iade.CANTIDAD_PARCIAL,u.ABREVIATURA,iade.FECHA_VENCIMIENTO,iade.LOTE_MATERIAL_PROVEEDOR"+
                                  " FROM   INGRESOS_ALMACEN ia LEFT OUTER JOIN INGRESOS_ALMACEN_DETALLE iad ON ia.COD_INGRESO_ALMACEN =iad.COD_INGRESO_ALMACEN "+
                                  " LEFT OUTER JOIN PROVEEDORES p  ON ia.COD_PROVEEDOR=p.COD_PROVEEDOR LEFT OUTER JOIN "+
                                  " TIPOS_INGRESO_ALMACEN tia ON tia.COD_TIPO_INGRESO_ALMACEN=ia.COD_TIPO_INGRESO_ALMACEN LEFT OUTER JOIN "+
                                  " UNIDADES_MEDIDA u  ON iad.COD_UNIDAD_MEDIDA=u.COD_UNIDAD_MEDIDA LEFT OUTER JOIN "+
                                  " INGRESOS_ALMACEN_DETALLE_ESTADO iade ON iad.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN AND iad.COD_MATERIAL=iade.COD_MATERIAL"+
                                  " inner join ALMACENES a on a.COD_ALMACEN=ia.COD_ALMACEN"+
                                  " inner join filiales f on f.COD_FILIAL=a.COD_FILIAL"+
                                  " inner join MATERIALES m on m.COD_MATERIAL=iad.COD_MATERIAL"+
                                  " inner join grupos g on g.COD_GRUPO=m.COD_GRUPO"+
                                 " inner join CAPITULOS c on c.COD_CAPITULO=g.COD_CAPITULO"+
                                 " where ia.COD_ESTADO_INGRESO_ALMACEN<>2 and 0=0 ";
                if (!codMaterial.equals("0")) {
                    consulta = consulta + " and m.cod_material = '" + codMaterial + "' ";
                }
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
                consulta = consulta + " order by ia.FECHA_INGRESO_ALMACEN desc,iade.ETIQUETA asc ";



                System.out.println("consulta 1 " + consulta);
                SimpleDateFormat sdfMMyyyy =new SimpleDateFormat("MM/yyyy");
                con = Util.openConnection(con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta);
                costoTotal = 0;
                while (res.next()) {
                    int codTipoIngAlmacen=res.getInt(4);
                    String cant_devuelta="";
                    String fechaVenc="";
                    String loteProv="";
                    String nomProv="";
                    String etiqueta="";
                    if( codTipoIngAlmacen==6 ){
                        String sql_dev= " select isnull(s.CANTIDAD_DEVUELTA,0)+isnull(s.CANTIDAD_FALLADOS,0)+isnull(s.CANTIDAD_FALLADOS_PROVEEDOR,0), ie.FECHA_VENCIMIENTO,ie.LOTE_MATERIAL_PROVEEDOR,(select p.NOMBRE_PROVEEDOR from PROVEEDORES p, INGRESOS_ALMACEN ia";
                        sql_dev += " where p.COD_PROVEEDOR=ia.COD_PROVEEDOR and ia.COD_INGRESO_ALMACEN=ie.COD_INGRESO_ALMACEN)as nom_proveedor,ie.ETIQUETA";
                        sql_dev += " from DEVOLUCIONES_DETALLE_ETIQUETAS s,INGRESOS_ALMACEN i,INGRESOS_ALMACEN_DETALLE_ESTADO ie";
                        sql_dev += " where i.COD_INGRESO_ALMACEN="+res.getString("COD_INGRESO_ALMACEN")+" and s.COD_MATERIAL= '" + codMaterial + "'  ";
                        sql_dev += " and s.COD_DEVOLUCION=i.COD_DEVOLUCION and ie.COD_INGRESO_ALMACEN=s.COD_INGRESO_ALMACEN";
                        sql_dev += " and ie.ETIQUETA=s.ETIQUETA and s.COD_MATERIAL=ie.COD_MATERIAL";
                        System.out.println("consulta sql_dev: " + sql_dev);

                        Statement st_dev = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        ResultSet res_dev = st_dev.executeQuery(sql_dev);
                        
                        while(res_dev.next()){
                             cant_devuelta +="<p>"+ formato.format(res_dev.getDouble(1))+"</p>";
                             if(res_dev.getTimestamp(2)==null){
                                  fechaVenc="";
                             }else{
                                  fechaVenc +="<p>"+ format1.format(res_dev.getTimestamp(2))+"</p>";
                             }
                            
                             loteProv +="<p>"+ res_dev.getString(3)+"</p>";
                             nomProv=res_dev.getString(4);
                             etiqueta +="<p>"+ res_dev.getString(5)+"</p>";
                        }

                    }
                   
                    out.print("<tr>");
                    out.print("<td HEIGHT='30PX'  align='right'  style='border : solid #D8D8D8 1px;padding-right:5PX;' >" + (res.getString("NRO_INGRESO_ALMACEN") == null ? "" : res.getString("NRO_INGRESO_ALMACEN")) + "</td>");
                    out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" +(res.getTimestamp("FECHA_INGRESO_ALMACEN")==null?"": format.format(res.getTimestamp("FECHA_INGRESO_ALMACEN"))) + "</td>");
                    out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + (res.getString("NOMBRE_TIPO_INGRESO_ALMACEN") == null ? "" : res.getString("NOMBRE_TIPO_INGRESO_ALMACEN")) + "</td>");
                    if( codTipoIngAlmacen==6 ){
                        out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + (nomProv == null ? "" : nomProv) + "</td>");
                        out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + (etiqueta == null ? "" : etiqueta) + "</td>");
                        out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (cant_devuelta == null ? "" : cant_devuelta) + "</td>");
                        out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (res.getString("ABREVIATURA") == null ? "" : res.getString("ABREVIATURA")) + "</td>");
                        out.print("<td align='center' style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" +(fechaVenc==null?"":fechaVenc) + "</td>");
                        out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (loteProv == null ? "" : loteProv) + "</td>");

                    }else{
                        out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + (res.getString("NOMBRE_PROVEEDOR") == null ? "" : res.getString("NOMBRE_PROVEEDOR")) + "</td>");
                        out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" + (res.getString("ETIQUETA") == null ? "" : res.getString("ETIQUETA")) + "</td>");
                        out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (res.getDouble("CANTIDAD_PARCIAL")>0?formato.format(res.getDouble("CANTIDAD_PARCIAL")):"") + "</td>");
                        out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (res.getString("ABREVIATURA") == null ? "" : res.getString("ABREVIATURA")) + "</td>");
                        out.print("<td  style='border : solid #D8D8D8 1px;padding-left:5PX;'    >" +(res.getTimestamp("FECHA_VENCIMIENTO")==null?"":sdfMMyyyy.format(res.getTimestamp("FECHA_VENCIMIENTO"))) + "</td>");
                        out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (res.getString("LOTE_MATERIAL_PROVEEDOR") == null ? "" : res.getString("LOTE_MATERIAL_PROVEEDOR")) + "</td>");
                        

                    }
                    out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'  >" + (this.detalleSalidasAlmacen(codMaterial, res.getString("COD_INGRESO_ALMACEN"))) + "</td>");
                    
                    out.print("</tr>");
                    
                }
                //out.print("<tr>");
                //out.print("<td HEIGHT='30PX' ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-left:5PX;' colspan = '5'  >TOTAL&nbsp;</td>");

              //out.print("<td ALIGN='RIGHT' style='border : solid #D8D8D8 1px;padding-right:5PX;'  >" + (formato.format(costoTotal)) + "</td>");
                //out.print("<td style='border : solid #D8D8D8 1px;padding-left:5PX;'></td>");
                //out.print("</tr>");





                %>
            </table>
            <td  align='right'  style='border : solid #D8D8D8 1px;padding-left:5PX;' >
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
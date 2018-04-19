

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
            <h4 align="center">Reporte de Salidas y Devoluciones por Nro. de Lote</h4>
            <%!    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }
            %>


            <%
        try {

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
            System.out.println("hola");
         


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
                                    <td><b>Producto</b></td>
                                    <td><%=nombreProductoP%></td>
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

            <BR>
            <BR>

            <table width="90%" align="center" class="outputText0" style="border : solid #000000 1px;" cellpadding="0" cellspacing="0" >


                <tr class="">
                    <td HEIGHT='25PX' class="border" style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" bgcolor="#f2f2f2"  align="center" colspan="9" ><b>&nbsp;</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#CCCCCC"  align="center" colspan="3"><b>SALIDAS</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center" colspan="3"><b>DEVOLUCIONES</b></td>
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center" colspan="2"><b>TOTALES</b></td>

                </tr>

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
                    <td  style="border-bottom : solid #D8D8D8 1px;border-left : solid #D8D8D8 1px" class="border"  bgcolor="#f2f2f2"  align="center"><b>Costo Neto</b></td>
                </tr>


                <%
                String consulta="";
                System.out.println("hola");
                ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
                int codRegistro=0;
                System.out.println("hola");
                consulta="delete from reporte_salidas_devoluciones where cod_personal='"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"'";
                System.out.println("consulta "+consulta);
                PreparedStatement pst=con.prepareStatement(consulta);
                if(pst.executeUpdate()>0)System.out.println("se inserto en la tabla auxiliar");
                consulta="select sa.nro_salida_almacen,sa.cod_salida_almacen,sa.cod_personal,sa.cod_tipo_salida_almacen,"+
                         " from salidas_almacen sa  where sa.estado_sistema=1 and sa.cod_estado_salida_almacen=1";
                if (!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01") ) {
                         consulta+=" and sa.fecha_salida_almacen>='"+fechaInicial+" 00:00:00' and sa.fecha_salida_almacen<='"+fechaFinal+" 23:59:59' ";
                }
                if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
                         consulta+=" and sa.cod_almacen='"+codAlmacen+"'";
                }
                if (!codProductoP.equals("") && !codProductoP.equals("0")) {
                         consulta+=" and sa.cod_prod='"+codProductoP+"'";
                }
                
                         consulta+=" and sa.cod_lote_produccion like'%"+numLoteP+"%' order by  sa.cod_gestion desc,sa.nro_salida_almacen desc";
                
                System.out.println("consulta "+consulta);
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta);
                while(res.next())
                {

                    codRegistro++;
                    consulta="insert into reporte_salidas_devoluciones (cod_personal,cod_registro,cod_material,cod_salida_almacen,cantidad,costo_unitario,costo_total,cantidad_devolucion,costo_unitario_devolucion,costo_total_devolucion,cod_orden_compra)'"+
                            "(select  '"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"','"+codRegistro+"', sad.cod_material,sad.cod_salida_almacen,"+
                            "isnull(sad.cantidad_salida_almacen,0)as cantidad_salida_almacen,"+
                            "(select top 1 isnull(sadi.costo_salida_actualizado,0) from salidas_almacen_detalle_ingreso sadi where sad.cod_material=sadi.cod_material and sad.cod_salida_almacen=sadi.cod_salida_almacen)as costo_salida,"+
                            "(select isnull(sum(sadi.costo_salida_actualizado*sadi.cantidad),0) from salidas_almacen_detalle_ingreso sadi where sad.cod_material=sadi.cod_material and sad.cod_salida_almacen=sadi.cod_salida_almacen)as total_salida,0,0,0,"+
                            "(select top 1 isnull(ia.COD_ORDEN_COMPRA,0) from salidas_almacen_detalle_ingreso sadi,INGRESOS_ALMACEN ia,INGRESOS_ALMACEN_DETALLE_ESTADO iade"+
                            "where sad.cod_material=sadi.cod_material and sad.cod_salida_almacen=sadi.cod_salida_almacen and sadi.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN"+
                            "and sadi.COD_MATERIAL=iade.COD_MATERIAL and sadi.ETIQUETA=iade.ETIQUETA and iade.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN order by ia.cod_orden_compra desc)as cod_oc"+
                            "from salidas_almacen_detalle sad"+
                            "where sad.cod_salida_almacen='"+res.getString("cod_salida_almacen")+"'";
                    System.out.println("consulta "+consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se ejecuto");
                }

                consulta="select d.cod_devolucion,sa.cod_salida_almacen,"+
          " ia.fecha_ingreso_almacen,ia.cod_ingreso_almacen,"+
          " iad.cod_material,"+
          " isnull(iad.CANT_TOTAL_INGRESO_FISICO,0)as CANT_TOTAL_INGRESO_FISICO,"+
          " isnull(iad.COSTO_UNITARIO_ACTUALIZADO,0)as COSTO_UNITARIO_ACTUALIZADO,"+
          " isnull(iad.COSTO_UNITARIO_ACTUALIZADO*iad.CANT_TOTAL_INGRESO_FISICO,0)as total_salida,"+
          " (select top 1 isnull(ia1.COD_ORDEN_COMPRA,0) from salidas_almacen_detalle_ingreso sadi,INGRESOS_ALMACEN ia1,INGRESOS_ALMACEN_DETALLE_ESTADO iade"+
          " where iad.cod_material=sadi.cod_material and sa.cod_salida_almacen=sadi.cod_salida_almacen and sadi.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN"+
          " and sadi.COD_MATERIAL=iade.COD_MATERIAL and sadi.ETIQUETA=iade.ETIQUETA and iade.COD_INGRESO_ALMACEN=ia1.COD_INGRESO_ALMACEN order by ia1.cod_orden_compra desc)as cod_oc"+
          " from devoluciones d,salidas_almacen sa,ingresos_almacen ia,ingresos_almacen_detalle iad  where d.estado_sistema=1 and d.cod_salida_almacen=sa.cod_salida_almacen and d.cod_devolucion=ia.cod_devolucion and ia.cod_almacen=d.cod_almacen ";
          if (!fechaInicial.equals("2011/01/01") && !fechaFinal.equals("2011/01/01") ) {
              consulta+=" and ia.fecha_ingreso_almacen>='"+fechaInicial+" 00:00:00' and ia.fecha_ingreso_almacen<='"+fechaFinal+" 23:59:59'";
              }
          consulta+=" and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and sa.cod_lote_produccion like '%"+numLoteP+"%'";
          if (!codAlmacen.equals("") && !codAlmacen.equals("0")) {
              consulta+=" and sa.cod_almacen='"+codAlmacen+"' and d.cod_almacen='"+codAlmacen+"'";
          }
           if (!codProductoP.equals("") && !codProductoP.equals("0")) {
               consulta+= " and sa.cod_prod='"+codProductoP+"'";
               }
          consulta+=" and ia.cod_estado_ingreso_almacen=1 and d.cod_estado_devolucion=1 order by  ia.fecha_ingreso_almacen desc'";
          System.out.println("consulta "+consulta);
          res=st.executeQuery(consulta);
          Statement stdetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
          ResultSet resdetalle=null;
          SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
          String fecha="";
          while(res.next())
          {
              fecha=sdf.format(res.getTimestamp("fecha_ingreso_almacen"));
              consulta=" select * from reporte_salidas_devoluciones where cod_personal='"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"' and cod_salida_almacen='"+res.getString("cod_salida_almacen")+"'"+
                       " and cod_material='"+res.getString("cod_material")+"'";
              System.out.println("consulta "+consulta);
              resdetalle=stdetalle.executeQuery(consulta);
              if(resdetalle.next())
              {
                 consulta="update reporte_salidas_devoluciones set cantidad_devolucion=cantidad_devolucion+'"+res.getDouble("CANT_TOTAL_INGRESO_FISICO")+"',"+
                          " costo_unitario_devolucion='"+res.getDouble("COSTO_UNITARIO_ACTUALIZADO")+"',"+
                          " costo_total_devolucion=costo_total_devolucion+'"+res.getDouble("total_salida")+"',"+
                          " fecha_devolucion='"+fecha+"'"+
                          " where cod_personal='"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"' and cod_material='"+res.getString("cod_material")+"' and cod_salida_almacen='"+res.getString("cod_salida_almacen")+"'";
                        System.out.println("consulta "+consulta);
                          pst=con.prepareStatement(consulta);
                          if(pst.executeUpdate()>0)System.out.println("se ejecuto 1");
              }
              else
              {
                  codRegistro++;
                  consulta=" insert into reporte_salidas_devoluciones (cod_personal,cod_registro,cod_material,cod_salida_almacen,cantidad,costo_unitario,costo_total,cantidad_devolucion,costo_unitario_devolucion,costo_total_devolucion,fecha_devolucion,cod_orden_compra)"+
                              " values('"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"','"+codRegistro+"', '"+ res.getString("cod_material")+"', '"+res.getString("cod_salida_almacen")+"',0,0,0,"+
                              " '"+res.getDouble("CANT_TOTAL_INGRESO_FISICO")+"',"+
                              " '"+res.getDouble("COSTO_UNITARIO_ACTUALIZADO")+"',"+
                              " '"+res.getDouble("total_salida")+"','"+fecha+"',"+
                              " '"+res.getString("cod_oc")+"')";
                        System.out.println("consulta "+consulta);
                            pst=con.prepareStatement(consulta);
                            if(pst.executeUpdate()>0)System.out.println("se ejecuto 2");
              }
          }


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
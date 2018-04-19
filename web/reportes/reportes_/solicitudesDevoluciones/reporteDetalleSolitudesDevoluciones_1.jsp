package reportes.solicitudesDevoluciones;

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

<%@ page import = "java.util.Locale"%>
<%@ page import = "com.cofar.bean.*" %>


<%@ page language="java" import="java.util.*,com.cofar.util.CofarConnection" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%--meta http-equiv="Content-Type" content="text/html; charset=UTF-8"--%>
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
        
        <script type="text/javascript" >
                function openPopup(f,url){
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                    f.action=url;
                    f.submit();
                }
                function openPopup1(url){
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                    showModalDialog(url, "dialogwidth: 800", "dialogwidth: 800px");
                    //window.openDialog(url, "dlg", "", "dlg", 6.98);
                    //(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');

                }
        </script>
    </head>
    <body>
        <%
        Connection con=null;
        String codSolicitud=request.getParameter("cod");
        System.out.println("cod Solicitud "+codSolicitud);
        try{
             con=Util.openConnection(con);
             Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             ResultSet res;

        %>
        <h3 align="center">Solicitud Devolucion</h3>
        <br>
        <form target="_blank" id="form" name="form">
            <%
            String consultaCabe="select s.NRO_SALIDA_ALMACEN,cp.nombre_prod_semiterminado,a.NOMBRE_ALMACEN,s.COD_LOTE_PRODUCCION,  "+
            "(p.AP_PATERNO_PERSONAL + ' ' + p.AP_MATERNO_PERSONAL + ' ' +p.NOMBRE_PILA) as nombrePer,sd.COD_SOLICITUD_DEVOLUCION, "+
            "esd.NOMBRE_ESTADO,sd.COD_SALIDA_ALMACEN,sd.FECHA_SOLICITUD,ISNULL( pp.NOMBRE_PRODUCTO_PRESENTACION,'') as presentacion,"+
            "sd.OBSERVACION from SOLICITUD_DEVOLUCIONES sd inner join PERSONAL p on sd.COD_PERSONAL = p.COD_PERSONAL"+
            " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN =sd.COD_SALIDA_ALMACEN left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD = s.COD_PROD"+
            " inner join ALMACENES a on a.COD_ALMACEN = s.COD_ALMACEN inner join ESTADOS_SOLICITUD_DEVOLUCION esd on sd.COD_ESTADO_SOLICITUD_DEVOLUCION = esd.COD_ESTADO_SOLICITUD_DEVOLUCION"+
            " left outer join PRESENTACIONES_PRODUCTO pp on s.COD_PRESENTACION=pp.cod_presentacion where sd.COD_SOLICITUD_DEVOLUCION='"+codSolicitud+"'";
            System.out.println("consulta "+consultaCabe);
            res=st.executeQuery(consultaCabe);
            SimpleDateFormat  sdf = new SimpleDateFormat("dd/MM/yyyy");
            if(res.next()){
                    out.print(" <table align='center' width='70%' border='0' style='text-align:left' class='outputText1'> ");
                    out.print(" <tr><td rowspan='5'><img src='../../img/cofar.png' /></td><td><b> Nro Solicitud : </b></td> <td> "+(res.getString("COD_SOLICITUD_DEVOLUCION")==null?"":res.getString("COD_SOLICITUD_DEVOLUCION"))+" </td><td><b>Estado de Solicitud: </b></td> <td> "+(res.getString("NOMBRE_ESTADO")==null?"":res.getString("NOMBRE_ESTADO"))+" </td> </tr>");
                    out.print(" <tr><td><b>Solicitante: </b></td><td> "+(res.getString("nombrePer")==null?"":(res.getString("nombrePer")))+" </td> <td><b>Fecha Solicitud :</b></td> <td>"+(sdf.format(res.getTimestamp("FECHA_SOLICITUD")))+"</td> </tr>");
                     out.print(" <tr><td><b>Nro salida: </b></td><td> "+(res.getString("NRO_SALIDA_ALMACEN")==null?"":(res.getString("NRO_SALIDA_ALMACEN")))+" </td> <td><b>Almacen :</b></td> <td>"+(res.getString("NOMBRE_ALMACEN"))+"</td> </tr>");
                     out.print(" <tr><td><b>Producto: </b></td><td> "+(res.getString("nombre_prod_semiterminado")==null?"":(res.getString("nombre_prod_semiterminado")))+" </td> <td><b>Presentación:</b></td> <td>"+(res.getString("presentacion"))+"</td> </tr>");
                      out.print(" <tr><td><b>Lote: </b></td><td> "+(res.getString("COD_LOTE_PRODUCCION")==null?"":(res.getString("COD_LOTE_PRODUCCION")))+" </td></tr>");

                    out.print(" </table> ");
                
                }
            %>
            <table  align="center" width="90%" class="outputText2" style="border : solid #f2f2f2 1px;" cellpadding="0" cellspacing="0">


                <tr class="tablaFiltroReporte">
                    <td  align="center" class="bordeNegroTd"><b></b></td>
                    <td  align="center" class="bordeNegroTd"><b>Material</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Cant. devuelta buenos</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Cant. devuelta fallados</b></td>
                    <td  align="center" class="bordeNegroTd"><b>Cant. devuelta fallados prooveedor</b></td>
                </tr>
                <%
                System.out.println("llegoa ");
                String conDetalle=" select m.CODIGO_ANTIGUO,m.NOMBRE_MATERIAL,sdd.CANTIDAD_DEVUELTA,"+
                        "sdd.CANTIDAD_DEVUELTA_FALLADOS,sdd.CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR "+
                       "from SOLICITUD_DEVOLUCIONES_DETALLE sdd INNER JOIN MATERIALES m"+
                       " on sdd.COD_MATERIAL=m.COD_MATERIAL where sdd.COD_SOLICITUD_DEVOLUCION='"+codSolicitud+"'";
                System.out.println("consulta detalle "+conDetalle);
                res=st.executeQuery(conDetalle);
                while(res.next())
                 {

                    %>
                    <tr class="tablaFiltroReporte">
                    <td  align="center" class="bordeNegroTd"><%=res.getString("CODIGO_ANTIGUO")%></td>
                    <td  align="center" class="bordeNegroTd"><%=res.getString("NOMBRE_MATERIAL")%></td>
                    <td  align="center" class="bordeNegroTd"><%=res.getString("CANTIDAD_DEVUELTA")%></td>
                    <td  align="center" class="bordeNegroTd"><%=res.getString("CANTIDAD_DEVUELTA_FALLADOS")%></td>
                    <td  align="center" class="bordeNegroTd"><%=res.getString("CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR")%></td>
                    
                    </tr>
                    <%
                }
                %>

               
            </table>
           
            <%
            res.close();
            st.close();
            }
        catch(SQLException ex)
         {
            ex.printStackTrace();
        }
        catch(Exception e)
         {
            e.printStackTrace();
        }
            %>
            
            
        </form>
    </body>
</html>
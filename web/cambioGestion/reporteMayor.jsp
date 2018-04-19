<%@ page import="com.cofar.web.ManagedAccesoSistema" %>
<%@ page import="com.cofar.util.*" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.text.DecimalFormat"%> 
<%@ page import = "java.text.NumberFormat"%> 
<%@ page import = "java.util.Locale"%> 
<%@ page import = "org.joda.time.DateTime"%> 
<%@ page import = "java.util.*"%> 
<%@ page import = "java.text.*"%> 
<%@ page import = "java.text.DecimalFormat"%> 
<%@ page import = "java.text.NumberFormat"%> 
<%@ page import = "java.util.Locale"%> 
<%! Connection con=null; %>
<link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
<html>
    <body>
        <form method="post" action="">        
            <%
            String codCuenta = request.getParameter("codCuenta");
            String codCentro = request.getParameter("codCentro");
            String nombreCuenta = request.getParameter("nombreCuenta");
            String nombreCentro = request.getParameter("nombreCentro");
            String fechaInicio = request.getParameter("fecha_inicio");
            String fechaFinal = request.getParameter("fecha_final");
            String codMoneda = request.getParameter("codMoneda");
            String codPlanCuenta = "";
            String[] vectorFechaInicio = fechaInicio.split("/");
            String[] vectorFechaFinal = fechaFinal.split("/");
            String fechaInicio1=vectorFechaInicio[1]+"/"+vectorFechaInicio[0]+"/"+vectorFechaInicio[2];
            String fechaFinal1=vectorFechaFinal[1]+"/"+vectorFechaFinal[0]+"/"+vectorFechaFinal[2]+" 23:59:59";
            String fechaSalidaAlmacen;
            //Statement st;
            
            String fecha="";
            String numero="";
            String tipoComprobante="";
            String nroCheque="";
            String glosa="";
            double debe=0;
            double haber=0;
            double totaldebe=0;
            double totalhaber=0;
            double saldo=0;
            double totalgeneraldebe=0;
            double totalgeneralhaber=0;
            
            double debes=0;
            double habers=0;
            double totaldebes=0;
            double totalhabers=0;
            double saldos=0;
            double totalgeneraldebes=0;
            double totalgeneralhabers=0;
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat form1 = (DecimalFormat)nf;
            form1.applyPattern("#,###.00");
            //**********
            fechaInicio=vectorFechaInicio[0]+"/"+vectorFechaInicio[1]+"/"+vectorFechaInicio[2];
            fechaFinal=vectorFechaFinal[0]+"/"+vectorFechaFinal[1]+"/"+vectorFechaFinal[2];
            
            
            try{
                ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                String codGestionActiva = usuario.getGestionTrabajoActiva().getCodGestion();
                System.out.println("codGestionActiva.................."+codGestionActiva);

                con=Util.openConnection(con);
                String sql0=" select COD_CUENTA from PLAN_DE_CUENTAS where cod_plan_cuenta="+codCuenta;
                System.out.println("sql:"+sql0);
                //setCon(Util.openConnection(getCon()));
                Statement st0=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs0=st0.executeQuery(sql0);
                while (rs0.next()){
                    codPlanCuenta=rs0.getString(1);
                }
                
                sql0="select isnull(sum(cd.debe),0),ISNULL(sum(cd.haber),0),isnull(sum(cd.debesus),0),ISNULL(sum(cd.habersus),0)";
                sql0+=" from comprobante c,COMPROBANTE_DETALLE cd";
                sql0+=" where c.estado_sistema=1 and c.COD_ESTADO_COMPROBANTE=1 and FECHA_COMPROBANTE<'"+fechaInicio1+"'";
                sql0+=" and c.cod_gestion="+codGestionActiva;
                sql0+=" and c.COD_COMPROBANTE=cd.COD_COMPROBANTE and cd.COD_PLAN_CUENTA="+codCuenta;
                if(!codCentro.equals("0")){
                    sql0+=" and COD_CENTRO_COSTOS="+codCentro;
                                    }
                System.out.println("sql:"+sql0);
                //setCon(Util.openConnection(getCon()));
                st0=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs0=st0.executeQuery(sql0);
                while (rs0.next()){
                    saldo=saldo+Math.round(rs0.getDouble(1)*100)/100.0;
                    saldo=saldo-Math.round(rs0.getDouble(2)*100)/100.0;
                    saldos=saldos+Math.round(rs0.getDouble(3)*100)/100.0;
                    saldos=saldos-Math.round(rs0.getDouble(4)*100)/100.0;
                }
                con.close();
            } catch(Exception e) {
            }
            
            %>                     
            
            <div align="center">
                
                <table>   
                    <tr>   
                        <td><img src="../../img/logo_cofar.png"></td>
                        <td>                
                            <table border="0" class="outputText2">
                                <tr>
                                    <td align="center" class="tituloCampo"><b>Reporte de Libro Mayor</td>
                                </tr>
                                <tr>
                                    <td ><b>Fecha Inicio::</b><%=fechaInicio%></td>
                                </tr>
                                <tr>
                                    <td ><b>Fecha Final::</b><%=fechaFinal%></td>
                                </tr>                                                               
                                
                                <tr>
                                    <td ><b>CUENTA::</b><%=codPlanCuenta%></td>
                                </tr>                                                               
                                <tr>
                                    <td ><b>NOMBRE::</b><%=nombreCuenta%>  <%=nombreCentro%></td>
                                </tr>                                                               
                            </table>    
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table class="tablaFiltroReporte"  cellpadding="0" cellspacing="0">                            
                                <tr>
                                    <td colspan="8" align="center" class="outputText2"><b>SALDO ANTERIOR::</b><%=saldo%></td>
                                </tr>                                                               
                                <tr  class="tituloCampo"> 
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>FECHA</b></td>
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>TIPO</b></td>
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>Nº</b></td>
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>DESCRIPCION</b></td>    
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>CHEQUE</b></td>
                                    <% if(codMoneda.equals("0")){%>
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>DEBE BS</b></td>    
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>HABER BS</b></td>                                                                                        
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>SALDO BS</b></td>
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>DEBE $US</b></td>    
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>HABER $US</b></td>                                                                                        
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>SALDO $US</b></td>
                                    <% }%>
                                    <% if(codMoneda.equals("1")){%>
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>DEBE BS</b></td>    
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>HABER BS</b></td>                                                                                        
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>SALDO BS</b></td>
                                    <% }%>
                                    <% if(codMoneda.equals("2")){%>
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>DEBE $US</b></td>    
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>HABER $US</b></td>                                                                                        
                                    <td class="bordeNegroTd" style="border:1px solid #000000"><b>SALDO $US</b></td>
                                    <% }%>
                                    
                                </tr> 
                                <%  
                                
                                try{
                                    con=Util.openConnection(con);
                                    String sql=" select FECHA_COMPROBANTE,NRO_COMPROBANTE,cd.GLOSA,";
                                    sql+=" (select nombre_tipo_comprobante from tipos_comprobantes t where t.cod_tipo_comprobante=c.cod_tipo_comprobante) as nombre_tipo_comprobante,";
                                    sql+=" cd.debe, cd.haber,C.NRO_CHEQUE,cd.debesus, cd.habersus";
                                    sql+=" from comprobante c,COMPROBANTE_DETALLE cd";
                                    sql+=" where c.estado_sistema=1 and c.COD_ESTADO_COMPROBANTE=1 and FECHA_COMPROBANTE>='"+fechaInicio1+"' and FECHA_COMPROBANTE<='"+fechaFinal1+"' ";
                                    sql+=" and c.COD_COMPROBANTE=cd.COD_COMPROBANTE and cd.COD_PLAN_CUENTA="+codCuenta+" and cd.COD_CENTRO_COSTOS="+codCentro+" order by FECHA_COMPROBANTE";
                                    System.out.println("sql:"+sql);
                                    //setCon(Util.openConnection(getCon()));
                                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                    ResultSet rs=st.executeQuery(sql);
                                    while (rs.next()){
                                        fecha=rs.getString(1);
                                        numero=rs.getString(2);
                                        glosa=rs.getString(3);
                                        tipoComprobante=rs.getString(4);
                                        debe=Math.round(rs.getDouble(5)*100)/100.0;
                                        haber=Math.round(rs.getDouble(6)*100)/100.0;
                                        nroCheque=rs.getString(7);
                                        debes=Math.round(rs.getDouble(8)*100)/100.0;
                                        habers=Math.round(rs.getDouble(9)*100)/100.0;
                                        
                                        totaldebe=totaldebe+debe;
                                        totalhaber=totalhaber+haber;
                                        totaldebes=totaldebes+debes;
                                        totalhabers=totalhabers+habers;
                                        
                                        String[] vectorFecha= fecha.split(" ");
                                        vectorFecha = vectorFecha[0].split("-");
                                        fecha=vectorFecha[2]+"/"+vectorFecha[1]+"/"+vectorFecha[0];
                                        saldo=saldo+debe-haber;
                                        totalgeneraldebe=totalgeneraldebe+debe;
                                        totalgeneralhaber=totalgeneralhaber+haber;
                                        saldos=saldos+debes-habers;
                                        totalgeneraldebes=totalgeneraldebes+debes;
                                        totalgeneralhabers=totalgeneralhabers+habers;
                                %>                           
                                <tr>
                                    <td class="outputText4" style="border-right:1px solid #000000;border-left:1px solid #000000">&nbsp;<%=fecha%></td>
                                    <td class="outputText4" style="border-right:1px solid #000000">&nbsp;<%=tipoComprobante%></td>
                                    <td class="outputText4" style="border-right:1px solid #000000">&nbsp;<%=numero%></td>
                                    <td class="outputText4" style="border-right:1px solid #000000">&nbsp;<%=glosa%></td>       
                                    <td class="outputText4" style="border-right:1px solid #000000">&nbsp;<%=nroCheque%></td>
                                    <% if(codMoneda.equals("0")){%>
                                        <% if(debe==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(debe)%></td>                    
                                        <%}%>
                                        <% if(haber==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(haber)%></td>                    
                                        <%}%>
                                        <% if(saldo==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(saldo)%></td>                    
                                        <%}%>
                                        <% if(debes==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(debes)%></td>                    
                                        <%}%>
                                        <% if(habers==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(habers)%></td>                    
                                        <%}%>
                                        <% if(saldos==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(saldos)%></td>                    
                                        <%}%>
                                    <% }%>
                                    <% if(codMoneda.equals("1")){%>
                                        <% if(debe==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(debe)%></td>                    
                                        <%}%>
                                        <% if(haber==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(haber)%></td>                    
                                        <%}%>
                                        <% if(saldo==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(saldo)%></td>                    
                                        <%}%>
                                    <% }%>
                                    <% if(codMoneda.equals("2")){%>
                                        <% if(debes==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(debes)%></td>                    
                                        <%}%>
                                        <% if(habers==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(habers)%></td>                    
                                        <%}%>
                                        <% if(saldos==0){%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border-right:1px solid #000000"><%=form1.format(saldos)%></td>                    
                                        <%}%>
                                    <% }%>                                    
                                </tr>
                                <% 
                                    }
                                %>                           
                                <tr>                                                                        
                                    <% if(codMoneda.equals("0")){%>
                                        <td class="outputText4" colspan="5" align="right" style="border:1px solid #000000">SALDO DEUDOR</td>                    
                                        <% if(totaldebe==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(totaldebe)%></td>                    
                                        <%}%>   
                                        <% if(totalhaber==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(totalhaber)%></td>                    
                                        <%}%>   
                                        
                                        <% if(saldo==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(saldo)%></td>                    
                                        <%}%>   
                                        
                                        <% if(totaldebes==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(totaldebes)%></td>                    
                                        <%}%>   
                                        <% if(totalhabers==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(totalhabers)%></td>                    
                                        <%}%>  
                                        <% if(saldos==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(saldos)%></td>                    
                                        <%}%>
                                    <% }%>
                                    <% if(codMoneda.equals("1")){%>
                                        <td class="outputText4" colspan="5" align="right" style="border:1px solid #000000">SALDO DEUDOR BS</td>                    
                                        <% if(totaldebe==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(totaldebe)%></td>                    
                                        <%}%>   
                                        <% if(totalhaber==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(totalhaber)%></td>                    
                                        <%}%>   
                                        <% if(saldo==0){%>
                                        <td class="outputText4" colspan="3" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" colspan="3" align="right" style="border:1px solid #000000"><%=form1.format(saldo)%></td>                    
                                        <%}%>   
                                    <% }%>
                                    <% if(codMoneda.equals("2")){%>
                                        <td class="outputText4" colspan="5" align="right" style="border:1px solid #000000">SALDO DEUDOR $US</td>                    
                                        <% if(totaldebes==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(totaldebes)%></td>                    
                                        <%}%>   
                                        <% if(totalhabers==0){%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(totalhabers)%></td>                    
                                        <%}%>  
                                        <% if(saldos==0){%>
                                        <td class="outputText4" colspan="3" align="right" style="border:1px solid #000000">0.00</td>                    
                                        <%}else{%>
                                        <td class="outputText4" align="right" style="border:1px solid #000000"><%=form1.format(saldos)%></td>                    
                                        <%}%>
                                    <% }%>                                    
                                </tr>
                                <% 
                                
                                con.close();
                                } catch(Exception e) {
                                } 
                                %>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
            
            
        </form>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>
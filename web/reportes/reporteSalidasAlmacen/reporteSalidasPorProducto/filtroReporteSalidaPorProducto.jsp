<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <link rel="STYLESHEET" type="text/css" href="../../../css/chosen.css" />
        <script src="../../../js/general.js"></script>
        <script  type="text/javascript">
            function ajaxAlmacen(f){
                ajax  = creaAjax();

                ajax.open("GET","../../ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_almacen").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                ajax.send(null);


            }
            //cel.getElementsByTagName('input')[0].checked=true;
            function verReporte(f,nombreReporte){

                //capitulos



                //f.codMaterial.value = arrayMateriales;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                f.nombreProductoP.value = f.codProducto.options[f.codProducto.selectedIndex].text
                //f.nombreMaterialP.value = f.nombreMaterial.value;
                f.codProductoP.value = f.codProducto.value;
               

                //alert(f.fecha1.value);
                //alert(f.check.checked);
                if(f.check.checked==false){
                   f.conLote.value='1';
                }else{
                   f.conLote.value='2';
                }
                //alert(f.fechaReporte1.value);
                //alert(f.fechaReporte2.value);
                        if(f.codTipoReporte.value=='2')
                            f.action="reporteSalidasPorProductoDetallado.jsf";
                        if(f.codTipoReporte.value=='1')
                            f.action="reporteSalidasPorProductoResumido.jsf";
                  
                f.submit();

            }

            function seleccionarTodo(){
                //alert('entro');
                var seleccionar_todo=document.getElementById('seleccionar_todo');
                var elements=document.getElementById('dataMateriales');

                if(seleccionar_todo.checked==true){
                    //alert('entro por verdad');
                    var rowsElement=elements.rows;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel=cellsElement[0];
                        if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                            cel.getElementsByTagName('input')[0].checked=true;
                        }
                    }
                }
                else
                {//alert('entro por false');
                    var rowsElement=elements.rows;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel=cellsElement[0];
                        if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                            cel.getElementsByTagName('input')[0].checked=false;
                        }
                    }

                }
                return true;

            }



        </script>



    </head>
    <body>
        <h3 align="center">Reporte de Salidas por Producto</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="75%">
                    <thead>
                        <tr>
                            <td height="35px" colspan="6" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td height="35px" class="">Filial</td>
                            <td class="">::</td>
                            <%
                                Connection con = null;
            String codProgramaProduccionPeriodoReq = "";
            if (request.getParameter("codProgramaProduccionPeriodo") != null) {
                codProgramaProduccionPeriodoReq = request.getParameter("codProgramaProduccionPeriodo");
            }
            ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSession("ManagedAccesoSistema");
             String codFilial="";
            try {
                con = Util.openConnection(con);
                System.out.println("con:::::::::::::" + con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                String consulta="select f.COD_FILIAL from FILIALES f where f.COD_FILIAL in(select a.COD_FILIAL from ALMACENES a where a.COD_ALMACEN='"+user.getAlmacenesGlobal().getCodAlmacen()+"')";
                ResultSet res1=st.executeQuery(consulta);

                while(res1.next())
                {
                    codFilial=res1.getString("COD_FILIAL");

                }
                res1.close();
                String sql = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

                System.out.println("sql filtro:" + sql);
                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td class="">
                                <select style="color:blue" id="codFilial" name="codFilial" onchange="ajaxAlmacen(form1);">
                                    <%

                                out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
                                while (rs.next()) {
                                    if(codFilial.equals(rs.getString("cod_filial")))
                                    {
                                        out.print("<option value='" + rs.getString("cod_filial") + "' selected>" + rs.getString("nombre_filial") + "</option>");
                                    }
                                    else
                                        {
                                    out.print("<option value='" + rs.getString("cod_filial") + "'>" + rs.getString("nombre_filial") + "</option>");
                                    }
                                }%>
                                </select>
                                <!--  <input type="checkbox"  onclick="selecccionarTodo(form1)" name="chk_todoTipo" >Todo-->
                                <%
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
                                %>
                            </td>
                            <td class="">Almacen</td>
                            <td class="">::</td>

                            <td class="">
                                <div id="div_almacen">
                                    <%try
                                    {
                                        Connection con2=null;
                                        con2=Util.openConnection(con2);
                                        Statement st=con2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                        String consulta="select cod_almacen, nombre_almacen from almacenes where cod_estado_registro=1 and cod_filial='"+codFilial+"'";
                                        System.out.println("consulta almacen  "+consulta);
                                        ResultSet res=st.executeQuery(consulta);

                                        out.println("<select style='color:blue' id='codAlmacen' name='codAlmacen' class='inputText' >");

                                        while(res.next())
                                        {
                                            if(user.getAlmacenesGlobal().getCodAlmacen()==res.getInt("cod_almacen"))
                                                out.println("<option value='"+res.getString("cod_almacen")+"' selected >"+res.getString("nombre_almacen")+"</option>");
                                            else
                                                out.println("<option value='"+res.getString("cod_almacen")+"'>"+res.getString("nombre_almacen")+"</option>");
                                        }
                                        out.println("</select>");
                                        res.close();
                                        st.close();
                                        con2.close();
                                    }
                                    catch(SQLException ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                        %>


                                </div>
                            </td>

                        </tr>


                        <tr>


                            <td class="">Producto </td>
                            <td class="">::</td>
                            <td class="">
                                <%

            try {
                con = Util.openConnection(con);
                //System.out.println("con:::::::::::::" + con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                String sql = " select c.COD_COMPPROD,c.nombre_prod_semiterminado from COMPONENTES_PROD c where c.nombre_prod_semiterminado is not null and c.nombre_prod_semiterminado!='' order by c.nombre_prod_semiterminado";

                System.out.println("sql filtro:" + sql);
                ResultSet rs = st.executeQuery(sql);
                                %>

                                <select style="width:350px;color:blue" id="codProducto" name="codProducto"  class="outputText3 chosen" style="width:300px">
                                    <option value="0" >-TODOS-</option>
                                    <%
                                                               while (rs.next()) {
                                                                   out.print("<option value='" + rs.getString("COD_COMPPROD") + "' >" + rs.getString("nombre_prod_semiterminado") + "</option>");
                                                               }%>
                                </select>
                                <%
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
                                %>

                            <td height="35px" class="">Salidas con N° de Lote</td>
                            <td colspan="5" class=""><input type="checkbox" id="check" name="check" ></td>
                        </tr>

                        <tr>
                            <td  height="35px" class="">Fecha Inicio</td>
                            <td class="">::</td>

                            <%
                                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                            %>

                            <td class="">
                                <input type="text"   size="12" style="color:blue"   value="<%=form.format(new Date())%>" id="fecha1" name="fecha1" class="dlCalendar"/>
                            </td>
                            <td  height="35px" class="">Fecha Final</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text"   size="12" style="color:blue"     value="<%=form.format(new Date())%>" id="fecha2" name="fecha2" class="dlCalendar"/>
                            </td>
                        </tr>
                        <tr>


                            <td class="">Tipo de Reporte</td>
                            <td class="">::</td>
                            <td class="">
                                <select style="width:350px;color:blue" id="codTipoReporte" name="codTipoReporte"  >
                                    <option value="2" selected>Detallado</option>
                                    <option value="1">Resumido</option>

                                </select>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Ver Reporte" name="reporte" onclick="verReporte(form1,'reporteSalidasPorProducto.jsp')" />
                            </td>
                        </tr>
                    </tfoot>
                </table>



            </div>
            <br>
            <br>
            <center>
                
                

                <input type="hidden" name="codigosArea" id="codigosArea" />
            </center>
            <!--datos de referencia para el envio de datos via post-->

            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />

            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">
            <input type="hidden" name="conLote">

            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">
            <input type="hidden" name="fechaReporte1">
            <input type="hidden" name="fechaReporte2">
            <input type="hidden" name="nombreMaterialP">
            <input type="hidden" name="codProductoP">
            <input type="hidden" name="nombreProductoP">
            <input type="hidden" name="numLoteP">



        </form>
        <script src="../../../js/chosen.js"></script> 
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>
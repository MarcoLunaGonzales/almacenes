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
                var div_almacen;
                div_almacen=document.getElementById("div_almacen");

                ajax = creaAjax();

                ajax.open("GET","../../ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_almacen.innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }

                ajax.send(null);


            }

            function ajaxGrupo(f){
                //con los capitulos seleccionados

                //var div_producto=document.getElementById("div_producto");
                var arrayCodCapitulo=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }


                var div_grupo;
                div_grupo=document.getElementById("div_grupo");

                ajax=nuevoAjax();

                ajax.open("GET","ajaxGrupo.jsf?codCapitulo="+arrayCodCapitulo,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_grupo.innerHTML=ajax.responseText;
                    }
                }

                ajax.send(null);


            }

            function buscarMaterial(f){
                //con los capitulos seleccionados

                //var div_producto=document.getElementById("div_producto");
                var arrayCodCapitulo=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }

                // grupos
                var arrayCodGrupo=new Array();
                j=0;
                for(var i=0;i<=f.codGrupo.options.length-1;i++)
                {	if(f.codGrupo.options[i].selected)
                    {	arrayCodGrupo[j]=f.codGrupo.options[i].value;
                        j++;
                    }
                }
                var nombreMaterial = document.getElementById("nombreMaterial").value;


                var div_material;
                div_material=document.getElementById("div_material");

                ajax=nuevoAjax();

                ajax.open("GET","ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo+"&nombreMaterial="+nombreMaterial,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_material.innerHTML=ajax.responseText;
                    }
                }

                ajax.send(null);


            }
            //cel.getElementsByTagName('input')[0].checked=true;
            function verReporte(f,nombreReporte){

               
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                f.nombreProductoP.value = f.codProducto.options[f.codProducto.selectedIndex].text
                f.codProductoP.value = f.codProducto.value;
                f.numLoteP.value = f.numLote.value;
                f.action = "reporteSalidasPorNroLote.jsf";
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
        <h3 align="center">Reporte de Salidas y Solicitudes de Devolución por Nro. de Lote</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" id="form1" name="form1">
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
                            <td class="">
                                <select style="color:blue" id="codFilial" name="codFilial" onchange="ajaxAlmacen(form1);">
                            <%
           Connection con=null;
            try
            {
                con = Util.openConnection(con);
                System.out.println("con:::::::::::::" + con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String consulta= " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";
                ResultSet res = st.executeQuery(consulta);
                out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
                while (res.next())
                {
                    out.print("<option value='"+res.getString("cod_filial")+"' >" +res.getString("nombre_filial") + "</option>");
                }
              %>
              </select>
              </td>
                <td class="">Almacen</td>
                <td class="">::</td>

                <td class="">
                    <div id="div_almacen">
                        <select style="color:blue" id="codAlmacen" name="codAlmacen" class="inputText" >
                            <option value="-1">-TODOS-</option>
                        </select>
                    </div>
                </td>

            </tr>
                        <tr>


                            <td class="">Producto </td>
                            <td class="">::</td>
                            <td class="">
                                <select style="width:350px;color:blue" id="codProducto" name="codProducto"  class="outputText3 chosen" >
                                    <option value="0" >-TODOS-</option>
                                <%
                            consulta = " select c.COD_COMPPROD,c.nombre_prod_semiterminado from COMPONENTES_PROD c where c.nombre_prod_semiterminado is not null and c.nombre_prod_semiterminado != '' order by c.nombre_prod_semiterminado          ";
                            res = st.executeQuery(consulta);
                            while (res.next())
                            {
                                out.print("<option value='" + res.getString("COD_COMPPROD") + "' >" + res.getString("nombre_prod_semiterminado") + "</option>");
                            }
                            %>
                                </select>
                                <%
                res.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
                                %>


                            </td>
                            <td  height="35px" class="">Nro de Lote</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text" style="color:blue;text-transform:uppercase" onkeypress="valNum(event);" class="inputText" size="20" id="numLote" name="numLote" />
                            </td>


                        </tr>
                        </tr>
                        <tr>
                            <td height="35px" class="">Filtrar Fecha Salida</td>
                            <td height="35px" class="">::</td>
                            <td class=""><input type="checkbox" id="check" name="check" onclick="click1(this.form)"></td>
                        </tr>
                        <tr style="display: none" id="trFechaSalida">
                            <td height="35px" class="outputText2">Fecha Inicio</td>
                            <td class="outputText2">::</td>
                            <%
                                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                            %>
                            <td class="">
                                <input type="text"  size="12" style="color:fuchsia"   value="<%=form.format(new Date())%>" id="fecha1" name="fecha1" class="inputText">
                                <img id="imagenFecha1" src="../../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fecha1" click_element_id="imagenFecha1">
                                </DLCALENDAR>
                            </td>
                            <td  height="35px" class="outputText2">Fecha Final</td>
                            <td class="outputText2">::</td>
                            <td class="">
                                <input type="text"  size="12" style="color:fuchsia"     value="<%=form.format(new Date())%>" id="fecha2" name="fecha2" class="inputText">
                                <img id="imagenFecha2" src="../../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fecha2" click_element_id="imagenFecha2">
                                </DLCALENDAR>


                            </td>
                        </tr>
                        <tr>
                            <td height="35px" class="">Filtrar Fecha Solicitud Devolucion</td>
                            <td height="35px" class="">::</td>
                            <td class=""><input type="checkbox" id="check2" name="check2" onclick="click2(this.form)"></td>
                        </tr>
                        <tr style="display: none" id="trFechaSolicitud">
                            <td height="35px" class="outputText2">Fecha Inicio Solicitud</td>
                            <td class="outputText2">::</td>
                            <td class="">
                                <input type="text" size="12" style="color:fuchsia" 
                                       value="<%=form.format(new Date())%>" id="fechaInicioSolicitud" name="fechaInicioSolicitud" class="inputText">
                                <img id="imagenFechaInicioSolicitud" src="../../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fechaInicioSolicitud" click_element_id="imagenFechaInicioSolicitud">
                                </DLCALENDAR>
                            </td>
                            <td  height="35px" class="outputText2">Fecha Final Solicitud</td>
                            <td class="outputText2">::</td>
                            <td class="">
                                <input type="text" size="12" style="color:fuchsia" 
                                       value="<%=form.format(new Date())%>" id="fechaFinalSolicitud" name="fechaFinalSolicitud" class="inputText">
                                <img id="imagenfechaFinalSolicitud" src="../../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fechaFinalSolicitud" click_element_id="imagenfechaFinalSolicitud">
                                </DLCALENDAR>


                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Reporte Por Item" name="reporte" onclick="verReporte(form1,'reporteSalidasPorNroLote.jsp')" />
                                <input type="reset" class="btn"  value="Limpiar " name="reporteL" />
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
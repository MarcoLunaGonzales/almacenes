

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
        <script src="../../js/general.js"></script>
       
        <script type="text/javascript">
             function ajaxAlmacen(f){

                ajax = creaAjax();

                ajax.open("GET","../../ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_almacen").innerHTML=ajax.responseText;
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
                ajax = creaAjax();

                ajax.open("GET","../../ajaxGrupo.jsf?codCapitulo="+arrayCodCapitulo+"&multiple=1&size=10",true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_grupo").innerHTML=ajax.responseText;
                        desBloquearPantalla();
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

                //capitulos


                var nombreCapitulo=new Array();
                var codCapitulo=new Array();
                //var arrayNombreAreaEmpresa=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	nombreCapitulo[j]=f.codCapitulo.options[i].innerHTML;
                        codCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }

                //grupo
                var nombreGrupo=new Array();
                var codGrupo=new Array();
                j=0;
                for(var i=0;i<=f.codGrupo.options.length-1;i++)
                {	if(f.codGrupo.options[i].selected)
                    {	codGrupo[j]=f.codGrupo.options[i].value;
                        nombreGrupo[j]=f.codGrupo.options[i].innerHTML;
                        j++;
                    }
                }

                //f.codMaterial.value = arrayMateriales;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                f.nombreCapitulo.value = nombreCapitulo;
                f.nombreGrupo.value = nombreGrupo;
                f.codGrupoArray.value = codGrupo;
                f.codCapituloArray.value = codCapitulo;
                f.nombreMaterialP.value = f.nombreMaterial.value;
                f.numIngresoP.value = f.numIngreso.value;

                //Salert(f.codMaterial.value);
                f.action = nombreReporte;
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
        <h3 align="center">Reporte de Liquidación de Ingresos</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table border="0"  border="0" class="border" width="90%">
                    <tr class="headerClassACliente">
                        <td height="35px" colspan="6" >
                            <div class="outputText3" align="center">
                                Introduzca Datos
                            </div>
                        </td>

                    </tr>

                    <tr class="outputText3">
                        <td height="35px" class="">Filial</td>
                        <td class="">::</td>
                        <%
                            Connection con = null;
           String codProgramaProduccionPeriodoReq ="";
           if(request.getParameter("codProgramaProduccionPeriodo")!=null)
           {codProgramaProduccionPeriodoReq = request.getParameter("codProgramaProduccionPeriodo");}
           try {
            con = Util.openConnection(con);
            System.out.println("con:::::::::::::" + con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




            String sql = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

            System.out.println("sql filtro:" + sql);
            ResultSet rs = st.executeQuery(sql);
                        %>
                        <td class="">
                            <select name="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
                                <%

                            out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
                            while (rs.next()) {
                                out.print("<option value="+ rs.getString("cod_filial") +" >"+rs.getString("nombre_filial")+"</option>");
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
                            <select name="codAlmacen" class="inputText" >
                                <option value="-1">-TODOS-</option>
                            </select>
                            </div>
                        </td>

                    </tr>

                    <tr class="outputText3" >
                        <td class="">Capitulos</td>
                        <td class="">::</td>

                                 <%

                           try {
                            con = Util.openConnection(con);
                            System.out.println("con:::::::::::::" + con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




                            String sql = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1";

                            System.out.println("sql filtro:" + sql);
                            ResultSet rs = st.executeQuery(sql);
                        %>
                        <td class="">
                            <select name="codCapitulo" multiple size="10"  class="outputText3" onchange="ajaxGrupo(form1);">
                                <%


                            while (rs.next()) {
                                out.print("<option value="+ rs.getString("COD_CAPITULO") +" >"+rs.getString("NOMBRE_CAPITULO")+"</option>");
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
                         <td class="">Grupo</td>
                        <td class="">::</td>
                        <td class="">
                            <div id="div_grupo">
                                <select name="codGrupo" class="inputText" size="10" style="width:200px;" >
                                <option value="-1">-TODOS-</option>
                            </select>

                            </div>
                        </td>
                    </tr>
                    <tr class="outputText3">
                        <td  height="35px" class="">Proveedor</td>
                        <td class="">::</td>
                        <td class="">
                             <%

                           try {
                            con = Util.openConnection(con);
                            //System.out.println("con:::::::::::::" + con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                            String sql = " select p.COD_PROVEEDOR,p.NOMBRE_PROVEEDOR from PROVEEDORES p where p.COD_ESTADO_REGISTRO = 1";

                            System.out.println("sql filtro:" + sql);
                            ResultSet rs = st.executeQuery(sql);
                        %>

                            <select name="codProveedor"  class="outputText3" style="width:350px">
                                <option value="0" >-TODOS-</option>
                                <%
                            while (rs.next()) {
                                out.print("<option value="+ rs.getString("COD_PROVEEDOR") +" >"+rs.getString("NOMBRE_PROVEEDOR")+"</option>");
                            }%>
                            </select>
                            <%
                            rs.close();
                            st.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            %>

                            <%--input type="text" class="inputText" size="70" name="nombreMaterial" /--%>
                        </td>

                        <td class="">Tipo Ingreso Almacen</td>
                        <td class="">::</td>
                        <td class="">
                             <%

                           try {
                            con = Util.openConnection(con);
                            //System.out.println("con:::::::::::::" + con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                            String sql = " select t.COD_TIPO_INGRESO_ALMACEN ,t.NOMBRE_TIPO_INGRESO_ALMACEN from TIPOS_INGRESO_ALMACEN t where t.COD_ESTADO_REGISTRO = 1";

                            System.out.println("sql filtro:" + sql);
                            ResultSet rs = st.executeQuery(sql);
                        %>

                            <select name="codTipoIngresoAlmacen"  class="outputText3" >
                                <option value="0" >-TODOS-</option>
                                <%
                            while (rs.next()) {
                                out.print("<option value="+ rs.getString("COD_TIPO_INGRESO_ALMACEN") +" >"+rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")+"</option>");
                            }%>
                            </select>
                            <%
                            rs.close();
                            st.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            %>

                            <%--input type="text" class="inputText" size="70" name="nombreMaterial" /--%>
                        </td>



                    </tr>



                     <tr class="outputText3">
                        <td  height="35px" class="">Fecha Inicio</td>
                        <td class="">::</td>

                         <%
                                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                         %>

                        <td class="">

                            <input type="text"  size="12"  value="<%=form.format(new Date()) %>" name="fecha1" class="inputText">
                            <img id="imagenFecha1" src="../../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fecha1" click_element_id="imagenFecha1">
                             </DLCALENDAR>
                         </td>
                             <td  height="35px" class="">Fecha Inicio</td>
                        <td class="">::</td>
                           <td class="">
                             <input type="text"  size="12"  value="<%=form.format(new Date()) %>" name="fecha2" class="inputText">
                            <img id="imagenFecha2" src="../../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fecha2" click_element_id="imagenFecha2">
                             </DLCALENDAR>


                        </td>


                    </tr>

                    <tr class="outputText3">
                        <td  height="35px" class="">Item</td>
                        <td class="">::</td>
                        <td class="">
                                <input type="text" class="inputText" size="57" name="nombreMaterial" />
                        </td>
                        <td  height="35px" class="">Nro. Ingreso</td>
                        <td class="">::</td>
                        <td class="">
                                <input type="text" class="inputText" size="57" name="numIngreso" />
                        </td>
                    </tr>

                </table>



            </div>
            <br>
            <br>
            <center>
                <input type="button" class="outputText2"  value="Reporte Por Item" name="reporte" onclick="verReporte(form1,'reporteIngresosPorCapituloGrupo.jsp')" />
                <input type="reset" class="outputText2"  value="Limpiar" name="reporteL"  />

                <input type="hidden" name="codigosArea" id="codigosArea" />
            </center>
            <!--datos de referencia para el envio de datos via post-->

            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />
            <input type="hidden" name="codGrupoArray" />
            <input type="hidden" name="codCapituloArray" />
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">
            <input type="hidden" name="nombreMaterialP">
            <input type="hidden" name="numIngresoP">

            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">



        </form>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>
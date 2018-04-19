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
        <script type="text/javascript">
          function ajaxAlmacen(f){
                ajax = creaAjax();
                ajax.open("GET","../../ajaxAlmacenTodos.jsf?codFilial="+(f.codFilial.value)+"&multiple=1&size=5",true);
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

                //tipos_salida
                var nomTipoSalida=new Array();
                var codTipoSalida=new Array();
                //var arrayNombreAreaEmpresa=new Array();
                var j=0;
                for(var i=0;i<=f.codTipoSalida.options.length-1;i++)
                {	if(f.codTipoSalida.options[i].selected)
                    {	nomTipoSalida[j]=f.codTipoSalida.options[i].innerHTML;
                        codTipoSalida[j]=f.codTipoSalida.options[i].value;
                        j++;
                    }
                }
                var codAlmacenArray = new Array();
                var nombreAlmacenArray = new Array();
                for(var i=0 ; i<f.codAlmacenSelect.options.length ; i++)
                {	
                    if(f.codAlmacenSelect.options[i].selected)
                    {	
                        nombreAlmacenArray.push(f.codAlmacenSelect.options[i].innerHTML);
                        codAlmacenArray.push(f.codAlmacenSelect.options[i].value);
                    }
                }


                f.codTipoSalidaArray.value = codTipoSalida;
                f.nomTipoSalidaArray.value = nomTipoSalida;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text;
                f.nombreAlmacen.value = nombreAlmacenArray;
                f.codAlmacen.value = codAlmacenArray;
                

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

                function click1(f){
                //alert(f.check.checked);
                if (f.check.checked==true){
                    f.fecha2.disabled=false;
                    f.fecha1.disabled=false;
                }else{
                    f.fecha2.disabled=true;
                    f.fecha1.disabled=true;
                }



            }



        </script>



    </head>
    <body>
        <h3 align="center">RESUMEN DE SALIDAS DE ALMACEN POR SECCION</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="90%">
                    <thead>
                        <tr>
                            <td height="35px" colspan="6" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr >
                            <td height="35px" class="">Filial</td>
                            <td class="">::</td>
                            <%
                                Connection con = null;
                               ManagedAccesoSistema bean1=(ManagedAccesoSistema)com.cofar.util.Util.getSessionBean("ManagedAccesoSistema");
                               int codigoAlmacen=bean1.getAlmacenesGlobal().getCodAlmacen();
                               System.out.println("codigoAlmacen:"+codigoAlmacen);

                               String codProgramaProduccionPeriodoReq ="";
                               if(request.getParameter("codProgramaProduccionPeriodo")!=null)
                               {codProgramaProduccionPeriodoReq = request.getParameter("codProgramaProduccionPeriodo");}
                               int codFilial=0;
                               try 
                               {
                                con = Util.openConnection(con);
                                System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);



                                String sql_filial="select a.COD_FILIAL from ALMACENES a where a.COD_ALMACEN="+codigoAlmacen+"";
                                System.out.println("sql_filial:" + sql_filial);
                                Statement st_filial = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_filial = st_filial.executeQuery(sql_filial);
                                codFilial=0;
                                while(rs_filial.next()){
                                    codFilial=rs_filial.getInt(1);
                                }

                                String sql = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td style="padding:7px" class="">
                                <select style="color:fuchsia" id="codFilial" name="codFilial"  onchange="ajaxAlmacen(form1);">
                                    <%

                                out.print("<option value='-1'>--TODOS--</option>");
                                while (rs.next()) {
                                    if(codFilial==rs.getInt("cod_filial")){
                                        out.print("<option value='"+ rs.getString("cod_filial") +"' selected >"+rs.getString("nombre_filial")+"</option>");
                                    }else{
                                        out.print("<option value='"+ rs.getString("cod_filial") +"' >"+rs.getString("nombre_filial")+"</option>");
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
                            <td style="padding:7px" class="">Almacen</td>
                            <td style="padding:7px" class="">::</td>

                            <td style="padding:7px" class="">
                                <div id="div_almacen">

                                <%
                                String sql_almacenes="select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a where a.COD_FILIAL="+codFilial+" and a.COD_ESTADO_REGISTRO=1";
                                System.out.println("sql_almacenes:" + sql_almacenes);
                                Statement st_a = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_a = st_a.executeQuery(sql_almacenes);
                                %>
                                <select style="color:fuchsia" id="codAlmacenSelect" name="codAlmacenSelect" multiple size="5" class="inputText" >
                                    <%
                                    while (rs_a.next()) {
                                    if(codigoAlmacen==rs_a.getInt("COD_ALMACEN")){
                                        out.print("<option value='"+ rs_a.getString("cod_almacen") +"' selected >"+rs_a.getString("nombre_almacen")+"</option>");
                                    }else{
                                        out.print("<option value='"+ rs_a.getString("cod_almacen") +"' >"+rs_a.getString("nombre_almacen")+"</option>");
                                    }

                                }
                                    %>
                                </select>
                                </div>
                            </td>

                        </tr>


                        <tr >
                            <td  height="35px" class="">Tipo de Salida</td>
                            <td class="">::</td>
                            <td class="">
                                 <%

                               try {
                                con = Util.openConnection(con);
                                //System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                                String sql = " select t.COD_TIPO_SALIDA_ALMACEN,t.NOMBRE_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN t where t.COD_ESTADO_REGISTRO=1 order by t.NOMBRE_TIPO_SALIDA_ALMACEN";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>

                                <select id="codTipoSalida" name="codTipoSalida"   multiple size="9" style="width:350px">

                                    <%
                                while (rs.next()) {
                                    out.print("<option value='"+ rs.getString("COD_TIPO_SALIDA_ALMACEN") +"' >"+rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")+"</option>");
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

                            <td class="">Sección</td>
                            <td class="">::</td>
                            <td class="">
                                 <%

                               try {
                                con = Util.openConnection(con);
                                //System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                                String sql = " select cod_area_empresa,nombre_area_empresa from areas_empresa  where COD_ESTADO_REGISTRO = 1 order by nombre_area_empresa";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>

                                <select id="codAreaEmpresa" name="codAreaEmpresa"  class="outputText3 chosen" >
                                    <option value="0" >-TODOS-</option>
                                    <%
                                while (rs.next()) {
                                    out.print("<option value="+ rs.getString("cod_area_empresa") +">"+rs.getString("nombre_area_empresa")+"</option>");
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



                        <%--tr >
                            <td height="35px" class=""><b>Usar Fecha</td>
                            <td colspan="5" class=""><input type="checkbox" name="check" onclick="click1(this.form)"></td>
                        </tr--%>

                         <tr >
                            <td  height="35px" class="">Fecha Inicio</td>
                            <td class="">::</td>

                             <%
                                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                             %>

                            <td class="">

                                <input type="text" style="color:fuchsia"  size="12"  value="<%=form.format(new Date()) %>" id="fecha1" name="fecha1" class="inputText">
                                <img id="imagenFecha1" src="../../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fecha1" click_element_id="imagenFecha1">
                                 </DLCALENDAR>
                            </td>
                            <td  height="35px" class="">Fecha Final</td>
                            <td class="">::</td>
                            <td class="">
                                 <input type="text" style="color:fuchsia"  size="12"  value="<%=form.format(new Date()) %>" id="fecha2" name="fecha2" class="inputText">
                                <img id="imagenFecha2" src="../../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fecha2" click_element_id="imagenFecha2" >
                                 </DLCALENDAR>


                            </td>


                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Ver Reporte Por Secciones" name="reporte1" onclick="verReporte(form1,'reporteResumenSalidasAlmacenSeccion.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte Por Cuentas" name="reporte2" onclick="verReporte(form1,'reporteResumenSalidasAlmacenCuentas.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte Por Secciones Histórico" name="reporte1" onclick="verReporte(form1,'reporteResumenSalidasAlmacenSeccion_Historico.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte Por Cuentas Histórico" name="reporte2" onclick="verReporte(form1,'reporteResumenSalidasAlmacenCuentas_Historico.jsp')" />
                                <input type="reset" class="btn"  value="Limpiar" name="reporteL"  />
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
            <input type="hidden" name="codAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />
            <input type="hidden" name="codGrupoArray" />
            <input type="hidden" name="codTipoSalidaArray" />
            <input type="hidden" name="nomTipoSalidaArray" />
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
        <script src="../../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>
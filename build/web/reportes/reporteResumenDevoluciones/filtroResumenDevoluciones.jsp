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
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <link rel="STYLESHEET" type="text/css" href="../../css/chosen.css" />
        <script src="../../js/general.js"></script>
        
        <script type="text/javascript">
            
          function ajaxAlmacen(f){
                var div_almacen;
                div_almacen=document.getElementById("div_almacen");

                ajax = creaAjax();

                ajax.open("GET","ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
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
                f.numOCP.value = f.numOC.value;


                //Salert(f.codMaterial.value);
                f.action = nombreReporte;
                f.submit();

            }

             function enviarForm(f)
             {
                var codAlmacenArray = new Array();
                for(var i = 0 ; i < f.codAlmacenSelect.options.length ; i++)
                {
                    if(f.codAlmacenSelect.options[i].selected)
                    {
                        codAlmacenArray.push(f.codAlmacenSelect.options[i].value);
                    }
                }
                var codTipoSalidaArray = new Array();
                for(var i=0 ; i < f.codTipoSalidaSelect.options.length ; i++)
                {
                    if(f.codTipoSalidaSelect.options[i].selected)
                    {
                        codTipoSalidaArray.push(f.codTipoSalidaSelect.options[i].value);
                    }
                }
                f.codTipoSalida.value = codTipoSalidaArray;
                f.codAlmacen.value = codAlmacenArray; 
                var inputhi= document.getElementById("reporteConLote");
                var check= document.getElementById("conLote");
                
                var todos=document.getElementById("todos");
                var varTodos=document.getElementById("varTodos");
                var tipoReporte= document.getElementById("codTipoReporte");
               // alert(tipoReporte.value);
                if(tipoReporte.value==1)
                   {
                        f.action="reporteResumenPorSecciones.jsf";
                      //  alert(f.action);
                   }
                    if(tipoReporte.value==2)
                   {
                        f.action="reporteResumenDevoluciones.jsf";
                      //  alert(f.action);
                   }
                    if(tipoReporte.value==3)
                   {
                        f.action="reporteResumenCapYGrup.jsf";
                      //  alert(f.action);
                   }
                    if(tipoReporte.value==4)
                   {
                        f.action="reporteDetalladoCapYGrup.jsf";
                      //  alert(f.action);
                   }
                if(check.checked==true)
                     {
                        inputhi.value="1"
                       // alert('con');
                     }
                     else
                         {
                            inputhi.value="2";
                        //   alert('sin');
                         }
                         if(todos.checked==true)
                             {
                                 varTodos.value="1";
                             }
                             else
                                 {
                                     varTodos.value="2";
                                 }
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
        <h3 align="center">Reporte de Resumen de Devoluciones</h3>
        <form method="post" action="reporteResumenDevoluciones.jsp" target="_blank" name="form1">
            <div align="center">
                <center>
                <table class="tablaFiltroReporte" width="50%">
                    <thead>
                        <tr class="headerClassACliente">
                            <td colspan="3">Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="outputText3" >

                          <td height="35px" class="">Filial</td>

                            <td class="">::</td>
                            <%
                Connection con = null;
                ManagedAccesoSistema bean1=(ManagedAccesoSistema)com.cofar.util.Util.getSessionBean("ManagedAccesoSistema");
                int codigoAlmacen=bean1.getAlmacenesGlobal().getCodAlmacen();
                String codFilial="";
                String codProgramaProduccionPeriodoReq ="";
                if(request.getParameter("codProgramaProduccionPeriodo")!=null)
                {codProgramaProduccionPeriodoReq = request.getParameter("codProgramaProduccionPeriodo");}
                try {
                con = Util.openConnection(con);
                String sql_filial="select a.COD_FILIAL from ALMACENES a where a.COD_ALMACEN="+codigoAlmacen+"";
                System.out.println("consulta "+sql_filial);
                Statement stfilial=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resFilial=stfilial.executeQuery(sql_filial);
                if(resFilial.next())
                {
                    codFilial=resFilial.getString("COD_FILIAL");
                }
                resFilial.close();
                stfilial.close();
                System.out.println("cod filial "+codFilial);
                System.out.println("con:::::::::::::" + con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);            




                String sql = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

                System.out.println("sql filtro:" + sql);
                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td class="">
                                <select name="codFilial" id="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
                                    <%

                                out.print("<option value='-1'>-TODOS-</option>");
                                while (rs.next()) {
                                    if(codFilial.equals(rs.getString("cod_filial")))
                                    {
                                    out.print("<option value="+ rs.getString("cod_filial") +" selected >"+rs.getString("nombre_filial")+"</option>");

                                    }
                                    else
                                     {
                                        out.print("<option value="+ rs.getString("cod_filial") +" >"+rs.getString("nombre_filial")+"</option>");
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
                            </tr>
                            <tr  class="outputText3">
                            <td class="">Almacen</td>
                            <td class="">::</td>

                            <td class="">
                                <div id="div_almacen">
                                  <%
                                String sql_almacenes="select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a where a.COD_FILIAL="+codFilial+" and a.COD_ESTADO_REGISTRO=1";
                                System.out.println("sql_almacenes:" + sql_almacenes);
                                Statement st_a = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_a = st_a.executeQuery(sql_almacenes);
                                %>
                                <select  name="codAlmacenSelect" id="codAlmacenSelect" multiple size="5" class="inputText" >
                                    <%
                                    while (rs_a.next()) {
                                    if(codigoAlmacen==rs_a.getInt("COD_ALMACEN")){
                                        out.print("<option value="+ rs_a.getString("cod_almacen") +" selected >"+rs_a.getString("nombre_almacen")+"</option>");
                                    }else{
                                        out.print("<option value="+ rs_a.getString("cod_almacen") +" >"+rs_a.getString("nombre_almacen")+"</option>");
                                    }

                                }
                                    %>
                                </select>
                                </div>
                            </td>

                        </tr>
                        <tr class="outputText3" >
                            <td style="padding:8px">Tipos de Salida</td>
                            <td style="padding:8px">::</td>

                                     <%

                               try {
                                con = Util.openConnection(con);
                                System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




                                String sql = " select t.COD_TIPO_SALIDA_ALMACEN,t.NOMBRE_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN t where t.COD_ESTADO_REGISTRO = 1 order by t.COD_TIPO_SALIDA_ALMACEN asc";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td style="padding:8px">
                                <select name="codTipoSalidaSelect" id="codTipoSalidaSelect" multiple size="10" class="outputText3">
                                    <%


                                while (rs.next()) {
                                    out.print("<option value="+ rs.getString("COD_TIPO_SALIDA_ALMACEN") +" >"+rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")+"</option>");
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
                        </tr>



                         <tr class="outputText3">
                            <td  height="35px" class="">Fecha Inicio</td>
                            <td class="">::</td>

                             <%
                                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                                    String text=form.format(new Date());
                                    String[] arraytext=text.split("/");
                                    text="01/"+arraytext[1]+"/"+arraytext[2];
                             %>

                            <td class="">

                                <input type="text"  size="12"  value="<%=text%>" id="fecha1" name="fecha1" class="inputText">
                                <img id="imagenFecha1" src="../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fecha1" click_element_id="imagenFecha1">
                                 </DLCALENDAR>
                             </td>
                         </tr>
                         <tr  class="outputText3">
                                 <td  height="35px" class="">Fecha Final</td>
                            <td class="">::</td>
                               <td class="">
                                 <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fecha2" name="fecha2" class="inputText">
                                <img id="imagenFecha2" src="../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fecha2" click_element_id="imagenFecha2">
                                 </DLCALENDAR>


                            </td>
                         </tr>
                         <tr class="outputText3">
                            <td height="35px" class="">Seccion</td>
                            <td class="">::</td>
                            <%

                try {
                con = Util.openConnection(con);
                System.out.println("con:::::::::::::" + con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




                String sql = " select ae.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA from AREAS_EMPRESA ae where ae.COD_ESTADO_REGISTRO=1 order by ae.NOMBRE_AREA_EMPRESA ASC";

                System.out.println("sql filtro:" + sql);
                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td class="">
                                <select name="codSeccion" id="codSeccion" class="outputText3 chosen">
                                    <%

                                out.print("<option value='-1'>-TODOS-</option>");
                                while (rs.next()) {
                                    out.print("<option value="+ rs.getString("COD_AREA_EMPRESA") +" >"+rs.getString("NOMBRE_AREA_EMPRESA")+"</option>");
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
                            </tr>
                            <tr class="outputText3">
                                <td></td>
                                <td><input type="checkbox" id="conLote" name="conLote"></td>

                                <td height="35px" class=""><label for="conLote">Con Lote</label></td>

                            </tr>
                             <tr class="outputText3">
                                <td></td>
                                <td><input type="checkbox" id="todos" name="todos"></td>

                                <td height="35px" class=""><label for="todos">Todos</label></td>

                            </tr>
                            <tr class="outputText3">
                                <td>Tipo de Reporte</td>
                                <td>::</td>
                                <td>
                                    <select name="codTipoReporte" id="codTipoReporte" class="outputText3" >
                                <option value="4" selected>Reporte Detallado por Cap. y Grupos</option>
                                <option value="2" selected>Reporte Detallado por Secciones</option>

                                 <option value="1">Reporte Resumen por Secciones</option>

                                 <option value="3">Reporte Resumen por Cap. y Grupos</option>
                             </select>
                                </td>
                            </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3">
                                <a class="btn" name="reporte" onclick="enviarForm(form1)" >Ver Reporte</a>
                            </td>
                        </tr>
                    </tfoot>
                </table>
                </center>


            </div>
            <br>
            <br>
            <center>
                
                
            </center>
            <input type="hidden" id="codTipoSalida" name="codTipoSalida" />
            <input type="hidden" id="reporteConLote" name="reporteConLote" />
            <input type="hidden" id="nombreFilial" name="nombreFilial" />
            <input type="hidden" id="nombreAlmacen" name="nombreAlmacen" />
            <input type="hidden" id="codAlmacen" name="codAlmacen" />
            <input type="hidden" id="nombreCapitulo" name="nombreCapitulo" />
            <input type="hidden" id="nombreGrupo" name="nombreGrupo" />
            <input type="hidden" id="codGrupoArray" name="codGrupoArray" />
            <input type="hidden" id="codCapituloArray" name="codCapituloArray" />
            <input type="hidden" id="codProgramaProduccionPeriodo" name="codProgramaProduccionPeriodo">
            <input type="hidden" id="nombreProgramaProduccionPeriodo" name="nombreProgramaProduccionPeriodo">
            <input type="hidden" id="codCompProdArray" name="codCompProdArray">
            <input type="hidden" id="varTodos" name="varTodos">
            <input type="hidden" id="codAreaEmpresaP" name="codAreaEmpresaP">
            <input type="hidden" id="nombreAreaEmpresaP" name="nombreAreaEmpresaP">
            <input type="hidden" id="desdeFechaP" name="desdeFechaP">
            <input type="hidden" id="hastaFechaP" name="hastaFechaP">
            <input type="hidden" id="nombreMaterialP" name="nombreMaterialP">
            <input type="hidden" id="nombreFilial" name="nombreFilial">
            <input type="hidden" id="nombreAlmacen" name="nombreAlmacen">



        </form>
        <script src="../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>
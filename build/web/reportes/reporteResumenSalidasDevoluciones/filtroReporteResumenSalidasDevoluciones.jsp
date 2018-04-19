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
                ajax = creaAjax();

                ajax.open("GET","../ajaxAlmacenTodos.jsf?codFilial="+(f.codFilial.value),true);
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
                var movimiento = document.getElementById("movimiento").value;


                var div_material;
                div_material=document.getElementById("div_material");

                ajax=nuevoAjax();

                ajax.open("GET","ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo+"&nombreMaterial="+nombreMaterial+"&movimiento="+movimiento,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_material.innerHTML=ajax.responseText;
                    }
                }

                ajax.send(null);


            }
            //cel.getElementsByTagName('input')[0].checked=true;
            function verReporte(f,nombreReporte){
                // seleccionar los items de la tabla
                var codAlmacenArray = new Array();
                var nombreAlmacenArray = new Array();
                
                for(var i = 0 ; i < f.codAlmacenSelect.options.length ; i++)
                {
                    if(f.codAlmacenSelect.options[i].selected)
                    {
                        codAlmacenArray.push(f.codAlmacenSelect.options[i].value);
                        nombreAlmacenArray.push(f.codAlmacenSelect.options[i].innerHTML);
                    }
                }
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text;
                f.nombreAlmacen.value = nombreAlmacenArray;
                f.codAlmacen.value = codAlmacenArray;
                var arrayCodTipoSalidaAlmacen = new Array();
                var arrayNombreTipoSalidaAlmacen = new Array();
                var j = 0;
                for(var i=0;i<=f.codTipoSalida.options.length-1;i++)
                {	if(f.codTipoSalida.options[i].selected)
                    {	arrayCodTipoSalidaAlmacen[j]=f.codTipoSalida.options[i].value;
                        arrayNombreTipoSalidaAlmacen[j]=f.codTipoSalida.options[i].innerHTML;

                        j++;
                    }
                }
                if(arrayCodTipoSalidaAlmacen.length>0)
                    {
                f.codigosTipos.value = arrayCodTipoSalidaAlmacen;
                    }
                    else
                        {
                            f.codigosTipos.value ='-1';
                        }
                f.nombreTipoSalida.value = arrayNombreTipoSalidaAlmacen;

            var tipoReporte= document.getElementById("codTipoReporte");
               // alert(tipoReporte.value);
                if(tipoReporte.value==1)
                   {
                        f.action="reporteResumenSalidasDevolucion.jsf";
                      //  alert(f.action);
                   }
                    if(tipoReporte.value==2)
                   {
                        f.action="reporteResumenSalidasDevolucionesPorCapGrup.jsf";
                      //  alert(f.action);
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
        <h3 align="center">REPORTE RESUMEN DE SALIDAS Y DEVOLUCIONES</h3>
        <form method="post" action="reporteResumenSalidas.jsp" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="50%">
                    <thead>
                        <tr>
                            <td  colspan="6" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td style="padding:8px">Filial</td>
                            <td style="padding:8px">::</td>
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
                            <td style="padding:8px">
                                <select id="codFilial" name="codFilial"  onchange="ajaxAlmacen(form1);">
                                    <%

                                out.print("<option value='-1'>-TODOS-</option>");

                                while (rs.next()) {
                                    if(codFilial==rs.getInt("cod_filial")){
                                        out.print("<option value='"+ rs.getString("cod_filial") +"' selected >"+rs.getString("nombre_filial")+"</option>");
                                    }else{
                                        out.print("<option value='"+ rs.getString("cod_filial") +"' >"+rs.getString("nombre_filial")+"</option>");
                                    }

                                }
                                %>
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
                            <td style="padding:8px">Almacen</td>
                            <td style="padding:8px">::</td>

                            <td style="padding:8px">
                                <div id="div_almacen">
                                <%
                                String sql_almacenes="select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a where a.COD_FILIAL="+codFilial+" and a.COD_ESTADO_REGISTRO=1";
                                System.out.println("sql_almacenes:" + sql_almacenes);
                                Statement st_a = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_a = st_a.executeQuery(sql_almacenes);
                                %>
                                <select id="codAlmacenSelect" name="codAlmacenSelect"  size = '5' multiple class="inputText" >
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
                                <select id="codTipoSalida" name="codTipoSalida" multiple size="10">
                                    <%


                                while (rs.next()) {
                                    out.print("<option value='"+ rs.getString("COD_TIPO_SALIDA_ALMACEN") +"' >"+rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")+"</option>");
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
                            <td style="padding:8px">Salidas con Lote ::</td>
                            <td style="padding:8px"><input id="conLote" type="checkbox" name="conLote"/></td>
                            <td  style="padding:8px">Todos ::<input type="checkbox" id="todosLote" name="todosLote" />
                            </td>
                        </tr>

                         <tr>
                            <td style="padding:8px" style="padding:8px">Fecha Inicio</td>
                            <td style="padding:8px" style="padding:8px">::</td>

                             <%
                                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                             %>

                            <td style="padding:8px" style="padding:8px">
                                <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fecha_inicio" name="fecha_inicio" class="dlCalendar"/>
                            </td>
                            <td style="padding:8px" style="padding:8px">Fecha Final</td>
                            <td style="padding:8px" style="padding:8px">::</td>
                            <td style="padding:8px">
                                <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fecha_final" name="fecha_final" class="dlCalendar"/>
                            </td>
                        </tr>
                        <tr>
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
                                <select id="codSeccion" name="codSeccion" class="outputText3 chosen">
                                    <%

                                out.print("<option value='-1'>-TODOS-</option>");
                                while (rs.next()) {
                                    out.print("<option value='"+ rs.getString("COD_AREA_EMPRESA") +"' >"+rs.getString("NOMBRE_AREA_EMPRESA")+"</option>");
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
                             <td>Tipo de Reporte</td>
                                <td>::</td>
                                <td>
                            <select id="codTipoReporte" name="codTipoReporte" >
                                <option value="1" selected>Reporte Resumen por Secciones</option>
                                <option value="2" >Reporte Resumen por Cap. y Grup.</option>



                            </select>
                        </tr>

                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Ver Reporte" name="reporte" onclick="verReporte(form1,'reporteResumenSalidas.jsf')"/><%-- onclick="verReporte(form1,'reporteExistenciasAlmacen.jsp')" --%>
                            </td>
                        </tr>
                    </tfoot>

                </table>



            </div>
            <br>
            <center>
                



                <input type="hidden" name="codigosArea" id="codigosArea" />
            </center>
            <!--datos de referencia para el envio de datos via post-->

            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />
            <input type="hidden" name="codigosTipos" />
            <input type="hidden" name="codAlmacen"/>




            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">

            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">
            <input type="hidden" name="nombreTipoSalida">



        </form>
        <script src="../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>
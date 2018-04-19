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
        
        <script type="text/javascript" >
            function ajaxAlmacen(f){
                ajax = creaAjax();

                ajax.open("GET","../../ajaxAlmacenTodos.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_almacen").innerHTML = ajax.responseText;
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
               
                var codTipoSalidaArray = new Array();
                var nombreTipoSalidaArray = new Array();
                var selectTipo = document.getElementById("codTipoSalidaSelect");
                for(var i =0 ; i <  selectTipo.options.length;i++){
                    if(selectTipo.options[i].selected){
                        codTipoSalidaArray.push(selectTipo.options[i].value);
                        nombreTipoSalidaArray.push(selectTipo.options[i].innerHTML);
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
                f.codTipoSalida.value = codTipoSalidaArray;
                f.nombreTipoSalida.value = nombreTipoSalidaArray;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text;
                f.codAlmacen.value = codAlmacenArray;
                f.nombreAlmacen.value = nombreAlmacenArray;
                f.nombreProductoP.value = f.codProducto.options[f.codProducto.selectedIndex].text
                //f.nombreMaterialP.value = f.nombreMaterial.value;
                f.codProductoP.value = f.codProducto.value;
                f.numLoteP.value = f.numLote.value;
                f.codTipoLoteP.value= f.codTipoLote.options[f.codTipoLote.selectedIndex].value       

                //alert(f.codTipoLoteP.value);
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
        <br>
        <br>
        <h3 align="center">Reporte Resúmen General de Lotes</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="60%">
                    <thead>
                        <tr>
                            <td height="35px" colspan="6" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>

                        <tr class="outputText3">
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
               try {
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
                                <select style="color:fuchsia" id="codFilial" name="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
                                    <%

                                out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
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
                                <select style="color:fuchsia" multiple size="5" id="codAlmacenSelect" name="codAlmacenSelect" class="inputText" >
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

                        <tr class="outputText3">


                            <td class="">Producto </td>
                            <td class="">::</td>
                            <td class="">
                                 <%

                               try {
                                con = Util.openConnection(con);
                                //System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                                String sql = " select c.COD_COMPPROD,c.nombre_prod_semiterminado from COMPONENTES_PROD c where c.nombre_prod_semiterminado is not null and c.nombre_prod_semiterminado != '' order by c.nombre_prod_semiterminado          ";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>

                            <select style="width:350px;color:blue" id="codProducto" name="codProducto"  class="outputText3 chosen" >
                                    <option value="0" >-TODOS-</option>
                                    <%
                                while (rs.next()) {
                                    out.print("<option value='"+ rs.getString("COD_COMPPROD") +"' >"+rs.getString("nombre_prod_semiterminado")+"</option>");
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
                           <td  height="35px" class="">Nro de Lote</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text" style="color:blue;text-transform:uppercase" onkeypress="valNum(event);" class="inputText" size="20" id="numLote" name="numLote" />
                            </td>


                        </tr>



                         <tr class="outputText3">
                            <td  height="35px" class="">Fecha Inicio</td>
                            <td class="">::</td>

                             <%
                                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                             %>

                            <td class="">

                                <input type="text"  size="12" style="color:fuchsia"   value="<%=form.format(new Date()) %>" id="fecha1" name="fecha1" class="dlCalendar">
                             </td>
                                 <td  height="35px" class="">Fecha Final</td>
                            <td class="">::</td>
                               <td class="">
                                <input type="text"  size="12" style="color:fuchsia"     value="<%=form.format(new Date()) %>" id="fecha2" name="fecha2" class="dlCalendar">
                            </td>


                    </tr>
                    <tr class="outputText3">
                            <td  height="35px" class="">Tipo Lote</td>
                            <td class="">::</td>



                            <td class="">

                                <select style="width:350px;color:blue" id="codTipoLote" name="codTipoLote"  class="outputText3" >
                                    <option value="0" >-TODOS-</option>
                                    <option value="1" >Lotes de Producción</option>
                                    <option value="2" >Lotes en Desarrollo</option>

                                </select>
                             </td>
                    </tr>
                    <tr>
                        <td class="outputText2">Tipo de Salida</td>
                        <td class="outputText2">::</td>
                        <td >
                            <select id="codTipoSalidaSelect" name="codTipoSalidaSelect"   multiple size="5">
                            <%
                                try {
                                    con = Util.openConnection(con);
                                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                    String sql = " select t.COD_TIPO_SALIDA_ALMACEN,t.NOMBRE_TIPO_SALIDA_ALMACEN "+
                                                    ", case when t.COD_TIPO_SALIDA_ALMACEN in (1,2,4,5,6,8) then 1 else 0 end as seleccionado"+
                                                 " from TIPOS_SALIDAS_ALMACEN t where t.COD_ESTADO_REGISTRO=1 order by t.NOMBRE_TIPO_SALIDA_ALMACEN";
                                    ResultSet res = st.executeQuery(sql);
                                    while (res.next()) {
                                    out.print("<option value='"+ res.getString("COD_TIPO_SALIDA_ALMACEN") +"' "+(res.getInt("seleccionado") > 0 ? "selected":"")+" >"+res.getString("NOMBRE_TIPO_SALIDA_ALMACEN")+"</option>");
                                    }   
                                    
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                finally{
                                    con.close();
                                }
                            %>
                            </select>
                            <input type="hidden" id="codTipoSalida" name="codTipoSalida"/>
                            <input type="hidden" id="nombreTipoSalida" name="nombreTipoSalida"/>
                        </td>
                        <td class="outputText2">Mostrar todas las devoluciones sin importar su almacén destino</td>
                        <td class="outputText2">::</td>
                        <td><input type="checkbox" name="devolucionesTodosAlmacenes" value="false"/> </td>
                    </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Ver Reporte" name="reporte" onclick="verReporte(form1,'reporteResumenGeneralLotes.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte con CU" name="reporte" onclick="verReporte(form1,'reporteResumenGeneralLotesCU.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte Excel" name="reporte" onclick="verReporte(form1,'reporteResumenGeneralLotesExportarExcel.jsp')" />
                                <input type="button" class="btn" onclick="limpiar(form1);"  value="Limpiar " name="reporteL" />
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

            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="codAlmacen" />
           
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">


            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">
            <input type="hidden" name="nombreMaterialP">
            <input type="hidden" name="codProductoP">
            <input type="hidden" name="nombreProductoP">
            <input type="hidden" name="numLoteP">
<input type="hidden" name="codTipoLoteP">



        </form>
        <script src="../../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>
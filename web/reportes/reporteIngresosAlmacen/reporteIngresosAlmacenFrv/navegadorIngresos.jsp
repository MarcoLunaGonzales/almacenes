


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
                //tipo ingreso
                var nombreTipoI=new Array();
                var codTipoI=new Array();
                j=0;
                for(var i=0;i<=f.codTipoIngresoAlmacen.options.length-1;i++)
                {	if(f.codTipoIngresoAlmacen.options[i].selected)
                    {	codTipoI[j]=f.codTipoIngresoAlmacen.options[i].value;
                        nombreTipoI[j]=f.codTipoIngresoAlmacen.options[i].innerHTML;
                        j++;
                    }
                }

                //f.codMaterial.value = arrayMateriales;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                f.nombreCapitulo.value = nombreCapitulo;
                f.nombreGrupo.value = nombreGrupo;
                f.codGrupoArray.value = codGrupo;
                f.codTipoIngresoAlmacenArray.value = codTipoI;
                f.nombreTipoIngresoAlmacenArray.value = nombreTipoI;
                f.codCapituloArray.value = codCapitulo;
                f.nombreMaterialP.value = f.nombreMaterial.value;
                f.numIngresoP.value = f.numIngreso.value;
                f.numOCP.value = f.numOC.value;


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
        <h3 align="center">Reporte de Ingresos</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="90%">
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
                            <td style="padding:8px">
                                <select style='color:fuchsia' id="codFilial" name="codFilial" onchange="ajaxAlmacen(form1);">
                                    <%

                                out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");

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
                                <select style='color:fuchsia' id="codAlmacen" name="codAlmacen" class="inputText" >
                                    <option value="-1">-TODOS-</option>
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
                            <td class="">Capitulos</td>
                            <td class="">::</td>

                                     <%

                               try {
                                con = Util.openConnection(con);
                                System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




                                String sql = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1 order by c.NOMBRE_CAPITULO";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td class="" width="15%">
                                <select id="codCapitulo" name="codCapitulo" multiple size="10"  onchange="ajaxGrupo(form1);">
                                    <%


                                while (rs.next()) {
                                    out.print("<option value='"+ rs.getString("COD_CAPITULO") +"' >"+rs.getString("NOMBRE_CAPITULO")+"</option>");
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
                                    <select id="codGrupo" name="codGrupo" class="inputText" size="10" style="width:200px;" >
                                    <option value="-1">-TODOS-</option>
                                </select>

                                </div>
                            </td>
                        </tr>
                        <tr>
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

                                <select id="codProveedor" name="codProveedor"  class="outputText3 chosen" style="width:350px">
                                    <option value="0" >-TODOS-</option>
                                    <%
                                while (rs.next()) {
                                    out.print("<option value='"+ rs.getString("COD_PROVEEDOR") +"' >"+rs.getString("NOMBRE_PROVEEDOR")+"</option>");
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

                            <td class="">Tipo Ingreso </td>
                            <td class="">::</td>
                            <td class="">
                                 <%

                               try {
                                con = Util.openConnection(con);
                                //System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                                String sql = " select  t.COD_TIPO_INGRESO_ALMACEN ,t.NOMBRE_TIPO_INGRESO_ALMACEN from TIPOS_INGRESO_ALMACEN t where t.COD_ESTADO_REGISTRO = 1" +
                                        " order by t.nombre_tipo_ingreso_almacen";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>

                                <select id="codTipoIngresoAlmacen" name="codTipoIngresoAlmacen"  multiple >

                                    <%
                                while (rs.next()) {
                                    out.print("<option value='"+ rs.getString("COD_TIPO_INGRESO_ALMACEN") +"' >"+rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")+"</option>");
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



                         <tr>
                            <td  height="35px" class="">Fecha Inicio</td>
                            <td class="">::</td>

                             <%
                                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                             %>

                            <td class="">

                                <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fecha1" name="fecha1" class="dlCalendar"/>
                            </td>
                                 <td  height="35px" class="">Fecha Final</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fecha2" name="fecha2" class="dlCalendar"/>
                            </td>


                        </tr>
                        <tr>
                            <td  height="35px" class="">Item</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text" style="color:blue;text-transform:uppercase" class="inputText" size="55" id="nombreMaterial" name="nombreMaterial" />
                            </td>
                            <td  height="35px" class="">Nro. Ingreso</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text" style="color:blue;text-transform:uppercase" onkeypress="valNum(event);" class="inputText" size="20" id="numIngreso" name="numIngreso" />
                            </td>
                        </tr>

                        <tr>

                            <td  height="35px" class="">Nro de O.C.</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text" style="color:blue;text-transform:uppercase" onkeypress="valNum(event);" class="inputText" size="20" id="numOC" name="numOC" />
                            </td>
                       </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Reporte Por Item" name="reporte" onclick="verReporte(form1,'reporteIngresos.jsp')" />
                                <input type="button" class="btn"  value="Reporte Resumido " name="reporte" onclick="verReporte(form1,'reporteIngresosResumido.jsp')" />
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
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />
            <input type="hidden" name="codGrupoArray" />
            <input type="hidden" name="codCapituloArray" />
            <input type="hidden" name="codTipoIngresoAlmacenArray" />
            <input type="hidden" name="nombreTipoIngresoAlmacenArray" />
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">


            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">
            <input type="hidden" name="nombreMaterialP">
            <input type="hidden" name="numIngresoP">
            <input type="hidden" name="numOCP">



        </form>
        <script src="../../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>
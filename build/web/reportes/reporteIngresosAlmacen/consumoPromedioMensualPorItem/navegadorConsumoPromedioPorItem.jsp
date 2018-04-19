
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

<%@page import="com.cofar.web.ManagedAccesoSistema" %>
<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <link rel="STYLESHEET" type="text/css" href="../../../css/chosen.css" />
        <script src="../../../js/general.js"></script>
        <script >
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
                var arrayCodCapitulo=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {
                    if(f.codCapitulo.options[i].selected){
                        arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
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
                ajax = creaAjax();
                ajax.open("GET","../../ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo+"&nombreMaterial="+encodeURIComponent(nombreMaterial)+
                    "&codEstadoRegistro="+f.estadoMaterial.value+"&movimientoItem="+f.itemEstado.value+"&data="+(new Date()).getTime().toString()
                ,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_material").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }

                ajax.send(null);


            }
            //cel.getElementsByTagName('input')[0].checked=true;
            function verReporte(f,nombreReporte){
               
                //capitulos
                var elements=document.getElementById('dataMateriales');
                 var rowsElement=elements.rows;
                 var arrayMateriales = new Array();
                 var j = 0;
                 var count=0;
                for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel=cellsElement[0];
                        var cel1=cellsElement[1];
                        if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                            if(cel.getElementsByTagName('input')[0].checked==true){
                                count++;
                                arrayMateriales[j]=cel.getElementsByTagName('input')[1].value==''?"0":cel.getElementsByTagName('input')[1].value;
                                f.nombreMaterialP.value =cel1.innerHTML;
                                j = j+1;
                                //alert(arrayMateriales[i]);
                            }
                        }
                }
                   if(count==0){
                       alert('No escogio ningun registro');
                       return false;
                   }
                   else if(count>1){
                       alert('Solo puede escoger un registro');
                       return false;
                   }        

                f.codMaterial.value = arrayMateriales;


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
                
                //Salert(f.codMaterial.value);
                f.action = nombreReporte;
                f.submit();

            }
            function verReporteGeneral(f,nombreReporte){

                //capitulos
                var elements=document.getElementById('dataMateriales');
                 var rowsElement=elements.rows;
                 var arrayMateriales = new Array();
                 var j = 0;
                 var count=0;
                for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel=cellsElement[0];
                        var cel1=cellsElement[1];
                        if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                            if(cel.getElementsByTagName('input')[0].checked==true){
                                count++;
                                arrayMateriales[j]=cel.getElementsByTagName('input')[1].value==''?"0":cel.getElementsByTagName('input')[1].value;
                                f.nombreMaterialP.value =cel1.innerHTML;
                                j = j+1;
                                //alert(arrayMateriales[i]);
                            }
                        }
                }
                   
                f.codMaterial.value = arrayMateriales;

                //alert(f.codMaterial.value);
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
    function seleccionarTodosMateriales(inputChecked)
    {
        var tabla=document.getElementById("dataMateriales");
        for(var i=1;i<tabla.rows.length;i++)
        {
            tabla.rows[i].cells[0].getElementsByTagName("input")[0].checked=inputChecked.checked;
        }
    }


        </script>



    </head>
    <body >
        <h3 align="center">Consumo Promedio Mensual Por Item</h3>
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
                                ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");

                                String codProgramaProduccionPeriodoReq ="";
                                if(request.getParameter("codProgramaProduccionPeriodo")!=null)
                                {codProgramaProduccionPeriodoReq = request.getParameter("codProgramaProduccionPeriodo");}
                                try {
                                 con = Util.openConnection(con);
                                 System.out.println("con:::::::::::::" + con);
                                 Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);            

                                 String consulta="select f.COD_FILIAL from FILIALES f inner join ALMACENES a on f.COD_FILIAL=a.COD_FILIAL where a.COD_ALMACEN='"+user.getAlmacenesGlobal().getCodAlmacen()+"'";
                                 ResultSet rs =st.executeQuery(consulta);
                                 String codFilial="";
                                 if(rs.next())
                                 {
                                     codFilial=rs.getString("COD_FILIAL");
                                 }
                                 String sql = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

                                 System.out.println("sql filtro:" + sql);
                                 rs = st.executeQuery(sql);
                            %>
                            <td class="">
                                <select id="codFilial" name="codFilial"  onchange="ajaxAlmacen(form1);">
                                    <%

                                out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
                                while (rs.next()) {
                                    out.print("<option "+(codFilial.equals(rs.getString("cod_filial"))?"selected":"")+" value='"+ rs.getString("cod_filial") +"' >"+rs.getString("nombre_filial")+"</option>");
                                }%>
                                </select>
                                <!--  <input type="checkbox"  onclick="selecccionarTodo(form1)" name="chk_todoTipo" >Todo-->
                                <%

                                %>
                            </td>
                            <td class="">Almacen</td>
                            <td class="">::</td>

                            <td class="">
                                <div id="div_almacen">
                                <%
                                        consulta = " select cod_almacen, nombre_almacen from almacenes where cod_estado_registro=1 and cod_filial='"+codFilial+"' ";

                                        System.out.println("consulta Filial "+consulta);
                                        con=Util.openConnection(con);

                                         rs=st.executeQuery(consulta);
                                        out.println("<select id='codAlmacen' name='codAlmacen'  class='inputText' >");
                                        out.println("<option value='0'>-TODOS-</option>");
                                        while(rs.next()){
                                            out.println("<option "+((user.getAlmacenesGlobal().getCodAlmacen()==rs.getInt("cod_almacen"))?"selected":"")+" value='"+rs.getString("cod_almacen")+"'>"+rs.getString("nombre_almacen")+"</option>");
                                        }
                                        out.println("</select>");
                                        rs.close();
                                        st.close();
                                        } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                %>
                                </div>
                            </td>

                        </tr>

                        <tr  >
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
                            <td class="">
                                <select id="codCapitulo" name="codCapitulo" multiple size="10"   onchange="ajaxGrupo(form1);">
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
                        <tr >
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

                                <select id="codTipoIngresoAlmacen" name="codTipoIngresoAlmacen"   >
                                    <option value="0" >-TODOS-</option>
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
                            <td height="35px" class="">Fecha Inicio</td>
                            <td class="">::</td>

                             <%
                                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                             %>

                            <td>

                                <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fecha1" name="fecha1" class="dlCalendar"/>
                            </td>
                                 <td  height="35px" class="">Fecha Inicio</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fecha2" name="fecha2" class="dlCalendar"/>
                                
                            </td>


                        </tr>
                        <tr >
                           <td  height="35px" class="">Estado Material</td>
                           <td class="">::</td>
                           <td>
                               <select id="estadoMaterial" name="estadoMaterial" class="inputText">
                                   <option value="1" selected>Activo</option>
                                   <option value="2">No Activo</option>
                               </select>
                           </td>
                           <td  height="35px" class="">Item</td>
                           <td class="">::</td>
                           <td>
                               <select id="itemEstado" name="itemEstado" class="inputText">
                                   <option value="1" selected>Con Movimiento</option>
                                   <option value="2">Sin Movimiento</option>
                               </select>
                           </td>

                       </tr>
                        <tr >
                           <td  height="35px" class="">Material </td>

                           <td class="">::</td>
                           <td>
                                <input type="text" id="nombreMaterial" name="nombreMaterial" style="width:350px" class="inputText">
                                    </td>
                          <td>
                                <input type="button" onclick="buscarMaterial(form1)" value="buscar" class="btn" />
                           </td>
                       </tr>
                       <tr>
                           <td colspan="6" style="text-align: center">
                                <input type="checkbox" id="checkTodo" onclick="seleccionarTodosMateriales(this)"/><label for="checkTodo">Seleccionar Todo</label>
                                <div style="overflow:auto;height:150px;margin-left:5%;width:90%;border: 1px solid #ccc" id="main">
                                    <div id="div_material">
                                        <table id='dataMateriales' class='outputText0' style='border : solid #cccccc 1px;' cellpadding='0' cellspacing='0'  >

                                        </table>
                                    </div>
                                </div>
                           </td>
                       </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value=" Reporte Por Item" name="reporte" onclick="verReporte(form1,'reporteConsumoPromedioPorItem.jsp')" />
                                <input type="button" class="btn"  value=" Reporte General" name="reporte" onclick="verReporteGeneral(form1,'reporteConsumoPromedioGeneral.jsp')" />
                            </td>
                        </tr>
                    </tfoot>

                </table>
                

            </div>
            <br>
            <br>
            <center>
                
                <%--input type="button" class="outputText2" style="visibility='hidden'" value=" Reporte General " name="reporte" onclick="verReporte(form1,'reporteConsumoPromedioGeneral.jsp')" />
                <input type="button" class="outputText2" style="visibility='hidden'" value="Reporte Por Lote" name="reporte" onclick="verReporte(form1,'reporteIngresosAlmacenPorItemLote.jsp')" />
                <input type="button" class="outputText2" style="visibility='hidden'" value="Reporte Por Lote Detallado" name="reporte" onclick="verReporte(form1,'reporteIngresosAlmacenPorItemLoteDetallado.jsp')" /--%>
                
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

            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">



        </form>
        <script src="../../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>
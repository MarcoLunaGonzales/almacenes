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
        
        <script >



            /****************** AJAX CLIENTE ********************/
            function ajaxClientes(f)
            {	ajaxDistritos(f);


            }


            function sel_todoLineaMkt(f){
                for(var i=0;i<=f.codfuncionario.options.length-1;i++)
                {   if(f.chk_todoFuncionario.checked==true)
                    {   f.codfuncionario.options[i].selected=true;
                    }
                    else
                    {   f.codfuncionario.options[i].selected=false;
                    }
                }
                return(true);
            }

            function selecccionarTodoArea(f){
                for(var i=0;i<=f.codAreaEmpresa.options.length-1;i++)
                {
                    f.codAreaEmpresa.options[i].selected=f.chk_todoArea.checked;                    
                }
                if(f.chk_todoArea.checked){
                    this.ajaxProductos(f);
                }else{
                    f.codCompProd.options.length=0;
                }
                
            }

            function selecccionarTodo(f){
                for(var i=0;i<=f.codCompProd.options.length-1;i++)
                {
                    f.codCompProd.options[i].selected=f.chk_todoTipo.checked;
                }
            }

            function sel_todoDistrito(f){
                var arrayDistrito=new Array();
                var j=0;
                for(var i=0;i<=f.coddistrito.options.length-1;i++)
                {   if(f.chk_todoDistrito.checked==true)
                    {   f.coddistrito.options[i].selected=true;
                        arrayDistrito[j]=f.coddistrito.options[i].value;
                        j++;
                    }
                    else
                    {   f.coddistrito.options[i].selected=false;
                    }
                }
                f.coddistrio=arrayDistrito;
                var div_zona;
                div_zona=document.getElementById("div_codzona");
                coddistrito=f.coddistrito;
                ajax=nuevoAjax();
                ajax.open("GET","../utiles/ajaxZonas.jsf?coddistrito="+arrayDistrito,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_zona.innerHTML=ajax.responseText;
                    }
                }
                ajax.send(null)
                return(true);
            }
            function sel_todoZona(f){
                for(var i=0;i<=f.codzona.options.length-1;i++)
                {   if(f.chk_todoZona.checked==true)
                    {   f.codzona.options[i].selected=true;
                    }
                    else
                    {   f.codzona.options[i].selected=false;
                    }
                }
                return(true);
            }

            function selecccionarTodoLinea(f){
                for(var i=0;i<=f.codLineaMkt.options.length-1;i++)
                {   if(f.chk_todoLinea.checked==true)
                    {   f.codLineaMkt.options[i].selected=true;
                    }
                    else
                    {   f.codLineaMkt.options[i].selected=false;
                    }
                }
                return(true);
            }
            function construirCadena(name){
                //document.getElementById(name).value='';
                var codtiposingresoventas=document.getElementById(name);
                var options=codtiposingresoventas.getElementsByTagName('option');

                var j=0;
                var data=new Array();
                for(var i=0;i<options.length;i++){
                    if(options[i].selected){
                        data[j]=options[i].value;j++;
                    }
                }
                return data;
            }

            function mandar(){
                var arrayMaquina=new Array();
                var j=0;
                for(var i=0;i<=form1.codAreaEmpresa.options.length-1;i++)
                {
                    if(form1.codAreaEmpresa.options[i].selected)
                    {
                        arrayMaquina[j]=form1.codAreaEmpresa.options[i].value;
                        j++;
                    }
                }
                form1.codigosArea.value=arrayMaquina;
                form1.action="navegadorReporteProgramaProduccion.jsf";

                form1.submit();
            }

            /****************** NUEVO AJAX ********************/
            function nuevoAjax()
            {	var xmlhttp=false;
                try {
                    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (E) {
                        xmlhttp = false;
                    }
                }
                if (!xmlhttp && typeof XMLHttpRequest!="undefined") {
                    xmlhttp = new XMLHttpRequest();
                }
                return xmlhttp;
            }
            /****************** AJAX DISTRITOS ********************/
            function ajaxDistritos(f)
            {
                var div_distrito;
                div_distrito=document.getElementById("div_distrito");
                codAreaEmpresa=f.codareaempresa.value;
                ajax=nuevoAjax();

                ajax.open("GET","../utiles/ajaxDistritos.jsf?codAreaEmpresa="+codAreaEmpresa,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_distrito.innerHTML=ajax.responseText;
                    }
                }
                ajax.send(null)
                var div_zona=document.getElementById("div_codzona");
                clearChild(div_zona.firstChild);
            }


            function ajaxX(f){
                var div_distrito;
                div_distrito=document.getElementById("div_coddistrito");
                codAreaEmpresa=f.codareaempresa.value;
                ajax=nuevoAjax();

                ajax.open("GET","../utiles/ajaxDistritos.jsf?codAreaEmpresa="+codAreaEmpresa,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_distrito.innerHTML=ajax.responseText;
                    }
                }
                ajax.send(null);
            }
            function desabilitarDistrito(f){
                f.chk_todoDistrito.checked=false;
            }
            function desabilitarZonas(f){
                f.chk_todoZona.checked=false;
            }
            function ajaxZonas(f){
                var div_zona;
                div_zona=document.getElementById("div_zona");

                var arrayDistrito=new Array();
                var j=0;
                for(var i=0;i<=f.coddistrito.options.length-1;i++)
                {	if(f.coddistrito.options[i].selected)
                    {	arrayDistrito[j]=f.coddistrito.options[i].value;
                        j++;
                    }
                }
                coddistrito=arrayDistrito;
                ajax=nuevoAjax();

                ajax.open("GET","../utiles/ajaxZonas.jsf?coddistrito="+coddistrito,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {

                        div_codzona.innerHTML=ajax.responseText;
                    }
                }
                ajax.send(null);
            }
            function ajaxArea(f){
                var div_area;
                div_area=document.getElementById("div_area");
                
                ajax=nuevoAjax();

                ajax.open("GET","ajaxArea.jsf?codProgramaProduccion="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {                        
                        div_area.innerHTML=ajax.responseText;
                    }
                }
                f.codCompProd.options.length=0;
                ajax.send(null);
                

            }

            function ajaxProductos(f){
                
                var div_producto=document.getElementById("div_producto");
                var arrayCodAreaEmpresa=new Array();
                var j=0;
                for(var i=0;i<=f.codAreaEmpresa.options.length-1;i++)
                {	if(f.codAreaEmpresa.options[i].selected)
                    {	arrayCodAreaEmpresa[j]=f.codAreaEmpresa.options[i].value;
                        j++;
                    }
                }
                
                ajax=nuevoAjax();

                ajax.open("GET","ajaxProducto.jsf?codProgramaProduccion="+(f.codFilial.value)+
                    "&codEstadoPrograma="+(f.codEstadoPrograma.value)+"&codAreaEmpresa="+arrayCodAreaEmpresa,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {

                        div_producto.innerHTML=ajax.responseText;
                    }
                }                
                ajax.send(null);
            }
            


            function clearChild(obj){
                while(obj.hasChildNodes())
                    obj.removeChild(obj.lastChild);
            }
            function filtraPorCodigoProgramaProduccion(codProgramaProduccionPeriodo){
                // var codigo=f.codAreaEmpresa.value;
                
                location.href="filtroReporteSeguimientoProgramaProduccion.jsf?codProgramaProduccionPeriodo="+codProgramaProduccionPeriodo;
            }

       function enviaProgramaProduccion(f){
           
       //sacar el valor del multiple
        /***** AREAS EMPRESA ******/

        

        //alert(document.getElementById('codFilial').options[document.getElementById('codFilial').selectedIndex].innerHTML);
        
        f.codProgramaProduccionPeriodo.value=document.getElementById('codFilial').options[document.getElementById('codFilial').selectedIndex].value;
        if(f.codProgramaProduccionPeriodo.value!='-1')
            {
                f.nombreProgramaProduccionPeriodo.value=document.getElementById('codFilial').options[document.getElementById('codFilial').selectedIndex].innerHTML;
            } else{
                f.nombreProgramaProduccionPeriodo.value="";
            }

        
        var arrayCompProd=new Array();
        var arrayCompProd1=new Array();
        var j=0;
        for(var i=0;i<=f.codCompProd.options.length-1;i++)
        {	if(f.codCompProd.options[i].selected)
            {	arrayCompProd[j]=f.codCompProd.options[i].value;
                arrayCompProd1[j]=f.codCompProd.options[i].innerHTML;
                j++;
            }
        }
        f.codCompProdArray.value=arrayCompProd;
        f.nombreCompProd.value=arrayCompProd1;       

        /*******************/

        var arrayCodAreaEmpresa=new Array();
        var arrayNombreAreaEmpresa=new Array();
        j=0;
        for(var i=0;i<=f.codAreaEmpresa.options.length-1;i++)
        {	if(f.codAreaEmpresa.options[i].selected)
            {	arrayCodAreaEmpresa[j]=f.codAreaEmpresa.options[i].value;
                arrayNombreAreaEmpresa[j]=f.codAreaEmpresa.options[i].innerHTML;
                j++;
            }
        }

        f.codAreaEmpresaP.value=arrayCodAreaEmpresa;
        f.nombreAreaEmpresaP.value=arrayNombreAreaEmpresa;

        /************************/
        f.desdeFechaP.value=f.fecha_inicio.value;
        f.hastaFechaP.value=f.fecha_final.value;

        /*
        if(f.codTipoReporteDetallado.value==1){
            f.action="reporteSeguimientoProgramaProduccion.jsf";
        }else{
            f.action="reporteSeguimientoProgramaProduccionResumido.jsf";
        }*/
        f.action="reporteSeguimientoProgramaProduccion.jsf";
        f.submit();
            }
        function areaEmpresa_change(f){

        

        
        alert("entro333333333333");
        /*var arrayCodAreaEmpresa=new Array();
        var arrayNombreAreaEmpresa=new Array();
        var j=0;
        for(var i=0;i<=f.codAreaEmpresa.options.length-1;i++)
        {	if(f.codAreaEmpresa.options[i].selected)
            {	arrayCodAreaEmpresa[j]=f.codAreaEmpresa.options[i].value;
                arrayNombreAreaEmpresa[j]=f.codAreaEmpresa.options[i].innerHTML;
                j++;
            }
        }

        f.codAreaEmpresaP.value=arrayCodAreaEmpresa;
        f.nombreAreaEmpresaP.value=arrayNombreAreaEmpresa;
        */

            f.chk_todoArea.checked=false
           // location.href="filtroReporteSeguimientoProgramaProduccion.jsf?codAreaEmpresa="+arrayCodAreaEmpresa;
        }


          function ajaxAlmacen(f){
                var div_almacen;
                div_almacen=document.getElementById("div_almacen");

                ajax=nuevoAjax();

                ajax.open("GET","ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_almacen.innerHTML=ajax.responseText;
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

             function buscarMaterial(){
                var arrayCodCapitulo=new Array();
                var capitulo = document.getElementById("codCapitulo");
                var grupo =document.getElementById("codGrupo");
                for(var i=0 ; i <capitulo.options.length ; i++)
                {	
                    if(capitulo.options[i].selected)
                    {	
                        arrayCodCapitulo.push(capitulo.options[i].value);
                    }
                }
                var arrayCodGrupo=new Array();
                for(var i=0 ; i<grupo.options.length-1;i++)
                {
                    if(grupo.options[i].selected)
                    {	
                        arrayCodGrupo.push(grupo.options[i].value);
                    }
                }
                
                var nombreMaterial = document.getElementById("nombreMaterial").value;
                var div_material = document.getElementById("div_material");

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
            function verReporte(f,nombreReporte){
                var nombreCapitulo=new Array();
                var codCapitulo=new Array();
                var capitulo = document.getElementById("codCapitulo");
                for(var i=0 ; i <capitulo.options.length ; i++)
                {	
                    if(capitulo.options[i].selected)
                    {	
                        nombreCapitulo.push(capitulo.options[i].innerHTML);
                        codCapitulo.push(capitulo.options[i].value);
                    }
                }

                var nombreGrupo=new Array();
                var codGrupo=new Array();
                var grupo = document.getElementById("codGrupo");
                for(var i=0 ; i < grupo.options.length ; i++)
                {
                    if(grupo.options[i].selected)
                    {	
                        codGrupo.push(grupo.options[i].value);
                        nombreGrupo.push(grupo.options[i].innerHTML);
                    }
                }
                var nombreEstado=new Array();
                var codEstado=new Array();
                var estadoMaterial = document.getElementById("codEstadoMaterial");
                for(var i=0 ; i < estadoMaterial.options.length ; i++)
                {
                    if(estadoMaterial.options[i].selected)
                    {	
                        codEstado.push(estadoMaterial.options[i].value);
                        nombreEstado.push(estadoMaterial.options[i].innerHTML);
                    }
                }
                
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].innerHTML;
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].innerHTML;
                f.nombreCapitulo.value = nombreCapitulo;
                
                f.nombreGrupo.value = nombreGrupo;
                f.codGrupoArray.value = codGrupo;
                f.codEstadoMaterialArray.value = codEstado;
                f.nombreEstadoMaterial.value = nombreEstado;
                f.codCapituloArray.value = codCapitulo;
                f.nombreMaterialP.value = f.nombreMaterial.value;
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
        <h3 align="center">Reporte de Lotes de Proveedor</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table border="0"  style="border:#ccc 1px solid" border="0" class="border" width="90%">
                    <tr class="headerClassACliente">
                        <td height="35px" colspan="6" >
                            <div class="outputText3" align="center">
                                Introduzca Datos
                            </div>
                        </td>

                    </tr>

                    <tr >
                        <td  class="outputText2">Filial</td>
                        <td class="outputText2">::</td>
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
                            <select style='color:fuchsia' name="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
                                <%
                            
                            out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
                            while (rs.next()) {
                                if(codFilial==rs.getInt("cod_filial")){
                                    out.print("<option value="+ rs.getString("cod_filial") +" selected >"+rs.getString("nombre_filial")+"</option>");
                                }else{
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
                        <td class="outputText2">Almacen</td>
                        <td class="outputText2">::</td>

                        <td style="padding:7px" class="">
                            <div id="div_almacen">

                            <%
                            String sql_almacenes="select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a where a.COD_FILIAL="+codFilial+" and a.COD_ESTADO_REGISTRO=1";
                            System.out.println("sql_almacenes:" + sql_almacenes);
                            Statement st_a = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet rs_a = st_a.executeQuery(sql_almacenes);
                            %>
                            <select style='color:fuchsia' name="codAlmacen" class="inputText" >
                                <option value="-1">-TODOS-</option>
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

                    <tr>
                        <td class="outputText2">Capitulos</td>
                        <td class="outputText2">::</td>
                        <td>
                            <select name="codCapitulo" id="codCapitulo" multiple size="10" class="inputText" onchange="ajaxGrupo(form1);">
                        <%
                            con = Util.openConnection(con);
                            System.out.println("con:::::::::::::" + con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            String sql = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1";
                            ResultSet res = st.executeQuery(sql);
                            while(res.next())
                            {
                                out.print("<option value="+ res.getInt("COD_CAPITULO") +" >"+res.getString("NOMBRE_CAPITULO")+"</option>");
                            }
                        %>
                            </select>
                        </td>
                        <td class="outputText2">Grupo</td>
                        <td class="outputText2">::</td>
                        <td >
                            <div id="div_grupo">
                                <select name="codGrupo" class="inputText" size="10" style="width:200px;" >
                                <option value="-1">-TODOS-</option>
                            </select>
                            
                            </div>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="outputText2">Material</td>
                        <td class="outputText2">::</td>
                        <td>
                            <input style="width:100%" id="nombreMaterial" name="nombreMaterial" type="text" class="inputText"/>
                        </td>
                        <td class="outputText2">Lote Material</td>
                        <td class="outputText2">::</td>
                        <td>
                            <input style="width:100%" id="loteMaterialProveedor" name="loteMaterialProveedor" type="text" class="inputText"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="outputText2">Estado Material</td>
                        <td class="outputText2">::</td>
                        <td>
                            <select id="codEstadoMaterial" multiple size="7" class="inputText">
                                <%
                                    sql = "select  em.COD_ESTADO_MATERIAL,em.NOMBRE_ESTADO_MATERIAL"
                                            +" from ESTADOS_MATERIAL em "
                                            +" where em.COD_ESTADO_MATERIAL not in(7)"
                                            +" order by em.NOMBRE_ESTADO_MATERIAL";
                                res = st.executeQuery(sql);
                                while(res.next())
                                {
                                    out.print("<option value="+ res.getInt("COD_ESTADO_MATERIAL") +" >"+res.getString("NOMBRE_ESTADO_MATERIAL")+"</option>");
                                }
                                %>
                            </select> 
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" style="text-align: center">
                            <a class="btn"  onclick="verReporte(form1,'reporteIngresosLotesProveedor.jsp')" >Ver reporte</a>
          
                        </td>
                    </tr>
                    
                </table>
                


            </div>
            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />
            <input type="hidden" name="nombreEstadoMaterial" />
            <input type="hidden" name="codGrupoArray" />
            <input type="hidden" name="codEstadoMaterialArray" />
            <input type="hidden" name="codCapituloArray" />
            <input type="hidden" name="codProveedorArray" />
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">
            <input type="hidden" name="nombreMaterialP">

            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">
            <input type="hidden" name="fechaIni">
            <input type="hidden" name="fechaFin">



        </form>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>
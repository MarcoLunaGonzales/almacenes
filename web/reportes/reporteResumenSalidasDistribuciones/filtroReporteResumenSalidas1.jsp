package reportes.reporteResumenSalidas_1;


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

<%! Connection con = null;
%>
<%
//con=CofarConnection.getConnectionJsp();
        con = Util.openConnection(con);

%>



<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
        <script>
            function cancelar(){
                // alert(codigo);
                location='../personal_jsp/navegador_personal.jsf';
            }
            function cargarAlmacen(f){
                var codigo=f.codAreaEmpresa.value;
                location.href="filtroReporteExistencias.jsp?codArea="+codigo;
            }
        </script>

        <style type="text/css">
            .tituloCampo1{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 11px;
                font-weight: bold;
            }
            .outputText3{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 11px;
            }
            .inputText3{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 11px;
            }
            .commandButtonR{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 11px;
                width: 150px;
                height: 20px;
                background-repeat :repeat-x;

                background-image: url('../img/bar3.png');
            }
        </style>

        <style type="text/css">
            .tituloCampo1{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 11px;
                font-weight: bold;
            }
            .outputText3{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 11px;
            }
            .inputText3{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 11px;
            }
            .commandButtonR{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 11px;
                width: 150px;
                height: 20px;
                background-repeat :repeat-x;

                background-image: url('../img/bar3.png');
            }
        </style>

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
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
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
                        f.action="reporteResumenPorCapGrupDetallado.jsf";
                      //  alert(f.action);
                   }
                    if(tipoReporte.value==2)
                   {
                        f.action="reporteResumenSalidasDetallado.jsf";
                      //  alert(f.action);
                   }
                    if(tipoReporte.value==3)
                   {
                        f.action="reporteResumenPorCapGrup.jsf";
                      //  alert(f.action);
                   }
                    if(tipoReporte.value==4)
                   {
                        f.action="reporteResumenSalidas.jsf";
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
        <h3 align="center">REPORTE DE SALIDAS Y DEVOLUCIONES</h3>
        <form method="post" action="reporteResumenSalidas.jsp" target="_blank" name="form1">
            <div align="center">
                <table border="0"  border="0" class="border" width="50%">
                    <tr class="headerClassACliente">
                        <td  colspan="6" >
                            <div class="outputText3" align="center">
                                Introduzca Datos
                            </div>
                        </td>

                    </tr>

                    <tr class="outputText3">
                        <td style="padding:8px">Filial</td>
                        <td style="padding:8px">::</td>
                        <%

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
                            <select  name="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
                                <%
                            
                            out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
                            
                            while (rs.next()) {
                                if(codFilial==rs.getInt("cod_filial")){
                                    out.print("<option value="+ rs.getString("cod_filial") +" selected >"+rs.getString("nombre_filial")+"</option>");
                                }else{
                                    out.print("<option value="+ rs.getString("cod_filial") +" >"+rs.getString("nombre_filial")+"</option>");
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
                            <select  name="codAlmacen" class="inputText" >
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
                            <select name="codTipoSalida" multiple size="10" class="outputText3">
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
                        <td style="padding:8px">Salidas con Lote ::</td>
                        <td style="padding:8px"><input id="conLote" type="checkbox" name="conLote"/></td>
                        <td  style="padding:8px">Todos ::<input type="checkbox" id="todosLote" name="todosLote" />
                        </td>
                    </tr>
                    
                     <tr class="outputText3">
                        <td style="padding:8px" style="padding:8px">Fecha Inicio</td>
                        <td style="padding:8px" style="padding:8px">::</td>
                       
                         <%
                                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                         %>

                        <td style="padding:8px" style="padding:8px">

                        <input type="text"  size="12"  value="<%=form.format(new Date()) %>" name="fecha_inicio" class="inputText">
                            <img id="imagenFecha1" src="../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fecha_inicio" click_element_id="imagenFecha1">
                             </DLCALENDAR>
                         
                            
                        </td>
                        <td style="padding:8px" style="padding:8px">Fecha Final</td>
                        <td style="padding:8px" style="padding:8px">::</td>
                        <td style="padding:8px">
                            <input type="text"  size="12"  value="<%=form.format(new Date()) %>" name="fecha_final" class="inputText">
                            <img id="imagenFecha2" src="../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fecha_final" click_element_id="imagenFecha2">
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
                            <select name="codSeccion" class="outputText3">
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
                         <td>Tipo de Reporte</td>
                            <td>::</td>
                            <td>
                         <select name="codTipoReporte" class="outputText3" >
                            <option value="1" selected>Reporte Detallado por Cap. y Grupos</option>
                            <option value="2" >Reporte Detallado por Secciones</option>
                            <option value="3">Reporte Resumen por Cap. y Grupos</option>
                             <option value="4">Reporte Resumen por Secciones</option>


                         </select>
                    </tr>

                    


                </table>
                


            </div>
            <br>
            <center>
                <input type="button" class="btn"  value="Ver Reporte" name="reporte" onclick="verReporte(form1,'reporteResumenSalidas.jsf')"/><%-- onclick="verReporte(form1,'reporteExistenciasAlmacen.jsp')" --%>
                


                <input type="hidden" name="codigosArea" id="codigosArea" />
            </center>
            <!--datos de referencia para el envio de datos via post-->

            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />
            <input type="hidden" name="codigosTipos" />




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
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>
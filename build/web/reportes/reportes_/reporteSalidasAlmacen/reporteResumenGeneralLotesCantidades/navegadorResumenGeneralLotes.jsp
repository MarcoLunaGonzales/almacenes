package reportes.reporteSalidasAlmacen.reporteResumenGeneralLotesCantidades;


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
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../../js/general.js"></script>
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


             function click2(f){
                //alert(f.check.checked);
                if (f.check2.checked==true){
                    f.codSalidas.disabled=false;

                }else{
                    f.codSalidas.disabled=true;

                }



            }

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
                 //alert();

                var fechaInicial = f.fecha1.value;
                var fechaFinal = f.fecha2.value;
                var codAlmacen = f.codAlmacen.value;
                

                var loteSelect;
                loteSelect=document.getElementById("loteSelect");
                

                ajax=nuevoAjax();

                ajax.open("GET","ajaxMaterial.jsf?fechaInicial="+fechaInicial+"&fechaFinal="+fechaFinal+"&codAlmacen="+codAlmacen,true);
                //alert("ajaxMaterial.jsf?fechaInicial="+fechaInicial+"&fechaFinal="+fechaFinal+"&codAlmacen="+codAlmacen,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        loteSelect.innerHTML=ajax.responseText;
                    }
                }

                ajax.send(null);


            }
             function click1(f){
                //alert(f.check.checked);
                if (f.check.checked==true){
                    f.fecha2.disabled=false;
                    f.fecha1.disabled=false;
                    f.nombreMaterial.disabled=false;
                }else{
                    f.fecha2.disabled=true;
                    f.fecha1.disabled=true;
                    f.nombreMaterial.disabled=true;
                }



            }
             function click2(f){
                //alert(f.check.checked);
                if (f.check2.checked==true){
                    f.codSalidas.disabled=false;

                }else{
                    f.codSalidas.disabled=true;

                }



            }
            //cel.getElementsByTagName('input')[0].checked=true;
            function verReporte(f,nombreReporte){
               
                //capitulos


               
                //f.codMaterial.value = arrayMateriales;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                // nro lote
                var arrayLote=new Array();
                j=0;
                for(var i=0;i<=f.codLote.options.length-1;i++)
                {	if(f.codLote.options[i].selected)
                    {	arrayLote[j]="'"+f.codLote.options[i].value+"'";
                        j++;
                    }
                }
                f.lotesArray.value=arrayLote;

                // salidas
                var arraySalidas=new Array();
                j=0;
                for(var i=0;i<=f.codSalidas.options.length-1;i++)
                {	if(f.codSalidas.options[i].selected)
                    {	arraySalidas[j]=f.codSalidas.options[i].value;
                        j++;
                    }
                }
                f.salidasArray.value=arraySalidas;
                //alert(arrayLote);
                //alert(arraySalidas);
                //alert("fecha1:"+f.fecha1.value);
                //alert("fecha2:"+f.fecha2.value);
                //alert("check:"+f.check.checked);
                if (f.check.checked==false){
                    f.fechaIni.value=0;
                    f.fechaFin.value=0;
                }else{
                    f.fechaIni.value=f.fecha1.value;
                    f.fechaFin.value=f.fecha2.value;
                }
                //alert("fecha1:"+f.fecha1.value);
                //alert("fecha2:"+f.fecha2.value);
                f.action = nombreReporte;
                f.submit();

            }
            function verReporte1(f,nombreReporte){

                //capitulos



                //f.codMaterial.value = arrayMateriales;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                // nro lote
                var arrayLote=new Array();
                j=0;
                for(var i=0;i<=f.codLote.options.length-1;i++)
                {	if(f.codLote.options[i].selected)
                    {	arrayLote[j]="'"+f.codLote.options[i].value+"'";
                        j++;
                    }
                }
                f.lotesArray.value=arrayLote;

                // salidas
                var arraySalidas=new Array();
                j=0;
                for(var i=0;i<=f.codSalidas.options.length-1;i++)
                {	if(f.codSalidas.options[i].selected)
                    {	arraySalidas[j]=f.codSalidas.options[i].value;
                        j++;
                    }
                }
                f.salidasArray.value=arraySalidas;
                //alert(arrayLote);
                //alert(arraySalidas);
                //alert("fecha1:"+f.fecha1.value);
                //alert("fecha2:"+f.fecha2.value);
                //alert("check:"+f.check.checked);
                if (f.check.checked==false){
                    f.fechaIni.value=0;
                    f.fechaFin.value=0;
                }else{
                    f.fechaIni.value=f.fecha1.value;
                    f.fechaFin.value=f.fecha2.value;
                }
                //alert("fecha1:"+f.fecha1.value);
                //alert("fecha2:"+f.fecha2.value);
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
        <h3 align="center"> Resúmen General de Lotes Cantidades</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table border="0"  border="0" style="border:1px solid maroon" class="border" cellpadding="2" width="80%">
                    <tr class="headerClassACliente">
                        <td height="50px" colspan="6" >
                            <div  align="center">
                                Introduzca Datos
                            </div>
                        </td>

                    </tr>

                    <tr class="outputText3">
                        <td height="50px" class=""><b>Filial</td>
                        <td class="">::</td>
                        <%
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
                            <select style="color:#a43706"    name="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
                                <%
                            
                            out.print("<option value='0'>-SELECCIONE UNA OPCION-</option>");
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
                        <td class=""><b>Almacen</td>
                        <td class=""><b>::</td>

                        <td class="">
                            <div id="div_almacen">
                            <select style="color:#a43706"    name="codAlmacen" class="inputText" >
                                <option value="0">-TODOS-</option>
                            </select>                            
                            </div>
                        </td>

                    </tr>

                   
                    <tr class="outputText3">
                       
                        
                        <td class=""><b>Nro de Lote </td>
                        <td class="">::</td>
                        <td style="padding:10px" class="">
                             <%

                           try {
                            con = Util.openConnection(con);
                            //System.out.println("con:::::::::::::" + con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                            String sql = "  select DISTINCT cod_lote_produccion from salidas_almacen where cod_almacen=1 and estado_sistema=1 and cod_estado_salida_almacen=1 and  cod_lote_produccion is NOT null and cod_lote_produccion<>''";

                            System.out.println("sql filtro:" + sql);
                            ResultSet rs = st.executeQuery(sql);
                        %>
                          <div id="loteSelect"   >
                            <select multiple size="10" style="width:200px;color:#a43706;font-size:12px" name="codLote"  class="outputText3" >
                               
                                <%
                            while (rs.next()) {
                                out.print("<option  value="+ rs.getString("cod_lote_produccion") +" >"+rs.getString("cod_lote_produccion")+"</option>");
                            }%>
                            </select>
                          </div>
                            <%
                            rs.close();
                            st.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            %>

                            <%--input type="text" class="inputText" size="70" name="nombreMaterial" /--%>
                        </td>
                       <td  height="35px" class=""><b>Habilitar Salidas <br><br> Sin Nro de Lote : &nbsp;<input type="checkbox" name="check2" onclick="click2(this.form)"></td>
                        <td class="">::&nbsp;</td>

                         <td style="padding:10px" class=""><br><br>
                             <%

                           try {
                            con = Util.openConnection(con);
                            //System.out.println("con:::::::::::::" + con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                            String sql = "  select cod_salida_almacen, nro_salida_almacen from salidas_almacen where estado_sistema=1";
                            //sql += "--and fecha_salida_almacen>='''+fecha_inicio+''' and fecha_salida_almacen<='''+fecha_fin+'''');";
                            sql += " and cod_almacen=1";
                            sql += " and cod_estado_salida_almacen=1 order by fecha_salida_almacen";
                            System.out.println("sql filtro:" + sql);
                            ResultSet rs = st.executeQuery(sql);
                        %>

                            <select multiple size="8" disabled="true" style="width:200px;color:#a43706;font-size:12px" name="codSalidas"  class="outputText3" >

                                <%
                            while (rs.next()) {
                                out.print("<option value="+ rs.getString("cod_salida_almacen") +" >"+rs.getString("nro_salida_almacen")+"</option>");
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
                        <td height="35px" class=""><b>Usar Fecha</td>
                        <td class=""><input type="checkbox" name="check" onclick="click1(this.form)"></td>
                        
                        <td colspan="3" class="">&nbsp;</td>
                    </tr>
                    
                     <tr class="outputText3">
                        <td  height="35px" class=""><b>Fecha Inicio</td>
                        <td class="">::</td>
                       
                         <%
                                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                         %>

                        <td class="">

                            <input type="text"  size="12" style="color:#a43706" disabled="true"  value="<%=form.format(new Date()) %>" name="fecha1" class="inputText">
                            <img id="imagenFecha1" src="../../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fecha1" click_element_id="imagenFecha1">
                             </DLCALENDAR>
                         </td>
                             <td  height="35px" class=""><b>Fecha Final</td>
                        <td class="">::</td>
                           <td class="">
                             <input type="text"  size="12" style="color:#a43706"  disabled="true"   value="<%=form.format(new Date()) %>" name="fecha2" class="inputText">
                            <img id="imagenFecha2" src="../../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fecha2" click_element_id="imagenFecha2">
                             </DLCALENDAR>
                         
                            <input type="button" value="Buscar Lotes" disabled="true" class="outputText2" size="50" name="nombreMaterial" onclick="buscarMaterial(this.form)" />
                        </td>
                     
                        

                  
                </table>
                


            </div>
            <br>
            <br>
            <center>
                <input type="button" class="outputText2"  value="Ver Reporte" name="reporte" onclick="verReporte(form1,'reporteResumenGeneralLotes.jsp')" />
                <input type="button" class="outputText2"  value="Ver Reporte por Item" name="reporte" onclick="verReporte1(form1,'reporteResumenGeneralLotesItem.jsp')" />
                <input type="reset" class="outputText2"  value="Limpiar " name="reporteL" />
              
                <input type="hidden" name="codigosArea" id="codigosArea" />
            </center>
            <!--datos de referencia para el envio de datos via post-->

            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
           
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="salidasArray">
            <input type="hidden" name="lotesArray">
            <input type="hidden" name="checkLotes">
            <input type="hidden" name="checkSalidas">
            <input type="hidden" name="fechaIni">
            <input type="hidden" name="fechaFin">


            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">
            <input type="hidden" name="nombreMaterialP">
            <input type="hidden" name="codProductoP">
            <input type="hidden" name="nombreProductoP">
            <input type="hidden" name="numLoteP">



        </form>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>
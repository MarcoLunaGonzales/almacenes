
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


             function click2(f){
                //alert(f.check.checked);
                if (f.check2.checked==true){
                    f.codSalidas.disabled=false;

                }else{
                    f.codSalidas.disabled=true;

                }



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
                

                ajax = creaAjax();
		var url="ajaxLotesSalidas.jsf?fechaInicial="+fechaInicial+"&fechaFinal="+fechaFinal+"&codAlmacen="+codAlmacen+"&data="+(new Date()).getTime().toString();
                ajax.open("GET",url,true);
                //alert("ajaxMaterial.jsf?fechaInicial="+fechaInicial+"&fechaFinal="+fechaFinal+"&codAlmacen="+codAlmacen,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        loteSelect.innerHTML=ajax.responseText;
                        desBloquearPantalla();
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
                if (f.check2.checked){
                    ajax = creaAjax();
                    var url="ajaxSalidasSinNroLote.jsf?data="+(new Date()).getTime().toString();
                    ajax.open("GET",url,true);
                    ajax.onreadystatechange=function(){
                        if (ajax.readyState==4) {
                            document.getElementById("divSalidasSinNroLote").innerHTML=ajax.responseText;
                            desBloquearPantalla();
                        }
                    }
                    ajax.send(null);

                }else{
                    f.codSalidas.disabled=true;
                }
            }
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
                    f.fechaIni.value = "null";
                    f.fechaFin.value = "null";
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
                <table class="tablaFiltroReporte" width="80%">
                    <thead>
                        <tr>
                            <td height="50px" colspan="6" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                    <tr class="outputText3">
                        <td height="50px" class=""><b>Filial</td>
                        <td class="">::</td>
                        <td class="">
                            <select style="color:#a43706" id="codFilial" name="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
                        <%
                            ManagedAccesoSistema bean1=(ManagedAccesoSistema)com.cofar.util.Util.getSessionBean("ManagedAccesoSistema");
                           int codigoAlmacen=bean1.getAlmacenesGlobal().getCodAlmacen();
                            Connection con = null;
                           String codProgramaProduccionPeriodoReq ="";
                           if(request.getParameter("codProgramaProduccionPeriodo")!=null)
                           {codProgramaProduccionPeriodoReq = request.getParameter("codProgramaProduccionPeriodo");}
                           try {
                            con = Util.openConnection(con);
                            System.out.println("con:::::::::::::" + con);
                            String sql_filial="select a.COD_FILIAL from ALMACENES a where a.COD_ALMACEN="+codigoAlmacen+"";
                            System.out.println("sql_filial:" + sql_filial);
                            Statement st_filial = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet rs_filial = st_filial.executeQuery(sql_filial);
                            int codFilial=0;
                            while(rs_filial.next()){
                                codFilial=rs_filial.getInt(1);
                            }
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);            
                            String sql = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

                            System.out.println("sql filtro:" + sql);
                            ResultSet rs = st.executeQuery(sql);
                            out.print("<option value='0'>-SELECCIONE UNA OPCION-</option>");
                            while (rs.next()) {
                                out.print("<option "+(rs.getInt("cod_filial")==codFilial ? "selected":"")+" value='"+ rs.getString("cod_filial") +"' >"+rs.getString("nombre_filial")+"</option>");
                            }%>
                            </select>
                            <!--  <input type="checkbox"  onclick="selecccionarTodo(form1)" name="chk_todoTipo" >Todo-->
                            
                        </td>
                        <td class=""><b>Almacen</td>
                        <td class=""><b>::</td>

                        <td class="">
                            <div id="div_almacen">
                            <select style="color:#a43706" id="codAlmacen" name="codAlmacen" class="inputText" >
                                <option value="0">-TODOS-</option>
                                <%
                                    sql = "select a.COD_ALMACEN,a.NOMBRE_ALMACEN"+
                                            " from almacenes a"+
                                            " where a.COD_ESTADO_REGISTRO=1"+
                                            " and a.COD_FILIAL="+codFilial+" order by a.NOMBRE_ALMACEN";
                                    rs = st.executeQuery(sql);
                                    while(rs.next()){
                                        out.println("<option "+(rs.getInt("COD_ALMACEN") == codigoAlmacen?"selected":"")+" value='"+rs.getInt("COD_ALMACEN")+"'>"+rs.getString("NOMBRE_ALMACEN")+"</option>");
                                    }
                                        rs.close();
                                        st.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                %>
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
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            String sql = "  select DISTINCT cod_lote_produccion"+
                                         " from salidas_almacen"+
                                         " where cod_almacen="+codigoAlmacen+
                                            " and estado_sistema=1"+
                                            " and cod_estado_salida_almacen=1"+
                                            " and  cod_lote_produccion is NOT null"+
                                            " and cod_lote_produccion<>''"+
                                            " and FECHA_SALIDA_ALMACEN BETWEEN '"+sdf.format(new Date())+" 00:00' and '"+sdf.format(new Date())+" 23:59'"+
                                         " order by cod_lote_produccion";

                            System.out.println("sql filtro:" + sql);
                            ResultSet rs = st.executeQuery(sql);
                        %>
                          <div id="loteSelect"   >
                            <select multiple size="10" style="width:200px;color:#a43706;font-size:12px" id="codLote" name="codLote"  class="outputText3" >
                                <%
                            while (rs.next()) {
                                out.print("<option  value='"+ rs.getString("cod_lote_produccion") +"' >"+rs.getString("cod_lote_produccion")+"</option>");
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
                            <div id="divSalidasSinNroLote">
                                <select multiple size="8" disabled="true" style="width:200px;color:#a43706;font-size:12px" id="codSalidas" name="codSalidas"  class="outputText3" >
                                </select>
                            </div>
                           
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

                            <input type="text"  size="12" style="color:#a43706" disabled="true"  value="<%=form.format(new Date()) %>" id="fecha1" name="fecha1" class="dlCalendar">
                            
                         </td>
                             <td  height="35px" class=""><b>Fecha Final</td>
                        <td class="">::</td>
                        <td class="">
                            <table>
                                <tr>
                                    <td>
                                        <input type="text"  size="12" style="color:#a43706"  disabled="true"   value="<%=form.format(new Date()) %>" id="fecha2" name="fecha2" class="dlCalendar">
                                    </td>
                                    <td>
                                        <input type="button" value="Buscar Lotes" disabled="true" class="btn" size="50" name="nombreMaterial" onclick="buscarMaterial(this.form)" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Ver Reporte" name="reporte" onclick="verReporte(form1,'reporteResumenGeneralLotes.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte por Item" name="reporte" onclick="verReporte1(form1,'reporteResumenGeneralLotesItem.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte por Item (Ubicaciones)" name="reporte" onclick="verReporte1(form1,'reporteResumenGeneralLotesItemUbicaciones.jsp')" />
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
        <script src="../../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>

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
        <script src="../../js/general.js"></script>
       
        <script type="text/javascript">
          function ajaxAlmacen(f){
                ajax=creaAjax();
                ajax.open("GET","../ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
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
                {	if(f.codCapitulo.options[i].selected)
                    {	arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }
                ajax=creaAjax();
                ajax.open("GET","../ajaxGrupo.jsf?codCapitulo="+arrayCodCapitulo+"&multiple=1&size=10",true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_grupo").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                
                ajax.send(null);


            }

             function buscarMaterial(f){
                var arrayCodCapitulo=new Array();
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {   if(f.codCapitulo.options[i].selected){
                    	arrayCodCapitulo.push(f.codCapitulo.options[i].value)
                    }
                }

                var arrayCodGrupo=new Array();
                for(var i=0;i<=f.codGrupo.options.length-1;i++){
                    if(f.codGrupo.options[i].selected){
                        arrayCodGrupo.push(f.codGrupo.options[i].value);
                    }
                }
                var nombreMaterial = document.getElementById("nombreMaterial").value;
                var movimiento = document.getElementById("movimiento").value;
                ajax=creaAjax();

                ajax.open("POST","../ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo+"&movimientoItem="+movimiento,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_material").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                ajax.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
                ajax.send("nombreMaterial="+encodeURIComponent(nombreMaterial));


            }
            function verReporte(f,nombreReporte){
                // seleccionar los items de la tabla
                 var elements=document.getElementById('dataMateriales');
                 var rowsElement=elements.rows;
                 var arrayMateriales = new Array();
                 var j = 0;
                for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel=cellsElement[0];
                        if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                            if(cel.getElementsByTagName('input')[0].checked==true){
                                arrayMateriales[j]=cel.getElementsByTagName('input')[1].value==''?"0":cel.getElementsByTagName('input')[1].value;
                                j = j+1;
                                //alert(arrayMateriales[i]);
                            }                            
                        }
                }
                //capitulos


                var nombreCapitulo=new Array();
                //var arrayNombreAreaEmpresa=new Array();
                j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	nombreCapitulo[j]=f.codCapitulo.options[i].innerHTML;
                        j++;
                    }
                }

                //grupo
                var nombreGrupo=new Array();
                j=0;
                for(var i=0;i<=f.codGrupo.options.length-1;i++)
                {	if(f.codGrupo.options[i].selected)
                    {	nombreGrupo[j]=f.codGrupo.options[i].innerHTML;
                        j++;
                    }
                }

                f.codMaterial.value = arrayMateriales;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                f.nombreCapitulo.value = nombreCapitulo;
                f.nombreGrupo.value = nombreGrupo;


                //Salert(f.codMaterial.value);
                f.action = nombreReporte+"?codAlmacen="+document.getElementById('codAlmacen').value
                        +"&fecha="+document.getElementById('fecha').value
                        //+"&codMaterial="+f.codMaterial.value
                        //+"&codGrupo="+document.getElementById('codGrupo').value
                        +"&movimiento="+document.getElementById('movimiento').value
                        //+"&codCapitulo="+document.getElementById('codCapitulo').value
                        +"&codStock="+document.getElementById('codStock').value
                        
                ;
                
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
        <h3 align="center">REPORTE DE EXISTENCIAS</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table border="0"  border="0" class="tablaFiltroReporte" width="50%">
                    <thead>
                        <tr>
                            <td  colspan="6" >
                                Introduzca Datos
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                    <tr class="outputText3">
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
                            <select style='color:fuchsia' id="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
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
                            <select style='color:fuchsia' id="codAlmacen" class="inputText" >
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

                    <tr class="outputText3" >
                        <td style="padding:8px">Capitulos</td>
                        <td style="padding:8px">::</td>
                        
                                 <%

                           try {
                            con = Util.openConnection(con);
                            System.out.println("con:::::::::::::" + con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




                            String sql = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1 order by c.NOMBRE_CAPITULO";

                            System.out.println("sql filtro:" + sql);
                            ResultSet rs = st.executeQuery(sql);
                        %>
                        <td style="padding:8px">
                            <select id="codCapitulo" multiple size="10" class="outputText3" onchange="ajaxGrupo(form1);">
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
                         <td style="padding:8px">Grupo</td>
                        <td style="padding:8px">::</td>
                        <td  style="padding:8px">
                            <div id="div_grupo">
                                <select multiple id="codGrupo" class="inputText" size="10" style="width:200px;" >
                                <option value="-1">-NINGUNO-</option>
                            </select>
                            
                            </div>
                        </td>
                    </tr>
                    <tr class="outputText3">
                        <td style="padding:8px" style="padding:8px">Item</td>
                        <td style="padding:8px" style="padding:8px">::</td>
                        <td style="padding:8px" style="padding:8px" colspan="3" >
                            <input type="text" class="inputText" size="70" id="nombreMaterial"  />
                        </td>
                        
                        <td style="padding:8px" style="padding:8px">
                            <input type="button" class="btn"   onclick="buscarMaterial(form1)" value="Buscar"/>
                        </td>
                    </tr>
                     <tr class="outputText3">
                        <td style="padding:8px" style="padding:8px">Existencias a Fecha</td>
                        <td style="padding:8px" style="padding:8px">::</td>
                       
                         <%
                                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                         %>

                        <td style="padding:8px" style="padding:8px">
                            <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fecha" name="fecha" class="dlCalendar"/>
                        </td>
                        <td style="padding:8px" style="padding:8px">Stock de Items</td>
                        <td style="padding:8px" style="padding:8px">::</td>
                        <td style="padding:8px">
                         <select id="codStock" class="inputText"  >
                                <option value="0"> Todos los Item </option>
                                <option value="1"> Items con Stock menores al Stock Mínimo</option>
                                <option value="2"> Items con Stock menores al Stock de Seguridad</option>
                                <option value="3"> Items con Stock Normales</option>
                                <option value="4"> Items con Stock mayores al Stock Máximo</option>
                         </select>
                        </td>


                        

                    </tr>

                        <tr>
                            <td style="padding:8px" style="padding:8px">Items </td>
                            <td style="padding:8px" style="padding:8px">::</td>
                            <td style="padding:8px" style="padding:8px">
                                <select id="movimiento" class="outputText3">
                                    <option value="1" selected>Con Movimiento</option>
                                    <option value="2">Sin Movimiento</option>

                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6" style="text-align: center">
                                <div class="outputText1">
                                    <input type="checkbox" id="seleccionar_todo" onchange="seleccionarTodo()"/>
                                    <label for="seleccionar_todo" >Seleccionar Todo </label>
                                </div>
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
                                <input type="button" class="btn"  value="Ver Reporte" name="reporte" onclick="verReporte(form1,'reporteExistenciasAlmacen.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte Comercial" name="reporte" onclick="verReporte(form1,'reporteExistenciasComercialAlmacen.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte para Inventario" name="reporte" onclick="verReporte(form1,'reporteExistenciasAlmacenInventario.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte para Inventario MP" name="reporte" onclick="verReporte(form1,'reporteExistenciasAlmacenInventarioMP.jsp')" />
                                <input type="button" class="btn"  value="Ver Reporte con Detalle de última salida" name="reporte" onclick="verReporte(form1,'reporteExistenciasAlmacenUltimaSalida.jsp')" />
                            </td>
                        </tr>
                    </tfoot>
                </table>
                
                


            </div>
            <input type="hidden" id="codigosArea" id="codigosArea" />
            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />



            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">

            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">



        </form>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>